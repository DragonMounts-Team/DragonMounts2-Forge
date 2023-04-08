package net.dragonmounts3.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts3.client.renderer.block.DragonCoreRenderer;
import net.dragonmounts3.objects.blocks.BlockDragonCore;
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
public class ModItemStackTileEntityRenderer extends ItemStackTileEntityRenderer {
    private static final ShulkerModel<?> DRAGON_CORE_MODEL = new ShulkerModel<>();
    public static final ModItemStackTileEntityRenderer INSTANCE = new ModItemStackTileEntityRenderer();

    public static ModItemStackTileEntityRenderer getInstance() {
        return INSTANCE;
    }

    private ModItemStackTileEntityRenderer() {
    }

    @Override
    public void renderByItem(@Nonnull ItemStack itemStack, @Nonnull ItemCameraTransforms.TransformType type, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        Item item = itemStack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            if (block instanceof BlockDragonCore) {
                DragonCoreRenderer.render(Direction.SOUTH, DRAGON_CORE_MODEL, 0.0F, matrixStack, buffer, combinedLight, combinedOverlay);
            }/* else {
                TileEntityRendererDispatcher.instance.renderItem(tileEntity, matrixStack, buffer, combinedLight, combinedOverlay);
            }*/
        }
    }
}