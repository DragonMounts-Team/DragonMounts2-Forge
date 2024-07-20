package net.dragonmounts.network;

import net.dragonmounts.config.ClientConfig;
import net.dragonmounts.entity.dragon.ServerDragonEntity;
import net.dragonmounts.init.DMKeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public final class CRideDragonPacket {
    public static byte getFlag(boolean a, boolean b, boolean c, boolean d) {
        return (byte) ((a ? 0b0001 : 0) | (b ? 0b0010 : 0) | (c ? 0b0100 : 0) | (d ? 0b1000 : 0));
    }
    public final int id;
    public final byte flag;
    public final boolean climbing;
    public final boolean descending;
    public final boolean convergePitch;
    public final boolean convergeYaw;

    public CRideDragonPacket(int id) {
        this.id = id;
        this.flag = getFlag(
                this.climbing = Minecraft.getInstance().options.keyJump.isDown(),
                this.descending = DMKeyBindings.DESCENT.isDown(),
                this.convergePitch = ClientConfig.INSTANCE.converge_pitch_angle.get(),
                this.convergeYaw = ClientConfig.INSTANCE.converge_yaw_angle.get()
        );
    }

    public CRideDragonPacket(PacketBuffer buffer) {
        this.id = buffer.readVarInt();
        this.flag = buffer.readByte();
        this.climbing = (this.flag & 0b0001) == 0b0001;
        this.descending = (this.flag & 0b0010) == 0b0010;
        this.convergePitch = (this.flag & 0b0100) == 0b0100;
        this.convergeYaw = (this.flag & 0b1000) == 0b1000;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.id).writeByte(this.flag);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            INetHandler handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerPlayNetHandler) {
                Entity entity = ((ServerPlayNetHandler) handler).player.level.getEntity(this.id);
                if (entity instanceof ServerDragonEntity) {
                    ((ServerDragonEntity) entity).playerControlledGoal.handlePacket(this);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
