package net.dragonmounts3.network;

import net.dragonmounts3.entity.dragon.HatchableDragonEggEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SShakeDragonEggPacket {
    private final int id;
    public float axis;
    public int amplitude;
    public boolean particle;

    public SShakeDragonEggPacket(int id, float axis, int amplitude, boolean particle) {
        this.id = id;
        this.axis = axis;
        this.amplitude = amplitude;
        this.particle = particle;
    }

    public SShakeDragonEggPacket(PacketBuffer buffer) {
        this.id = buffer.readVarInt();
        this.axis = buffer.readFloat();
        this.amplitude = buffer.readVarInt();
        this.particle = buffer.readBoolean();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.id);
        buffer.writeFloat(this.axis);
        buffer.writeVarInt(this.amplitude);
        buffer.writeBoolean(this.particle);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            World level = Minecraft.getInstance().level;
            if (level == null) return;
            Entity entity = level.getEntity(this.id);
            if (entity instanceof HatchableDragonEggEntity) {
                ((HatchableDragonEggEntity) entity).applyPacket(this);
            }
        });
        context.setPacketHandled(true);
    }

}
