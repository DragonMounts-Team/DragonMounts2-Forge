package net.dragonmounts3.client.resource;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.util.ResourceLocation;

import static net.dragonmounts3.DragonMounts.prefix;

public abstract class AbstractResourceManager {
    public final static String TEXTURES_ROOT = "textures/entities/dragon/";
    public final static ResourceLocation DEFAULT_CHEST = prefix(TEXTURES_ROOT + "chest.png");
    public final static ResourceLocation DEFAULT_SADDLE = prefix(TEXTURES_ROOT + "saddle.png");
    public final boolean hasTailHorns;
    public final boolean hasSideTailScale;
    public final float modelPositionScale;
    public final float modelRenderScale;

    public AbstractResourceManager(
            boolean hasTailHorns,
            boolean hasSideTailScale,
            float modelScale
    ) {
        this.hasTailHorns = hasTailHorns;
        this.hasSideTailScale = hasSideTailScale;
        this.modelRenderScale = modelScale;
        this.modelPositionScale = modelScale / 16.0f;
    }

    public ResourceLocation getChest(TameableDragonEntity dragon) {
        return DEFAULT_CHEST;
    }

    public ResourceLocation getSaddle(TameableDragonEntity dragon) {
        return DEFAULT_SADDLE;
    }

    public abstract ResourceLocation getBody(TameableDragonEntity dragon);

    public abstract ResourceLocation getGlow(TameableDragonEntity dragon);
}
