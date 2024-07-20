package net.dragonmounts.block;

import net.dragonmounts.registry.DragonVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

import static net.minecraft.state.properties.BlockStateProperties.ROTATION_16;

public class DragonHeadBlock extends AbstractDragonHeadBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);

    public DragonHeadBlock(DragonVariant variant, Properties props) {
        super(variant, props, false);
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION_16, 0));
    }

    @Override
    public float getYRotation(BlockState state) {
        return 180F + 22.5F * state.getValue(ROTATION_16);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOcclusionShape(BlockState state, IBlockReader level, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(ROTATION_16, MathHelper.floor(context.getRotation() / 22.5F + 0.5F) & 15);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(ROTATION_16, rotation.rotate(state.getValue(ROTATION_16), 16));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(ROTATION_16, mirror.mirror(state.getValue(ROTATION_16), 16));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ROTATION_16);
    }
}
