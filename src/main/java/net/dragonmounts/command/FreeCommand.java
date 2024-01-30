package net.dragonmounts.command;

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
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.UUID;

import static net.dragonmounts.command.DMCommand.createClassCastException;
import static net.dragonmounts.command.DMCommand.getSingleProfileOrException;

public class FreeCommand {
    public static LiteralArgumentBuilder<CommandSource> register() {
        return Commands.literal("free")
                .requires(source -> source.hasPermission(3))
                .then(Commands.argument("targets", EntityArgument.entities())
                        .executes(context -> free(context, EntityArgument.getEntities(context, "targets")))
                        .then(Commands.argument("owner", GameProfileArgument.gameProfile())
                                .executes(context -> free(context, EntityArgument.getEntities(context, "targets"), getSingleProfileOrException(context, "owner").getId()))
                        )
                        .then(Commands.argument("forced", BoolArgumentType.bool())
                                .executes(context -> free(context, BoolArgumentType.getBool(context, "forced")))
                        )
                );
    }

    private static int free(CommandContext<CommandSource> context, Collection<? extends Entity> targets) throws CommandSyntaxException {
        return free(context, targets, context.getSource().getPlayerOrException().getUUID(), targets.size() == 1);
    }

    private static int free(CommandContext<CommandSource> context, Collection<? extends Entity> targets, UUID owner) {
        return free(context, targets, owner, false);
    }

    private static int free(CommandContext<CommandSource> context, boolean forced) throws CommandSyntaxException {
        return free(context, EntityArgument.getEntities(context, "targets"), null, forced);
    }

    public static int free(CommandContext<CommandSource> context, Collection<? extends Entity> targets, @Nullable UUID owner, boolean forced) {
        CommandSource source = context.getSource();
        Entity cache = null;
        boolean flag = true;
        int count = 0;
        for (Entity target : targets) {
            if (target instanceof TameableEntity) {
                TameableEntity entity = (TameableEntity) target;
                if (forced || (owner != null && owner.equals(entity.getOwnerUUID()))) {
                    entity.setTame(false);
                    entity.setOwnerUUID(null);
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
                source.sendFailure(new TranslationTextComponent("commands.dragonmounts.free.multiple", count));
            }
        } else if (count == 1) {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.free.single", cache.getDisplayName()), true);
        } else {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.free.multiple", count), true);
        }
        return count;
    }
}
