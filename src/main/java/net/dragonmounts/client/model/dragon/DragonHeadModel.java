package net.dragonmounts.client.model.dragon;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dragonmounts.util.math.MathUtil;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

import static net.dragonmounts.util.ModelUtil.applyRotateAngle;

public class DragonHeadModel extends Model {
    private final Part head;

    public DragonHeadModel() {
        super(RenderType::entityTranslucent);
        this.texWidth = 256;
        this.texHeight = 256;
        this.head = new Part(this);
    }

    public void setupAnim(float ticks, float yRot, float xRot, float scale) {
        this.head.scaleX = this.head.scaleY = this.head.scaleZ = scale;
        this.head.lowerJaw.xRot = MathHelper.sin(ticks * MathUtil.PI * 0.2F) * 0.2F + 0.2F;
        this.head.yRot = yRot * MathUtil.TO_RAD_FACTOR;
        this.head.xRot = xRot * MathUtil.TO_RAD_FACTOR;
        this.head.y = -6F;
    }

    @Override
    public void renderToBuffer(MatrixStack matrices, IVertexBuilder builder, int light, int overlay, float red, float green, float blue, float alpha) {
        this.head.render(matrices, builder, light, overlay, red, green, blue, alpha);
    }

    public static class Part extends ScaledModelPart {
        public static final int HEAD_SIZE = 16;
        public static final int HEAD_OFS = -16;
        public static final int JAW_WIDTH = 12;
        public static final int JAW_HEIGHT = 5;
        public static final int JAW_LENGTH = 16;
        public static final int HORN_THICK = 3;
        public static final int HORN_LENGTH = 12;
        public static final float HORN_OFS = -HORN_THICK / 2F;
        public final ModelRenderer lowerJaw;

        public Part(Model model) {
            super(model);
            buildHead();
            this.addChild(this.createHorn(model, false));
            this.addChild(this.createHorn(model, true));
            this.lowerJaw = this.createLowerJaw(model);
            this.addChild(this.lowerJaw);
        }

        protected void buildHead() {
            //main head
            this.texOffs(0, 0).addBox(-8, -8, 6 + HEAD_OFS, HEAD_SIZE, HEAD_SIZE, HEAD_SIZE);
            //upper jaw
            this.texOffs(56, 88).addBox(-6, -1, -8 + HEAD_OFS, JAW_WIDTH, JAW_HEIGHT, JAW_LENGTH);
            //nostril
            this.texOffs(48, 0)
                    .addBox(-5, -3, -6 + HEAD_OFS, 2, 2, 4, false)
                    .addBox(3, -3, -6 + HEAD_OFS, 2, 2, 4, true);
        }

        protected ModelRenderer createHorn(Model model, boolean mirror) {
            final ModelRenderer renderer = applyRotateAngle(new ModelRenderer(model), Math.toRadians(30), Math.toRadians(mirror ? 30 : -30), 0);
            renderer.setPos(mirror ? 5 : -5, -8, 0);
            return renderer.texOffs(28, 32).addBox(HORN_OFS, HORN_OFS, HORN_OFS, HORN_THICK, HORN_THICK, HORN_LENGTH, mirror);
        }

        protected ModelRenderer createLowerJaw(Model model) {
            final ModelRenderer renderer = new ModelRenderer(model);
            renderer.setPos(0, 4, 8 + HEAD_OFS);
            return renderer.texOffs(0, 88).addBox(-6, 0, -16, 12, 4, 16);
        }
    }
}
