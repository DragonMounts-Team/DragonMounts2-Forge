package net.dragonmounts3.client.resource;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.util.ResourceLocation;

import static net.dragonmounts3.DragonMounts.prefix;

public class ForestResourceManager extends AbstractResourceManager {
    public static final ResourceLocation DRY_BODY = prefix(TEXTURES_ROOT + "forest/dry/bodydry.png");
    public static final ResourceLocation DRY_GLOW = prefix(TEXTURES_ROOT + "forest/dry/glowdry.png");
    public static final ResourceLocation FOREST_BODY = prefix(TEXTURES_ROOT + "forest/forest/bodyforest.png");
    public static final ResourceLocation FOREST_GLOW = prefix(TEXTURES_ROOT + "forest/forest/glowforest.png");
    public static final ResourceLocation TAIGA_BODY = prefix(TEXTURES_ROOT + "forest/taiga/bodytaiga.png");
    public static final ResourceLocation TAIGA_GLOW = prefix(TEXTURES_ROOT + "forest/taiga/glowtaiga.png");

    public ForestResourceManager() {
        super(false, false, 1.6f);
    }

    @Override
    public ResourceLocation getBody(TameableDragonEntity dragon) {
        switch (dragon.getLifeStage()) {
            case ADULT:
                return TAIGA_BODY;
            case JUVENILE:
                return DRY_BODY;
            default:
                return FOREST_BODY;
        }
    }

    @Override
    public ResourceLocation getGlow(TameableDragonEntity dragon) {
        switch (dragon.getLifeStage()) {
            case ADULT:
                return TAIGA_GLOW;
            case JUVENILE:
                return DRY_GLOW;
            default:
                return FOREST_GLOW;
        }
    }
}
