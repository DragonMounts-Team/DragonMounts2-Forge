package net.dragonmounts3.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.client.model.CarriageModel;
import net.dragonmounts3.objects.entity.carriage.EntityCarriage;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class CarriageRenderer extends EntityRenderer<EntityCarriage> {
    protected final CarriageModel model = new CarriageModel();

    private static final ResourceLocation[] CARRIAGE_TEXTURES = new ResourceLocation[]{
            DragonMounts.prefix("textures/entities/dragon_carriage/carriage_oak.png"),
            DragonMounts.prefix("textures/entities/dragon_carriage/carriage_spruce.png"),
            DragonMounts.prefix("textures/entities/dragon_carriage/carriage_birch.png"),
            DragonMounts.prefix("textures/entities/dragon_carriage/carriage_jungle.png"),
            DragonMounts.prefix("textures/entities/dragon_carriage/carriage_acacia.png"),
            DragonMounts.prefix("textures/entities/dragon_carriage/carriage_dark_oak.png"),
            DragonMounts.prefix("textures/entities/dragon_carriage/carriage_oak.png"),/*crimson*/
            DragonMounts.prefix("textures/entities/dragon_carriage/carriage_oak.png")/*warped*/};

    public CarriageRenderer(EntityRendererManager entityRenderDispatcher) {
        super(entityRenderDispatcher);
        this.shadowRadius = 0.2F;
    }

    @Override
    public void render(@Nonnull EntityCarriage entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        matrixStack.pushPose();
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        //this.setupRotation(entity, matrixStack, entityYaw, partialTicks);

        //BoatRenderer
        IVertexBuilder ivertexbuilder = buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity)));
        this.model.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @SuppressWarnings("deprecation")
    private void setupRotation(EntityCarriage entity, MatrixStack matrixStack, float entityYaw, float partialTicks) {
        RenderSystem.rotatef(90.0F - entityYaw, 0.0F, 1.0F, 0.0F);
        float f = (float) entity.getTimeSinceHit() - partialTicks;
        float f1 = entity.getDamage() - partialTicks;

        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            RenderSystem.rotatef(MathHelper.sin(f) * f * f1 / 10.0F * (float) entity.getForwardDirection(), 1.0F, 0.0F, 0.0F);
        }

        matrixStack.scale(-0.8F, -0.8F, 0.8F);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(EntityCarriage entity) {
        return CARRIAGE_TEXTURES[entity.getCarriageType().ordinal()];
    }

}