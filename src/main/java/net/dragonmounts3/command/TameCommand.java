package net.dragonmounts3.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;

import static net.dragonmounts3.command.DMCommand.createClassCastException;

public class TameCommand {
    public static LiteralArgumentBuilder<CommandSource> register() {
        return Commands.literal("tame")
                .requires(source -> source.hasPermission(3))
                .then(Commands.argument("targets", EntityArgument.entities())
                        .executes(context -> tame(context, EntityArgument.getEntities(context, "targets")))
                        .then(Commands.argument("owner", EntityArgument.player())
                                .executes(context -> tame(context, EntityArgument.getEntities(context, "targets"), EntityArgument.getPlayer(context, "owner")))
                                .then(Commands.argument("forced", BoolArgumentType.bool()).executes(
                                        context -> tame(context, BoolArgumentType.getBool(context, "forced"))
                                ))
                        ));

    }

    private static int tame(CommandContext<CommandSource> context, Collection<? extends Entity> targets) throws CommandSyntaxException {
        return tame(context, targets, context.getSource().getPlayerOrException(), targets.size() == 1);
    }

    private static int tame(CommandContext<CommandSource> context, Collection<? extends Entity> targets, PlayerEntity owner) {
        return tame(context, targets, owner, targets.size() == 1);
    }

    private static int tame(CommandContext<CommandSource> context, boolean forced) throws CommandSyntaxException {
        return tame(context, EntityArgument.getEntities(context, "targets"), EntityArgument.getPlayer(context, "owner"), forced);
    }

    private static int tame(CommandContext<CommandSource> context, Collection<? extends Entity> targets, PlayerEntity owner, boolean forced) {
        CommandSource source = context.getSource();
        boolean flag = true;
        int count = 0;
        Entity cache = null;
        for (Entity target : targets) {
            if (target instanceof TameableEntity) {
                TameableEntity entity = ((TameableEntity) target);
                if (forced || entity.getOwnerUUID() == null) {
                    entity.tame(owner);
                    ++count;
                }
                flag = false;
                cache = entity;
            }
        }
        if (flag) {
            if (targets.size() == 1) {
                source.sendFailure(createClassCastException(targets.iterator().next(), TameableEntity.class));
            } else {
                source.sendFailure(new TranslationTextComponent("commands.dragonmounts.tame.multiple", count));
            }
        } else if (count == 1) {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.tame.single", cache.getDisplayName(), owner.getDisplayName()), true);
        } else {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.tame.multiple", count, owner.getDisplayName()), true);
        }
        return count;
    }
}
