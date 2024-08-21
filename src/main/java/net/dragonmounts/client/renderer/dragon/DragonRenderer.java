package net.dragonmounts.client.renderer.dragon;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts.client.ClientDragonEntity;
import net.dragonmounts.client.DragonRendererContext;
import net.dragonmounts.client.model.dragon.DragonModel;
import net.dragonmounts.client.variant.VariantAppearances;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EnderCrystalRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraft.client.renderer.entity.EnderDragonRenderer.renderCrystalBeams;

public class DragonRenderer extends LivingRenderer<ClientDragonEntity, DragonModel> {
    public DragonRenderer(EntityRendererManager dispatcher) {
        super(dispatcher, new DragonModel(), 0);
        this.addLayer(new DragonLayer(this));
    }

    @Nullable
    @Override
    protected RenderType getRenderType(@Nonnull ClientDragonEntity dragon, boolean visible, boolean invisibleToClient, boolean glowing) {
        // During death, do not use the standard rendering and let the death layer handle it. Hacky, but better than mixins.
        return dragon.deathTime > 0 ? null : super.getRenderType(dragon, visible, invisibleToClient, glowing);
    }

    @Override
    protected void setupRotations(@Nonnull ClientDragonEntity dragon, @Nonnull MatrixStack matrices, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(dragon, matrices, ageInTicks, rotationYaw, partialTicks);
        float scale = dragon.getScale() * dragon.getVariant().getAppearance(VariantAppearances.ENDER_FEMALE).renderScale;
        matrices.scale(scale, scale, scale);
        this.shadowRadius = dragon.isBaby() ? 4 * scale : 2 * scale;
        DragonRendererContext context = dragon.context;
        matrices.translate(context.getModelOffsetX(), context.getModelOffsetY(), context.getModelOffsetZ());
        matrices.translate(0, 1.5, 0.5); // change rotation point
        matrices.mulPose(Vector3f.XP.rotationDegrees(context.getModelPitch(partialTicks))); // rotate near the saddle so we can support the player
        matrices.translate(0, -1.5, -0.5); // restore rotation point
    }

    @Override
    public void render(@Nonnull ClientDragonEntity dragon, float entityYaw, float partialTicks, @Nonnull MatrixStack matrices, @Nonnull IRenderTypeBuffer buffer, int light) {
        if (dragon.renderCrystalBeams) {
            EnderCrystalEntity crystal = dragon.nearestCrystal;
            if (crystal != null) {
                matrices.pushPose();
                float x = (float) (crystal.getX() - MathHelper.lerp(partialTicks, dragon.xo, dragon.getX()));
                float y = (float) (crystal.getY() - MathHelper.lerp(partialTicks, dragon.yo, dragon.getY()));
                float z = (float) (crystal.getZ() - MathHelper.lerp(partialTicks, dragon.zo, dragon.getZ()));
                renderCrystalBeams(x, y + EnderCrystalRenderer.getY(crystal, partialTicks), z, partialTicks, dragon.tickCount, matrices, buffer, light);
                matrices.popPose();
            }
        }
        super.render(dragon, entityYaw, partialTicks, matrices, buffer, light);
    }

    @Override
    protected float getFlipDegrees(@Nonnull ClientDragonEntity dragon) {
        // dragons dissolve during death, not flip.
        return 0;
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull ClientDragonEntity dragon) {
        return dragon.getVariant().getAppearance(VariantAppearances.ENDER_FEMALE).getBody(dragon);
    }

    @Override
    protected boolean shouldShowName(ClientDragonEntity dragon) {
        return (dragon.shouldShowName() || dragon.hasCustomName() && dragon == this.entityRenderDispatcher.crosshairPickEntity) && super.shouldShowName(dragon);
    }
}
