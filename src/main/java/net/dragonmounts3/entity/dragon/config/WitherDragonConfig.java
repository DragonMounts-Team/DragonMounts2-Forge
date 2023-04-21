package net.dragonmounts3.entity.dragon.config;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.DamageSource;

public class WitherDragonConfig extends DragonConfig {
    public WitherDragonConfig() {
        super();
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
        double health = super.getMaxHealth();
        if (health > 11D)
            return health - 10D;
        return 1D;
    }

    @Override
    public BasicParticleType getSneezeParticle() {
        return null;
    }
}
