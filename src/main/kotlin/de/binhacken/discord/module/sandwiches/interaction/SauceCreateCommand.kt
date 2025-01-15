@file:Suppress("unused")

package de.binhacken.discord.module.sandwiches.interaction

import com.github.kaktushose.jda.commands.annotations.interactions.Interaction
import com.github.kaktushose.jda.commands.annotations.interactions.Param
import com.github.kaktushose.jda.commands.annotations.interactions.SlashCommand
import com.github.kaktushose.jda.commands.dispatching.events.interactions.CommandEvent
import de.binhacken.discord.Main
import de.binhacken.discord.data.database.DatabaseController
import de.binhacken.discord.data.database.entity.SauceEntity
import de.binhacken.discord.data.database.entity.SauceTable
import de.binhacken.discord.utils.ifFalse
import de.binhacken.discord.utils.replyEmbed
import de.binhacken.discord.utils.replyModuleNotEnabled
import org.jetbrains.exposed.sql.transactions.transaction

@Interaction
class SauceCreateCommand {
    @SlashCommand(value = "sandwichmeister sauce-create", desc = "Sandwichmeister only!")
    fun onCommand(
        event: CommandEvent,
        @Param(name = "name", value = "Sosenname") sauceName: String
    ) {
        Main.config.sandwichConfig.enabled.ifFalse {
            event.replyModuleNotEnabled("Sandwiches")
            return
        }

        if (sauceName.length > 50) {
            event.reply("Zu langer Name (50 Zeichen)!")
            return
        }
        transaction(DatabaseController.db) {
            SauceEntity.find { SauceTable.name eq sauceName }.firstOrNull()?.let {
                event.replyEmbed {
                    title = "❌ Sose existiert bereits!"
                    field {
                        name = "Sose"
                        value = sauceName
                    }
                }
            } ?: run {
                SauceEntity.new { name = sauceName }
                event.replyEmbed {
                    title = "✔ Sose erstellt!"
                    field {
                        name = "Sose"
                        value = sauceName
                    }
                }
            }
        }
    }
}