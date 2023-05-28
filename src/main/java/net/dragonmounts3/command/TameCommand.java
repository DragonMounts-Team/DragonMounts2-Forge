package net.dragonmounts3.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.Collection;
import java.util.UUID;

import static net.dragonmounts3.command.DMCommand.createClassCastException;
import static net.dragonmounts3.command.DMCommand.getSingleProfileOrException;

public class TameCommand {
    public static LiteralArgumentBuilder<CommandSource> register() {
        return Commands.literal("tame")
                .requires(source -> source.hasPermission(3))
                .then(Commands.argument("targets", EntityArgument.entities())
                        .executes(context -> tame(context, EntityArgument.getEntities(context, "targets")))
                        .then(Commands.argument("owner", GameProfileArgument.gameProfile())
                                .executes(context -> tame(context, EntityArgument.getEntities(context, "targets"), getSingleProfileOrException(context, "owner")))
                                .then(Commands.argument("forced", BoolArgumentType.bool()).executes(
                                        context -> tame(context, BoolArgumentType.getBool(context, "forced"))
                                ))
                        ));

    }

    private static int tame(CommandContext<CommandSource> context, Collection<? extends Entity> targets) throws CommandSyntaxException {
        return tame(context, targets, context.getSource().getPlayerOrException().getGameProfile(), targets.size() == 1);
    }

    private static int tame(CommandContext<CommandSource> context, Collection<? extends Entity> targets, GameProfile owner) {
        return tame(context, targets, owner, targets.size() == 1);
    }

    private static int tame(CommandContext<CommandSource> context, boolean forced) throws CommandSyntaxException {
        return tame(context, EntityArgument.getEntities(context, "targets"), getSingleProfileOrException(context, "owner"), forced);
    }

    private static int tame(CommandContext<CommandSource> context, Collection<? extends Entity> targets, GameProfile owner, boolean forced) {
        CommandSource source = context.getSource();
        ServerWorld level = source.getLevel();
        UUID uuid = owner.getId();
        PlayerEntity player = level.getPlayerByUUID(uuid);
        Entity cache = null;
        boolean flag = true;
        int count = 0;
        if (player == null) {
            for (Entity target : targets) {
                if (target instanceof TameableEntity) {
                    TameableEntity entity = ((TameableEntity) target);
                    if (forced || entity.getOwnerUUID() == null) {
                        entity.setTame(true);
                        entity.setOwnerUUID(uuid);
                        ++count;
                    }
                    flag = false;
                    cache = entity;
                }
            }
        } else {
            for (Entity target : targets) {
                if (target instanceof TameableEntity) {
                    TameableEntity entity = ((TameableEntity) target);
                    if (forced || entity.getOwnerUUID() == null) {
                        entity.tame(player);
                        ++count;
                    }
                    flag = false;
                    cache = entity;
                }
            }
        }
        if (flag) {
            if (targets.size() == 1) {
                source.sendFailure(createClassCastException(targets.iterator().next(), TameableEntity.class));
            } else {
                source.sendFailure(new TranslationTextComponent("commands.dragonmounts.tame.multiple", count));
            }
        } else if (count == 1) {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.tame.single", cache.getDisplayName(), owner.getName()), true);
        } else {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.tame.multiple", count, owner.getName()), true);
        }
        return count;
    }
}
