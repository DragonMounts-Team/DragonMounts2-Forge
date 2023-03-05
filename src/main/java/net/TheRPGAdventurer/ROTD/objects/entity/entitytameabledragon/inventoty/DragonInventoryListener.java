package net.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.inventoty;

import net.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
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