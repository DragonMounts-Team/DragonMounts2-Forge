package net.dragonmounts3.api;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.dragonmounts3.api.variant.DragonVariants;
import net.dragonmounts3.api.variant.VariantManager;
import net.dragonmounts3.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts3.util.DragonTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import static net.dragonmounts3.DragonMounts.MOD_ID;

public class DragonType implements IStringSerializable, Comparable<DragonType> {
    public static final Codec<DragonType> CODEC = Codec.STRING.comapFlatMap(name -> DataResult.success(byName(name)), DragonType::getSerializedName).stable();
    private static final AtomicInteger COUNTER = new AtomicInteger();
    private static final HashMap<String, DragonType> BY_NAME = new HashMap<>();
    public static final String DATA_PARAMETER_KEY = "DragonType";
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

    public static final Predicate<HatchableDragonEggEntity> DEFAULT_ENVIRONMENT_PREDICATE = egg -> false;

    public static final DragonType AETHER = new DragonTypeBuilder(0x0294BD)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.LAPIS_BLOCK)
            .addHabitat(Blocks.LAPIS_ORE)
            .setEnvironmentPredicate(egg -> egg.getY() >= egg.level.getHeight() * 0.625)
            .addVariant(DragonVariants.AETHER_FEMALE)
            .addVariant(DragonVariants.AETHER_MALE)
            .addVariant(DragonVariants.AETHER_NEW)
            .build("aether");
    public static final DragonType ENCHANT = new DragonTypeBuilder(0x8359AE)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.BOOKSHELF)
            .addHabitat(Blocks.ENCHANTING_TABLE)
            .addVariant(DragonVariants.ENCHANT_FEMALE)
            .addVariant(DragonVariants.ENCHANT_MALE)
            .build("enchant");
    public static final DragonType ENDER = new DragonTypeBuilder(0xAB39BE)
            .notConvertible()
            .putAttributeModifier(Attributes.MAX_HEALTH, "DragonTypeBonus", 10.0D, AttributeModifier.Operation.ADDITION)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .setSneezeParticle(ParticleTypes.PORTAL)
            .setEggParticle(ParticleTypes.PORTAL)
            .addVariant(DragonVariants.ENDER_FEMALE)
            .addVariant(DragonVariants.ENDER_MALE)
            .build("ender");
    public static final DragonType FIRE = new DragonTypeBuilder(0x960B0F)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.FIRE)
            //.addHabitat(Blocks.LIT_FURNACE)
            .addHabitat(Blocks.LAVA)
            //.addHabitat(Blocks.FLOWING_LAVA)
            .addVariant(DragonVariants.FIRE_FEMALE)
            .addVariant(DragonVariants.FIRE_MALE)
            .build("fire");
    public static final DragonType FOREST = new DragonTypeBuilder(0x298317)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            //.addHabitat(Blocks.YELLOW_FLOWER)
            //.addHabitat(Blocks.RED_FLOWER)
            .addHabitat(Blocks.MOSSY_COBBLESTONE)
            .addHabitat(Blocks.VINE)
            //.addHabitat(Blocks.SAPLING)
            //.addHabitat(Blocks.LEAVES)
            //.addHabitat(Blocks.LEAVES2)
            .addHabitat(Biomes.JUNGLE)
            .addHabitat(Biomes.JUNGLE_HILLS)
            .addVariant(DragonVariants.FOREST_FEMALE)
            .addVariant(DragonVariants.FOREST_MALE)
            .addVariant(DragonVariants.FOREST_DRY_FEMALE)
            .addVariant(DragonVariants.FOREST_DRY_MALE)
            .addVariant(DragonVariants.FOREST_TAIGA_FEMALE)
            .addVariant(DragonVariants.FOREST_TAIGA_MALE)
            .build("forest");
    public static final DragonType ICE = new DragonTypeBuilder(0x00F2FF)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.SNOW)
            .addHabitat(Blocks.ICE)
            .addHabitat(Blocks.PACKED_ICE)
            .addHabitat(Blocks.FROSTED_ICE)
            .addHabitat(Biomes.FROZEN_OCEAN)
            .addHabitat(Biomes.FROZEN_RIVER)
            //.addHabitat(Biomes.JUNGLE)
            //.addHabitat(Biomes.JUNGLE_HILLS)
            .addVariant(DragonVariants.ICE_FEMALE)
            .addVariant(DragonVariants.ICE_MALE)
            .build("ice");
    public static final DragonType MOONLIGHT = new DragonTypeBuilder(0x2C427C)
            .addHabitat(Blocks.BLUE_GLAZED_TERRACOTTA)
            .addVariant(DragonVariants.MOONLIGHT_FEMALE)
            .addVariant(DragonVariants.MOONLIGHT_MALE)
            .build("moonlight");
    public static final DragonType NETHER = new DragonTypeBuilder(0xE5B81B)
            .putAttributeModifier(Attributes.MAX_HEALTH, "DragonTypeBonus", 5.0D, AttributeModifier.Operation.ADDITION)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            //.addHabitat(Biomes.HELL)
            .setEggParticle(ParticleTypes.DRIPPING_LAVA)
            .addVariant(DragonVariants.NETHER_FEMALE)
            .addVariant(DragonVariants.NETHER_MALE)
            .build("nether");
    public static final DragonType SCULK = new DragonTypeBuilder(0x29DFEB)
            .notConvertible()
            .putAttributeModifier(Attributes.MAX_HEALTH, "DragonTypeBonus", 10.0D, AttributeModifier.Operation.ADDITION)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addVariant(DragonVariants.SCULK)
            .build("sculk");
    public static final DragonType SKELETON = new DragonTypeBuilder(0xFFFFFF)
            .isSkeleton()
            .putAttributeModifier(Attributes.MAX_HEALTH, "DragonTypeBonus", -15.0D, AttributeModifier.Operation.ADDITION)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.BONE_BLOCK)
            .setEnvironmentPredicate(egg -> egg.getY() <= egg.level.getHeight() * 0.25 || egg.level.getRawBrightness(egg.blockPosition(), 0) < 4)
            .addVariant(DragonVariants.SKELETON_FEMALE)
            .addVariant(DragonVariants.SKELETON_MALE)
            .build("skeleton");
    public static final DragonType STORM = new DragonTypeBuilder(0xF5F1E9)
            .addVariant(DragonVariants.STORM_FEMALE)
            .addVariant(DragonVariants.STORM_MALE)
            .build("storm");
    public static final DragonType SUNLIGHT = new DragonTypeBuilder(0xFFDE00)
            .addHabitat(Blocks.GLOWSTONE)
            .addHabitat(Blocks.JACK_O_LANTERN)
            .addHabitat(Blocks.SHROOMLIGHT)
            .addHabitat(Blocks.YELLOW_GLAZED_TERRACOTTA)
            .addVariant(DragonVariants.SUNLIGHT_FEMALE)
            .addVariant(DragonVariants.SUNLIGHT_MALE)
            .build("sunlight");
    public static final DragonType TERRA = new DragonTypeBuilder(0xA56C21)
            .addHabitat(Blocks.TERRACOTTA)
            .addHabitat(Blocks.SAND)
            .addHabitat(Blocks.SANDSTONE)
            .addHabitat(Blocks.SANDSTONE_SLAB)
            .addHabitat(Blocks.SANDSTONE_STAIRS)
            .addHabitat(Blocks.SANDSTONE_WALL)
            .addHabitat(Blocks.RED_SAND)
            .addHabitat(Blocks.RED_SANDSTONE)
            .addHabitat(Blocks.RED_SANDSTONE_SLAB)
            .addHabitat(Blocks.RED_SANDSTONE_STAIRS)
            .addHabitat(Blocks.RED_SANDSTONE_WALL)
            //.addHabitat(Biomes.MESA)
            //.addHabitat(Biomes.MESA_ROCK)
            //.addHabitat(Biomes.MESA_CLEAR_ROCK)
            //.addHabitat(Biomes.MUTATED_MESA_CLEAR_ROCK)
            //.addHabitat(Biomes.MUTATED_MESA_ROCK)
            .addVariant(DragonVariants.TERRA_FEMALE)
            .addVariant(DragonVariants.TERRA_MALE)
            .build("terra");
    public static final DragonType WATER = new DragonTypeBuilder(0x4F69A8)
            .addImmunity(DamageSource.DROWN)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.WATER)
            .addHabitat(Biomes.OCEAN)
            .addHabitat(Biomes.RIVER)
            .addVariant(DragonVariants.WATER_FEMALE)
            .addVariant(DragonVariants.WATER_MALE)
            .build("water");
    public static final DragonType WITHER = new DragonTypeBuilder(0x50260A)
            .notConvertible()
            .isSkeleton()
            .putAttributeModifier(Attributes.MAX_HEALTH, "DragonTypeBonus", -10.0D, AttributeModifier.Operation.ADDITION)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addVariant(DragonVariants.WITHER_FEMALE)
            .addVariant(DragonVariants.WITHER_MALE)
            .build("wither");
    public static final DragonType ZOMBIE = new DragonTypeBuilder(0x5A5602)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.SOUL_SAND)
            .addHabitat(Blocks.SOUL_SAND)
            .addHabitat(Blocks.NETHER_WART_BLOCK)
            .addHabitat(Blocks.WARPED_WART_BLOCK)
            .addVariant(DragonVariants.ZOMBIE_FEMALE)
            .addVariant(DragonVariants.ZOMBIE_MALE)
            .build("zombie");

    public static Collection<DragonType> values() {
        return BY_NAME.values();
    }

    public static DragonType byName(String name) {
        return BY_NAME.getOrDefault(name, ENDER);
    }

    public final int color;
    public final boolean convertible;
    public final boolean isSkeleton;
    public final VariantManager variants;
    public final BiFunction<Integer, Boolean, Vector3d> passengerOffset;
    public final Predicate<HatchableDragonEggEntity> isHabitatEnvironment;
    private final int id = COUNTER.incrementAndGet();
    private final Style style;
    private final String name;
    private final String text;
    private final ImmutableMultimap<Attribute, AttributeModifier> attributes;
    private final Set<DamageSource> immunities;
    private final Set<Block> blocks;
    private final List<RegistryKey<Biome>> biomes;
    private final BasicParticleType sneezeParticle;
    private final BasicParticleType eggParticle;

    public DragonType(String name, DragonTypeBuilder builder) {
        if (BY_NAME.containsKey(name)) {
            throw new IllegalArgumentException();
        }
        this.variants = builder.variants;
        this.color = builder.color;
        this.convertible = builder.convertible;
        this.isSkeleton = builder.isSkeleton;
        this.style = Style.EMPTY.withColor(Color.fromRgb(this.color));
        this.name = name;
        this.text = "dragon.type." + name;
        this.attributes = builder.attributes.build();
        this.immunities = new HashSet<>(builder.immunities);
        this.blocks = new HashSet<>(builder.blocks);
        new ArrayList<>();
        this.biomes = new ArrayList<>();
        this.biomes.addAll(builder.biomes);
        this.sneezeParticle = builder.sneezeParticle;
        this.eggParticle = builder.eggParticle;
        this.passengerOffset = builder.passengerOffset;
        this.isHabitatEnvironment = builder.isHabitatEnvironment;
        BY_NAME.put(name, this);
    }

    public ITextComponent getText() {
        return new TranslationTextComponent(this.text).withStyle(this.style);
    }

    public ITextComponent getTypifiedName(String pattern) {
        return new TranslationTextComponent(pattern, new TranslationTextComponent(this.text));
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        return this.attributes;
    }

    public int getColor() {
        return this.color;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }

    public ResourceLocation createDragonLootTable() {
        return new ResourceLocation(MOD_ID, "entities/dragon/" + this.getSerializedName());
    }

    public boolean isInvulnerableTo(DamageSource source) {
        if (this.immunities.isEmpty()) {
            return false;
        }
        return this.immunities.contains(source);
    }

    public boolean isHabitat(Block block) {
        if (this.blocks.isEmpty()) {
            return false;
        }
        return this.blocks.contains(block);
    }

    public boolean isHabitat(@Nullable RegistryKey<Biome> biome) {
        if (biome == null || this.biomes.isEmpty()) {
            return false;
        }
        return this.biomes.contains(biome);
    }

    public BasicParticleType getSneezeParticle() {
        return this.sneezeParticle;
    }

    public BasicParticleType getEggParticle() {
        return this.eggParticle;
    }

    @Override
    public int compareTo(@Nonnull DragonType other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        DragonType type = (DragonType) o;
        return this.id == type.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}
