package net.dragonmounts3.item;

import net.dragonmounts3.entity.dragon.HatchableDragonEggEntity;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class DragonWhistleItem extends Item {
    private static final String DRAGON_UUID_KEY = "DragonUUID";


    public DragonWhistleItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Nonnull
    @Override
    public ActionResultType interactLivingEntity(@Nonnull ItemStack stack, @Nonnull PlayerEntity player, LivingEntity entity, @Nonnull Hand hand) {
        if (entity.level.isClientSide) return ActionResultType.PASS;
        if (entity instanceof HatchableDragonEggEntity) {//test
            CompoundNBT tag = new CompoundNBT();
            tag.putUUID(DRAGON_UUID_KEY, entity.getUUID());
            tag.putString("Type", entity.getType().getDescriptionId());
            tag.putString("OwnerName", player.getDisplayName().getString());
            tag.putInt("Color", ((HatchableDragonEggEntity) entity).getDragonType().getColor());
            stack.setTag(tag);
            player.setItemSlot(hand == Hand.MAIN_HAND ? EquipmentSlotType.MAINHAND : EquipmentSlotType.OFFHAND, stack);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Nullable
    private CompoundNBT getDragonData(ItemStack stack) {
        if (stack.hasTag()) {
            CompoundNBT tag = stack.getTag();
            if (tag != null && tag.hasUUID(DRAGON_UUID_KEY)) {
                return tag;
            }
        }
        return null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> components, @Nonnull ITooltipFlag flag) {
        CompoundNBT tag = this.getDragonData(stack);
        if (tag != null) {
            components.add(new TranslationTextComponent("tooltip.dragonmounts.type", new TranslationTextComponent(tag.getString("Type"))).withStyle(TextFormatting.GRAY));
            components.add(new TranslationTextComponent("tooltip.dragonmounts.owner_name", tag.getString("OwnerName")).withStyle(TextFormatting.GRAY));
        }
    }
}
