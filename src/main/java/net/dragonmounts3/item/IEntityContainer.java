package net.dragonmounts3.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public interface IEntityContainer<T extends Entity> {
    static CompoundNBT simplifyData(CompoundNBT tag) {
        tag.remove("Air");
        tag.remove("DeathTime");
        tag.remove("FallDistance");
        tag.remove("FallFlying");
        tag.remove("Fire");
        tag.remove("HurtByTimestamp");
        tag.remove("HurtTime");
        tag.remove("InLove");
        tag.remove("Leash");
        tag.remove("Motion");
        tag.remove("OnGround");
        tag.remove("PortalCooldown");
        tag.remove("Pos");
        tag.remove("Rotation");
        tag.remove("SleepingX");
        tag.remove("SleepingY");
        tag.remove("SleepingZ");
        tag.remove("TicksFrozen");
        return tag;
    }

    @Nullable
    Entity spwanEntity(
            ServerWorld level,
            @Nullable PlayerEntity player,
            CompoundNBT tag,
            BlockPos pos,
            SpawnReason reason,
            @Nullable ILivingEntityData data,
            boolean yOffset,
            boolean extraOffset
    );

    ItemStack saveEntity(T entity);

    boolean isEmpty(CompoundNBT tag);

    default boolean canSetNbt(MinecraftServer server, Entity entity, @Nullable PlayerEntity player) {
        return !entity.onlyOpCanSetNbt() || player != null && server.getPlayerList().isOp(player.getGameProfile());
    }
}
