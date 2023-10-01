package net.dragonmounts3.init;

import net.dragonmounts3.registry.DragonVariant;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static net.dragonmounts3.DragonMounts.MOD_ID;
import static net.dragonmounts3.registry.DragonVariant.DEFAULT_KEY;

public class DragonVariants {
    public static final DragonVariant AETHER_FEMALE = new DragonVariant(DragonTypes.AETHER).setRegistryName(MOD_ID, "aether_female");
    public static final DragonVariant AETHER_MALE = new DragonVariant(DragonTypes.AETHER).setRegistryName(MOD_ID, "aether_male");
    public static final DragonVariant AETHER_NEW = new DragonVariant(DragonTypes.AETHER).setRegistryName(MOD_ID, "aether_new");
    public static final DragonVariant ENCHANT_FEMALE = new DragonVariant(DragonTypes.ENCHANT).setRegistryName(MOD_ID, "enchant_female");
    public static final DragonVariant ENCHANT_MALE = new DragonVariant(DragonTypes.ENCHANT).setRegistryName(MOD_ID, "enchant_male");
    public static final DragonVariant ENDER_FEMALE = new DragonVariant(DragonTypes.ENDER).setRegistryName(DEFAULT_KEY);
    public static final DragonVariant ENDER_MALE = new DragonVariant(DragonTypes.ENDER).setRegistryName(MOD_ID, "ender_male");
    public static final DragonVariant FIRE_FEMALE = new DragonVariant(DragonTypes.FIRE).setRegistryName(MOD_ID, "fire_female");
    public static final DragonVariant FIRE_MALE = new DragonVariant(DragonTypes.FIRE).setRegistryName(MOD_ID, "fire_male");
    public static final DragonVariant FOREST_FEMALE = new DragonVariant(DragonTypes.FOREST).setRegistryName(MOD_ID, "forest_female");
    public static final DragonVariant FOREST_MALE = new DragonVariant(DragonTypes.FOREST).setRegistryName(MOD_ID, "forest_male");
    public static final DragonVariant FOREST_DRY_FEMALE = new DragonVariant(DragonTypes.FOREST).setRegistryName(MOD_ID, "forest_dry_female");
    public static final DragonVariant FOREST_DRY_MALE = new DragonVariant(DragonTypes.FOREST).setRegistryName(MOD_ID, "forest_dry_male");
    public static final DragonVariant FOREST_TAIGA_FEMALE = new DragonVariant(DragonTypes.FOREST).setRegistryName(MOD_ID, "forest_taiga_female");
    public static final DragonVariant FOREST_TAIGA_MALE = new DragonVariant(DragonTypes.FOREST).setRegistryName(MOD_ID, "forest_taiga_male");
    public static final DragonVariant ICE_FEMALE = new DragonVariant(DragonTypes.ICE).setRegistryName(MOD_ID, "ice_female");
    public static final DragonVariant ICE_MALE = new DragonVariant(DragonTypes.ICE).setRegistryName(MOD_ID, "ice_male");
    public static final DragonVariant MOONLIGHT_FEMALE = new DragonVariant(DragonTypes.MOONLIGHT).setRegistryName(MOD_ID, "moonlight_female");
    public static final DragonVariant MOONLIGHT_MALE = new DragonVariant(DragonTypes.MOONLIGHT).setRegistryName(MOD_ID, "moonlight_male");
    public static final DragonVariant NETHER_FEMALE = new DragonVariant(DragonTypes.NETHER).setRegistryName(MOD_ID, "nether_female");
    public static final DragonVariant NETHER_MALE = new DragonVariant(DragonTypes.NETHER).setRegistryName(MOD_ID, "nether_male");
    public static final DragonVariant SCULK = new DragonVariant(DragonTypes.SCULK).setRegistryName(MOD_ID, "sculk");
    public static final DragonVariant SKELETON_FEMALE = new DragonVariant(DragonTypes.SKELETON).setRegistryName(MOD_ID, "skeleton_female");
    public static final DragonVariant SKELETON_MALE = new DragonVariant(DragonTypes.SKELETON).setRegistryName(MOD_ID, "skeleton_male");
    public static final DragonVariant STORM_FEMALE = new DragonVariant(DragonTypes.STORM).setRegistryName(MOD_ID, "storm_female");
    public static final DragonVariant STORM_MALE = new DragonVariant(DragonTypes.STORM).setRegistryName(MOD_ID, "storm_male");
    public static final DragonVariant SUNLIGHT_FEMALE = new DragonVariant(DragonTypes.SUNLIGHT).setRegistryName(MOD_ID, "sunlight_female");
    public static final DragonVariant SUNLIGHT_MALE = new DragonVariant(DragonTypes.SUNLIGHT).setRegistryName(MOD_ID, "sunlight_male");
    public static final DragonVariant TERRA_FEMALE = new DragonVariant(DragonTypes.TERRA).setRegistryName(MOD_ID, "terra_female");
    public static final DragonVariant TERRA_MALE = new DragonVariant(DragonTypes.TERRA).setRegistryName(MOD_ID, "terra_male");
    public static final DragonVariant WATER_FEMALE = new DragonVariant(DragonTypes.WATER).setRegistryName(MOD_ID, "water_female");
    public static final DragonVariant WATER_MALE = new DragonVariant(DragonTypes.WATER).setRegistryName(MOD_ID, "water_male");
    public static final DragonVariant WITHER_FEMALE = new DragonVariant(DragonTypes.WITHER).setRegistryName(MOD_ID, "wither_female");
    public static final DragonVariant WITHER_MALE = new DragonVariant(DragonTypes.WITHER).setRegistryName(MOD_ID, "wither_male");
    public static final DragonVariant ZOMBIE_FEMALE = new DragonVariant(DragonTypes.ZOMBIE).setRegistryName(MOD_ID, "zombie_female");
    public static final DragonVariant ZOMBIE_MALE = new DragonVariant(DragonTypes.ZOMBIE).setRegistryName(MOD_ID, "zombie_male");

