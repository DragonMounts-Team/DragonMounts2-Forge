package net.dragonmounts.api;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;

import javax.annotation.Nonnull;

public class ArmorSuit<T extends ArmorItem, M extends IArmorMaterial> {
    public final M material;
    public final T helmet;
    public final T chestplate;
    public final T leggings;
    public final T boots;

    public ArmorSuit(M material, T helmet, T chestplate, T leggings, T boots) {
        this.material = material;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    public final T bySlot(@Nonnull EquipmentSlotType slot) {
        switch (slot) {
            case HEAD: return this.helmet;
            case CHEST: return this.chestplate;
            case LEGS: return this.leggings;
            case FEET: return this.boots;
            default: return null;
        }
    }
}
