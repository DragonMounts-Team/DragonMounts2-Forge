package net.dragonmounts3.api;

import net.dragonmounts3.capability.IArmorEffectManager;
import net.minecraft.entity.player.PlayerEntity;

@FunctionalInterface
public interface IArmorEffect {
    boolean activate(IArmorEffectManager manager, PlayerEntity player, int level);
}
