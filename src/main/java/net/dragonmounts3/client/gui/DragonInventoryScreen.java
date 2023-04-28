package net.dragonmounts3.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts3.inventory.DragonInventoryContainer;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

/**
 * @see net.minecraft.client.gui.screen.inventory.HorseInventoryScreen
 */
public class DragonInventoryScreen extends DisplayEffectsScreen<DragonInventoryContainer> {
    public DragonInventoryScreen(DragonInventoryContainer menu, PlayerInventory playerInventory, ITextComponent title) {
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

    }
}
