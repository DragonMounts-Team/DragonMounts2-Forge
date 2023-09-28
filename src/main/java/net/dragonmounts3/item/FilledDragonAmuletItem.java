package net.dragonmounts3.item;

import net.dragonmounts3.api.IDragonTypified;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.init.DMItems;
import net.dragonmounts3.registry.DragonType;
import net.dragonmounts3.util.EntityUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
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

import static net.dragonmounts3.util.EntityUtil.loadScores;

public class FilledDragonAmuletItem extends DragonAmuletItem implements IDragonTypified {
    private static final Logger LOGGER = LogManager.getLogger();

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
                BlockPos pos = rayTraceResult.getBlockPos().relative(rayTraceResult.getDirection());
                TameableDragonEntity dragon = this.release(stack.getTag(), level, this.type);
                EntityUtil.setPos(dragon, pos);
                level.addFreshEntity(dragon);
                ItemStack newStack = new ItemStack(DMItems.DRAGON_AMULET.get());
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
        components.add(new TranslationTextComponent("tooltip.dragonmounts.type", this.type.getName()).withStyle(TextFormatting.GRAY));
        if (compound != null) {
            try {
                String string = compound.getString("CustomName");
                if (!string.isEmpty()) {
                    components.add(new TranslationTextComponent("tooltip.dragonmounts.custom_name", ITextComponent.Serializer.fromJson(string)).withStyle(TextFormatting.GRAY));
                }
                components.add(new TranslationTextComponent("tooltip.dragonmounts.health", new StringTextComponent(Float.toString(compound.getFloat("Health"))).withStyle(TextFormatting.GREEN)).withStyle(TextFormatting.GRAY));
                if (compound.hasUUID("Owner")) {
                    string = compound.getString("OwnerName");
                    if (!string.isEmpty()) {
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
    public TameableDragonEntity release(CompoundNBT compound, World level, @Nullable DragonType type) {
        TameableDragonEntity dragon = new TameableDragonEntity(level);
        if (type == null) {
            type = this.type;
        }
        if (compound != null) {
            dragon.load(compound);
            loadScores(dragon, compound).setDragonType(type, false);
        } else {
            dragon.setDragonType(type, true);
        }
        return dragon;
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
