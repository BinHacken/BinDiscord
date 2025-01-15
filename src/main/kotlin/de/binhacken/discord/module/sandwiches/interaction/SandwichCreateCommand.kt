@file:Suppress("unused")

package de.binhacken.discord.module.sandwiches.interaction

import com.github.kaktushose.jda.commands.annotations.interactions.Choices
import com.github.kaktushose.jda.commands.annotations.interactions.Interaction
import com.github.kaktushose.jda.commands.annotations.interactions.Param
import com.github.kaktushose.jda.commands.annotations.interactions.SlashCommand
import com.github.kaktushose.jda.commands.dispatching.events.interactions.CommandEvent
import de.binhacken.discord.Main
import de.binhacken.discord.data.database.DatabaseController
import de.binhacken.discord.data.database.entity.SandwichEntity
import de.binhacken.discord.data.database.entity.SandwichTable
import de.binhacken.discord.utils.ifFalse
import de.binhacken.discord.utils.replyEmbed
import de.binhacken.discord.utils.replyModuleNotEnabled
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.transactions.transaction

@Interaction
class SandwichCreateCommand {
    @SlashCommand("sandwichmeister sandwich-create", desc = "Erstelle ein neues Sandwich")
    fun onSandwichCommand(
        event: CommandEvent,
        @Param(name = "name", value = "Name des Sandwiches")
        sandwichName: String,
        @Param(name = "ingredients", value = "Zutaten des Sandwiches")
        ingredients: String,
        @Param(name = "price-large", value = "Preis für ein großes Sandwich")
        priceLarge: Float,
        @Param(name = "price-small", value = "Preis für ein kleines Sandwich")
        priceSmall: Float,
        @Param(name = "vegetarian", value = "Vegetarisch?")
        @Choices(value = arrayOf("Ja", "Nein"))
        vegetarian: String,
    ) {
        Main.config.sandwichConfig.enabled.ifFalse {
            event.replyModuleNotEnabled("Sandwiches")
            return
        }

        transaction(DatabaseController.db) {
            SandwichEntity.find { SandwichTable.name.lowerCase() eq sandwichName.lowercase() }.singleOrNull()?.let {
                event.replyEmbed {
                    title = "❌ Sandwich existiert bereits!"
                    field {
                        name = "🧾 Name"
                        value = sandwichName
                    }
                }
            } ?: run {
                SandwichEntity.new {
                    this.name = sandwichName.lowercase()
                    this.ingredients = ingredients
                    this.priceLarge = priceLarge
                    this.priceSmall = priceSmall
                    this.vegetarian = vegetarian == "Ja"
                }
                event.replyEmbed {
                    title = "✔ Sandwich erstellt!"
                    field {
                        name = "🧾 Name"
                        value = sandwichName
                        inline = true
                    }
                    field {
                        name = "🌱 Vegetarisch"
                        value = vegetarian
                        inline = true
                    }
                    field {
                        name = "🍲 Zutaten"
                        value = ingredients

                    }
                    field {
                        name = "💸 Preis Groß"
                        value = priceLarge.toString()
                        inline = true
                    }
                    field {
                        name = "💵 Preis Klein"
                        value = priceSmall.toString()
                        inline = true
                    }
                }
            }
        }

    }

}