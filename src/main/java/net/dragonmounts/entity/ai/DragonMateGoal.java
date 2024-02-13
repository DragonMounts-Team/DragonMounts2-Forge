package net.dragonmounts.entity.ai;

import net.dragonmounts.entity.dragon.ServerDragonEntity;
import net.dragonmounts.entity.dragon.TameableDragonEntity;

public class DragonMateGoal extends AbstractDragonGoal {

    private TameableDragonEntity dragonMate;
    private int spawnBabyDelay = 0;
    private double speed;

    public DragonMateGoal(ServerDragonEntity dragon, double speed) {
        super(dragon);
        this.speed = speed;
    }

    @Override
    public boolean canUse() {
        return false;
        //TODO: Need to complete.
//        if (!this.dragon.isInLove()) {
//            return false;
//        } else {
//            dragonMate = getNearbyMate();
//            return dragonMate != null && (dragon.isMale() && !dragonMate.isMale() || !dragon.isMale() && dragonMate.isMale()) && !dragonMate.isInLove();
//        }
    }



}