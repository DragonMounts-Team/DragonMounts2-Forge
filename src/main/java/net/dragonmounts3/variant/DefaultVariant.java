package net.dragonmounts3.variant;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.registry.DragonType;
import net.dragonmounts3.registry.DragonVariant;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;

import static net.dragonmounts3.util.RenderStateAccessor.createGlowDecalRenderType;
import static net.dragonmounts3.util.RenderStateAccessor.createGlowRenderType;

public class DefaultVariant extends DragonVariant {
    public final ResourceLocation body;
    public final RenderType decal;
    public final RenderType glow;
    public final RenderType glowDecal;
    public final boolean hasTailHorns;
    public final boolean hasSideTailScale;

    public DefaultVariant(DragonType type, ResourceLocation body, ResourceLocation glow, boolean hasTailHorns, boolean hasSideTailScale) {
        super(type, 1.6F);
        this.body = body;
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
}
