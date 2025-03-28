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

@OnlyIn(Dist.CLIENT)
public class DragonNeckModelPart extends ScaledModelPart {
    public static final int NECK_SIZE = 10;
    public static final int NECK_SEGMENT_COUNT_INT = 7;
    public static final float NECK_SEGMENT_COUNT_FLOAT = 7F;
    protected final Segment[] segments = new Segment[NECK_SEGMENT_COUNT_INT];
    public final ModelRenderer scale;

    public DragonNeckModelPart(Model model) {
        super(model);
        this.buildNeck();
        this.addChild(this.scale = createScale(model));
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

    @Override
    public void render(@Nonnull MatrixStack poseStack, @Nonnull IVertexBuilder vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        for (Segment segment : this.segments) {
            segment.apply(this);
            super.render(poseStack, vertexConsumer, light, overlay, red, green, blue, alpha);
        }
    }

    public static class Segment extends ModelSegment<DragonNeckModelPart> {
        public boolean scaleVisible;

        @Override
        public Segment save(DragonNeckModelPart model) {
            super.save(model);
            this.scaleVisible = model.scale.visible;
            return this;
        }

        @Override
        public void apply(DragonNeckModelPart model) {
            super.apply(model);
            model.scale.visible = this.scaleVisible;
        }
    }

    protected void buildNeck() {
        this.texOffs(112, 88).addBox(-5, -5, -5, NECK_SIZE, NECK_SIZE, NECK_SIZE);
    }

    protected ModelRenderer createScale(Model model) {
        return new ModelRenderer(model)
                .texOffs(0, 0)
                .addBox(-1, -7, -3, 2, 4, 6);
    }
}
