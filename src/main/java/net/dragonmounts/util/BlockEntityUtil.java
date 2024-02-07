package net.dragonmounts.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class BlockEntityUtil {
    public static IInventory getInventory(PlayerEntity player, PacketBuffer buffer, int size) {
        if (buffer != null) {
            TileEntity entity = player.level.getBlockEntity(buffer.readBlockPos());
            if (entity instanceof IInventory) return (IInventory) entity;
        }
        return new Inventory(size);
    }
}
