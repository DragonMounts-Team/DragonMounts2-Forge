package net.dragonmounts.entity.ai;

import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.EnumSet;

public class DragonHurtByTargetGoal extends TargetGoal {

    private final EntityPredicate copyOwnerTargeting = (new EntityPredicate()).allowUnseeable().ignoreInvisibilityTesting();
    private final boolean entityCallsForHelp;
    private final Class<?>[] excludedReinforcementTypes;
    private int revengeTimerOld;
    private final TameableDragonEntity dragon;

    public DragonHurtByTargetGoal(TameableDragonEntity dragon, boolean entityCallsForHelp, Class<?>... excludedReinforcementTypes) {
        super(dragon, true, true);
        this.dragon = dragon;
        this.entityCallsForHelp = entityCallsForHelp;
        this.excludedReinforcementTypes = excludedReinforcementTypes;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = this.dragon.getOwner();
        int i = this.dragon.getLastHurtByMobTimestamp();
        LivingEntity livingEntity = this.dragon.getLastHurtByMob();
        return i != this.revengeTimerOld && livingEntity != null && owner != null
                && this.dragon.canAttack(livingEntity, this.copyOwnerTargeting)
                && this.dragon.wantsToAttack(livingEntity, owner);
    }

    @Override
    public void start() {
        this.dragon.setTarget(this.dragon.getLastHurtByMob());
        this.targetMob = this.dragon.getTarget();
        this.revengeTimerOld = this.dragon.getLastHurtByMobTimestamp();
        this.unseenMemoryTicks = 300;
        if (this.entityCallsForHelp) {
            this.alertOthers();
        }

        super.start();
    }

    protected void alertOthers() {
        double d0 = this.getFollowDistance();
        LivingEntity livingEntity = this.dragon.getLastHurtByMob();
        for (TameableEntity mobEntity : this.dragon.level.getEntitiesOfClass(this.dragon.getClass(), (new AxisAlignedBB(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ(), this.dragon.getX() + 1.0D, this.dragon.getY() + 1.0D, this.dragon.getZ() + 1.0D)).inflate(d0, 10.0D, d0))) {
            if (this.dragon != mobEntity && mobEntity.getTarget() == null && livingEntity != null && this.dragon.getOwner() == mobEntity.getOwner() && !mobEntity.isAlliedTo(livingEntity)) {
                boolean flag = false;
                for (Class<?> aClass : this.excludedReinforcementTypes) {
                    if (mobEntity.getClass() == aClass) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    mobEntity.setTarget(this.dragon.getLastHurtByMob());
                }
            }
        }
    }

}