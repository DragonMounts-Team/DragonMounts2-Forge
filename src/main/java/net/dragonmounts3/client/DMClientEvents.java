package net.dragonmounts3.client;

import net.dragonmounts3.DragonMountsConfig;
import net.dragonmounts3.client.gui.DMConfigScreen;
import net.dragonmounts3.client.renderer.CarriageRenderer;
import net.dragonmounts3.client.renderer.DragonEggRenderer;
import net.dragonmounts3.client.renderer.dragon.TameableDragonRenderer;
import net.dragonmounts3.client.variant.VariantAppearances;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.init.DMBlockEntities;
import net.dragonmounts3.init.DMContainers;
import net.dragonmounts3.item.DragonScaleBowItem;
import net.dragonmounts3.item.DragonScaleShieldItem;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.dragonmounts3.DragonMounts.MOD_ID;
import static net.dragonmounts3.init.DMEntities.*;
import static net.dragonmounts3.init.DMItems.DRAGON_WHISTLE;

@OnlyIn(Dist.CLIENT)
public class DMClientEvents {
    @Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        private static final IItemPropertyGetter DURATION = (stack, world, entity) -> entity == null ? 0.0F : entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
        private static final IItemPropertyGetter IS_USING_ITEM = (stack, world, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;

        private static void onFMLClientSetupEnqueueWork() {
            DMContainers.registerScreens();
            DMBlockEntities.registerBlockEntityRenders();
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                CompoundNBT tag = stack.getTag();
                DragonType type = tag != null && tintIndex == 1 && tag.contains("Type") ? DragonType.REGISTRY.getRaw(new ResourceLocation(tag.getString("Type"))) : null;
                return type == null ? 0xFFFFFF : type.color;
            }, DRAGON_WHISTLE::get);
        }

        @SubscribeEvent
        public static void onFMLClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(ModBusEvents::onFMLClientSetupEnqueueWork);
            ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> DMConfigScreen::new);
            RenderingRegistry.registerEntityRenderingHandler(CARRIAGE.get(), CarriageRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(HATCHABLE_DRAGON_EGG.get(), DragonEggRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(TAMEABLE_DRAGON.get(), TameableDragonRenderer::new);
            VariantAppearances.bindAppearance();
        }

        @SubscribeEvent
        public static void modelBake(ModelBakeEvent event) {
            for (DragonType type : DragonType.REGISTRY) {//Do NOT load other mods at the same time!
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
            if (entity == null) return;
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
    }
}
