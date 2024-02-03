package net.dragonmounts.registry;

import com.google.common.collect.ImmutableMultimap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.dragonmounts.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts.init.DragonTypes;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import static net.dragonmounts.DragonMounts.MOD_ID;

public class DragonType extends ForgeRegistryEntry<DragonType> {
    public static final String DATA_PARAMETER_KEY = "DragonType";
    public static final ResourceLocation DEFAULT_KEY = new ResourceLocation(MOD_ID, "ender");
    public static final DeferredRegistry<DragonType> REGISTRY = new DeferredRegistry<>(MOD_ID, "dragon_type", DragonType.class, new RegistryBuilder<DragonType>().setDefaultKey(DEFAULT_KEY));
    public static final IDataSerializer<DragonType> SERIALIZER = new IDataSerializer<DragonType>() {
        @Override
        public void write(PacketBuffer buffer, @Nonnull DragonType value) {
            buffer.writeVarInt(REGISTRY.getID(value));
        }

        @Nonnull
        @Override
        public DragonType read(@Nonnull PacketBuffer buffer) {
            return REGISTRY.getValue(buffer.readVarInt());
        }

        @Nonnull
        @Override
        public DragonType copy(@Nonnull DragonType value) {
            return value;
        }
    };
    public static final Predicate<HatchableDragonEggEntity> DEFAULT_ENVIRONMENT_PREDICATE = egg -> false;
    public static final BiFunction<Integer, Boolean, Vector3d> DEFAULT_PASSENGER_OFFSET = (index, sitting) -> {
        double yOffset = sitting ? 3.4 : 4.4;
        double yOffset2 = sitting ? 2.1 : 2.5; // maybe not needed
        // dragon position is the middle of the model, and the saddle is on
        // the shoulders, so move player forwards on Z axis relative to the
        // dragon's rotation to fix that
        switch (index) {
            case 1:
                return new Vector3d(0.6, yOffset, 0.1);
            case 2:
                return new Vector3d(-0.6, yOffset, 0.1);
            case 3:
                return new Vector3d(1.6, yOffset2, 0.2);
            case 4:
                return new Vector3d(-1.6, yOffset2, 0.2);
            default:
                return new Vector3d(0, yOffset, 2.2);
        }
    };

    public static DragonType byName(String name) {
        DragonType type = REGISTRY.getValue(new ResourceLocation(name));
        return type == null ? DragonTypes.ENDER : type;
    }

    public final int color;
    public final boolean convertible;
    public final boolean isSkeleton;
    public final ImmutableMultimap<Attribute, AttributeModifier> attributes;
    public final Predicate<HatchableDragonEggEntity> isHabitatEnvironment;
    public final BiFunction<Integer, Boolean, Vector3d> passengerOffset;
    public final IParticleData sneezeParticle;
    public final IParticleData eggParticle;
    public final DragonVariant.Manager variants = new DragonVariant.Manager(this);
    private final Reference2ObjectOpenHashMap<Class<?>, Object> map = new Reference2ObjectOpenHashMap<>();
    private final Style style;
    private final Set<DamageSource> immunities;
    private final Set<Block> blocks;
    private final List<RegistryKey<Biome>> biomes;
    @Nullable
    private String translationKey;
    @Nullable
    private ResourceLocation lootTable;

