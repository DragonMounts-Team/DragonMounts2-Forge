package net.dragonmounts3.client.model.dragon;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

import static net.dragonmounts3.util.ModelUtil.applyRotateAngle;

public class DragonTailModelPart extends ModelRenderer {
    public static final int TAIL_SIZE = 10;
    public static final int TAIL_SEGMENT_COUNT = 12;
    public static final int HORN_THICK = 3;
    public static final int HORN_LENGTH = 12;
    public static final float HORN_OFS = -HORN_THICK / 2f;
    protected final Segment[] segments = new Segment[TAIL_SEGMENT_COUNT];

    public DragonTailModelPart(Model model) {
        super(model);
        Segment cache;
        for (int i = 0; i < segments.length; ++i) {
            cache = new Segment(model);
            segments[i] = cache;
            this.addChild(cache);
        }
    }

    public Segment getSegment(int index) {
        if (index >= 0 && index < segments.length) {
            return segments[index];
        }
        throw new IndexOutOfBoundsException();
    }

    public static class Segment extends ModelRenderer {
        public final ModelRenderer middleScale;
        public final ModelRenderer leftScale;
        public final ModelRenderer rightScale;
        public final ModelRenderer leftHorn;
        public final ModelRenderer rightHorn;

        protected Segment(Model model) {
            super(model);
            buildSegment();
            float rot = (float) Math.toRadians(45);
            this.leftScale = createScale(model);
            this.addChild(applyRotateAngle(this.leftScale, 0, 0, rot));
            this.middleScale = createScale(model);
            this.addChild(this.middleScale);
            this.rightScale = createScale(model);
            this.addChild(applyRotateAngle(this.rightScale, 0, 0, -rot));
            this.leftHorn = createHorn(model, true);
            this.addChild(this.leftHorn);
            this.rightHorn = createHorn(model, false);
            this.addChild(this.rightHorn);
        }

        protected void buildSegment() {
            this.texOffs(112, 88).addBox(-5, -5, -5, TAIL_SIZE, TAIL_SIZE, TAIL_SIZE);
        }

        protected ModelRenderer createScale(Model model) {
            return new ModelRenderer(model)
                    .texOffs(0, 0)
                    .addBox(-1, -8, -3, 2, 4, 6);
        }

        protected ModelRenderer createHorn(Model model, boolean mirror) {
            final ModelRenderer renderer = applyRotateAngle(new ModelRenderer(model), Math.toRadians(-15), Math.toRadians(mirror ? 145 : -145), 0);
            renderer.setPos(0, HORN_OFS, TAIL_SIZE / 2f);
            return renderer.texOffs(0, 117).addBox(HORN_OFS, HORN_OFS, HORN_OFS, HORN_THICK, HORN_THICK, HORN_LENGTH, mirror);
        }
    }
}
