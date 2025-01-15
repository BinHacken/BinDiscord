package de.binhacken.discord.utils

import com.github.kaktushose.jda.commands.dispatching.events.interactions.CommandEvent

fun CommandEvent.replyModuleNotEnabled(module: String) = replyEmbed {
    title = "❌ Dieses Modul ist nicht aktiviert!"
    field {
        name = "🧳 Module"
        value = module
    }
}