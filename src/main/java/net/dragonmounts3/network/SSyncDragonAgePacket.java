package net.dragonmounts3.network;

import net.dragonmounts3.entity.dragon.DragonLifeStage;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SSyncDragonAgePacket {
    public final int id;
    public int age;
    public DragonLifeStage stage;

    public SSyncDragonAgePacket(TameableDragonEntity dragon) {
        this.id = dragon.getId();
        this.age = dragon.getAge();
        this.stage = dragon.getLifeStage();
    }

    public SSyncDragonAgePacket(PacketBuffer buffer) {
        this.id = buffer.readVarInt();
        this.age = buffer.readVarInt();
        this.stage = DragonLifeStage.byId(buffer.readVarInt());
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.id);
        buffer.writeVarInt(this.age);
        buffer.writeVarInt(this.stage.ordinal());
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
