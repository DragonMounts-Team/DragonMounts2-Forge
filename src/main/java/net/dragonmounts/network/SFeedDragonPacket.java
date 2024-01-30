package net.dragonmounts.network;

import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;

public class SFeedDragonPacket extends SSyncDragonAgePacket {
    public final Item item;

    public SFeedDragonPacket(TameableDragonEntity entity, Item item) {
        super(entity);
        this.item = item;
    }

    public SFeedDragonPacket(PacketBuffer buffer) {
        super(buffer);
        this.item = Item.byId(buffer.readVarInt());
    }

    public void encode(PacketBuffer buffer) {
        super.encode(buffer);
        buffer.writeVarInt(Item.getId(this.item));
    }
}
