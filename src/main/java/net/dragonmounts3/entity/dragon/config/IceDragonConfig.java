package net.dragonmounts3.entity.dragon.config;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.block.Blocks;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.biome.Biomes;

public class IceDragonConfig extends DragonConfig {
    public IceDragonConfig() {
        super();
        addImmunity(DamageSource.MAGIC);
        addImmunity(DamageSource.HOT_FLOOR);
        addImmunity(DamageSource.LIGHTNING_BOLT);
        addImmunity(DamageSource.WITHER);

        addHabitatBlock(Blocks.SNOW);
        //setHabitatBlock(Blocks.SNOW_LAYER);
        addHabitatBlock(Blocks.ICE);
        addHabitatBlock(Blocks.PACKED_ICE);
        addHabitatBlock(Blocks.FROSTED_ICE);

        addHabitatBiome(Biomes.FROZEN_OCEAN);
        addHabitatBiome(Biomes.FROZEN_RIVER);
        //setHabitatBiome(Biomes.ICE_MOUNTAINS);
        //setHabitatBiome(Biomes.ICE_PLAINS);
    }

    @Override
    public void onEnable(TameableDragonEntity dragon) {

    }

    @Override
    public void onDisable(TameableDragonEntity dragon) {

    }

    @Override
    public void onDeath(TameableDragonEntity dragon) {

    }

    @Override
    public BasicParticleType getSneezeParticle() {
        return null;
    }
}
