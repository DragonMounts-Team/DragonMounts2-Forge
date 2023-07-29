package net.dragonmounts3.util;

import com.google.common.collect.ImmutableMultimap;
import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.variant.AbstractVariant;
import net.dragonmounts3.api.variant.VariantManager;
import net.dragonmounts3.entity.dragon.HatchableDragonEggEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.biome.Biome;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class DragonTypeBuilder {
    protected static final UUID MODIFIER_UUID = UUID.fromString("12e4cc82-db6d-5676-afc5-86498f0f6464");
    public final ImmutableMultimap.Builder<Attribute, AttributeModifier> attributes = ImmutableMultimap.builder();
    public final int color;
    public final Set<DamageSource> immunities = new HashSet<>();
    public final Set<Block> blocks = new HashSet<>();
    public final Set<RegistryKey<Biome>> biomes = new HashSet<>();
    public final VariantManager variants = new VariantManager();
    public boolean convertible = true;
    public boolean isSkeleton = false;
    public BasicParticleType sneezeParticle = ParticleTypes.LARGE_SMOKE;
    public BasicParticleType eggParticle = ParticleTypes.MYCELIUM;
    public BiFunction<Integer, Boolean, Vector3d> passengerOffset = DragonType.DEFAULT_PASSENGER_OFFSET;
    public Predicate<HatchableDragonEggEntity> isHabitatEnvironment = DragonType.DEFAULT_ENVIRONMENT_PREDICATE;

    public DragonTypeBuilder(int color) {
        this.color = color;
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

    public DragonTypeBuilder notConvertible() {
        this.convertible = false;
        return this;
    }

    public DragonTypeBuilder isSkeleton() {
        this.isSkeleton = true;
        return this;
    }

    public DragonTypeBuilder putAttributeModifier(Attribute attribute, String name, double value, AttributeModifier.Operation operation) {
        this.attributes.put(attribute, new AttributeModifier(MODIFIER_UUID, name, value, operation));
        return this;
    }

    public DragonTypeBuilder addImmunity(DamageSource source) {
        this.immunities.add(source);
        return this;
    }

    public DragonTypeBuilder addHabitat(Block block) {
        this.blocks.add(block);
        return this;
    }

    public DragonTypeBuilder addHabitat(RegistryKey<Biome> block) {
        this.biomes.add(block);
        return this;
    }

    public DragonTypeBuilder addVariant(AbstractVariant variant) {
        this.variants.add(variant);
        return this;
    }

    public DragonTypeBuilder setSneezeParticle(BasicParticleType particle) {
        this.sneezeParticle = particle;
        return this;
    }

    public DragonTypeBuilder setEggParticle(BasicParticleType particle) {
        this.eggParticle = particle;
        return this;
    }

    public DragonTypeBuilder setPassengerOffset(BiFunction<Integer, Boolean, Vector3d> passengerOffset) {
        this.passengerOffset = passengerOffset;
        return this;
    }

    public DragonTypeBuilder setEnvironmentPredicate(Predicate<HatchableDragonEggEntity> predicate) {
        this.isHabitatEnvironment = predicate;
        return this;
    }

    public DragonType build(String name) {
        return new DragonType(name, this);
    }
}
