package net.dragonmounts3.capability;

import net.dragonmounts3.network.SInitCooldownPacket;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;

public interface IDragonTypifiedCooldown {
    void bind(PlayerEntity player);

    int get(DragonType type);

    void set(DragonType type, int cooldown);

    void tick();

    CompoundNBT writeNBT(Direction side);

    void readNBT(Direction side, CompoundNBT nbt);

    SInitCooldownPacket createPacket();
}
