package net.dragonmounts.entity.ai;

import net.dragonmounts.entity.dragon.ServerDragonEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class AbstractDragonGoal extends Goal {

    protected ServerDragonEntity dragon;
    protected World world;
    protected Random random;
    protected PlayerEntity rider;

    public AbstractDragonGoal(ServerDragonEntity dragon) {
        this.dragon = dragon;
        this.world = dragon.level;
        this.random = dragon.getRandom();
        this.rider = dragon.getControllingPlayer();
    }

    protected boolean tryMoveToBlockPos(BlockPos pos, double speed) {
        return dragon.getNavigation().moveTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, speed);
    }

    protected double getFollowRange() {
        return dragon.getAttributeValue(Attributes.FOLLOW_RANGE);
    }

}