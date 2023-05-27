package net.dragonmounts3.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class SUpdateAgePacket {
    private final int id;
    private final int age;

    public SUpdateAgePacket(int id, int age) {
        this.id = id;
        this.age = age;
    }

    public SUpdateAgePacket(AgeableEntity entity) {
        this(entity.getId(), entity.getAge());
    }

    public SUpdateAgePacket(PacketBuffer buffer) {
        this.id = buffer.readVarInt();
        this.age = buffer.readVarInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.id);
        buffer.writeVarInt(this.age);
    }

    public static void send(AgeableEntity entity) {
        DMPacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new SUpdateAgePacket(entity));
    }

    public static void send(ServerPlayerEntity player, AgeableEntity entity) {
        DMPacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SUpdateAgePacket(entity));
    }

    public static void onMessage(SUpdateAgePacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            World level = Minecraft.getInstance().level;
            if (level != null) {
                Entity entity = level.getEntity(packet.id);
                if (entity instanceof AgeableEntity) {
                    ((AgeableEntity) entity).setAge(packet.age);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
