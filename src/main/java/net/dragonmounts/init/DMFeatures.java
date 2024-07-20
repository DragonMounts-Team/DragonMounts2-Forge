package net.dragonmounts.init;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import net.dragonmounts.DragonMounts;
import net.dragonmounts.registry.DragonType;
import net.dragonmounts.registry.StructureRegistry;
import net.dragonmounts.world.DragonNestConfig;
import net.dragonmounts.world.DragonNestStructure;
import net.dragonmounts.world.impl.*;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Supplier;

import static net.dragonmounts.DragonMounts.MOD_ID;
import static net.dragonmounts.DragonMounts.makeId;
import static net.minecraft.world.gen.feature.structure.IStructurePieceType.setPieceId;

public class DMFeatures {
    public static final DeferredRegister<Structure<?>> STRUCTURE_FEATURE = DragonMounts.create(ForgeRegistries.STRUCTURE_FEATURES);
    public static final IStructurePieceType AERIAL_DRAGON_NEST_PIECE_TYPE = setPieceId(AerialDragonNestPiece::new, MOD_ID + ":aerial_dragon_nest");
    public static final IStructurePieceType SURFACE_DRAGON_NEST_PIECE_TYPE = setPieceId(SurfaceDragonNestPiece::new, MOD_ID + ":surface_dragon_nest");
    public static final IStructurePieceType UNDERGROUND_DRAGON_NEST_PIECE_TYPE = setPieceId(UndergroundDragonNestPiece::new, MOD_ID + ":underground_dragon_nest");
    public static final StructureRegistry<DragonType> DRAGON_NEST = new StructureRegistry<>();
    public static final StructureRegistry.Holder<AerialDragonNestConfig> AETHER_DRAGON_NEST =
            registerDragonNest("aether_dragon_nest", DragonTypes.AETHER, AerialDragonNestConfig.CODEC, new AerialDragonNestConfig(0.25, 32, 56, Arrays.asList(
                    makeId("aether"),
                    makeId("aether2")
            )), 32, 8, 12345678);
    public static final StructureRegistry.Holder<AerialDragonNestConfig> ENCHANT_DRAGON_NEST =
            registerDragonNest("enchant_dragon_nest", DragonTypes.ENCHANT, AerialDragonNestConfig.CODEC, new AerialDragonNestConfig(0.25, 0, 32, Collections.singletonList(makeId("enchant"))), 32, 8, 23456789);
    public static final StructureRegistry.Holder<SurfaceDragonNestConfig> FIRE_DRAGON_NEST =
            registerDragonNest("fire_dragon_nest", DragonTypes.FIRE, SurfaceDragonNestConfig.CODEC, new SurfaceDragonNestConfig(0.25, Arrays.asList(
                    makeId("fire"),
                    makeId("fire2")
            )), 32, 8, 34567890);
    public static final StructureRegistry.Holder<SurfaceDragonNestConfig> FOREST_DRAGON_NEST =
            registerDragonNest("forest_dragon_nest", DragonTypes.FOREST, SurfaceDragonNestConfig.CODEC, new SurfaceDragonNestConfig(0.25, Arrays.asList(
                    makeId("forest1"),
                    makeId("forest2")
            )), 32, 8, 45678901);
    public static final StructureRegistry.Holder<SurfaceDragonNestConfig> ICE_DRAGON_NEST =
            registerDragonNest("ice_dragon_nest", DragonTypes.ICE, SurfaceDragonNestConfig.CODEC, new SurfaceDragonNestConfig(0.25, Collections.singletonList(makeId("ice"))), 32, 8, 56789012);
    public static final StructureRegistry.Holder<SurfaceDragonNestConfig> MOONLIGHT_DRAGON_NEST =
            registerDragonNest("moonlight_dragon_nest", DragonTypes.MOONLIGHT, SurfaceDragonNestConfig.CODEC, new SurfaceDragonNestConfig(0.25, Collections.singletonList(makeId("moonlight"))), 32, 8, 67890123);
    public static final StructureRegistry.Holder<UndergroundDragonNestConfig> NETHER_DRAGON_NEST =
            registerDragonNest("nether_dragon_nest", DragonTypes.NETHER, UndergroundDragonNestConfig.CODEC, new UndergroundDragonNestConfig(0.25, Collections.singletonList(makeId("nether"))), 32, 8, 78901234);
    public static final StructureRegistry.Holder<UndergroundDragonNestConfig> SKELETON_DRAGON_NEST =
            registerDragonNest("skeleton_dragon_nest", DragonTypes.SKELETON, UndergroundDragonNestConfig.CODEC, new UndergroundDragonNestConfig(0.25, Collections.singletonList(makeId("skeleton"))), 32, 8, 89012345);
    public static final StructureRegistry.Holder<SurfaceDragonNestConfig> SUNLIGHT_DRAGON_NEST =
            registerDragonNest("sunlight_dragon_nest", DragonTypes.SUNLIGHT, SurfaceDragonNestConfig.CODEC, new SurfaceDragonNestConfig(0.25, Collections.singletonList(makeId("sunlight"))), 32, 8, 90123456);
    public static final StructureRegistry.Holder<SurfaceDragonNestConfig> TERRA_DRAGON_NEST =
            registerDragonNest("terra_dragon_nest", DragonTypes.TERRA, SurfaceDragonNestConfig.CODEC, new SurfaceDragonNestConfig(0.25, Collections.singletonList(makeId("terra"))), 32, 8, 66666666);
    public static final StructureRegistry.Holder<SurfaceDragonNestConfig> WATER_DRAGON_NEST =
            registerDragonNest("water_dragon_nest", DragonTypes.WATER, SurfaceDragonNestConfig.CODEC, new SurfaceDragonNestConfig(0.25, Arrays.asList(
                    makeId("water1"),
                    makeId("water2"),
                    makeId("water3")
            )), 32, 8, 88888888);
    public static final StructureRegistry.Holder<UndergroundDragonNestConfig> ZOMBIE_DRAGON_NEST =
            registerDragonNest("zombie_dragon_nest", DragonTypes.ZOMBIE, UndergroundDragonNestConfig.CODEC, new UndergroundDragonNestConfig(0.25, Collections.singletonList(makeId("zombie"))), 32, 8, 99999999);

