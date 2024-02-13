package net.dragonmounts.client.model.dragon;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DragonBodyModelPart extends ModelRenderer {
    public final ModelRenderer back;
    public final ModelRenderer chest;
    public final ModelRenderer saddle;

    public DragonBodyModelPart(Model model) {
        super(model);
        this.buildBody();
        this.addChild(this.back = this.createBack(model));
        this.addChild(this.chest = this.createChest(model));
        this.addChild(this.saddle = this.createSaddle(model));
    }

    protected void buildBody() {
        this.setPos(0, 4, 8);
        //body
        this.texOffs(0, 0).addBox(-12, 0, -16, 24, 24, 64);
        //scale
        this.texOffs(0, 32).addBox(-1, -6, 10, 2, 6, 12);
        this.texOffs(0, 32).addBox(-1, -6, 30, 2, 6, 12);
        //heart
        this.texOffs(130, 110).addBox(-4, 12, -5, 8, 6, 15);
    }

    protected ModelRenderer createBack(Model model) {
        return new ModelRenderer(model)
                .texOffs(0, 32)
                .addBox(-1, -6, -10, 2, 6, 12);
    }

    protected ModelRenderer createChest(Model model) {
        return new ModelRenderer(model)
                .texOffs(192, 132).addBox(12, 0, 21, 4, 12, 12)
                .texOffs(224, 132).addBox(-16, 0, 21, 4, 12, 12);
    }

    protected ModelRenderer createSaddle(Model model) {
        return new ModelRenderer(model)
                .texOffs(184, 98)
                .addBox(-7, -2, -15, 15, 3, 20)
                .texOffs(214, 120)
                .addBox(-3, -3, -14, 6, 1, 2)
                .addBox(-6, -4, 2, 13, 2, 2)
                .texOffs(220, 100)
                .addBox(12, 0, -14, 1, 14, 2)
                .addBox(-13, 0, -14, 1, 10, 2)
                .texOffs(224, 132)
                .addBox(12, 14, -15, 1, 5, 4)
                .addBox(-13, 10, -15, 1, 5, 4);
    }
}
