package de.binhacken.discord.module.remindme

import de.binhacken.discord.commands.BangCommandsManager
import de.binhacken.discord.data.config.modules.RemindMeConfig
import de.binhacken.discord.data.database.DatabaseController
import de.binhacken.discord.data.database.entity.RemindMeEntity
import de.binhacken.discord.data.database.entity.RemindMeTable
import de.binhacken.discord.jda
import de.binhacken.discord.utils.debug
import de.binhacken.discord.utils.trace
import kotlinx.coroutines.*
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class RemindMeModule(config: RemindMeConfig) {
    init {
        if (!config.enabled)
            throw IllegalStateException("RemindMeModule Constructor called while de.binhacken.discord.module was deactivated")

        BangCommandsManager.command("remindme") {
            val message = it.message
            val content = message.contentRaw

            val instantPattern = content.removePrefix("!remindme ")

            if (!instantPattern.matches(Regex("""^(?!0(\.0+)?${'$'})\d+(\.\d+)?\s+(minutes?|hours?|days?|weeks?|months?|years?|seconds?)${'$'}"""))) {
                message.reply("Invalid Remind Me!").queue()
                return@command
            }

            val instant = Clock.System.now().plus(
                instantPattern.split(" ").let { (num: String, type: String) ->
                    when (type) {
                        "second", "seconds" -> num.toInt().seconds
                        "minute", "minutes" -> num.toInt().minutes
                        "hour", "hours" -> num.toInt().hours
                        "day", "days" -> num.toInt().days
                        "week", "weeks" -> num.toInt().days * 7
                        "month", "months" -> num.toInt().days * 30
                        "year", "years" -> num.toInt().days * 356
                        else -> throw IllegalStateException("Invalid Type after Regex Check")
                    }
                }
            )

            createRemindMe(message.channelIdLong, message.idLong, instant)
            if (!config.easterEggTimeStampFormatting) {
                message.reply(
                    "I will remind you at ${
                        instant.plus(1.hours).format(DateTimeComponents.Formats.RFC_1123).removeSuffix(" GMT")
                    }"
                ).queue()
            } else {
                message.reply("no").queue()
            }
        }


        CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                debug("checking for remindmes")

                getRemainingRemindMes().forEach { message ->
                    trace("sending remindme channel:${message.channelId} message:${message.messageId}")

                    jda.getChannelById(MessageChannel::class.java, message.channelId)
                        ?.retrieveMessageById(message.messageId)
                        ?.queue { retrievedMessage ->
                            trace { "retrieved id:" + retrievedMessage.id + " retrieved channel:" + retrievedMessage.channelIdLong + " retrieved user:" + retrievedMessage.author.idLong }
                            retrievedMessage.reply("You wanted me to remind you of this message!").queue()
                        }

                    trace("deleting message channel:${message.channelId} message:${message.messageId}")

                    deleteRemindMe(message)
                }

                delay(10.seconds)
            }
        }
    }

    private fun createRemindMe(channelId: Long, messageId: Long, instant: Instant) =
        transaction(DatabaseController.db) {
            RemindMeEntity.new {
                this.channelId = channelId
                this.messageId = messageId
                this.instant = instant
            }
        }

    private fun getRemainingRemindMes(): List<RemindMeEntity> = transaction(DatabaseController.db) {
        RemindMeEntity.find { RemindMeTable.instant lessEq Clock.System.now() }.toList()
    }

    private fun deleteRemindMe(remindMeEntity: RemindMeEntity) =
        transaction(DatabaseController.db) { remindMeEntity.delete() }
}