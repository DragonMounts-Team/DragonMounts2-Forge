package net.dragonmounts.inventory;

import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.init.DMContainers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.dragonmounts.inventory.DragonInventory.*;

public class DragonInventoryContainer extends Container {
    protected static final int PLAYER_INVENTORY_SIZE = INVENTORY_SIZE + 27;
    protected static final int PLAYER_HOTBAR_SIZE = PLAYER_INVENTORY_SIZE + 9;

    @Nullable
    public static DragonInventoryContainer fromPacket(int containerId, PlayerInventory playerInventory, PacketBuffer extraData) {
        Entity entity = extraData == null ? null : playerInventory.player.level.getEntity(extraData.readVarInt());
        if (entity instanceof TameableDragonEntity) {
            TameableDragonEntity dragon = (TameableDragonEntity) entity;
            return new DragonInventoryContainer(containerId, playerInventory, dragon.inventory, dragon);
        }
        return null;
    }

    public final DragonInventory inventory;
    public final TameableDragonEntity dragon;

    public DragonInventoryContainer(int containerId, PlayerInventory playerInventory, DragonInventory dragonInventory, TameableDragonEntity dragon) {
        super(DMContainers.DRAGON_INVENTORY.get(), containerId);
        this.dragon = dragon;
        this.inventory = dragonInventory;
        PlayerEntity player = playerInventory.player;
        dragonInventory.startOpen(player);
        this.addSlot(new LimitedSlot.Saddle(dragonInventory, SLOT_SADDLE_INDEX, 8, 18));
        this.addSlot(new LimitedSlot.DragonArmor(dragonInventory, SLOT_ARMOR_INDEX, 8, 36));
        this.addSlot(new LimitedSlot.SingleWoodenChest(dragonInventory, SLOT_CHEST_INDEX, 8, 54));
        for (int i = 0; i < 3; ++i) {
            for (int k = 3; k < 12; ++k) {
                this.addSlot(new DragonChestSlot(dragonInventory, dragon, k + i * 9, k * 18 - 46, 76 + i * 18));
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + i * 9 + 9, 8 + k * 18, 142 + i * 18));
            }
        }
        for (int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 200));
        }
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull PlayerEntity player, int index) {
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem(), copy = stack.copy();
            if (index < INVENTORY_SIZE) {
                if (!this.moveItemStackTo(stack, INVENTORY_SIZE, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(2).mayPlace(stack) && !this.getSlot(2).hasItem()) {
                if (!this.moveItemStackTo(stack, 2, 3, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).mayPlace(stack) && !this.getSlot(1).hasItem()) {
                if (!this.moveItemStackTo(stack, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).mayPlace(stack) && !this.getSlot(0).hasItem()) {
                if (!this.moveItemStackTo(stack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.dragon.hasChest() || !this.moveItemStackTo(stack, 3, INVENTORY_SIZE, false)) {
                if (index >= PLAYER_INVENTORY_SIZE) {
                    this.moveItemStackTo(stack, INVENTORY_SIZE, PLAYER_INVENTORY_SIZE, false);
                } else {
                    this.moveItemStackTo(stack, PLAYER_INVENTORY_SIZE, PLAYER_HOTBAR_SIZE, false);
                }
                return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            return copy;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void removed(@Nonnull PlayerEntity player) {
        super.removed(player);
        this.inventory.stopOpen(player);
    }

    @Override
    public boolean stillValid(@Nonnull PlayerEntity player) {
        return this.inventory.stillValid(player);
    }
}
