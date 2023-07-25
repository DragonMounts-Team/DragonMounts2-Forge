package net.dragonmounts3.world;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class DragonNestConfig implements IFeatureConfig {
    public final double chance;
    public final ImmutableList<ResourceLocation> structures;

    public DragonNestConfig(double chance, List<ResourceLocation> structures) {
        this.chance = chance;
        this.structures = ImmutableList.copyOf(structures);
    }

    public int getY(Random random, ChunkGenerator generator, int y, int ySpan, MutableBoundingBox boundingBox) {
        List<BlockPos> list1 = ImmutableList.of(new BlockPos(boundingBox.x0, 0, boundingBox.z0), new BlockPos(boundingBox.x1, 0, boundingBox.z0), new BlockPos(boundingBox.x0, 0, boundingBox.z1), new BlockPos(boundingBox.x1, 0, boundingBox.z1));
        List<IBlockReader> list = list1.stream().map(
                pos -> generator.getBaseColumn(pos.getX(), pos.getZ())).collect(Collectors.toList());
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int k;
        for (k = y; k > 15; --k) {
            int l = 0;
            blockpos$mutable.set(0, k, 0);
            for (IBlockReader iblockreader : list) {
                BlockState blockstate = iblockreader.getBlockState(blockpos$mutable);
                if (Heightmap.Type.WORLD_SURFACE_WG.isOpaque().test(blockstate)) {
                    ++l;
                    if (l == 3) {
                        return k;
                    }
                }
            }
        }
        return k;
    }

    public abstract DragonNestPiece generatePiece(Random random, DynamicRegistries registries, ChunkGenerator generator, TemplateManager manager, int chunkX, int chunkY, Biome biome);
}
