package net.dragonmounts.entity.ai;

import net.dragonmounts.entity.dragon.ServerDragonEntity;
import net.dragonmounts.init.DragonTypes;
import net.dragonmounts.util.EntityUtil;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.EnumSet;

import static net.dragonmounts.util.math.MathUtil.TO_RAD_FACTOR;

public class PlayerControlledGoal extends AbstractDragonGoal {
    private static final Logger LOGGER = LogManager.getLogger();
    protected ServerPlayerEntity controller;
    protected boolean climbing;
    protected boolean descending;
    protected boolean convergePitch;
    protected boolean convergeYaw;

    public PlayerControlledGoal(ServerDragonEntity dragon) {
        super(dragon);
        this.setFlags(null);
    }

    @Override
    public final void setFlags(@Nullable EnumSet<Flag> ignored) {
        super.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Flag.JUMP));
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public boolean canUse() {
        return (this.controller = this.dragon.getControllingPlayer()) != null;
    }

    @Override
    public boolean canContinueToUse() {
        return (this.controller = this.dragon.getControllingPlayer()) != null;
    }

    @Override
    public void start() {
        this.dragon.setOrderedToSit(false);
        this.dragon.getNavigation().stop();
    }

    @Override
    public void tick() {
        ServerPlayerEntity player = this.controller;
        ServerDragonEntity dragon = this.dragon;
        if (dragon.getDragonType() == DragonTypes.WATER && player.isInWaterOrBubble()) {
            EntityUtil.addOrResetEffect(player, Effects.WATER_BREATHING, 200, 0, true, true, true, 21);
        }
        Vector3d pos = player.position();
        double x = pos.x, y = pos.y, z = pos.z;
        float yRot = player.yRot;
        boolean flying = dragon.isFlying();
        /* if we're breathing at a target, look at it
        if ((dragon.isUsingBreathWeapon() && dragon.getBreed().canUseBreathWeapon())) {
            Vector3d dragonEyePos = dragon.getPositionVector().add(0, dragon.getEyeHeight(), 0);
            Vector3d lookDirection = rider.getLook(1.0F);
            Vector3d endOfLook = dragonEyePos.add(lookDirection.x, lookDirection.y, lookDirection.z);
            dragon.getLookHelper().setLookPosition(endOfLook.x, endOfLook.y, endOfLook.z,
                    90, 120);
            dragon.updateIntendedRideRotation(rider);
        }*/
        if (this.convergeYaw && this.dragon.xxa == 0) {
            if (this.controller.zza == 0) {
                this.dragon.yRotO = this.dragon.yBodyRot = this.dragon.yRot = this.controller.yRot;
                this.dragon.xRot = this.controller.xRot;
            }
        }
        // control direction with movement keys
        if (player.xxa != 0 || player.zza != 0) {
            if (player.zza < 0) {
                yRot += 180;
            } else if (player.xxa > 0) {
                yRot -= 90;
            } else if (player.xxa < 0) {
                yRot += 90;
            }
            float yRad = -yRot * TO_RAD_FACTOR;
            if (flying) {
                float xRad = player.xRot * TO_RAD_FACTOR;
                float cosX = MathHelper.cos(xRad);
                x += MathHelper.sin(yRad) * cosX * 10;
                if (this.convergePitch) {
                    y -= MathHelper.sin(xRad) * 10;
                }
                z += MathHelper.cos(yRad) * cosX * 10;
            } else {
                float xRad = MathHelper.clamp(player.xRot, -30.0F, 30.0F) * TO_RAD_FACTOR;
                float cosX = MathHelper.cos(xRad);
                x += MathHelper.sin(yRad) * cosX * 10;
                y -= MathHelper.sin(xRad) * 2;
                z += MathHelper.cos(yRad) * cosX * 10;
            }
        }
        //lift off from a jump
        if (flying) {
            y += (this.climbing ? 10 : 0) + (this.descending ? 0 : -10);
        } else if (this.climbing) {
            dragon.liftOff();
        }
        dragon.getMoveControl().setWantedPosition(x, y, z, 1.2);
    }
}
