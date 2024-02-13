package net.dragonmounts.entity.ai;

import net.dragonmounts.entity.dragon.ServerDragonEntity;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.List;

public class DragonFollowParentGoal extends AbstractDragonGoal {

    // assume any adult dragon nearby is a parent even if its not
    TameableDragonEntity adultDragon;
    double moveSpeed;
    private int delayCounter;

    public DragonFollowParentGoal(ServerDragonEntity dragon, double speed) {
        super(dragon);
        this.moveSpeed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!this.dragon.isBaby()) {
            return false;
        } else {
            List<TameableDragonEntity> list = this.dragon.level.getEntitiesOfClass(this.dragon.getClass(), this.dragon.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
            TameableDragonEntity adultDragon1 = null;
            double d0 = Double.MAX_VALUE;
            for (TameableDragonEntity adultDragon11 : list) {
                if (adultDragon11.getAge() >= 0) {
                    double d1 = this.dragon.distanceToSqr(adultDragon11);
                    if (d1 <= d0) {
                        d0 = d1;
                        adultDragon1 = adultDragon11;
                    }
                }
            }

            if (this.dragon.getOwner() != null && this.dragon.getOwner().isSuppressingBounce() && this.adultDragon != null) {
                return false;
            }

            if (adultDragon1 == null) {
                return false;
            } else if (d0 < 9.0D) {
                return false;
            } else if (!adultDragon1.isTame() && adultDragon1.getControllingPlayer() != null) {
                return false;
            } else if (dragon.isInSittingPose()) {
                return false;
            } else {
                this.adultDragon = adultDragon1;
                return true;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.dragon.getAge() >= 0) {
            return false;
        } else if (!this.adultDragon.isAlive()) {
            return false;
        } else {
            double d0 = this.dragon.distanceToSqr(this.adultDragon);
            return d0 >= 9.0D && d0 <= 256.0D;
        }
    }

    @Override
    public void start() {
        this.delayCounter = 0;
    }

    @Override
    public void stop() {
        this.adultDragon = null;
    }

    @Override
    public void tick() {
        if (--this.delayCounter <= 0) {
            this.delayCounter = 10;
            this.dragon.getNavigation().moveTo(this.adultDragon, this.moveSpeed);
        }
    }

}