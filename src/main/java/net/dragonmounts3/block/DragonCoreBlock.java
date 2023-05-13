package net.dragonmounts3.block;

import net.dragonmounts3.block.entity.DragonCoreBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.ForgeSoundType;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * @see net.minecraft.block.ChestBlock
 * @see net.minecraft.block.ShulkerBoxBlock
 */
public class DragonCoreBlock extends ContainerBlock {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    private static final SoundType SOUND = new ForgeSoundType(1.0F, 1.0F, () -> SoundEvents.CHEST_LOCKED, () -> SoundEvents.STONE_STEP, () -> SoundEvents.BOOK_PUT, () -> SoundEvents.STONE_HIT, () -> SoundEvents.STONE_FALL);

    private static boolean testPositionPredicate(BlockState state, IBlockReader world, BlockPos pos) {
        TileEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof DragonCoreBlockEntity) {
            return ((DragonCoreBlockEntity) blockEntity).isClosed();
        }
        return true;
    }

    public DragonCoreBlock() {
        super(Properties.of(Material.PORTAL, MaterialColor.COLOR_BLACK).strength(2000, 600).sound(SOUND).dynamicShape().noOcclusion().isSuffocating(DragonCoreBlock::testPositionPredicate).isViewBlocking(DragonCoreBlock::testPositionPredicate));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nonnull
    @Override
    public TileEntity newBlockEntity(@Nonnull IBlockReader world) {
        return new DragonCoreBlockEntity();
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderShape(@Nonnull BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType use(@Nonnull BlockState state, @Nonnull World level, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        if (level.isClientSide) {
            return ActionResultType.SUCCESS;
        } else if (player.isSpectator()) {
            return ActionResultType.CONSUME;
        } else {
            TileEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof DragonCoreBlockEntity) {
                DragonCoreBlockEntity dragonCoreBlockEntity = (DragonCoreBlockEntity) blockEntity;
                ShulkerBoxTileEntity.AnimationStatus status = dragonCoreBlockEntity.getAnimationStatus();
                if (status != ShulkerBoxTileEntity.AnimationStatus.CLOSING && (status != ShulkerBoxTileEntity.AnimationStatus.CLOSED || level.noCollision(ShulkerAABBHelper.openBoundingBox(pos, Direction.UP)))) {
                    player.openMenu(dragonCoreBlockEntity);
                    player.awardStat(Stats.OPEN_SHULKER_BOX);
                }
                return ActionResultType.CONSUME;
            } else {
                return ActionResultType.PASS;
            }
        }
    }

    @Override
    public void setPlacedBy(@Nonnull World level, @Nonnull BlockPos pos, @Nonnull BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            TileEntity tileEntity = level.getBlockEntity(pos);
            if (tileEntity instanceof DragonCoreBlockEntity) {
                ((DragonCoreBlockEntity) tileEntity).setCustomName(stack.getHoverName());
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, @Nonnull World level, @Nonnull BlockPos pos, BlockState newState, boolean pIsMoving) {
        if (!state.is(newState.getBlock())) {
            level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 0.3F, level.random.nextFloat() * 0.1F + 0.3F);
            level.playSound(null, pos, SoundEvents.ENDER_EYE_DEATH, SoundCategory.NEUTRAL, 2.0F, level.random.nextFloat() * 0.1F + 0.3F);
            TileEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof DragonCoreBlockEntity) {
                InventoryHelper.dropContents(level, pos, (IInventory) blockEntity);
                level.updateNeighbourForOutputSignal(pos, state.getBlock());
            }
            super.onRemove(state, level, pos, newState, pIsMoving);
        }
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, IBlockReader pLevel, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        TileEntity blockEntity = pLevel.getBlockEntity(pos);
        return blockEntity instanceof DragonCoreBlockEntity ? VoxelShapes.create(((DragonCoreBlockEntity) blockEntity).getBoundingBox()) : VoxelShapes.block();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean hasAnalogOutputSignal(@Nonnull BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getAnalogOutputSignal(@Nonnull BlockState blockState, World level, @Nonnull BlockPos pos) {
        return Container.getRedstoneSignalFromContainer((IInventory) level.getBlockEntity(pos));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(@Nonnull BlockState state, @Nonnull World level, @Nonnull BlockPos pos, @Nonnull Random rand) {
        for (int i = 0; i < 3; ++i) {
            int j = rand.nextInt(2) * 2 - 1;
            int k = rand.nextInt(2) * 2 - 1;
            //Pos
            double x = pos.getX() + 0.5D + 0.25D * j;
            double y = pos.getY() + rand.nextFloat();
            double z = pos.getZ() + 0.75D + 0.25D * k;
            //Speed
            double sx = rand.nextFloat() * j;
            double sy = (rand.nextFloat() - 0.5D) * 0.125D;
            double sz = rand.nextFloat() * k;
            level.addParticle(ParticleTypes.PORTAL, x, y, z, sx, sy, sz);
        }
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
