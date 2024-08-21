package net.dragonmounts.client.renderer.dragon;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts.client.ClientDragonEntity;
import net.dragonmounts.client.model.dragon.DragonModel;
import net.dragonmounts.client.variant.VariantAppearance;
import net.dragonmounts.client.variant.VariantAppearances;
import net.dragonmounts.item.DragonArmorItem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static net.minecraft.client.renderer.ItemRenderer.getArmorFoilBuffer;
import static net.minecraft.client.renderer.RenderType.armorCutoutNoCull;

@OnlyIn(Dist.CLIENT)
public class DragonLayer extends LayerRenderer<ClientDragonEntity, DragonModel> {
    public DragonLayer(IEntityRenderer<ClientDragonEntity, DragonModel> renderer) {
        super(renderer);
    }

    @Override
    public void render(@Nonnull MatrixStack matrices, @Nonnull IRenderTypeBuffer buffer, int light, ClientDragonEntity dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        DragonModel model = this.getParentModel();
        VariantAppearance appearance = dragon.getVariant().getAppearance(VariantAppearances.ENDER_FEMALE);
        int noOverlay = OverlayTexture.NO_OVERLAY;
        if (dragon.deathTime > 0) {
            model.renderToBuffer(matrices, buffer.getBuffer(appearance.getDissolve(dragon)), light, noOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            model.renderToBuffer(matrices, buffer.getBuffer(appearance.getDecal(dragon)), light, noOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            model.renderToBuffer(matrices, buffer.getBuffer(appearance.getGlowDecal(dragon)), 15728640, noOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        //saddle
        if (dragon.isSaddled()) {
            renderColoredCutoutModel(model, appearance.getSaddle(dragon), matrices, buffer, light, dragon, 1.0F, 1.0F, 1.0F);
        }
        //chest
        if (dragon.hasChest()) {
            renderColoredCutoutModel(model, appearance.getChest(dragon), matrices, buffer, light, dragon, 1.0F, 1.0F, 1.0F);
        }
        //armor
        ItemStack stack = dragon.getArmor();
        Item item = stack.getItem();
        if (item instanceof DragonArmorItem) {
            model.renderToBuffer(
                    matrices,
                    getArmorFoilBuffer(buffer, armorCutoutNoCull(((DragonArmorItem) item).getDragonArmorTexture(stack, dragon)), false, stack.hasFoil()),
                    light,
                    noOverlay,
                    1.0F,
                    1.0F,
                    1.0F,
                    1.0F
            );
        }
        //glow
        model.renderToBuffer(matrices, buffer.getBuffer(appearance.getGlow(dragon)), 15728640, noOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
