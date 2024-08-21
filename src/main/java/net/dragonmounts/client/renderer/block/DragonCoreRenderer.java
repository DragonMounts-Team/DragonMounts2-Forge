package net.dragonmounts.client.renderer.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dragonmounts.block.entity.DragonCoreBlockEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static net.dragonmounts.DragonMounts.makeId;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

/**
 * @see net.minecraft.client.renderer.tileentity.ShulkerBoxTileEntityRenderer
 */
@OnlyIn(Dist.CLIENT)
public class DragonCoreRenderer extends TileEntityRenderer<DragonCoreBlockEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = makeId("textures/blocks/dragon_core.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);
    private final ShulkerModel<?> model = new ShulkerModel<>();

    public DragonCoreRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(@Nonnull DragonCoreBlockEntity core, float partialTicks, @Nonnull MatrixStack matrices, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        render(core.getBlockState().getValue(HORIZONTAL_FACING), this.model, core.getProgress(partialTicks), matrices, buffer, light, overlay);
    }

    public static void render(Direction direction, ShulkerModel<?> model, float progress, @Nonnull MatrixStack matrices, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        IVertexBuilder builder = buffer.getBuffer(RENDER_TYPE);
        matrices.pushPose();
        matrices.translate(0.5D, 0.5D, 0.5D);
        matrices.scale(0.9995F, 0.9995F, 0.9995F);
        matrices.scale(1.0F, -1.0F, -1.0F);
        matrices.translate(0.0D, -1.0D, 0.0D);
        matrices.mulPose(Vector3f.YP.rotationDegrees(direction.toYRot()));
        model.getBase().render(matrices, builder, light, overlay);
        matrices.translate(0.0D, -progress * 0.5D, 0.0D);
        matrices.mulPose(Vector3f.YP.rotationDegrees(270.0F * progress));
        model.getLid().render(matrices, builder, light, overlay);
        matrices.popPose();
    }
}
