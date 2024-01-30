package net.dragonmounts3.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.dragonmounts3.registry.CooldownCategory;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import static net.dragonmounts3.init.DMCapabilities.ARMOR_EFFECT_MANAGER;

public class CooldownCommand {
    public static LiteralArgumentBuilder<CommandSource> register() {
        RequiredArgumentBuilder<CommandSource, EntitySelector> builder = Commands.argument("player", EntityArgument.player());
        for (CooldownCategory category : CooldownCategory.REGISTRY) {
            builder.then(Commands.literal(category.getSerializedName().toString()).executes(context -> get(context.getSource(), EntityArgument.getPlayer(context, "player"), category))
                    .then(Commands.argument("value", IntegerArgumentType.integer(0)).executes(context -> set(context.getSource(), EntityArgument.getPlayer(context, "player"), category, IntegerArgumentType.getInteger(context, "value"))))
            );
        }
        return Commands.literal("cooldown").requires(source -> source.hasPermission(3)).then(builder);
    }

    public static int get(CommandSource source, PlayerEntity player, CooldownCategory category) {
        player.getCapability(ARMOR_EFFECT_MANAGER).ifPresent(manager -> source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.cooldown.get", player.getDisplayName(), category.getSerializedName(), manager.getCooldown(category)), true));
        return 1;
    }

    public static int set(CommandSource source, PlayerEntity player, CooldownCategory category, int value) {
        player.getCapability(ARMOR_EFFECT_MANAGER).ifPresent(manager -> {
            manager.setCooldown(category, value);
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.cooldown.set", player.getDisplayName(), category.getSerializedName(), value), true);
        });
        return 1;
    }
}
