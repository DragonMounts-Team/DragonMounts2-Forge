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
import net.minecraft.util.ResourceLocation;
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

    public static int getColor(ItemStack stack, int layer) {
        if (layer == 0) return 0xFFFFFF;
        CompoundNBT tag = stack.getTag();
        if (tag != null && tag.contains("Type")) {
            DragonType type = DragonType.REGISTRY.getRaw(new ResourceLocation(tag.getString("Type")));
            return type == null ? 0xFFFFFF : type.color;
        }
        return 0xFFFFFF;
    }

    public DragonWhistleItem(Properties props) {
        super(props.stacksTo(1));
    }

    @Nonnull
    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        if (entity instanceof TameableDragonEntity) {
            if (player.level.isClientSide) return ActionResultType.SUCCESS;
            TameableDragonEntity dragon = (TameableDragonEntity) entity;
            if (dragon.isOwnedBy(player)) {
                CompoundNBT tag = new CompoundNBT();
                tag.putUUID("UUID", dragon.getUUID());
                DragonType type = dragon.getDragonType();
                tag.putString("Type", type.getSerializedName().toString());
                tag.putString("OwnerName", ITextComponent.Serializer.toJson(player.getName()));
                stack.setTag(tag);
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
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        CompoundNBT tag = stack.getTag();
        if (tag != null && tag.contains("UUID")) {
            if (tag.contains("Type"))
                DragonType.byName(tag.getString("Type")).getFormattedName("tooltip.dragonmounts.type").withStyle(TextFormatting.GRAY);
            try {
                String string = tag.getString("OwnerName");
                if (!string.isEmpty())
                    tooltips.add(new TranslationTextComponent("tooltip.dragonmounts.owner_name", ITextComponent.Serializer.fromJson(string)).withStyle(TextFormatting.GRAY));
            } catch (Exception exception) {
                LOGGER.warn(exception);
            }
        }
    }
}
