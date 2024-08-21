package net.dragonmounts.entity.path;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.MobEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.pathfinding.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.Region;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;

/**
 * @see WalkAndSwimNodeProcessor
 */
public class DragonNodeProcessor extends NodeProcessor {
    private final Long2ObjectOpenHashMap<PathNodeType> nodeTypesByPosCache = new Long2ObjectOpenHashMap<>();
    protected float oldWaterCost;

    @Override
    public void prepare(@Nonnull Region region, @Nonnull MobEntity mob) {
        this.level = region;
        this.mob = mob;
        this.nodes.clear();
        EntitySize size = mob.getDimensions(mob.getPose());
        this.entityWidth = this.entityDepth = MathHelper.ceil(size.width / 2F);
        this.entityHeight = MathHelper.ceil(size.width / 2F);
        this.oldWaterCost = mob.getPathfindingMalus(PathNodeType.WATER);
        mob.setPathfindingMalus(PathNodeType.WATER, 0.0F);
    }

    @Override
    public final boolean canPassDoors() {
        return true;
    }

    @Override
    public final boolean canOpenDoors() {
        return false;
    }

    @Override
    public void done() {
        this.mob.setPathfindingMalus(PathNodeType.WATER, this.oldWaterCost);
        this.nodeTypesByPosCache.clear();
        super.done();
    }

    @Nonnull
    @Override
    public PathPoint getStart() {
        AxisAlignedBB box = this.mob.getBoundingBox();
        return this.getNode(MathHelper.floor(box.minX), MathHelper.floor(box.minY + 0.5D), MathHelper.floor(box.minZ));
    }

    @Nonnull
    @Override
    public FlaggedPathPoint getGoal(double x, double y, double z) {
        return new FlaggedPathPoint(this.getNode(MathHelper.floor(x), MathHelper.floor(y + 0.5D), MathHelper.floor(z)));
    }

    @Override
    public int getNeighbors(@Nonnull PathPoint[] p_222859_1_, @Nonnull PathPoint p_222859_2_) {
        int i = 0;
        double d0 = this.getWaterDependentPosHeight(p_222859_2_.x, p_222859_2_.y, p_222859_2_.z);
        PathPoint pathpoint = this.getAcceptedNode(p_222859_2_.x, p_222859_2_.y, p_222859_2_.z + 1, 1, d0);
        PathPoint pathpoint1 = this.getAcceptedNode(p_222859_2_.x - 1, p_222859_2_.y, p_222859_2_.z, 1, d0);
        PathPoint pathpoint2 = this.getAcceptedNode(p_222859_2_.x + 1, p_222859_2_.y, p_222859_2_.z, 1, d0);
        PathPoint pathpoint3 = this.getAcceptedNode(p_222859_2_.x, p_222859_2_.y, p_222859_2_.z - 1, 1, d0);
        PathPoint pathpoint4 = this.getAcceptedNode(p_222859_2_.x, p_222859_2_.y + 1, p_222859_2_.z, 0, d0);
        PathPoint pathpoint5 = this.getAcceptedNode(p_222859_2_.x, p_222859_2_.y - 1, p_222859_2_.z, 1, d0);
        if (pathpoint != null && !pathpoint.closed) {
            p_222859_1_[i++] = pathpoint;
        }

        if (pathpoint1 != null && !pathpoint1.closed) {
            p_222859_1_[i++] = pathpoint1;
        }

        if (pathpoint2 != null && !pathpoint2.closed) {
            p_222859_1_[i++] = pathpoint2;
        }

        if (pathpoint3 != null && !pathpoint3.closed) {
            p_222859_1_[i++] = pathpoint3;
        }

        if (pathpoint4 != null && !pathpoint4.closed) {
            p_222859_1_[i++] = pathpoint4;
        }

        if (pathpoint5 != null && !pathpoint5.closed) {
            p_222859_1_[i++] = pathpoint5;
        }

        boolean flag = pathpoint3 == null || pathpoint3.type == PathNodeType.OPEN || pathpoint3.costMalus != 0.0F;
        boolean flag1 = pathpoint == null || pathpoint.type == PathNodeType.OPEN || pathpoint.costMalus != 0.0F;
        boolean flag2 = pathpoint2 == null || pathpoint2.type == PathNodeType.OPEN || pathpoint2.costMalus != 0.0F;
        boolean flag3 = pathpoint1 == null || pathpoint1.type == PathNodeType.OPEN || pathpoint1.costMalus != 0.0F;
        if (flag && flag3) {
            PathPoint pathpoint6 = this.getAcceptedNode(p_222859_2_.x - 1, p_222859_2_.y, p_222859_2_.z - 1, 1, d0);
            if (pathpoint6 != null && !pathpoint6.closed) {
                p_222859_1_[i++] = pathpoint6;
            }
        }

        if (flag && flag2) {
            PathPoint pathpoint7 = this.getAcceptedNode(p_222859_2_.x + 1, p_222859_2_.y, p_222859_2_.z - 1, 1, d0);
            if (pathpoint7 != null && !pathpoint7.closed) {
                p_222859_1_[i++] = pathpoint7;
            }
        }

        if (flag1 && flag3) {
            PathPoint pathpoint8 = this.getAcceptedNode(p_222859_2_.x - 1, p_222859_2_.y, p_222859_2_.z + 1, 1, d0);
            if (pathpoint8 != null && !pathpoint8.closed) {
                p_222859_1_[i++] = pathpoint8;
            }
        }

        if (flag1 && flag2) {
            PathPoint pathpoint9 = this.getAcceptedNode(p_222859_2_.x + 1, p_222859_2_.y, p_222859_2_.z + 1, 1, d0);
            if (pathpoint9 != null && !pathpoint9.closed) {
                p_222859_1_[i++] = pathpoint9;
            }
        }

        return i;
    }

