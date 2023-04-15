package net.dragonmounts3.client.renderer;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class TameableDragonRenderer extends EntityRenderer<TameableDragonEntity> {


    protected TameableDragonRenderer(EntityRendererManager entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull TameableDragonEntity entity) {
        return null;
    }
}
