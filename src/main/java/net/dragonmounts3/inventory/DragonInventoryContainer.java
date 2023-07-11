package net.dragonmounts3.inventory;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.init.DMContainers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DragonInventoryContainer extends Container {
    @Nullable
    public static DragonInventoryContainer fromPacket(int containerId, PlayerInventory playerInventory, PacketBuffer extraData) {
        Entity entity = extraData == null ? null : playerInventory.player.level.getEntity(extraData.readVarInt());
        if (entity instanceof TameableDragonEntity) {
            TameableDragonEntity dragon = (TameableDragonEntity) entity;
            return new DragonInventoryContainer(containerId, playerInventory, dragon.getInventory(), dragon);
        }
        return null;
    }

    private final DragonInventory inventory;
    public final TameableDragonEntity dragon;

    public DragonInventoryContainer(int containerId, PlayerInventory playerInventory, DragonInventory dragonInventory, TameableDragonEntity dragon) {
        super(DMContainers.DRAGON_INVENTORY.get(), containerId);
        this.dragon = dragon;
        this.inventory = dragonInventory;
        PlayerEntity player = playerInventory.player;
        dragonInventory.startOpen(player);
        this.addSlot(new LimitedSlot.Saddle(dragonInventory, DragonInventory.SLOT_SADDLE_INDEX, 8, 18));
        this.addSlot(new LimitedSlot.DragonArmor(dragonInventory, DragonInventory.SLOT_ARMOR_INDEX, 8, 36));
        this.addSlot(new LimitedSlot.SingleWoodenChest(dragonInventory, DragonInventory.SLOT_CHEST_INDEX, 8, 54));
        for (int i = 0; i < 3; ++i) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new DragonChestSlot(dragonInventory, dragon, k + i * 9, 8 + k * 18, 76 + i * 18));
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
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();
            int dragonInventorySize = this.inventory.getContainerSize();
            if (index < dragonInventorySize) {
                if (!this.moveItemStackTo(stack, dragonInventorySize, this.slots.size(), true)) {
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
            } else if (!this.moveItemStackTo(stack, 3, dragonInventorySize, false)) {
                int playerInventorySize = dragonInventorySize + 27;
                int playerHotbarSize = playerInventorySize + 9;
                if (index >= playerInventorySize && index < playerHotbarSize) {
                    if (!this.moveItemStackTo(stack, dragonInventorySize, playerInventorySize, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < playerInventorySize) {
                    if (!this.moveItemStackTo(stack, playerInventorySize, playerHotbarSize, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(stack, playerInventorySize, playerInventorySize, false)) {
                    return ItemStack.EMPTY;
                }

                return ItemStack.EMPTY;
            }
            if (result.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return result;
    }

    @Override
    public void removed(@Nonnull PlayerEntity player) {
        super.removed(player);
        this.inventory.stopOpen(player);
    }

    public DragonInventory getInventory() {
        return this.inventory;
    }

    @Override
    public boolean stillValid(@Nonnull PlayerEntity player) {
        return this.inventory.stillValid(player);
    }
}
