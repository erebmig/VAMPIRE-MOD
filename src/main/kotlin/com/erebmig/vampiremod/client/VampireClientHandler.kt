package com.erebmig.vampiremod.client

import com.erebmig.vampiremod.entity.VampireData
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW

object VampireClientHandler {
    private lateinit var bitingKeyBinding: KeyBinding

    fun registerClientEvents() {
        // Register key binding
        bitingKeyBinding = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "key.vampire-mod.bite",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "category.vampire-mod"
            )
        )

        // Client tick event
        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (bitingKeyBinding.wasPressed()) {
                handleBiteKey(client)
            }
        }
    }

    private fun handleBiteKey(client: MinecraftClient) {
        val player = client.player ?: return
        if (!VampireData.isVampire(player)) return

        player.attack(player.raycast(5.0, 0f, false).entity ?: return)
    }
}
