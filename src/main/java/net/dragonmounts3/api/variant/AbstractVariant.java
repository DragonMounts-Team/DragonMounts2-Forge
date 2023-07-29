package net.dragonmounts3.api.variant;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

import static net.dragonmounts3.DragonMounts.prefix;

public abstract class AbstractVariant implements IStringSerializable {
    public final static String TEXTURES_ROOT = "textures/entities/dragon/";
    public final static ResourceLocation DEFAULT_CHEST = prefix(TEXTURES_ROOT + "chest.png");
    public final static ResourceLocation DEFAULT_SADDLE = prefix(TEXTURES_ROOT + "saddle.png");
    public final static ResourceLocation DEFAULT_DISSOLVE = prefix(TEXTURES_ROOT + "dissolve.png");
    public final boolean hasTailHorns;
    public final boolean hasSideTailScale;
    public final float modelPositionScale;
    public final float modelRenderScale;

    public AbstractVariant(
            boolean hasTailHorns,
            boolean hasSideTailScale,
            float modelScale
    ) {
        this.hasTailHorns = hasTailHorns;
        this.hasSideTailScale = hasSideTailScale;
        this.modelRenderScale = modelScale;
        this.modelPositionScale = modelScale / 16.0F;
    }

    public abstract ResourceLocation getBody(TameableDragonEntity dragon);

    public abstract RenderType getGlow(TameableDragonEntity dragon);

    public abstract RenderType getDecal(TameableDragonEntity dragon);

    public abstract RenderType getGlowDecal(TameableDragonEntity dragon);

    public ResourceLocation getChest(TameableDragonEntity dragon) {
        return DEFAULT_CHEST;
    }

    public ResourceLocation getSaddle(TameableDragonEntity dragon) {
        return DEFAULT_SADDLE;
    }

    public RenderType getDissolve(TameableDragonEntity dragon) {
        return RenderType.dragonExplosionAlpha(DEFAULT_DISSOLVE, dragon.deathTime / dragon.getMaxDeathTime());
    }
}
