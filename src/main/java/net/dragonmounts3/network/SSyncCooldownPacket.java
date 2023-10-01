package net.dragonmounts3.network;

import net.minecraft.network.PacketBuffer;

import java.util.ArrayList;
import java.util.List;

public class SSyncCooldownPacket {
    public static class Entry {
        public int id;
        public int value;

        public Entry(int id, int value) {
            this.id = id;
            this.value = value;
        }
    }

    public final List<Entry> list;

    public SSyncCooldownPacket(List<Entry> list) {
        this.list = list;
    }

    public SSyncCooldownPacket(PacketBuffer buffer) {
        this.list = new ArrayList<>();
        while (buffer.readableBytes() != 0) {
            this.list.add(new Entry(buffer.readVarInt(), buffer.readVarInt()));
        }
    }

    public void encode(PacketBuffer buffer) {
        for (Entry entry : this.list) {
            buffer.writeVarInt(entry.id);
            buffer.writeVarInt(entry.value);
        }
    }
}
