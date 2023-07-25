package net.dragonmounts3.inventory;

import net.dragonmounts3.item.DragonArmorItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SaddleItem;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;

public abstract class LimitedSlot extends Slot {
    public LimitedSlot(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public abstract boolean mayPlace(@Nonnull ItemStack stack);


    public static class Reject extends LimitedSlot {
        public Reject(IInventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return false;
        }
    }

    public static class Saddle extends LimitedSlot {
        public static boolean mayPlace(@Nonnull Item item) {
            return item instanceof SaddleItem;
        }

        public Saddle(IInventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return Saddle.mayPlace(stack.getItem());
        }

        @Override
        public void setChanged() {
            if (this.container instanceof DragonInventory) {
                ((DragonInventory) this.container).dragon.setSaddle(this.getItem(), false);
            } else {
                this.container.setChanged();
            }
        }
    }

    public static class DragonArmor extends LimitedSlot {
        public static boolean mayPlace(@Nonnull Item item) {
            return item instanceof DragonArmorItem;
        }

        public DragonArmor(IInventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return DragonArmor.mayPlace(stack.getItem());
        }

        @Override
        public void setChanged() {
            if (this.container instanceof DragonInventory) {
                ((DragonInventory) this.container).dragon.setArmor(this.getItem(), false);
            } else {
                this.container.setChanged();
            }
        }
    }

    public static class SingleWoodenChest extends LimitedSlot {
        public static boolean mayPlace(@Nonnull Item item) {
            return Tags.Items.CHESTS_WOODEN.contains(item);
        }

        public SingleWoodenChest(IInventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return SingleWoodenChest.mayPlace(stack.getItem());
        }

        @Override
        public void setChanged() {
            if (this.container instanceof DragonInventory) {
                ((DragonInventory) this.container).dragon.setChest(this.getItem(), false);
            } else {
                this.container.setChanged();
            }
        }
    }
}
