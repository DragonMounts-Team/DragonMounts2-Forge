package net.dragonmounts3.capability;

import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.network.SSyncCooldownPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static net.dragonmounts3.init.DMCapabilities.DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN;
import static net.dragonmounts3.network.DMPacketHandler.CHANNEL;
import static net.minecraftforge.fml.network.PacketDistributor.PLAYER;

public class DragonTypifiedCooldown implements IDragonTypifiedCooldown {
    protected final HashMap<DragonType, Integer> map = new HashMap<>();
    protected PlayerEntity player = null;

    @Override
    public void bind(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void set(DragonType type, int cooldown) {
        this.map.put(type, cooldown);
        if (this.player != null && !this.player.level.isClientSide) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt(type.getSerializedName(), cooldown);
            CHANNEL.send(PLAYER.with(() -> (ServerPlayerEntity) player), new SSyncCooldownPacket(nbt));
        }
    }

    @Override
    public int get(DragonType type) {
        return this.map.getOrDefault(type, 0);
    }

    @Override
    public void tick() {
        for (Map.Entry<DragonType, Integer> entry : map.entrySet()) {
            int value = entry.getValue() - 1;
            if (value >= 0) {
                this.map.put(entry.getKey(), value);
            }
        }
    }

    @Nullable
    @Override
    public CompoundNBT writeNBT(Direction side) {
        CompoundNBT compound = new CompoundNBT();
        for (Map.Entry<DragonType, Integer> entry : this.map.entrySet()) {
            compound.putInt(entry.getKey().getSerializedName(), entry.getValue());
        }
        return compound;
    }

    @Override
    public void readNBT(Direction side, CompoundNBT nbt) {
        for (DragonType type : DragonType.values()) {
            String key = type.getSerializedName();
            if (nbt.contains(key)) {
                this.map.put(type, nbt.getInt(key));
            }
        }
    }

    public static class Storage implements Capability.IStorage<IDragonTypifiedCooldown> {
        @Nullable
        @Override
        public CompoundNBT writeNBT(Capability<IDragonTypifiedCooldown> capability, IDragonTypifiedCooldown instance, Direction side) {
            return instance.writeNBT(side);
        }

        @Override
        public void readNBT(Capability<IDragonTypifiedCooldown> capability, IDragonTypifiedCooldown instance, Direction side, INBT nbt) {
            instance.readNBT(side, (CompoundNBT) nbt);
        }
    }

    public static class Provider implements ICapabilitySerializable<CompoundNBT> {
        private final IDragonTypifiedCooldown cooldown;

        public Provider(PlayerEntity player) {
            this.cooldown = DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN.getDefaultInstance();
            if (this.cooldown != null) {
                this.cooldown.bind(player);
            }
        }

        @Override
        public CompoundNBT serializeNBT() {
            return (CompoundNBT) DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN.getStorage().writeNBT(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN, cooldown, null);
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN.getStorage().readNBT(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN, this.cooldown, null, nbt);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
            return DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN.orEmpty(capability, LazyOptional.of(() -> this.cooldown));
        }
    }
}
