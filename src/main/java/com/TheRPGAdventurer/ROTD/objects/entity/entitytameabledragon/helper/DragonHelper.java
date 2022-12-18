package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.network.datasync.EntityDataManager;

import java.util.Random;

public abstract class DragonHelper {

    protected final EntityTameableDragon dragon;
    protected final EntityDataManager dataWatcher;
    protected final Random rand;

    public DragonHelper(EntityTameableDragon dragon) {
        this.dragon = dragon;
        this.dataWatcher = dragon.getEntityData();
        this.rand = dragon.getRandom();
    }



}