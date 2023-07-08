package net.dragonmounts3.api;

import net.minecraft.entity.player.PlayerEntity;

@FunctionalInterface
public interface IArmorEffect {
    void invoke(PlayerEntity player, int strength);
}
