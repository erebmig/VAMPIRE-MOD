package com.erebmig.vampiremod.client.render

import com.erebmig.vampiremod.entity.VampireData
import com.erebmig.vampiremod.util.VampireUtil
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.RenderTickCounter

object VampireRenderManager {
    fun registerRenders() {
        // HUD rendering
        HudRenderCallback.EVENT.register { drawContext: DrawContext, _: RenderTickCounter ->
            renderVampireHUD(drawContext)
        }
    }

    private fun renderVampireHUD(drawContext: DrawContext) {
        val client = MinecraftClient.getInstance()
        val player = client.player ?: return

        if (!VampireData.isVampire(player)) return

        val info = VampireData.getVampireInfo(player)
        val bloodBar = VampireUtil.formatBloodBar(info.blood, info.maxBlood)
        val screenWidth = drawContext.scaledWindowWidth
        val screenHeight = drawContext.scaledWindowHeight

        // Render blood bar at top left
        val text = "§c🧛 $bloodBar"
        drawContext.drawText(
            client.textRenderer,
            text,
            10,
            10,
            0xFFFFFF,
            true
        )

        // Render vampire level
        val levelText = "§fLevel: §c${info.vampireLevel}"
        drawContext.drawText(
            client.textRenderer,
            levelText,
            10,
            25,
            0xFFFFFF,
            true
        )

        // Render minion count
        val minionText = "§fMinions: §c${info.minionCount}"
        drawContext.drawText(
            client.textRenderer,
            minionText,
            10,
            40,
            0xFFFFFF,
            true
        )

        // Low blood warning
        if (info.blood < 30) {
            val warning = "§c§lSTARVING FOR BLOOD!"
            val textWidth = client.textRenderer.getWidth(warning)
            drawContext.drawText(
                client.textRenderer,
                warning,
                (screenWidth - textWidth) / 2,
                screenHeight - 30,
                0xFF0000,
                true
            )
        }
    }
}