    public static <C extends DragonNestConfig> StructureRegistry.Holder<C> registerDragonNest(
            String name,
            DragonType type,
            Codec<C> codec,
            C config,
            int spacing,
            int separation,
            int slat
    ) {
        return DRAGON_NEST.register(STRUCTURE_FEATURE, name, type, new DragonNestStructure<>(codec), config, new StructureSeparationSettings(spacing, separation, slat));
    }

    public static void addDimensionalSpacing(WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) event.getWorld();
            RegistryKey<World> dimension = world.dimension();
            ChunkGenerator generator = world.getChunkSource().generator;
            if (generator instanceof FlatChunkGenerator && dimension.equals(World.OVERWORLD)) {
                return;
            }
            Map<Structure<?>, StructureSeparationSettings> map = new HashMap<>(generator.getSettings().structureConfig);
            if (dimension.equals(World.NETHER)) {
                map.put(NETHER_DRAGON_NEST.structure, NETHER_DRAGON_NEST.settings);
                map.put(SKELETON_DRAGON_NEST.structure, SKELETON_DRAGON_NEST.settings);
                map.put(ZOMBIE_DRAGON_NEST.structure, ZOMBIE_DRAGON_NEST.settings);
            } else if (dimension.equals(World.END)) {
                map.put(ENCHANT_DRAGON_NEST.structure, ENCHANT_DRAGON_NEST.settings);
            } else {
                map.put(AETHER_DRAGON_NEST.structure, AETHER_DRAGON_NEST.settings);
                map.put(FIRE_DRAGON_NEST.structure, FIRE_DRAGON_NEST.settings);
                map.put(FOREST_DRAGON_NEST.structure, FOREST_DRAGON_NEST.settings);
                map.put(ICE_DRAGON_NEST.structure, ICE_DRAGON_NEST.settings);
                map.put(MOONLIGHT_DRAGON_NEST.structure, MOONLIGHT_DRAGON_NEST.settings);
                map.put(SUNLIGHT_DRAGON_NEST.structure, SUNLIGHT_DRAGON_NEST.settings);
                map.put(TERRA_DRAGON_NEST.structure, TERRA_DRAGON_NEST.settings);
                map.put(WATER_DRAGON_NEST.structure, WATER_DRAGON_NEST.settings);
            }
            generator.getSettings().structureConfig = map;
        }
    }

    public static void loadBiome(BiomeLoadingEvent event) {
        List<Supplier<StructureFeature<?, ?>>> list = event.getGeneration().getStructures();
        switch (event.getCategory()) {
            case NONE:
            case RIVER:
            case MUSHROOM:
            case ICY:
            case PLAINS:
                break;
            case EXTREME_HILLS:
                list.add(FIRE_DRAGON_NEST::getFeature);
                break;
            case BEACH:
            case MESA:
                list.add(TERRA_DRAGON_NEST::getFeature);
                break;
            case SAVANNA:
                if (event.getClimate().temperature < 1.2) {//isHighland
                    list.add(FIRE_DRAGON_NEST::getFeature);
                }
                break;
            case THEEND:
                list.add(ENCHANT_DRAGON_NEST::getFeature);
                break;
            case TAIGA:
            case JUNGLE:
            case FOREST:
                list.add(FOREST_DRAGON_NEST::getFeature);
                break;
            case DESERT:
                list.add(TERRA_DRAGON_NEST::getFeature);
                list.add(SUNLIGHT_DRAGON_NEST::getFeature);
                break;
            case OCEAN:
                list.add(WATER_DRAGON_NEST::getFeature);
                break;
            case SWAMP:
                list.add(FOREST_DRAGON_NEST::getFeature);
                list.add(WATER_DRAGON_NEST::getFeature);
                break;
            case NETHER:
                list.add(NETHER_DRAGON_NEST::getFeature);
                list.add(SKELETON_DRAGON_NEST::getFeature);
                list.add(ZOMBIE_DRAGON_NEST::getFeature);
                break;
        }
        if (event.getClimate().precipitation.equals(Biome.RainType.SNOW)) {
            list.add(ICE_DRAGON_NEST::getFeature);
        }
    }

    public static void setup() {
        //ImmutableList.Builder<Structure<?>> listBuilder = ImmutableList.builder();
        ImmutableMap.Builder<Structure<?>, StructureSeparationSettings> mapBuilder = ImmutableMap.builder();
        for (StructureRegistry.Holder<?> holder : DRAGON_NEST) {
            ResourceLocation name = holder.structure.getRegistryName();
            //noinspection DataFlowIssue
            Structure.STRUCTURES_REGISTRY.put(name.toString(), holder.structure);
            //listBuilder.add(holder.structure);
            mapBuilder.put(holder.structure, holder.settings);
            Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, name, holder.feature);
            FlatGenerationSettings.STRUCTURE_FEATURES.put(holder.structure, holder.feature);
        }
        //Structure.NOISE_AFFECTING_FEATURES = listBuilder.addAll(Structure.NOISE_AFFECTING_FEATURES).build();
        DimensionStructuresSettings.DEFAULTS = mapBuilder.putAll(DimensionStructuresSettings.DEFAULTS).build();
    }
}
