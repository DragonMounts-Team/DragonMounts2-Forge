package net.dragonmounts3.util;

import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelUtil {
    public static void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
