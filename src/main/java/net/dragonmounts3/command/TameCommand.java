package net.dragonmounts3.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;

import static net.dragonmounts3.command.DMCommand.createClassCastException;

public class TameCommand {
    public static LiteralArgumentBuilder<CommandSource> register() {
        RequiredArgumentBuilder<CommandSource, EntitySelector> builder = Commands.argument("targets", EntityArgument.entities())
                .executes(context -> set(context.getSource(), EntityArgument.getEntities(context, "targets"), context.getSource().getPlayerOrException()))
                .then(Commands.argument("owner", EntityArgument.player())
                        .executes(context -> set(context.getSource(), EntityArgument.getEntities(context, "targets"), EntityArgument.getPlayer(context, "owner")))
                );
        return Commands.literal("tame").requires(source -> source.hasPermission(3))
                .then(builder)//Is there a player named "set" or "reset"?
                .then(Commands.literal("reset")
                        .then(Commands.argument("targets", EntityArgument.entities())
                                .executes(context -> reset(context.getSource(), EntityArgument.getEntities(context, "targets")))
                        )
                )
                .then(Commands.literal("set").then(builder));

    }

    private static int set(CommandSource source, Collection<? extends Entity> targets, PlayerEntity owner) {
        int count = 0;
        Entity cache = null;
        for (Entity target : targets) {
            if (target instanceof TameableEntity) {
                ((TameableEntity) target).tame(owner);
                ++count;
                cache = target;
            }
        }
        if (count == 0) {
            if (targets.size() == 1) {
                source.sendFailure(createClassCastException(targets.iterator().next(), TameableEntity.class));
            }
        } else if (count == 1) {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.tame.set.single", cache.getDisplayName(), owner.getDisplayName()), true);
        } else {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.tame.set.multiple", count, owner.getDisplayName()), true);
        }
        return count;
    }

    private static int reset(CommandSource source, Collection<? extends Entity> targets) {
        int count = 0;
        Entity cache = null;
        for (Entity target : targets) {
            if (target instanceof TameableEntity) {
                TameableEntity entity = (TameableEntity) target;
                entity.setTame(false);
                entity.setOwnerUUID(null);
                ++count;
                cache = entity;
            }
        }
        if (count == 0) {
            if (targets.size() == 1) {
                source.sendFailure(createClassCastException(targets.iterator().next(), TameableEntity.class));
            }
        } else if (count == 1) {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.tame.reset.single", cache.getDisplayName()), true);
        } else {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.tame.reset.multiple", count), true);
        }
        return count;
    }
}
