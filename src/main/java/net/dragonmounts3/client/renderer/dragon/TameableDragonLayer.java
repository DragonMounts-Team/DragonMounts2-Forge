package net.dragonmounts3.client.renderer.dragon;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dragonmounts3.api.variant.AbstractVariant;
import net.dragonmounts3.client.model.dragon.DragonModel;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.item.DragonArmorItem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class TameableDragonLayer extends LayerRenderer<TameableDragonEntity, DragonModel> {
    public TameableDragonLayer(IEntityRenderer<TameableDragonEntity, DragonModel> renderer) {
        super(renderer);
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int packedLight, TameableDragonEntity dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        DragonModel model = this.getParentModel();
        AbstractVariant variant = dragon.getVariant();
        if (dragon.deathTime > 0) {
            model.renderToBuffer(matrixStack, buffer.getBuffer(variant.getDissolve(dragon)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            model.renderToBuffer(matrixStack, buffer.getBuffer(variant.getDecal(dragon)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            model.renderToBuffer(matrixStack, buffer.getBuffer(variant.getGlowDecal(dragon)), 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        //saddle
        if (dragon.isSaddled()) {
            renderColoredCutoutModel(model, variant.getSaddle(dragon), matrixStack, buffer, packedLight, dragon, 1.0F, 1.0F, 1.0F);
        }
        //chest
        if (dragon.hasChest()) {
            renderColoredCutoutModel(model, variant.getChest(dragon), matrixStack, buffer, packedLight, dragon, 1.0F, 1.0F, 1.0F);
        }
        //armor
        ItemStack stack = dragon.getArmor();
        Item item = stack.getItem();
        if (item instanceof DragonArmorItem) {
            DragonArmorItem armor = (DragonArmorItem) item;
            IVertexBuilder builder = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(armor.getDragonArmorTexture(stack, dragon)), false, stack.hasFoil());
            model.renderToBuffer(matrixStack, builder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        //glow
        model.renderToBuffer(matrixStack, buffer.getBuffer(variant.getGlow(dragon)), 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
