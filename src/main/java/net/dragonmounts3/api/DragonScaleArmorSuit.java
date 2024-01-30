package net.dragonmounts3.api;

import net.dragonmounts3.item.DragonScaleArmorItem;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

import static net.dragonmounts3.init.DMItemGroups.TOOL_TAB;

public class DragonScaleArmorSuit extends ArmorSuit<DragonScaleArmorItem, DragonScaleMaterial> implements IDragonTypified {
    public static final Function<EquipmentSlotType, Item.Properties> DRAGONMOUNTS_TOOL_TAB = slot -> new Item.Properties().tab(TOOL_TAB);
    public final IDragonScaleArmorEffect effect;

    public DragonScaleArmorSuit(@Nonnull DragonScaleMaterial material, @Nullable IDragonScaleArmorEffect effect, Function<EquipmentSlotType, Item.Properties> properties) {
        super(material, new DragonScaleArmorItem(material, EquipmentSlotType.HEAD, effect, properties.apply(EquipmentSlotType.HEAD)), new DragonScaleArmorItem(material, EquipmentSlotType.CHEST, effect, properties.apply(EquipmentSlotType.CHEST)), new DragonScaleArmorItem(material, EquipmentSlotType.LEGS, effect, properties.apply(EquipmentSlotType.LEGS)), new DragonScaleArmorItem(material, EquipmentSlotType.FEET, effect, properties.apply(EquipmentSlotType.FEET)));
        this.effect = effect;
    }

    @Override
    public DragonType getDragonType() {
        return this.material.type;
    }
}
