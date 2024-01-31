package net.dragonmounts3.entity.ai;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;

public class DragonMateGoal extends DragonBaseGoal {

    private TameableDragonEntity dragonMate;
    private int spawnBabyDelay = 0;
    private double speed;

    public DragonMateGoal(TameableDragonEntity dragon, double speed) {
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