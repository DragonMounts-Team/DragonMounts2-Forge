package net.dragonmounts3.network;

import net.minecraft.network.PacketBuffer;


public class SRiposteEffectPacket {
    public final int id;
    public int flag = 0;

    public SRiposteEffectPacket(int id) {
        this.id = id;
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
