package net.dragonmounts3.item;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.registry.DragonVariant;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class VariantSwitcherItem extends Item {
    public VariantSwitcherItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ActionResultType interactLivingEntity(@Nonnull ItemStack stack, @Nonnull PlayerEntity player, @Nonnull LivingEntity entity, @Nonnull Hand hand) {
        if (entity instanceof TameableDragonEntity) {
            if (player.level.isClientSide) return ActionResultType.SUCCESS;
            TameableDragonEntity dragon = (TameableDragonEntity) entity;
            if (dragon.isOwnedBy(player)) {
                DragonVariant previous = dragon.getVariant();
                dragon.setVariant(previous.type.variants.draw(random, previous));
                if (!player.abilities.instabuild) {
                    stack.shrink(1);
                }
                return ActionResultType.CONSUME;
            } else {
                player.displayClientMessage(new TranslationTextComponent("message.dragonmounts.not_owner"), true);
                return ActionResultType.FAIL;
            }
        }
        return ActionResultType.PASS;
    }
}
