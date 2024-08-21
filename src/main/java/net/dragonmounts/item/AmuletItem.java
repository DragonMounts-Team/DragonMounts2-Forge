package net.dragonmounts.item;

import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

import static net.dragonmounts.DragonMounts.ITEM_TRANSLATION_KEY_PREFIX;
import static net.dragonmounts.entity.dragon.TameableDragonEntity.FLYING_DATA_PARAMETER_KEY;
import static net.dragonmounts.util.EntityUtil.consume;
import static net.dragonmounts.util.EntityUtil.finalizeSpawn;


public class AmuletItem<T extends Entity> extends Item implements IEntityContainer<T> {
    private static final String TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_amulet";
    public final Class<T> contentType;

    public AmuletItem(Class<T> contentType, Properties props) {
        super(props.stacksTo(1));
        this.contentType = contentType;
    }

    @Nullable
    @Override
    public Entity loadEntity(
            ServerWorld level,
            @Nullable PlayerEntity player,
            CompoundNBT tag,
            BlockPos pos,
            SpawnReason reason,
            @Nullable ILivingEntityData data,
            boolean yOffset,
            boolean extraOffset
    ) {
        CompoundNBT info = tag.getCompound("EntityTag");
        if (info.contains("id", 8)) {
            EntityType<? extends Entity> type = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryParse(info.getString("id")));
            if (type == null) return null;
            Entity entity = type.create(level);
            if (entity != null) {
                finalizeSpawn(level, entity, pos, reason, data, tag, yOffset, extraOffset);
                if (this.canSetNbt(level.getServer(), entity, player)) {
                    UUID uuid = entity.getUUID();
                    entity.load(entity.saveWithoutId(new CompoundNBT()).merge(info));
                    entity.setUUID(uuid);
                }
                return entity;
            }
        }
        return null;
    }

    @Override
    public ItemStack saveEntity(T entity) {
        EntityType<?> type = entity.getType();
        if (type.canSerialize()) {
            CompoundNBT root = new CompoundNBT();
            CompoundNBT tag = entity.saveWithoutId(new CompoundNBT());
            tag.putString("id", EntityType.getKey(type).toString());
            tag.remove(FLYING_DATA_PARAMETER_KEY);
            tag.remove("UUID");
            root.put("EntityTag", IEntityContainer.simplifyData(tag));
            ItemStack stack = new ItemStack(this);
            stack.setTag(root);
            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public final Class<T> getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty(@Nullable CompoundNBT tag) {
        return tag == null || !tag.contains("EntityTag", 10);
    }

    @Nonnull
    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {
        if (target instanceof TameableDragonEntity) {
            World level = target.level;
            if (level.isClientSide) return ActionResultType.SUCCESS;
            TameableDragonEntity dragon = (TameableDragonEntity) target;
            if (dragon.isOwnedBy(player)) {
                DragonAmuletItem amulet = dragon.getDragonType().getInstance(DragonAmuletItem.class, null);
                if (amulet == null) return ActionResultType.FAIL;
                CompoundNBT tag = stack.getTag();
                if (tag != null && !this.isEmpty(tag)) {
                    Entity entity = this.loadEntity(
                            (ServerWorld) level,
                            player,
                            tag,
                            target.blockPosition(),
                            SpawnReason.BUCKET,
                            null,
                            false,
                            false
                    );
                    if (entity != null) level.addFreshEntity(entity);
                }
                dragon.inventory.dropContents(true, 0);
                consume(player, hand, stack, amulet.saveEntity(dragon));
                player.awardStat(Stats.ITEM_USED.get(this));
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
        if (level.isClientSide) return ActionResultType.SUCCESS;
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos spawnPos = level.getBlockState(pos).getCollisionShape(level, pos).isEmpty() ? pos : pos.relative(direction);
        Entity entity = this.loadEntity(
                (ServerWorld) level,
                player,
                tag,
                spawnPos,
                SpawnReason.BUCKET,
                null,
                true,
                !Objects.equals(pos, spawnPos) && direction == Direction.UP
        );
        if (entity != null) {
            level.addFreshEntity(entity);
            if (player != null) {
                consume(player, context.getHand(), stack, new ItemStack(this));
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
        return ActionResultType.CONSUME;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundNBT tag = stack.getTag();
        if (tag == null || this.isEmpty(tag)) return ActionResult.pass(stack);
        BlockRayTraceResult result = getPlayerPOVHitResult(level, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (result.getType() != RayTraceResult.Type.BLOCK) return ActionResult.pass(stack);
        if (level.isClientSide) return ActionResult.success(stack);
        BlockPos pos = result.getBlockPos();
        if (!(level.getBlockState(pos).getBlock() instanceof FlowingFluidBlock)) return ActionResult.pass(stack);
        if (level.mayInteract(player, pos) && player.mayUseItemAt(pos, result.getDirection(), stack)) {
            Entity entity = this.loadEntity((ServerWorld) level, player, tag, pos, SpawnReason.BUCKET, null, false, false);
            if (entity == null) return ActionResult.pass(stack);
            level.addFreshEntity(entity);
            player.awardStat(Stats.ITEM_USED.get(this));
            return ActionResult.success(consume(player, hand, stack, new ItemStack(this)));
        }
        return ActionResult.fail(stack);
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return TRANSLATION_KEY;
    }
}
