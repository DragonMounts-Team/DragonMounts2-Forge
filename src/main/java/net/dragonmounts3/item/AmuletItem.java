package net.dragonmounts3.item;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

import static net.dragonmounts3.DragonMounts.ITEM_TRANSLATION_KEY_PREFIX;
import static net.dragonmounts3.util.EntityUtil.consume;
import static net.dragonmounts3.util.EntityUtil.finalizeSpawn;


public class AmuletItem<T extends Entity> extends Item implements IEntityContainer<T> {
    private static final String TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_amulet";

    public AmuletItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Nullable
    @Override
    public Entity spwanEntity(
            ServerWorld level,
            @Nullable PlayerEntity player,
            CompoundNBT tag,
            BlockPos pos,
            SpawnReason reason,
            @Nullable ILivingEntityData data,
            boolean yOffset,
            boolean extraOffset
    ) {
        CompoundNBT compound = tag.getCompound("EntityTag");
        if (compound.contains("id", 8)) {
            EntityType<? extends Entity> type = EntityType.byString(compound.getString("id")).orElse(null);
            if (type == null) return null;
            Entity entity = type.create(level);
            if (entity != null) {
                finalizeSpawn(level, entity, pos, reason, data, tag, yOffset, extraOffset);
                if (this.canSetNbt(level.getServer(), entity, player)) {
                    UUID uuid = entity.getUUID();
                    entity.load(entity.saveWithoutId(new CompoundNBT()).merge(compound));
                    entity.setUUID(uuid);
                }
                return entity;
            }
        }
        return null;
    }

    @Override
    public ItemStack saveEntity(T entity) {
        ItemStack stack = new ItemStack(this);
        CompoundNBT tag = new CompoundNBT();
        entity.saveAsPassenger(tag);
        stack.setTag(IEntityContainer.simplifyData(tag));
        return stack;
    }

    @Override
    public boolean isEmpty(CompoundNBT tag) {
        return !tag.contains("EntityTag", 10);
    }

    @Nonnull
    @Override
    public ActionResultType interactLivingEntity(@Nonnull ItemStack stack, @Nonnull PlayerEntity player, @Nonnull LivingEntity target, @Nonnull Hand hand) {
        if (target instanceof TameableDragonEntity) {
            if (player.level.isClientSide) return ActionResultType.SUCCESS;
            TameableDragonEntity dragon = (TameableDragonEntity) target;
            if (dragon.isOwnedBy(player)) {
                DragonAmuletItem amulet = dragon.getDragonType().getInstance(DragonAmuletItem.class, null);
                if (amulet == null) return ActionResultType.FAIL;
                CompoundNBT tag = stack.getTag();
                if (tag != null && !this.isEmpty(tag)) {
                    Entity entity = this.spwanEntity(
                            (ServerWorld) player.level,
                            player,
                            tag,
                            target.blockPosition(),
                            SpawnReason.EVENT,
                            null,
                            false,
                            false
                    );
                    if (entity != null) {
                        player.level.addFreshEntity(entity);
                    }
                }
                consume(player, hand, stack, amulet.saveEntity(dragon));
                dragon.remove(false);
                return ActionResultType.CONSUME;
            } else {
                player.displayClientMessage(new TranslationTextComponent("message.dragonmounts.not_owner"), true);
                return ActionResultType.FAIL;
            }
        }
        return ActionResultType.PASS;
    }

    @Nonnull
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        ItemStack stack = context.getItemInHand();
        CompoundNBT tag = stack.getTag();
        if (tag == null || this.isEmpty(tag)) return ActionResultType.PASS;
        World level = context.getLevel();
        if (level.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            ServerWorld world = (ServerWorld) level;
            PlayerEntity player = context.getPlayer();
            BlockPos clickedPos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = world.getBlockState(clickedPos);
            BlockPos spawnPos = blockstate.getCollisionShape(world, clickedPos).isEmpty() ? clickedPos : clickedPos.relative(direction);
            Entity entity = this.spwanEntity(world,
                    player,
                    tag,
                    spawnPos,
                    SpawnReason.EVENT,
                    null,
                    true,
                    !Objects.equals(clickedPos, spawnPos) && direction == Direction.UP
            );
            if (entity != null) {
                world.addFreshEntity(entity);
                consume(player, context.getHand(), stack, new ItemStack(this));
            }
            return ActionResultType.CONSUME;
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World level, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundNBT tag = stack.getTag();
        if (tag == null || this.isEmpty(tag)) return ActionResult.pass(stack);
        BlockRayTraceResult result = getPlayerPOVHitResult(level, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (result.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.pass(stack);
        } else if (level.isClientSide) {
            return ActionResult.success(stack);
        } else {
            BlockPos pos = result.getBlockPos();
            if (!(level.getBlockState(pos).getBlock() instanceof FlowingFluidBlock)) {
                return ActionResult.pass(stack);
            } else if (level.mayInteract(player, pos) && player.mayUseItemAt(pos, result.getDirection(), stack)) {
                ServerWorld world = (ServerWorld) level;
                Entity entity = this.spwanEntity(world, player, tag, pos, SpawnReason.EVENT, null, false, false);
                if (entity == null) return ActionResult.pass(stack);
                world.addFreshEntity(entity);
                player.awardStat(Stats.ITEM_USED.get(this));
                return ActionResult.success(consume(player, hand, stack, new ItemStack(this)));
            }
            return ActionResult.fail(stack);
        }
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return TRANSLATION_KEY;
    }
}
