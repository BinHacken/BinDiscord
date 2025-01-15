package de.binhacken.discord.data.config

import kotlinx.serialization.Serializable

@Serializable
data class DiscordConfig(
    val token: String = "",
    val serverId: Long = 0,
    var admins: List<Long> = listOf(492297419736875009)
)