    protected double getWaterDependentPosHeight(double x, double y, double z) {
        if (this.mob.isInWater()) return y + 0.5D;
        BlockPos pos = new BlockPos(x, --y, z);
        VoxelShape shape = this.level.getBlockState(pos).getCollisionShape(this.level, pos);
        return y + (shape.isEmpty() ? 0.0D : shape.max(Direction.Axis.Y));
    }

    @Nullable
    protected PathPoint getAcceptedNode(int x, int y, int z, int step, double posY) {
        if (this.getWaterDependentPosHeight(x, y, z) - posY > 1.125D) {
            return null;
        }
        MobEntity mob = this.mob;
        PathPoint point = null;
        PathNodeType type = this.getCachedNodeType(x, y, z);
        float f = mob.getPathfindingMalus(type);
        double d1 = mob.getBbWidth() / 2.0D;
        if (f >= 0.0F) {
            point = this.getNode(x, y, z);
            point.type = type;
            point.costMalus = Math.max(point.costMalus, f);
        }
        if (type == PathNodeType.WATER || type == PathNodeType.WALKABLE) {
            if (y < mob.level.getSeaLevel() - 10 && point != null) {
                ++point.costMalus;
            }

            return point;
        }
        if (point == null && step > 0 && type != PathNodeType.FENCE && type != PathNodeType.UNPASSABLE_RAIL && type != PathNodeType.TRAPDOOR) {
            point = this.getAcceptedNode(x, y + 1, z, step - 1, posY);
        }

        if (type == PathNodeType.OPEN) {
            if (!mob.level.noCollision(mob, new AxisAlignedBB((double) x - d1 + 0.5D, (double) y + 0.001D, (double) z - d1 + 0.5D, (double) x + d1 + 0.5D, (float) y + this.mob.getBbHeight(), (double) z + d1 + 0.5D))) {
                return null;
            }

            PathNodeType pathnodetype1 = this.getCachedNodeType(x, y, z);
            if (pathnodetype1 == PathNodeType.BLOCKED) {
                point = this.getNode(x, y, z);
                point.type = PathNodeType.WALKABLE;
                point.costMalus = Math.max(point.costMalus, f);
                return point;
            }

            if (pathnodetype1 == PathNodeType.WATER) {
                point = this.getNode(x, y, z);
                point.type = PathNodeType.WATER;
                point.costMalus = Math.max(point.costMalus, f);
                return point;
            }

            int i = 0;

            while (y > 0 && type == PathNodeType.OPEN) {
                --y;
                if (i++ >= mob.getMaxFallDistance()) {
                    return null;
                }

                type = this.getCachedNodeType(x, y, z);
                f = mob.getPathfindingMalus(type);
                if (type != PathNodeType.OPEN && f >= 0.0F) {
                    point = this.getNode(x, y, z);
                    point.type = type;
                    point.costMalus = Math.max(point.costMalus, f);
                    break;
                }

                if (f < 0.0F) {
                    return null;
                }
            }
        }

        return point;


    }


