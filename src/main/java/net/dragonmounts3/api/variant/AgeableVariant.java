package net.dragonmounts3.api.variant;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static net.dragonmounts3.util.RenderStateAccessor.createGlowDecalRenderType;
import static net.dragonmounts3.util.RenderStateAccessor.createGlowRenderType;

public class AgeableVariant extends AbstractVariant {
    public final String name;
    public final ResourceLocation body;
    public final ResourceLocation babyBody;
    public final RenderType decal;
    public final RenderType babyDecal;
    public final RenderType glow;
    public final RenderType babyGlow;
    public final RenderType glowDecal;
    public final RenderType babyGlowDecal;

    public AgeableVariant(
            String name,
            ResourceLocation body,
            ResourceLocation babyBody,
            ResourceLocation babyGlow,
            ResourceLocation glow,
            boolean hasTailHorns,
            boolean hasSideTailScale
    ) {
        super(hasTailHorns, hasSideTailScale, 1.6F);
        this.name = name;
        this.body = body;
        this.decal = RenderType.entityDecal(body);
        this.babyBody = babyBody;
        this.babyDecal = RenderType.entityDecal(babyBody);
        this.glow = createGlowRenderType(glow);
        this.glowDecal = createGlowDecalRenderType(glow);
        this.babyGlow = createGlowRenderType(babyGlow);
        this.babyGlowDecal = createGlowDecalRenderType(babyGlow);
    }

    public static AgeableVariant create(String namespace, String path, boolean hasTailHorns, boolean hasSideTailScale) {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + path);
        int length = builder.length();
        return new AgeableVariant(
                path.substring(path.indexOf('/') + 1),
                new ResourceLocation(namespace, builder.append("/body.png").toString()),
                new ResourceLocation(namespace, builder.insert(length, "/baby").toString()),
                new ResourceLocation(namespace, builder.replace(length + 6, length + 10, "glow").toString()),
                new ResourceLocation(namespace, builder.delete(length, length + 5).toString()),
                hasTailHorns,
                hasSideTailScale
        );
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

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }
}
