package net.dragonmounts.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.dragonmounts.registry.CooldownCategory;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;

import static net.dragonmounts.init.DMCapabilities.ARMOR_EFFECT_MANAGER;

public class CooldownCommand {
    public static LiteralArgumentBuilder<CommandSource> register() {
        RequiredArgumentBuilder<CommandSource, EntitySelector> single = Commands.argument("player", EntityArgument.player());
        RequiredArgumentBuilder<CommandSource, EntitySelector> multiple = Commands.argument("players", EntityArgument.players());
        for (CooldownCategory category : CooldownCategory.REGISTRY) {
            single.then(Commands.literal(category.getSerializedName().toString()).executes(context -> get(context.getSource(), EntityArgument.getPlayer(context, "player"), category))
                    .then(Commands.argument("value", IntegerArgumentType.integer(0)).executes(context -> set(context.getSource(), EntityArgument.getPlayer(context, "player"), category, IntegerArgumentType.getInteger(context, "value"))))
            );
            multiple.then(Commands.literal(category.getSerializedName().toString()).executes(context -> get(context.getSource(), EntityArgument.getPlayers(context, "players"), category))
                    .then(Commands.argument("value", IntegerArgumentType.integer(0)).executes(context -> set(context.getSource(), EntityArgument.getPlayers(context, "players"), category, IntegerArgumentType.getInteger(context, "value"))))
            );
        }
        return Commands.literal("cooldown").requires(source -> source.hasPermission(3)).then(single).then(multiple);
    }

    public static int get(CommandSource source, ServerPlayerEntity player, CooldownCategory category) {
        int[] result = new int[]{0};
        player.getCapability(ARMOR_EFFECT_MANAGER).ifPresent(manager -> {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.cooldown.get.success", player.getDisplayName(), category.getSerializedName(), manager.getCooldown(category)), true);
            result[0] = 1;
        });
        if (result[0] == 0) {
            source.sendFailure(new TranslationTextComponent("commands.dragonmounts.cooldown.get.failure"));
            return 0;
        }
        return 1;
    }

    public static int get(CommandSource source, Collection<ServerPlayerEntity> players, CooldownCategory category) {
        int[] result = new int[]{0};
        for (ServerPlayerEntity player : players) {
            player.getCapability(ARMOR_EFFECT_MANAGER).ifPresent(manager -> {
                source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.cooldown.get.success", player.getDisplayName(), category.getSerializedName(), manager.getCooldown(category)), true);
                ++result[0];
            });
        }
        if (result[0] == 0) {
            source.sendFailure(new TranslationTextComponent("commands.dragonmounts.cooldown.get.failure"));
            return 0;
        }
        return result[0];
    }

    public static int set(CommandSource source, ServerPlayerEntity player, CooldownCategory category, int value) {
        int[] result = new int[]{0};
        player.getCapability(ARMOR_EFFECT_MANAGER).ifPresent(manager -> {
            manager.setCooldown(category, value);
            result[0] = 1;
        });
        if (result[0] == 0) {
            source.sendFailure(new TranslationTextComponent("commands.dragonmounts.cooldown.set.failure"));
            return 0;
        }
        source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.cooldown.set.single", player.getDisplayName(), category.getSerializedName(), value), true);
        return 1;
    }

    public static int set(CommandSource source, Collection<ServerPlayerEntity> players, CooldownCategory category, int value) {
        int[] result = new int[]{0};
        for (ServerPlayerEntity player : players) {
            player.getCapability(ARMOR_EFFECT_MANAGER).ifPresent(manager -> {
                manager.setCooldown(category, value);
                ++result[0];
            });
        }
        if (result[0] == 0) {
            source.sendFailure(new TranslationTextComponent("commands.dragonmounts.cooldown.set.failure"));
            return 0;
        }
        source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.cooldown.set.multiple", result[0], category.getSerializedName(), value), true);
        return result[0];
    }
}
