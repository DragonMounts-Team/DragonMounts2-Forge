package net.dragonmounts.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.dragonmounts.config.ServerConfig;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ConfigCommand {
    public static final String OPEN_CONFIG_SCREEN = "/dragonmounts config client";

    public static <T> LiteralArgumentBuilder<CommandSource> create(
            ForgeConfigSpec.ConfigValue<T> config,
            ArgumentType<T> type,
            BiFunction<CommandContext<CommandSource>, String, T> function
    ) {
        Iterator<String> iterator = config.getPath().iterator();
        if (!iterator.hasNext()) throw new NullPointerException();
        StringBuilder builder = new StringBuilder(iterator.next());
        while (iterator.hasNext()) {
            builder.append('.').append(iterator.next());
        }
        String name = builder.toString();
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

    public static LiteralArgumentBuilder<CommandSource> register(Predicate<CommandSource> permission) {
        return Commands.literal("config")
                .then(Commands.literal("client").requires(source -> source.getEntity() instanceof ServerPlayerEntity))
                .then(Commands.literal("server").requires(permission).then(create(ServerConfig.INSTANCE.debug, BoolArgumentType.bool(), BoolArgumentType::getBool)));
    }
}
