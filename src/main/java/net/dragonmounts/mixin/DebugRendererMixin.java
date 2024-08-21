package net.dragonmounts.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts.config.ClientConfig;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.debug.PathfindingDebugRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugRenderer.class)
public abstract class DebugRendererMixin {
    @Shadow
    @Final
    public PathfindingDebugRenderer pathfindingRenderer;

    @Inject(method = "render", at = @At("TAIL"))
    public void renderPath(MatrixStack matrices, IRenderTypeBuffer.Impl buffer, double x, double y, double z, CallbackInfo info) {
        if (ClientConfig.INSTANCE.debug.get()) {
            this.pathfindingRenderer.render(matrices, buffer, x, y, z);
        }
    }

    private DebugRendererMixin() {}
}
