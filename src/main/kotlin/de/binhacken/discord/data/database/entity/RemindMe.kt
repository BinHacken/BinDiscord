@file:Suppress("unused", "ExposedReference")

package de.binhacken.discord.data.database.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object RemindMeTable : IntIdTable() {
    val channelId = long("channelId")
    val messageId = long("messageId").uniqueIndex()
    val instant = timestamp("instant")
}

class RemindMeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RemindMeEntity>(RemindMeTable)

    var channelId by RemindMeTable.channelId
    var messageId by RemindMeTable.messageId
    var instant by RemindMeTable.instant
}