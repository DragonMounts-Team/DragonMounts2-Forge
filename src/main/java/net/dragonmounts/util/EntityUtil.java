package net.dragonmounts.util;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Map;

public class EntityUtil extends EntityType<Entity> {//to access protected methods

    private EntityUtil(IFactory<Entity> p1, EntityClassification p2, boolean p3, boolean p4, boolean p5, boolean p6, ImmutableSet<Block> p7, EntitySize p8, int p9, int p10) {
        super(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
    }

    public static void finalizeSpawn(ServerWorld level, Entity entity, BlockPos pos, SpawnReason reason, @Nullable ILivingEntityData data, @Nullable CompoundNBT tag, boolean yOffset, boolean extraOffset) {
        double offset;
        if (yOffset) {
            entity.setPos(pos.getX() + 0.5D, (pos.getY() + 1), pos.getZ() + 0.5D);
            offset = getYOffset(level, pos, extraOffset, entity.getBoundingBox());
        } else {
            offset = 0.0D;
        }
        entity.moveTo(pos.getX() + 0.5D, pos.getY() + offset, pos.getZ() + 0.5D, MathHelper.wrapDegrees(level.random.nextFloat() * 360.0F), 0.0F);
        if (entity instanceof MobEntity) {
            MobEntity mobentity = (MobEntity) entity;
            mobentity.yHeadRot = mobentity.yRot;
            mobentity.yBodyRot = mobentity.yRot;
            mobentity.finalizeSpawn(level, level.getCurrentDifficultyAt(mobentity.blockPosition()), reason, data, tag);
            mobentity.playAmbientSound();
        }
    }

    public static boolean addOrMergeEffect(LivingEntity entity, Effect effect, int duration, int amplifier, boolean ambient, boolean visible, boolean showIcon) {
        EffectInstance instance = entity.getEffect(effect);
        return entity.addEffect(new EffectInstance(effect, instance != null && instance.getAmplifier() == amplifier ? duration + instance.getDuration() : duration, amplifier, ambient, visible, showIcon, null));
    }

    public static boolean addOrResetEffect(LivingEntity entity, Effect effect, int duration, int amplifier, boolean ambient, boolean visible, boolean showIcon, int threshold) {
        EffectInstance instance = entity.getEffect(effect);
        if (instance != null && instance.getAmplifier() == amplifier && instance.getDuration() > threshold)
            return false;
        return entity.addEffect(new EffectInstance(effect, duration, amplifier, ambient, visible, showIcon, null));
    }

    public static ItemStack consume(PlayerEntity player, Hand hand, ItemStack stack, @Nullable ItemStack result) {
        stack.shrink(1);
        if (result != null) {
            if (stack.isEmpty()) {
                player.setItemInHand(hand, result);
                return result;
            } else if (!player.inventory.add(result)) {//PlayerInventory.getFreeSlot() won't check the offhand slot
                player.drop(result, false);
            }
        }
        return stack;
    }

    public static CompoundNBT saveScoreboard(Entity entity, CompoundNBT compound) {
        Scoreboard scoreboard = entity.level.getScoreboard();
        String scoreboardName = entity.getScoreboardName();
        ScorePlayerTeam team = scoreboard.getPlayersTeam(scoreboardName);
        Map<ScoreObjective, Score> scores = scoreboard.getPlayerScores(scoreboardName);
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
            if (team != null) {
                compound.putString("Team", team.getName());
            }
        }
        return compound;
    }

    public static <T extends Entity> T loadScores(T entity, CompoundNBT compound) {
        World level = entity.level;
        Scoreboard scoreboard = level.getScoreboard();
        String scoreboardName = entity.getScoreboardName();
        Map<ScoreObjective, Score> existingScores = level.getScoreboard().getPlayerScores(scoreboardName);
        CompoundNBT scores;
        ScoreObjective objective;
        Score score;
        if (compound.contains("Scores")) {
            scores = compound.getCompound("Scores");
            for (String name : scores.getAllKeys()) {
                objective = scoreboard.getOrCreateObjective(name);
                if (!existingScores.containsKey(objective)) {
                    score = scoreboard.getOrCreatePlayerScore(scoreboardName, objective);
                    score.setScore(scores.getInt(name));
                    score.setLocked(false);
                }
            }
        }
        if (compound.contains("LockedScores")) {
            scores = compound.getCompound("LockedScores");
            for (String name : scores.getAllKeys()) {
                objective = scoreboard.getOrCreateObjective(name);
                if (!existingScores.containsKey(objective)) {
                    score = scoreboard.getOrCreatePlayerScore(scoreboardName, objective);
                    score.setScore(scores.getInt(name));
                }
            }
        }
        return entity;
    }

    public static <T extends Entity> T loadScoreboard(T entity, CompoundNBT compound) {
        World level = entity.level;
        Scoreboard scoreboard = level.getScoreboard();
        String scoreboardName = entity.getScoreboardName();
        Map<ScoreObjective, Score> existingScores = level.getScoreboard().getPlayerScores(scoreboardName);
        CompoundNBT scores;
        ScoreObjective objective;
        Score score;
        // net.minecraft.entity.LivingEntity.readAdditionalSaveData
        if (compound.contains("Team", 8)) {
            scoreboard.addPlayerToTeam(scoreboardName, level.getScoreboard().getPlayerTeam(compound.getString("Team")));
        }
        if (compound.contains("Scores")) {
            scores = compound.getCompound("Scores");
            for (String name : scores.getAllKeys()) {
                objective = scoreboard.getOrCreateObjective(name);
                if (!existingScores.containsKey(objective)) {
                    score = scoreboard.getOrCreatePlayerScore(scoreboardName, objective);
                    score.setScore(scores.getInt(name));
                    score.setLocked(false);
                }
            }
        }
        if (compound.contains("LockedScores")) {
            scores = compound.getCompound("LockedScores");
            for (String name : scores.getAllKeys()) {
                objective = scoreboard.getOrCreateObjective(name);
                if (!existingScores.containsKey(objective)) {
                    score = scoreboard.getOrCreatePlayerScore(scoreboardName, objective);
                    score.setScore(scores.getInt(name));
                }
            }
        }
        return entity;
    }
}
