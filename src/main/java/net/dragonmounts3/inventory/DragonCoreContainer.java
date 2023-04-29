package net.dragonmounts3.inventory;

import net.dragonmounts3.inits.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

import static net.dragonmounts3.util.BlockEntityUtil.getInventory;

/**
 * @see net.minecraft.inventory.container.ShulkerBoxContainer
 */
public class DragonCoreContainer extends Container {
    private final IInventory container;

    public DragonCoreContainer(int containerId, PlayerInventory playerInventory, PacketBuffer extraData) {
        this(containerId, playerInventory, getInventory(playerInventory.player, extraData, 1));
    }

    public DragonCoreContainer(int containerId, PlayerInventory playerInventory, IInventory container) {
        super(ModContainers.DRAGON_CORE.get(), containerId);
        this.container = container;
        PlayerEntity player = playerInventory.player;
        container.startOpen(player);
        this.addSlot(new FurnaceResultSlot(player, container, 0, 80, 35));
        for (int i = 0; i < 3; ++i) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }
        for (int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 142));
        }
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (!this.moveItemStackTo(itemstack1, 1, 37, true)) {
                return ItemStack.EMPTY;
            }
        }
        return itemstack;
    }

    @Override
    public void removed(@Nonnull PlayerEntity player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    @Override
    public boolean stillValid(@Nonnull PlayerEntity player) {
        return this.container.stillValid(player);
    }

    public IInventory getContainer() {
        return this.container;
    }
}