    public DragonType(Properties properties) {
        this.color = properties.color;
        this.convertible = properties.convertible;
        this.isSkeleton = properties.isSkeleton;
        this.style = Style.EMPTY.withColor(Color.fromRgb(this.color));
        this.attributes = properties.attributes.build();
        this.immunities = new HashSet<>(properties.immunities);
        this.blocks = new HashSet<>(properties.blocks);
        this.biomes = new ArrayList<>();
        this.biomes.addAll(properties.biomes);
        this.sneezeParticle = properties.sneezeParticle;
        this.eggParticle = properties.eggParticle;
        this.passengerOffset = properties.passengerOffset;
        this.isHabitatEnvironment = properties.isHabitatEnvironment;
    }

    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("dragon_type", this.getSerializedName());
        }
        return this.translationKey;
    }

    public ResourceLocation getSerializedName() {
        ResourceLocation key = this.getRegistryName();
        return key == null ? DEFAULT_KEY : key;
    }

    public TranslationTextComponent getName() {
        return (TranslationTextComponent) new TranslationTextComponent(this.getTranslationKey()).withStyle(this.style);
    }

    public TranslationTextComponent getFormattedName(String pattern) {
        return new TranslationTextComponent(pattern, new TranslationTextComponent(this.getTranslationKey()));
    }

    public ResourceLocation getLootTable() {
        if (this.lootTable == null) {
            ResourceLocation key = this.getSerializedName();
            this.lootTable = new ResourceLocation(key.getNamespace(), "entities/dragon/" + key.getPath());
        }
        return this.lootTable;
    }

    public boolean isInvulnerableTo(DamageSource source) {
        return !this.immunities.isEmpty() && this.immunities.contains(source);
    }

    public boolean isHabitat(Block block) {
        return !this.blocks.isEmpty() && this.blocks.contains(block);
    }

    public boolean isHabitat(@Nullable RegistryKey<Biome> biome) {
        return biome != null && !this.biomes.isEmpty() && this.biomes.contains(biome);
    }

    @SuppressWarnings("UnusedReturnValue")
    public <T> T bindInstance(Class<T> clazz, T instance) {
        return clazz.cast(this.map.put(clazz, instance));
    }

    public <T> T getInstance(Class<T> clazz, T defaultValue) {
        return clazz.cast(this.map.getOrDefault(clazz, defaultValue));
    }

    public static class Properties {
        protected static final UUID MODIFIER_UUID = UUID.fromString("12e4cc82-db6d-5676-afc5-86498f0f6464");
        public final ImmutableMultimap.Builder<Attribute, AttributeModifier> attributes = ImmutableMultimap.builder();
        public final int color;
        public final Set<DamageSource> immunities = new HashSet<>();
        public final Set<Block> blocks = new HashSet<>();
        public final Set<RegistryKey<Biome>> biomes = new HashSet<>();
        public boolean convertible = true;
        public boolean isSkeleton = false;
        public IParticleData sneezeParticle = ParticleTypes.LARGE_SMOKE;
        public IParticleData eggParticle = ParticleTypes.MYCELIUM;
        public BiFunction<Integer, Boolean, Vector3d> passengerOffset = DragonType.DEFAULT_PASSENGER_OFFSET;
        public Predicate<HatchableDragonEggEntity> isHabitatEnvironment = DragonType.DEFAULT_ENVIRONMENT_PREDICATE;

        public Properties(int color) {
            this.color = color;
            // ignore suffocation damage
            this.addImmunity(DamageSource.ON_FIRE).addImmunity(DamageSource.IN_FIRE)
                    .addImmunity(DamageSource.HOT_FLOOR)
                    .addImmunity(DamageSource.LAVA)
                    .addImmunity(DamageSource.DROWN)
                    .addImmunity(DamageSource.IN_WALL)
                    .addImmunity(DamageSource.CACTUS) // assume that cactus needles don't do much damage to animals with horned scales
                    .addImmunity(DamageSource.DRAGON_BREATH); // ignore damage from vanilla ender dragon. I kinda disabled this because it wouldn't make any sense, feel free to re enable
        }

        public Properties notConvertible() {
            this.convertible = false;
            return this;
        }

        public Properties isSkeleton() {
            this.isSkeleton = true;
            return this;
        }

        public Properties putAttributeModifier(Attribute attribute, String name, double value, AttributeModifier.Operation operation) {
            this.attributes.put(attribute, new AttributeModifier(MODIFIER_UUID, name, value, operation));
            return this;
        }

        public Properties addImmunity(DamageSource source) {
            this.immunities.add(source);
            return this;
        }

        public Properties addHabitat(Block block) {
            this.blocks.add(block);
            return this;
        }

        public Properties addHabitat(RegistryKey<Biome> block) {
            this.biomes.add(block);
            return this;
        }

        public Properties setSneezeParticle(IParticleData particle) {
            this.sneezeParticle = particle;
            return this;
        }

        public Properties setEggParticle(IParticleData particle) {
            this.eggParticle = particle;
            return this;
        }

        public Properties setPassengerOffset(BiFunction<Integer, Boolean, Vector3d> passengerOffset) {
            this.passengerOffset = passengerOffset;
            return this;
        }

        public Properties setEnvironmentPredicate(Predicate<HatchableDragonEggEntity> predicate) {
            this.isHabitatEnvironment = predicate;
            return this;
        }
    }
}
