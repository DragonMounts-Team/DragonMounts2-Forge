package net.dragonmounts.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.dragonmounts.capability.IArmorEffectManager.Provider;
import net.dragonmounts.registry.CooldownCategory;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;
import java.util.function.Predicate;

public class CooldownCommand {
    public static LiteralArgumentBuilder<CommandSource> register(Predicate<CommandSource> permission) {
        RequiredArgumentBuilder<CommandSource, EntitySelector> single = Commands.argument("player", EntityArgument.player());
        RequiredArgumentBuilder<CommandSource, EntitySelector> multiple = Commands.argument("players", EntityArgument.players());
        for (CooldownCategory category : CooldownCategory.REGISTRY) {
            String identifier = category.getSerializedName().toString();
            single.then(Commands.literal(identifier).executes(context -> get(context.getSource(), EntityArgument.getPlayer(context, "player"), category))
                    .then(Commands.argument("value", IntegerArgumentType.integer(0)).executes(context -> set(context.getSource(), EntityArgument.getPlayer(context, "player"), category, IntegerArgumentType.getInteger(context, "value"))))
            );
            multiple.then(Commands.literal(identifier).executes(context -> get(context.getSource(), EntityArgument.getPlayers(context, "players"), category))
                    .then(Commands.argument("value", IntegerArgumentType.integer(0)).executes(context -> set(context.getSource(), EntityArgument.getPlayers(context, "players"), category, IntegerArgumentType.getInteger(context, "value"))))
            );
        }
        return Commands.literal("cooldown").requires(permission).then(single).then(multiple);
    }

    public static int get(CommandSource source, ServerPlayerEntity player, CooldownCategory category) {
        source.sendSuccess(new TranslationTextComponent(
                "commands.dragonmounts.cooldown.get.success",
                player.getDisplayName(),
                category.getSerializedName(),
                ((Provider) player).dragonmounts$getManager().getCooldown(category)
        ), true);
        return 1;
    }

    public static int get(CommandSource source, Collection<ServerPlayerEntity> players, CooldownCategory category) {
        if (players.isEmpty()) {
            source.sendFailure(new TranslationTextComponent("commands.dragonmounts.cooldown.get.failure"));
            return 0;
        }
        for (ServerPlayerEntity player : players) {
            source.sendSuccess(new TranslationTextComponent(
                    "commands.dragonmounts.cooldown.get.success",
                    player.getDisplayName(),
                    category.getSerializedName(),
                    ((Provider) player).dragonmounts$getManager().getCooldown(category)
            ), true);
        }
        return players.size();
    }

    public static int set(CommandSource source, ServerPlayerEntity player, CooldownCategory category, int value) {
        ((Provider) player).dragonmounts$getManager().setCooldown(category, value);
        source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.cooldown.set.single", player.getDisplayName(), category.getSerializedName(), value), true);
        return 1;
    }

    public static int set(CommandSource source, Collection<ServerPlayerEntity> players, CooldownCategory category, int value) {
        if (players.isEmpty()) {
            source.sendFailure(new TranslationTextComponent("commands.dragonmounts.cooldown.set.failure"));
            return 0;
        }
        for (ServerPlayerEntity player : players) {
            ((Provider) player).dragonmounts$getManager().setCooldown(category, value);
        }
        int size = players.size();
        source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.cooldown.set.multiple", size, category.getSerializedName(), value), true);
        return size;
    }
}
