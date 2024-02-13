package net.dragonmounts.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.inventory.DragonInventoryContainer;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

import static net.dragonmounts.DragonMounts.prefix;

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
    public void render(@Nonnull MatrixStack matrices, int x, int y, float ticks) {
        this.renderBackground(matrices);
        super.render(matrices, x, y, ticks);
        this.renderTooltip(matrices, x, y);
    }

    @Override
    protected void renderBg(@Nonnull MatrixStack matrices, float ticks, int x, int y) {
        //noinspection DataFlowIssue
        this.minecraft.getTextureManager().bind(TEXTURE_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.width - this.imageWidth) >> 1;
        int j = (this.height - this.imageHeight) >> 1;
        this.blit(matrices, i, j, 0, 0, this.imageWidth, this.imageHeight);
        final TameableDragonEntity dragon = this.menu.dragon;
        final EnderCrystalEntity crystal = dragon.nearestCrystal;
        if (dragon.hasChest()) {
            this.blit(matrices, (this.width - 162) >> 1, (this.height - 74) >> 1, 7, 141, 162, 54);
        }
        dragon.nearestCrystal = null;// to disable crystal beam
        InventoryScreen.renderEntityInInventory(i + 60, j + 62, 5, i - x + 60F, j - y + 13F, dragon);
        dragon.nearestCrystal = crystal;
    }
}