    protected PathNodeType evaluateBlockPathType(IBlockReader level, BlockPos pos, PathNodeType type) {
        if (type == PathNodeType.RAIL && !(level.getBlockState(pos).getBlock() instanceof AbstractRailBlock) && !(level.getBlockState(pos.below()).getBlock() instanceof AbstractRailBlock)) {
            type = PathNodeType.UNPASSABLE_RAIL;
        }
        if (type == PathNodeType.DOOR_OPEN || type == PathNodeType.DOOR_WOOD_CLOSED || type == PathNodeType.DOOR_IRON_CLOSED) {
            type = PathNodeType.BLOCKED;
        }
        if (type == PathNodeType.LEAVES) {
            type = PathNodeType.BLOCKED;
        }
        return type;
    }

    @Nonnull
    @Override
    public PathNodeType getBlockPathType(@Nonnull IBlockReader level, int x, int y, int z) {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        PathNodeType pathnodetype = getBlockPathTypeRaw(level, blockpos$mutable.set(x, y, z));
        if (pathnodetype == PathNodeType.WATER) {
            for (Direction direction : Direction.values()) {
                PathNodeType pathnodetype2 = getBlockPathTypeRaw(level, blockpos$mutable.set(x, y, z).move(direction));
                if (pathnodetype2 == PathNodeType.BLOCKED) {
                    return PathNodeType.WATER_BORDER;
                }
            }
            return PathNodeType.WATER;
        } else {
            if (pathnodetype == PathNodeType.OPEN && y >= 1) {
                BlockState blockstate = level.getBlockState(new BlockPos(x, y - 1, z));
                PathNodeType pathnodetype1 = getBlockPathTypeRaw(level, blockpos$mutable.set(x, y - 1, z));
                if (pathnodetype1 != PathNodeType.WALKABLE && pathnodetype1 != PathNodeType.OPEN && pathnodetype1 != PathNodeType.LAVA) {
                    pathnodetype = PathNodeType.WALKABLE;
                } else {
                    pathnodetype = PathNodeType.OPEN;
                }

                if (pathnodetype1 == PathNodeType.DAMAGE_FIRE || blockstate.is(Blocks.MAGMA_BLOCK) || blockstate.is(BlockTags.CAMPFIRES)) {
                    pathnodetype = PathNodeType.DAMAGE_FIRE;
                }

                if (pathnodetype1 == PathNodeType.DAMAGE_CACTUS) {
                    pathnodetype = PathNodeType.DAMAGE_CACTUS;
                }

                if (pathnodetype1 == PathNodeType.DAMAGE_OTHER) {
                    pathnodetype = PathNodeType.DAMAGE_OTHER;
                }
            }

            if (pathnodetype == PathNodeType.WALKABLE) {
                pathnodetype = WalkNodeProcessor.checkNeighbourBlocks(level, blockpos$mutable.set(x, y, z), pathnodetype);
            }

            return pathnodetype;
        }
    }

    //-------------------------walk

    @Nonnull
    @Override
    public PathNodeType getBlockPathType(@Nonnull IBlockReader pBlockaccess, int pX, int pY, int pZ, MobEntity pEntityliving, int pXSize, int pYSize, int pZSize, boolean pCanBreakDoors, boolean pCanEnterDoors) {
        EnumSet<PathNodeType> enumset = EnumSet.noneOf(PathNodeType.class);
        PathNodeType pathnodetype = PathNodeType.BLOCKED;
        BlockPos blockpos = pEntityliving.blockPosition();
        pathnodetype = this.getBlockPathTypes(pBlockaccess, pX, pY, pZ, pXSize, pYSize, pZSize, enumset, pathnodetype, blockpos);
        if (enumset.contains(PathNodeType.FENCE)) {
            return PathNodeType.FENCE;
        } else if (enumset.contains(PathNodeType.UNPASSABLE_RAIL)) {
            return PathNodeType.UNPASSABLE_RAIL;
        } else {
            PathNodeType pathnodetype1 = PathNodeType.BLOCKED;

            for (PathNodeType pathnodetype2 : enumset) {
                if (pEntityliving.getPathfindingMalus(pathnodetype2) < 0.0F) {
                    return pathnodetype2;
                }

                if (pEntityliving.getPathfindingMalus(pathnodetype2) >= pEntityliving.getPathfindingMalus(pathnodetype1)) {
                    pathnodetype1 = pathnodetype2;
                }
            }

            return pathnodetype == PathNodeType.OPEN && pEntityliving.getPathfindingMalus(pathnodetype1) == 0.0F && pXSize <= 1 ? PathNodeType.OPEN : pathnodetype1;
        }
    }

