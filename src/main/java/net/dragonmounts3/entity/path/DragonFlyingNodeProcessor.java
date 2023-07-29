package net.dragonmounts3.entity.path;

import net.dragonmounts3.util.BlockUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Based on {@link SwimNodeProcessor} but for air blocks.
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
@ParametersAreNonnullByDefault
public class DragonFlyingNodeProcessor extends NodeProcessor {
    @Nonnull
    @Override
    public PathPoint getStart() {
        return super.getNode(MathHelper.floor(this.mob.getBoundingBox().minX), MathHelper.floor(this.mob.getBoundingBox().minY + 0.5D), MathHelper.floor(this.mob.getBoundingBox().minZ));
    }

    @Nonnull
    @Override
    public FlaggedPathPoint getGoal(double x, double y, double z) {
        return new FlaggedPathPoint(super.getNode(MathHelper.floor(x - this.mob.getBbWidth() / 2.0), MathHelper.floor(y + 0.5), MathHelper.floor(z - this.mob.getBbWidth() / 2.0)));
    }

    @Override
    public int getNeighbors(PathPoint[] options, PathPoint point) {
        int i = 0;
        for (Direction direction : Direction.values()) {
            PathPoint target = this.getAirNode(point.x + direction.getStepX(), point.y + direction.getStepY(), point.z + direction.getStepZ());
            if (target != null && !target.closed) {
                options[i++] = point;
            }
        }
        return i;
    }

    @Nonnull
    @Override
    public PathNodeType getBlockPathType(IBlockReader level, int x, int y, int z, MobEntity entity, int sizeX, int sizeY, int sizeZ, boolean canBreakDoors, boolean canEnterDoors) {
        return this.getBlockPathType(level, x, y, z);
    }

    /**
     * Returns the node type at the specified position taking the block below into account
     */
    @Nonnull
    @Override
    public PathNodeType getBlockPathType(IBlockReader level, int x, int y, int z) {
        return BlockUtil.isAir(level.getBlockState(new BlockPos(x, y, z))) ? PathNodeType.OPEN : PathNodeType.BLOCKED;
    }

    /**
     * Returns a point that the entity can move to
     */
    protected PathPoint getAirNode(int x, int y, int z) {
        BlockPos.Mutable pos = new BlockPos.Mutable(x, y, z);
        for (int i = x; i < x + this.entityWidth; ++i) {
            for (int j = y; j < y + this.entityHeight; ++j) {
                for (int k = z; k < z + this.entityDepth; ++k) {
                    BlockState state = this.level.getBlockState(pos.set(i, j, k));
                    if (!BlockUtil.isAir(state)) {
                        return null;
                    }
                }
            }
        }
        return this.getAvailableNode(x, y, z);
    }

    protected PathPoint getAvailableNode(int x, int y, int z) {
        PathNodeType type = this.getBlockPathType(this.level, x, y, z);
        float f = this.mob.getPathfindingMalus(type);
        if (f >= 0.0F) {
            PathPoint point = this.getNode(x, y, z);
            point.type = type;
            point.costMalus = Math.max(point.costMalus, f);
            if (type == PathNodeType.WALKABLE) {
                ++point.costMalus;
            }
            return point;
        }
        return null;
    }
}
