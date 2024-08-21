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
    public static int getFlag(boolean a, boolean b, boolean c, boolean d) {
        return (a ? 0b0001 : 0) | (b ? 0b0010 : 0) | (c ? 0b0100 : 0) | (d ? 0b1000 : 0);
    }
    public final int id;
    public final int flag;

    public CRideDragonPacket(int id) {
        this.id = id;
        this.flag = getFlag(
                Minecraft.getInstance().options.keyJump.isDown(),
                DMKeyBindings.DESCENT.isDown(),
                ClientConfig.INSTANCE.converge_pitch_angle.get(),
                ClientConfig.INSTANCE.converge_yaw_angle.get()
        );
    }

    public CRideDragonPacket(PacketBuffer buffer) {
        this.id = buffer.readVarInt();
        this.flag = buffer.readByte();
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
                    ((ServerDragonEntity) entity).updateInput(this);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