    /**
     * Populates the nodeTypeEnum with all the surrounding node types and returns the center one
     */
    public PathNodeType getBlockPathTypes(IBlockReader level, int x, int y, int z, int sizeX, int sizeY, int sizeZ, EnumSet<PathNodeType> set, PathNodeType type, BlockPos pos) {
        for (int i = 0; i < sizeX; ++i) {
            for (int j = 0; j < sizeY; ++j) {
                for (int k = 0; k < sizeZ; ++k) {
                    int l = i + x;
                    int i1 = j + y;
                    int j1 = k + z;
                    PathNodeType pathnodetype = this.getBlockPathType(level, l, i1, j1);
                    pathnodetype = this.evaluateBlockPathType(level, pos, pathnodetype);
                    if (i == 0 && j == 0 && k == 0) {
                        type = pathnodetype;
                    }

                    set.add(pathnodetype);
                }
            }
        }
        return type;
    }

    protected PathNodeType getCachedNodeType(int x, int y, int z) {
        return this.nodeTypesByPosCache.computeIfAbsent(
                BlockPos.asLong(x, y, z),
                $ -> this.getBlockPathType(this.level, x, y, z, this.mob, this.entityWidth, this.entityHeight, this.entityDepth, false, true)
        );
    }

    protected static PathNodeType getBlockPathTypeRaw(IBlockReader p_237238_0_, BlockPos p_237238_1_) {
        BlockState blockstate = p_237238_0_.getBlockState(p_237238_1_);
        PathNodeType type = blockstate.getAiPathNodeType(p_237238_0_, p_237238_1_);
        if (type != null) return type;
        Block block = blockstate.getBlock();
        Material material = blockstate.getMaterial();
        if (blockstate.isAir(p_237238_0_, p_237238_1_)) {
            return PathNodeType.OPEN;
        } else if (!blockstate.is(BlockTags.TRAPDOORS) && !blockstate.is(Blocks.LILY_PAD)) {
            if (blockstate.is(Blocks.CACTUS)) {
                return PathNodeType.DAMAGE_CACTUS;
            } else if (blockstate.is(Blocks.SWEET_BERRY_BUSH)) {
                return PathNodeType.DAMAGE_OTHER;
            } else if (blockstate.is(Blocks.HONEY_BLOCK)) {
                return PathNodeType.STICKY_HONEY;
            } else if (blockstate.is(Blocks.COCOA)) {
                return PathNodeType.COCOA;
            } else {
                FluidState fluidstate = p_237238_0_.getFluidState(p_237238_1_);
                if (fluidstate.is(FluidTags.WATER)) {
                    return PathNodeType.WATER;
                } else if (fluidstate.is(FluidTags.LAVA)) {
                    return PathNodeType.LAVA;
                } else if (isBurningBlock(blockstate)) {
                    return PathNodeType.DAMAGE_FIRE;
                } else if (DoorBlock.isWoodenDoor(blockstate) && !blockstate.getValue(DoorBlock.OPEN)) {
                    return PathNodeType.DOOR_WOOD_CLOSED;
                } else if (block instanceof DoorBlock && material == Material.METAL && !blockstate.getValue(DoorBlock.OPEN)) {
                    return PathNodeType.DOOR_IRON_CLOSED;
                } else if (block instanceof DoorBlock && blockstate.getValue(DoorBlock.OPEN)) {
                    return PathNodeType.DOOR_OPEN;
                } else if (block instanceof AbstractRailBlock) {
                    return PathNodeType.RAIL;
                } else if (block instanceof LeavesBlock) {
                    return PathNodeType.LEAVES;
                } else if (!block.is(BlockTags.FENCES) && !block.is(BlockTags.WALLS) && (!(block instanceof FenceGateBlock) || blockstate.getValue(FenceGateBlock.OPEN))) {
                    return !blockstate.isPathfindable(p_237238_0_, p_237238_1_, PathType.LAND) ? PathNodeType.BLOCKED : PathNodeType.OPEN;
                } else {
                    return PathNodeType.FENCE;
                }
            }
        } else {
            return PathNodeType.TRAPDOOR;
        }
    }

    /**
     * Checks whether the specified block state can cause burn damage
     */
    private static boolean isBurningBlock(BlockState pState) {
        return pState.is(BlockTags.FIRE) || pState.is(Blocks.LAVA) || pState.is(Blocks.MAGMA_BLOCK) || CampfireBlock.isLitCampfire(pState);
    }
}
