package net.dragonmounts.network;

import net.dragonmounts.DragonMountsConfig;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.init.DMKeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CRideDragonPacket {
    public final int id;
    public boolean climbing;
    public boolean descending;
    public boolean convergePitch;
    public boolean convergeYaw;

    public CRideDragonPacket(int id) {
        this.id = id;
    }

    public CRideDragonPacket(PacketBuffer buffer) {
        this.id = buffer.readVarInt();
        byte flag = buffer.readByte();
        this.climbing = (flag & 0b0001) == 0b0001;
        this.descending = (flag & 0b0010) == 0b0010;
        this.convergePitch = (flag & 0b0100) == 0b0100;
        this.convergeYaw = (flag & 0b1000) == 0b1000;
    }

    public static CRideDragonPacket create(int id) {
        CRideDragonPacket packet = new CRideDragonPacket(id);
        packet.climbing = Minecraft.getInstance().options.keyJump.isDown();
        packet.descending = DMKeyBindings.DESCENT.isDown();
        packet.convergePitch = DragonMountsConfig.CLIENT.converge_pitch_angle.get();
        packet.convergeYaw = DragonMountsConfig.CLIENT.converge_yaw_angle.get();
        return packet;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.id);
        byte flag = 0;
        if (this.climbing) {
            flag |= 0b0001;
        }
        if (this.descending) {
            flag |= 0b0010;
        }
        if (this.convergePitch) {
            flag |= 0b0100;
        }
        if (this.convergeYaw) {
            flag |= 0b1000;
        }
        buffer.writeByte(flag);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            INetHandler handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerPlayNetHandler) {
                Entity entity = ((ServerPlayNetHandler) handler).player.level.getEntity(this.id);
                if (entity instanceof TameableDragonEntity) {
                    ((TameableDragonEntity) entity).playerControlledGoal.applyPacket(this);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
