package net.dragonmounts3;

import net.minecraftforge.common.ForgeConfigSpec;

public class DragonMountsConfig {

	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	// config properties
	public static final ForgeConfigSpec.ConfigValue<Boolean> debug;
	public static final ForgeConfigSpec.ConfigValue<Boolean> disableBlockOverride;
	public static final ForgeConfigSpec.ConfigValue<Boolean> shouldChangeBreedViaHabitatOrBlock;
	public static final ForgeConfigSpec.ConfigValue<Boolean> canDragonDespawn;
	public static final ForgeConfigSpec.ConfigValue<Boolean> canIceBreathBePermanent;
	public static final ForgeConfigSpec.ConfigValue<Boolean> canFireBreathAffectBlocks;
	public static final ForgeConfigSpec.ConfigValue<Boolean> useCommandingPlayer;
	public static final ForgeConfigSpec.ConfigValue<Boolean> allowOtherPlayerControl;
	public static final ForgeConfigSpec.ConfigValue<Boolean> allowBreeding;
	public static final ForgeConfigSpec.ConfigValue<Boolean> canSpawnSurfaceDragonNest;
	public static final ForgeConfigSpec.ConfigValue<Boolean> canSpawnUnderGroundNest;
	public static final ForgeConfigSpec.ConfigValue<Boolean> canSpawnNetherNest;
	public static final ForgeConfigSpec.ConfigValue<Boolean> canSpawnEndNest;
	public static final ForgeConfigSpec.ConfigValue<Integer> hungerDecrement;
	public static final ForgeConfigSpec.ConfigValue<Integer> REG_FACTOR;
	public static final ForgeConfigSpec.ConfigValue<Double> BASE_HEALTH;
	public static final ForgeConfigSpec.ConfigValue<Double> BASE_DAMAGE;
	public static final ForgeConfigSpec.ConfigValue<Double> ARMOR;

	// chances
	public static final ForgeConfigSpec.ConfigValue<Integer> FireNestRarity;
	public static final ForgeConfigSpec.ConfigValue<Integer> TerraNestRarity;
	public static final ForgeConfigSpec.ConfigValue<Integer> ForestNestRarity;
	public static final ForgeConfigSpec.ConfigValue<Integer> SunlightNestRarity;
	public static final ForgeConfigSpec.ConfigValue<Integer> OceanNestRarity;
	public static final ForgeConfigSpec.ConfigValue<Integer> EnchantNestRarity;
	public static final ForgeConfigSpec.ConfigValue<Integer> JungleNestRarity;
	public static final ForgeConfigSpec.ConfigValue<Integer> WaterNestRarity;
	public static final ForgeConfigSpec.ConfigValue<Integer> IceNestRarity;
	public static final ForgeConfigSpec.ConfigValue<Integer> netherNestRarity;
	public static final ForgeConfigSpec.ConfigValue<Integer> netherNestRarityInX;
	public static final ForgeConfigSpec.ConfigValue<Integer> netherNestRarityInZ;
	public static final ForgeConfigSpec.ConfigValue<Integer> zombieNestRarity;
	public static final ForgeConfigSpec.ConfigValue<Integer> zombieNestRarityInX;
	public static final ForgeConfigSpec.ConfigValue<Integer> zombieNestRarityInZ;
	public static final ForgeConfigSpec.ConfigValue<Integer> dragonWanderFromHomeDist;
	public static final ForgeConfigSpec.ConfigValue<Double> thirdPersonZoom;
	public static final ForgeConfigSpec.ConfigValue<Double> maxFlightHeight;
	public static final ForgeConfigSpec.ConfigValue<Boolean> useDimensionBlackList;
	public static final ForgeConfigSpec.ConfigValue<int[]> dragonBlacklistedDimensions;
	public static final ForgeConfigSpec.ConfigValue<int[]> dragonWhitelistedDimensions;

