package net.dragonmounts.client.model.dragon;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.dragonmounts.util.ModelUtil.applyRotateAngle;

@OnlyIn(Dist.CLIENT)
public class DragonHeadModelPart extends ScaledModelPart {
    public static final int HEAD_SIZE = 16;
    public static final int HEAD_OFS = -16;
    public static final int JAW_WIDTH = 12;
    public static final int JAW_HEIGHT = 5;
    public static final int JAW_LENGTH = 16;
    public static final int HORN_THICK = 3;
    public static final int HORN_LENGTH = 12;
    public static final float HORN_OFS = -HORN_THICK / 2F;
    public final ModelRenderer lowerJaw;

    public DragonHeadModelPart(Model model) {
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
