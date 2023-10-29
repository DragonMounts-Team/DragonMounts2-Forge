package net.dragonmounts3.client.variant;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;

import static net.dragonmounts3.DragonMounts.prefix;

public abstract class VariantAppearance {
    public final static String TEXTURES_ROOT = "textures/entities/dragon/";
    public final static ResourceLocation DEFAULT_CHEST = prefix(TEXTURES_ROOT + "chest.png");
    public final static ResourceLocation DEFAULT_SADDLE = prefix(TEXTURES_ROOT + "saddle.png");
    public final static ResourceLocation DEFAULT_DISSOLVE = prefix(TEXTURES_ROOT + "dissolve.png");
    public final float positionScale;
    public final float renderScale;

    public VariantAppearance(float modelScale) {
        this.renderScale = modelScale;
        this.positionScale = modelScale / 16.0F;
    }

    public abstract boolean hasTailHorns(@SuppressWarnings("unused") TameableDragonEntity dragon);

    public abstract boolean hasSideTailScale(@SuppressWarnings("unused") TameableDragonEntity dragon);

    public abstract boolean hasTailHornsOnShoulder();

    public abstract boolean hasSideTailScaleOnShoulder();

    public abstract ResourceLocation getBody(@SuppressWarnings("unused") TameableDragonEntity dragon);

    public abstract RenderType getGlow(@SuppressWarnings("unused") TameableDragonEntity dragon);

    public abstract RenderType getDecal(@SuppressWarnings("unused") TameableDragonEntity dragon);

    public abstract RenderType getGlowDecal(@SuppressWarnings("unused") TameableDragonEntity dragon);

    public abstract RenderType getBodyOnShoulder();

    public abstract RenderType getGlowOnShoulder();

    public ResourceLocation getChest(@SuppressWarnings("unused") TameableDragonEntity dragon) {
        return DEFAULT_CHEST;
    }

    public ResourceLocation getSaddle(@SuppressWarnings("unused") TameableDragonEntity dragon) {
        return DEFAULT_SADDLE;
    }

    public RenderType getDissolve(TameableDragonEntity dragon) {
        return RenderType.dragonExplosionAlpha(DEFAULT_DISSOLVE, dragon.deathTime / dragon.getMaxDeathTime());
    }
}
