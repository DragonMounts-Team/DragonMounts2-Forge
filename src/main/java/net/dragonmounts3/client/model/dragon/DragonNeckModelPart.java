package net.dragonmounts3.client.model.dragon;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class DragonNeckModelPart extends ModelRenderer {
    public static final int NECK_SIZE = 10;
    public static final int NECK_SEGMENT_COUNT = 7;
    protected final Segment[] segments = new Segment[NECK_SEGMENT_COUNT];

    public DragonNeckModelPart(Model model) {
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
        public final ModelRenderer scale;

        protected Segment(Model model) {
            super(model);
            buildSegment();
            this.scale = createScale(model);
            this.addChild(this.scale);
        }

        protected void buildSegment() {
            this.texOffs(152, 88).addBox(-5, -5, -5, NECK_SIZE, NECK_SIZE, NECK_SIZE);
        }

        protected ModelRenderer createScale(Model model) {
            return new ModelRenderer(model)
                    .texOffs(0, 0)
                    .addBox(-1, -7, -3, 2, 4, 6);
        }
    }
}
