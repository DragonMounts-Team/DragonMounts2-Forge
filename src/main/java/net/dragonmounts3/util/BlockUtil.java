package net.dragonmounts3.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;

public class BlockUtil {
    public static boolean isAir(BlockState state) {
        return state.getMaterial() == Material.AIR;
    }
}