	static {
		BUILDER.push("Some config of Dragon Mount 3");
		// config properties
		debug = BUILDER.comment("Debug mode. You need to restart Minecraft for the change to take effect. Unless you're a developer or are told to activate it, you don't want to set this to true.").define("debug", false);
		disableBlockOverride = BUILDER.comment("Disables right-click override on the vanilla dragon egg block. May help to fix issues with other mods.").define("disable block override", false);
		shouldChangeBreedViaHabitatOrBlock = BUILDER.comment("Enables changing of egg breeds via block or environment").define("can eggs change breeds", true);
		canDragonDespawn = BUILDER.comment("Enables or Disables dragons ability to despawn, works only for adult non tamed dragons").define("can dragons despawn", true);
		canIceBreathBePermanent = BUILDER.comment("refers to the ice breath for the dragon in water, set true if you want the ice block to be permanent. false otherwise.").define("can ice breath be permanent", false);
		canFireBreathAffectBlocks = BUILDER.comment("refers to the fire breath to affect blocks").define("fire breath affect blocks", true);
		useCommandingPlayer = BUILDER.comment("Use a commanding player method(Experimental) to make dragons land on multiple players").define("use Commanding Player", false);
		allowOtherPlayerControl = BUILDER.comment("Disable or enable the dragon's ability to obey other players").define("Allow Other Player's Control", true);
		allowBreeding = BUILDER.comment("Allow or disallow breeding").define("Allow Other Breeding", true);
		canSpawnSurfaceDragonNest = BUILDER.comment("Enables spawning of nests in extreme hills").define("can spawn surface dragon nest", true);
		canSpawnUnderGroundNest = BUILDER.comment("Enables spawning of nests in underground").define("can spawn underground nest", true);
		canSpawnNetherNest = BUILDER.comment("Enables spawning of nether, zombie, and skeleton dragon nests in the nether").define("can spawn nether nest", true);
		canSpawnEndNest = BUILDER.comment("Enables spawning of end dragon nests in end cities").define("can spawn end nest", true);
		hungerDecrement = BUILDER.comment("More numbers slower, i.e. gets a number from the factor of (3000) to 1 per tick (millisecond) if it equals to 1 reduce hunger, don't make it too low or might crash the game").define("Hunger Speed", 6000);
		REG_FACTOR = BUILDER.comment("Higher numbers slower regen for dragons").define("Health Regen Speed", 75);
		BASE_HEALTH = BUILDER.comment("Dragon Base Health").define("Dragon Base Health", 90.0D);
		BASE_DAMAGE = BUILDER.comment("Damage for dragon attack").define("Damage", 12.0D);
		ARMOR = BUILDER.comment("Makes Dragons Tougher or Not").define("Armor", 8.0D);
		// chances
		FireNestRarity = BUILDER.comment("Determines how rare fire dragon nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
				+ "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Fire Nest Rarity", 150);
		TerraNestRarity = BUILDER.comment("Determines how rare terra dragon nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
				+ "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Terra Nest Rarity", 220);
		ForestNestRarity = BUILDER.comment("Determines how rare Forest Plains dragon nests will mainly spawn. I did this because the forest biomes is too common thus making the forest breed to common. "
				+ "Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
				+ "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Forest Nest Rarity", 220);
		SunlightNestRarity = BUILDER.comment("Determines how rare sunlight dragon temples will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
				+ "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Sunlight Nest Rarity", 60);
		OceanNestRarity = BUILDER.comment("Determines how rare moonlight or aether dragon temples will spawn above the ocean. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
				+ "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Ocean Nest Rarity", 8000);
		EnchantNestRarity = BUILDER.comment("Determines how rare forest enchant dragon nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
				+ "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Enchant Nest Rarity", 300);
		JungleNestRarity = BUILDER.comment("Determines how rare forest jungle dragon nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
				+ "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Jungle Nest Rarity", 800);
		WaterNestRarity = BUILDER.comment("Determines how rare water dragon nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
				+ "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Water Nest Rarity", 180);
		IceNestRarity = BUILDER.comment("Determines how rare ice dragon nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn), "
				+ "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Ice Nest Rarity", 200);
		netherNestRarity = BUILDER.comment("Determines how rare nether nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn)"
				+ "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Nether Nest Chance", 200);
		netherNestRarityInX = BUILDER.comment("Determines how rare nether nests will spawn in the X Axis. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn)"
				+ "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Nether Nest Rarity X", 16);
		netherNestRarityInZ = BUILDER.comment("Determines how rare nether nests will spawn in the Z Axis. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn)"
				+ "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Nest Nether Rarity Z", 16);
		zombieNestRarity = BUILDER.comment("Determines how rare zombie nests will mainly spawn. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn)"
				+ "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Zombie Nest Chance", 500);
		zombieNestRarityInX = BUILDER.comment("Determines how rare zombie nests will spawn in the X Axis. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn)"
				+ "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Zombie Nest Rarity X", 28);
		zombieNestRarityInZ = BUILDER.comment("Determines how rare zombie nests will spawn in the Z Axis. Higher numbers = higher rarity (in other words, how many blocks for another nest to spawn)"
				+ "(Note: Experiment on a new world when editing these numbers because it may cause damages to your own worlds)").define("Zombie Nest Rarity Z", 28);
		dragonWanderFromHomeDist = BUILDER.comment("Wander From Home Dist").define("Wander From Home Dist", 50);
		thirdPersonZoom = BUILDER.comment("Zoom out for third person 2 while riding the the dragon and dragon carriages DO NOT EXAGGERATE IF YOU DON'T WANT CORRUPTED WORLDS").define("Third Person Zoom BACK", 20.0D);
		maxFlightHeight = BUILDER.comment("Max flight for dragons circling players on a whistle").define("Max Flight Height", 20.0D);
		useDimensionBlackList = BUILDER.comment("true to use dimensional blacklist, false to use the whitelist.").define("Use Dimension Blacklist", true);
		dragonBlacklistedDimensions = BUILDER.comment("Dragons cannot spawn in these dimensions' IDs").define("Blacklisted Dragon Dimensions", new int[]{-1, 1});
		dragonWhitelistedDimensions = BUILDER.comment("Dragons can only spawn in these dimensions' IDs").define("Whitelisted Dragon Dimensions", new int[]{0});
		BUILDER.pop();
		SPEC = BUILDER.build();
	}

}