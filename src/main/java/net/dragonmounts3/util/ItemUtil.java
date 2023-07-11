package net.dragonmounts3.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ItemUtil {
    public static CompoundNBT saveItem(ItemStack stack, byte index) {
        CompoundNBT compound = new CompoundNBT();
        compound.putByte("Slot", index);
        stack.save(compound);
        return compound;
    }
}
