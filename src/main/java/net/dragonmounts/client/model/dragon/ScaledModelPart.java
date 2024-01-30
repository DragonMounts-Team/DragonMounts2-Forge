package net.dragonmounts.client.model.dragon;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

import javax.annotation.Nonnull;

public class ScaledModelPart extends ModelRenderer {
    public float scaleX = 1.0F;
    public float scaleY = 1.0F;
    public float scaleZ = 1.0F;

    public ScaledModelPart(Model model) {
        super(model);
    }

    public ScaledModelPart(Model model, int offsetX, int offsetY) {
        this(model.texWidth, model.texHeight, offsetX, offsetY);
        model.accept(this);
    }

    public ScaledModelPart(int textureWidth, int textureHeight, int offsetX, int offsetY) {
        super(textureWidth, textureHeight, offsetX, offsetY);
    }

    @Override
    public void translateAndRotate(@Nonnull MatrixStack poseStack) {
        super.translateAndRotate(poseStack);
        poseStack.scale(this.scaleX, this.scaleY, this.scaleZ);
    }
}
