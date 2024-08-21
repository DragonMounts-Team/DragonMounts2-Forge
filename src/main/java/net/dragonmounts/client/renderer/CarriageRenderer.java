package net.dragonmounts.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dragonmounts.client.model.CarriageModel;
import net.dragonmounts.entity.CarriageEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class CarriageRenderer extends EntityRenderer<CarriageEntity> {
    protected final CarriageModel model = new CarriageModel();

    public CarriageRenderer(EntityRendererManager dispatcher) {
        super(dispatcher);
        this.shadowRadius = 0.2F;
    }

    @Override
    public void render(@Nonnull CarriageEntity entity, float entityYaw, float partialTicks, MatrixStack matrices, IRenderTypeBuffer buffer, int light) {
        matrices.pushPose();
        matrices.scale(-1.0F, -1.0F, 1.0F);
        //this.setupRotation(entity, matrices, entityYaw, partialTicks);

        //BoatRenderer
        IVertexBuilder ivertexbuilder = buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity)));
        this.model.renderToBuffer(matrices, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.popPose();
        super.render(entity, entityYaw, partialTicks, matrices, buffer, light);
    }

    @SuppressWarnings("deprecation")
    private void setupRotation(CarriageEntity entity, MatrixStack matrices, float entityYaw, float partialTicks) {
        RenderSystem.rotatef(90.0F - entityYaw, 0.0F, 1.0F, 0.0F);
        float f = (float) entity.getTimeSinceHit() - partialTicks;
        float f1 = Math.max(entity.getDamage() - partialTicks, 0F);
        if (f > 0.0F) {
            RenderSystem.rotatef(MathHelper.sin(f) * f * f1 / 10.0F * (float) entity.getForwardDirection(), 1.0F, 0.0F, 0.0F);
        }
        matrices.scale(-0.8F, -0.8F, 0.8F);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(CarriageEntity entity) {
        return entity.getCarriageType().getTexture(entity);
    }

}