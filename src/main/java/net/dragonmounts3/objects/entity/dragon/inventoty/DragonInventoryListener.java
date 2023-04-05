package net.dragonmounts3.objects.entity.dragon.inventoty;

import net.dragonmounts3.objects.entity.dragon.EntityTameableDragon;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;

public class DragonInventoryListener implements IInventoryChangedListener {

    EntityTameableDragon dragon;

    public DragonInventoryListener(EntityTameableDragon dragon) {
        this.dragon = dragon;
    }

    @Override
    public void containerChanged(IInventory pInvBasic) {

    }

}