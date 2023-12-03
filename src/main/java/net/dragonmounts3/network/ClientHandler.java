package net.dragonmounts3.network;

import net.dragonmounts3.api.IDragonFood;
import net.dragonmounts3.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.registry.DragonType;
import net.dragonmounts3.util.DragonFood;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static net.dragonmounts3.init.DMCapabilities.DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN;

public class ClientHandler {
    private static final IDragonFood DEFAULT_DRAGON_FOOD_IMPL = (dragon, player, stack, hand) -> {};
    public static void handle(SFeedDragonPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            World level = Minecraft.getInstance().level;
            if (level == null) return;
            Entity entity = level.getEntity(packet.id);
            if (entity instanceof TameableDragonEntity) {
                TameableDragonEntity dragon = (TameableDragonEntity) entity;
                dragon.applyPacket(packet);
                DragonFood.get(packet.item, DEFAULT_DRAGON_FOOD_IMPL).act(dragon, packet.item);
            }
        });
        context.setPacketHandled(true);
    }

    public static void handle(SInitCooldownPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            PlayerEntity player = Minecraft.getInstance().player;
            if (player != null) {
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> cooldown.init(packet));
            }
        });
        context.setPacketHandled(true);
    }

    public static void handle(SRiposteEffectPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            World level = minecraft.level;
            if (level == null) return;
            Entity entity = level.getEntity(packet.id);
            if (entity == null) return;
            double x = entity.getX();
            double z = entity.getZ();
            if ((packet.flag & 0b01) == 0b01) {
                double y = entity.getY() + 0.1;
                for (int i = -30; i < 31; ++i) {
                    level.addParticle(ParticleTypes.CLOUD, false, x, y, z, Math.sin(i), 0, Math.cos(i));
                }
                level.playSound(minecraft.player, entity.blockPosition(), SoundEvents.GRASS_BREAK, SoundCategory.BLOCKS, 0.46F, 1.0F);
            }
            if ((packet.flag & 0b10) == 0b10) {
                double y = entity.getY() + 1;
                for (int i = -27; i < 28; ++i) {
                    level.addParticle(ParticleTypes.FLAME, x, y, z, Math.sin(i) / 3, 0, Math.cos(i) / 3);
                }
                level.playSound(minecraft.player, entity.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundCategory.NEUTRAL, 0.46F, 1.0F);
            }
        });
        context.setPacketHandled(true);
    }

    public static void handle(SShakeDragonEggPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            World level = Minecraft.getInstance().level;
            if (level == null) return;
            Entity entity = level.getEntity(packet.id);
            if (entity instanceof HatchableDragonEggEntity) {
                ((HatchableDragonEggEntity) entity).applyPacket(packet);
            }
        });
        context.setPacketHandled(true);
    }

    public static void handle(SSyncCooldownPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            PlayerEntity player = Minecraft.getInstance().player;
            if (player != null) {
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN)
                        .ifPresent(cooldown -> cooldown.set(DragonType.REGISTRY.getValue(packet.id), packet.cd));
            }
        });
        context.setPacketHandled(true);
    }

    public static void handle(SSyncDragonAgePacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            World level = Minecraft.getInstance().level;
            if (level == null) return;
            Entity entity = level.getEntity(packet.id);
            if (entity instanceof TameableDragonEntity) {
                ((TameableDragonEntity) entity).applyPacket(packet);
            }
        });
        context.setPacketHandled(true);
    }
}
