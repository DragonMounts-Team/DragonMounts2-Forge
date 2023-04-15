package net.dragonmounts3.client.renderer;

import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.entity.dragon.DragonEggEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static net.dragonmounts3.DragonMounts.prefix;

public class DragonEggRenderer extends EntityRenderer<DragonEggEntity> {
    private static final ResourceLocation[] DRAGON_EGG_TEXTURES = new ResourceLocation[DragonType.values().length];

    static {
        for (DragonType type : DragonType.values()) {
            DRAGON_EGG_TEXTURES[type.ordinal()] = prefix("textures/entities/dragon/" + type.getSerializedName() + "/egg.png");
        }
    }

    public DragonEggRenderer(EntityRendererManager entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(DragonEggEntity entity) {
        return DRAGON_EGG_TEXTURES[entity.getDragonTypeId()];
    }
}
