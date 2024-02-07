package net.dragonmounts.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts.client.renderer.block.DragonHeadRenderer;
import net.dragonmounts.client.variant.VariantAppearances;
import net.dragonmounts.item.DragonHeadItem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(HeadLayer.class)
public abstract class HeadLayerMixin {
    @Unique
    private static final Logger dragonmounts$LOGGER = LogManager.getLogger();

    @Inject(
            method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/model/ModelRenderer;translateAndRotate(Lcom/mojang/blaze3d/matrix/MatrixStack;)V",
                    shift = At.Shift.AFTER//BeforeInvoke for void
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    public void renderDragonHead(
            MatrixStack matrices,
            IRenderTypeBuffer buffer,
            int light,
            LivingEntity entity,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch,
            CallbackInfo info,
            ItemStack stack,
            Item item,
            boolean flag
    ) {
        if (item instanceof DragonHeadItem) {
            DragonHeadRenderer.renderHead(
                    ((DragonHeadItem) item).variant.getAppearance(VariantAppearances.ENDER_FEMALE),
                    0D,
                    flag ? -0.0078125D : -0.0703125D,
                    0D,
                    limbSwing,
                    0F,
                    0.890625F,//0.5F * 1.1875F  0.0078125
                    false,
                    matrices,
                    buffer,
                    light,
                    OverlayTexture.NO_OVERLAY
            );
            info.cancel();
            matrices.popPose();
        }
    }
}
