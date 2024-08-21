package net.dragonmounts.item;

import net.dragonmounts.api.IDragonTypified;
import net.dragonmounts.entity.dragon.ServerDragonEntity;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.init.DMItems;
import net.dragonmounts.registry.DragonType;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import static net.dragonmounts.entity.dragon.TameableDragonEntity.FLYING_DATA_PARAMETER_KEY;
import static net.dragonmounts.util.EntityUtil.*;

public class DragonAmuletItem extends AmuletItem<TameableDragonEntity> implements IDragonTypified {
    private static final Logger LOGGER = LogManager.getLogger();
    public final DragonType type;

    public DragonAmuletItem(DragonType type, Properties props) {
        super(TameableDragonEntity.class, props);
        this.type = type;
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
                level.addFreshEntity(this.loadEntity(
                        (ServerWorld) level,
                        player,
                        stack.getTag(),
                        target.blockPosition(),
                        SpawnReason.BUCKET,
                        null,
                        false,
                        false
                ));
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
                SpawnReason.BUCKET,
                null,
                true,
                !Objects.equals(pos, spawnPos) && direction == Direction.UP
        ));
        if (player != null) {
            consume(player, context.getHand(), stack, new ItemStack(DMItems.AMULET));
            player.awardStat(Stats.ITEM_USED.get(this));
        }
        return ActionResultType.CONSUME;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockRayTraceResult result = getPlayerPOVHitResult(level, player, RayTraceContext.FluidMode.SOURCE_ONLY);
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
                    SpawnReason.BUCKET,
                    null,
                    false,
                    false
            ));
            player.awardStat(Stats.ITEM_USED.get(this));
            return ActionResult.success(consume(player, hand, stack, new ItemStack(DMItems.AMULET)));
        }
        return ActionResult.fail(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        CompoundNBT tag = stack.getTag();
        tooltips.add(new TranslationTextComponent("tooltip.dragonmounts.type", this.type.getName()).withStyle(TextFormatting.GRAY));
        if (tag != null) {
            try {
                String string = tag.getString("CustomName");
                if (!string.isEmpty()) {
                    tooltips.add(new TranslationTextComponent("tooltip.dragonmounts.custom_name", ITextComponent.Serializer.fromJson(string)).withStyle(TextFormatting.GRAY));
                }
                tooltips.add(new TranslationTextComponent("tooltip.dragonmounts.health", new StringTextComponent(Float.toString(tag.getFloat("Health"))).withStyle(TextFormatting.GREEN)).withStyle(TextFormatting.GRAY));
                if (tag.hasUUID("Owner")) {
                    string = tag.getString("OwnerName");
                    if (!string.isEmpty()) {
                        tooltips.add(new TranslationTextComponent("tooltip.dragonmounts.owner_name", ITextComponent.Serializer.fromJson(string)).withStyle(TextFormatting.GRAY));
                    }
                    return;
                }
            } catch (Exception exception) {
                LOGGER.warn(exception);
            }
        }
        tooltips.add(new TranslationTextComponent("tooltip.dragonmounts.missing").withStyle(TextFormatting.RED));
    }

    @Override
    public ItemStack saveEntity(TameableDragonEntity entity) {
        ItemStack stack = new ItemStack(this);
        entity.ejectPassengers();
        CompoundNBT tag = IEntityContainer.simplifyData(entity.saveWithoutId(new CompoundNBT()));
        tag.remove(FLYING_DATA_PARAMETER_KEY);
        tag.remove("UUID");
        LivingEntity owner = entity.getOwner();
        if (owner != null) {
            tag.putString("OwnerName", ITextComponent.Serializer.toJson(owner.getName()));
        }
        stack.setTag(saveScoreboard(entity, tag));
        return stack;
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
            loadScores(dragon, tag).setDragonType(this.type, false);
        }
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

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}
