package net.dragonmounts3.client.resource;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.util.ResourceLocation;

import static net.dragonmounts3.DragonMounts.prefix;

public class SculkResourceManager extends AbstractResourceManager {
    public static final ResourceLocation BODY = prefix(TEXTURES_ROOT + "sculk/body.png");
    public static final ResourceLocation GLOW = prefix(TEXTURES_ROOT + "ender/glow.png");

    public SculkResourceManager() {
        super(false, false, 1.6f);
    }

    @Override
    public ResourceLocation getBody(TameableDragonEntity dragon) {
        return BODY;
    }

    @Override
    public ResourceLocation getGlow(TameableDragonEntity dragon) {
        return GLOW;
    }
}