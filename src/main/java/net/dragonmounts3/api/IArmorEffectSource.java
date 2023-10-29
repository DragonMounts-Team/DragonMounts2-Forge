package net.dragonmounts3.api;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface IArmorEffectSource {
    void attachEffect(Object2IntMap<IArmorEffect> map, PlayerEntity player, ItemStack stack);
}
