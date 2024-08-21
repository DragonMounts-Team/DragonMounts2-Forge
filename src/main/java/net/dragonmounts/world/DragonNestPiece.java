package net.dragonmounts.world;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public abstract class DragonNestPiece extends TemplateStructurePiece {
    protected final ResourceLocation templateLocation;
    protected final Rotation rotation;
    protected final Mirror mirror;

    public DragonNestPiece(IStructurePieceType type, BlockPos pos, ResourceLocation templateLocation, Rotation rotation, Mirror mirror) {
        super(type, 0);
        this.templatePosition = pos;
        this.rotation = rotation;
        this.templateLocation = templateLocation;
        this.mirror = mirror;
    }

    public DragonNestPiece(IStructurePieceType type, CompoundNBT tag) {
        super(type, tag);
        this.rotation = Rotation.valueOf(tag.getString("Rotation"));
        this.templateLocation = new ResourceLocation(tag.getString("Template"));
        this.mirror = Mirror.valueOf(tag.getString("Mirror"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("Template", this.templateLocation.toString());
        tag.putString("Rotation", this.rotation.name());
        tag.putString("Mirror", this.mirror.name());
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, IServerWorld level, Random random, MutableBoundingBox boundingBox) {

    }
}
