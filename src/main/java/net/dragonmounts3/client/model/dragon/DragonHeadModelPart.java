package net.dragonmounts3.client.model.dragon;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

import static net.dragonmounts3.util.ModelUtil.applyRotateAngle;

public class DragonHeadModelPart extends ModelRenderer {
    public static final int HEAD_OFS = -16;
    public static final int HORN_THICK = 3;
    public static final int HORN_LENGTH = 12;
    public static final float HORN_OFS = -HORN_THICK / 2f;
    public final ModelRenderer jaw;

    public DragonHeadModelPart(Model model) {
        super(model);
        buildHead();
        this.addChild(createHorn(model, false));
        this.addChild(createHorn(model, true));
        this.jaw = this.createJaw(model);
        this.addChild(this.jaw);
    }

    protected void buildHead() {
        //main head
        this.texOffs(0, 0).addBox(-8, -8, 6 + HEAD_OFS, 16, 16, 16);
        //upper jaw
        this.texOffs(56, 88).addBox(-6, -1, -8 + HEAD_OFS, 12, 5, 16);
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

    protected ModelRenderer createJaw(Model model) {
        final ModelRenderer renderer = new ModelRenderer(model);
        renderer.setPos(0, 4, 8 + HEAD_OFS);
        return renderer.texOffs(56, 88).addBox(-6, 0, -16, 12, 4, 16);
    }
}
