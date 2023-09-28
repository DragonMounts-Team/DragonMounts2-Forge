package net.dragonmounts3.registry;

import net.dragonmounts3.api.IDragonTypified;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.init.DragonVariants;
import net.minecraft.client.renderer.RenderType;
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

public abstract class DragonVariant extends ForgeRegistryEntry<DragonVariant> implements IDragonTypified {
    public static final String DATA_PARAMETER_KEY = "Variant";
    public final static String TEXTURES_ROOT = "textures/entities/dragon/";
    public static final ResourceLocation DEFAULT_KEY = prefix("ender_female");
    public final static ResourceLocation DEFAULT_CHEST = prefix(TEXTURES_ROOT + "chest.png");
    public final static ResourceLocation DEFAULT_SADDLE = prefix(TEXTURES_ROOT + "saddle.png");
    public final static ResourceLocation DEFAULT_DISSOLVE = prefix(TEXTURES_ROOT + "dissolve.png");
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
        DragonVariant variant = REGISTRY.getValue(new ResourceLocation(name));
        return variant == null ? DragonVariants.AETHER_FEMALE : variant;
    }

    public final DragonType type;
    public final float positionScale;
    public final float renderScale;

    public DragonVariant(DragonType type, float modelScale) {
        this.type = type;
        this.renderScale = modelScale;
        this.positionScale = modelScale / 16.0F;
    }

    public abstract boolean hasTailHorns(@SuppressWarnings("unused") TameableDragonEntity dragon);

    public abstract boolean hasSideTailScale(@SuppressWarnings("unused") TameableDragonEntity dragon);

    public abstract ResourceLocation getBody(@SuppressWarnings("unused") TameableDragonEntity dragon);

    public abstract RenderType getGlow(@SuppressWarnings("unused") TameableDragonEntity dragon);

    public abstract RenderType getDecal(@SuppressWarnings("unused") TameableDragonEntity dragon);

    public abstract RenderType getGlowDecal(@SuppressWarnings("unused") TameableDragonEntity dragon);

    public ResourceLocation getChest(@SuppressWarnings("unused") TameableDragonEntity dragon) {
        return DEFAULT_CHEST;
    }

    public ResourceLocation getSaddle(@SuppressWarnings("unused") TameableDragonEntity dragon) {
        return DEFAULT_SADDLE;
    }

    public RenderType getDissolve(TameableDragonEntity dragon) {
        return RenderType.dragonExplosionAlpha(DEFAULT_DISSOLVE, dragon.deathTime / dragon.getMaxDeathTime());
    }

    public final ResourceLocation getSerializedName() {
        ResourceLocation key = this.getRegistryName();
        return key == null ? DEFAULT_KEY : key;
    }

    @Override
    public final DragonType getDragonType() {
        return this.type;
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
            return size == 0 ? DragonVariants.ENDER_FEMALE : list.get(random.nextInt(size));
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
