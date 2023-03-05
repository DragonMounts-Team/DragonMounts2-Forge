package net.TheRPGAdventurer.ROTD;

import net.TheRPGAdventurer.ROTD.inits.ModItems;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod(DragonMounts.MOD_ID)
public class DragonMounts {

    public static final String MOD_ID = "dragonmounts";
    public IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    public static DamageSource DRAGONS_FIRE = new DamageSource("dragons_fire");

    public DragonMounts() {
        ModItems.ITEMS.register(this.eventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DragonMountsConfig.SPEC);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static <B extends IForgeRegistryEntry<B>> DeferredRegister<B> create(IForgeRegistry<B> reg) {
        return DeferredRegister.create(reg, MOD_ID);
    }

}