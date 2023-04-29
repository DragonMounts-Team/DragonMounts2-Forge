package net.dragonmounts3.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class BlockEntityUtil {
    public static IInventory getInventory(PlayerEntity player, PacketBuffer extraData, int size) {
        if (extraData != null) {
            TileEntity blockEntity = player.level.getBlockEntity(extraData.readBlockPos());
            if (blockEntity instanceof IInventory) {
                return (IInventory) blockEntity;
            }
        }
        return new Inventory(size);
    }
}
