package net.dragonmounts3.capability;

import net.dragonmounts3.network.SSyncCooldownPacket;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
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
            int id = DragonType.REGISTRY.getID(type);
            if (id != -1) {
                CHANNEL.send(PLAYER.with(() -> (ServerPlayerEntity) player), new SSyncCooldownPacket(Collections.singletonList(new SSyncCooldownPacket.Entry(id, cooldown))));
            }
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
            ResourceLocation location = entry.getKey().getRegistryName();
            if (location != null) {
                compound.putInt(location.toString(), entry.getValue());
            }
        }
        return compound;
    }


    @Override
    public void readNBT(Direction side, CompoundNBT nbt) {
        for (Map.Entry<RegistryKey<DragonType>, DragonType> entry : DragonType.REGISTRY.getEntries()) {
            String key = entry.getKey().location().toString();
            if (nbt.contains(key)) {
                this.map.put(entry.getValue(), nbt.getInt(key));
            }
        }
    }

    @Override
    public void fromNetwork(SSyncCooldownPacket packet) {
        for (SSyncCooldownPacket.Entry entry : packet.list) {
            if (entry.id != -1) {
                this.map.put(DragonType.REGISTRY.getValue(entry.id), entry.value);
            }
        }
    }

    @Override
    public SSyncCooldownPacket createPacket() {
        ArrayList<SSyncCooldownPacket.Entry> list = new ArrayList<>();
        for (Map.Entry<DragonType, Integer> entry : this.map.entrySet()) {
            int id = DragonType.REGISTRY.getID(entry.getKey());
            if (id != -1) {
                list.add(new SSyncCooldownPacket.Entry(id, entry.getValue()));
            }
        }
        return list.isEmpty() ? null : new SSyncCooldownPacket(list);
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
