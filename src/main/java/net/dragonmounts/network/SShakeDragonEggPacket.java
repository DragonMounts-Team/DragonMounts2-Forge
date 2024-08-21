package net.dragonmounts.network;

import net.minecraft.network.PacketBuffer;

public class SShakeDragonEggPacket {
    public final int id;
    public final int axis;
    public final int amplitude;
    public final boolean mirror;
    public final boolean crack;

    public SShakeDragonEggPacket(int id, int amplitude, int axis, boolean mirror, boolean crack) {
        this.id = id;
        this.amplitude = amplitude;
        this.axis = axis;
        this.mirror = mirror;
        this.crack = crack;
    }

    public SShakeDragonEggPacket(PacketBuffer buffer) {
        this.id = buffer.readVarInt();
        this.amplitude = buffer.readByte();
        this.axis = Byte.toUnsignedInt(buffer.readByte());
        int flag = buffer.readByte();
        this.mirror = (flag & 0b10) == 0b10;
        this.crack = (flag & 0b01) == 0b01;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.id).writeByte(this.amplitude).writeByte(this.axis).writeByte(
                (this.mirror ? 0b10 : 0) | (this.crack ? 0b01 : 0)
        );
    }
}
