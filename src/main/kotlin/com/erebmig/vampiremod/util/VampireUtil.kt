package com.erebmig.vampiremod.util

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.particle.ParticleTypes
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.Vec3d
import kotlin.random.Random

object VampireUtil {
    
    fun playBiteEffect(player: PlayerEntity, villager: VillagerEntity) {
        if (player.world.isClient) return
        
        val world = player.world
        val pos = villager.pos
        
        // Blood particles
        repeat(10) {
            val vx = Random.nextDouble(-0.5, 0.5)
            val vy = Random.nextDouble(0.0, 1.0)
            val vz = Random.nextDouble(-0.5, 0.5)
            
            world.spawnParticles(
                ParticleTypes.DRIPPING_OBSIDIAN_TEARS,
                pos.x, pos.y + villager.height / 2, pos.z,
                1,
                vx, vy, vz,
                0.5
            )
        }
        
        // Blood spatter particles (red)
        repeat(5) {
            val angle = Random.nextDouble(0.0, Math.PI * 2)
            val distance = Random.nextDouble(0.5, 2.0)
            val vx = Math.cos(angle) * distance
            val vz = Math.sin(angle) * distance
            
            world.spawnParticles(
                ParticleTypes.REDSTONE,
                pos.x + vx, pos.y + villager.height / 2, pos.z + vz,
                2,
                0.0, 0.1, 0.0,
                1.0
            )
        }
        
        // Sound effect
        world.playSound(
            null,
            pos.x, pos.y, pos.z,
            SoundEvents.ENTITY_GENERIC_HURT,
            net.minecraft.sound.SoundCategory.PLAYERS,
            1.0f,
            0.8f
        )
    }

    fun applyVampireBlindness(player: PlayerEntity) {
        if (player.world.isClient) return
        
        player.sendMessage(
            net.minecraft.text.Text.literal("§4§l*** VISION FADES ***"),
            true
        )
    }

    fun getBloodPercentage(blood: Int, maxBlood: Int): Float {
        return (blood.toFloat() / maxBlood.toFloat()).coerceIn(0f, 1f)
    }

    fun formatBloodBar(blood: Int, maxBlood: Int): String {
        val percentage = getBloodPercentage(blood, maxBlood)
        val barLength = 20
        val filledLength = (percentage * barLength).toInt()
        
        val bar = "█".repeat(filledLength) + "░".repeat(barLength - filledLength)
        return "§c$bar §f$blood/$maxBlood"
    }
}
