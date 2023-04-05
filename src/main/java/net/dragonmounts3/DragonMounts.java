package net.dragonmounts3;

import net.dragonmounts3.inits.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Locale;

@Mod(DragonMounts.MOD_ID)
public class DragonMounts {

    public static final String MOD_ID = "dragonmounts";
    private static final String ITEM_TRANSLATION_KEY_PREFIX = "item." + MOD_ID + '.';
    private static final String BLOCK_TRANSLATION_KEY_PREFIX = "block." + MOD_ID + '.';

    public IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    public static DamageSource DRAGONS_FIRE = new DamageSource("dragons_fire");

    public DragonMounts() {
        ModItems.ITEMS.register(this.eventBus);
        ModBlocks.BLOCKS.register(this.eventBus);
        ModEntities.ENTITY_TYPES.register(this.eventBus);
        ModTileEntities.TILE_ENTITY.register(this.eventBus);
        ModContainers.CONTAINERS.register(this.eventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DragonMountsConfig.SPEC);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static <B extends IForgeRegistryEntry<B>> DeferredRegister<B> create(IForgeRegistry<B> reg) {
        return DeferredRegister.create(reg, MOD_ID);
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public static String getItemTranslationKey(String name) {
        return ITEM_TRANSLATION_KEY_PREFIX + name;
    }

    public static String getBlockTranslationKey(String name) {
        return BLOCK_TRANSLATION_KEY_PREFIX + name;
    }
}