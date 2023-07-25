package net.dragonmounts3.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dragonmounts3.inventory.DragonInventoryContainer;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

import static net.dragonmounts3.DragonMounts.prefix;

/**
 * @see net.minecraft.client.gui.screen.inventory.HorseInventoryScreen
 */
public class DragonInventoryScreen extends DisplayEffectsScreen<DragonInventoryContainer> {
    private static final ResourceLocation TEXTURE_LOCATION = prefix("textures/gui/dragon.png");

    public DragonInventoryScreen(DragonInventoryContainer menu, PlayerInventory playerInventory, ITextComponent title) {
        super(menu, playerInventory, title);
        this.passEvents = false;
        this.imageHeight = 224;
        this.inventoryLabelY = 131;//224 - 94 + 1
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y) {
        if (this.minecraft == null) return;
        this.minecraft.getTextureManager().bind(TEXTURE_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.width - this.imageWidth) >> 1;
        int j = (this.height - this.imageHeight) >> 1;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if (this.menu.dragon.hasChest()) {
            this.blit(matrixStack, (this.width - 162) >> 1, (this.height - 74) >> 1, 7, 141, 162, 54);
        }
        InventoryScreen.renderEntityInInventory(i + 60, j + 62, 5, (float) (i + 60) - x, (float) (j + 75 - 62) - y, this.menu.dragon);

    }
}
