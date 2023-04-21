package net.dragonmounts3.entity.dragon.config;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.block.Blocks;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.DamageSource;

public class SkeletonDragonConfig extends DragonConfig {
    public SkeletonDragonConfig() {
        super();
        setHabitatBlock(Blocks.BONE_BLOCK);
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
        if (health > 16D)
            return health - 15D;
        return 1D;
    }

    @Override
    public BasicParticleType getSneezeParticle() {
        return null;
    }
}
