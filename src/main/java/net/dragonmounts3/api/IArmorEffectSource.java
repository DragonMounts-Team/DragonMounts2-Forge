package net.dragonmounts3.api;

import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface IArmorEffectSource {
    void attachEffect(Reference2IntMap<IArmorEffect> map, PlayerEntity player, ItemStack stack);
}
