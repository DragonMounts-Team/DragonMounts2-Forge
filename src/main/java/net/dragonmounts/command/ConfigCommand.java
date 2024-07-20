package net.dragonmounts.command;

import com.google.common.base.Joiner;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.dragonmounts.config.ServerConfig;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.BiFunction;

import static net.dragonmounts.command.DMCommand.HAS_PERMISSION_LEVEL_3;


public class ConfigCommand {
    private static final Joiner DOT_JOINER = Joiner.on(".");
    public static final String OPEN_CONFIG_SCREEN = "/dragonmounts config client";

    public static <T> LiteralArgumentBuilder<CommandSource> create(
            ForgeConfigSpec.ConfigValue<T> config,
            ArgumentType<T> type,
            BiFunction<CommandContext<CommandSource>, String, T> function
    ) {
        String name = DOT_JOINER.join(config.getPath());
        return Commands.literal(name)
                .executes(context -> {
                    context.getSource().sendSuccess(new TranslationTextComponent("commands.dragonmounts.config.query", name, config.get()), true);
                    return 1;
                })
                .then(Commands.argument("value", type).executes(context -> {
                    T value = function.apply(context, "value");
                    config.set(value);
                    context.getSource().sendSuccess(new TranslationTextComponent("commands.dragonmounts.config.set", name, value), true);
                    return 1;
                }));
    }

    public static LiteralArgumentBuilder<CommandSource> register(Commands.EnvironmentType environment) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("config").then(Commands.literal("server")
                .requires(HAS_PERMISSION_LEVEL_3)
                .then(create(ServerConfig.INSTANCE.debug, BoolArgumentType.bool(), BoolArgumentType::getBool))
        );
        return environment == Commands.EnvironmentType.INTEGRATED ? builder.then(Commands.literal("client")) : builder;
    }
}
