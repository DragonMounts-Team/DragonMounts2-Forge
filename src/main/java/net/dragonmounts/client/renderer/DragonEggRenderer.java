package net.dragonmounts.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.MatrixApplyingVertexBuilder;
import net.dragonmounts.block.HatchableDragonEggBlock;
import net.dragonmounts.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts.init.DMBlocks;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.data.EmptyModelData;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.dragonmounts.entity.dragon.HatchableDragonEggEntity.EGG_CRACK_THRESHOLD;
import static net.dragonmounts.entity.dragon.HatchableDragonEggEntity.MIN_HATCHING_TIME;
import static net.dragonmounts.util.math.MathUtil.HALF_RAD_FACTOR;

/**
 * @see net.minecraft.client.renderer.entity.FallingBlockRenderer
 */
public class DragonEggRenderer extends EntityRenderer<HatchableDragonEggEntity> {
    public DragonEggRenderer(EntityRendererManager dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(@Nonnull HatchableDragonEggEntity entity, float entityYaw, float partialTicks, @Nonnull MatrixStack matrices, @Nonnull IRenderTypeBuffer buffer, int light) {
        HatchableDragonEggBlock block = entity.getDragonType().getInstance(HatchableDragonEggBlock.class, DMBlocks.ENDER_DRAGON_EGG);
        BlockState state = block.defaultBlockState();
        if (state.getRenderShape() != BlockRenderType.INVISIBLE) {
            World level = entity.level;
            BlockPos pos = new BlockPos(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
            BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
            IBakedModel model = dispatcher.getBlockModel(state);
            BlockModelRenderer renderer = dispatcher.getModelRenderer();
            Random random = level.random;
            final long seed = state.getSeed(pos);
            matrices.pushPose();
            float angle = entity.getAmplitude(partialTicks);
            if (angle != 0) {
                float axis = entity.getRotationAxis();
                float half = angle * HALF_RAD_FACTOR;
                float sin = MathHelper.sin(half);
                matrices.mulPose(new Quaternion(MathHelper.cos(axis) * sin, 0.0F, MathHelper.sin(axis) * sin, MathHelper.cos(half)));
            }
            matrices.translate(-0.5D, 0.0D, -0.5D);
            int stage = entity.getAge();
            if (stage >= EGG_CRACK_THRESHOLD) {
                stage = Math.min((stage - EGG_CRACK_THRESHOLD) * 90 / MIN_HATCHING_TIME, 9);
                for (RenderType type : RenderType.chunkBufferLayers()) {
                    if (RenderTypeLookup.canRenderInLayer(state, type)) {
                        ForgeHooksClient.setRenderLayer(type);
                        renderer.renderModel(level, model, state, pos, matrices, buffer.getBuffer(type), false, random, seed, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
                        MatrixStack.Entry last = matrices.last();
                        renderer.renderModel(level, model, state, pos, matrices, new MatrixApplyingVertexBuilder(
                                buffer.getBuffer(ModelBakery.DESTROY_TYPES.get(stage)), last.pose(), last.normal()
                        ), true, random, seed, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
                    }
                }
            } else for (RenderType type : RenderType.chunkBufferLayers()) {
                if (RenderTypeLookup.canRenderInLayer(state, type)) {
                    ForgeHooksClient.setRenderLayer(type);
                    renderer.renderModel(level, model, state, pos, matrices, buffer.getBuffer(type), false, random, seed, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
                }
            }
            ForgeHooksClient.setRenderLayer(null);
            matrices.popPose();
            super.render(entity, entityYaw, partialTicks, matrices, buffer, light);
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull HatchableDragonEggEntity entity) {
        return PlayerContainer.BLOCK_ATLAS;
    }
}
