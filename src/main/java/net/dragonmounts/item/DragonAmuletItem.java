package net.dragonmounts.item;

import net.dragonmounts.api.IDragonTypified;
import net.dragonmounts.entity.dragon.ServerDragonEntity;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.init.DMItems;
import net.dragonmounts.registry.DragonType;
import net.minecraft.block.BlockState;
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

    public DragonAmuletItem(DragonType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Nonnull
    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {
        if (target instanceof TameableDragonEntity) {
            if (player.level.isClientSide) return ActionResultType.SUCCESS;
            TameableDragonEntity dragon = (TameableDragonEntity) target;
            if (dragon.isOwnedBy(player)) {
                DragonAmuletItem amulet = dragon.getDragonType().getInstance(DragonAmuletItem.class, null);
                if (amulet == null) return ActionResultType.FAIL;
                player.level.addFreshEntity(this.spwanEntity(
                        (ServerWorld) player.level,
                        player,
                        stack.getTag(),
                        target.blockPosition(),
                        SpawnReason.EVENT,
                        null,
                        false,
                        false
                ));
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
        World level = context.getLevel();
        if (level.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            PlayerEntity player = context.getPlayer();
            BlockPos clickedPos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState state = level.getBlockState(clickedPos);
            BlockPos spawnPos = state.getCollisionShape(level, clickedPos).isEmpty() ? clickedPos : clickedPos.relative(direction);
            level.addFreshEntity(this.spwanEntity(
                    (ServerWorld) level,
                    player,
                    stack.getTag(),
                    spawnPos,
                    SpawnReason.EVENT,
                    null,
                    true,
                    !Objects.equals(clickedPos, spawnPos) && direction == Direction.UP
            ));
            consume(player, context.getHand(), stack, new ItemStack(DMItems.AMULET));
            return ActionResultType.CONSUME;
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        BlockRayTraceResult result = getPlayerPOVHitResult(level, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        ItemStack stack = player.getItemInHand(hand);
        if (result.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.pass(stack);
        } else if (level.isClientSide) {
            return ActionResult.success(stack);
        } else {
            BlockPos pos = result.getBlockPos();
            if (!(level.getBlockState(pos).getBlock() instanceof FlowingFluidBlock)) {
                return ActionResult.pass(stack);
            } else if (level.mayInteract(player, pos) && player.mayUseItemAt(pos, result.getDirection(), stack)) {
                level.addFreshEntity(this.spwanEntity(
                        (ServerWorld) level,
                        player,
                        stack.getTag(),
                        pos,
                        SpawnReason.EVENT,
                        null,
                        false,
                        false
                ));
                player.awardStat(Stats.ITEM_USED.get(this));
                return ActionResult.success(consume(player, hand, stack, new ItemStack(DMItems.AMULET)));
            }
            return ActionResult.fail(stack);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        CompoundNBT compound = stack.getTag();
        tooltips.add(new TranslationTextComponent("tooltip.dragonmounts.type", this.type.getName()).withStyle(TextFormatting.GRAY));
        if (compound != null) {
            try {
                String string = compound.getString("CustomName");
                if (!string.isEmpty()) {
                    tooltips.add(new TranslationTextComponent("tooltip.dragonmounts.custom_name", ITextComponent.Serializer.fromJson(string)).withStyle(TextFormatting.GRAY));
                }
                tooltips.add(new TranslationTextComponent("tooltip.dragonmounts.health", new StringTextComponent(Float.toString(compound.getFloat("Health"))).withStyle(TextFormatting.GREEN)).withStyle(TextFormatting.GRAY));
                if (compound.hasUUID("Owner")) {
                    string = compound.getString("OwnerName");
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
        CompoundNBT compound = IEntityContainer.simplifyData(entity.saveWithoutId(new CompoundNBT()));
        compound.remove(FLYING_DATA_PARAMETER_KEY);
        compound.remove("UUID");
        LivingEntity owner = entity.getOwner();
        if (owner != null) {
            compound.putString("OwnerName", ITextComponent.Serializer.toJson(owner.getName()));
        }
        stack.setTag(saveScoreboard(entity, compound));
        return stack;
    }

    @Nonnull
    @Override
    public ServerDragonEntity spwanEntity(
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
        if (tag != null) {
            tag.remove("Passengers");
            finalizeSpawn(level, dragon, pos, SpawnReason.EVENT, null, tag, false, false);
            dragon.load(dragon.saveWithoutId(new CompoundNBT()).merge(tag));
            loadScores(dragon, tag).setDragonType(this.type, false);
        } else {
            finalizeSpawn(level, dragon, pos, SpawnReason.EVENT, null, null, false, false);
            dragon.setDragonType(this.type, true);
        }
        return dragon;
    }

    @Override
    public boolean isEmpty(CompoundNBT tag) {
        return false;
    }

    @Override
    public boolean canSetNbt(MinecraftServer server, Entity entity, @Nullable PlayerEntity player) {
        return true;
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return 12000;//10 minutes, Nether Star
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}
