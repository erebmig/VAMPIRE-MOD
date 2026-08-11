package com.erebmig.vampiremod.command

import com.erebmig.vampiremod.entity.VampireData
import com.erebmig.vampiremod.util.VampireUtil
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

object VampireCommands {
    fun registerCommands() {
        CommandRegistrationCallback.EVENT.register { dispatcher: CommandDispatcher<ServerCommandSource>, _: CommandRegistryAccess, _: CommandManager.RegistrationEnvironment ->
            dispatcher.register(
                CommandManager.literal("vampire")
                    .requires { it.hasPermissionLevel(2) }
                    .then(
                        CommandManager.literal("make")
                            .then(
                                CommandManager.argument("player", EntityArgumentType.player())
                                    .executes { context ->
                                        val player = EntityArgumentType.getPlayer(context, "player")
                                        VampireData.makeVampire(player)
                                        context.source.sendFeedback(
                                            { Text.literal("§c🧛 ${player.name.string} is now a vampire!") },
                                            false
                                        )
                                        1
                                    }
                            )
                    )
                    .then(
                        CommandManager.literal("blood")
                            .then(
                                CommandManager.argument("player", EntityArgumentType.player())
                                    .then(
                                        CommandManager.argument("amount", IntegerArgumentType.integer(0, 200))
                                            .executes { context ->
                                                val player = EntityArgumentType.getPlayer(context, "player")
                                                val amount = IntegerArgumentType.getInteger(context, "amount")
                                                VampireData.setBlood(player, amount)
                                                context.source.sendFeedback(
                                                    { Text.literal("§c${player.name.string}'s blood set to $amount") },
                                                    false
                                                )
                                                1
                                            }
                                    )
                            )
                    )
                    .then(
                        CommandManager.literal("status")
                            .then(
                                CommandManager.argument("player", EntityArgumentType.player())
                                    .executes { context ->
                                        val player = EntityArgumentType.getPlayer(context, "player")
                                        val info = VampireData.getVampireInfo(player)
                                        val status = if (info.isVampire) "§c🧛 VAMPIRE" else "§fNormal"
                                        val bloodBar = VampireUtil.formatBloodBar(info.blood, info.maxBlood)
                                        
                                        context.source.sendFeedback(
                                            { Text.literal("§e${player.name.string} Status:\n$status\n$bloodBar\n§fLevel: ${info.vampireLevel}\n§fMinions: ${info.minionCount}") },
                                            false
                                        )
                                        1
                                    }
                            )
                    )
            )
        }
    }
}
