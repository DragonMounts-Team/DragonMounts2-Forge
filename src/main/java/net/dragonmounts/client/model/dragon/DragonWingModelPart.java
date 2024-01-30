package net.dragonmounts.client.model.dragon;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DragonWingModelPart extends ModelRenderer {
    public static final int FINGER_COUNT = 4;
    public final ModelRenderer forearm;
    protected final ModelRenderer[] fingers = new ModelRenderer[FINGER_COUNT];

    public DragonWingModelPart(Model model) {
        super(model);
        buildWing();
        this.forearm = this.createForearm(model);
        this.addChild(this.forearm);
        for (int i = 0; i < this.fingers.length; ++i) {
            this.fingers[i] = createFinger(model, i + 1 == this.fingers.length);
            this.forearm.addChild(this.fingers[i]);
        }
    }

    protected void buildWing() {
        //setRenderScale(1.1F);
        this.setPos(-10, 5, 4);
        this.texOffs(0, 152).addBox(-28, -3, -3, 28, 6, 6);
        this.texOffs(116, 232).addBox(-28, 0, 2, 28, 0, 24);
    }

    protected ModelRenderer createForearm(Model model) {
        final ModelRenderer renderer = new ModelRenderer(model);
        renderer.setPos(-28, 0, 0);
        return renderer.texOffs(0, 164).addBox(-48, -2, -2, 48, 4, 4);
    }

    protected ModelRenderer createFinger(Model model, boolean small) {
        final ModelRenderer renderer = new ModelRenderer(model);
        renderer.setPos(-47, 0, 0);
        if (small) {
            renderer.texOffs(-32, 224).addBox(-70, 0, 1, 70, 0, 32);
        } else {
            renderer.texOffs(-49, 176).addBox(-70, 0, 1, 70, 0, 48);
        }
        return renderer.texOffs(0, 172).addBox(-70, -1, -1, 70, 2, 2);
    }

    public ModelRenderer getFinger(int index) {
        if (index >= 0 && index < 4) {
            return this.fingers[index];
        }
        throw new IndexOutOfBoundsException();
    }
}
