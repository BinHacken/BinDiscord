package de.binhacken.discord.data.config.modules

import kotlinx.serialization.Serializable

@Serializable
data class RemindMeConfig(
    val enabled: Boolean = true,
    val easterEggTimeStampFormatting: Boolean = true,
)