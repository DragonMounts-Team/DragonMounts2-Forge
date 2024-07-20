package net.dragonmounts.network;

import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

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
}
