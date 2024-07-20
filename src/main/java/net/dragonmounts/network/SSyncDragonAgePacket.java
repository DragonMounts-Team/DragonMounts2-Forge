package net.dragonmounts.network;

import net.dragonmounts.entity.dragon.DragonLifeStage;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.minecraft.network.PacketBuffer;

public class SSyncDragonAgePacket {
    public final int id;
    public final int age;
    public final DragonLifeStage stage;

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
        buffer.writeVarInt(this.id).writeVarInt(this.age).writeVarInt(this.stage.ordinal());
    }
}
