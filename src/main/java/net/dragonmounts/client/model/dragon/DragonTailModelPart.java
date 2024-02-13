package net.dragonmounts.client.model.dragon;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dragonmounts.util.ModelSegment;
import net.dragonmounts.util.ScaledModelPart;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static net.dragonmounts.util.ModelUtil.applyRotateAngle;
import static net.dragonmounts.util.math.MathUtil.TO_RAD_FACTOR;

@OnlyIn(Dist.CLIENT)
public class DragonTailModelPart extends ScaledModelPart {
    public static final int TAIL_SIZE = 10;
    public static final int TAIL_SEGMENT_COUNT_INT = 12;
    public static final float TAIL_SEGMENT_COUNT_FLOAT = 12F;
    public static final int HORN_THICK = 3;
    public static final int HORN_LENGTH = 32;
    public static final float HORN_OFS = -HORN_THICK / 2F;
    protected final Segment[] segments = new Segment[TAIL_SEGMENT_COUNT_INT];
    public final ModelRenderer middleScale;
    public final ModelRenderer leftScale;
    public final ModelRenderer rightScale;
    public final ModelRenderer leftHorn;
    public final ModelRenderer rightHorn;

    public DragonTailModelPart(Model model) {
        super(model);
        this.buildTail();
        final float rot = 45F * TO_RAD_FACTOR;
        this.addChild(applyRotateAngle(this.leftScale = createScale(model), 0, 0, rot));
        this.addChild(applyRotateAngle(this.rightScale = createScale(model), 0, 0, -rot));
        this.addChild(this.middleScale = createScale(model));
        this.addChild(this.leftHorn = createHorn(model, true));
        this.addChild(this.rightHorn = createHorn(model, false));
        for (int i = 0; i < this.segments.length; ++i) {
            this.segments[i] = new Segment();
        }
    }

    public void save(int index) {
        if (index < 0 || index >= this.segments.length) {
            throw new IndexOutOfBoundsException();
        }
        this.segments[index].save(this);
    }

    protected void buildTail() {
        this.texOffs(152, 88).addBox(-5, -5, -5, TAIL_SIZE, TAIL_SIZE, TAIL_SIZE);
    }

    protected ModelRenderer createScale(Model model) {
        return new ModelRenderer(model)
                .texOffs(0, 0)
                .addBox(-1, -8, -3, 2, 4, 6);
    }

    protected ModelRenderer createHorn(Model model, boolean mirror) {
        final ModelRenderer renderer = new ModelRenderer(model).texOffs(0, 117).addBox(HORN_OFS, HORN_OFS, HORN_OFS, HORN_THICK, HORN_THICK, HORN_LENGTH, mirror);
        renderer.setPos(0, HORN_OFS, TAIL_SIZE / 2F);
        renderer.mirror = mirror;
        return applyRotateAngle(renderer, Math.toRadians(-15), Math.toRadians(mirror ? 145 : -145), 0);
    }

    @Override
    public void render(@Nonnull MatrixStack poseStack, @Nonnull IVertexBuilder vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        for (Segment segment : this.segments) {
            segment.apply(this);
            super.render(poseStack, vertexConsumer, light, overlay, red, green, blue, alpha);
        }
    }

    public static class Segment extends ModelSegment<DragonTailModelPart> {
        public boolean middleScaleVisible;
        public boolean leftScaleVisible;
        public boolean rightScaleVisible;
        public boolean leftHornVisible;
        public boolean rightHornVisible;

        @Override
        public Segment save(DragonTailModelPart model) {
            super.save(model);
            this.middleScaleVisible = model.middleScale.visible;
            this.leftScaleVisible = model.leftScale.visible;
            this.rightScaleVisible = model.rightScale.visible;
            this.leftHornVisible = model.leftHorn.visible;
            this.rightHornVisible = model.rightHorn.visible;
            return this;
        }

        @Override
        public void apply(DragonTailModelPart model) {
            super.apply(model);
            model.middleScale.visible = this.middleScaleVisible;
            model.leftScale.visible = this.leftScaleVisible;
            model.rightScale.visible = this.rightScaleVisible;
            model.leftHorn.visible = this.leftHornVisible;
            model.rightHorn.visible = this.rightHornVisible;
        }
    }
}
