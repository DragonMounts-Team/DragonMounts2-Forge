package net.dragonmounts3.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.IDragonTypified;
import net.dragonmounts3.api.IMutableDragonTypified;
import net.dragonmounts3.event.OnDragonTypeEditEvent;
import net.dragonmounts3.init.DMBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;

import static net.dragonmounts3.command.DMCommand.createClassCastException;

public class TypeCommand {
    public static LiteralArgumentBuilder<CommandSource> register() {
        return Commands.literal("type").requires(source -> source.hasPermission(3))
                .then(Commands.literal("block").then(impl(
                        Commands.argument("pos", BlockPosArgument.blockPos()),
                        TypeCommand::getType,
                        TypeCommand::setType,
                        TypeCommand::block
                )))
                .then(Commands.literal("entity").then(impl(
                        Commands.argument("target", EntityArgument.entity()),
                        TypeCommand::getType,
                        TypeCommand::setType,
                        TypeCommand::entity
                )));
    }

    private static BlockPos block(CommandContext<CommandSource> context) throws CommandSyntaxException {
        return BlockPosArgument.getLoadedBlockPos(context, "pos");
    }

    private static Entity entity(CommandContext<CommandSource> context) throws CommandSyntaxException {
        return EntityArgument.getEntity(context, "target");
    }

    private static int getType(CommandContext<CommandSource> context, BlockPos pos) {
        CommandSource source = context.getSource();
        World level = source.getLevel();
        BlockState oldState = level.getBlockState(pos);
        net.minecraft.block.Block block = oldState.getBlock();
        if (block instanceof IDragonTypified) {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.block.get", pos.getX(), pos.getY(), pos.getZ(), ((IDragonTypified) block).getDragonType().getText()), true);
            return 1;
        }
        source.sendFailure(createClassCastException(block.getClass().getCanonicalName(), IDragonTypified.class));
        return 0;
    }

    private static int getType(CommandContext<CommandSource> context, Entity target) {
        CommandSource source = context.getSource();
        if (target instanceof IDragonTypified) {
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.entity.get", target.getDisplayName(), ((IDragonTypified) target).getDragonType().getText()), true);
            return 1;
        }
        source.sendFailure(createClassCastException(target, IDragonTypified.class));
        return 0;
    }

    private static int setType(CommandContext<CommandSource> context, BlockPos pos, DragonType type) {
        CommandSource source = context.getSource();
        ServerWorld level = source.getLevel();
        BlockState originalState = level.getBlockState(pos);
        OnDragonTypeEditEvent.Block event = new OnDragonTypeEditEvent.Block(level, type, pos, originalState);
        Block block = originalState.getBlock();
        if (block instanceof IDragonTypified) {
            if (block instanceof DragonEggBlock) {
                net.minecraft.block.Block egg = DMBlocks.HATCHABLE_DRAGON_EGG.get(type);
                if (egg != null) {
                    event.setState(egg.defaultBlockState());
                }
            }
        }
        MinecraftForge.EVENT_BUS.post(event);
        BlockState newState = event.getState();
        if (event.isCanceled()) {
            return 0;
        }
        if (newState == originalState) {
            return 0;
        }
        level.setBlockAndUpdate(pos, newState);
        source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.block.set", pos.getX(), pos.getY(), pos.getZ(), type.getText()), true);
        return 1;
    }

    private static int setType(CommandContext<CommandSource> context, Entity target, DragonType type) {
        CommandSource source = context.getSource();
        if (target instanceof IMutableDragonTypified) {
            ((IMutableDragonTypified) target).setDragonType(type);
            source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.entity.set", target.getDisplayName(), type.getText()), true);
            return 1;
        }
        source.sendFailure(createClassCastException(target, IMutableDragonTypified.class));
        return 0;
    }

    private static <T, S> RequiredArgumentBuilder<CommandSource, T> impl(
            RequiredArgumentBuilder<CommandSource, T> builder,
            Getter<S> getter,
            Setter<S> setter,
            Provider<S> provider
    ) {
        for (DragonType type : DragonType.values()) {
            builder.then(Commands.literal(type.getSerializedName())
                    .executes(context -> setter.set(context, provider.get(context), type)));
        }
        builder.executes(context -> getter.get(context, provider.get(context)));
        return builder;
    }

    @FunctionalInterface
    private interface Getter<T> {
        int get(CommandContext<CommandSource> context, T target);
    }

    @FunctionalInterface
    private interface Setter<T> {
        int set(CommandContext<CommandSource> context, T target, DragonType type);
    }

    @FunctionalInterface
    private interface Provider<T> {
        T get(CommandContext<CommandSource> context) throws CommandSyntaxException;
    }
}
