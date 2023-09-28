package net.dragonmounts3.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.dragonmounts3.init.DMCapabilities.DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN;

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

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            PlayerEntity player = Minecraft.getInstance().player;
            if (player != null) {
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> cooldown.fromNetwork(this));
            }
        });
        context.setPacketHandled(true);
    }
}
