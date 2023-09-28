package net.dragonmounts3.variant;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.registry.DragonType;
import net.dragonmounts3.registry.DragonVariant;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;

import static net.dragonmounts3.util.RenderStateAccessor.createGlowDecalRenderType;
import static net.dragonmounts3.util.RenderStateAccessor.createGlowRenderType;

public class AgeableVariant extends DragonVariant {
    public final ResourceLocation body;
    public final ResourceLocation babyBody;
    public final RenderType decal;
    public final RenderType babyDecal;
    public final RenderType glow;
    public final RenderType babyGlow;
    public final RenderType glowDecal;
    public final RenderType babyGlowDecal;
    public final boolean hasTailHorns;
    public final boolean hasSideTailScale;

    public AgeableVariant(
            DragonType type,
            ResourceLocation body,
            ResourceLocation babyBody,
            ResourceLocation babyGlow,
            ResourceLocation glow,
            boolean hasTailHorns,
            boolean hasSideTailScale
    ) {
        super(type, 1.6F);
        this.body = body;
        this.decal = RenderType.entityDecal(body);
        this.babyBody = babyBody;
        this.babyDecal = RenderType.entityDecal(babyBody);
        this.glow = createGlowRenderType(glow);
        this.glowDecal = createGlowDecalRenderType(glow);
        this.babyGlow = createGlowRenderType(babyGlow);
        this.babyGlowDecal = createGlowDecalRenderType(babyGlow);
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
        return dragon.isBaby() ? this.babyBody : this.body;
    }

    @Override
    public RenderType getGlow(TameableDragonEntity dragon) {
        return dragon.isBaby() ? this.babyGlow : this.glow;
    }

    @Override
    public RenderType getDecal(TameableDragonEntity dragon) {
        return dragon.isBaby() ? this.babyDecal : this.decal;
    }

    @Override
    public RenderType getGlowDecal(TameableDragonEntity dragon) {
        return dragon.isBaby() ? this.babyGlowDecal : this.glowDecal;
    }
}
