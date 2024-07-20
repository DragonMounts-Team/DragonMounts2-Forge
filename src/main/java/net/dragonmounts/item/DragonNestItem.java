package net.dragonmounts.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;

import javax.annotation.Nullable;

public class DragonNestItem extends BlockItem {
    public DragonNestItem(Block block, Properties props) {
        super(block, props);
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable IRecipeType<?> recipeType) {
        return 1000;
    }
}
