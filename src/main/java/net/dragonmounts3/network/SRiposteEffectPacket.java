package net.dragonmounts3.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;


public class SRiposteEffectPacket {
    public final int id;
    public int flag = 0;

    public SRiposteEffectPacket(int id) {
        this.id = id;
    }

    public SRiposteEffectPacket(PacketBuffer buffer) {
        this.id = buffer.readVarInt();
        this.flag = buffer.readVarInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.id);
        buffer.writeVarInt(this.flag);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            World level = minecraft.level;
            if (level == null) return;
            Entity entity = level.getEntity(this.id);
            if (entity == null) return;
            double x = entity.getX();
            double z = entity.getZ();
            if ((this.flag & 0B01) == 0B01) {
                double y = entity.getY() + 0.1;
                for (int i = -30; i < 31; ++i) {
                    level.addParticle(ParticleTypes.CLOUD, false, x, y, z, Math.sin(i), 0, Math.cos(i));
                }
                level.playSound(minecraft.player, entity.blockPosition(), SoundEvents.GRASS_BREAK, SoundCategory.BLOCKS, 0.46F, 1.0F);

            }
            if ((this.flag & 0B10) == 0B10) {
                double y = entity.getY() + 1;
                for (int i = -27; i < 28; ++i) {
                    level.addParticle(ParticleTypes.FLAME, x, y, z, Math.sin(i) / 3, 0, Math.cos(i) / 3);
                }
                level.playSound(minecraft.player, entity.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundCategory.NEUTRAL, 0.46F, 1.0F);

            }
        });
        context.setPacketHandled(true);
    }
}
