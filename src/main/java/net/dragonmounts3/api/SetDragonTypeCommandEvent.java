package net.dragonmounts3.api;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.eventbus.api.Event;

public class SetDragonTypeCommandEvent {
    public static class Block extends Event {
        private final CommandContext<CommandSource> context;
        private final BlockPos pos;
        private final DragonType type;
        private BlockState state;
        private boolean succeed;

        public Block(CommandContext<CommandSource> context, BlockPos pos, DragonType type, BlockState state, boolean succeed) {
            this.context = context;
            this.pos = pos;
            this.type = type;
            this.state = state;
            this.succeed = succeed;
        }

        public CommandContext<CommandSource> getContext() {
            return this.context;
        }

        public DragonType getType() {
            return this.type;
        }

        public BlockPos getPos() {
            return this.pos;
        }

        public BlockState getState() {
            return this.state;
        }

        public void setState(BlockState state) {
            this.succeed = state != null;
            this.state = state;
        }

        public boolean isSucceed() {
            return this.succeed;
        }
    }
}
