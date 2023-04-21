package net.dragonmounts3.entity.dragon.config;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.block.Blocks;

public class TerraDragonConfig extends DragonConfig {
    public TerraDragonConfig() {
        super();
        //setHabitatBiome(Biomes.MESA);
        //setHabitatBiome(Biomes.MESA_ROCK);
        //setHabitatBiome(Biomes.MESA_CLEAR_ROCK);
        //setHabitatBiome(Biomes.MUTATED_MESA_CLEAR_ROCK);
        //setHabitatBiome(Biomes.MUTATED_MESA_ROCK);
        //setHabitatBlock(Blocks.HARDENED_CLAY);
        setHabitatBlock(Blocks.SAND);
        setHabitatBlock(Blocks.SANDSTONE);
        setHabitatBlock(Blocks.SANDSTONE_STAIRS);
        setHabitatBlock(Blocks.RED_SANDSTONE);
        setHabitatBlock(Blocks.RED_SANDSTONE_STAIRS);
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
