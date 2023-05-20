package net.dragonmounts3.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.dragonmounts3.DragonMountsConfig;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class ConfigCommand {
    public static LiteralArgumentBuilder<CommandSource> register() {
        return Commands.literal("config").then(
                Commands.literal("common").then(
                        Commands.literal("debug").then(
                                Commands.argument("value", BoolArgumentType.bool()).executes(
                                        context -> {
                                            DragonMountsConfig.DEBUG.set(BoolArgumentType.getBool(context, "value"));
                                            return 1;
                                        }
                                )
                        ).executes(context -> {
                            CommandSource source = context.getSource();
                            source.sendSuccess(new StringTextComponent(DragonMountsConfig.DEBUG.get().toString()), true);
                            return 1;
                        })
                ).then(
                        Commands.literal("max_health").then(
                                Commands.argument("value", DoubleArgumentType.doubleArg(1, 1024)).executes(
                                        context -> {
                                            DragonMountsConfig.BASE_HEALTH.set(DoubleArgumentType.getDouble(context, "value"));
                                            return 1;
                                        }
                                )
                        ).executes(context -> {
                            CommandSource source = context.getSource();
                            source.sendSuccess(new StringTextComponent(DragonMountsConfig.BASE_HEALTH.get().toString()), true);
                            return 1;
                        })
                )
        );
    }
}
