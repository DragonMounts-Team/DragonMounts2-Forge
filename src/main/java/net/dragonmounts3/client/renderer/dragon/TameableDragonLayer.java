package net.dragonmounts3.client.renderer.dragon;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dragonmounts3.client.model.dragon.DragonModel;
import net.dragonmounts3.client.resource.AbstractResourceManager;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.item.DragonArmorItem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class TameableDragonLayer extends LayerRenderer<TameableDragonEntity, DragonModel> {
    public static final RenderState.WriteMaskState COLOR_WRITE = new RenderState.WriteMaskState(true, false);
    public static final RenderState.TransparencyState TRANSLUCENT_TRANSPARENCY = new RenderState.TransparencyState("translucent_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public TameableDragonLayer(IEntityRenderer<TameableDragonEntity, DragonModel> renderer) {
        super(renderer);
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int packedLight, TameableDragonEntity dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        DragonModel model = this.getParentModel();
        AbstractResourceManager resources = dragon.getDragonType().resources;
        //saddle
        if (dragon.isSaddled()) {
            renderColoredCutoutModel(model, resources.getSaddle(dragon), matrixStack, buffer, packedLight, dragon, 1.0F, 1.0F, 1.0F);
        }
        //chest
        if (dragon.hasChest()) {
            renderColoredCutoutModel(model, resources.getChest(dragon), matrixStack, buffer, packedLight, dragon, 1.0F, 1.0F, 1.0F);
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
        RenderType type = RenderType.create("eyes", DefaultVertexFormats.NEW_ENTITY, 7, 256, false, true, RenderType.State.builder()
                .setTextureState(new RenderState.TextureState(resources.getGlow(dragon), false, false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setWriteMaskState(COLOR_WRITE)
                .createCompositeState(false)
        );
        IVertexBuilder builder = buffer.getBuffer(type);
        model.renderToBuffer(matrixStack, builder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
