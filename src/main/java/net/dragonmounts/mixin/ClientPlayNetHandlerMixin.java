package net.dragonmounts.mixin;

import net.dragonmounts.capability.ArmorEffectManager;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientPlayNetHandler.class)
public abstract class ClientPlayNetHandlerMixin {
    @Inject(method = "handleRespawn", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/entity/player/ClientPlayerEntity;setId(I)V"
    ), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onPlayerClone(
            SRespawnPacket $,
            CallbackInfo info,
            RegistryKey<World> _$,
            DimensionType $_,
            ClientPlayerEntity oldPlayer,
            int i,
            String $$,
            ClientPlayerEntity newPlayer
    ) {
        ArmorEffectManager.onPlayerClone(oldPlayer, newPlayer);
    }

    private ClientPlayNetHandlerMixin() {}
}
