@file:Suppress("unused", "ExposedReference")

package de.binhacken.discord.data.database.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object SandwichTable : IntIdTable() {
    val name = varchar("name", 50).uniqueIndex()
    val priceSmall = float("price_small")
    val priceLarge = float("price_large")
    val vegetarian = bool("vegetarian")
    val ingredients = varchar("ingredients", 255)
}

class SandwichEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SandwichEntity>(SandwichTable)

    var name by SandwichTable.name
    var priceSmall by SandwichTable.priceSmall
    var priceLarge by SandwichTable.priceLarge
    var vegetarian by SandwichTable.vegetarian
    var ingredients by SandwichTable.ingredients
}