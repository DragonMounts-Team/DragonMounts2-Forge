package net.dragonmounts3.item;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.inits.ModItems;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.Map;

import static net.dragonmounts3.DragonMounts.getItemTranslationKey;

public class DragonAmuletItem extends Item {
    private static final String TRANSLATION_KEY = getItemTranslationKey("dragon_amulet");

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
                DragonType type = dragon.getDragonType();
                Item amulet = ModItems.FILLED_DRAGON_AMULET.get(type);
                if (amulet == null) {
                    return ActionResultType.FAIL;
                }
                CompoundNBT compound = dragon.getData();
                Map<ScoreObjective, Score> scores = player.level.getScoreboard().getPlayerScores(dragon.getScoreboardName());
                if (!scores.isEmpty()) {
                    CompoundNBT scoresTag = new CompoundNBT();
                    CompoundNBT lockedScoresTag = new CompoundNBT();
                    Score cache;
                    for (Map.Entry<ScoreObjective, Score> entry : scores.entrySet()) {
                        cache = entry.getValue();
                        if (cache.isLocked()) {
                            lockedScoresTag.putInt(entry.getKey().getName(), cache.getScore());
                        } else {
                            scoresTag.putInt(entry.getKey().getName(), cache.getScore());
                        }
                    }
                    if (!scoresTag.isEmpty()) {
                        compound.put("Scores", scoresTag);
                    }
                    if (!lockedScoresTag.isEmpty()) {
                        compound.put("LockedScores", lockedScoresTag);
                    }
                }
                compound.putString("OwnerName", ITextComponent.Serializer.toJson(player.getName()));
                compound.remove("UUID");
                ItemStack newStack = new ItemStack(amulet);
                newStack.setTag(compound);
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
