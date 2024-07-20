package net.dragonmounts.init;

import net.dragonmounts.registry.DragonType;
import net.dragonmounts.util.Values;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static net.dragonmounts.DragonMounts.MOD_ID;

public class DragonTypes {
    public static final DragonType AETHER = new DragonType(new DragonType.Properties(0x0294BD)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.LAPIS_BLOCK)
            .addHabitat(Blocks.LAPIS_ORE)
            .setEnvironmentPredicate(egg -> egg.getY() >= egg.level.getHeight() * 0.625)
    ).setRegistryName(MOD_ID + ":aether");
    public static final DragonType ENCHANT = new DragonType(new DragonType.Properties(0x8359AE)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.BOOKSHELF)
            .addHabitat(Blocks.ENCHANTING_TABLE)
    ).setRegistryName(MOD_ID + ":enchant");
    public static final DragonType ENDER = new DragonType(new DragonType.Properties(0xAB39BE)
            .notConvertible()
            .putAttributeModifier(Attributes.MAX_HEALTH, "DragonTypeBonus", 10.0D, AttributeModifier.Operation.ADDITION)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .setSneezeParticle(ParticleTypes.PORTAL)
            .setEggParticle(ParticleTypes.PORTAL)
    ).setRegistryName(DragonType.DEFAULT_KEY);
    public static final DragonType FIRE = new DragonType(new DragonType.Properties(0x960B0F)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.FIRE)
            //.addHabitat(Blocks.LIT_FURNACE)
            .addHabitat(Blocks.LAVA)
            //.addHabitat(Blocks.FLOWING_LAVA)
    ).setRegistryName(MOD_ID + ":fire");
    public static final DragonType FOREST = new DragonType(new DragonType.Properties(0x298317)
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
    ).setRegistryName(MOD_ID + ":forest");
    public static final DragonType ICE = new DragonType(new DragonType.Properties(0x00F2FF)
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
    ).setRegistryName(MOD_ID + ":ice");
    public static final DragonType MOONLIGHT = new DragonType(new DragonType.Properties(0x2C427C)
            .addHabitat(Blocks.BLUE_GLAZED_TERRACOTTA)
    ).setRegistryName(MOD_ID + ":moonlight");
    public static final DragonType NETHER = new DragonType(new DragonType.Properties(0xE5B81B)
            .putAttributeModifier(Attributes.MAX_HEALTH, "DragonTypeBonus", 5.0D, AttributeModifier.Operation.ADDITION)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            //.addHabitat(Biomes.HELL)
            .setEggParticle(ParticleTypes.DRIPPING_LAVA)
    ).setRegistryName(MOD_ID + ":nether");
    public static final DragonType SCULK = new DragonType(new DragonType.Properties(0x29DFEB)
            .notConvertible()
            .putAttributeModifier(Attributes.MAX_HEALTH, "DragonTypeBonus", 10.0D, AttributeModifier.Operation.ADDITION)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
    ).setRegistryName(MOD_ID + ":sculk");
    public static final DragonType SKELETON = new DragonType(new DragonType.Properties(0xFFFFFF)
            .isSkeleton()
            .putAttributeModifier(Attributes.MAX_HEALTH, "DragonTypeBonus", -15.0D, AttributeModifier.Operation.ADDITION)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.BONE_BLOCK)
            .setEnvironmentPredicate(egg -> egg.getY() <= egg.level.getHeight() * 0.25 || egg.level.getRawBrightness(egg.blockPosition(), 0) < 4)
    ).setRegistryName(MOD_ID + ":skeleton");
    public static final DragonType STORM = new DragonType(
            new DragonType.Properties(0xF5F1E9)
    ).setRegistryName(MOD_ID + ":storm");
    public static final DragonType SUNLIGHT = new DragonType(new DragonType.Properties(0xFFDE00)
            .addHabitat(Blocks.GLOWSTONE)
            .addHabitat(Blocks.JACK_O_LANTERN)
            .addHabitat(Blocks.SHROOMLIGHT)
            .addHabitat(Blocks.YELLOW_GLAZED_TERRACOTTA)
    ).setRegistryName(MOD_ID + ":sunlight");
    public static final DragonType TERRA = new DragonType(new DragonType.Properties(0xA56C21)
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
    ).setRegistryName(MOD_ID + ":terra");
    public static final DragonType WATER = new DragonType(new DragonType.Properties(0x4F69A8)
            .addImmunity(DamageSource.DROWN)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.WATER)
            .addHabitat(Biomes.OCEAN)
            .addHabitat(Biomes.RIVER)
    ).setRegistryName(MOD_ID + ":water");
    public static final DragonType WITHER = new DragonType(new DragonType.Properties(0x50260A)
            .notConvertible()
            .isSkeleton()
            .putAttributeModifier(Attributes.MAX_HEALTH, "DragonTypeBonus", -10.0D, AttributeModifier.Operation.ADDITION)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
    ).setRegistryName(MOD_ID + ":wither");
    public static final DragonType ZOMBIE = new DragonType(new DragonType.Properties(0x5A5602)
            .addImmunity(DamageSource.MAGIC)
            .addImmunity(DamageSource.HOT_FLOOR)
            .addImmunity(DamageSource.LIGHTNING_BOLT)
            .addImmunity(DamageSource.WITHER)
            .addHabitat(Blocks.SOUL_SAND)
            .addHabitat(Blocks.SOUL_SAND)
            .addHabitat(Blocks.NETHER_WART_BLOCK)
            .addHabitat(Blocks.WARPED_WART_BLOCK)
    ).setRegistryName(MOD_ID + ":zombie");

    public static void register(RegistryEvent.Register<DragonType> event) {
        IForgeRegistry<DragonType> registry = event.getRegistry();
        Values.LazyIterator<DragonType> iterator = new Values.LazyIterator<>(DragonTypes.class, DragonType.class);
        while (iterator.hasNext()) {
            registry.register(iterator.next());
        }
    }
}
