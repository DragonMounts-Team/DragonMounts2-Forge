package net.dragonmounts3.world;

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

    public DragonNestPiece(IStructurePieceType type, CompoundNBT compound) {
        super(type, compound);
        this.rotation = Rotation.valueOf(compound.getString("Rotation"));
        this.templateLocation = new ResourceLocation(compound.getString("Template"));
        this.mirror = Mirror.valueOf(compound.getString("Mirror"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Template", this.templateLocation.toString());
        compound.putString("Rotation", this.rotation.name());
        compound.putString("Mirror", this.mirror.name());
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, IServerWorld level, Random random, MutableBoundingBox boundingBox) {

    }
}
