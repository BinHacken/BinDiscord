@file:Suppress("unused", "ExposedReference")

package de.binhacken.discord.data.database.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object SandwichOrderTable : IntIdTable() {
    val eaterName = varchar("eater_name", 50)
    val sandwich = reference("sandwich", SandwichTable)
    val sauce = reference("sauce", SauceTable)
    val remarks = varchar("remarks", 255).nullable()
    val isSmall = bool("small")

    val paypalUser = varchar("paypal_user", 50).nullable()

    val orderDate = timestamp("order_date")

    val inPersonPayment = bool("in_person_payment")
    val isPaid = bool("is_paid")
}

class SandwichOrderEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SandwichOrderEntity>(SandwichOrderTable)

    var eaterName by SandwichOrderTable.eaterName
    var sandwich by SandwichEntity referencedOn SandwichOrderTable.sandwich
    var sauce by SauceEntity referencedOn SandwichOrderTable.sauce
    var remark by SandwichOrderTable.remarks
    var isSmall by SandwichOrderTable.isSmall

    var paypalUser by SandwichOrderTable.paypalUser
    var orderDate by SandwichOrderTable.orderDate
    var inPersonPayment by SandwichOrderTable.inPersonPayment
    var isPaid by SandwichOrderTable.isPaid
}
