package net.dragonmounts3.inventory;

import net.dragonmounts3.data.tags.DMItemTags;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
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
        public Saddle(IInventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return stack.getItem() instanceof SaddleItem;
        }

        @Override
        public void setChanged() {
            if (this.container instanceof DragonInventory) {
                ((DragonInventory) this.container).applySaddleChange(this.getItem());
            } else {
                this.container.setChanged();
            }
        }
    }

    public static class DragonArmor extends LimitedSlot {
        public DragonArmor(IInventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return stack.getItem().is(DMItemTags.DRAGON_ARMOR);
        }

        @Override
        public void setChanged() {
            if (this.container instanceof DragonInventory) {
                ((DragonInventory) this.container).applyArmorChange(this.getItem());
            } else {
                this.container.setChanged();
            }
        }
    }

    public static class SingleWoodenChest extends LimitedSlot {
        public SingleWoodenChest(IInventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return stack.getItem().is(Tags.Items.CHESTS_WOODEN);
        }

        @Override
        public void setChanged() {
            if (this.container instanceof DragonInventory) {
                ((DragonInventory) this.container).applyChestChange(this.getItem());
            } else {
                this.container.setChanged();
            }
        }
    }
}
