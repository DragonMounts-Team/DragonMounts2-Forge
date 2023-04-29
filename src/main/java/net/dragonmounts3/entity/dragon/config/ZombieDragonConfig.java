package net.dragonmounts3.entity.dragon.config;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.block.Blocks;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.DamageSource;

public class ZombieDragonConfig extends DragonConfig {
    public ZombieDragonConfig() {
        super();
        addImmunity(DamageSource.MAGIC);
        addImmunity(DamageSource.HOT_FLOOR);
        addImmunity(DamageSource.LIGHTNING_BOLT);
        addImmunity(DamageSource.WITHER);

        addHabitatBlock(Blocks.SOUL_SAND);
        addHabitatBlock(Blocks.SOUL_SOIL);
        addHabitatBlock(Blocks.NETHER_WART_BLOCK);
        addHabitatBlock(Blocks.WARPED_WART_BLOCK);
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
