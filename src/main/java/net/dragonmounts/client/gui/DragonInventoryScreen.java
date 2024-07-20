package net.dragonmounts.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dragonmounts.client.ClientDragonEntity;
import net.dragonmounts.inventory.DragonInventoryContainer;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

import static net.dragonmounts.DragonMounts.makeId;
import static net.minecraft.client.gui.screen.inventory.InventoryScreen.renderEntityInInventory;

/**
 * @see net.minecraft.client.gui.screen.inventory.HorseInventoryScreen
 */
public class DragonInventoryScreen extends DisplayEffectsScreen<DragonInventoryContainer> {
    private static final ResourceLocation TEXTURE_LOCATION = makeId("textures/gui/dragon.png");

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
        //noinspection deprecation
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        int left = this.leftPos;
        int top = this.topPos;
        this.blit(matrices, left, top, 0, 0, this.imageWidth, this.imageHeight);
        final ClientDragonEntity dragon = (ClientDragonEntity) this.menu.dragon;
        if (dragon.hasChest()) {
            this.blit(matrices, left + 7, top + 75, 7, 141, 162, 54);
        }
        dragon.renderCrystalBeams = false;// to disable crystal beam
        renderEntityInInventory(left + 60, top + 62, 5, left - x + 60F, top - y + 13F, dragon);
        dragon.renderCrystalBeams = true;
    }
}
