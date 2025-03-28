package net.dragonmounts.util;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public abstract class RenderStateAccessor extends RenderState {
    public static RenderType createGlowRenderType(ResourceLocation location) {
        return RenderType.create("glow", DefaultVertexFormats.NEW_ENTITY, 7, 256, true, true, RenderType.State.builder()
                .setTextureState(new TextureState(location, false, false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setWriteMaskState(COLOR_WRITE)
                .createCompositeState(false)
        );
    }

    public static RenderType createGlowDecalRenderType(ResourceLocation location) {
        return RenderType.create("glow_decal", DefaultVertexFormats.NEW_ENTITY, 7, 256, true, true, RenderType.State.builder()
                .setTextureState(new TextureState(location, false, false))
                .setDiffuseLightingState(DIFFUSE_LIGHTING)
                .setAlphaState(DEFAULT_ALPHA)
                .setDepthTestState(EQUAL_DEPTH_TEST)
                .setCullState(NO_CULL)
                .setWriteMaskState(COLOR_WRITE)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(false)
        );
    }

    private RenderStateAccessor(String a, Runnable b, Runnable c) {
        super(a, b, c);
    }
}
