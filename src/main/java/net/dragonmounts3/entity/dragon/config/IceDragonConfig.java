package net.dragonmounts3.entity.dragon.config;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.block.Blocks;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.biome.Biomes;

public class IceDragonConfig extends DragonConfig {
    public IceDragonConfig() {
        super();
        setImmunity(DamageSource.MAGIC);
        setImmunity(DamageSource.HOT_FLOOR);
        setImmunity(DamageSource.LIGHTNING_BOLT);
        setImmunity(DamageSource.WITHER);

        setHabitatBlock(Blocks.SNOW);
        //setHabitatBlock(Blocks.SNOW_LAYER);
        setHabitatBlock(Blocks.ICE);
        setHabitatBlock(Blocks.PACKED_ICE);
        setHabitatBlock(Blocks.FROSTED_ICE);

        setHabitatBiome(Biomes.FROZEN_OCEAN);
        setHabitatBiome(Biomes.FROZEN_RIVER);
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
