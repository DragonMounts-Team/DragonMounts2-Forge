package net.dragonmounts3.inventory;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DragonChestSlot extends Slot {
    public final TameableDragonEntity dragon;

    public DragonChestSlot(DragonInventory inventory, TameableDragonEntity dragon, int index, int x, int y) {
        super(inventory, index, x, y);
        this.dragon = dragon;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isActive() {
        return this.dragon.hasChest();
    }
}
