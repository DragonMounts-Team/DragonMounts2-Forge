package net.dragonmounts.client.renderer.dragon;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts.client.model.dragon.DragonLegConfig;
import net.dragonmounts.client.model.dragon.DragonModel;
import net.dragonmounts.client.variant.VariantAppearances;
import net.dragonmounts.config.ClientConfig;
import net.dragonmounts.entity.dragon.DragonLifeStage;
import net.dragonmounts.init.DMEntities;
import net.dragonmounts.registry.DragonVariant;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;

public class TamableDragonOnShoulderLayer<T extends PlayerEntity> extends LayerRenderer<T, PlayerModel<T>> {
    private final DragonModel model = new DragonModel();

    public TamableDragonOnShoulderLayer(IEntityRenderer<T, PlayerModel<T>> renderer) {
        super(renderer);
    }

    @Override
    public void render(@Nonnull MatrixStack matrices, @Nonnull IRenderTypeBuffer buffers, int light, @Nonnull T player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ClientConfig.INSTANCE.debug.get()) {
            double offsetY = player.isCrouching() ? -1.3D : -1.5D;
            render(player.getShoulderEntityLeft(), matrices, buffers, light, 0.4D, offsetY, 0.0D);
            render(player.getShoulderEntityRight(), matrices, buffers, light, -0.4D, offsetY, 0.0D);
        }
    }

    public void render(CompoundNBT tag, MatrixStack matrices, IRenderTypeBuffer buffers, int light, double offsetX, double offsetY, double offsetZ) {
        if (EntityType.getKey(DMEntities.TAMEABLE_DRAGON.get()).toString().equals(tag.getString("id"))) {
            DragonModel model = this.model;
            DragonVariant variant = DragonVariant.byName(tag.getString(DragonVariant.DATA_PARAMETER_KEY));
            DragonLegConfig config = variant.type.isSkeleton ? DragonLegConfig.SKELETON : DragonLegConfig.DEFAULT;
            float size = DragonLifeStage.getSize(DragonLifeStage.byName(tag.getString(DragonLifeStage.DATA_PARAMETER_KEY)), tag.getInt("Age"));
            model.foreRightLeg.load(config);
            model.hindRightLeg.load(config);
            model.foreLeftLeg.load(config);
            model.hindLeftLeg.load(config);
            matrices.pushPose();
            matrices.translate(offsetX, offsetY, offsetZ);
            model.renderOnShoulder(variant.getAppearance(VariantAppearances.ENDER_FEMALE), matrices, buffers, light, size);
            matrices.popPose();
        }
    }
}
