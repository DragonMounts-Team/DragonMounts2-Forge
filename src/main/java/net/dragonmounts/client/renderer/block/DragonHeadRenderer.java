package net.dragonmounts.client.renderer.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts.block.AbstractDragonHeadBlock;
import net.dragonmounts.block.entity.DragonHeadBlockEntity;
import net.dragonmounts.client.model.dragon.DragonHeadModel;
import net.dragonmounts.client.variant.VariantAppearance;
import net.dragonmounts.client.variant.VariantAppearances;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;

import javax.annotation.Nonnull;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class DragonHeadRenderer extends TileEntityRenderer<DragonHeadBlockEntity> {
    private static final DragonHeadModel DRAGON_HEAD_MODEL = new DragonHeadModel();

    public DragonHeadRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(@Nonnull DragonHeadBlockEntity entity, float ticks, @Nonnull MatrixStack matrices, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        final BlockState state = entity.getBlockState();
        final Block block = state.getBlock();
        if (block instanceof AbstractDragonHeadBlock) {
            final AbstractDragonHeadBlock head = (AbstractDragonHeadBlock) block;
            if (head.isOnWall) {
                final Direction direction = state.getValue(HORIZONTAL_FACING);
                renderHead(
                        head.variant.getAppearance(VariantAppearances.ENDER_FEMALE),
                        0.5D - direction.getStepX() * 0.25D,
                        0.25D,
                        0.5D - direction.getStepZ() * 0.25D,
                        entity.getAnimation(ticks),
                        head.getYRotation(state),
                        0.75F,
                        true,
                        matrices,
                        buffer,
                        light,
                        overlay
                );
            } else {
                renderHead(
                        head.variant.getAppearance(VariantAppearances.ENDER_FEMALE),
                        0.5D,
                        0D,
                        0.5D,
                        entity.getAnimation(ticks),
                        head.getYRotation(state),
                        0.75F,
                        true,
                        matrices,
                        buffer,
                        light,
                        overlay
                );
            }
        }
    }

    public static void renderHead(VariantAppearance appearance, double offsetX, double offsetY, double offsetZ, float ticks, float yaw, float scale, boolean flip, MatrixStack matrices, IRenderTypeBuffer buffer, int light, int overlay) {
        matrices.pushPose();
        matrices.translate(offsetX, offsetY, offsetZ);
        if (flip) {
            matrices.scale(1.0F, -1.0F, -1.0F);
        }
        DRAGON_HEAD_MODEL.setupAnim(ticks, yaw, 0F, scale);
        DRAGON_HEAD_MODEL.renderToBuffer(matrices, buffer.getBuffer(appearance.getBodyForBlock()), light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        DRAGON_HEAD_MODEL.renderToBuffer(matrices, buffer.getBuffer(appearance.getGlowForBlock()), 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.popPose();
    }
}
