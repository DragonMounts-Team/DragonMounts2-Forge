package net.dragonmounts3.api.variant;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class SculkVariant extends AbstractVariant {
    public final ResourceLocation body;
    public final RenderType decal;
    public final String name;

    public SculkVariant(String namespace, String path) {
        super(false, false, 1.6F);
        this.body = new ResourceLocation(namespace, TEXTURES_ROOT + path + "/body.png");
        this.decal = RenderType.entityDecal(this.body);
        this.name = path.substring(path.indexOf('/') + 1);
    }

    @Override
    public ResourceLocation getBody(TameableDragonEntity dragon) {
        return this.body;
    }

    @Override
    public RenderType getGlow(TameableDragonEntity dragon) {
        return DragonVariants.ENDER_FEMALE.getGlow(dragon);
    }

    @Override
    public RenderType getDecal(TameableDragonEntity dragon) {
        return this.decal;
    }

    @Override
    public RenderType getGlowDecal(TameableDragonEntity dragon) {
        return DragonVariants.ENDER_FEMALE.getGlowDecal(dragon);
    }


    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }
}