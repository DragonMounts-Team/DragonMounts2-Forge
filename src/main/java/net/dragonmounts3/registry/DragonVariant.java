package net.dragonmounts3.registry;

import net.dragonmounts3.api.IDragonTypified;
import net.dragonmounts3.client.variant.VariantAppearance;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Function;

import static net.dragonmounts3.DragonMounts.MOD_ID;
import static net.dragonmounts3.DragonMounts.prefix;

public class DragonVariant extends ForgeRegistryEntry<DragonVariant> implements IDragonTypified {
    public static final String DATA_PARAMETER_KEY = "Variant";
    public static final ResourceLocation DEFAULT_KEY = prefix("ender_female");
    public static final DragonVariantRegistry REGISTRY = new DragonVariantRegistry(MOD_ID, "dragon_variant", new RegistryBuilder<DragonVariant>().setDefaultKey(DEFAULT_KEY));
    public static final IDataSerializer<DragonVariant> SERIALIZER = new IDataSerializer<DragonVariant>() {
        public void write(PacketBuffer buffer, @Nonnull DragonVariant value) {
            buffer.writeVarInt(REGISTRY.getID(value));
        }

        @Nonnull
        public DragonVariant read(@Nonnull PacketBuffer buffer) {
            return REGISTRY.getValue(buffer.readVarInt());
        }

        @Nonnull
        public DragonVariant copy(@Nonnull DragonVariant value) {
            return value;
        }
    };

    public static DragonVariant byName(String name) {
        return REGISTRY.getValue(new ResourceLocation(name));
    }

    public final DragonType type;
    private VariantAppearance appearance;

    public DragonVariant(DragonType type) {
        this.type = type;
    }

    public final ResourceLocation getSerializedName() {
        ResourceLocation key = this.getRegistryName();
        return key == null ? DEFAULT_KEY : key;
    }

    @Override
    public final DragonType getDragonType() {
        return this.type;
    }

    public VariantAppearance getAppearance(VariantAppearance defaultValue) {
        return this.appearance == null ? defaultValue : this.appearance;
    }

    @SuppressWarnings("UnusedReturnValue")
    public VariantAppearance setAppearance(VariantAppearance value) {
        VariantAppearance old = this.appearance;
        this.appearance = value;
        return old;
    }

    public static class DragonVariantRegistry extends DeferredRegistry<DragonVariant> implements IForgeRegistry.AddCallback<DragonVariant>, IForgeRegistry.ClearCallback<DragonVariant> {
        protected static final Function<DragonType, ArrayList<DragonVariant>> CREATE_ARRAY = k -> new ArrayList<>();
        protected final HashMap<DragonType, ArrayList<DragonVariant>> map = new HashMap<>();

        public DragonVariantRegistry(String namespace, String name, RegistryBuilder<DragonVariant> builder) {
            super(namespace, name, DragonVariant.class, builder);
        }

        public DragonVariant getValue(DragonType type, Random random) {
            ArrayList<DragonVariant> list = this.map.computeIfAbsent(type, CREATE_ARRAY);
            int size = list.size();
            return size == 0 ? this.getValue(DEFAULT_KEY) : list.get(random.nextInt(size));
        }

        public DragonVariant getNext(DragonVariant variant) {
            ArrayList<DragonVariant> list = this.map.computeIfAbsent(variant.type, CREATE_ARRAY);
            int end = list.size();
            if (end-- == 0) return variant;
            DragonVariant first = list.get(0);
            DragonVariant cache = first;
            int i = 1;
            while (i < end) {
                if (cache == variant) return list.get(i);
                cache = list.get(i++);
            }
            return cache == variant ? list.get(i) : first;
        }

        @Override
        public void onAdd(IForgeRegistryInternal<DragonVariant> owner, RegistryManager stage, int id, DragonVariant obj, @Nullable DragonVariant oldObj) {
            if (owner == this.registry) {//public -> protected
                ArrayList<DragonVariant> list;
                if (oldObj != null) {
                    list = this.map.computeIfAbsent(oldObj.type, CREATE_ARRAY);
                    list.remove(oldObj);
                }
                list = this.map.computeIfAbsent(obj.type, CREATE_ARRAY);
                list.add(obj);
            }
        }

        @Override
        public void onClear(IForgeRegistryInternal<DragonVariant> owner, RegistryManager stage) {
            if (owner == this.registry) {//public -> protected
                this.map.clear();
            }
        }
    }
}
