package net.dragonmounts3.inventory;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.ref.WeakReference;

/**
 * @see net.minecraft.inventory.Inventory
 */
public class DragonInventory implements IInventory, INamedContainerProvider {
    public static final int CHEST_SIZE = 27;
    public static final int SLOT_SADDLE_INDEX = CHEST_SIZE;
    public static final int SLOT_ARMOR_INDEX = CHEST_SIZE + 1;
    public static final int SLOT_CHEST_INDEX = CHEST_SIZE + 2;

    public final WeakReference<TameableDragonEntity> dragon;
    protected final NonNullList<ItemStack> items = NonNullList.withSize(CHEST_SIZE, ItemStack.EMPTY);

    protected ItemStack saddle = ItemStack.EMPTY;
    protected ItemStack armor = ItemStack.EMPTY;
    protected ItemStack chest = ItemStack.EMPTY;

    public DragonInventory(TameableDragonEntity dragon) {
        this.dragon = new WeakReference<>(dragon);
    }

    public void setSaddle(@Nullable ItemStack stack) {
        this.saddle = stack == null ? ItemStack.EMPTY : stack;
    }

    @Nonnull
    public ItemStack getSaddle() {
        return this.saddle;
    }

    public void setArmor(@Nullable ItemStack stack) {
        this.armor = stack == null ? ItemStack.EMPTY : stack;
    }

    @Nonnull
    public ItemStack getArmor() {
        return this.armor;
    }

    public void setChest(@Nullable ItemStack stack) {
        this.chest = stack == null ? ItemStack.EMPTY : stack;
    }

    @Nonnull
    public ItemStack getChest() {
        return this.chest;
    }

    @Override
    public int getContainerSize() {
        TameableDragonEntity dragon = this.dragon.get();
        return dragon != null && dragon.hasChest() ? CHEST_SIZE + 3 : 3;
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
                return this.saddle;
            case SLOT_ARMOR_INDEX:
                return this.armor;
            case SLOT_CHEST_INDEX:
                return this.chest;
            default:
                return index >= 0 && index < CHEST_SIZE ? this.items.get(index) : ItemStack.EMPTY;
        }
    }

    @Nonnull
    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack stack = ItemStackHelper.removeItem(this.items, index, count);
        if (!stack.isEmpty()) {
            this.setChanged();
        }
        return stack;
    }

    @Nonnull
    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ItemStackHelper.takeItem(this.items, index);
    }

    @Override
    public void setItem(int index, @Nonnull ItemStack stack) {
        switch (index) {
            case SLOT_SADDLE_INDEX:
                this.saddle = stack;
                break;
            case SLOT_ARMOR_INDEX:
                this.armor = stack;
                break;
            case SLOT_CHEST_INDEX:
                this.chest = stack;
                break;
            default:
                if (index < 0 || index >= CHEST_SIZE) return;
                this.items.set(index, stack);
                break;
        }
        if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
        this.setChanged();
    }

    @Override
    public void setChanged() {
        TameableDragonEntity dragon = this.dragon.get();
        if (dragon != null) {
            dragon.containerChanged();
        }
    }

    @Override
    public boolean stillValid(@Nonnull PlayerEntity player) {
        TameableDragonEntity dragon = this.dragon.get();
        if (dragon == null || !dragon.isAlive()) return false;
        return player.distanceToSqr(dragon) <= 64.0D;//8 blocks
    }

    @Override
    public void clearContent() {
        this.saddle = ItemStack.EMPTY;
        this.armor = ItemStack.EMPTY;
        this.chest = ItemStack.EMPTY;
        this.items.clear();
        this.setChanged();
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        TameableDragonEntity dragon = this.dragon.get();
        return dragon == null ? new StringTextComponent("") : dragon.getDisplayName();
    }

    @Nullable
    @Override
    public DragonInventoryContainer createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        TameableDragonEntity dragon = this.dragon.get();
        return dragon == null ? null : new DragonInventoryContainer(id, inventory, this, dragon);
    }
}
