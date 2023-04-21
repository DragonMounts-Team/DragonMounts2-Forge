package net.dragonmounts3.entity.dragon.config;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;

public class NetherDragonConfig extends DragonConfig {
    public NetherDragonConfig() {
        super();
        //setHabitatBiome(Biomes.HELL);

        setImmunity(DamageSource.MAGIC);
        setImmunity(DamageSource.HOT_FLOOR);
        setImmunity(DamageSource.LIGHTNING_BOLT);
        setImmunity(DamageSource.WITHER);
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
    public double getMaxHealth() {
        return super.getMaxHealth() + 5D;
    }

    @Override
    public BasicParticleType getSneezeParticle() {
        return ParticleTypes.PORTAL;
    }

    @Override
    public BasicParticleType getEggParticle() {
        return ParticleTypes.DRIPPING_LAVA;
    }
}
