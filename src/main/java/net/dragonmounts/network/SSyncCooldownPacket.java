package net.dragonmounts.network;

import net.minecraft.network.PacketBuffer;

public class SSyncCooldownPacket {
    public final int id;
    public final int cd;

    public SSyncCooldownPacket(int id, int cd) {
        this.id = id;
        this.cd = cd;
    }

    public SSyncCooldownPacket(PacketBuffer buffer) {
        this.id = buffer.readVarInt();
        this.cd = buffer.readVarInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.id).writeVarInt(this.cd);
    }
}
