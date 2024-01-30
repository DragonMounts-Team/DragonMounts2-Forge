package net.dragonmounts.entity.dragon.helper;

import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;

import java.util.Random;

public abstract class DragonHelper {

    protected final TameableDragonEntity dragon;
    protected final EntityDataManager dataWatcher;
    protected final Random rand;

    public DragonHelper(TameableDragonEntity dragon) {
        this.dragon = dragon;
        this.dataWatcher = dragon.getEntityData();
        this.rand = dragon.getRandom();
    }

    public void addAdditionalSaveData(CompoundNBT compound) {}

    public void readAdditionalSaveData(CompoundNBT compound) {}

    public void aiStep() {}

    public void tick() {}

    public void tickDeath() {}

    public void die(DamageSource cause) {}

}