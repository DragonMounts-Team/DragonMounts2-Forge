package net.dragonmounts3.entity.ai;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.util.math.MathHelper;

public class DragonBodyController extends BodyController {
    protected final TameableDragonEntity dragon;
    protected float lastDifference = 0;
    protected boolean wasMoving = false;
    protected float process;

    public DragonBodyController(TameableDragonEntity dragon) {
        super(dragon);
        this.dragon = dragon;
    }

    @Override
    public void clientTick() {
        double deltaX = this.dragon.getX() - this.dragon.xo;
        double deltaZ = this.dragon.getZ() - this.dragon.zo;
        boolean flag = this.process > 0.0f;
        boolean isMoving = deltaX * deltaX + deltaZ * deltaZ > 2.5000003E-7d;
        float difference = Math.abs(this.dragon.yHeadRot - this.dragon.yBodyRot);
        if (this.wasMoving && !isMoving) {
            this.process = Math.min(this.process, 0.25f);
        } else if (difference > 0) {
            if (difference > this.lastDifference) {
                this.process = 0.5f;
                flag = true;
            } else if (flag) {
                this.process -= 0.05f;
            }
        } else if (flag) {
            this.process -= 0.1f;
        }
        float limit = this.dragon.getMaxHeadYRot() * (flag ? this.process : 1.0f);
        this.dragon.yBodyRot = MathHelper.rotateIfNecessary(this.dragon.yBodyRot, this.dragon.yHeadRot, limit);
        this.dragon.yRot = MathHelper.rotateIfNecessary(dragon.yRot, this.dragon.yBodyRot, limit);
        this.wasMoving = isMoving;
        this.lastDifference = difference;
    }
}
