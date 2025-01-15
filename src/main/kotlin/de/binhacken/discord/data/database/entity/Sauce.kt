@file:Suppress("unused", "ExposedReference")

package de.binhacken.discord.data.database.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object SauceTable : IntIdTable() {
    val name = varchar("name", 50).uniqueIndex()
}

class SauceEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SauceEntity>(SauceTable)

    var name by SauceTable.name
}