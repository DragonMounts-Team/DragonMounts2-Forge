package net.dragonmounts3.api.variant;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static net.dragonmounts3.util.RenderStateAccessor.createGlowDecalRenderType;
import static net.dragonmounts3.util.RenderStateAccessor.createGlowRenderType;

public class DefaultVariant extends AbstractVariant {
    public final String name;
    public final ResourceLocation body;
    public final RenderType decal;
    public final RenderType glow;
    public final RenderType glowDecal;

    public DefaultVariant(String name, ResourceLocation body, ResourceLocation glow, boolean hasTailHorns, boolean hasSideTailScale) {
        super(hasTailHorns, hasSideTailScale, 1.6F);
        this.name = name;
        this.body = body;
        this.decal = RenderType.entityDecal(body);
        this.glow = createGlowRenderType(glow);
        this.glowDecal = createGlowDecalRenderType(glow);
    }

    public static DefaultVariant create(String namespace, String path, boolean hasTailHorns, boolean hasSideTailScale) {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + path);
        int length = builder.length();
        return new DefaultVariant(
                path.substring(path.indexOf('/') + 1),
                new ResourceLocation(namespace, builder.append("/body.png").toString()),
                new ResourceLocation(namespace, builder.replace(length + 6, length + 10, "glow").toString()),
                hasTailHorns,
                hasSideTailScale
        );
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

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }
}
