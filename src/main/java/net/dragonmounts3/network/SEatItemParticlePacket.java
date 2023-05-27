package net.dragonmounts3.network;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Random;
import java.util.function.Supplier;

public class SEatItemParticlePacket {
    private final int entityId;
    private final int itemId;

    public SEatItemParticlePacket(int entityId, int itemId) {
        this.entityId = entityId;
        this.itemId = itemId;
    }

    public SEatItemParticlePacket(TameableDragonEntity entity, Item item) {
        this(entity.getId(), Item.getId(item));
    }

    public SEatItemParticlePacket(PacketBuffer buffer) {
        this.entityId = buffer.readVarInt();
        this.itemId = buffer.readVarInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeVarInt(this.entityId);
        buffer.writeVarInt(this.itemId);
    }

    public static void send(TameableDragonEntity entity, Item item) {
        DMPacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new SEatItemParticlePacket(entity, item));
    }

    public static void onMessage(SEatItemParticlePacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            World level = Minecraft.getInstance().level;
            if (level != null) {
                Entity entity = level.getEntity(packet.entityId);
                if (entity instanceof TameableDragonEntity) {
                    Random random = level.random;
                    ItemStack stack = new ItemStack(Item.byId(packet.itemId));
                    for (int i = 0; i < 8; ++i) {
                        //TODO: Create particles around the mouth of the dragon
                        Vector3d vector3d = new Vector3d((random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D).xRot((float) (-entity.xRot * Math.PI / 180F)).yRot((float) (-entity.yRot * Math.PI / 180F));
                        level.addParticle(new ItemParticleData(ParticleTypes.ITEM, stack), entity.getX(), entity.getY(), entity.getZ(), vector3d.x, vector3d.y + 0.05D, vector3d.z);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
