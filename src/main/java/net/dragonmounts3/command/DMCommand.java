package net.dragonmounts3.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.HoverEvent;

import static net.minecraft.util.text.event.HoverEvent.Action.SHOW_ENTITY;

public class DMCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("dragonmounts")
                .then(ConfigCommand.register())
                .then(FreeCommand.register())
                .then(StageCommand.register())
                .then(TameCommand.register())
                .then(TypeCommand.register());
        dispatcher.register(builder);
    }

    public static ITextComponent createClassCastException(String string, Class<?> clazz) {
        return new TranslationTextComponent("message.class_cast_exception", string, clazz.getCanonicalName());
    }

    public static ITextComponent createClassCastException(Entity entity, Class<?> clazz) {
        return new TranslationTextComponent(
                "message.class_cast_exception",
                new StringTextComponent(entity.getClass().getCanonicalName()).setStyle(
                        Style.EMPTY.withInsertion(entity.getStringUUID())
                                .withHoverEvent(new HoverEvent(SHOW_ENTITY, new HoverEvent.EntityHover(entity.getType(), entity.getUUID(), entity.getName())))
                ),
                clazz.getCanonicalName()
        );
    }
}
