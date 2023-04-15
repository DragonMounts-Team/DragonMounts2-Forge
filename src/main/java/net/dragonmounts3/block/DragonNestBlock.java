package net.dragonmounts3.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class DragonNestBlock extends Block {
    public DragonNestBlock() {
        super(Properties.of(Material.STONE).strength(1.0F).sound(SoundType.WOOD));
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 30;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 77;
    }
}