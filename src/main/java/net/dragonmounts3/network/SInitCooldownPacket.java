package net.dragonmounts3.network;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.network.PacketBuffer;

public class SInitCooldownPacket {
    public final int size;
    public final int[] data;

    public SInitCooldownPacket(Reference2IntOpenHashMap<DragonType> data) {
        this.data = new int[data.size() << 1];
        int index = 0;
        for (ObjectIterator<Reference2IntMap.Entry<DragonType>> it = data.reference2IntEntrySet().fastIterator(); it.hasNext(); ) {
            Reference2IntMap.Entry<DragonType> entry = it.next();
            int id = DragonType.REGISTRY.getID(entry.getKey());
            if (id != -1) {
                this.data[index++] = id;
                this.data[index++] = entry.getIntValue();
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
