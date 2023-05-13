package net.dragonmounts3.item;

import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.IDragonTypified;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.inits.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.EntityPredicates;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

import static net.dragonmounts3.util.EntityUtil.loadScores;

public class FilledDragonAmuletItem extends DragonAmuletItem implements IDragonTypified {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Predicate<Entity> ENTITY_PREDICATE = EntityPredicates.NO_SPECTATORS.and(Entity::isPickable);

    protected DragonType type;

    public FilledDragonAmuletItem(DragonType type, Properties properties) {
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
        ItemStack stack = player.getItemInHand(hand);
        RayTraceResult.Type resultType = rayTraceResult.getType();
        if (resultType == RayTraceResult.Type.MISS) {
            return ActionResult.pass(stack);
        } else if (resultType == RayTraceResult.Type.BLOCK) {
            if (!level.isClientSide) {
                CompoundNBT compound = stack.getTag();
                BlockPos pos = rayTraceResult.getBlockPos().relative(rayTraceResult.getDirection());
                TameableDragonEntity dragon = new TameableDragonEntity(level);
                if (compound != null) {
                    dragon.load(compound);
                    loadScores(dragon, compound).setDragonType(this.type, false);
                } else {
                    dragon.setDragonType(this.type, true);
                }
                dragon.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                level.addFreshEntity(dragon);
                ItemStack newStack = new ItemStack(ModItems.DRAGON_AMULET.get());
                player.setItemInHand(hand, newStack);
                return ActionResult.success(newStack);
            }
            return ActionResult.consume(stack);
        }
        return ActionResult.fail(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> components, @Nonnull ITooltipFlag flag) {
        CompoundNBT compound = stack.getTag();
        components.add(new TranslationTextComponent("tooltip.dragonmounts.type", this.type.getText()).withStyle(TextFormatting.GRAY));
        if (compound != null) {
            try {
                String string = compound.getString("CustomName");
                if (!string.equals("")) {
                    components.add(new TranslationTextComponent("tooltip.dragonmounts.custom_name", ITextComponent.Serializer.fromJson(string)).withStyle(TextFormatting.GRAY));
                }
                components.add(new TranslationTextComponent("tooltip.dragonmounts.health", new StringTextComponent(Float.toString(compound.getFloat("Health"))).withStyle(TextFormatting.GREEN)).withStyle(TextFormatting.GRAY));
                if (compound.hasUUID("Owner")) {
                    string = compound.getString("OwnerName");
                    if (!string.equals("")) {
                        components.add(new TranslationTextComponent("tooltip.dragonmounts.owner_name", ITextComponent.Serializer.fromJson(string)).withStyle(TextFormatting.GRAY));
                    }
                    return;
                }
            } catch (Exception exception) {
                LOGGER.warn(exception);
            }
        }
        components.add(new TranslationTextComponent("tooltip.dragonmounts.missing").withStyle(TextFormatting.RED));
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}
