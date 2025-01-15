package de.binhacken.discord.data.config

import kotlinx.serialization.Serializable

@Serializable
data class LogConfig(
    val debug: Boolean = false,
    val trace: Boolean = false,
)