package net.dragonmounts.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Predicate;

public class RideCommand {
    public static LiteralArgumentBuilder<CommandSource> register(Predicate<CommandSource> permission) {
        return Commands.literal("ride").requires(permission).then(
                Commands.argument("target", EntityArgument.entity())
                        .then(Commands.literal("mount")
                                .then(Commands.argument("vehicle", EntityArgument.entity())
                                        .executes(context -> mount(context.getSource(), EntityArgument.getEntity(context, "target"), EntityArgument.getEntity(context, "vehicle")))))
                        .then(Commands.literal("dismount")
                                .executes(context -> dismount(context.getSource(), EntityArgument.getEntity(context, "target"))))
        );
    }

    public static int mount(CommandSource source, Entity target, Entity vehicle) {
        Entity current = target.getVehicle();
        if (current != null) {
            source.sendFailure(new TranslationTextComponent("commands.ride.already_riding", target.getDisplayName(), current.getDisplayName()));
            return 0;
        }
        if (vehicle.getType() == EntityType.PLAYER) {
            source.sendFailure(new TranslationTextComponent("commands.ride.mount.failure.cant_ride_players"));
            return 0;
        }
        if (target.getSelfAndPassengers().anyMatch(vehicle::is)) {
            source.sendFailure(new TranslationTextComponent("commands.ride.mount.failure.loop"));
            return 0;
        }
        if (target.level != vehicle.level) {
            source.sendFailure(new TranslationTextComponent("commands.ride.mount.failure.wrong_dimension"));
            return 0;
        }
        if (target.startRiding(vehicle, true)) {
            source.sendSuccess(new TranslationTextComponent("commands.ride.mount.success", target.getDisplayName(), vehicle.getDisplayName()), true);
            return 1;
        }
        source.sendFailure(new TranslationTextComponent("commands.ride.mount.failure.generic"));
        return 0;
    }

    public static int dismount(CommandSource source, Entity target) {
        Entity vehicle = target.getVehicle();
        if (vehicle == null) {
            source.sendFailure(new TranslationTextComponent("commands.ride.not_riding"));
            return 0;
        }
        target.stopRiding();
        source.sendSuccess(new TranslationTextComponent("commands.ride.dismount.success", target.getDisplayName(), vehicle.getDisplayName()), true);
        return 1;
    }
}
