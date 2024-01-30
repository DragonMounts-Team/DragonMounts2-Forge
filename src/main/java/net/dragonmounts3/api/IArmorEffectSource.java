package net.dragonmounts3.api;

import net.dragonmounts3.capability.IArmorEffectManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface IArmorEffectSource {
    void affect(IArmorEffectManager manager, PlayerEntity player, ItemStack stack);
}
