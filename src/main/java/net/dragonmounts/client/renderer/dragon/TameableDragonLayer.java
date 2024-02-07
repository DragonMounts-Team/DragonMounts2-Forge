package net.dragonmounts.client.renderer.dragon;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dragonmounts.client.model.dragon.DragonLegConfig;
import net.dragonmounts.client.model.dragon.DragonModel;
import net.dragonmounts.client.variant.VariantAppearance;
import net.dragonmounts.client.variant.VariantAppearances;
import net.dragonmounts.entity.dragon.DragonLifeStage;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.init.DMEntities;
import net.dragonmounts.item.DragonArmorItem;
import net.dragonmounts.registry.DragonVariant;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public class TameableDragonLayer extends LayerRenderer<TameableDragonEntity, DragonModel> {
    public TameableDragonLayer(IEntityRenderer<TameableDragonEntity, DragonModel> renderer) {
        super(renderer);
    }

    @Override
    public void render(@Nonnull MatrixStack matrices, @Nonnull IRenderTypeBuffer buffer, int light, TameableDragonEntity dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        DragonModel model = this.getParentModel();
        VariantAppearance appearance = dragon.getVariant().getAppearance(VariantAppearances.ENDER_FEMALE);
        if (dragon.deathTime > 0) {
            model.renderToBuffer(matrices, buffer.getBuffer(appearance.getDissolve(dragon)), light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            model.renderToBuffer(matrices, buffer.getBuffer(appearance.getDecal(dragon)), light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            model.renderToBuffer(matrices, buffer.getBuffer(appearance.getGlowDecal(dragon)), 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
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
            IVertexBuilder builder = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(((DragonArmorItem) item).getDragonArmorTexture(stack, dragon)), false, stack.hasFoil());
            model.renderToBuffer(matrices, builder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        //glow
        model.renderToBuffer(matrices, buffer.getBuffer(appearance.getGlow(dragon)), 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static class Player {
        private static final DragonModel MODEL = new DragonModel();
        private static final Predicate<EntityType<?>> FILTER = type -> type == DMEntities.TAMEABLE_DRAGON.get();

        public static void render(PlayerEntity player, MatrixStack matrices, IRenderTypeBuffer buffer, int light, boolean left) {
            CompoundNBT tag = left ? player.getShoulderEntityLeft() : player.getShoulderEntityRight();
            EntityType.byString(tag.getString("id")).filter(FILTER).ifPresent($ -> {
                DragonVariant variant = DragonVariant.byName(tag.getString(DragonVariant.DATA_PARAMETER_KEY));
                DragonLifeStage stage = DragonLifeStage.byName(tag.getString(DragonLifeStage.DATA_PARAMETER_KEY));
                DragonLegConfig config = variant.type.isSkeleton ? DragonLegConfig.SKELETON : DragonLegConfig.DEFAULT;
                MODEL.foreRightLeg.load(config);
                MODEL.hindRightLeg.load(config);
                MODEL.foreLeftLeg.load(config);
                MODEL.hindLeftLeg.load(config);
                matrices.pushPose();
                matrices.translate(left ? 0.4D : -0.4D, player.isCrouching() ? -1.3D : -1.5D, 0.0D);
                MODEL.renderOnShoulder(
                        variant.getAppearance(VariantAppearances.ENDER_FEMALE),
                        matrices,
                        buffer,
                        light,
                        DragonLifeStage.getSize(stage, tag.getInt("Age"))
                );
                matrices.popPose();
            });
        }
    }
}
