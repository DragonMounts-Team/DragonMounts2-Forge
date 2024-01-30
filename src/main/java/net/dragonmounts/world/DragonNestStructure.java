package net.dragonmounts.world;

import com.mojang.serialization.Codec;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DragonNestStructure<T extends DragonNestConfig> extends Structure<T> {
    public DragonNestStructure(Codec<T> codec) {
        super(codec);
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator generator, BiomeProvider provider, long seed, SharedSeedRandom random, int x, int y, Biome biome, ChunkPos pos, DragonNestConfig config) {
        return random.nextFloat() < config.chance;
    }

    @Nonnull
    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Nonnull
    public Structure.IStartFactory<T> getStartFactory() {
        return Start::new;
    }

    public static class Start<T extends DragonNestConfig> extends StructureStart<T> {
        protected Start(Structure<T> feature, int chunkX, int chunkY, MutableBoundingBox box, int references, long seed) {
            super(feature, chunkX, chunkY, box, references, seed);
        }

        @Override
        public void generatePieces(DynamicRegistries registries, ChunkGenerator generator, TemplateManager manager, int chunkX, int chunkY, Biome biome, DragonNestConfig config) {
            this.pieces.add(config.generatePiece(this.random, registries, generator, manager, chunkX, chunkY, biome));
            this.calculateBoundingBox();
        }
    }
}
