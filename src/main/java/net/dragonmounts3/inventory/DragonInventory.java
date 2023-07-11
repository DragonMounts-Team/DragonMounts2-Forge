package net.dragonmounts3.inventory;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.ref.WeakReference;

import static net.dragonmounts3.util.ItemUtil.saveItem;

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

    public void setSaddle(@Nonnull ItemStack stack) {
        if (!stack.isEmpty()) {
            stack.setCount(1);
        }
        this.saddle = applySaddleChange(stack);
    }

    @Nonnull
    public ItemStack getSaddle() {
        return this.saddle;
    }

    public void setArmor(@Nonnull ItemStack stack) {
        if (!stack.isEmpty()) {
            stack.setCount(1);
        }
        this.armor = applyArmorChange(stack);
    }

    @Nonnull
    public ItemStack getArmor() {
        return this.armor;
    }

    public void setChest(@Nonnull ItemStack stack) {
        if (!stack.isEmpty()) {
            stack.setCount(1);
        }
        this.chest = applyChestChange(stack);
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
        ItemStack stack;
        if (count <= 0) return ItemStack.EMPTY;
        switch (index) {
            case SLOT_SADDLE_INDEX:
                if (this.saddle.isEmpty()) return ItemStack.EMPTY;
                count = Math.min(count, this.saddle.getCount());
                stack = this.saddle.copy();
                stack.setCount(count);
                this.saddle.shrink(count);
                this.applySaddleChange(this.saddle);
                break;
            case SLOT_ARMOR_INDEX:
                if (this.armor.isEmpty()) return ItemStack.EMPTY;
                count = Math.min(count, this.armor.getCount());
                stack = this.armor.copy();
                stack.setCount(count);
                this.armor.shrink(count);
                this.applyArmorChange(this.armor);
                break;
            case SLOT_CHEST_INDEX:
                if (this.chest.isEmpty()) return ItemStack.EMPTY;
                count = Math.min(count, this.chest.getCount());
                stack = this.chest.copy();
                stack.setCount(count);
                this.chest.shrink(count);
                this.applyChestChange(this.chest);
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
                if (this.saddle.isEmpty()) return ItemStack.EMPTY;
                stack = this.saddle;
                this.saddle = ItemStack.EMPTY;
                break;
            case SLOT_ARMOR_INDEX:
                if (this.armor.isEmpty()) return ItemStack.EMPTY;
                stack = this.armor;
                this.armor = ItemStack.EMPTY;
                break;
            case SLOT_CHEST_INDEX:
                if (this.chest.isEmpty()) return ItemStack.EMPTY;
                stack = this.chest;
                this.chest = ItemStack.EMPTY;
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
                this.saddle = this.applySaddleChange(stack);
                break;
            case SLOT_ARMOR_INDEX:
                this.armor = this.applyArmorChange(stack);
                break;
            case SLOT_CHEST_INDEX:
                this.chest = this.applyChestChange(stack);
                break;
            default:
                if (index < 0 || index >= CHEST_SIZE) return;
                this.items.set(index, stack);
                this.setChanged();
        }
    }

    @Override
    public void setChanged() {
        TameableDragonEntity dragon = this.dragon.get();
        if (dragon != null) {
            dragon.inventoryChanged();
        }
    }

    public ItemStack applySaddleChange(ItemStack stack) {
        TameableDragonEntity dragon = this.dragon.get();
        if (dragon != null) {
            dragon.applySaddleChange(stack);
        }
        return stack;
    }

    public ItemStack applyArmorChange(ItemStack stack) {
        TameableDragonEntity dragon = this.dragon.get();
        if (dragon != null) {
            dragon.applyArmorChange(stack);
        }
        return stack;
    }

    public ItemStack applyChestChange(ItemStack stack) {
        TameableDragonEntity dragon = this.dragon.get();
        if (dragon != null) {
            dragon.applyChestChange(stack);
        }
        return stack;
    }

    @Override
    public boolean stillValid(@Nonnull PlayerEntity player) {
        TameableDragonEntity dragon = this.dragon.get();
        if (dragon == null || !dragon.isAlive()) return false;
        return player.distanceToSqr(dragon) <= 64.0D;//8 blocks
    }

    @Override
    public void clearContent() {
        this.items.clear();
        this.saddle = this.applySaddleChange(ItemStack.EMPTY);
        this.armor = this.applyArmorChange(ItemStack.EMPTY);
        this.chest = this.applyChestChange(ItemStack.EMPTY);
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
        if (!this.saddle.isEmpty()) {
            list.add(saveItem(this.saddle, (byte) SLOT_SADDLE_INDEX));
        }
        if (!this.armor.isEmpty()) {
            list.add(saveItem(this.armor, (byte) SLOT_ARMOR_INDEX));
        }
        if (!this.chest.isEmpty()) {
            list.add(saveItem(this.chest, (byte) SLOT_CHEST_INDEX));
        }
        return list;
    }
}
