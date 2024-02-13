package net.dragonmounts.mixin;

import net.dragonmounts.DragonMountsConfig;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerController;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerController.class)
public class PlayerControllerMixin {
    @Final
    @Shadow
    private Minecraft minecraft;

    @Inject(method = "isServerControlledInventory", at = @At("RETURN"), cancellable = true)
    public void isTameableDragon(CallbackInfoReturnable<Boolean> info) {
        if (DragonMountsConfig.CLIENT.redirect_inventory.get()) {
            //noinspection DataFlowIssue
            info.setReturnValue(info.getReturnValueZ() || this.minecraft.player.getVehicle() instanceof TameableDragonEntity);
        }
    }
}
