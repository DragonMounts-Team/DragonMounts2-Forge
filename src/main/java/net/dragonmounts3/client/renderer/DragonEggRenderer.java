package net.dragonmounts3.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts3.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts3.init.DMBlocks;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.data.EmptyModelData;

import javax.annotation.Nonnull;

/**
 * @see net.minecraft.client.renderer.entity.FallingBlockRenderer
 */
public class DragonEggRenderer extends EntityRenderer<HatchableDragonEggEntity> {
    public DragonEggRenderer(EntityRendererManager entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public void render(@Nonnull HatchableDragonEggEntity entity, float entityYaw, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int packedLight) {
        DragonEggBlock block = DMBlocks.HATCHABLE_DRAGON_EGG.get(entity.getDragonType());
        if (block != null) {
            BlockState blockstate = block.defaultBlockState();
            World world = entity.level;
            if (blockstate.getRenderShape() != BlockRenderType.INVISIBLE) {
                BlockPos pos = new BlockPos(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
                float wriggleX = entity.getWriggleX();
                float wriggleZ = entity.getWriggleZ();
                matrixStack.pushPose();
                if (wriggleX > 0) {
                    matrixStack.mulPose(Vector3f.XP.rotationDegrees((float) (Math.sin(wriggleX - partialTicks) * 8)));
                }
                if (wriggleZ > 0) {
                    matrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) (Math.sin(wriggleZ - partialTicks) * 8)));
                }
                matrixStack.translate(-0.5D, 0.0D, -0.5D);
                BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
                for (RenderType type : RenderType.chunkBufferLayers()) {
                    if (RenderTypeLookup.canRenderInLayer(blockstate, type)) {
                        ForgeHooksClient.setRenderLayer(type);
                        dispatcher.getModelRenderer().renderModel(world, dispatcher.getBlockModel(blockstate), blockstate, pos, matrixStack, buffer.getBuffer(type), false, world.random, blockstate.getSeed(pos), OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
                    }
                }
                ForgeHooksClient.setRenderLayer(null);
                matrixStack.popPose();
                super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
            }
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull HatchableDragonEggEntity entity) {
        return PlayerContainer.BLOCK_ATLAS;
    }
}
