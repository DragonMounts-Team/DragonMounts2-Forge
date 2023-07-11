package net.dragonmounts3;

import net.dragonmounts3.command.DMCommand;
import net.dragonmounts3.init.*;
import net.dragonmounts3.item.DragonArmorEffect;
import net.dragonmounts3.network.DMPacketHandler;
import net.dragonmounts3.util.ArmorEffect;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Locale;

@Mod(DragonMounts.MOD_ID)
public class DragonMounts {
    public static final String MOD_ID = "dragonmounts";
    public static final String ITEM_TRANSLATION_KEY_PREFIX = "item." + MOD_ID + '.';
    public static final String BLOCK_TRANSLATION_KEY_PREFIX = "block." + MOD_ID + '.';
    public static final DamageSource DRAGONS_FIRE = new DamageSource("dragons_fire");
    public IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

    public DragonMounts() {
        DMPacketHandler.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DragonMountsConfig.SPEC);
        DMSounds.SOUNDS.register(this.eventBus);
        DMAttributes.ATTRIBUTES.register(this.eventBus);
        DMItems.ITEMS.register(this.eventBus);
        DMBlocks.BLOCKS.register(this.eventBus);
        DMEntities.ENTITY_TYPES.register(this.eventBus);
        DMBlockEntities.BLOCK_ENTITY.register(this.eventBus);
        DMContainers.CONTAINERS.register(this.eventBus);
        this.eventBus.addListener(DragonMounts::preInit);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, DMCapabilities::attachCapabilities);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(ArmorEffect.class);
        MinecraftForge.EVENT_BUS.addListener(DragonArmorEffect::xpBonus);
        MinecraftForge.EVENT_BUS.addListener(DragonArmorEffect::meleeChanneling);
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        DMCommand.register(event.getDispatcher());
    }

    public static <B extends IForgeRegistryEntry<B>> DeferredRegister<B> create(IForgeRegistry<B> reg) {
        return DeferredRegister.create(reg, MOD_ID);
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public static void preInit(FMLCommonSetupEvent event) {
        DMCapabilities.register();
    }
}