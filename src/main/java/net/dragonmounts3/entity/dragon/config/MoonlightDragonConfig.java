package net.dragonmounts3.entity.dragon.config;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.block.Blocks;
import net.minecraft.particles.BasicParticleType;

public class MoonlightDragonConfig extends DragonConfig {
    public MoonlightDragonConfig() {
        super();
        //setHabitatBlock(Blocks.DAYLIGHT_DETECTOR_INVERTED);
        addHabitatBlock(Blocks.BLUE_GLAZED_TERRACOTTA);
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
