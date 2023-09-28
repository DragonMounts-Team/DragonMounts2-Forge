package net.dragonmounts3.entity.ai;

import net.dragonmounts3.init.DragonTypes;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.network.CRideDragonPacket;
import net.dragonmounts3.util.EntityUtil;
import net.dragonmounts3.util.math.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;

public class PlayerControlledGoal extends Goal {
    public final TameableDragonEntity dragon;
    protected PlayerEntity controller;
    protected boolean climbing;
    protected boolean descending;
    protected boolean convergePitch;
    protected boolean convergeYaw;

    public PlayerControlledGoal(TameableDragonEntity dragon) {
        this.dragon = dragon;
    }

    public void applyPacket(CRideDragonPacket packet) {
        this.climbing = packet.climbing;
        this.descending = packet.descending;
        this.convergePitch = packet.convergePitch;
        this.convergeYaw = packet.convergeYaw;
    }

    @Override
    public boolean canUse() {
        Entity entity = this.dragon.getControllingPassenger();
        if (entity instanceof PlayerEntity) {
            this.controller = (PlayerEntity) entity;
            return true;
        }
        this.controller = null;
        return false;
    }

    @Override
    public void start() {
        this.dragon.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (this.dragon.getDragonType() == DragonTypes.WATER && this.controller.isInWaterOrBubble()) {
            EntityUtil.addOrResetEffect(this.controller, Effects.WATER_BREATHING, 200, 0, true, true, true, 21);
        }

        Vector3d view = this.controller.getViewVector(1.0F);
        double x = dragon.getX();
        double y = dragon.getY();
        double z = dragon.getZ();
        // if we're breathing at a target, look at it
        /*if ((dragon.isUsingBreathWeapon() && dragon.getBreed().canUseBreathWeapon())) {
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
        if (this.controller.xxa != 0 || this.controller.zza != 0) {
            if (this.controller.zza < 0) {
                view = view.yRot(MathUtil.PI);
            } else if (this.controller.xxa > 0) {
                view = view.yRot(MathUtil.PI * 0.5F);
            } else if (this.controller.xxa < 0) {
                view = view.yRot(MathUtil.PI * -0.5F);
            }
            x += view.x * 10;
            if (this.convergePitch) y += view.y * 10;
            z += view.z * 10;
        }
        //lift off from a jump
        if (this.dragon.isFlying()) {
            y += (this.climbing ? 10 : 0) + (this.descending ? 0 : -10);
        } else if (this.climbing) {
            this.dragon.liftOff();
        }
        this.dragon.getMoveControl().setWantedPosition(x, y, z, 1.2);
    }
}
