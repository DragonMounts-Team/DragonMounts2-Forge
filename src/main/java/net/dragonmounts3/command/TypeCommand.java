package net.dragonmounts3.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.IDragonTypified;
import net.dragonmounts3.api.IMutableDragonTypified;
import net.dragonmounts3.block.HatchableDragonEggBlock;
import net.dragonmounts3.init.DMBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.HashMap;

import static net.dragonmounts3.command.DMCommand.createClassCastException;

public class TypeCommand {
    public abstract static class CommandHandler<A> {
        protected abstract A getArgument(CommandContext<CommandSource> context) throws CommandSyntaxException;

        protected abstract int setType(CommandContext<CommandSource> context, A argument, DragonType type);

        protected abstract int getType(CommandContext<CommandSource> context, A argument);

        public <T> RequiredArgumentBuilder<CommandSource, T> load(RequiredArgumentBuilder<CommandSource, T> builder) {
            for (DragonType type : DragonType.values()) {
                builder.then(Commands.literal(type.getSerializedName()).executes(context -> this.setType(context, this.getArgument(context), type)));
            }
            builder.executes(context -> this.getType(context, this.getArgument(context)));
            return builder;
        }
    }

    public static class BlockHandler extends CommandHandler<BlockPos> {
        @FunctionalInterface
        public interface Getter {
            DragonType get(Block block, ServerWorld level, BlockPos pos, BlockState state);
        }

        @FunctionalInterface
        public interface Setter {
            BlockState set(Block block, ServerWorld level, BlockPos pos, BlockState state, DragonType type);
        }

        public static DragonType defaultGetter(Block block, ServerWorld level, BlockPos pos, BlockState state) {
            if (block == Blocks.DRAGON_EGG) return DragonType.ENDER;
            return null;
        }

        public static BlockState defaultSetter(Block block, ServerWorld level, BlockPos pos, BlockState state, DragonType type) {
            if (block == Blocks.DRAGON_EGG || block instanceof HatchableDragonEggBlock) {
                Block egg = DMBlocks.HATCHABLE_DRAGON_EGG.get(type);
                return egg == null ? state : egg.defaultBlockState();
            }
            return state;
        }

        private final HashMap<Class<? extends Block>, Getter> getters = new HashMap<>();
        private final HashMap<Class<? extends Block>, Setter> setters = new HashMap<>();

        public Getter bind(Class<? extends Block> clazz, Getter getter) {
            return this.getters.put(clazz, getter);
        }

        public Setter bind(Class<? extends Block> clazz, Setter setter) {
            return this.setters.put(clazz, setter);
        }

        @Override
        protected BlockPos getArgument(CommandContext<CommandSource> context) throws CommandSyntaxException {
            return BlockPosArgument.getLoadedBlockPos(context, "pos");
        }

        @Override
        protected int setType(CommandContext<CommandSource> context, BlockPos pos, DragonType type) {
            CommandSource source = context.getSource();
            ServerWorld level = source.getLevel();
            BlockState original = level.getBlockState(pos);
            Block block = original.getBlock();
            Class<? extends Block> clazz = block.getClass();
            Setter setter = this.setters.get(clazz);
            if (setter != null) {
                BlockState state = setter.set(block, level, pos, original, type);
                if (state != original) {
                    level.setBlockAndUpdate(pos, state);
                    source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.block.set", pos.getX(), pos.getY(), pos.getZ(), type.getText()), true);
                    return 1;
                }
            }
            source.sendFailure(new StringTextComponent("java.lang.NullPointerException: " + clazz.getCanonicalName() + " has no bound handler"));
            return 0;
        }

        @Override
        protected int getType(CommandContext<CommandSource> context, BlockPos pos) {
            CommandSource source = context.getSource();
            ServerWorld level = source.getLevel();
            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();
            Class<? extends Block> clazz = block.getClass();
            Getter getter = this.getters.get(clazz);
            if (getter != null) {
                DragonType type = getter.get(block, level, pos, state);
                if (type != null) {
                    source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.block.get", pos.getX(), pos.getY(), pos.getZ(), type.getText()), true);
                    return 1;
                }
            }
            if (block instanceof IDragonTypified) {
                source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.block.get", pos.getX(), pos.getY(), pos.getZ(), ((IDragonTypified) block).getDragonType().getText()), true);
                return 1;
            }
            source.sendFailure(createClassCastException(clazz, IDragonTypified.class));
            return 0;
        }
    }

    public static class EntityHandler extends CommandHandler<Entity> {
        @Override
        protected Entity getArgument(CommandContext<CommandSource> context) throws CommandSyntaxException {
            return EntityArgument.getEntity(context, "target");
        }

        @Override
        protected int setType(CommandContext<CommandSource> context, Entity entity, DragonType type) {
            CommandSource source = context.getSource();
            if (entity instanceof IMutableDragonTypified) {
                ((IMutableDragonTypified) entity).setDragonType(type);
                source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.entity.set", entity.getDisplayName(), type.getText()), true);
                return 1;
            }
            source.sendFailure(createClassCastException(entity, IMutableDragonTypified.class));
            return 0;
        }

        @Override
        protected int getType(CommandContext<CommandSource> context, Entity entity) {
            CommandSource source = context.getSource();
            if (entity instanceof IDragonTypified) {
                source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.entity.get", entity.getDisplayName(), ((IDragonTypified) entity).getDragonType().getText()), true);
                return 1;
            }
            source.sendFailure(createClassCastException(entity, IDragonTypified.class));
            return 0;
        }
    }

    public static final BlockHandler BLOCK_HANDLER = new BlockHandler();
    public static final EntityHandler ENTITY_HANDLER = new EntityHandler();

    static {
        BLOCK_HANDLER.bind(DragonEggBlock.class, BlockHandler::defaultGetter);
        BLOCK_HANDLER.bind(DragonEggBlock.class, BlockHandler::defaultSetter);
    }

    public static LiteralArgumentBuilder<CommandSource> register() {
        return Commands.literal("type").requires(source -> source.hasPermission(3))
                .then(Commands.literal("block").then(BLOCK_HANDLER.load(Commands.argument("pos", BlockPosArgument.blockPos()))))
                .then(Commands.literal("entity").then(ENTITY_HANDLER.load(Commands.argument("target", EntityArgument.entity()))));
    }
}
