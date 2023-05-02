package net.dragonmounts3.item;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.inits.ModItems;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

import static net.dragonmounts3.DragonMounts.getItemTranslationKey;

public class DragonAmuletItem extends Item {
    private static final String TRANSLATION_KEY = getItemTranslationKey("dragon_amulet");

    public DragonAmuletItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    @Nonnull
    @Override
    public ActionResultType interactLivingEntity(@Nonnull ItemStack stack, @Nonnull PlayerEntity player, @Nonnull LivingEntity entity, @Nonnull Hand hand) {
        boolean isClientSide = player.level.isClientSide;
        if (entity instanceof TameableDragonEntity) {
            if (!isClientSide) {
                TameableDragonEntity dragon = (TameableDragonEntity) entity;
                if (dragon.isOwnedBy(player)) {
                    DragonType type = dragon.getDragonType();
                    Item amulet = ModItems.FILLED_DRAGON_AMULET.get(type);
                    if (amulet != null) {
                        CompoundNBT compound = dragon.getData();
                        compound.putString("OwnerName", ITextComponent.Serializer.toJson(player.getName()));
                        ItemStack newStack = new ItemStack(amulet);
                        newStack.setTag(compound);
                        player.setItemInHand(hand, newStack);
                        dragon.remove(false);
                    } else {
                        return ActionResultType.FAIL;
                    }
                    return ActionResultType.CONSUME;
                } else {
                    player.displayClientMessage(new TranslationTextComponent("message.dragonmounts.not_owner"), true);
                    return ActionResultType.FAIL;
                }
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return TRANSLATION_KEY;
    }
}
