package net.dragonmounts3.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.Map;

public interface IArmorEffectSource {
    void putEffect(Map<IArmorEffect, Integer> map, PlayerEntity player, ItemStack stack);
}
