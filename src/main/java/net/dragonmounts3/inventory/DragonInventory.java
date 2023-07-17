package net.dragonmounts3.inventory;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.dragonmounts3.util.ItemUtil.saveItem;

/**
 * @see net.minecraft.inventory.Inventory
 */
public class DragonInventory implements IInventory, INamedContainerProvider {
    public static final int MAX_CONTAINER_SIZE = 30;
    public static final int CHEST_SIZE = MAX_CONTAINER_SIZE - 3;
    public static final int SLOT_SADDLE_INDEX = CHEST_SIZE;
    public static final int SLOT_ARMOR_INDEX = MAX_CONTAINER_SIZE - 2;
    public static final int SLOT_CHEST_INDEX = MAX_CONTAINER_SIZE - 1;

    public final TameableDragonEntity dragon;
    protected final NonNullList<ItemStack> items = NonNullList.withSize(CHEST_SIZE, ItemStack.EMPTY);

    public DragonInventory(TameableDragonEntity dragon) {
        this.dragon = dragon;
    }

    @Override
    public int getContainerSize() {
        return this.dragon.hasChest() ? MAX_CONTAINER_SIZE : 3;
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
                return index >= 0 && index < CHEST_SIZE ? this.items.get(index) : ItemStack.EMPTY;
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
        if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
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
                if (index < 0 || index >= CHEST_SIZE) return;
                this.items.set(index, stack);
                this.setChanged();
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

    public void dropContents(boolean keepEquipments) {
        World level = this.dragon.level;
        double x = this.dragon.getX();
        double y = this.dragon.getY();
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
        for (int i = 0; i < CHEST_SIZE; ++i) {
            this.setItem(i, ItemStack.EMPTY);
        }
        for (int i = 0; i < list.size(); ++i) {
            CompoundNBT compound = list.getCompound(i);
            int j = compound.getByte("Slot") & 255;
            this.setItem(j, ItemStack.of(compound));
        }
    }

    public ListNBT createTag() {
        ListNBT list = new ListNBT();
        for (int i = 0; i < CHEST_SIZE; ++i) {
            ItemStack stack = this.items.get(i);
            if (!stack.isEmpty()) {
                list.add(saveItem(stack, (byte) i));
            }
        }
        ItemStack stack = this.dragon.getSaddle();
        if (!stack.isEmpty()) {
            list.add(saveItem(stack, (byte) SLOT_SADDLE_INDEX));
        }
        stack = this.dragon.getArmor();
        if (!stack.isEmpty()) {
            list.add(saveItem(stack, (byte) SLOT_ARMOR_INDEX));
        }
        stack = this.dragon.getChest();
        if (!stack.isEmpty()) {
            list.add(saveItem(stack, (byte) SLOT_CHEST_INDEX));
        }
        return list;
    }
}
