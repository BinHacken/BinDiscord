package de.binhacken.discord.commands

import de.binhacken.discord.jda
import dev.minn.jda.ktx.events.listener
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object BangCommandsManager {
    private val bangCommands: MutableMap<String, (MessageReceivedEvent) -> Unit> = mutableMapOf()

    fun command(name: String, block: (MessageReceivedEvent) -> Unit): Boolean {
        if (bangCommands.containsKey(name.lowercase())) return false
        bangCommands[name.lowercase()] = block
        return true
    }

    fun start() =
        jda.listener<MessageReceivedEvent> { event ->
            if (!event.message.contentRaw.startsWith("!")) return@listener
            event.message.contentRaw
                .removePrefix("!")
                .split(" ")
                .getOrNull(0)
                ?.let { name ->
                    bangCommands[name.lowercase()]?.invoke(event)
                }
        }

}