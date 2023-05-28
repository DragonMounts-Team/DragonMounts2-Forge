package net.dragonmounts3.inventory;

import net.dragonmounts3.init.DMContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * @see net.minecraft.inventory.container.HorseInventoryContainer
 */
public class DragonInventoryContainer extends Container {
    private final IInventory container;

    public DragonInventoryContainer(int containerId, PlayerInventory playerInventory, PacketBuffer extraData) {
        this(containerId, playerInventory, (IInventory) Objects.requireNonNull(playerInventory.player.level.getBlockEntity(extraData.readBlockPos())));
    }

    public DragonInventoryContainer(int containerId, PlayerInventory playerInventory, IInventory container) {
        super(DMContainers.DRAGON_CORE.get(), containerId);
        this.container = container;
        PlayerEntity player = playerInventory.player;
        container.startOpen(player);
        this.addSlot(new FurnaceResultSlot(player, container, 0, 80, 36));
        for (int i = 0; i < 3; ++i) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }
        for (int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 142));
        }
    }

    public IInventory getContainer() {
        return this.container;
    }

    @Override
    public boolean stillValid(@Nonnull PlayerEntity player) {
        return this.container.stillValid(player);
    }
}