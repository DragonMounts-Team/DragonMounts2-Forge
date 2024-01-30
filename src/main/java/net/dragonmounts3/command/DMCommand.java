package net.dragonmounts3.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.event.HoverEvent;

import java.util.Collection;

import static net.minecraft.util.text.event.HoverEvent.Action.SHOW_ENTITY;

public class DMCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("dragonmounts")
                .then(ConfigCommand.register())
                .then(CooldownCommand.register())
                .then(FreeCommand.register())
                .then(StageCommand.register())
                .then(TameCommand.register())
                .then(TypeCommand.register());
        dispatcher.register(builder);
    }

    public static ITextComponent createClassCastException(Class<?> from, Class<?> to) {
        return new StringTextComponent("java.lang.ClassCastException: " + from.getName() + " cannot be cast to " + to.getName());
    }

    public static ITextComponent createClassCastException(Entity entity, Class<?> clazz) {
        return new StringTextComponent("java.lang.ClassCastException: ").append(
                new StringTextComponent(entity.getClass().getName()).setStyle(
                        Style.EMPTY.withInsertion(entity.getStringUUID())
                                .withHoverEvent(new HoverEvent(SHOW_ENTITY, new HoverEvent.EntityHover(entity.getType(), entity.getUUID(), entity.getName())))
                )
        ).append(" cannot be cast to " + clazz.getName());
    }

    public static GameProfile getSingleProfileOrException(CommandContext<CommandSource> context, String name) throws CommandSyntaxException {
        Collection<GameProfile> profiles = GameProfileArgument.getGameProfiles(context, name);
        if (profiles.isEmpty()) {
            throw EntityArgument.NO_ENTITIES_FOUND.create();
        } else if (profiles.size() > 1) {
            throw EntityArgument.ERROR_NOT_SINGLE_ENTITY.create();
        } else {
            return profiles.stream().findAny().get();
        }
    }
}
