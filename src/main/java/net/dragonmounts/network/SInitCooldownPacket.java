package net.dragonmounts.network;

import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SInitCooldownPacket {
    private static final Logger LOGGER = LogManager.getLogger();
    public final int size;
    public final int[] data;

    public SInitCooldownPacket(final int size, final int[] reference, final int[] category, final int[] cooldown) {
        final int[] data = new int[size << 1];
        int index = 0;
        for (int i = 0, j, k; i < size; ++i) {
            if ((k = category[j = reference[i]]) != -1) {
                data[index++] = k;
                data[index++] = cooldown[j];
            } else {
                LOGGER.warn("Unexpected entry at ${}", i);
            }
        }
        this.data = data;
        this.size = index;
    }

    public SInitCooldownPacket(PacketBuffer buffer) {
        final int maxSize = buffer.readVarInt();
        final int[] data = new int[maxSize];
        int i = 0;
        while (i < maxSize && buffer.isReadable()) {
            data[i++] = buffer.readVarInt();
        }
        this.size = i;
        this.data = data;
        if (i != maxSize) {
            LOGGER.warn("Missed entry detected.");
        }
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.size);
        for (int i = 0; i < this.size; ++i) {
            buffer.writeVarInt(this.data[i]);
        }
    }
}
