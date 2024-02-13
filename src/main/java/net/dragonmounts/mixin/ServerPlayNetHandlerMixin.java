package net.dragonmounts.mixin;

import net.dragonmounts.entity.dragon.ServerDragonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.CEntityActionPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetHandler.class)
public class ServerPlayNetHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "handlePlayerCommand", at = @At("TAIL"))
    public void openDragonInventory(CEntityActionPacket packet, CallbackInfo info) {
        if (packet.getAction() == CEntityActionPacket.Action.OPEN_INVENTORY) {
            Entity entity = this.player.getVehicle();
            if (entity instanceof ServerDragonEntity) {
                ((ServerDragonEntity) entity).openInventory(this.player);
            }
        }
    }
}
