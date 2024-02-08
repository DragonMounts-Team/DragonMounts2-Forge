package net.dragonmounts.init;

import net.dragonmounts.registry.DragonType;
import net.dragonmounts.registry.DragonVariant;
import net.dragonmounts.util.ImmutableArray;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.Rarity;
import net.minecraftforge.event.RegistryEvent;

import static net.dragonmounts.DragonMounts.MOD_ID;
import static net.dragonmounts.init.DMBlocks.GET_DMISTER;

public class DragonVariants {
    public static final DragonVariant AETHER_FEMALE = create(DragonTypes.AETHER, MOD_ID + ":aether_female");
    public static final DragonVariant AETHER_MALE = create(DragonTypes.AETHER, MOD_ID + ":aether_male");
    public static final DragonVariant AETHER_NEW = create(DragonTypes.AETHER, MOD_ID + ":aether_new");
    public static final DragonVariant ENCHANT_FEMALE = create(DragonTypes.ENCHANT, MOD_ID + ":enchant_female");
    public static final DragonVariant ENCHANT_MALE = create(DragonTypes.ENCHANT, MOD_ID + ":enchant_male");
    public static final DragonVariant ENDER_FEMALE = create(DragonTypes.ENDER, DragonVariant.DEFAULT_KEY.toString());
    public static final DragonVariant ENDER_MALE = create(DragonTypes.ENDER, MOD_ID + ":ender_male");
    public static final DragonVariant FIRE_FEMALE = create(DragonTypes.FIRE, MOD_ID + ":fire_female");
    public static final DragonVariant FIRE_MALE = create(DragonTypes.FIRE, MOD_ID + ":fire_male");
    public static final DragonVariant FOREST_FEMALE = create(DragonTypes.FOREST, MOD_ID + ":forest_female");
    public static final DragonVariant FOREST_MALE = create(DragonTypes.FOREST, MOD_ID + ":forest_male");
    public static final DragonVariant FOREST_DRY_FEMALE = create(DragonTypes.FOREST, MOD_ID + ":forest_dry_female");
    public static final DragonVariant FOREST_DRY_MALE = create(DragonTypes.FOREST, MOD_ID + ":forest_dry_male");
    public static final DragonVariant FOREST_TAIGA_FEMALE = create(DragonTypes.FOREST, MOD_ID + ":forest_taiga_female");
    public static final DragonVariant FOREST_TAIGA_MALE = create(DragonTypes.FOREST, MOD_ID + ":forest_taiga_male");
    public static final DragonVariant ICE_FEMALE = create(DragonTypes.ICE, MOD_ID + ":ice_female");
    public static final DragonVariant ICE_MALE = create(DragonTypes.ICE, MOD_ID + ":ice_male");
    public static final DragonVariant MOONLIGHT_FEMALE = create(DragonTypes.MOONLIGHT, MOD_ID + ":moonlight_female");
    public static final DragonVariant MOONLIGHT_MALE = create(DragonTypes.MOONLIGHT, MOD_ID + ":moonlight_male");
    public static final DragonVariant NETHER_FEMALE = create(DragonTypes.NETHER, MOD_ID + ":nether_female");
    public static final DragonVariant NETHER_MALE = create(DragonTypes.NETHER, MOD_ID + ":nether_male");
    public static final DragonVariant SCULK = create(DragonTypes.SCULK, MOD_ID + ":sculk");
    public static final DragonVariant SKELETON_FEMALE = create(DragonTypes.SKELETON, MOD_ID + ":skeleton_female");
    public static final DragonVariant SKELETON_MALE = create(DragonTypes.SKELETON, MOD_ID + ":skeleton_male");
    public static final DragonVariant STORM_FEMALE = create(DragonTypes.STORM, MOD_ID + ":storm_female");
    public static final DragonVariant STORM_MALE = create(DragonTypes.STORM, MOD_ID + ":storm_male");
    public static final DragonVariant SUNLIGHT_FEMALE = create(DragonTypes.SUNLIGHT, MOD_ID + ":sunlight_female");
    public static final DragonVariant SUNLIGHT_MALE = create(DragonTypes.SUNLIGHT, MOD_ID + ":sunlight_male");
    public static final DragonVariant TERRA_FEMALE = create(DragonTypes.TERRA, MOD_ID + ":terra_female");
    public static final DragonVariant TERRA_MALE = create(DragonTypes.TERRA, MOD_ID + ":terra_male");
    public static final DragonVariant WATER_FEMALE = create(DragonTypes.WATER, MOD_ID + ":water_female");
    public static final DragonVariant WATER_MALE = create(DragonTypes.WATER, MOD_ID + ":water_male");
    public static final DragonVariant WITHER_FEMALE = create(DragonTypes.WITHER, MOD_ID + ":wither_female");
    public static final DragonVariant WITHER_MALE = create(DragonTypes.WITHER, MOD_ID + ":wither_male");
    public static final DragonVariant ZOMBIE_FEMALE = create(DragonTypes.ZOMBIE, MOD_ID + ":zombie_female");
    public static final DragonVariant ZOMBIE_MALE = create(DragonTypes.ZOMBIE, MOD_ID + ":zombie_male");
    public static final ImmutableArray<DragonVariant> VALUES = new ImmutableArray<>(
            DragonVariant.class,
            AETHER_FEMALE,
            AETHER_MALE,
            AETHER_NEW,
            ENCHANT_FEMALE,
            ENCHANT_MALE,
            ENDER_FEMALE,
            ENDER_MALE,
            FIRE_FEMALE,
            FIRE_MALE,
            FOREST_DRY_FEMALE,
            FOREST_DRY_MALE,
            FOREST_TAIGA_FEMALE,
            FOREST_TAIGA_MALE,
            FOREST_FEMALE,
            FOREST_MALE,
            ICE_FEMALE,
            ICE_MALE,
            MOONLIGHT_FEMALE,
            MOONLIGHT_MALE,
            NETHER_FEMALE,
            NETHER_MALE,
            SCULK,
            SKELETON_FEMALE,
            SKELETON_MALE,
            STORM_FEMALE,
            STORM_MALE,
            SUNLIGHT_FEMALE,
            SUNLIGHT_MALE,
            TERRA_FEMALE,
            TERRA_MALE,
            WATER_FEMALE,
            WATER_MALE,
            WITHER_FEMALE,
            WITHER_MALE,
            ZOMBIE_FEMALE,
            ZOMBIE_MALE
    );

    private static DragonVariant create(DragonType type, String name) {
        return new DragonVariant(
                type,
                AbstractBlock.Properties.of(Material.DECORATION).strength(1F),
                DMItemGroups.block().rarity(Rarity.UNCOMMON).setISTER(GET_DMISTER)
        ).setRegistryName(name).registerHead(DMItems.ITEMS, DMBlocks.BLOCKS);
    }

    public static void register(RegistryEvent.Register<DragonVariant> event) {
        VALUES.forEach(event.getRegistry()::register);
    }
}
