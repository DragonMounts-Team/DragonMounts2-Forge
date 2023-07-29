package net.dragonmounts3.network;

import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.variant.AbstractVariant;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SSyncAppearancePacket {
    public final int id;
    public DragonType type;
    public AbstractVariant variant;

    public SSyncAppearancePacket(TameableDragonEntity dragon) {
        this.id = dragon.getId();
        this.type = dragon.getDragonType();
        this.variant = dragon.getVariant();
    }

    public SSyncAppearancePacket(PacketBuffer buffer) {
        this.id = buffer.readVarInt();
        this.type = DragonType.byName(buffer.readUtf());
        this.variant = this.type.variants.get(buffer.readUtf());
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.id);
        buffer.writeUtf(this.type.getSerializedName());
        buffer.writeUtf(this.variant.getSerializedName());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            World level = Minecraft.getInstance().level;
            if (level == null) return;
            Entity entity = level.getEntity(this.id);
            if (entity instanceof TameableDragonEntity) {
                ((TameableDragonEntity) entity).applyPacket(this);
            }
        });
        context.setPacketHandled(true);
    }
}
