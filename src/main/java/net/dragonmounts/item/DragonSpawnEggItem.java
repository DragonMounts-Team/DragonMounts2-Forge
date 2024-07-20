package net.dragonmounts.item;

import net.dragonmounts.api.IDragonTypified;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.init.DMEntities;
import net.dragonmounts.registry.DragonType;
import net.dragonmounts.registry.DragonVariant;
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

import static net.dragonmounts.DragonMounts.ITEM_TRANSLATION_KEY_PREFIX;

public final class DragonSpawnEggItem extends ForgeSpawnEggItem implements IDragonTypified {
    private static final String TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_spawn_egg";
    private final DragonType type;

    public DragonSpawnEggItem(DragonType type, int background, int highlight, Properties props) {
        super(DMEntities.TAMEABLE_DRAGON, background, highlight, props);
        this.type = type;
    }

    @Nonnull
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        if (world.isClientSide) return ActionResultType.SUCCESS;
        ItemStack stack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockState state = world.getBlockState(pos);
        EntityType<?> _type = this.getType(stack.getTag());
        if (state.is(Blocks.SPAWNER)) {
            TileEntity entity = world.getBlockEntity(pos);
            if (entity instanceof MobSpawnerTileEntity) {
                AbstractSpawner spawner = ((MobSpawnerTileEntity) entity).getSpawner();
                if (_type == DMEntities.TAMEABLE_DRAGON.get()) {
                        CompoundNBT tag = new CompoundNBT();
                        tag.putString("id", DMEntities.TAMEABLE_DRAGON.getId().toString());
                    tag.putString(DragonVariant.DATA_PARAMETER_KEY, this.type.variants.draw(random, null).getSerializedName().toString());
                        WeightedSpawnerEntity spawnerEntity = new WeightedSpawnerEntity(1, tag);
                        spawner.setNextSpawnData(spawnerEntity);
                } else spawner.setEntityId(_type);
                entity.setChanged();
                world.sendBlockUpdated(pos, state, state, 3);
                stack.shrink(1);
                return ActionResultType.CONSUME;
            }
        }
        BlockPos spawnPos = state.getCollisionShape(world, pos).isEmpty() ? pos : pos.relative(direction);
        Entity entity = _type.spawn((ServerWorld) world, stack, context.getPlayer(), spawnPos, SpawnReason.SPAWN_EGG, true, !Objects.equals(pos, spawnPos) && direction == Direction.UP);
        if (entity instanceof TameableDragonEntity) ((TameableDragonEntity) entity).setDragonType(this.type, true);
        stack.shrink(1);
        return ActionResultType.CONSUME;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockRayTraceResult result = getPlayerPOVHitResult(level, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (result.getType() != RayTraceResult.Type.BLOCK) return ActionResult.pass(stack);
        if (!level.isClientSide) {
            BlockPos pos = result.getBlockPos();
            if (!(level.getBlockState(pos).getBlock() instanceof FlowingFluidBlock)) return ActionResult.pass(stack);
            else if (level.mayInteract(player, pos) && player.mayUseItemAt(pos, result.getDirection(), stack)) {
                Entity entity = this.getType(stack.getTag()).spawn((ServerWorld) level, stack, player, pos, SpawnReason.SPAWN_EGG, false, false);
                if (entity == null) return ActionResult.pass(stack);
                if (entity instanceof TameableDragonEntity)
                    ((TameableDragonEntity) entity).setDragonType(this.type, true);
                if (!player.abilities.instabuild) stack.shrink(1);
                player.awardStat(Stats.ITEM_USED.get(this));
                return ActionResult.consume(stack);
            } else return ActionResult.fail(stack);
        }
        return ActionResult.success(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        tooltips.add(this.type.getName());
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
