package net.dragonmounts.world.impl;

import net.dragonmounts.init.DMFeatures;
import net.dragonmounts.util.BlockUtil;
import net.dragonmounts.world.DragonNestPiece;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class UndergroundDragonNestPiece extends DragonNestPiece {
    public UndergroundDragonNestPiece(Template template, BlockPos pos, ResourceLocation location, Rotation rotation, Mirror mirror, BlockPos pivot) {
        super(DMFeatures.UNDERGROUND_DRAGON_NEST_PIECE_TYPE, pos, location, rotation, mirror);
        this.loadTemplate(template, pivot);
    }

    public UndergroundDragonNestPiece(TemplateManager manager, CompoundNBT tag) {
        super(DMFeatures.UNDERGROUND_DRAGON_NEST_PIECE_TYPE, tag);
        Template template = manager.getOrCreate(this.templateLocation);
        this.loadTemplate(template, new BlockPos(template.getSize().getX() >> 1, 0, template.getSize().getZ() >> 1));
    }

    private void loadTemplate(Template template, BlockPos pivot) {
        this.setup(template, this.templatePosition, new PlacementSettings()
                .setRotation(this.rotation)
                .setRotationPivot(pivot)
                .setMirror(this.mirror)
                .addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_AND_AIR)
        );
    }

    @Override
    public boolean postProcess(
            ISeedReader level,
            StructureManager manager,
            ChunkGenerator generator,
            Random random,
            MutableBoundingBox box,
            ChunkPos chunkPos,
            BlockPos blockPos
    ) {
        BlockPos size = this.template.getSize();
        int sizeX = size.getX();
        int sizeZ = size.getZ();
        int y = generator.getSeaLevel();
        int max = random.nextInt(generator.getGenDepth() - y) + y;
        if (sizeX != 0 && sizeZ != 0) {
            int centerX = this.templatePosition.getX() + sizeX >> 1;
            int centerZ = this.templatePosition.getZ() + sizeZ >> 1;
            IBlockReader reader = generator.getBaseColumn(centerX, centerZ);
            for (BlockPos.Mutable pos = new BlockPos.Mutable(centerX, y, centerZ); y < max; ++y) {
                BlockState state = reader.getBlockState(pos);
                pos.move(Direction.UP);
                if (BlockUtil.isAir(reader.getBlockState(pos)) && state.isFaceSturdy(reader, pos, Direction.UP)) {
                    break;
                }
            }
        }
        this.templatePosition = new BlockPos(this.templatePosition.getX(), Math.max(1, y - random.nextInt(8)), this.templatePosition.getZ());
        return super.postProcess(level, manager, generator, random, box, chunkPos, blockPos);
    }
}