    public static void register(RegistryEvent.Register<DragonVariant> event) {
        IForgeRegistry<DragonVariant> registry = event.getRegistry();
        registry.register(AETHER_FEMALE);
        registry.register(AETHER_MALE);
        registry.register(AETHER_NEW);
        registry.register(ENCHANT_FEMALE);
        registry.register(ENCHANT_MALE);
        registry.register(ENDER_FEMALE);
        registry.register(ENDER_MALE);
        registry.register(FIRE_FEMALE);
        registry.register(FIRE_MALE);
        registry.register(FOREST_DRY_FEMALE);
        registry.register(FOREST_DRY_MALE);
        registry.register(FOREST_TAIGA_FEMALE);
        registry.register(FOREST_TAIGA_MALE);
        registry.register(FOREST_FEMALE);
        registry.register(FOREST_MALE);
        registry.register(ICE_FEMALE);
        registry.register(ICE_MALE);
        registry.register(MOONLIGHT_FEMALE);
        registry.register(MOONLIGHT_MALE);
        registry.register(NETHER_FEMALE);
        registry.register(NETHER_MALE);
        registry.register(SCULK);
        registry.register(SKELETON_FEMALE);
        registry.register(SKELETON_MALE);
        registry.register(STORM_FEMALE);
        registry.register(STORM_MALE);
        registry.register(SUNLIGHT_FEMALE);
        registry.register(SUNLIGHT_MALE);
        registry.register(TERRA_FEMALE);
        registry.register(TERRA_MALE);
        registry.register(WATER_FEMALE);
        registry.register(WATER_MALE);
        registry.register(WITHER_FEMALE);
        registry.register(WITHER_MALE);
        registry.register(ZOMBIE_FEMALE);
        registry.register(ZOMBIE_MALE);
    }
}
