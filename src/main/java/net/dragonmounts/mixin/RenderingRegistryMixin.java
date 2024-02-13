package net.dragonmounts.mixin;

import net.dragonmounts.client.ClientDragonEntity;
import net.dragonmounts.client.renderer.dragon.TameableDragonRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

import static net.dragonmounts.init.DMEntities.TAMEABLE_DRAGON;

@Mixin(value = RenderingRegistry.class, remap = false)
public class RenderingRegistryMixin {
    @Final
    @Shadow
    private Map<EntityType<? extends Entity>, IRenderFactory<? extends Entity>> entityRenderers;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void registerRenderer(CallbackInfo info) {
        this.entityRenderers.put(TAMEABLE_DRAGON.get(), (IRenderFactory<ClientDragonEntity>) TameableDragonRenderer::new);
    }
}
