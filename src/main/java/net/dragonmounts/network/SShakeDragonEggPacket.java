package net.dragonmounts.network;

import net.minecraft.network.PacketBuffer;

public class SShakeDragonEggPacket {
    public final int id;
    public final float axis;
    public final int amplitude;
    public final boolean particle;

    public SShakeDragonEggPacket(int id, float axis, int amplitude, boolean particle) {
        this.id = id;
        this.axis = axis;
        this.amplitude = amplitude;
        this.particle = particle;
    }

    public SShakeDragonEggPacket(PacketBuffer buffer) {
        this.id = buffer.readVarInt();
        this.amplitude = buffer.readVarInt();
        this.axis = buffer.readFloat();
        this.particle = buffer.readBoolean();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.id).writeVarInt(this.amplitude).writeFloat(this.axis).writeBoolean(this.particle);
    }
}
