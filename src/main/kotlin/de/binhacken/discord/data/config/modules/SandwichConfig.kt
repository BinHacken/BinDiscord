package de.binhacken.discord.data.config.modules

import kotlinx.serialization.Serializable

@Serializable
data class SandwichConfig(
    val enabled: Boolean = true,
    val sandwichMasters: List<Long> = listOf(492297419736875009)
)
