package net.dragonmounts3.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.inventory.ContainerDragonShulkerBox;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static net.dragonmounts3.DragonMounts.prefix;

/**
 * @see net.minecraft.client.gui.screen.inventory.ShulkerBoxScreen
 */
@OnlyIn(Dist.CLIENT)
public class ScreenDragonShulkerBox extends ContainerScreen<ContainerDragonShulkerBox> {
    private static final ResourceLocation CONTAINER_TEXTURE = prefix("textures/gui/dragon_shulker_box.png");

    public ScreenDragonShulkerBox(ContainerDragonShulkerBox menu, PlayerInventory playerInventory, ITextComponent title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y) {
        if (this.minecraft != null) {
            this.minecraft.getTextureManager().bind(CONTAINER_TEXTURE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int i = (this.width - this.imageWidth) / 2;
            int j = (this.height - this.imageHeight) / 2;
            this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        }
    }
}
