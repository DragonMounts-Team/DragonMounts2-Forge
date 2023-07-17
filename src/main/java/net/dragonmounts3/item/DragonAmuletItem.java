package net.dragonmounts3.item;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.init.DMItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

import static net.dragonmounts3.DragonMounts.ITEM_TRANSLATION_KEY_PREFIX;

public class DragonAmuletItem extends Item implements IDragonContainer {
    private static final String TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_amulet";

    public DragonAmuletItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
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
                Item amulet = DMItems.FILLED_DRAGON_AMULET.get(dragon.getDragonType());
                if (amulet == null) return ActionResultType.FAIL;
                ItemStack newStack = IDragonContainer.fill(dragon, amulet, false);
                player.setItemInHand(hand, newStack);
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
    public String getDescriptionId() {
        return TRANSLATION_KEY;
    }
}
