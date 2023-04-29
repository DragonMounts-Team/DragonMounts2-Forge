package net.dragonmounts3.entity.dragon.config;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.block.Blocks;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.biome.Biomes;

public class WaterDragonConfig extends DragonConfig {
    public WaterDragonConfig() {
        super();
        addImmunity(DamageSource.DROWN);
        addImmunity(DamageSource.MAGIC);
        addImmunity(DamageSource.HOT_FLOOR);
        addImmunity(DamageSource.LIGHTNING_BOLT);
        addImmunity(DamageSource.WITHER);

        addHabitatBlock(Blocks.WATER);

        addHabitatBiome(Biomes.OCEAN);
        addHabitatBiome(Biomes.RIVER);
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
