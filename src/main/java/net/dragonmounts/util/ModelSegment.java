package net.dragonmounts.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ModelSegment<T extends ScaledModelPart> extends Segment {
    // misc meta data
    public boolean visible;

    public ModelSegment<T> save(T model) {
        this.scaleX = model.scaleX;
        this.scaleY = model.scaleY;
        this.scaleZ = model.scaleZ;
        this.x = model.x;
        this.y = model.y;
        this.z = model.z;
        this.xRot = model.xRot;
        this.yRot = model.yRot;
        this.zRot = model.zRot;
        this.visible = model.visible;
        return this;
    }

    public void apply(T model) {
        model.scaleX = this.scaleX;
        model.scaleY = this.scaleY;
        model.scaleZ = this.scaleZ;
        model.x = this.x;
        model.y = this.y;
        model.z = this.z;
        model.xRot = this.xRot;
        model.yRot = this.yRot;
        model.zRot = this.zRot;
        model.visible = this.visible;
    }
}
