package com.erebmig.vampiremod.mixin;

import com.erebmig.vampiremod.entity.VampireData;
import com.erebmig.vampiremod.util.VampireUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerBiteMixin extends LivingEntity {

    public VillagerBiteMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onVillagerDamage(net.minecraft.entity.damage.DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.getAttacker() instanceof PlayerEntity player && !this.getWorld().isClient) {
            if (VampireData.INSTANCE.isVampire(player)) {
                VampireData.drinkBlood(player, 15);
                VampireUtil.playBiteEffect(player, (VillagerEntity)(Object)this);
            }
        }
    }
}
