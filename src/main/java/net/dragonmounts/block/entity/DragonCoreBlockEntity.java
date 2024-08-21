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
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

import static net.minecraft.tileentity.ShulkerBoxTileEntity.AnimationStatus;


/**
 * @see net.minecraft.tileentity.ShulkerBoxTileEntity
 */
@ParametersAreNonnullByDefault
public class DragonCoreBlockEntity extends LockableLootTileEntity implements ISidedInventory, ITickableTileEntity {
    public static final int[] SLOTS = new int[]{0};
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
        switch (this.status.ordinal()) {
            case 0:
                this.progress = 0.0F;
                return;
            case 1: if ((this.progress += 0.1F) >= 1.0F) {
                    this.moveCollidedEntities();
                    this.status = AnimationStatus.OPENED;
                    this.progress = 1.0F;
                    this.doNeighborUpdates();
                }
                return;
            case 2:
                this.progress = 1.0F;
                return;
            case 3: if ((this.progress -= 0.1F) <= 0.1F) {
                this.progress = 0.0F;
                //noinspection DataFlowIssue
                this.level.levelEvent(2003, this.worldPosition.above(), 0);
                this.level.destroyBlock(this.worldPosition, true);
                this.status = AnimationStatus.CLOSED;
            }
        }
    }

    public AnimationStatus getStatus() {
        return this.status;
    }

    public AxisAlignedBB getBoundingBox() {
        return VoxelShapes.block().bounds().expandTowards(0, 0.5 * this.getProgress(1.0F), 0);
    }

    public float getProgress(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.progressOld, this.progress);
    }

    public boolean isClosed() {
        return this.status == AnimationStatus.CLOSED;
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
        if (id == 1) switch (this.openCount = data) {
            case 0:
                this.status = AnimationStatus.CLOSING;
                this.doNeighborUpdates();
                return true;
            case 1:
                this.status = AnimationStatus.OPENING;
                this.doNeighborUpdates();
                return true;
            default: return true;
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
            if (this.openCount < 0) this.openCount = 0;
            World level = this.level;
            BlockPos pos;
            //noinspection DataFlowIssue
            level.blockEvent(pos = this.worldPosition, this.getBlockState().getBlock(), 1, ++this.openCount);
            if (this.openCount == 1) {
                Random random = level.random;
                level.playSound(null, pos, SoundEvents.ENDER_CHEST_OPEN, SoundCategory.BLOCKS, 0.9F, random.nextFloat() * 0.1F + 0.9F);
                level.playSound(null, pos, SoundEvents.ENDER_DRAGON_AMBIENT, SoundCategory.HOSTILE, 0.05F, random.nextFloat() * 0.3F + 0.9F);
                level.playSound(null, pos, SoundEvents.END_PORTAL_SPAWN, SoundCategory.BLOCKS, 0.08F, random.nextFloat() * 0.1F + 0.9F);
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
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        if (!this.tryLoadLootTable(tag)) {
            ItemStackHelper.loadAllItems(tag, this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY));
        }
    }

    @Nonnull
    @Override
    public CompoundNBT save(CompoundNBT tag) {
        if (!this.trySaveLootTable(super.save(tag))) {
            ItemStackHelper.saveAllItems(tag, this.items);
        }
        return tag;
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

    @Nonnull
    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new DragonCoreContainer(id, player, this);
    }

    @Nonnull
    @Override
    public int[] getSlotsForFace(Direction side) {
        return SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return false;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.empty();// to make ISidedInventory works properly, ignoring the tampers from Forge
        }
        return super.getCapability(capability, facing);
    }
}
