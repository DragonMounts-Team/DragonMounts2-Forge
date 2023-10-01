package net.dragonmounts3.command;

import com.google.common.base.Joiner;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.dragonmounts3.client.gui.DMConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.BiFunction;

import static net.dragonmounts3.DragonMountsConfig.COMMON;
import static net.dragonmounts3.DragonMountsConfig.SERVER;


public class ConfigCommand {
    private static final Joiner DOT_JOINER = Joiner.on(".");

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

    public static LiteralArgumentBuilder<CommandSource> register() {
        return Commands.literal("config")
                .then(Commands.literal("server")
                        .requires(source -> source.hasPermission(3))
                        .then(create(COMMON.debug, BoolArgumentType.bool(), BoolArgumentType::getBool))
                        .then(create(SERVER.base_armor, DoubleArgumentType.doubleArg(0, 30), DoubleArgumentType::getDouble))
                        .then(create(SERVER.base_health, DoubleArgumentType.doubleArg(1, 1024), DoubleArgumentType::getDouble))
                        .then(create(SERVER.base_damage, DoubleArgumentType.doubleArg(0, 2048), DoubleArgumentType::getDouble))
                        .then(create(SERVER.block_override, BoolArgumentType.bool(), BoolArgumentType::getBool))
                ).then(Commands.literal("client"));
    }

    public static class Client {
        private static final String OPEN_CONFIG_SCREEN = "/dragonmounts config client";
        private static boolean OPEN_CONFIG_SCREEN_FLAG = false;

        public static void onClientSendMessage(ClientChatEvent event) {
            if (OPEN_CONFIG_SCREEN.equals(event.getOriginalMessage())) {
                event.setCanceled(true);
                OPEN_CONFIG_SCREEN_FLAG = true;
            }
        }

        public static void onGuiOpen(GuiOpenEvent event) {
            if (OPEN_CONFIG_SCREEN_FLAG && event.getGui() == null) {
                OPEN_CONFIG_SCREEN_FLAG = false;
                Minecraft minecraft = Minecraft.getInstance();
                minecraft.gui.getChat().addRecentChat(OPEN_CONFIG_SCREEN);
                event.setGui(new DMConfigScreen(minecraft, minecraft.screen));
            }
        }
    }
}
