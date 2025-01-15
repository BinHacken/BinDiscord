@file:Suppress("unused", "RemoveRedundantQualifierName")

import com.github.kaktushose.jda.commands.annotations.interactions.Interaction
import com.github.kaktushose.jda.commands.annotations.interactions.Param
import com.github.kaktushose.jda.commands.annotations.interactions.SlashCommand
import com.github.kaktushose.jda.commands.dispatching.events.interactions.CommandEvent
import de.binhacken.discord.Main
import de.binhacken.discord.data.database.DatabaseController
import de.binhacken.discord.data.database.entity.SauceTable
import de.binhacken.discord.utils.ifFalse
import de.binhacken.discord.utils.replyEmbed
import de.binhacken.discord.utils.replyModuleNotEnabled
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.transactions.transaction

@Interaction
class SauceDeleteCommand {
    @SlashCommand("sandwichmeister sauce-delete", desc = "Lösche eine Sose")
    fun onSandwichCommand(
        event: CommandEvent,
        @Param(name = "name", value = "Name der Sose")
        sauceName: String,
    ) {
        Main.config.sandwichConfig.enabled.ifFalse {
            event.replyModuleNotEnabled("Sandwiches")
            return
        }

        transaction(DatabaseController.db) {
            if (SauceTable.deleteWhere { SauceTable.name.lowerCase() eq sauceName.lowercase() } > 0) {
                event.replyEmbed {
                    title = "✔ Sose gelöscht!"
                    field {
                        name = "🧾 Name"
                        value = sauceName
                    }
                }
            } else {
                event.replyEmbed {
                    title = "❌ Sose existiert nicht!"
                    field {
                        name = "🧾 Name"
                        value = sauceName
                    }
                }
            }
        }

    }

}