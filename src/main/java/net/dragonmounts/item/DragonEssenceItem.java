package net.dragonmounts.item;

import net.dragonmounts.api.IDragonTypified;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.inventory.DragonInventory;
import net.dragonmounts.registry.DragonType;
import net.minecraft.block.BlockState;
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

    protected DragonType type;

    public DragonEssenceItem(DragonType type, Properties properties) {
        super(properties.stacksTo(1));
        this.type = type;
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
            BlockState blockstate = level.getBlockState(clickedPos);
            BlockPos spawnPos = blockstate.getCollisionShape(level, clickedPos).isEmpty() ? clickedPos : clickedPos.relative(direction);
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
            if (player == null || !player.abilities.instabuild) {
                stack.shrink(1);
            }
            return ActionResultType.CONSUME;
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World level, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
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
                if (!player.abilities.instabuild) {
                    stack.shrink(1);
                }
                player.awardStat(Stats.ITEM_USED.get(this));
                return ActionResult.success(stack);
            }
            return ActionResult.fail(stack);
        }
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

    @Override
    public ItemStack saveEntity(TameableDragonEntity entity) {
        ItemStack stack = new ItemStack(this);
        CompoundNBT compound = IEntityContainer.simplifyData(entity.saveWithoutId(new CompoundNBT()));
        compound.remove(FLYING_DATA_PARAMETER_KEY);
        compound.remove(DragonInventory.DATA_PARAMETER_KEY);
        compound.remove("UUID");
        compound.remove("AbsorptionAmount");
        compound.remove("Age");
        compound.remove("AgeLocked");
        compound.remove("ArmorDropChances");
        compound.remove("ArmorItems");
        compound.remove("Attributes");
        compound.remove("Brain");
        compound.remove("ForcedAge");
        compound.remove("HandDropChances");
        compound.remove("HandItems");
        compound.remove("Health");
        compound.remove("LifeStage");
        compound.remove("LoveCause");
        compound.remove("ShearCooldown");
        compound.remove("Sitting");
        LivingEntity owner = entity.getOwner();
        if (owner != null) {
            compound.putString("OwnerName", ITextComponent.Serializer.toJson(owner.getName()));
        }
        stack.setTag(compound);
        return stack;
    }

    @Nonnull
    @Override
    public TameableDragonEntity spwanEntity(
            ServerWorld level,
            @Nullable PlayerEntity player,
            @Nullable CompoundNBT tag,
            BlockPos pos,
            SpawnReason reason,
            @Nullable ILivingEntityData data,
            boolean yOffset,
            boolean extraOffset
    ) {
        TameableDragonEntity dragon = new TameableDragonEntity(level);
        if (tag != null) {
            tag.remove("Passengers");
            finalizeSpawn(level, dragon, pos, SpawnReason.EVENT, null, tag, false, false);
            dragon.load(dragon.saveWithoutId(new CompoundNBT()).merge(tag));
            dragon.setDragonType(this.type, false);
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
}
