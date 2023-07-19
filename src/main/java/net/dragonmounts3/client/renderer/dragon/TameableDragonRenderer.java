package net.dragonmounts3.client.renderer.dragon;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts3.client.DragonAnimator;
import net.dragonmounts3.client.model.dragon.DragonModel;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TameableDragonRenderer extends LivingRenderer<TameableDragonEntity, DragonModel> {

    public TameableDragonRenderer(EntityRendererManager entityRenderDispatcher) {
        super(entityRenderDispatcher, new DragonModel(), 0);
        this.addLayer(new TameableDragonLayer(this));
    }

    // During death, do not use the standard rendering and let the death layer handle it. Hacky, but better than mixins.
    @Nullable
    @Override
    protected RenderType getRenderType(@Nonnull TameableDragonEntity dragon, boolean visible, boolean invisibleToClient, boolean glowing) {
        return super.getRenderType(dragon, visible, invisibleToClient, glowing);
    }

    @Override
    protected void setupRotations(@Nonnull TameableDragonEntity dragon, @Nonnull MatrixStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(dragon, matrixStack, ageInTicks, rotationYaw, partialTicks);
        DragonAnimator animator = dragon.animator;
        float scale = dragon.getScale() * dragon.getDragonType().resources.modelRenderScale;
        matrixStack.scale(scale, scale, scale);
        this.shadowRadius = dragon.isBaby() ? 4 * scale : 2 * scale;
        matrixStack.translate(animator.getModelOffsetX(), animator.getModelOffsetY(), animator.getModelOffsetZ());
        matrixStack.translate(0, 1.5, 0.5); // change rotation point
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(animator.getModelPitch(partialTicks))); // rotate near the saddle so we can support the player
        matrixStack.translate(0, -1.5, -0.5); // restore rotation point
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull TameableDragonEntity dragon) {
        return dragon.getDragonType().resources.getBody(dragon);
    }

    @Override
    protected boolean shouldShowName(TameableDragonEntity dragon) {
        return (dragon.shouldShowName() || dragon.hasCustomName() && dragon == this.entityRenderDispatcher.crosshairPickEntity) && super.shouldShowName(dragon);
    }
}
