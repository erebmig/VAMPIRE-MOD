package com.erebmig.vampiremod

import com.erebmig.vampiremod.command.VampireCommands
import com.erebmig.vampiremod.entity.VampireData
import com.erebmig.vampiremod.event.VampireEventHandler
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object VampireMod : ModInitializer {
    const val MOD_ID = "vampire-mod"
    private val logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitialize() {
        logger.info("🧛 Vampire Mod Initialized! Version 1.0.0")
        
        VampireEventHandler.registerEvents()
        VampireCommands.registerCommands()
        VampireData.init()
        
        logger.info("✅ All Vampire Mod systems loaded successfully!")
    }
}
