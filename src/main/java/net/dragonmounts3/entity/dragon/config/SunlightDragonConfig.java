package net.dragonmounts3.entity.dragon.config;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.block.Blocks;

public class SunlightDragonConfig extends DragonConfig {
    public SunlightDragonConfig() {
        super();
        setHabitatBlock(Blocks.DAYLIGHT_DETECTOR);
        setHabitatBlock(Blocks.GLOWSTONE);
        setHabitatBlock(Blocks.YELLOW_GLAZED_TERRACOTTA);
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
}
