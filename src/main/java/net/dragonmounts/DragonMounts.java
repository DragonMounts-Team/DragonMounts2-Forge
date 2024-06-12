package net.dragonmounts;

import net.dragonmounts.capability.ArmorEffectManager;
import net.dragonmounts.command.ConfigCommand;
import net.dragonmounts.command.DMCommand;
import net.dragonmounts.init.*;
import net.dragonmounts.network.DMPacketHandler;
import net.dragonmounts.registry.CarriageType;
import net.dragonmounts.registry.CooldownCategory;
import net.dragonmounts.registry.DragonType;
import net.dragonmounts.registry.DragonVariant;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
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
    public IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

    public DragonMounts() {
        DMGameRules.load();
        DragonMountsConfig.init();
        CarriageType.REGISTRY.register(this.eventBus);
        CooldownCategory.REGISTRY.register(this.eventBus);
        DragonType.REGISTRY.register(this.eventBus);
        DragonVariant.REGISTRY.register(this.eventBus);
        this.eventBus.addListener(DragonMounts::preInit);
        this.eventBus.addGenericListener(DataSerializerEntry.class, DragonMounts::registerDataSerializers);
        this.eventBus.addGenericListener(CarriageType.class, CarriageTypes::register);
        this.eventBus.addGenericListener(CooldownCategory.class, DMArmorEffects::register);
        this.eventBus.addGenericListener(DragonType.class, DragonTypes::register);
        this.eventBus.addGenericListener(DragonVariant.class, DragonVariants::register);
        MinecraftForge.EVENT_BUS.addListener(DMFeatures::addDimensionalSpacing);
        MinecraftForge.EVENT_BUS.addListener(DMFeatures::loadBiome);
        DMSounds.SOUNDS.register(this.eventBus);
        DMItems.ITEMS.register(this.eventBus);
        DMBlocks.BLOCKS.register(this.eventBus);
        DMEntities.ENTITY_TYPES.register(this.eventBus);
        DMBlockEntities.BLOCK_ENTITY.register(this.eventBus);
        DMContainers.CONTAINERS.register(this.eventBus);
        DMFeatures.STRUCTURE_FEATURE.register(this.eventBus);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, DMCapabilities::attachCapabilities);
        MinecraftForge.EVENT_BUS.addListener(ArmorEffectManager::onPlayerLoggedIn);
        MinecraftForge.EVENT_BUS.addListener(DragonMounts::onPlayerClone);
        MinecraftForge.EVENT_BUS.addListener(DragonMounts::registerCommands);
        DMItems.subscribeEvents();
        if (FMLLoader.getDist().isClient()) {
            //noinspection ConstantValue
            if (Minecraft.getInstance() != null) {
                DMKeyBindings.register();
            }
            MinecraftForge.EVENT_BUS.addListener(ConfigCommand.Client::onClientSendMessage);
            MinecraftForge.EVENT_BUS.addListener(ConfigCommand.Client::onGuiOpen);
        }
    }

    public static void registerDataSerializers(RegistryEvent.Register<DataSerializerEntry> event) {
        IForgeRegistry<DataSerializerEntry> registry = event.getRegistry();
        registry.register(new DataSerializerEntry(CarriageType.SERIALIZER).setRegistryName(MOD_ID, "carriage_type"));
        registry.register(new DataSerializerEntry(DragonType.SERIALIZER).setRegistryName(MOD_ID, "dragon_type"));
        registry.register(new DataSerializerEntry(DragonVariant.SERIALIZER).setRegistryName(MOD_ID, "dragon_variant"));
    }

    public static void registerCommands(RegisterCommandsEvent event) {
        DMCommand.register(event.getDispatcher(), event.getEnvironment());
    }

    public static <B extends IForgeRegistryEntry<B>> DeferredRegister<B> create(IForgeRegistry<B> reg) {
        return DeferredRegister.create(reg, MOD_ID);
    }

    public static ResourceLocation prefix(String name) {
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