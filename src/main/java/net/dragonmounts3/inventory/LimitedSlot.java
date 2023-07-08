package net.dragonmounts3.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class LimitedSlot extends Slot {
    @FunctionalInterface
    public interface ICondition {
        boolean mayPlace(@Nonnull ItemStack stack);
    }

    public final int size;
    public final ICondition condition;

    public LimitedSlot(IInventory inventory, int index, int x, int y, int size, ICondition condition) {
        super(inventory, index, x, y);
        this.size = size;
        this.condition = condition;
    }

    @Override
    public int getMaxStackSize() {
        return this.size;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return this.condition.mayPlace(stack);
    }
}
