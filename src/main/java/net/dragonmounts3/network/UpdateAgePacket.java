package net.dragonmounts3.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class UpdateAgePacket {
    private static final Logger LOGGER = LogManager.getLogger();
    private final int id;
    private final int age;

    public UpdateAgePacket(int id, int age) {
        this.id = id;
        this.age = age;
    }

    public UpdateAgePacket(AgeableEntity entity) {
        this(entity.getId(), entity.getAge());
    }

    public UpdateAgePacket(PacketBuffer buffer) {
        this.id = buffer.readInt();
        this.age = buffer.readInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.id);
        buffer.writeInt(this.age);
    }

    public static void send(AgeableEntity entity) {
        DMPacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new UpdateAgePacket(entity));
    }

    public static void onMessage(UpdateAgePacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        LOGGER.log(Level.DEBUG, packet.id + ":" + packet.age);
        context.enqueueWork(() -> {
            World level = Minecraft.getInstance().level;
            if (level != null) {
                Entity entity = level.getEntity(packet.id);
                if (entity instanceof AgeableEntity) {
                    LOGGER.log(Level.DEBUG, "set");
                    ((AgeableEntity) entity).setAge(packet.age);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
