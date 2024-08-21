package net.dragonmounts.item;

import net.dragonmounts.api.IDragonTypified;
import net.dragonmounts.entity.dragon.DragonLifeStage;
import net.dragonmounts.entity.dragon.ServerDragonEntity;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.inventory.DragonInventory;
import net.dragonmounts.registry.DragonType;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import static net.dragonmounts.DragonMounts.ITEM_TRANSLATION_KEY_PREFIX;
import static net.dragonmounts.entity.dragon.TameableDragonEntity.FLYING_DATA_PARAMETER_KEY;
import static net.dragonmounts.util.EntityUtil.finalizeSpawn;

public class DragonEssenceItem extends Item implements IDragonTypified, IEntityContainer<TameableDragonEntity> {
    private static final String TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_essence";

    public final DragonType type;

    public DragonEssenceItem(DragonType type, Properties props) {
        super(props.stacksTo(1));
        this.type = type;
    }

    @Nonnull
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World level = context.getLevel();
        if (level.isClientSide) return ActionResultType.SUCCESS;
        ItemStack stack = context.getItemInHand();
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos spawnPos = level.getBlockState(pos).getCollisionShape(level, pos).isEmpty() ? pos : pos.relative(direction);
        level.addFreshEntity(this.loadEntity(
                (ServerWorld) level,
                player,
                stack.getTag(),
                spawnPos,
                SpawnReason.SPAWN_EGG,
                null,
                true,
                !Objects.equals(pos, spawnPos) && direction == Direction.UP
        ));
        if (player != null) {
            if (!player.abilities.instabuild) {
                stack.shrink(1);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
        }
        return ActionResultType.CONSUME;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        BlockRayTraceResult result = getPlayerPOVHitResult(level, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        ItemStack stack = player.getItemInHand(hand);
        if (result.getType() != RayTraceResult.Type.BLOCK) return ActionResult.pass(stack);
        if (level.isClientSide) return ActionResult.success(stack);
        BlockPos pos = result.getBlockPos();
        if (!(level.getBlockState(pos).getBlock() instanceof FlowingFluidBlock)) return ActionResult.pass(stack);
        if (level.mayInteract(player, pos) && player.mayUseItemAt(pos, result.getDirection(), stack)) {
            level.addFreshEntity(this.loadEntity(
                    (ServerWorld) level,
                    player,
                    stack.getTag(),
                    pos,
                    SpawnReason.SPAWN_EGG,
                    null,
                    false,
                    false
            ));
            if (!player.abilities.instabuild) {
                stack.shrink(1);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            return ActionResult.success(stack);
        }
        return ActionResult.fail(stack);
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

    @Override
    public ItemStack saveEntity(TameableDragonEntity entity) {
        ItemStack stack = new ItemStack(this);
        CompoundNBT tag = IEntityContainer.simplifyData(entity.saveWithoutId(new CompoundNBT()));
        tag.remove(FLYING_DATA_PARAMETER_KEY);
        tag.remove(DragonInventory.DATA_PARAMETER_KEY);
        tag.remove("UUID");
        tag.remove("AbsorptionAmount");
        tag.remove("Age");
        tag.remove("AgeLocked");
        tag.remove("ArmorDropChances");
        tag.remove("ArmorItems");
        tag.remove("Attributes");
        tag.remove("Brain");
        tag.remove("ForcedAge");
        tag.remove("HandDropChances");
        tag.remove("HandItems");
        tag.remove("Health");
        tag.remove("LifeStage");
        tag.remove("LoveCause");
        tag.remove("ShearCooldown");
        LivingEntity owner = entity.getOwner();
        if (owner != null) tag.putString("OwnerName", ITextComponent.Serializer.toJson(owner.getName()));
        stack.setTag(tag);
        return stack;
    }

    @Override
    public final Class<TameableDragonEntity> getContentType() {
        return TameableDragonEntity.class;
    }

    @Nonnull
    @Override
    public ServerDragonEntity loadEntity(
            ServerWorld level,
            @Nullable PlayerEntity player,
            @Nullable CompoundNBT tag,
            BlockPos pos,
            SpawnReason reason,
            @Nullable ILivingEntityData data,
            boolean yOffset,
            boolean extraOffset
    ) {
        ServerDragonEntity dragon = new ServerDragonEntity(level);
        if (tag == null) {
            finalizeSpawn(level, dragon, pos, reason, null, null, yOffset, extraOffset);
            dragon.setDragonType(this.type, true);
        } else {
            tag.remove("Passengers");
            finalizeSpawn(level, dragon, pos, reason, null, tag, yOffset, extraOffset);
            dragon.load(dragon.saveWithoutId(new CompoundNBT()).merge(tag));
            dragon.setDragonType(this.type, false);
        }
        dragon.setLifeStage(DragonLifeStage.NEWBORN, true, false);
        return dragon;
    }

    @Override
    public boolean isEmpty(@Nullable CompoundNBT tag) {
        return false;
    }

    @Override
    public boolean canSetNbt(MinecraftServer server, Entity entity, @Nullable PlayerEntity player) {
        return true;
    }
}
