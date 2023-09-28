package net.dragonmounts3.item;

import net.dragonmounts3.api.IDragonTypified;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.init.DMEntities;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.AbstractSpawner;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import static net.dragonmounts3.DragonMounts.ITEM_TRANSLATION_KEY_PREFIX;

public final class DragonSpawnEggItem extends ForgeSpawnEggItem implements IDragonTypified {
    private static final String TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_spawn_egg";
    private final DragonType type;
    private final CompoundNBT spawnData = new CompoundNBT();

    public DragonSpawnEggItem(DragonType type, int backgroundColor, int highlightColor, Properties props) {
        super(DMEntities.TAMEABLE_DRAGON, backgroundColor, highlightColor, props);
        this.type = type;
        this.spawnData.putString("id", DMEntities.TAMEABLE_DRAGON.getId().toString());
        this.spawnData.putString(DragonType.DATA_PARAMETER_KEY, type.getSerializedName().toString());
    }

    @Nonnull
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        if (!(world instanceof ServerWorld)) {
            return ActionResultType.SUCCESS;
        } else {
            ItemStack itemstack = context.getItemInHand();
            BlockPos pos1 = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = world.getBlockState(pos1);
            EntityType<?> entityType = this.getType(itemstack.getTag());
            if (blockstate.is(Blocks.SPAWNER)) {
                TileEntity tileentity = world.getBlockEntity(pos1);
                if (tileentity instanceof MobSpawnerTileEntity) {
                    AbstractSpawner spawner = ((MobSpawnerTileEntity) tileentity).getSpawner();
                    WeightedSpawnerEntity spawnerEntity = new WeightedSpawnerEntity(1, this.spawnData.copy());
                    spawner.setNextSpawnData(spawnerEntity);
                    tileentity.setChanged();
                    world.sendBlockUpdated(pos1, blockstate, blockstate, 3);
                    itemstack.shrink(1);
                    return ActionResultType.CONSUME;
                }
            }
            BlockPos pos2;
            if (blockstate.getCollisionShape(world, pos1).isEmpty()) {
                pos2 = pos1;
            } else {
                pos2 = pos1.relative(direction);
            }
            Entity entity = entityType.spawn((ServerWorld) world, itemstack, context.getPlayer(), pos1, SpawnReason.SPAWN_EGG, true, !Objects.equals(pos1, pos2) && direction == Direction.UP);
            if (entity instanceof TameableDragonEntity) {
                ((TameableDragonEntity) entity).setDragonType(this.type, true);
            }
            itemstack.shrink(1);
            return ActionResultType.CONSUME;
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World level, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockRayTraceResult result = getPlayerPOVHitResult(level, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (result.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.pass(itemstack);
        } else if (level instanceof ServerWorld) {
            BlockPos blockpos = result.getBlockPos();
            if (!(level.getBlockState(blockpos).getBlock() instanceof FlowingFluidBlock)) {
                return ActionResult.pass(itemstack);
            } else if (level.mayInteract(player, blockpos) && player.mayUseItemAt(blockpos, result.getDirection(), itemstack)) {
                EntityType<?> entityType = this.getType(itemstack.getTag());
                Entity entity = entityType.spawn((ServerWorld) level, itemstack, player, blockpos, SpawnReason.SPAWN_EGG, false, false);
                if (entity == null) {
                    return ActionResult.pass(itemstack);
                } else {
                    if (entity instanceof TameableDragonEntity) {
                        ((TameableDragonEntity) entity).setDragonType(this.type, true);
                    }
                    if (!player.abilities.instabuild) {
                        itemstack.shrink(1);
                    }
                    player.awardStat(Stats.ITEM_USED.get(this));
                    return ActionResult.consume(itemstack);
                }
            } else {
                return ActionResult.fail(itemstack);
            }
        }
        return ActionResult.success(itemstack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> components, @Nonnull ITooltipFlag flag) {
        components.add(this.type.getName());
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return TRANSLATION_KEY;
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}
