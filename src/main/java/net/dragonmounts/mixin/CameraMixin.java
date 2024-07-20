package net.dragonmounts.mixin;

import net.dragonmounts.config.ClientConfig;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ActiveRenderInfo.class)
public abstract class CameraMixin {
    @Shadow
    protected abstract double getMaxZoom(double desiredCameraDistance);

    @Shadow
    protected abstract void move(double x, double y, double z);

    @Inject(method = "setup", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/ActiveRenderInfo;getMaxZoom(D)D"
    ), cancellable = true)
    public void setCameraOffset(IBlockReader $$, Entity host, boolean __, boolean $_, float $, CallbackInfo info) {
        Entity entity = host.getVehicle();
        if (entity instanceof TameableDragonEntity) {
            this.move(-this.getMaxZoom(ClientConfig.INSTANCE.camera_distance.get()), 0.0D, -ClientConfig.INSTANCE.camera_offset.get());
            info.cancel();
        }
    }

    private CameraMixin() {}
}
