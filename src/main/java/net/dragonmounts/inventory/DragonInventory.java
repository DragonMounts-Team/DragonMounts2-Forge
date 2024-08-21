package net.dragonmounts.inventory;

import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.util.ItemStackArrays;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * @see net.minecraft.inventory.Inventory
 */
public class DragonInventory implements IInventory, INamedContainerProvider {
    public static final String DATA_PARAMETER_KEY = "Items";
    public static final int SLOT_SADDLE_INDEX = 0;
    public static final int SLOT_ARMOR_INDEX = 1;
    public static final int SLOT_CHEST_INDEX = 2;
    public static final int INVENTORY_SIZE = 30;
    public final TameableDragonEntity dragon;
    protected final ItemStack[] stacks;

    public DragonInventory(TameableDragonEntity dragon) {
        Arrays.fill(this.stacks = new ItemStack[INVENTORY_SIZE], ItemStack.EMPTY);
        this.dragon = dragon;
    }

    @Override
    public final int getContainerSize() {
        return this.dragon.hasChest() ? this.stacks.length : 3;
    }

    @Override
    public boolean isEmpty() {
        ItemStack[] stacks = this.stacks;
        for (int i = 3, n = stacks.length; i < n; ++i) {
            if (!stacks[i].isEmpty()) return false;
        }
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getItem(int index) {
        switch (index) {
            case SLOT_SADDLE_INDEX:
                return this.dragon.getSaddle();
            case SLOT_ARMOR_INDEX:
                return this.dragon.getArmor();
            case SLOT_CHEST_INDEX:
                return this.dragon.getChest();
            default:
                return index > 2 && index < this.stacks.length ? this.stacks[index] : ItemStack.EMPTY;
        }
    }

    @Nonnull
    @Override
    public ItemStack removeItem(int index, int count) {
        if (count <= 0) return ItemStack.EMPTY;
        ItemStack old, neo;
        switch (index) {
            case SLOT_SADDLE_INDEX:
                old = this.dragon.getSaddle();
                if (old.isEmpty()) return ItemStack.EMPTY;
                count = Math.min(count, old.getCount());
                neo = old.copy();
                neo.setCount(count);
                old.shrink(count);
                this.dragon.setSaddle(old, true);
                return neo;
            case SLOT_ARMOR_INDEX:
                old = this.dragon.getArmor();
                if (old.isEmpty()) return ItemStack.EMPTY;
                count = Math.min(count, old.getCount());
                neo = old.copy();
                neo.setCount(count);
                old.shrink(count);
                this.dragon.setArmor(old, true);
                return neo;
            case SLOT_CHEST_INDEX:
                old = this.dragon.getChest();
                if (old.isEmpty()) return ItemStack.EMPTY;
                count = Math.min(count, old.getCount());
                neo = old.copy();
                neo.setCount(count);
                old.shrink(count);
                this.dragon.setChest(old, true);
                return neo;
            default:
                neo = ItemStackArrays.split(this.stacks, index, count);
                if (!neo.isEmpty()) {
                    this.setChanged();
                }
                return neo;
        }
    }

    @Nonnull
    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack stack;
        switch (index) {
            case SLOT_SADDLE_INDEX:
                stack = this.dragon.getSaddle();
                if (stack.isEmpty()) return ItemStack.EMPTY;
                this.dragon.setSaddle(ItemStack.EMPTY, false);
                return stack;
            case SLOT_ARMOR_INDEX:
                stack = this.dragon.getArmor();
                if (stack.isEmpty()) return ItemStack.EMPTY;
                this.dragon.setSaddle(ItemStack.EMPTY, false);
                return stack;
            case SLOT_CHEST_INDEX:
                stack = this.dragon.getChest();
                if (stack.isEmpty()) return ItemStack.EMPTY;
                this.dragon.setChest(ItemStack.EMPTY, false);
                return stack;
            default:
                return ItemStackArrays.take(this.stacks, index);
        }
    }

