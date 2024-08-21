package net.dragonmounts.block.entity;

import net.dragonmounts.init.DMBlockEntities;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * @see net.minecraft.tileentity.SkullTileEntity
 */
public class DragonHeadBlockEntity extends TileEntity implements ITickableTileEntity {
    public DragonHeadBlockEntity() {
        super(DMBlockEntities.DRAGON_HEAD.get());
    }

    private int ticks;
    private boolean active;

    @SuppressWarnings("AssignmentUsedAsCondition")
    @Override
    public void tick() {
        //noinspection DataFlowIssue
        if (this.active /*--> */ = /* <--*/ this.level.hasNeighborSignal(this.worldPosition)) {
            ++this.ticks;
        }
    }

    public float getAnimation(float partialTicks) {
        return this.active ? partialTicks + (float) this.ticks : (float) this.ticks;
    }
}
