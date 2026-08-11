package com.erebmig.vampiremod.entity

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import java.util.concurrent.ConcurrentHashMap

object VampireData {
    private val vampireData = ConcurrentHashMap<String, VampireInfo>()

    data class VampireInfo(
        var isVampire: Boolean = false,
        var blood: Int = 100,
        var maxBlood: Int = 100,
        var lastBloodTime: Long = 0,
        var vampireLevel: Int = 0,
        var minionCount: Int = 0
    ) {
        fun toNbt(): NbtCompound {
            val nbt = NbtCompound()
            nbt.putBoolean("IsVampire", isVampire)
            nbt.putInt("Blood", blood)
            nbt.putInt("MaxBlood", maxBlood)
            nbt.putLong("LastBloodTime", lastBloodTime)
            nbt.putInt("VampireLevel", vampireLevel)
            nbt.putInt("MinionCount", minionCount)
            return nbt
        }

        companion object {
            fun fromNbt(nbt: NbtCompound): VampireInfo {
                return VampireInfo(
                    isVampire = nbt.getBoolean("IsVampire"),
                    blood = nbt.getInt("Blood"),
                    maxBlood = nbt.getInt("MaxBlood"),
                    lastBloodTime = nbt.getLong("LastBloodTime"),
                    vampireLevel = nbt.getInt("VampireLevel"),
                    minionCount = nbt.getInt("MinionCount")
                )
            }
        }
    }

    fun init() {
        // Initialize data storage
    }

    fun getVampireInfo(player: PlayerEntity): VampireInfo {
        return vampireData.getOrPut(player.uuid.toString()) {
            VampireInfo()
        }
    }

    fun setVampireInfo(player: PlayerEntity, info: VampireInfo) {
        vampireData[player.uuid.toString()] = info
    }

    fun isVampire(player: PlayerEntity): Boolean {
        return getVampireInfo(player).isVampire
    }

    fun makeVampire(player: PlayerEntity) {
        val info = getVampireInfo(player)
        info.isVampire = true
        info.blood = 100
        info.maxBlood = 100
        setVampireInfo(player, info)
    }

    fun getBlood(player: PlayerEntity): Int {
        return getVampireInfo(player).blood
    }

    fun setBlood(player: PlayerEntity, amount: Int) {
        val info = getVampireInfo(player)
        info.blood = amount.coerceIn(0, info.maxBlood)
        setVampireInfo(player, info)
    }

    fun drainBlood(player: PlayerEntity, amount: Int = 1) {
        val info = getVampireInfo(player)
        info.blood = (info.blood - amount).coerceAtLeast(0)
        setVampireInfo(player, info)
    }

    fun drinkBlood(player: PlayerEntity, amount: Int = 20) {
        val info = getVampireInfo(player)
        info.blood = (info.blood + amount).coerceAtMost(info.maxBlood)
        info.lastBloodTime = System.currentTimeMillis()
        setVampireInfo(player, info)
    }
}
