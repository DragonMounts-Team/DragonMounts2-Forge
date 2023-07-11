package net.dragonmounts3.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.world.World;

import java.util.Map;

import static net.dragonmounts3.entity.dragon.TameableDragonEntity.FLYING_DATA_PARAMETER_KEY;

public class EntityUtil {
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

    public static CompoundNBT simplifyDragonData(CompoundNBT compound) {
        compound.remove(FLYING_DATA_PARAMETER_KEY);
        compound.remove("Air");
        compound.remove("DeathTime");
        compound.remove("FallDistance");
        compound.remove("FallFlying");
        compound.remove("Fire");
        compound.remove("HurtByTimestamp");
        compound.remove("HurtTime");
        compound.remove("InLove");
        compound.remove("Leash");
        compound.remove("Motion");
        compound.remove("OnGround");
        compound.remove("PortalCooldown");
        compound.remove("Pos");
        compound.remove("Rotation");
        compound.remove("SleepingX");
        compound.remove("SleepingY");
        compound.remove("SleepingZ");
        compound.remove("TicksFrozen");
        return compound;
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
