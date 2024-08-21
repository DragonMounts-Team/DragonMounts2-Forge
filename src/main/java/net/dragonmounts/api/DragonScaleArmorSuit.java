package net.dragonmounts.api;

import net.dragonmounts.item.DragonScaleArmorItem;
import net.dragonmounts.registry.DragonType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

import static net.dragonmounts.init.DMItemGroups.TOOL_TAB;
import static net.minecraft.inventory.EquipmentSlotType.*;

public class DragonScaleArmorSuit extends ArmorSuit<DragonScaleArmorItem> implements IDragonTypified {
    public static final Function<EquipmentSlotType, Item.Properties> DRAGONMOUNTS_TOOL_TAB = $ -> new Item.Properties().tab(TOOL_TAB);
    public final IDragonScaleArmorEffect effect;
    public final DragonType type;

    public DragonScaleArmorSuit(@Nonnull DragonScaleMaterial material, @Nullable IDragonScaleArmorEffect effect, Function<EquipmentSlotType, Item.Properties> factory) {
        super(
                new DragonScaleArmorItem(material, HEAD, effect, factory.apply(HEAD)),
                new DragonScaleArmorItem(material, CHEST, effect, factory.apply(CHEST)),
                new DragonScaleArmorItem(material, LEGS, effect, factory.apply(LEGS)),
                new DragonScaleArmorItem(material, FEET, effect, factory.apply(FEET))
        );
        this.type = material.type;
        this.effect = effect;
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}
