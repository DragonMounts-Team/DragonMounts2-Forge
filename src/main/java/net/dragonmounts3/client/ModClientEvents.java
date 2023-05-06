package net.dragonmounts3.client;

import net.dragonmounts3.client.renderer.CarriageRenderer;
import net.dragonmounts3.client.renderer.DragonEggRenderer;
import net.dragonmounts3.client.renderer.dragon.TameableDragonRenderer;
import net.dragonmounts3.inits.ModBlockEntities;
import net.dragonmounts3.inits.ModContainers;
import net.dragonmounts3.inits.ModItems;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.dragonmounts3.DragonMounts.MOD_ID;
import static net.dragonmounts3.inits.ModEntities.*;
import static net.dragonmounts3.inits.ModItems.DRAGON_WHISTLE;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {
    @Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        private static void onFMLClientSetupEnqueueWork() {
            ModContainers.registerScreens();
        }

        @SubscribeEvent
        public static void onFMLClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(ModBusEvents::onFMLClientSetupEnqueueWork);
            RenderingRegistry.registerEntityRenderingHandler(CARRIAGE.get(), CarriageRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(HATCHABLE_DRAGON_EGG.get(), DragonEggRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(TAMEABLE_DRAGON.get(), TameableDragonRenderer::new);
            ModBlockEntities.registerBlockEntityRenders();
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                CompoundNBT tag = stack.getTag();
                if (tag != null && tag.contains("Type") && tintIndex == 1)
                    return DragonType.byId(tag.getInt("Type")).getColor();
                return 0xFFFFFF;
            }, DRAGON_WHISTLE::get);
        }

        @SubscribeEvent
        public static void modelBake(ModelBakeEvent event) {
            ModItems.addItemModelProperties();
        }
    }
}
