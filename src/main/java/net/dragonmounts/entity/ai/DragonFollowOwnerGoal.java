package net.dragonmounts.entity.ai;

import net.dragonmounts.entity.dragon.ServerDragonEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.pathfinding.PathNavigator;

import java.util.EnumSet;

public class DragonFollowOwnerGoal extends AbstractDragonGoal {
    public static final double START_DISTANCE_SQUARE = 400D;//20
    public static final double STOP_DISTANCE_SQUARE = 64D;//8
    private LivingEntity owner;
    private int pathTicks;
    private PathNavigator navigator;

    public DragonFollowOwnerGoal(ServerDragonEntity dragon) {
        super(dragon);
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return !(this.dragon.isOrderedToSit() || this.dragon.isLeashed() || this.dragon.isPassenger() || (this.owner = this.dragon.getOwner()) == null || this.owner.isSpectator() || this.dragon.distanceToSqr(this.owner) < START_DISTANCE_SQUARE);
    }

    public boolean canContinueToUse() {
        return !(this.dragon.isOrderedToSit() || this.dragon.isLeashed() || this.dragon.isPassenger() || (this.navigator = this.dragon.getNavigation()).isDone() || this.dragon.distanceToSqr(this.owner) <= STOP_DISTANCE_SQUARE);
    }

    public void start() {
        this.navigator = this.dragon.getNavigation();
        this.pathTicks = 0;
    }

    public void stop() {
        this.owner = null;
        this.dragon.getNavigation().stop();
    }

    public void tick() {
        this.dragon.getLookControl().setLookAt(this.owner, 10F, (float) this.dragon.getMaxHeadXRot());
        if (++this.pathTicks > 0) {
            this.pathTicks = -10;
            this.navigator.moveTo(this.owner, 1D);
        }
    }
}
