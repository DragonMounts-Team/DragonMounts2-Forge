package net.dragonmounts.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts.block.AbstractDragonHeadBlock;
import net.dragonmounts.block.DragonCoreBlock;
import net.dragonmounts.client.renderer.block.DragonCoreRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static net.dragonmounts.client.renderer.block.DragonHeadRenderer.renderHead;
import static net.dragonmounts.client.variant.VariantAppearances.ENDER_FEMALE;

@OnlyIn(Dist.CLIENT)
public class DMItemStackTileEntityRenderer extends ItemStackTileEntityRenderer {
    private static final ShulkerModel<?> DRAGON_CORE_MODEL = new ShulkerModel<>();
    public static final DMItemStackTileEntityRenderer INSTANCE = new DMItemStackTileEntityRenderer();

    public static DMItemStackTileEntityRenderer getInstance() {
        return INSTANCE;
    }

    private DMItemStackTileEntityRenderer() {}

    @Override
    public void renderByItem(@Nonnull ItemStack stack, @Nonnull TransformType type, @Nonnull MatrixStack matrices, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        Item item = stack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            if (block instanceof DragonCoreBlock) {
                DragonCoreRenderer.render(Direction.SOUTH, DRAGON_CORE_MODEL, 0.0F, matrices, buffer, light, overlay);
            } else if (block instanceof AbstractDragonHeadBlock) {
                if (type == TransformType.HEAD) {
                    renderHead(((AbstractDragonHeadBlock) block).variant.getAppearance(ENDER_FEMALE), 0.5D, 0.4375D, 0.5D, 180F, 180F, 1.425F, true, matrices, buffer, light, overlay);
                } else {
                    renderHead(((AbstractDragonHeadBlock) block).variant.getAppearance(ENDER_FEMALE), 0.5D, 0D, 0.5D, 180F, 0F, 0.75F, true, matrices, buffer, light, overlay);
                }
            }
        }
    }
}