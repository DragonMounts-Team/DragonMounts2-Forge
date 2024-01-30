package net.dragonmounts.item;

import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.registry.DragonType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
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

public class DragonWhistleItem extends Item {
    private static final Logger LOGGER = LogManager.getLogger();

    public DragonWhistleItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Nonnull
    @Override
    public ActionResultType interactLivingEntity(@Nonnull ItemStack stack, @Nonnull PlayerEntity player, @Nonnull LivingEntity entity, @Nonnull Hand hand) {
        if (entity instanceof TameableDragonEntity) {
            if (player.level.isClientSide) {
                return ActionResultType.SUCCESS;
            }
            TameableDragonEntity dragon = (TameableDragonEntity) entity;
            if (dragon.isOwnedBy(player)) {
                CompoundNBT compound = new CompoundNBT();
                compound.putUUID("UUID", dragon.getUUID());
                DragonType type = dragon.getDragonType();
                compound.putString("Type", type.getSerializedName().toString());
                compound.putString("OwnerName", ITextComponent.Serializer.toJson(player.getName()));
                stack.setTag(compound);
                player.setItemSlot(hand == Hand.MAIN_HAND ? EquipmentSlotType.MAINHAND : EquipmentSlotType.OFFHAND, stack);
                return ActionResultType.CONSUME;
            } else {
                player.displayClientMessage(new TranslationTextComponent("message.dragonmounts.not_owner"), true);
                return ActionResultType.FAIL;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> components, @Nonnull ITooltipFlag flag) {
        CompoundNBT compound = stack.getTag();
        if (compound != null && compound.contains("UUID")) {
            if (compound.contains("Type")) {
                DragonType.byName(compound.getString("Type")).getFormattedName("tooltip.dragonmounts.type").withStyle(TextFormatting.GRAY);
            }
            try {
                String string = compound.getString("OwnerName");
                if (!string.isEmpty()) {
                    components.add(new TranslationTextComponent("tooltip.dragonmounts.owner_name", ITextComponent.Serializer.fromJson(string)).withStyle(TextFormatting.GRAY));
                }
            } catch (Exception exception) {
                LOGGER.warn(exception);
            }
        }
    }
}
