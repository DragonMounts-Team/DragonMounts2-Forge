package net.dragonmounts3.client.model.dragon;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.client.renderer.entity.model.EntityModel;

import javax.annotation.Nonnull;

import static net.dragonmounts3.registry.DragonType.isSkeleton;

public class DragonModel extends EntityModel<TameableDragonEntity> {
    protected DragonHeadModelPart head;
    protected DragonNeckModelPart neck;
    protected DragonBodyModelPart body;
    protected DragonTailModelPart tail;
    protected DragonWingModelPart wing;
    protected DragonLegModelPart foreLeg;
    protected DragonLegModelPart hindLeg;
    protected DragonType typeSnapshot = null;

    public DragonModel() {
        this.texWidth = 256;
        this.texHeight = 256;
        this.head = new DragonHeadModelPart(this);
        this.neck = new DragonNeckModelPart(this);
        this.body = new DragonBodyModelPart(this);
        this.tail = new DragonTailModelPart(this);
        this.wing = new DragonWingModelPart(this);
    }

    @Override
    public void prepareMobModel(@Nonnull TameableDragonEntity entity, float limbSwing, float limbSwingAmount, float partialTick) {
        DragonType type = entity.getDragonType();
        if (type != this.typeSnapshot) {
            boolean newIsSkeleton = isSkeleton(type);
            boolean oldIsSkeleton = isSkeleton(this.typeSnapshot);
            if (newIsSkeleton && !oldIsSkeleton) {
                this.foreLeg = new DragonLegModelPart(this, DragonLegModelPart.Config.SKELETON, false);
                this.hindLeg = new DragonLegModelPart(this, DragonLegModelPart.Config.SKELETON, true);
            } else if (!newIsSkeleton && oldIsSkeleton) {
                this.foreLeg = new DragonLegModelPart(this, DragonLegModelPart.Config.DEFAULT, false);
                this.hindLeg = new DragonLegModelPart(this, DragonLegModelPart.Config.DEFAULT, true);
            }
            this.typeSnapshot = type;
        }
    }

    @Override
    public void setupAnim(@Nonnull TameableDragonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);/*
        this.neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.wing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.foreLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.hindLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);*/
    }
}
