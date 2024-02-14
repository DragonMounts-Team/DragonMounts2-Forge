package net.dragonmounts.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts.DragonMountsConfig;
import net.dragonmounts.capability.ArmorEffectManager;
import net.dragonmounts.client.gui.DMConfigScreen;
import net.dragonmounts.client.renderer.CarriageRenderer;
import net.dragonmounts.client.renderer.DragonEggRenderer;
import net.dragonmounts.client.renderer.dragon.TameableDragonLayer;
import net.dragonmounts.client.variant.VariantAppearances;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.init.DMBlockEntities;
import net.dragonmounts.init.DMContainers;
import net.dragonmounts.item.DragonScaleBowItem;
import net.dragonmounts.item.DragonScaleShieldItem;
import net.dragonmounts.registry.DragonType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.dragonmounts.DragonMounts.MOD_ID;
import static net.dragonmounts.init.DMEntities.CARRIAGE;
import static net.dragonmounts.init.DMEntities.HATCHABLE_DRAGON_EGG;
import static net.dragonmounts.init.DMItems.DRAGON_WHISTLE;

@OnlyIn(Dist.CLIENT)
public class DMClientEvents {
    @Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        private static final IItemPropertyGetter DURATION = (stack, $, entity) -> entity == null ? 0.0F : entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
        private static final IItemPropertyGetter IS_USING_ITEM = (stack, $, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;

        private static void onFMLClientSetupEnqueueWork() {
            DMContainers.registerScreens();
            DMBlockEntities.registerBlockEntityRenders();
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                CompoundNBT tag = stack.getTag();
                DragonType type = tag != null && tintIndex == 1 && tag.contains("Type") ? DragonType.REGISTRY.getRaw(new ResourceLocation(tag.getString("Type"))) : null;
                return type == null ? 0xFFFFFF : type.color;
            }, DRAGON_WHISTLE);
        }

        /**
         * @see net.dragonmounts.mixin.RenderingRegistryMixin
         */
        @SubscribeEvent
        public static void onFMLClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(ModBusEvents::onFMLClientSetupEnqueueWork);
            ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> DMConfigScreen::new);
            RenderingRegistry.registerEntityRenderingHandler(CARRIAGE.get(), CarriageRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(HATCHABLE_DRAGON_EGG.get(), DragonEggRenderer::new);
            VariantAppearances.bindAppearance();
        }

        @SubscribeEvent
        public static void modelBake(ModelBakeEvent event) {
            for (DragonType type : DragonType.REGISTRY) {
                DragonScaleBowItem bow = type.getInstance(DragonScaleBowItem.class, null);
                if (bow != null) {
                    ItemModelsProperties.register(bow, new ResourceLocation("pull"), DURATION);
                    ItemModelsProperties.register(bow, new ResourceLocation("pulling"), IS_USING_ITEM);
                }
                DragonScaleShieldItem shield = type.getInstance(DragonScaleShieldItem.class, null);
                if (shield != null) {
                    ItemModelsProperties.register(shield, new ResourceLocation("blocking"), IS_USING_ITEM);
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeBusEvents {
        /**
         * Credit to AlexModGuy: Ice and Fire
         */
        @SubscribeEvent
        public static void setupCamera(EntityViewRenderEvent.CameraSetup event) {
            Minecraft minecraft = Minecraft.getInstance();
            PlayerEntity player = minecraft.player;
            if (player == null) return;
            Entity entity = player.getVehicle();
            if (entity instanceof TameableDragonEntity) {
                if (!minecraft.options.getCameraType().isFirstPerson()) {
                    ActiveRenderInfo info = event.getInfo();
                    info.move(
                            -info.getMaxZoom(DragonMountsConfig.CLIENT.camera_distance.get()),
                            0.0D,
                            -DragonMountsConfig.CLIENT.camera_offset.get()
                    );
                }
            }
        }

        @SubscribeEvent
        public static void render(RenderPlayerEvent.Post event) {
            PlayerEntity player = event.getPlayer();
            if (!DragonMountsConfig.CLIENT.debug.get() || player.isSpectator()) return;
            MatrixStack matrices = event.getMatrixStack();
            IRenderTypeBuffer buffer = event.getBuffers();
            int light = event.getLight();
            //float partialTicks = event.getPartialRenderTick();
            TameableDragonLayer.Player.render(player, matrices, buffer, light, true);
            TameableDragonLayer.Player.render(player, matrices, buffer, light, false);
        }

        @SubscribeEvent
        public static void onPlayerClone(ClientPlayerNetworkEvent.RespawnEvent event) {
            ArmorEffectManager.onPlayerClone(event.getNewPlayer(), event.getOldPlayer());
        }
    }
}
