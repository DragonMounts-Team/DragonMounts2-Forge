package net.dragonmounts.entity.path;

import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.network.DMPacketHandler;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import static net.minecraft.util.math.MathHelper.floor;

/**
 * @see net.minecraft.entity.passive.TurtleEntity.Navigator
 */
@SuppressWarnings("JavadocReference")
public class DragonPathNavigator extends PathNavigator {
    public final TameableDragonEntity dragon;

    public DragonPathNavigator(TameableDragonEntity dragon, World level) {
        super(dragon, level);
        this.dragon = dragon;
    }

    @Nonnull
    @Override
    protected PathFinder createPathFinder(int range) {
        return new PathFinder(this.nodeEvaluator = new DragonNodeProcessor(), range);
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }

    @Nonnull
    @Override
    protected Vector3d getTempMobPos() {
        Vector3d pos = this.mob.position();//dragon
        return new Vector3d(pos.x, pos.y + this.mob.getBbHeight() * 0.5D, pos.z);
    }

    public void tick() {
        ++this.tick;
        if (this.hasDelayedRecomputation) {
            this.recomputePath();
        }
        if (!this.isDone()) {
            MobEntity mob = this.mob;//dragon
            Path path = this.path;
            if (this.canUpdatePath()) {
                this.followThePath();
            } else if (path != null && !path.isDone()) {
                Vector3d next = path.getNextEntityPos(mob);
                Vector3d pos = mob.position();
                if (floor(next.x) == floor(pos.x) && floor(next.y) == floor(pos.y) && floor(next.z) == floor(pos.z)) {
                    path.advance();
                }
            }
            DMPacketHandler.trySendPathFindingPacket(this.level, mob, path, this.maxDistanceToWaypoint);
            if (!this.isDone()) {
                //noinspection DataFlowIssue
                Vector3d next = path.getNextEntityPos(mob);
                mob.getMoveControl().setWantedPosition(next.x, next.y, next.z, this.speedModifier);
            }
        }
    }

    protected void followThePath() {
        if (this.path != null) {
            MobEntity mob = this.mob;
            Path path = this.path;
            Vector3d center = this.getTempMobPos();
            float width = mob.getBbWidth();
            double factor = width > 0.75D ? width / 2.0D : 0.75D - width / 2.0D;
            Vector3d momentum = mob.getDeltaMovement();
            if (Math.abs(momentum.x) > 0.2D || Math.abs(momentum.z) > 0.2D) {
                factor = factor * momentum.length() * 6.0D;
            }
            Vector3d next = Vector3d.atBottomCenterOf(path.getNextNodePos());
            Vector3d pos = mob.position();
            if (Math.abs(pos.x - next.x) < factor && Math.abs(pos.z - next.z) < factor && Math.abs(pos.y - next.y) < factor * 2.0D) {
                path.advance();
            }
            for (int i = Math.min(path.getNextNodeIndex() + 6, path.getNodeCount() - 1); i > path.getNextNodeIndex(); --i) {
                next = path.getEntityPosAtNode(mob, i);
                if (!(next.distanceToSqr(center) > 36.0D) && this.canMoveDirectly(center, next, 0, 0, 0)) {
                    path.setNextNodeIndex(i);
                    break;
                }
            }
            this.doStuckDetection(center);
        }
    }

    protected void doStuckDetection(@Nonnull Vector3d pos) {
        if (this.tick - this.lastStuckCheck > 100) {
            if (pos.distanceToSqr(this.lastStuckCheckPos) < 2.25D) {
                this.stop();
            }
            this.lastStuckCheck = this.tick;
            this.lastStuckCheckPos = pos;
        }
        if (this.path != null && !this.path.isDone()) {
            Vector3i next = this.path.getNextNodePos();
            if (next.equals(this.timeoutCachedNode)) {
                this.timeoutTimer += Util.getMillis() - this.lastTimeoutCheck;
            } else {
                this.timeoutCachedNode = next;
                double distance = pos.distanceTo(Vector3d.atCenterOf(this.timeoutCachedNode));
                float speed = this.mob.getSpeed();
                this.timeoutLimit = speed > 0.0F ? distance / speed * 100.0D : 0.0D;
            }
            if (this.timeoutLimit > 0.0D && this.timeoutTimer > this.timeoutLimit * 2.0D) {
                this.timeoutCachedNode = Vector3i.ZERO;
                this.timeoutTimer = 0L;
                this.timeoutLimit = 0.0D;
                this.stop();
            }
            this.lastTimeoutCheck = Util.getMillis();
        }
    }

    @Override
    protected boolean canMoveDirectly(@Nonnull Vector3d current, Vector3d target, int sizeX, int sizeY, int sizeZ) {
        return this.level.clip(new RayTraceContext(
                current,
                new Vector3d(target.x, target.y + this.mob.getBbHeight() * 0.5D, target.z),
                RayTraceContext.BlockMode.COLLIDER,
                RayTraceContext.FluidMode.NONE,
                this.mob
        )).getType() == RayTraceResult.Type.MISS;
    }

    @Override
    public boolean isStableDestination(@Nonnull BlockPos pos) {
        return this.level.getBlockState(pos).entityCanStandOn(this.level, pos, this.mob);
    }

    @Override
    public final boolean canFloat() {
        return true;
    }
}
