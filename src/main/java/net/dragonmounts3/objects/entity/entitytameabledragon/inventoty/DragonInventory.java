package net.dragonmounts3.objects.entity.entitytameabledragon.inventoty;

import net.dragonmounts3.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.inventory.Inventory;

public class DragonInventory extends Inventory {

    public DragonInventory(int slotCount, EntityTameableDragon dragon) {
        super(slotCount);
        this.addListener(new DragonInventoryListener(dragon));
    }

}