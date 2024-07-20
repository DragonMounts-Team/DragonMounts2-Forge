package net.dragonmounts.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dragonmounts.api.IArmorEffect;
import net.dragonmounts.api.IDragonScaleArmorEffect;
import net.dragonmounts.capability.ArmorEffectManager;
import net.dragonmounts.item.DragonScaleArmorItem;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow
    private void fillRect(BufferBuilder buffer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {}

    @Inject(
            method = "renderGuiItemDecorations(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getInstance()Lnet/minecraft/client/Minecraft;", ordinal = 0)
    )
    public void renderCooldown(FontRenderer font, ItemStack stack, int x, int y, String $, CallbackInfo info) {
        final Item item = stack.getItem();
        if (item instanceof DragonScaleArmorItem) {
            final IArmorEffect effect = ((DragonScaleArmorItem) item).effect;
            if (effect instanceof IDragonScaleArmorEffect.Advanced) {
                final IDragonScaleArmorEffect.Advanced advanced = (IDragonScaleArmorEffect.Advanced) effect;
                final int cooldown = ArmorEffectManager.getLocalCooldown(advanced);
                if (cooldown > 0) {
                    float remaining = MathHelper.clamp(cooldown / (float) advanced.cooldown, 0F, 1F);
                    RenderSystem.disableDepthTest();
                    RenderSystem.disableTexture();
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    this.fillRect(Tessellator.getInstance().getBuilder(), x, y + MathHelper.floor(16.0F * (1.0F - remaining)), 16, MathHelper.ceil(16.0F * remaining), 255, 255, 255, 127);
                    RenderSystem.enableTexture();
                    RenderSystem.enableDepthTest();
                }
            }
        }
    }

    private ItemRendererMixin() {}
}
