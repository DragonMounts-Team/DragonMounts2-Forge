package net.dragonmounts.mixin;

import net.dragonmounts.entity.dragon.DragonLifeStage;
import net.dragonmounts.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts.entity.dragon.ServerDragonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Inject(method = "removeEntityComplete", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/server/ServerWorld;getScoreboard()Lnet/minecraft/scoreboard/ServerScoreboard;"), cancellable = true)
    public void tryHatchDragonEgg(Entity entity, boolean $, CallbackInfo info) {
        if (entity instanceof HatchableDragonEggEntity) {
            HatchableDragonEggEntity egg = (HatchableDragonEggEntity) entity;
            if (egg.isHatched()) {
                egg.level.addFreshEntity(new ServerDragonEntity(egg, DragonLifeStage.NEWBORN));
                info.cancel();
            }
        }
    }

    private ServerWorldMixin() {}
}
