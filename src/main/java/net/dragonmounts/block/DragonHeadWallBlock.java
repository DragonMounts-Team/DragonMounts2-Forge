package net.dragonmounts.block;

import net.dragonmounts.registry.DragonVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import java.util.EnumMap;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class DragonHeadWallBlock extends AbstractDragonHeadBlock {
    private static final EnumMap<Direction, VoxelShape> AABBS = new EnumMap<>(Direction.class);

    static {
        AABBS.put(Direction.NORTH, Block.box(4.0D, 4.0D, 8.0D, 12.0D, 12.0D, 16.0D));
        AABBS.put(Direction.SOUTH, Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 8.0D));
        AABBS.put(Direction.EAST, Block.box(0.0D, 4.0D, 4.0D, 8.0D, 12.0D, 12.0D));
        AABBS.put(Direction.WEST, Block.box(8.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D));
    }

    public DragonHeadWallBlock(DragonVariant variant, Properties props) {
        super(variant, props, true);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public float getYRotation(BlockState state) {
        return state.getValue(HORIZONTAL_FACING).toYRot();
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
        return AABBS.get(state.getValue(HORIZONTAL_FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getClickedPos();
        IBlockReader level = context.getLevel();
        for (Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal() && !level.getBlockState(pos.relative(direction)).canBeReplaced(context)) {
                return this.defaultBlockState().setValue(HORIZONTAL_FACING, direction.getOpposite());
            }
        }
        return null;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }
}
