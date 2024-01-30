package net.dragonmounts.inventory;

import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
    protected final NonNullList<ItemStack> items = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);

    public DragonInventory(TameableDragonEntity dragon) {
        this.dragon = dragon;
    }

    @Override
    public final int getContainerSize() {
        return this.dragon.hasChest() ? INVENTORY_SIZE : 3;
    }

    @Override
    public boolean isEmpty() {
        return this.items.stream().allMatch(ItemStack::isEmpty);
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
                return index >= 0 && index < this.items.size() ? this.items.get(index) : ItemStack.EMPTY;
        }
    }

    @Nonnull
    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack stack;
        if (count <= 0) return ItemStack.EMPTY;
        ItemStack original;
        switch (index) {
            case SLOT_SADDLE_INDEX:
                original = this.dragon.getSaddle();
                if (original.isEmpty()) return ItemStack.EMPTY;
                count = Math.min(count, original.getCount());
                stack = original.copy();
                stack.setCount(count);
                original.shrink(count);
                this.dragon.setSaddle(original, true);
                break;
            case SLOT_ARMOR_INDEX:
                original = this.dragon.getArmor();
                if (original.isEmpty()) return ItemStack.EMPTY;
                count = Math.min(count, original.getCount());
                stack = original.copy();
                stack.setCount(count);
                original.shrink(count);
                this.dragon.setArmor(original, true);
                break;
            case SLOT_CHEST_INDEX:
                original = this.dragon.getChest();
                if (original.isEmpty()) return ItemStack.EMPTY;
                count = Math.min(count, original.getCount());
                stack = original.copy();
                stack.setCount(count);
                original.shrink(count);
                this.dragon.setChest(original, true);
                break;
            default:
                stack = ItemStackHelper.removeItem(this.items, index, count);
                if (!stack.isEmpty()) {
                    this.setChanged();
                }
        }
        return stack;
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
                break;
            case SLOT_ARMOR_INDEX:
                stack = this.dragon.getArmor();
                if (stack.isEmpty()) return ItemStack.EMPTY;
                this.dragon.setSaddle(ItemStack.EMPTY, false);
                break;
            case SLOT_CHEST_INDEX:
                stack = this.dragon.getChest();
                if (stack.isEmpty()) return ItemStack.EMPTY;
                this.dragon.setChest(ItemStack.EMPTY, false);
                break;
            default:
                stack = ItemStackHelper.takeItem(this.items, index);
        }
        return stack;
    }

    @Override
    public void setItem(int index, @Nonnull ItemStack stack) {
        switch (index) {
            case SLOT_SADDLE_INDEX:
                this.dragon.setSaddle(stack, true);
                break;
            case SLOT_ARMOR_INDEX:
                this.dragon.setArmor(stack, true);
                break;
            case SLOT_CHEST_INDEX:
                this.dragon.setChest(stack, true);
                break;
            default:
                if (index < 0 || index >= this.items.size()) return;
                if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
                    stack.setCount(this.getMaxStackSize());
                }
                this.items.set(index, stack);
                this.setChanged();
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
                if (index < 0 || index >= this.items.size() || !this.dragon.hasChest()) return false;
                if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
                    stack.setCount(this.getMaxStackSize());
                }
                this.items.set(index, stack);
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
        if (!this.dragon.isAlive()) return false;
        return player.distanceToSqr(this.dragon) <= 64.0D;//8 blocks
    }

    @Override
    public void clearContent() {
        this.items.clear();
        this.dragon.setSaddle(ItemStack.EMPTY, true);
        this.dragon.setArmor(ItemStack.EMPTY, true);
        this.dragon.setChest(ItemStack.EMPTY, true);
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
        World level = this.dragon.level;
        double x = this.dragon.getX();
        double y = this.dragon.getY() + offsetY;
        double z = this.dragon.getZ();
        if (!keepEquipments) {
            InventoryHelper.dropItemStack(level, x, y, z, this.dragon.getSaddle());
            InventoryHelper.dropItemStack(level, x, y, z, this.dragon.getArmor());
            InventoryHelper.dropItemStack(level, x, y, z, this.dragon.getChest());
        }
        for (ItemStack stack : this.items) {
            InventoryHelper.dropItemStack(level, x, y, z, stack);
        }
    }

    public void fromTag(ListNBT list) {
        boolean saddle = false;
        boolean armor = false;
        boolean chest = false;
        for (int i = 3; i < this.items.size(); ++i) {
            this.items.set(i, ItemStack.EMPTY);
        }
        for (int i = 0; i < list.size(); ++i) {
            CompoundNBT compound = list.getCompound(i);
            int j = compound.getByte("Slot") & 255;
            saddle |= j == SLOT_SADDLE_INDEX;
            armor |= j == SLOT_ARMOR_INDEX;
            chest |= j == SLOT_CHEST_INDEX;
            this.setItem(j, ItemStack.of(compound));
        }
        if (!saddle) {
            this.dragon.setSaddle(ItemStack.EMPTY, true);
        }
        if (!armor) {
            this.dragon.setArmor(ItemStack.EMPTY, true);
        }
        if (!chest) {
            this.dragon.setChest(ItemStack.EMPTY, true);
        }
    }

    public ListNBT createTag() {
        ListNBT list = new ListNBT();
        for (int i = 0; i < INVENTORY_SIZE; ++i) {
            ItemStack stack = this.getItem(i);
            if (!stack.isEmpty()) {
                CompoundNBT compound = new CompoundNBT();
                compound.putByte("Slot", (byte) i);
                list.add(stack.save(compound));
            }
        }
        return list;
    }
}
