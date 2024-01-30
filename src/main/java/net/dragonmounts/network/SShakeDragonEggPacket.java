package net.dragonmounts.network;

import net.minecraft.network.PacketBuffer;

public class SShakeDragonEggPacket {
    public final int id;
    public float axis;
    public int amplitude;
    public boolean particle;

    public SShakeDragonEggPacket(int id, float axis, int amplitude, boolean particle) {
        this.id = id;
        this.axis = axis;
        this.amplitude = amplitude;
        this.particle = particle;
    }

    public SShakeDragonEggPacket(PacketBuffer buffer) {
        this.id = buffer.readVarInt();
        this.axis = buffer.readFloat();
        this.amplitude = buffer.readVarInt();
        this.particle = buffer.readBoolean();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.id);
        buffer.writeFloat(this.axis);
        buffer.writeVarInt(this.amplitude);
        buffer.writeBoolean(this.particle);
    }
}
