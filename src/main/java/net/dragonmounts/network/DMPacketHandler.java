package net.dragonmounts.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.dragonmounts.config.ServerConfig;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SCustomPayloadPlayPacket;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

import static net.dragonmounts.DragonMounts.makeId;

public class DMPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            makeId("default"),
            PROTOCOL_VERSION::toString,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void initClient() {
        CHANNEL.registerMessage(0, SSyncDragonAgePacket.class, SSyncDragonAgePacket::encode, SSyncDragonAgePacket::new, ClientHandler::handle);
        CHANNEL.registerMessage(1, SSyncEggAgePacket.class, SSyncEggAgePacket::encode, SSyncEggAgePacket::new, ClientHandler::handle);
        CHANNEL.registerMessage(2, SFeedDragonPacket.class, SFeedDragonPacket::encode, SFeedDragonPacket::new, ClientHandler::handle);
        CHANNEL.registerMessage(3, SShakeDragonEggPacket.class, SShakeDragonEggPacket::encode, SShakeDragonEggPacket::new, ClientHandler::handle);
        CHANNEL.registerMessage(4, SInitCooldownPacket.class, SInitCooldownPacket::encode, SInitCooldownPacket::new, ClientHandler::handle);
        CHANNEL.registerMessage(5, SSyncCooldownPacket.class, SSyncCooldownPacket::encode, SSyncCooldownPacket::new, ClientHandler::handle);
        CHANNEL.registerMessage(6, SRiposteEffectPacket.class, SRiposteEffectPacket::encode, SRiposteEffectPacket::new, ClientHandler::handle);
        CHANNEL.registerMessage(7, CRideDragonPacket.class, CRideDragonPacket::encode, CRideDragonPacket::new, CRideDragonPacket::handle);
    }

    private static <T> void placeholder(T packet, Supplier<NetworkEvent.Context> supplier) {}

    public static void initServer() {
        CHANNEL.registerMessage(0, SSyncDragonAgePacket.class, SSyncDragonAgePacket::encode, SSyncDragonAgePacket::new, DMPacketHandler::placeholder);
        CHANNEL.registerMessage(1, SSyncEggAgePacket.class, SSyncEggAgePacket::encode, SSyncEggAgePacket::new, DMPacketHandler::placeholder);
        CHANNEL.registerMessage(2, SFeedDragonPacket.class, SFeedDragonPacket::encode, SFeedDragonPacket::new, DMPacketHandler::placeholder);
        CHANNEL.registerMessage(3, SShakeDragonEggPacket.class, SShakeDragonEggPacket::encode, SShakeDragonEggPacket::new, DMPacketHandler::placeholder);
        CHANNEL.registerMessage(4, SInitCooldownPacket.class, SInitCooldownPacket::encode, SInitCooldownPacket::new, DMPacketHandler::placeholder);
        CHANNEL.registerMessage(5, SSyncCooldownPacket.class, SSyncCooldownPacket::encode, SSyncCooldownPacket::new, DMPacketHandler::placeholder);
        CHANNEL.registerMessage(6, SRiposteEffectPacket.class, SRiposteEffectPacket::encode, SRiposteEffectPacket::new, DMPacketHandler::placeholder);
        CHANNEL.registerMessage(7, CRideDragonPacket.class, CRideDragonPacket::encode, CRideDragonPacket::new, CRideDragonPacket::handle);
    }

    public static <T extends PathPoint> void writePathPoint(ByteBuf buffer, Collection<T> nodes) {
        if (nodes == null) {
            buffer.writeInt(0);
            return;
        }
        buffer.writeInt(nodes.size());
        for (T node : nodes) {
            buffer.writeInt(node.x).writeInt(node.y).writeInt(node.z)
                    .writeFloat(node.walkedDistance)
                    .writeFloat(node.costMalus)
                    .writeBoolean(node.closed)
                    .writeInt(node.type.ordinal())
                    .writeFloat(node.f);
        }
    }

    public static void trySendPathFindingPacket(World level, MobEntity mob, Path path, float distance) {
        if (!ServerConfig.INSTANCE.debug.get()) return;
        try {
            BlockPos pos = path.getTarget();
            //noinspection VulnerableCodeUsages
            PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
            buffer.writeBoolean(path.canReach());
            buffer.writeInt(mob.getId());
            buffer.writeFloat(distance);
            buffer.writeInt(path.getNextNodeIndex());
            writePathPoint(buffer, path.targetNodes);
            buffer.writeInt(pos.getX()).writeInt(pos.getY()).writeInt(pos.getZ());
            writePathPoint(buffer, path.nodes);
            writePathPoint(buffer, Arrays.asList(path.getOpenSet()));
            writePathPoint(buffer, Arrays.asList(path.getClosedSet()));
            IPacket<?> packet = new SCustomPayloadPlayPacket(SCustomPayloadPlayPacket.DEBUG_PATHFINDING_PACKET, buffer);
            for (PlayerEntity player : level.players()) {
                ((ServerPlayerEntity) player).connection.send(packet);
            }
        } catch (Throwable ignored) {}
    }
}
