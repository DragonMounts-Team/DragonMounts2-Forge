package net.dragonmounts.world.impl;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dragonmounts.world.DragonNestConfig;
import net.dragonmounts.world.DragonNestPiece;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class SurfaceDragonNestConfig extends DragonNestConfig {
    public static final Codec<SurfaceDragonNestConfig> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.doubleRange(0, 1).fieldOf("chance").orElse(0.5).forGetter(config -> config.chance),
            new ListCodec<>(ResourceLocation.CODEC).fieldOf("structures").forGetter(config -> config.structures)
    ).apply(builder, SurfaceDragonNestConfig::new));

    public SurfaceDragonNestConfig(double chance, List<ResourceLocation> structures) {
        super(chance, structures);
    }

    @Override
    public DragonNestPiece generatePiece(Random random, DynamicRegistries registries, ChunkGenerator generator, TemplateManager manager, int chunkX, int chunkY, Biome biome) {
        ResourceLocation templateLocation = this.structures.get(random.nextInt(this.structures.size()));
        Rotation rotation = Util.getRandom(Rotation.values(), random);
        Mirror mirror = Util.getRandom(Mirror.values(), random);
        Template template = manager.getOrCreate(templateLocation);
        BlockPos pivot = new BlockPos(template.getSize().getX() >> 1, 0, template.getSize().getZ() >> 1);
        BlockPos blockpos1 = new ChunkPos(chunkX, chunkY).getWorldPosition();
        MutableBoundingBox boundingBox = template.getBoundingBox(blockpos1, rotation, pivot, mirror);
        Vector3i center = boundingBox.getCenter();
        BlockPos blockpos2 = new BlockPos(blockpos1.getX(), getY(random, generator, generator.getBaseHeight(center.getX(), center.getZ(), Heightmap.Type.WORLD_SURFACE_WG) - 1, boundingBox.getYSpan(), boundingBox), blockpos1.getZ());
        return new SurfaceDragonNestPiece(template, blockpos2, templateLocation, rotation, mirror, pivot);
    }
}
