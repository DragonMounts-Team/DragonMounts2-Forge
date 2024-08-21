package net.dragonmounts;

import net.dragonmounts.capability.ArmorEffectManager;
import net.dragonmounts.command.DMCommand;
import net.dragonmounts.config.ClientConfig;
import net.dragonmounts.config.ServerConfig;
import net.dragonmounts.init.*;
import net.dragonmounts.network.DMPacketHandler;
import net.dragonmounts.registry.CarriageType;
import net.dragonmounts.registry.CooldownCategory;
import net.dragonmounts.registry.DragonType;
import net.dragonmounts.registry.DragonVariant;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.DataSerializerEntry;
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

    public DragonMounts() {
        ModLoadingContext context = ModLoadingContext.get();
        IEventBus modBus = ((FMLJavaModLoadingContext) context.extension()).getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        DMGameRules.init();
        ServerConfig.init(context);
        ClientConfig.init(context);
        CarriageType.REGISTRY.register(modBus);
        CooldownCategory.REGISTRY.register(modBus);
        DragonType.REGISTRY.register(modBus);
        DragonVariant.REGISTRY.register(modBus);
        modBus.addListener(DragonMounts::preInit);
        modBus.addGenericListener(DataSerializerEntry.class, DragonMounts::registerDataSerializers);
        modBus.addGenericListener(CarriageType.class, CarriageTypes::register);
        modBus.addGenericListener(CooldownCategory.class, DMArmorEffects::register);
        modBus.addGenericListener(DragonType.class, DragonTypes::register);
        modBus.addGenericListener(DragonVariant.class, DragonVariants::register);
        forgeBus.addListener(DMFeatures::addDimensionalSpacing);
        forgeBus.addListener(DMFeatures::loadBiome);
        DMSounds.SOUNDS.register(modBus);
        DMItems.ITEMS.register(modBus);
        DMBlocks.BLOCKS.register(modBus);
        DMEntities.ENTITY_TYPES.register(modBus);
        DMBlockEntities.BLOCK_ENTITY.register(modBus);
        DMContainers.CONTAINERS.register(modBus);
        DMFeatures.STRUCTURE_FEATURE.register(modBus);
        forgeBus.addGenericListener(Entity.class, DMCapabilities::attachCapabilities);
        forgeBus.addListener(DragonMounts::onPlayerClone);
        forgeBus.addListener(DragonMounts::registerCommands);
        DMItems.subscribeEvents(forgeBus);
    }

    public static void registerDataSerializers(RegistryEvent.Register<DataSerializerEntry> event) {
        IForgeRegistry<DataSerializerEntry> registry = event.getRegistry();
        registry.register(new DataSerializerEntry(CarriageType.REGISTRY).setRegistryName(MOD_ID, "carriage_type"));
        registry.register(new DataSerializerEntry(CooldownCategory.REGISTRY).setRegistryName(MOD_ID, "cooldown_category"));//unused
        registry.register(new DataSerializerEntry(DragonType.REGISTRY).setRegistryName(MOD_ID, "dragon_type"));
        registry.register(new DataSerializerEntry(DragonVariant.REGISTRY).setRegistryName(MOD_ID, "dragon_variant"));
    }

    public static void registerCommands(RegisterCommandsEvent event) {
        DMCommand.register(event.getDispatcher());
    }

    public static <B extends IForgeRegistryEntry<B>> DeferredRegister<B> create(IForgeRegistry<B> reg) {
        return DeferredRegister.create(reg, MOD_ID);
    }

    public static ResourceLocation makeId(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public static void preInit(FMLCommonSetupEvent event) {
        if (FMLLoader.getDist().isDedicatedServer()) {
            DMPacketHandler.initServer();
        } else {
            DMPacketHandler.initClient();
        }
        DMCapabilities.register();
        DMFeatures.setup();
    }

    public static void onPlayerClone(PlayerEvent.Clone event) {
        ArmorEffectManager.onPlayerClone(event.getPlayer(), event.getOriginal());
    }
}