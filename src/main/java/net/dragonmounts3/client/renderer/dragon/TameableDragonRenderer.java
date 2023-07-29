package net.dragonmounts3.client.renderer.dragon;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts3.client.model.dragon.DragonModel;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EnderCrystalRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraft.client.renderer.entity.EnderDragonRenderer.renderCrystalBeams;

public class TameableDragonRenderer extends LivingRenderer<TameableDragonEntity, DragonModel> {

    public TameableDragonRenderer(EntityRendererManager entityRenderDispatcher) {
        super(entityRenderDispatcher, new DragonModel(), 0);
        this.addLayer(new TameableDragonLayer(this));
    }

    @Nullable
    @Override
    protected RenderType getRenderType(@Nonnull TameableDragonEntity dragon, boolean visible, boolean invisibleToClient, boolean glowing) {
        // During death, do not use the standard rendering and let the death layer handle it. Hacky, but better than mixins.
        return dragon.deathTime > 0 ? null : super.getRenderType(dragon, visible, invisibleToClient, glowing);
    }

    @Override
    protected void setupRotations(@Nonnull TameableDragonEntity dragon, @Nonnull MatrixStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(dragon, matrixStack, ageInTicks, rotationYaw, partialTicks);
        float scale = dragon.getScale() * dragon.getVariant().modelRenderScale;
        matrixStack.scale(scale, scale, scale);
        this.shadowRadius = dragon.isBaby() ? 4 * scale : 2 * scale;
        matrixStack.translate(dragon.animator.getModelOffsetX(), dragon.animator.getModelOffsetY(), dragon.animator.getModelOffsetZ());
        matrixStack.translate(0, 1.5, 0.5); // change rotation point
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(dragon.animator.getModelPitch(partialTicks))); // rotate near the saddle so we can support the player
        matrixStack.translate(0, -1.5, -0.5); // restore rotation point
    }

    @Override
    public void render(@Nonnull TameableDragonEntity dragon, float entityYaw, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int packedLight) {
        if (dragon.nearestCrystal != null) {
            matrixStack.pushPose();
            float x = (float) (dragon.nearestCrystal.getX() - MathHelper.lerp(partialTicks, dragon.xo, dragon.getX()));
            float y = (float) (dragon.nearestCrystal.getY() - MathHelper.lerp(partialTicks, dragon.yo, dragon.getY()));
            float z = (float) (dragon.nearestCrystal.getZ() - MathHelper.lerp(partialTicks, dragon.zo, dragon.getZ()));
            renderCrystalBeams(x, y + EnderCrystalRenderer.getY(dragon.nearestCrystal, partialTicks), z, partialTicks, dragon.tickCount, matrixStack, buffer, packedLight);
            matrixStack.popPose();
        }
        super.render(dragon, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    protected float getFlipDegrees(@Nonnull TameableDragonEntity dragon) {
        // dragons dissolve during death, not flip.
        return 0;
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull TameableDragonEntity dragon) {
        return dragon.getVariant().getBody(dragon);
    }

    @Override
    protected boolean shouldShowName(TameableDragonEntity dragon) {
        return (dragon.shouldShowName() || dragon.hasCustomName() && dragon == this.entityRenderDispatcher.crosshairPickEntity) && super.shouldShowName(dragon);
    }
}
