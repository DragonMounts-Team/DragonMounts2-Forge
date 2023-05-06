package net.dragonmounts3.client.renderer.dragon;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dragonmounts3.client.model.dragon.DragonModel;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;

public class TameableDragonRenderer extends LivingRenderer<TameableDragonEntity, DragonModel> {
    protected DragonType typeSnapshot = null;
    protected TextureManager manager = TextureManager.ENDER;

    public TameableDragonRenderer(EntityRendererManager entityRenderDispatcher) {
        super(entityRenderDispatcher, new DragonModel(), 0);
    }

    @Override
    public void render(@Nonnull TameableDragonEntity dragon, float entityYaw, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int packedLight) {
        if (MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Pre<>(dragon, this, partialTicks, matrixStack, buffer, packedLight)))
            return;
        DragonType type = dragon.getDragonType();
        if (type != this.typeSnapshot) {
            this.model.onTypeChanged(typeSnapshot, type);
            this.manager = TextureManager.get(type);
            this.typeSnapshot = type;
        }
        matrixStack.pushPose();
        this.model.attackTime = this.getAttackAnim(dragon, partialTicks);
        boolean shouldSit = dragon.isPassenger() && (dragon.getVehicle() != null && dragon.getVehicle().shouldRiderSit());
        this.model.riding = shouldSit;
        float f = MathHelper.rotLerp(partialTicks, dragon.yBodyRotO, dragon.yBodyRot);
        float f1 = MathHelper.rotLerp(partialTicks, dragon.yHeadRotO, dragon.yHeadRot);
        float f2 = f1 - f;
        Entity vehicle = dragon.getVehicle();
        if (shouldSit && vehicle instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) vehicle;
            f = MathHelper.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
            f2 = f1 - f;
            float f3 = MathHelper.wrapDegrees(f2);
            if (f3 < -85.0F) {
                f3 = -85.0F;
            }
            if (f3 >= 85.0F) {
                f3 = 85.0F;
            }
            f = f1 - f3;
            if (f3 * f3 > 2500.0F) {
                f += f3 * 0.2F;
            }
            f2 = f1 - f;
        }
        float f6 = MathHelper.lerp(partialTicks, dragon.xRotO, dragon.xRot);
        if (dragon.getPose() == Pose.SLEEPING) {
            Direction direction = dragon.getBedOrientation();
            if (direction != null) {
                float f4 = dragon.getEyeHeight(Pose.STANDING) - 0.1F;
                matrixStack.translate(-direction.getStepX() * f4, 0.0D, -direction.getStepZ() * f4);
            }
        }

        float f7 = this.getBob(dragon, partialTicks);
        this.setupRotations(dragon, matrixStack, f7, f, partialTicks);
        float scale = dragon.getScale();
        matrixStack.scale(-scale, -scale, scale);
        matrixStack.translate(0.0D, -1.5D, 0.0D);
        float f8 = 0.0F;
        float f5 = 0.0F;
        if (!shouldSit && dragon.isAlive()) {
            f8 = MathHelper.lerp(partialTicks, dragon.animationSpeedOld, dragon.animationSpeed);
            f5 = dragon.animationPosition - dragon.animationSpeed * (1.0F - partialTicks);
            if (dragon.isBaby()) {
                f5 *= 3.0F;
            }
            if (f8 > 1.0F) {
                f8 = 1.0F;
            }
        }

        this.model.prepareMobModel(dragon, f5, f8, partialTicks);
        this.model.setupAnim(dragon, f5, f8, f7, f2, f6);
        Minecraft minecraft = Minecraft.getInstance();
        ClientPlayerEntity player = minecraft.player;
        boolean flag = this.isBodyVisible(dragon);
        boolean flag1 = player == null || !flag && !dragon.isInvisibleTo(player);
        boolean flag2 = minecraft.shouldEntityAppearGlowing(dragon);
        RenderType rendertype = this.getRenderType(dragon, flag, flag1, flag2);
        if (rendertype != null) {
            IVertexBuilder ivertexbuilder = buffer.getBuffer(rendertype);
            int i = getOverlayCoords(dragon, this.getWhiteOverlayProgress(dragon, partialTicks));
            this.model.renderToBuffer(matrixStack, ivertexbuilder, packedLight, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
        }

        for (LayerRenderer<TameableDragonEntity, DragonModel> renderer : this.layers) {
            renderer.render(matrixStack, buffer, packedLight, dragon, f5, f8, partialTicks, f7, f2, f6);
        }

        matrixStack.popPose();
        RenderNameplateEvent renderNameplateEvent = new RenderNameplateEvent(dragon, dragon.getDisplayName(), this, matrixStack, buffer, packedLight, partialTicks);
        Event.Result result = renderNameplateEvent.getResult();
        MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
        if (result != Event.Result.DENY && (result == Event.Result.ALLOW || this.shouldShowName(dragon))) {
            this.renderNameTag(dragon, renderNameplateEvent.getContent(), matrixStack, buffer, packedLight);
        }
        MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Post<>(dragon, this, partialTicks, matrixStack, buffer, packedLight));
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull TameableDragonEntity dragon) {
        return this.manager.body;
    }

    @Override
    protected boolean shouldShowName(TameableDragonEntity dragon) {
        return (dragon.shouldShowName() || dragon.hasCustomName() && dragon == this.entityRenderDispatcher.crosshairPickEntity) && super.shouldShowName(dragon);
    }
}
