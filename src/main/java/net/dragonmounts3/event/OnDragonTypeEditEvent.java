package net.dragonmounts3.event;

import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.IDragonTypified;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.eventbus.api.Event;

public abstract class OnDragonTypeEditEvent extends Event implements IDragonTypified {
    public final ServerWorld level;
    protected final DragonType type;

    protected OnDragonTypeEditEvent(ServerWorld level, DragonType type) {
        this.level = level;
        this.type = type;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }

    public static class Block extends OnDragonTypeEditEvent {
        public final BlockPos pos;
        public final BlockState originalState;
        private BlockState newState;

        public Block(
                ServerWorld level,
                DragonType type,
                BlockPos pos,
                BlockState originalState
        ) {
            this(level, type, pos, originalState, originalState);
        }

        public Block(
                ServerWorld level,
                DragonType type,
                BlockPos pos,
                BlockState originalState,
                BlockState newState
        ) {
            super(level, type);
            this.pos = pos;
            this.originalState = originalState;
            this.newState = newState;
        }

        public BlockState getState() {
            return this.newState == null ? Blocks.AIR.defaultBlockState() : this.newState;
        }

        public void setState(BlockState state) {
            this.newState = state;
        }
    }
}
