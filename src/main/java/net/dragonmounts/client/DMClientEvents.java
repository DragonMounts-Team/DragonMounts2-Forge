package net.dragonmounts.client;

import net.dragonmounts.client.gui.DMConfigScreen;
import net.dragonmounts.client.renderer.CarriageRenderer;
import net.dragonmounts.client.renderer.DragonEggRenderer;
import net.dragonmounts.client.variant.VariantAppearances;
import net.dragonmounts.init.DMBlockEntities;
import net.dragonmounts.init.DMContainers;
import net.dragonmounts.init.DMKeyBindings;
import net.dragonmounts.item.DragonScaleBowItem;
import net.dragonmounts.item.DragonScaleShieldItem;
import net.dragonmounts.item.DragonWhistleItem;
import net.dragonmounts.registry.DragonType;
import net.minecraft.client.Minecraft;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.dragonmounts.DragonMounts.MOD_ID;
import static net.dragonmounts.init.DMEntities.CARRIAGE;
import static net.dragonmounts.init.DMEntities.HATCHABLE_DRAGON_EGG;
import static net.dragonmounts.init.DMItems.DRAGON_WHISTLE;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMClientEvents {
    private static void onFMLClientSetupEnqueueWork() {
        DMContainers.registerScreens();
        Minecraft.getInstance().getItemColors().register(DragonWhistleItem::getColor, DRAGON_WHISTLE);
    }

    /**
     * @see net.dragonmounts.mixin.RenderingRegistryMixin
     */
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(DMClientEvents::onFMLClientSetupEnqueueWork);
        DMBlockEntities.registerBlockEntityRenders();
        ClientRegistry.registerKeyBinding(DMKeyBindings.DESCENT);
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> DMConfigScreen::new);
        RenderingRegistry.registerEntityRenderingHandler(CARRIAGE.get(), CarriageRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(HATCHABLE_DRAGON_EGG.get(), DragonEggRenderer::new);
        VariantAppearances.bindAppearance();
    }

    @SubscribeEvent
    public static void modelBake(ModelBakeEvent event) {
        final ResourceLocation pull = new ResourceLocation("pull");
        final ResourceLocation pulling = new ResourceLocation("pulling");
        final ResourceLocation blocking = new ResourceLocation("blocking");
        final IItemPropertyGetter duration = (stack, $, entity) -> entity == null ? 0.0F : entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
        final IItemPropertyGetter isUsingItem = (stack, $, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
        for (DragonType type : DragonType.REGISTRY) {
            DragonScaleBowItem bow = type.getInstance(DragonScaleBowItem.class, null);
            if (bow != null) {
                ItemModelsProperties.register(bow, pull, duration);
                ItemModelsProperties.register(bow, pulling, isUsingItem);
            }
            DragonScaleShieldItem shield = type.getInstance(DragonScaleShieldItem.class, null);
            if (shield != null) {
                ItemModelsProperties.register(shield, blocking, isUsingItem);
            }
        }
    }
}
