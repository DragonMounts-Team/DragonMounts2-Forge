package net.dragonmounts3.entity.dragon.inventoty;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.inventory.Inventory;

public class DragonInventory extends Inventory {

    public DragonInventory(int slotCount, TameableDragonEntity dragon) {
        super(slotCount);
        this.addListener(new DragonInventoryListener(dragon));
    }

}