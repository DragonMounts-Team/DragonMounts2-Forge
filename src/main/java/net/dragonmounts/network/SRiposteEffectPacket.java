package net.dragonmounts.network;

import net.minecraft.network.PacketBuffer;


public class SRiposteEffectPacket {
    public final int id;
    public final int flag;

    public SRiposteEffectPacket(int id, int flag) {
        this.id = id;
        this.flag = flag;
    }

    public SRiposteEffectPacket(PacketBuffer buffer) {
        this.id = buffer.readVarInt();
        this.flag = buffer.readVarInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.id);
        buffer.writeVarInt(this.flag);
    }
}
