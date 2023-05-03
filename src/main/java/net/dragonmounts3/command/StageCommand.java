package net.dragonmounts3.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.dragonmounts3.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.entity.dragon.config.DragonLifeStage;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

import static net.dragonmounts3.command.DMCommand.createClassCastException;
import static net.dragonmounts3.entity.dragon.TameableDragonEntity.AGE_DATA_PARAMETER_KEY;
import static net.dragonmounts3.entity.dragon.config.DragonLifeStage.EGG_TRANSLATION_KEY;

public class StageCommand {
    public static LiteralArgumentBuilder<CommandSource> register() {
        return Commands.literal("stage").requires(source -> source.hasPermission(3))
                .then(Commands.literal("set")
                        .then(addValuesTo(Commands.argument("target", EntityArgument.entity())))
                )
                .then(Commands.literal("get")
                        .then(Commands.argument("targets", EntityArgument.entities()).executes(context -> get(context.getSource(), EntityArgument.getEntity(context, "targets"))))
                );
    }

    private static RequiredArgumentBuilder<CommandSource, EntitySelector> addValuesTo(RequiredArgumentBuilder<CommandSource, EntitySelector> builder) {
        builder.then(Commands.literal("egg").executes(context -> egg(context.getSource(), EntityArgument.getEntity(context, "target"))));
        for (DragonLifeStage stage : DragonLifeStage.values()) {
            builder.then(Commands.literal(stage.name().toLowerCase()).executes(context -> set(context.getSource(), EntityArgument.getEntity(context, "target"), stage)));
        }
        return builder;
    }

    private static int egg(CommandSource source, Entity target) {
        if (target instanceof TameableDragonEntity) {
            ServerWorld level = source.getLevel();
            HatchableDragonEggEntity egg = new HatchableDragonEggEntity(target.level);
            CompoundNBT compound = target.saveWithoutId(new CompoundNBT());
            compound.remove(AGE_DATA_PARAMETER_KEY);
            egg.load(compound);
            level.removeEntity(target, false);
            level.addFreshEntity(egg);
        } else if (!(target instanceof HatchableDragonEggEntity)) {
            source.sendFailure(createClassCastException(target, TameableDragonEntity.class));
            return 0;
        }
        source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.stage.set", target.getDisplayName(), new TranslationTextComponent(EGG_TRANSLATION_KEY)), true);
        return 1;
    }

    private static int get(CommandSource source, Entity target) {
        if (target instanceof TameableDragonEntity) {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.stage.get", target.getDisplayName(), ((TameableDragonEntity) target).getLifeStage().getText()), true);
        } else if (target instanceof HatchableDragonEggEntity) {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.stage.get", target.getDisplayName(), new TranslationTextComponent(EGG_TRANSLATION_KEY)), true);
        } else {
            source.sendFailure(createClassCastException(target, TameableDragonEntity.class));
            return 0;
        }
        return 1;
    }

    private static int set(CommandSource source, Entity target, DragonLifeStage stage) {
        if (target instanceof TameableDragonEntity) {
            TameableDragonEntity dragon = (TameableDragonEntity) target;
            dragon.setLifeStage(stage);
        } else if (target instanceof HatchableDragonEggEntity) {
            ServerWorld level = source.getLevel();
            TameableDragonEntity dragon = new TameableDragonEntity(target.level);
            CompoundNBT compound = target.saveWithoutId(new CompoundNBT());
            compound.putInt(DragonLifeStage.DATA_PARAMETER_KEY, stage.ordinal());
            //TODO: reset age
            dragon.load(compound);
            level.removeEntity(target, false);
            level.addFreshEntity(dragon);
        } else {
            source.sendFailure(createClassCastException(target, TameableDragonEntity.class));
            return 0;
        }
        source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.stage.set", target.getDisplayName(), stage.getText()), true);
        return 1;
    }
}
