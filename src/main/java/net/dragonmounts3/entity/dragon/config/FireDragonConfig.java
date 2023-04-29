package net.dragonmounts3.entity.dragon.config;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.block.Blocks;
import net.minecraft.util.DamageSource;

public class FireDragonConfig extends DragonConfig {
    public FireDragonConfig() {
        super();
        addImmunity(DamageSource.MAGIC);
        addImmunity(DamageSource.HOT_FLOOR);
        addImmunity(DamageSource.LIGHTNING_BOLT);
        addImmunity(DamageSource.WITHER);

        addHabitatBlock(Blocks.FIRE);
        //setHabitatBlock(Blocks.LIT_FURNACE);
        addHabitatBlock(Blocks.LAVA);
        //setHabitatBlock(Blocks.FLOWING_LAVA);
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
