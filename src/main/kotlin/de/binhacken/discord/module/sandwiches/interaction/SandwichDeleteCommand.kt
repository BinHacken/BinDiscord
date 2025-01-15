@file:Suppress("unused", "RemoveRedundantQualifierName")

package de.binhacken.discord.module.sandwiches.interaction

import com.github.kaktushose.jda.commands.annotations.interactions.Interaction
import com.github.kaktushose.jda.commands.annotations.interactions.Param
import com.github.kaktushose.jda.commands.annotations.interactions.SlashCommand
import com.github.kaktushose.jda.commands.dispatching.events.interactions.CommandEvent
import de.binhacken.discord.Main
import de.binhacken.discord.data.database.DatabaseController
import de.binhacken.discord.data.database.entity.SandwichTable
import de.binhacken.discord.utils.ifFalse
import de.binhacken.discord.utils.replyEmbed
import de.binhacken.discord.utils.replyModuleNotEnabled
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.transactions.transaction

@Interaction
class SandwichDeleteCommand {
    @SlashCommand("sandwichmeister sandwich-delete", desc = "Lösche ein Sandwich")
    fun onSandwichCommand(
        event: CommandEvent,
        @Param(name = "name", value = "Name des Sandwiches")
        sandwichName: String,
    ) {
        Main.config.sandwichConfig.enabled.ifFalse {
            event.replyModuleNotEnabled("Sandwiches")
            return
        }

        transaction(DatabaseController.db) {
            if (SandwichTable.deleteWhere { SandwichTable.name.lowerCase() eq sandwichName.lowercase() } > 0) {
                event.replyEmbed {
                    title = "✔ Sandwich gelöscht!"
                    field {
                        name = "🧾 Name"
                        value = sandwichName
                    }
                }
            } else {
                event.replyEmbed {
                    title = "❌ Sandwich existiert nicht!"
                    field {
                        name = "🧾 Name"
                        value = sandwichName
                        inline = true
                    }
                }
            }
        }

    }
}