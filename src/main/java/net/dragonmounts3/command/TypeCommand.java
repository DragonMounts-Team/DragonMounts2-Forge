package net.dragonmounts3.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.dragonmounts3.registry.DragonType;
import net.dragonmounts3.registry.IDragonTypified;
import net.dragonmounts3.registry.IMutableDragonTypified;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TranslationTextComponent;

import static net.dragonmounts3.command.DMCommand.createClassCastException;

public class TypeCommand {
    public static LiteralArgumentBuilder<CommandSource> register() {
        return Commands.literal("type").requires(source -> source.hasPermission(3))
                .then(Commands.literal("get")
                        .then(Commands.argument("targets", EntityArgument.entities()).executes(context -> get(context.getSource(), EntityArgument.getEntity(context, "targets"))))
                )
                .then(Commands.literal("set")
                        .then(addValuesTo(Commands.argument("target", EntityArgument.entity())))
                );
    }

    private static RequiredArgumentBuilder<CommandSource, EntitySelector> addValuesTo(RequiredArgumentBuilder<CommandSource, EntitySelector> builder) {
        for (DragonType type : DragonType.values()) {
            builder.then(Commands.literal(type.getSerializedName()).executes(context -> set(context.getSource(), EntityArgument.getEntity(context, "target"), type)));
        }
        return builder;
    }

    private static int get(CommandSource source, Entity target) {
        if (target instanceof IDragonTypified) {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.get", target.getDisplayName(), ((IDragonTypified) target).getDragonType().getText()), true);
            return 1;
        }
        source.sendFailure(createClassCastException(target, IDragonTypified.class));
        return 0;
    }

    private static int set(CommandSource source, Entity target, DragonType type) {
        if (target instanceof IMutableDragonTypified) {
            ((IMutableDragonTypified) target).setDragonType(type);
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.set", target.getDisplayName(), type.getText()), true);
            return 1;
        }
        source.sendFailure(createClassCastException(target, IMutableDragonTypified.class));
        return 0;
    }
}
