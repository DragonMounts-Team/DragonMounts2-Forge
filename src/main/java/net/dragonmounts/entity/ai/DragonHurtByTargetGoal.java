package net.dragonmounts.entity.ai;

import net.dragonmounts.entity.dragon.ServerDragonEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.TargetGoal;

import java.util.EnumSet;

/**
 * Simplified {@link HurtByTargetGoal}
 */
public class DragonHurtByTargetGoal extends TargetGoal {
    public final ServerDragonEntity dragon;
    protected int reinforcementCooldown;
    protected int timestamp;

    public DragonHurtByTargetGoal(ServerDragonEntity dragon) {
        super(dragon, true, true);
        this.dragon = dragon;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        ServerDragonEntity dragon = this.dragon;
        int timestamp = dragon.getLastHurtByMobTimestamp();
        if (timestamp == this.timestamp) return false;
        LivingEntity owner = dragon.getOwner();
        if (owner == null) return false;
        LivingEntity target = dragon.getLastHurtByMob();
        return target != null && dragon.canAttack(target, HurtByTargetGoal.HURT_BY_TARGETING) && dragon.wantsToAttack(target, owner);
    }

    public boolean canContinueToUse() {
        if (super.canContinueToUse()) {
            if (this.reinforcementCooldown-- <= 0) {
                alertOthers();
            }
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        MobEntity dragon = this.mob;
        dragon.setTarget(dragon.getLastHurtByMob());
        this.targetMob = dragon.getTarget();
        this.timestamp = dragon.getLastHurtByMobTimestamp();
        this.unseenMemoryTicks = 300;
        this.reinforcementCooldown = 0;
        this.alertOthers();
        super.start();
    }

    protected void alertOthers() {
        LivingEntity target = this.targetMob;
        if (target == null) return;
        ServerDragonEntity self = this.dragon;
        LivingEntity owner = self.getOwner();
        if (owner == null) return;
        this.reinforcementCooldown = self.getRandom().nextInt(256) + 128;
        double distance = this.getFollowDistance();
        for (ServerDragonEntity dragon : self.level.getEntitiesOfClass(ServerDragonEntity.class, self.getBoundingBox().inflate(distance, 10.0D, distance))) {
            if (self != dragon && dragon.getTarget() == null && owner == dragon.getOwner() && !dragon.isAlliedTo(target)) {
                dragon.setTarget(target);
            }
        }
    }

}