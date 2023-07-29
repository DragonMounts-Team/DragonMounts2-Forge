package net.dragonmounts3.entity.ai;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.util.math.MathHelper;

public class DragonBodyController extends BodyController {
    public static final int MAX_TICKS = 20;
    protected final TameableDragonEntity dragon;
    protected float lastYHeadRot;
    protected int ticks;

    public DragonBodyController(TameableDragonEntity dragon) {
        super(dragon);
        this.dragon = dragon;
    }

    @Override
    public void clientTick() {
        double deltaX = this.dragon.getX() - this.dragon.xo;
        double deltaZ = this.dragon.getZ() - this.dragon.zo;
        double distance = deltaX * deltaX + deltaZ * deltaZ;
        float maxDifference = 90.0F;
        // rotate instantly if flying, sitting or moving
        if (this.dragon.isFlying() || this.dragon.isInSittingPose() || distance > 0.0001) {
            this.dragon.yBodyRot = this.dragon.yRot;
            this.dragon.yHeadRot = MathHelper.approachDegrees(this.dragon.yBodyRot, this.dragon.yHeadRot, maxDifference);
            this.lastYHeadRot = this.dragon.yHeadRot;
            this.ticks = 0;
            return;
        }

        double changeInHeadYaw = Math.abs(this.dragon.yHeadRot - this.lastYHeadRot);
        if (changeInHeadYaw > 15) {
            this.ticks = 0;
            this.lastYHeadRot = this.dragon.yHeadRot;
        } else {
            if (++this.ticks > MAX_TICKS) {
                maxDifference = Math.max(1 - (float) (this.ticks - MAX_TICKS) / MAX_TICKS, 0) * 75;
            }
        }
        this.dragon.yBodyRot = MathHelper.rotateIfNecessary(this.dragon.yBodyRot, this.dragon.yHeadRot, maxDifference);
        this.dragon.yRot = this.dragon.yBodyRot;
    }
}
