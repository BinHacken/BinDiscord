package de.binhacken.discord.data.config

import com.akuleshov7.ktoml.Toml
import com.akuleshov7.ktoml.TomlIndentation
import com.akuleshov7.ktoml.TomlInputConfig
import com.akuleshov7.ktoml.TomlOutputConfig
import de.binhacken.discord.data.config.modules.RemindMeConfig
import de.binhacken.discord.data.config.modules.SandwichConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlin.io.path.*

val toml = Toml(
    inputConfig = TomlInputConfig(
        ignoreUnknownNames = true,
        allowNullValues = true,
    ),
    outputConfig = TomlOutputConfig(
        ignoreNullValues = false,
        ignoreDefaultValues = false,
        indentation = TomlIndentation.TWO_SPACES,
    )
)

@Serializable
data class Config(
    @SerialName("Loggging")
    val logConfig: LogConfig = LogConfig(),
    @SerialName("Discord")
    val discordConfig: DiscordConfig = DiscordConfig(),
    @SerialName("RemindMe")
    val remindMeConfig: RemindMeConfig = RemindMeConfig(),
    @SerialName("Sandwiches")
    val sandwichConfig: SandwichConfig = SandwichConfig(),
)


fun loadConfig(): Config? = Path("data/config.toml").let {
    if (!it.exists()) {
        it.createParentDirectories()
        it.createFile()
        it.writeText(toml.encodeToString(Config()))
    }
    runCatching { toml.decodeFromString<Config>(it.readText()) }.getOrNull()
}
