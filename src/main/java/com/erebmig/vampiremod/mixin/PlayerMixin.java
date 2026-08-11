package com.erebmig.vampiremod.mixin;

import com.erebmig.vampiremod.entity.VampireData;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onPlayerTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (!player.getWorld().isClient && VampireData.INSTANCE.isVampire(player)) {
            VampireData.VampireInfo info = VampireData.INSTANCE.getVampireInfo(player);
            
            long currentTime = System.currentTimeMillis();
            long timeSinceLastBlood = currentTime - info.getLastBloodTime();
            
            // Drain blood every 3 minutes
            if (timeSinceLastBlood > 180000) {
                VampireData.drainBlood(player, 1);
                info.setLastBloodTime(currentTime);
            }
        }
    }
}
