package net.dragonmounts.mixin;

import net.dragonmounts.capability.IArmorEffectManager.Provider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.management.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {
    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    public void sendInitPacket(NetworkManager $, ServerPlayerEntity player, CallbackInfo ci) {
        ((Provider) player).dragonmounts$getManager().sendInitPacket();
    }

    private PlayerListMixin() {}
}
