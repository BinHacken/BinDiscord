package de.binhacken.discord

import com.github.kaktushose.jda.commands.JDACommands
import de.binhacken.discord.commands.BangCommandsManager
import de.binhacken.discord.data.config.Config
import de.binhacken.discord.data.config.loadConfig
import de.binhacken.discord.data.database.DatabaseController
import de.binhacken.discord.module.remindme.RemindMeModule
import de.binhacken.discord.module.sandwiches.SandwichModule
import de.binhacken.discord.utils.info
import dev.minn.jda.ktx.jdabuilder.intents
import dev.minn.jda.ktx.jdabuilder.light
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.requests.GatewayIntent


val jda by lazy { Main.jda }

object Main {
    lateinit var config: Config private set
    lateinit var jda: JDA; private set
    lateinit var jdaCommands: JDACommands; private set

    var debugEnabled = true; private set
    var traceEnabled = true; private set

    @JvmStatic
    fun main(args: Array<String>) {
        Runtime.getRuntime().addShutdownHook(Thread { stop() })
        config = loadConfig() ?: error("Cannot read Config!")

        debugEnabled = config.logConfig.debug
        traceEnabled = config.logConfig.trace

        DatabaseController

        jda = light(config.discordConfig.token, enableCoroutines = true) {
            intents += GatewayIntent.GUILD_MESSAGES
            intents += GatewayIntent.MESSAGE_CONTENT
        }
        jdaCommands = JDACommands.start(jda, this::class.java)

        BangCommandsManager.start()

        if (config.remindMeConfig.enabled) {
            info("Enabling RemindMe Module")
            RemindMeModule(config.remindMeConfig)
        }


        if (config.sandwichConfig.enabled) {
            info("Enabling Sandwich Module")
            SandwichModule(config.sandwichConfig)
        }
    }

    fun stop() {

    }
}

