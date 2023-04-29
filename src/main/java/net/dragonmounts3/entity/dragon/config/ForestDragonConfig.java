package net.dragonmounts3.entity.dragon.config;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.block.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.world.biome.Biomes;

public class ForestDragonConfig extends DragonConfig {
    public ForestDragonConfig() {
        super();
        addImmunity(DamageSource.MAGIC);
        addImmunity(DamageSource.HOT_FLOOR);
        addImmunity(DamageSource.LIGHTNING_BOLT);
        addImmunity(DamageSource.WITHER);

        //setHabitatBlock(Blocks.YELLOW_FLOWER);
        //setHabitatBlock(Blocks.RED_FLOWER);
        addHabitatBlock(Blocks.MOSSY_COBBLESTONE);
        addHabitatBlock(Blocks.VINE);
        //setHabitatBlock(Blocks.SAPLING);
        //setHabitatBlock(Blocks.LEAVES);
        //setHabitatBlock(Blocks.LEAVES2);

        addHabitatBiome(Biomes.JUNGLE);
        addHabitatBiome(Biomes.JUNGLE_HILLS);
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
