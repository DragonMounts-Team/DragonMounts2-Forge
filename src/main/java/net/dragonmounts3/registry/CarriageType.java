package net.dragonmounts3.registry;

import net.dragonmounts3.entity.CarriageEntity;
import net.dragonmounts3.init.CarriageTypes;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

import static net.dragonmounts3.DragonMounts.MOD_ID;

public abstract class CarriageType extends ForgeRegistryEntry<CarriageType> {
    public static final ResourceLocation DEFAULT_KEY = new ResourceLocation(MOD_ID, "oak");
    public static final DeferredRegistry<CarriageType> REGISTRY = new DeferredRegistry<>(MOD_ID, "carriage_type", CarriageType.class, new RegistryBuilder<CarriageType>().setDefaultKey(DEFAULT_KEY));
    public static final IDataSerializer<CarriageType> SERIALIZER = new IDataSerializer<CarriageType>() {
        @Override
        public void write(PacketBuffer buffer, @Nonnull CarriageType value) {
            buffer.writeVarInt(REGISTRY.getID(value));
        }

        @Nonnull
        @Override
        public CarriageType read(@Nonnull PacketBuffer buffer) {
            return REGISTRY.getValue(buffer.readVarInt());
        }

        @Nonnull
        @Override
        public CarriageType copy(@Nonnull CarriageType value) {
            return value;
        }
    };

    public static CarriageType byName(String name) {
        CarriageType type = REGISTRY.getValue(new ResourceLocation(name));
        return type == null ? CarriageTypes.OAK : type;
    }

    public abstract Item getItem(CarriageEntity entity);

    public abstract ResourceLocation getTexture(CarriageEntity entity);

    public final ResourceLocation getSerializedName() {
        ResourceLocation key = this.getRegistryName();
        return key == null ? DEFAULT_KEY : key;
    }

    public static class Default extends CarriageType {
        public final Supplier<? extends Item> item;
        public final ResourceLocation texture;

        public Default(Supplier<? extends Item> item, ResourceLocation texture) {
            this.item = item;
            this.texture = texture;
        }

        @Override
        public Item getItem(CarriageEntity entity) {
            return this.item.get();
        }

        @Override
        public ResourceLocation getTexture(CarriageEntity entity) {
            return this.texture;
        }
    }
}
