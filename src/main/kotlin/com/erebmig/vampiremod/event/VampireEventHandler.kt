package com.erebmig.vampiremod.event

import com.erebmig.vampiremod.entity.VampireData
import com.erebmig.vampiremod.util.VampireUtil
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.mob.Mob
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Box
import net.minecraft.world.World

object VampireEventHandler {
    fun registerEvents() {
        // Server tick for blood drain and effects
        ServerTickEvents.END_SERVER_TICK.register { server ->
            for (world in server.worlds) {
                if (world !is ServerWorld) continue
                
                for (player in world.players) {
                    updateVampirePlayer(player)
                }
            }
        }

        // Attack event for biting
        ServerLivingEntityEvents.ALLOW_DAMAGE.register { entity, source, amount ->
            if (source.attacker is PlayerEntity && entity is VillagerEntity) {
                val player = source.attacker as PlayerEntity
                if (VampireData.isVampire(player)) {
                    handleVampireBite(player, entity)
                }
            }
            true
        }

        // Death event for minions
        ServerLivingEntityEvents.AFTER_DEATH.register { entity, damageSource ->
            if (entity is Mob && entity.world !is ServerWorld) return@register
            
            val owner = (entity as? Mob)?.owner
            if (owner is PlayerEntity && VampireData.isVampire(owner)) {
                // Handle minion death
            }
        }
    }

    private fun updateVampirePlayer(player: PlayerEntity) {
        if (!VampireData.isVampire(player)) return

        val info = VampireData.getVampireInfo(player)
        val currentTime = System.currentTimeMillis()
        val timeSinceLastBlood = currentTime - info.lastBloodTime

        // Blood drain every 3 minutes (180000 ms)
        if (timeSinceLastBlood > 180000) {
            VampireData.drainBlood(player, 1)

            // Apply negative effects when blood is low
            if (info.blood < 30) {
                applyVampireStarvationEffects(player)
            }
        }

        // Spawn minions to attack nearby villagers
        if (player.world is ServerWorld && info.vampireLevel > 0) {
            val world = player.world as ServerWorld
            val nearbyVillagers = world.getOtherEntities(
                player,
                Box.of(player.pos, 32.0, 16.0, 32.0),
                { it is VillagerEntity }
            )

            for (villager in nearbyVillagers) {
                if (villager is VillagerEntity && Math.random() > 0.95) {
                    commandMinionAttack(villager, player)
                }
            }
        }
    }

    private fun handleVampireBite(player: PlayerEntity, villager: VillagerEntity) {
        val info = VampireData.getVampireInfo(player)

        // Drink blood
        VampireData.drinkBlood(player, 15)

        // Option to convert to vampire
        if (Math.random() > 0.7 && info.vampireLevel >= 2) {
            convertVillagerToVampire(villager)
            info.minionCount++
        }

        VampireUtil.playBiteEffect(player, villager)
        VampireData.setVampireInfo(player, info)
    }

    private fun convertVillagerToVampire(villager: VillagerEntity) {
        // Tag villager as vampire minion
        villager.nbt.putBoolean("VampireMinion", true)
    }

    private fun commandMinionAttack(villager: VillagerEntity, owner: PlayerEntity) {
        if (!villager.nbt.getBoolean("VampireMinion")) return
        
        val nearbyVillagers = villager.world.getOtherEntities(
            villager,
            Box.of(villager.pos, 16.0, 8.0, 16.0),
            { it is VillagerEntity && it != villager }
        )

        if (nearbyVillagers.isNotEmpty()) {
            val target = nearbyVillagers.random() as VillagerEntity
            // Attack logic would go here
        }
    }

    private fun applyVampireStarvationEffects(player: PlayerEntity) {
        // Add visual/audio effects for low blood
        val info = VampireData.getVampireInfo(player)
        
        if (info.blood < 20) {
            player.sendMessage(
                net.minecraft.text.Text.literal("§c🧛 You are starving for blood!"),
                true
            )
            // Screen effects would be applied on client side
        }
    }
}
