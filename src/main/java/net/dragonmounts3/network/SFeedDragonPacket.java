package net.dragonmounts3.network;

import net.dragonmounts3.api.IDragonFood;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.util.DragonFood;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

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

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            World level = Minecraft.getInstance().level;
            if (level == null) return;
            Entity entity = level.getEntity(this.id);
            if (entity instanceof TameableDragonEntity) {
                TameableDragonEntity dragon = (TameableDragonEntity) entity;
                dragon.applyPacket(this);
                IDragonFood food = DragonFood.get(this.item);
                if (food != null) {
                    food.act(dragon, this.item);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
