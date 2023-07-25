package net.dragonmounts3.client.resource;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.util.ResourceLocation;

public class DefaultResourceManager extends AbstractResourceManager {
    public final ResourceLocation body;
    public final ResourceLocation glow;

    public DefaultResourceManager(String namespace, String name, boolean hasTailHorns, boolean hasSideTailScale) {
        super(hasTailHorns, hasSideTailScale, 1.6F);
        this.body = new ResourceLocation(namespace, TEXTURES_ROOT + name + "/body.png");
        this.glow = new ResourceLocation(namespace, TEXTURES_ROOT + name + "/glow.png");
    }

    @Override
    public ResourceLocation getBody(TameableDragonEntity dragon) {
        return this.body;
    }

    @Override
    public ResourceLocation getGlow(TameableDragonEntity dragon) {
        return this.glow;
    }
}
