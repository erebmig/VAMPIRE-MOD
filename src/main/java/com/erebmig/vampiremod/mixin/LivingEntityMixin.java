package com.erebmig.vampiremod.mixin;

import com.erebmig.vampiremod.entity.VampireData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onLivingEntityTick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        
        if (entity instanceof PlayerEntity player && !entity.getWorld().isClient) {
            if (VampireData.INSTANCE.isVampire(player)) {
                VampireData.VampireInfo info = VampireData.INSTANCE.getVampireInfo(player);
                
                // Apply starvation effects
                if (info.getBlood() < 20) {
                    player.addStatusEffect(
                        new net.minecraft.entity.effect.StatusEffectInstance(
                            net.minecraft.entity.effect.StatusEffects.WEAKNESS,
                            20,
                            1
                        )
                    );
                }
            }
        }
    }
}
