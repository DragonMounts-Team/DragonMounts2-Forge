package net.dragonmounts.mixin;

import net.dragonmounts.client.renderer.dragon.TamableDragonOnShoulderLayer;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {
    @Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererManager;)V", at = @At("TAIL"))
    public void addDragonLayer(EntityRendererManager $, CallbackInfo info) {
        this.addLayer(new TamableDragonOnShoulderLayer<>(this));
    }

    private PlayerRendererMixin(EntityRendererManager a, PlayerModel<AbstractClientPlayerEntity> b, float c) {super(a, b, c);}
}
