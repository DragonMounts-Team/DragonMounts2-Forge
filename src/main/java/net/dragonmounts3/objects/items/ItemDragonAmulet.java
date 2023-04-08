package net.dragonmounts3.objects.items;

import net.dragonmounts3.inits.ModItems;
import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.objects.IDragonTypified;
import net.dragonmounts3.objects.entity.carriage.EntityCarriage;
import net.dragonmounts3.objects.entity.dragon.EntityTameableDragon;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ItemDragonAmulet extends ItemBaseDragonAmulet implements IDragonTypified {
    private static final Predicate<Entity> ENTITY_PREDICATE = EntityPredicates.NO_SPECTATORS.and(Entity::isPickable);

    protected DragonType type;

    public ItemDragonAmulet(DragonType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Nonnull
    @Override
    public ActionResultType interactLivingEntity(@Nonnull ItemStack stack, @Nonnull PlayerEntity player, @Nonnull LivingEntity entity, @Nonnull Hand hand) {
        return ActionResultType.PASS;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World level, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        BlockRayTraceResult rayTraceResult = getPlayerPOVHitResult(level, player, RayTraceContext.FluidMode.NONE);
        ItemStack itemstack = player.getItemInHand(hand);
        RayTraceResult.Type resultType = rayTraceResult.getType();
        if (resultType == RayTraceResult.Type.MISS) {
            return ActionResult.pass(itemstack);
        } else if (resultType == RayTraceResult.Type.BLOCK) {
            if (!level.isClientSide) {
                CompoundNBT tag = itemstack.getTag();
                BlockPos pos = rayTraceResult.getBlockPos().relative(rayTraceResult.getDirection());
                if (tag != null && tag.contains("TypeID", 8)) {
                    EntityType<?> entityType = EntityType.byString(tag.getString("TypeID")).orElse(EntityType.VILLAGER);
                    if (entityType.spawn((ServerWorld) level, itemstack, player, pos, SpawnReason.COMMAND, false, false) == null) {
                        return ActionResult.pass(itemstack);
                    } else {
                        ItemStack newStack = new ItemStack(ModItems.DRAGON_AMULET.get());
                        player.setItemInHand(hand, newStack);
                        return ActionResult.success(newStack);
                    }
                }
            }
            return ActionResult.consume(itemstack);
        }
        return ActionResult.fail(itemstack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> components, @Nonnull ITooltipFlag flag) {
        CompoundNBT tag = stack.getTag();
        if (tag != null) {
            components.add(new TranslationTextComponent("tooltip.dragonmounts.type", new TranslationTextComponent(tag.getString("Type"))).withStyle(TextFormatting.GRAY));
            components.add(new TranslationTextComponent("tooltip.dragonmounts.owner_name", tag.getString("OwnerName")).withStyle(TextFormatting.GRAY));
        }
    }

    @Override
    public DragonType getDragonType() {
        return type;
    }
}
