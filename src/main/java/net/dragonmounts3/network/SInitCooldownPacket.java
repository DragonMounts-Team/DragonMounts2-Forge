package net.dragonmounts3.network;

import net.minecraft.network.PacketBuffer;

public class SInitCooldownPacket {
    public final int size;
    public final int[] data;

    public SInitCooldownPacket(final int size, final int[] reference, final int[] category, final int[] cooldown) {
        this.data = new int[size << 1];
        int index = 0;
        for (int i = 0, j, k; i < size; ++i) {
            if ((k = category[j = reference[i]]) != -1) {
                this.data[index++] = k;
                this.data[index++] = cooldown[j];
            }
        }
        this.size = index;
    }

    public SInitCooldownPacket(PacketBuffer buffer) {
        this.size = buffer.readVarInt();
        this.data = new int[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.data[i] = buffer.readVarInt();
        }
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.size);
        for (int i = 0; i < this.size; ++i) {
            buffer.writeVarInt(this.data[i]);
        }
    }
}