    @Override
    public void setItem(int index, @Nonnull ItemStack stack) {
        switch (index) {
            case SLOT_SADDLE_INDEX:
                this.dragon.setSaddle(stack, true);
                return;
            case SLOT_ARMOR_INDEX:
                this.dragon.setArmor(stack, true);
                return;
            case SLOT_CHEST_INDEX:
                this.dragon.setChest(stack, true);
                return;
            default: if (index >= 0 && index < this.stacks.length) {
                if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
                    stack.setCount(this.getMaxStackSize());
                }
                this.stacks[index] = stack;
                this.setChanged();
            }
        }
    }

    public boolean setItemAfterChecked(int index, @Nonnull ItemStack stack) {
        Item item = stack.getItem();
        switch (index) {
            case SLOT_SADDLE_INDEX:
                if (item == Items.AIR || LimitedSlot.Saddle.mayPlace(item)) {
                    this.dragon.setSaddle(stack, true);
                    return true;
                }
                return false;
            case SLOT_ARMOR_INDEX:
                if (item == Items.AIR || LimitedSlot.DragonArmor.mayPlace(item)) {
                    this.dragon.setArmor(stack, true);
                    return true;
                }
                return false;
            case SLOT_CHEST_INDEX:
                if (item == Items.AIR || LimitedSlot.SingleWoodenChest.mayPlace(item)) {
                    this.dragon.setChest(stack, true);
                    return true;
                }
                return false;
            default:
                if (index < 0 || index >= this.stacks.length || !this.dragon.hasChest()) return false;
                if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
                    stack.setCount(this.getMaxStackSize());
                }
                this.stacks[index] = stack;
                this.setChanged();
                return true;
        }
    }

    @Override
    public void setChanged() {
        this.dragon.inventoryChanged();
    }

    @Override
    public boolean stillValid(@Nonnull PlayerEntity player) {
        return this.dragon.isAlive() && player.distanceToSqr(this.dragon) <= 64.0D;//8 blocks
    }

    @Override
    public void clearContent() {
        TameableDragonEntity dragon = this.dragon;
        ItemStack empty = ItemStack.EMPTY;
        Arrays.fill(this.stacks, empty);
        dragon.setSaddle(empty, true);
        dragon.setArmor(empty, true);
        dragon.setChest(empty, true);
        this.setChanged();
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return this.dragon.getDisplayName();
    }

    @Nullable
    @Override
    public DragonInventoryContainer createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return new DragonInventoryContainer(id, inventory, this, this.dragon);
    }

    public void dropContents(boolean keepEquipments, double offsetY) {
        TameableDragonEntity dragon = this.dragon;
        World level = dragon.level;
        double x = dragon.getX();
        double y = dragon.getY() + offsetY;
        double z = dragon.getZ();
        if (!keepEquipments) {
            InventoryHelper.dropItemStack(level, x, y, z, dragon.getSaddle());
            InventoryHelper.dropItemStack(level, x, y, z, dragon.getArmor());
            InventoryHelper.dropItemStack(level, x, y, z, dragon.getChest());
        }
        ItemStack[] stacks = this.stacks;
        for (int i = 3, n = stacks.length; i < n; ++i) {
            InventoryHelper.dropItemStack(level, x, y, z, stacks[i]);
        }
    }

    public void fromTag(ListNBT list) {
        TameableDragonEntity dragon = this.dragon;
        ItemStack[] stacks = this.stacks;
        ItemStack empty = ItemStack.EMPTY;
        Arrays.fill(stacks, empty);
        boolean saddle = false;
        boolean armor = false;
        boolean chest = false;
        for (int i = 0, j, n = list.size(), m = stacks.length; i < n; ++i) {
            CompoundNBT tag = list.getCompound(i);
            switch (j = tag.getByte("Slot") & 255) {
                case SLOT_SADDLE_INDEX:
                    dragon.setSaddle(ItemStack.of(tag), saddle = true);
                    break;
                case SLOT_ARMOR_INDEX:
                    dragon.setArmor(ItemStack.of(tag), armor = true);
                    break;
                case SLOT_CHEST_INDEX:
                    dragon.setChest(ItemStack.of(tag), chest = true);
                    break;
                default: if (j < m) {
                    ItemStack stack = stacks[j] = ItemStack.of(tag);
                    if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
                        stack.setCount(this.getMaxStackSize());
                    }
                }
            }
        }
        if (!saddle) {
            dragon.setSaddle(empty, true);
        }
        if (!armor) {
            dragon.setArmor(empty, true);
        }
        if (!chest) {
            dragon.setChest(empty, true);
        }
        this.setChanged();
    }

    public ListNBT createTag() {
        TameableDragonEntity dragon = this.dragon;
        ItemStack[] stacks = this.stacks;
        stacks[SLOT_SADDLE_INDEX] = dragon.getSaddle();
        stacks[SLOT_ARMOR_INDEX] = dragon.getArmor();
        stacks[SLOT_CHEST_INDEX] = dragon.getChest();
        return ItemStackArrays.writeList(new ListNBT(), stacks);
    }
}
