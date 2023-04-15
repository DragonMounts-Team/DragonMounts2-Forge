package net.dragonmounts3.item;

import net.dragonmounts3.inits.ModItems;
import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

import java.util.Objects;

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
                DragonType type = dragon.getDragonType();
                Item amulet = ModItems.FILLED_DRAGON_AMULET.get(type);
                if (amulet != null) {
                    CompoundNBT tag = new CompoundNBT();
                    tag.putString("Type", entity.getType().getDescriptionId());
                    tag.putString("TypeID", Objects.requireNonNull(ForgeRegistries.ENTITIES.getKey(entity.getType())).toString());
                    tag.putString("OwnerName", player.getDisplayName().getString());
                    ItemStack newStack = new ItemStack(amulet);
                    newStack.setTag(tag);
                    player.setItemInHand(hand, newStack);
                    dragon.remove(false);
                } else {
                    return ActionResultType.FAIL;
                }
            }
            return ActionResultType.sidedSuccess(isClientSide);
        } else {//test
            Item amulet = ModItems.ENDER_DRAGON_AMULET.get();
            CompoundNBT tag = new CompoundNBT();
            tag.putString("Type", entity.getType().getDescriptionId());
            tag.putString("TypeID", Objects.requireNonNull(ForgeRegistries.ENTITIES.getKey(entity.getType())).toString());
            tag.putString("OwnerName", player.getDisplayName().getString());
            ItemStack newStack = new ItemStack(amulet);
            newStack.setTag(tag);
            player.setItemInHand(hand, newStack);
            entity.remove(false);
            return ActionResultType.sidedSuccess(isClientSide);
        }
        //return ActionResultType.PASS;
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return TRANSLATION_KEY;
    }
}
