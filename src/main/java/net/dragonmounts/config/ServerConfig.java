package net.dragonmounts.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ServerConfig {
    private static ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ServerConfig INSTANCE = new ServerConfig(BUILDER);
    public final ForgeConfigSpec.ConfigValue<Boolean> debug;
    // config properties
    public final ForgeConfigSpec.ConfigValue<Boolean> shouldChangeBreedViaHabitatOrBlock;
    public final ForgeConfigSpec.ConfigValue<Boolean> canDragonDespawn;
    public final ForgeConfigSpec.ConfigValue<Boolean> canIceBreathBePermanent;
    public final ForgeConfigSpec.ConfigValue<Boolean> canFireBreathAffectBlocks;
    public final ForgeConfigSpec.ConfigValue<Boolean> useCommandingPlayer;
    public final ForgeConfigSpec.ConfigValue<Boolean> allowOtherPlayerControl;
    public final ForgeConfigSpec.ConfigValue<Boolean> allowBreeding;
    public final ForgeConfigSpec.ConfigValue<Boolean> canSpawnSurfaceDragonNest;
    public final ForgeConfigSpec.ConfigValue<Boolean> canSpawnUnderGroundNest;
    public final ForgeConfigSpec.ConfigValue<Boolean> canSpawnNetherNest;
    public final ForgeConfigSpec.ConfigValue<Boolean> canSpawnEndNest;
    public final ForgeConfigSpec.ConfigValue<Integer> dragonWanderFromHomeDist;
    public final ForgeConfigSpec.ConfigValue<Double> maxFlightHeight;
    public final ForgeConfigSpec.ConfigValue<Boolean> useDimensionBlackList;
    public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> dragonBlacklistedDimensions;
    public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> dragonWhitelistedDimensions;
    // chances
    public final ForgeConfigSpec.ConfigValue<Integer> FireNestRarity;
    public final ForgeConfigSpec.ConfigValue<Integer> TerraNestRarity;
    public final ForgeConfigSpec.ConfigValue<Integer> ForestNestRarity;
    public final ForgeConfigSpec.ConfigValue<Integer> SunlightNestRarity;
    public final ForgeConfigSpec.ConfigValue<Integer> OceanNestRarity;
    public final ForgeConfigSpec.ConfigValue<Integer> EnchantNestRarity;
    public final ForgeConfigSpec.ConfigValue<Integer> JungleNestRarity;
    public final ForgeConfigSpec.ConfigValue<Integer> WaterNestRarity;
    public final ForgeConfigSpec.ConfigValue<Integer> IceNestRarity;
    public final ForgeConfigSpec.ConfigValue<Integer> netherNestRarity;
    public final ForgeConfigSpec.ConfigValue<Integer> netherNestRarityInX;
    public final ForgeConfigSpec.ConfigValue<Integer> netherNestRarityInZ;
    public final ForgeConfigSpec.ConfigValue<Integer> zombieNestRarity;
    public final ForgeConfigSpec.ConfigValue<Integer> zombieNestRarityInX;
    public final ForgeConfigSpec.ConfigValue<Integer> zombieNestRarityInZ;

    private ServerConfig(ForgeConfigSpec.Builder builder) {
        this.debug = builder
                .comment("Debug mode. You need to restart Minecraft for the change to take effect. Unless you're a developer or are told to activate it, you don't want to set this to true.")
                .define("debug", false);
        builder.push("gameplay");
        shouldChangeBreedViaHabitatOrBlock = builder.comment("Enables changing of egg breeds via block or environment").define("can eggs change breeds", true);
        canDragonDespawn = builder.comment("Enables or Disables dragons ability to despawn, works only for adult non tamed dragons").define("can dragons despawn", true);
        canIceBreathBePermanent = builder.comment("refers to the ice breath for the dragon in water, set true if you want the ice block to be permanent. false otherwise.").define("can ice breath be permanent", false);
        canFireBreathAffectBlocks = builder.comment("refers to the fire breath to affect blocks").define("fire breath affect blocks", true);
        useCommandingPlayer = builder.comment("Use a commanding player method(Experimental) to make dragons land on multiple players").define("use Commanding Player", false);
        allowOtherPlayerControl = builder.comment("Disable or enable the dragon's ability to obey other players").define("Allow Other Player's Control", true);
        allowBreeding = builder.comment("Allow or disallow breeding").define("Allow Other Breeding", true);
        canSpawnSurfaceDragonNest = builder.comment("Enables spawning of nests in extreme hills").define("can spawn surface dragon nest", true);
        canSpawnUnderGroundNest = builder.comment("Enables spawning of nests in underground").define("can spawn underground nest", true);
        canSpawnNetherNest = builder.comment("Enables spawning of nether, zombie, and skeleton dragon nests in the nether").define("can spawn nether nest", true);
        canSpawnEndNest = builder.comment("Enables spawning of end dragon nests in end cities").define("can spawn end nest", true);
        // chances
        dragonWanderFromHomeDist = builder.comment("Wander From Home Dist").define("Wander From Home Dist", 50);
        maxFlightHeight = builder.comment("Max flight for dragons circling players on a whistle").define("Max Flight Height", 20.0D);
        useDimensionBlackList = builder.comment("true to use dimensional blacklist, false to use the whitelist.").define("Use Dimension Blacklist", true);
        dragonBlacklistedDimensions = builder.comment("Dragons cannot spawn in these dimensions' IDs").defineList("Blacklisted Dragon Dimensions", Arrays.asList(-1, 1), i -> i instanceof Integer);
        dragonWhitelistedDimensions = builder.comment("Dragons can only spawn in these dimensions' IDs").defineList("Whitelisted Dragon Dimensions", Collections.singletonList(0), i -> i instanceof Integer);
        // config properties
        builder.pop();
        builder.push("property");
        FireNestRarity = builder.comment("Determines how rare fire dragon nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
                + "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Fire Nest Rarity", 150);
        TerraNestRarity = builder.comment("Determines how rare terra dragon nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
                + "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Terra Nest Rarity", 220);
        ForestNestRarity = builder.comment("Determines how rare Forest Plains dragon nests will mainly spawn. I did this because the forest biomes is too common thus making the forest breed to common. "
                + "Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
                + "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Forest Nest Rarity", 220);
        SunlightNestRarity = builder.comment("Determines how rare sunlight dragon temples will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
                + "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Sunlight Nest Rarity", 60);
        OceanNestRarity = builder.comment("Determines how rare moonlight or aether dragon temples will spawn above the ocean. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
                + "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Ocean Nest Rarity", 8000);
        EnchantNestRarity = builder.comment("Determines how rare forest enchant dragon nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
                + "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Enchant Nest Rarity", 300);
        JungleNestRarity = builder.comment("Determines how rare forest jungle dragon nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
                + "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Jungle Nest Rarity", 800);
        WaterNestRarity = builder.comment("Determines how rare water dragon nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
                + "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Water Nest Rarity", 180);
        IceNestRarity = builder.comment("Determines how rare ice dragon nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
                + "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Ice Nest Rarity", 200);
        netherNestRarity = builder.comment("Determines how rare nether nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn)"
                + "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Nether Nest Chance", 200);
        netherNestRarityInX = builder.comment("Determines how rare nether nests will spawn in the X Axis. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn)"
                + "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Nether Nest Rarity X", 16);
        netherNestRarityInZ = builder.comment("Determines how rare nether nests will spawn in the Z Axis. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn)"
                + "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Nest Nether Rarity Z", 16);
        zombieNestRarity = builder.comment("Determines how rare zombie nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn)"
                + "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Zombie Nest Chance", 500);
        zombieNestRarityInX = builder.comment("Determines how rare zombie nests will spawn in the X Axis. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn)"
                + "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Zombie Nest Rarity X", 28);
        zombieNestRarityInZ = builder.comment("Determines how rare zombie nests will spawn in the Z Axis. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn)"
                + "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Zombie Nest Rarity Z", 28);
        builder.pop();
    }

    public static void init(ModLoadingContext context) {
        if (BUILDER != null) {
            context.registerConfig(ModConfig.Type.SERVER, BUILDER.build());
            BUILDER = null;
        }
    }
}
