package net.dragonmounts3.entity.dragon.inventoty;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;

public class DragonInventoryListener implements IInventoryChangedListener {

    TameableDragonEntity dragon;

    public DragonInventoryListener(TameableDragonEntity dragon) {
        this.dragon = dragon;
    }

    @Override
    public void containerChanged(IInventory pInvBasic) {

    }

}