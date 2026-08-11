package com.erebmig.vampiremod

import com.erebmig.vampiremod.client.VampireClientHandler
import com.erebmig.vampiremod.client.render.VampireRenderManager
import net.fabricmc.api.ClientModInitializer
import org.slf4j.LoggerFactory

object VampireModClient : ClientModInitializer {
    private val logger = LoggerFactory.getLogger("${VampireMod.MOD_ID}-client")

    override fun onInitializeClient() {
        logger.info("🧛 Vampire Mod Client Initialized!")
        
        VampireClientHandler.registerClientEvents()
        VampireRenderManager.registerRenders()
        
        logger.info("✅ Vampire Mod Client systems loaded!")
    }
}
