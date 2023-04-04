package net.dragonmounts3.objects.items;

import net.minecraft.item.Item;

/**
 * @see net.minecraft.item.HorseArmorItem
 */
public class ItemDragonArmor extends Item {
    private final int protection;

    public ItemDragonArmor(int protection, Properties properties) {
        super(properties);
        this.protection = protection;
    }

    public int getProtection() {
        return this.protection;
    }
}
