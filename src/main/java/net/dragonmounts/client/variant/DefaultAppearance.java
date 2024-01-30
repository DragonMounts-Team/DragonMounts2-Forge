package net.dragonmounts.client.variant;

import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;

import static net.dragonmounts.util.RenderStateAccessor.createGlowDecalRenderType;
import static net.dragonmounts.util.RenderStateAccessor.createGlowRenderType;

public class DefaultAppearance extends VariantAppearance {
    public final ResourceLocation body;
    public final RenderType bodyOnShoulder;
    public final RenderType decal;
    public final RenderType glow;
    public final RenderType glowDecal;
    public final boolean hasTailHorns;
    public final boolean hasSideTailScale;

    public DefaultAppearance(ResourceLocation body, ResourceLocation glow, boolean hasTailHorns, boolean hasSideTailScale) {
        super(1.6F);
        this.body = body;
        this.bodyOnShoulder = RenderType.entityCutoutNoCull(body);
        this.decal = RenderType.entityDecal(body);
        this.glow = createGlowRenderType(glow);
        this.glowDecal = createGlowDecalRenderType(glow);
        this.hasTailHorns = hasTailHorns;
        this.hasSideTailScale = hasSideTailScale;
    }

    @Override
    public boolean hasTailHorns(TameableDragonEntity dragon) {
        return this.hasTailHorns;
    }

    @Override
    public boolean hasSideTailScale(TameableDragonEntity dragon) {
        return this.hasSideTailScale;
    }

    @Override
    public boolean hasTailHornsOnShoulder() {
        return this.hasTailHorns;
    }

    @Override
    public boolean hasSideTailScaleOnShoulder() {
        return this.hasSideTailScale;
    }

    @Override
    public ResourceLocation getBody(TameableDragonEntity dragon) {
        return this.body;
    }

    @Override
    public RenderType getGlow(TameableDragonEntity dragon) {
        return this.glow;
    }

    @Override
    public RenderType getDecal(TameableDragonEntity dragon) {
        return this.decal;
    }

    @Override
    public RenderType getGlowDecal(TameableDragonEntity dragon) {
        return this.glowDecal;
    }

    @Override
    public RenderType getBodyOnShoulder() {
        return this.bodyOnShoulder;
    }

    @Override
    public RenderType getGlowOnShoulder() {
        return this.glow;
    }
}
