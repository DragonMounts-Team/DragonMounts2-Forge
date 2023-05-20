package net.dragonmounts3.network;

import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import static net.dragonmounts3.DragonMounts.prefix;

public class DMPacketHandler {
    private static final String PROTOCOL_VERSION = "Test";
    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            prefix("default"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        CHANNEL.registerMessage(0, SUpdateAgePacket.class, SUpdateAgePacket::encode, SUpdateAgePacket::new, SUpdateAgePacket::onMessage);
    }
}
