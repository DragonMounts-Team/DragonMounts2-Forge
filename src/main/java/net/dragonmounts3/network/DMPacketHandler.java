package net.dragonmounts3.network;

import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Supplier;

import static net.dragonmounts3.DragonMounts.prefix;

public class DMPacketHandler {
    private static final String PROTOCOL_VERSION = "Test";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            prefix("default"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void initClient() {
        CHANNEL.registerMessage(0, SSyncDragonAgePacket.class, SSyncDragonAgePacket::encode, SSyncDragonAgePacket::new, ClientHandler::handle);
        CHANNEL.registerMessage(1, SFeedDragonPacket.class, SFeedDragonPacket::encode, SFeedDragonPacket::new, ClientHandler::handle);
        CHANNEL.registerMessage(2, SShakeDragonEggPacket.class, SShakeDragonEggPacket::encode, SShakeDragonEggPacket::new, ClientHandler::handle);
        CHANNEL.registerMessage(3, SSyncCooldownPacket.class, SSyncCooldownPacket::encode, SSyncCooldownPacket::new, ClientHandler::handle);
        CHANNEL.registerMessage(4, SRiposteEffectPacket.class, SRiposteEffectPacket::encode, SRiposteEffectPacket::new, ClientHandler::handle);
        CHANNEL.registerMessage(5, CRideDragonPacket.class, CRideDragonPacket::encode, CRideDragonPacket::new, CRideDragonPacket::handle);
    }

    private static <T> void placeholder(T packet, Supplier<NetworkEvent.Context> supplier) {
    }

    public static void initServer() {
        CHANNEL.registerMessage(0, SSyncDragonAgePacket.class, SSyncDragonAgePacket::encode, SSyncDragonAgePacket::new, DMPacketHandler::placeholder);
        CHANNEL.registerMessage(1, SFeedDragonPacket.class, SFeedDragonPacket::encode, SFeedDragonPacket::new, DMPacketHandler::placeholder);
        CHANNEL.registerMessage(2, SShakeDragonEggPacket.class, SShakeDragonEggPacket::encode, SShakeDragonEggPacket::new, DMPacketHandler::placeholder);
        CHANNEL.registerMessage(3, SSyncCooldownPacket.class, SSyncCooldownPacket::encode, SSyncCooldownPacket::new, DMPacketHandler::placeholder);
        CHANNEL.registerMessage(4, SRiposteEffectPacket.class, SRiposteEffectPacket::encode, SRiposteEffectPacket::new, DMPacketHandler::placeholder);
        CHANNEL.registerMessage(5, CRideDragonPacket.class, CRideDragonPacket::encode, CRideDragonPacket::new, CRideDragonPacket::handle);
    }
}
