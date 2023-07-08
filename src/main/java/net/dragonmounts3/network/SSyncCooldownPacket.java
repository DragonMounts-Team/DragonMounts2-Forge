package net.dragonmounts3.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static net.dragonmounts3.init.DMCapabilities.DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN;

public class SSyncCooldownPacket {
    private final CompoundNBT nbt;

    public SSyncCooldownPacket(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public SSyncCooldownPacket(PacketBuffer buffer) {
        this.nbt = buffer.readNbt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeNbt(this.nbt);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            PlayerEntity player = Minecraft.getInstance().player;
            if (player != null) {
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> cooldown.readNBT(null, this.nbt));
            }
        });
        context.setPacketHandled(true);
    }
}
