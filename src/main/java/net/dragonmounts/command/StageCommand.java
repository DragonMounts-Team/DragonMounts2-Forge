package net.dragonmounts.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.dragonmounts.entity.dragon.DragonLifeStage;
import net.dragonmounts.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts.entity.dragon.ServerDragonEntity;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.function.Predicate;

import static net.dragonmounts.command.DMCommand.createClassCastException;
import static net.dragonmounts.entity.dragon.DragonLifeStage.EGG_TRANSLATION_KEY;
import static net.dragonmounts.entity.dragon.TameableDragonEntity.AGE_DATA_PARAMETER_KEY;

public class StageCommand {
    public static LiteralArgumentBuilder<CommandSource> register(Predicate<CommandSource> permission) {
        return Commands.literal("stage").requires(permission).then(
                DragonLifeStage.applyValues(Commands.argument("target", EntityArgument.entity()))
                        .executes(context -> get(context.getSource(), EntityArgument.getEntity(context, "target")))
        );
    }

    public static int egg(CommandSource source, Entity target) {
        if (target instanceof TameableDragonEntity) {
            ServerWorld level = source.getLevel();
            HatchableDragonEggEntity egg = new HatchableDragonEggEntity(level);
            TameableDragonEntity dragon = (TameableDragonEntity) target;
            dragon.inventory.dropContents(false, 1.25);
            CompoundNBT tag = dragon.saveWithoutId(new CompoundNBT());
            tag.remove(AGE_DATA_PARAMETER_KEY);
            egg.load(tag);
            egg.setDragonType(dragon.getDragonType(), false);
            level.removeEntity(dragon, false);
            level.addFreshEntity(egg);
        } else if (target instanceof HatchableDragonEggEntity) {
            ((HatchableDragonEggEntity) target).setAge(0, false);
        } else {
            source.sendFailure(createClassCastException(target, TameableDragonEntity.class));
            return 0;
        }
        source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.stage.set", target.getDisplayName(), new TranslationTextComponent(EGG_TRANSLATION_KEY)), true);
        return 1;
    }

    public static int get(CommandSource source, Entity target) {
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

    public static int set(CommandSource source, Entity target, DragonLifeStage stage) {
        if (target instanceof TameableDragonEntity) {
            ((TameableDragonEntity) target).setLifeStage(stage, true, true);
        } else if (target instanceof HatchableDragonEggEntity) {
            ServerWorld level = source.getLevel();
            ServerDragonEntity dragon = new ServerDragonEntity((HatchableDragonEggEntity) target, stage);
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
