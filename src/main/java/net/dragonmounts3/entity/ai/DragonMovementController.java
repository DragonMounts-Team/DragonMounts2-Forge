package net.dragonmounts3.entity.ai;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.util.math.MathUtil;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.MathHelper;

public class DragonMovementController extends MovementController {
    public DragonMovementController(TameableDragonEntity dragon) {
        super(dragon);
    }

    @Override
    public void tick() {
        // original movement behavior if the entity isn't flying
        if (!((TameableDragonEntity) this.mob).isFlying()) {
            super.tick();
            return;
        }
        if (this.operation == Action.MOVE_TO) {
            this.operation = Action.WAIT;
            double xDif = this.wantedX - this.mob.getX();
            double yDif = this.wantedY - this.mob.getY();
            double zDif = this.wantedZ - this.mob.getZ();
            double sq = xDif * xDif + yDif * yDif + zDif * zDif;
            if (sq < 2.5000003E-7F) {
                this.mob.setYya(0.0F);
                this.mob.setZza(0.0F);
                return;
            }
            float speed = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
            double distSq = Math.sqrt(xDif * xDif + zDif * zDif);
            this.mob.setSpeed(speed);
            if (Math.abs(yDif) > 1.0E-5f || Math.abs(distSq) > 1.0E-5f) {
                this.mob.setYya(yDif > 0d ? speed : -speed);
            }
            float yaw = (float) (MathHelper.atan2(zDif, xDif) * (180F / MathUtil.PI) - 90.0F);
            this.mob.yRot = rotlerp(this.mob.yRot, yaw, 6);
        } else {
            this.mob.setYya(0);
            this.mob.setZza(0);
        }
    }
}
