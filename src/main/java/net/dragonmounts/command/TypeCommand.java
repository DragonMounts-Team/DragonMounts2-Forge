package net.dragonmounts.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.dragonmounts.api.IDragonTypified;
import net.dragonmounts.block.AbstractDragonHeadBlock;
import net.dragonmounts.block.DragonHeadBlock;
import net.dragonmounts.block.DragonHeadWallBlock;
import net.dragonmounts.block.HatchableDragonEggBlock;
import net.dragonmounts.init.DragonTypes;
import net.dragonmounts.init.DragonVariants;
import net.dragonmounts.registry.DragonType;
import net.dragonmounts.registry.DragonVariant;
import net.minecraft.block.*;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.Map;

import static net.dragonmounts.command.DMCommand.HAS_PERMISSION_LEVEL_3;
import static net.dragonmounts.command.DMCommand.createClassCastException;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.state.properties.BlockStateProperties.ROTATION_16;

public class TypeCommand {
    public abstract static class CommandHandler<A> {
        protected abstract A getArgument(CommandContext<CommandSource> context) throws CommandSyntaxException;

        protected abstract int setType(CommandContext<CommandSource> context, A argument, DragonType type);

        protected abstract int getType(CommandContext<CommandSource> context, A argument);

        public <T> RequiredArgumentBuilder<CommandSource, T> load(RequiredArgumentBuilder<CommandSource, T> builder) {
            for (Map.Entry<RegistryKey<DragonType>, DragonType> entry : DragonType.REGISTRY.getEntries()) {
                builder.then(Commands.literal(entry.getKey().location().toString()).executes(context -> this.setType(context, this.getArgument(context), entry.getValue())));
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

        public static final Setter SETTER_DRAGON_EEG = (block, level, pos, state, type) -> {
            final Block egg = type.getInstance(HatchableDragonEggBlock.class, null);
            return egg == null ? state : egg.defaultBlockState();
        };
        public static final Setter SETTER_DRAGON_HEAD = (block, level, pos, state, type) -> {
            final DragonVariant variant = type.variants.draw(level.random, block == Blocks.DRAGON_HEAD ?
                    DragonVariants.ENDER_FEMALE : block instanceof AbstractDragonHeadBlock ?
                    ((AbstractDragonHeadBlock) block).variant : null
            );
            return variant == null ? state : variant.headBlock.defaultBlockState().setValue(ROTATION_16, state.getValue(ROTATION_16));
        };
        public static final Setter SETTER_DRAGON_HEAD_WALL = (block, level, pos, state, type) -> {
            final DragonVariant variant = type.variants.draw(level.random, block == Blocks.DRAGON_HEAD ?
                    DragonVariants.ENDER_FEMALE : block instanceof AbstractDragonHeadBlock ?
                    ((AbstractDragonHeadBlock) block).variant : null
            );
            return variant == null ? state : variant.headWallBlock.defaultBlockState().setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING));
        };

        private final Reference2ObjectOpenHashMap<Class<? extends Block>, Getter> getters = new Reference2ObjectOpenHashMap<>();
        private final Reference2ObjectOpenHashMap<Class<? extends Block>, Setter> setters = new Reference2ObjectOpenHashMap<>();

        @SuppressWarnings("UnusedReturnValue")
        public Getter bind(Class<? extends Block> clazz, Getter getter) {
            return getter == null ? this.getters.remove(clazz) : this.getters.put(clazz, getter);
        }

        @SuppressWarnings("UnusedReturnValue")
        public Setter bind(Class<? extends Block> clazz, Setter setter) {
            return setter == null ? this.setters.remove(clazz) : this.setters.put(clazz, setter);
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
                    source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.block.set", pos.getX(), pos.getY(), pos.getZ(), type.getName()), true);
                    return 1;
                }
            }
            source.sendFailure(new StringTextComponent("java.lang.NullPointerException: " + clazz.getName() + " has not bound to a handler"));
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
                    source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.block.get", pos.getX(), pos.getY(), pos.getZ(), type.getName()), true);
                    return 1;
                }
            }
            if (block instanceof IDragonTypified) {
                source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.block.get", pos.getX(), pos.getY(), pos.getZ(), ((IDragonTypified) block).getDragonType().getName()), true);
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
            if (entity instanceof IDragonTypified.Mutable) {
                ((IDragonTypified.Mutable) entity).setDragonType(type);
                source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.entity.set", entity.getDisplayName(), type.getName()), true);
                return 1;
            }
            source.sendFailure(createClassCastException(entity, IDragonTypified.Mutable.class));
            return 0;
        }

        @Override
        protected int getType(CommandContext<CommandSource> context, Entity entity) {
            CommandSource source = context.getSource();
            if (entity instanceof IDragonTypified) {
                source.sendSuccess(new TranslationTextComponent("commands.dragonmounts.type.entity.get", entity.getDisplayName(), ((IDragonTypified) entity).getDragonType().getName()), true);
                return 1;
            }
            source.sendFailure(createClassCastException(entity, IDragonTypified.class));
            return 0;
        }
    }

    public static final BlockHandler BLOCK_HANDLER = new BlockHandler();
    public static final EntityHandler ENTITY_HANDLER = new EntityHandler();

    static {
        BLOCK_HANDLER.bind(DragonEggBlock.class, ($_, __, $, $$) -> DragonTypes.ENDER);
        BLOCK_HANDLER.bind(SkullBlock.class, (block, __, $, $$) -> block == Blocks.DRAGON_HEAD ? DragonTypes.ENDER : null);
        BLOCK_HANDLER.bind(WallSkullBlock.class, (block, __, $, $$) -> block == Blocks.DRAGON_WALL_HEAD ? DragonTypes.ENDER : null);
        BLOCK_HANDLER.bind(DragonEggBlock.class, BlockHandler.SETTER_DRAGON_EEG);
        BLOCK_HANDLER.bind(HatchableDragonEggBlock.class, BlockHandler.SETTER_DRAGON_EEG);
        BLOCK_HANDLER.bind(DragonHeadBlock.class, BlockHandler.SETTER_DRAGON_HEAD);
        BLOCK_HANDLER.bind(SkullBlock.class, BlockHandler.SETTER_DRAGON_HEAD);
        BLOCK_HANDLER.bind(DragonHeadWallBlock.class, BlockHandler.SETTER_DRAGON_HEAD_WALL);
        BLOCK_HANDLER.bind(WallSkullBlock.class, BlockHandler.SETTER_DRAGON_HEAD_WALL);
    }

    public static LiteralArgumentBuilder<CommandSource> register() {
        return Commands.literal("type").requires(HAS_PERMISSION_LEVEL_3)
                .then(Commands.literal("block").then(BLOCK_HANDLER.load(Commands.argument("pos", BlockPosArgument.blockPos()))))
                .then(Commands.literal("entity").then(ENTITY_HANDLER.load(Commands.argument("target", EntityArgument.entity()))));
    }
}
