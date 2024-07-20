package net.dragonmounts.block.entity;

import net.dragonmounts.block.DragonCoreBlock;
import net.dragonmounts.init.DMBlockEntities;
import net.dragonmounts.inventory.DragonCoreContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static net.minecraft.tileentity.ShulkerBoxTileEntity.AnimationStatus;


/**
 * @see net.minecraft.tileentity.ShulkerBoxTileEntity
 */
@ParametersAreNonnullByDefault
public class DragonCoreBlockEntity extends LockableLootTileEntity implements ITickableTileEntity {
    private static final String TRANSLATION_KEY = "container.dragonmounts.dragon_core";
    private NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    private int openCount;
    private AnimationStatus status = AnimationStatus.CLOSED;
    private float progress;
    private float progressOld;

    public DragonCoreBlockEntity() {
        super(DMBlockEntities.DRAGON_CORE.get());
    }

    @Override
    public void tick() {
        this.updateAnimation();
        if (this.status == AnimationStatus.OPENING || this.status == AnimationStatus.CLOSING) {
            this.moveCollidedEntities();
        }
    }

    protected void updateAnimation() {
        this.progressOld = this.progress;
        switch (this.status) {
            case CLOSED:
                this.progress = 0.0F;
                break;
            case OPENING:
                this.progress += 0.1F;
                if (this.progress >= 1.0F) {
                    this.moveCollidedEntities();
                    this.status = AnimationStatus.OPENED;
                    this.progress = 1.0F;
                    this.doNeighborUpdates();
                }
                break;
            case CLOSING:
                this.progress -= 0.1F;
                if (this.progress <= 0.1F) {
                    this.progress = 0.0F;
                    if (this.level != null) {
                        this.level.levelEvent(2003, this.worldPosition.above(), 0);
                        this.level.destroyBlock(this.worldPosition, true);
                    }
                    this.status = AnimationStatus.CLOSED;
                }
                break;
            case OPENED:
                this.progress = 1.0F;
        }

    }

    public AnimationStatus getStatus() {
        return this.status;
    }

    public AxisAlignedBB getBoundingBox() {
        return VoxelShapes.block().bounds().expandTowards(0, 0.5 * this.getProgress(1.0F), 0);
    }

    private void moveCollidedEntities() {
        //noinspection DataFlowIssue
        if (this.level.getBlockState(this.worldPosition).getBlock() instanceof DragonCoreBlock) {
            AxisAlignedBB box = this.getBoundingBox().move(this.worldPosition);
            List<Entity> list = this.level.getEntities(null, box);
            if (list.isEmpty()) return;
            for (Entity entity : list) {
                if (entity.getPistonPushReaction() != PushReaction.IGNORE) {
                    entity.move(MoverType.SHULKER_BOX, new Vector3d(0, box.maxY + 0.01 - entity.getBoundingBox().minY, 0));
                }
            }
        }
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean triggerEvent(int id, int data) {
        if (id == 1) {
            this.openCount = data;
            if (data == 0) {
                this.status = AnimationStatus.CLOSING;
                this.doNeighborUpdates();
            } else if (data == 1) {
                this.status = AnimationStatus.OPENING;
                this.doNeighborUpdates();
            }
            return true;
        }
        return super.triggerEvent(id, data);
    }

    private void doNeighborUpdates() {
        //noinspection DataFlowIssue
        this.getBlockState().updateNeighbourShapes(this.level, this.worldPosition, 3);
    }

    @Override
    public void startOpen(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.openCount < 0) {
                this.openCount = 1;
            } else {
                ++this.openCount;
            }
            if (this.level != null) {
                this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
                if (this.openCount == 1) {
                    this.level.playSound(null, this.worldPosition, SoundEvents.ENDER_CHEST_OPEN, SoundCategory.BLOCKS, 0.9F, this.level.random.nextFloat() * 0.1F + 0.9F);
                    this.level.playSound(null, this.worldPosition, SoundEvents.ENDER_DRAGON_AMBIENT, SoundCategory.HOSTILE, 0.05F, this.level.random.nextFloat() * 0.3F + 0.9F);
                    this.level.playSound(null, this.worldPosition, SoundEvents.END_PORTAL_SPAWN, SoundCategory.BLOCKS, 0.08F, this.level.random.nextFloat() * 0.1F + 0.9F);
                }
            }
        }
    }

    @Override
    public void stopOpen(PlayerEntity player) {
        if (!player.isSpectator()) {
            //noinspection DataFlowIssue
            this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, --this.openCount);
        }
    }

    @Nonnull
    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent(TRANSLATION_KEY);
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        if (!this.tryLoadLootTable(compound)) {
            ItemStackHelper.loadAllItems(compound, this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY));
        }
    }

    @Nonnull
    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        if (!this.trySaveLootTable(compound)) {
            ItemStackHelper.saveAllItems(compound, this.items);
        }
        return compound;
    }

    @Nonnull
    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    public float getProgress(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.progressOld, this.progress);
    }

    @Nonnull
    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new DragonCoreContainer(id, player, this);
    }

    public boolean isClosed() {
        return this.status == AnimationStatus.CLOSED;
    }
}
