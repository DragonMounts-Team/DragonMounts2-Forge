package net.dragonmounts.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts.block.DragonCoreBlock;
import net.dragonmounts.client.renderer.block.DragonCoreRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class DMItemStackTileEntityRenderer extends ItemStackTileEntityRenderer {
    private static final ShulkerModel<?> DRAGON_CORE_MODEL = new ShulkerModel<>();
    public static final DMItemStackTileEntityRenderer INSTANCE = new DMItemStackTileEntityRenderer();

    public static DMItemStackTileEntityRenderer getInstance() {
        return INSTANCE;
    }

    private DMItemStackTileEntityRenderer() {
    }

    @Override
    public void renderByItem(@Nonnull ItemStack itemStack, @Nonnull ItemCameraTransforms.TransformType type, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        Item item = itemStack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            if (block instanceof DragonCoreBlock) {
                DragonCoreRenderer.render(Direction.SOUTH, DRAGON_CORE_MODEL, 0.0F, matrixStack, buffer, combinedLight, combinedOverlay);
            }/* else {
                TileEntityRendererDispatcher.instance.renderItem(tileEntity, matrixStack, buffer, combinedLight, combinedOverlay);
            }*/
        }
    }
}