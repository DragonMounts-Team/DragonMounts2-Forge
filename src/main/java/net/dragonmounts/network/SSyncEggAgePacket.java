package net.dragonmounts.network;

import net.minecraft.network.PacketBuffer;

public class SSyncEggAgePacket {
    public final int id;
    public final int age;

    public SSyncEggAgePacket(int id, int age) {
        this.id = id;
        this.age = age;
    }

    public SSyncEggAgePacket(PacketBuffer buffer) {
        this.id = buffer.readVarInt();
        this.age = buffer.readVarInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.id);
        buffer.writeVarInt(this.age);
    }
}
