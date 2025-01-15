package de.binhacken.discord.module.sandwiches

import de.binhacken.discord.data.config.modules.SandwichConfig

class SandwichModule(config: SandwichConfig) {

    init {
        if (!config.enabled)
            throw IllegalStateException("SandwichModule Constructor called while de.binhacken.discord.module was deactivated")
    }
}