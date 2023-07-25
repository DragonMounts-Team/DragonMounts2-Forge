package net.dragonmounts3.world.impl;

import net.dragonmounts3.init.DMFeatures;
import net.dragonmounts3.world.DragonNestPiece;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class AerialDragonNestPiece extends DragonNestPiece {
    protected final int distance;

    public AerialDragonNestPiece(Template template, BlockPos pos, ResourceLocation location, Rotation rotation, Mirror mirror, BlockPos pivot, int distance) {
        super(DMFeatures.AERIAL_DRAGON_NEST_PIECE_TYPE, pos, location, rotation, mirror);
        this.distance = distance;
        this.loadTemplate(template, pivot);
    }

    public AerialDragonNestPiece(TemplateManager manager, CompoundNBT compound) {
        super(DMFeatures.AERIAL_DRAGON_NEST_PIECE_TYPE, compound);
        this.distance = compound.getInt("Distance");
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
    protected void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Distance", this.distance);
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
        int levelHeight = level.getHeight();
        int y = levelHeight;
        if (sizeX != 0 && sizeZ != 0) {
            BlockPos position = this.templatePosition.offset(sizeX - 1, 0, sizeZ - 1);
            for (BlockPos pos : BlockPos.betweenClosed(this.templatePosition, position)) {
                y = Math.min(y, level.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ()));
            }
        }
        this.templatePosition = new BlockPos(this.templatePosition.getX(), MathHelper.clamp(y + this.distance, generator.getSeaLevel(), levelHeight - size.getY() - 1), this.templatePosition.getZ());
        return super.postProcess(level, manager, generator, random, box, chunkPos, blockPos);
    }
}
