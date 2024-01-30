package net.dragonmounts3.init;

import net.dragonmounts3.registry.CarriageType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static net.dragonmounts3.DragonMounts.MOD_ID;
import static net.dragonmounts3.DragonMounts.prefix;

public class CarriageTypes {
    public static final CarriageType OAK = new CarriageType.Default(() -> DMItems.OAK_CARRIAGE, prefix("textures/entities/dragon_carriage/carriage_oak.png")).setRegistryName(CarriageType.DEFAULT_KEY);
    public static final CarriageType SPRUCE = new CarriageType.Default(() -> DMItems.SPRUCE_CARRIAGE, prefix("textures/entities/dragon_carriage/carriage_spruce.png")).setRegistryName(MOD_ID, "spruce");
    public static final CarriageType BIRCH = new CarriageType.Default(() -> DMItems.BIRCH_CARRIAGE, prefix("textures/entities/dragon_carriage/carriage_birch.png")).setRegistryName(MOD_ID, "birch");
    public static final CarriageType JUNGLE = new CarriageType.Default(() -> DMItems.JUNGLE_CARRIAGE, prefix("textures/entities/dragon_carriage/carriage_jungle.png")).setRegistryName(MOD_ID, "jungle");
    public static final CarriageType ACACIA = new CarriageType.Default(() -> DMItems.ACACIA_CARRIAGE, prefix("textures/entities/dragon_carriage/carriage_acacia.png")).setRegistryName(MOD_ID, "acacia");
    public static final CarriageType DARK_OAK = new CarriageType.Default(() -> DMItems.DARK_OAK_CARRIAGE, prefix("textures/entities/dragon_carriage/carriage_dark_oak.png")).setRegistryName(MOD_ID, "dark_oak");

    /* upcoming types:
    public static final CarriageType MANGROVE = new CarriageType(Blocks.CRIMSON_PLANKS, DMItems.MANGROVE_CARRIAGE);
    public static final CarriageType CHERRY = new CarriageType(Blocks.CRIMSON_PLANKS, DMItems.CHERRY_CARRIAGE);
    public static final CarriageType BAMBOO = new CarriageType(Blocks.CRIMSON_PLANKS, DMItems.BAMBOO_CARRIAGE);
    public static final CarriageType CRIMSON = new CarriageType(Blocks.CRIMSON_PLANKS, DMItems.CRIMSON_CARRIAGE);
    public static final CarriageType WARPED = new CarriageType(Blocks.WARPED_PLANKS, DMItems.WARPED_CARRIAGE);*/
    public static void register(RegistryEvent.Register<CarriageType> event) {
        IForgeRegistry<CarriageType> registry = event.getRegistry();
        registry.register(OAK);
        registry.register(SPRUCE);
        registry.register(BIRCH);
        registry.register(JUNGLE);
        registry.register(ACACIA);
        registry.register(DARK_OAK);
        /* upcoming types:
        registry.register(MANGROVE);
        registry.register(CHERRY);
        registry.register(BAMBOO);
        registry.register(CRIMSON);
        registry.register(WARPED);*/
    }
}
