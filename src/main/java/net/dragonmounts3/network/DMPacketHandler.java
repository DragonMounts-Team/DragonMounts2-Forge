package net.dragonmounts3.network;

import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import static net.dragonmounts3.DragonMounts.prefix;

public class DMPacketHandler {
    private static final String PROTOCOL_VERSION = "Test";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            prefix("default"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        CHANNEL.registerMessage(0, SSyncDragonAgePacket.class, SSyncDragonAgePacket::encode, SSyncDragonAgePacket::new, SSyncDragonAgePacket::handle);
        CHANNEL.registerMessage(1, SFeedDragonPacket.class, SFeedDragonPacket::encode, SFeedDragonPacket::new, SFeedDragonPacket::handle);
        CHANNEL.registerMessage(2, SShakeDragonEggPacket.class, SShakeDragonEggPacket::encode, SShakeDragonEggPacket::new, SShakeDragonEggPacket::handle);
        CHANNEL.registerMessage(3, SSyncCooldownPacket.class, SSyncCooldownPacket::encode, SSyncCooldownPacket::new, SSyncCooldownPacket::handle);
        CHANNEL.registerMessage(4, SRiposteEffectPacket.class, SRiposteEffectPacket::encode, SRiposteEffectPacket::new, SRiposteEffectPacket::handle);
        CHANNEL.registerMessage(5, CRideDragonPacket.class, CRideDragonPacket::encode, CRideDragonPacket::new, CRideDragonPacket::handle);
    }
}
