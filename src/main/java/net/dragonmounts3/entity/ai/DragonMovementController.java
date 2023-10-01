package net.dragonmounts3.entity.ai;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

public class DragonMovementController extends MovementController {
    public final TameableDragonEntity dragon;
    public DragonMovementController(TameableDragonEntity dragon) {
        super(dragon);
        this.dragon = dragon;
        this.speedModifier = 0.9D;
    }

    @Override
    public void tick() {
        // original movement behavior if the entity isn't flying
        if (this.dragon.isFlying()) {
            Vector3d current = dragon.position();
            Vector3d movePos = new Vector3d(this.wantedX, this.wantedY, this.wantedZ);
            boolean uncontrolled = !(this.dragon.getControllingPassenger() instanceof PlayerEntity);
            // get direction vector by subtracting the current position from the
            // target position and normalizing the result
            Vector3d direction = movePos.subtract(current).normalize();
            // get euclidean distance to target
            double distance = current.distanceTo(movePos);
            // move towards target if it's far away enough   dragon.width
            if (distance > this.dragon.getBbWidth()) {
                double speed = this.dragon.getAttributeValue(Attributes.FLYING_SPEED) * (this.dragon.isSprinting() ? 4 : 1);
                // update velocity to approach target
                this.dragon.setDeltaMovement(direction.multiply(speed, speed, speed));
            } else if (uncontrolled) {
                // just slow down and hover at current location
                this.dragon.setDeltaMovement(direction
                        .multiply(0.8, 0.8, 0.8)
                        .add(0, Math.sin(dragon.tickCount / 5.0) * 0.03, 0)
                );
            }
            // face entity towards target
            if (distance > 2.5E-7) {
                float newYaw = (float) Math.toDegrees(Math.PI * 2 - Math.atan2(direction.x, direction.z));
                this.dragon.yRot = rotlerp(this.dragon.yRot, newYaw, uncontrolled ? 15 : 5);
                this.dragon.setSpeed((float) (this.speedModifier * dragon.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            }
            // apply movement
            this.dragon.move(MoverType.SELF, this.dragon.getDeltaMovement());
        } else {
            super.tick();
        }
    }
}
