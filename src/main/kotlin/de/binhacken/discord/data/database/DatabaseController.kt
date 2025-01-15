package de.binhacken.discord.data.database

import de.binhacken.discord.data.database.entity.RemindMeTable
import de.binhacken.discord.data.database.entity.SandwichOrderTable
import de.binhacken.discord.data.database.entity.SandwichTable
import de.binhacken.discord.data.database.entity.SauceTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseController {
    val db by lazy { Database.connect("jdbc:sqlite:data/data.sqlite", driver = "org.sqlite.JDBC") }

    init {
        db
        transaction {
            SchemaUtils.create(RemindMeTable, SandwichTable, SauceTable, SandwichOrderTable)

        }
    }
}