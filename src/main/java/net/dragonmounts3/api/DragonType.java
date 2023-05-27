package net.dragonmounts3.api;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.dragonmounts3.client.DragonResources;
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
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

import javax.annotation.Nonnull;
import java.util.*;

public class DragonType implements IStringSerializable, Comparable<DragonType> {
    private static final HashMap<String, DragonType> BY_NAME = new HashMap<>();
    public static final String DATA_PARAMETER_KEY = "DragonType";
    public static final DragonType AETHER = new Builder(0x0294BD)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.LAPIS_BLOCK)
            .addHabitat(Blocks.LAPIS_ORE)
            .build("aether");
    public static final DragonType ENCHANT = new Builder(0x8359AE)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.BOOKSHELF)
            .addHabitat(Blocks.ENCHANTING_TABLE)
            .build("enchant");
    public static final DragonType ENDER = new Builder(0xAB39BE)
            .putAttributeModifier(Attributes.MAX_HEALTH, "Dragon health adjustment", 10.0D, AttributeModifier.Operation.ADDITION)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .setSneezeParticle(ParticleTypes.PORTAL)
            .setEggParticle(ParticleTypes.PORTAL)
            .build("ender");
    public static final DragonType FIRE = new Builder(0x960B0F)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.FIRE)
            //.addHabitat(Blocks.LIT_FURNACE)
            .addHabitat(Blocks.LAVA)
            //.addHabitat(Blocks.FLOWING_LAVA)
            .build("fire");
    public static final DragonType FOREST = new Builder(0x298317)
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
            .build("forest");
    public static final DragonType ICE = new Builder(0x00F2FF)
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
            .build("ice");
    public static final DragonType MOONLIGHT = new Builder(0x2C427C)
            .addHabitat(Blocks.BLUE_GLAZED_TERRACOTTA)
            .build("moonlight");
    public static final DragonType NETHER = new Builder(0xE5B81B)
            .putAttributeModifier(Attributes.MAX_HEALTH, "Dragon health adjustment", 5.0D, AttributeModifier.Operation.ADDITION)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            //.addHabitat(Biomes.HELL)
            .setEggParticle(ParticleTypes.DRIPPING_LAVA)
            .build("nether");
    public static final DragonType SCULK = new Builder(0x29DFEB)
            .putAttributeModifier(Attributes.MAX_HEALTH, "Dragon health adjustment", 10.0D, AttributeModifier.Operation.ADDITION)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .build("sculk");
    public static final DragonType SKELETON = new Builder(0xFFFFFF, true)
            .putAttributeModifier(Attributes.MAX_HEALTH, "Dragon health adjustment", -15.0D, AttributeModifier.Operation.ADDITION)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.BONE_BLOCK)
            .build("skeleton");
    public static final DragonType STORM = new Builder(0xF5F1E9)
            .build("storm");
    public static final DragonType SUNLIGHT = new Builder(0xFFDE00)
            .addHabitat(Blocks.GLOWSTONE)
            .addHabitat(Blocks.JACK_O_LANTERN)
            .addHabitat(Blocks.SHROOMLIGHT)
            .addHabitat(Blocks.YELLOW_GLAZED_TERRACOTTA)
            .build("sunlight");
    public static final DragonType TERRA = new Builder(0xA56C21)
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
            .build("terra");
    public static final DragonType WATER = new Builder(0x4F69A8)
            .addImmunity(DamageSource.DROWN)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.WATER)
            .addHabitat(Biomes.OCEAN)
            .addHabitat(Biomes.RIVER)
            .build("water");
    public static final DragonType WITHER = new Builder(0x50260A, true)
            .putAttributeModifier(Attributes.MAX_HEALTH, "Dragon health adjustment", -10.0D, AttributeModifier.Operation.ADDITION)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .build("wither");
    public static final DragonType ZOMBIE = new Builder(0x5A5602)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.SOUL_SAND)
            .addHabitat(Blocks.SOUL_SAND)
            .addHabitat(Blocks.NETHER_WART_BLOCK)
            .addHabitat(Blocks.WARPED_WART_BLOCK)
            .build("zombie");

    public static Collection<DragonType> values() {
        return BY_NAME.values();
    }

    public static DragonType byName(String string) {
        DragonType value = BY_NAME.get(string);
        return value == null ? ENDER : value;
    }

    public final DragonResources resources;
    private final Style style;
    private final String name;
    private final String text;
    public final int color;
    public final boolean isSkeleton;
    private final ImmutableMultimap<Attribute, AttributeModifier> attributes;
    private final Set<DamageSource> immunities;
    private final Set<Block> blocks;
    private final Set<RegistryKey<Biome>> biomes;
    private final BasicParticleType sneezeParticle;
    private final BasicParticleType eggParticle;

    public DragonType(String name, Builder builder, DragonResources resources) {
        if (BY_NAME.containsKey(name)) {
            throw new IllegalArgumentException();
        }
        this.resources = resources;
        this.color = builder.color;
        this.isSkeleton = builder.isSkeleton;
        this.style = Style.EMPTY.withColor(Color.fromRgb(this.color));
        this.name = name;
        this.text = "dragon.type." + name;
        this.attributes = builder.attributes.build();
        this.immunities = new HashSet<>(builder.immunities);
        this.blocks = new HashSet<>(builder.blocks);
        this.biomes = new HashSet<>(builder.biomes);
        this.sneezeParticle = builder.sneezeParticle;
        this.eggParticle = builder.eggParticle;
        BY_NAME.put(name, this);
    }

    public ITextComponent getText() {
        return new TranslationTextComponent(this.text).withStyle(this.style);
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

    public boolean isHabitat(RegistryKey<Biome> biome) {
        if (this.biomes.isEmpty()) {
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

    public static class Builder {
        private static final UUID MODIFIER_UUID = UUID.fromString("12e4cc82-db6d-5676-afc5-86498f0f6464");
        public final ImmutableMultimap.Builder<Attribute, AttributeModifier> attributes = ImmutableMultimap.builder();
        public final int color;
        public final Set<DamageSource> immunities = new HashSet<>();
        public final Set<Block> blocks = new HashSet<>();
        public final Set<RegistryKey<Biome>> biomes = new HashSet<>();
        public final boolean isSkeleton;
        public BasicParticleType sneezeParticle = ParticleTypes.LARGE_SMOKE;
        public BasicParticleType eggParticle = ParticleTypes.MYCELIUM;

        public Builder(int color) {
            this(color, false);
        }

        public Builder(int color, boolean isSkeleton) {
            this.color = color;
            this.isSkeleton = isSkeleton;
            // ignore suffocation damage
            this.addImmunity(DamageSource.DROWN);
            this.addImmunity(DamageSource.IN_WALL);
            this.addImmunity(DamageSource.ON_FIRE);
            this.addImmunity(DamageSource.IN_FIRE);
            this.addImmunity(DamageSource.LAVA);
            this.addImmunity(DamageSource.HOT_FLOOR);
            this.addImmunity(DamageSource.CACTUS); // assume that cactus needles don't do much damage to animals with horned scales
            this.addImmunity(DamageSource.DRAGON_BREATH); // ignore damage from vanilla ender dragon. I kinda disabled this because it wouldn't make any sense, feel free to re enable
        }

        public final Builder putAttributeModifier(Attribute attribute, String name, double value, AttributeModifier.Operation operation) {
            this.attributes.put(attribute, new AttributeModifier(MODIFIER_UUID, name, value, operation));
            return this;
        }

        public final Builder addImmunity(DamageSource source) {
            this.immunities.add(source);
            return this;
        }

        public final Builder addHabitat(Block block) {
            this.blocks.add(block);
            return this;
        }

        public final Builder addHabitat(RegistryKey<Biome> block) {
            this.biomes.add(block);
            return this;
        }

        public final Builder setSneezeParticle(BasicParticleType particle) {
            this.sneezeParticle = particle;
            return this;
        }

        public final Builder setEggParticle(BasicParticleType particle) {
            this.eggParticle = particle;
            return this;
        }

        public DragonType build(String name) {
            return new DragonType(name, this, new DragonResources(name));
        }

        public DragonType build(String name, DragonResources resources) {
            return new DragonType(name, this, resources);
        }
    }
}
