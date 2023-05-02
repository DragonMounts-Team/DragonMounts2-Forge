package net.dragonmounts3.util;

import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelUtil {
    public static ModelRenderer applyRotateAngle(ModelRenderer renderer, double x, double y, double z) {
        renderer.xRot = (float) x;
        renderer.yRot = (float) y;
        renderer.zRot = (float) z;
        return renderer;
    }

    public static ModelRenderer applyRotateAngle(ModelRenderer renderer, float x, float y, float z) {
        renderer.xRot = x;
        renderer.yRot = y;
        renderer.zRot = z;
        return renderer;
    }
}
