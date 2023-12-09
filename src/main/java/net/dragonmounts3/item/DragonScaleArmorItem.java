package net.dragonmounts3.item;

import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import net.dragonmounts3.api.DragonScaleMaterial;
import net.dragonmounts3.api.IArmorEffect;
import net.dragonmounts3.api.IArmorEffectSource;
import net.dragonmounts3.api.IDragonTypified;
import net.dragonmounts3.capability.DragonTypifiedCooldown;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dragonmounts3.DragonMounts.ITEM_TRANSLATION_KEY_PREFIX;
import static net.dragonmounts3.util.TimeUtil.formatAsFloat;

public class DragonScaleArmorItem extends ArmorItem implements IDragonTypified, IArmorEffectSource {
    protected DragonType type;
    public final DragonScaleArmorEffect effect;

    public DragonScaleArmorItem(DragonScaleMaterial material, EquipmentSlotType slot, DragonScaleArmorEffect effect, Properties properties) {
        super(material, slot, properties);
        this.type = material.getDragonType();
        this.effect = effect;
    }

    @Override
    public void attachEffect(Reference2IntMap<IArmorEffect> map, PlayerEntity player, ItemStack stack) {
        if (this.effect != null) {
            if (map.containsKey(this.effect)) {
                map.put(this.effect, map.getOrDefault(this.effect, 0) + 1);
            } else {
                map.put(this.effect, 1);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> components, @Nonnull ITooltipFlag flag) {
        components.add(this.type.getName());
        if (this.effect == null) return;
        components.add(StringTextComponent.EMPTY);
        components.add(new TranslationTextComponent("tooltip.dragonmounts.armor_effect_piece_4"));
        if (this.effect.luck) {
            components.add(new TranslationTextComponent("tooltip.dragonmounts.armor_effect_fishing_luck"));
        }
        components.add(new TranslationTextComponent(this.effect.description));
        int value = DragonTypifiedCooldown.getLocal(this.type);
        if (value > 0) {
            components.add(new TranslationTextComponent("tooltip.dragonmounts.armor_effect_remaining_cooldown", formatAsFloat(value)));
        } else if (this.effect.cooldown > 0) {
            components.add(new TranslationTextComponent("tooltip.dragonmounts.armor_effect_cooldown", formatAsFloat(this.effect.cooldown)));
        }
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }

    public static class Boots extends DragonScaleArmorItem {
        private static final String TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_boots";

        public Boots(DragonScaleMaterial material, DragonScaleArmorEffect effect, Properties properties) {
            super(material, EquipmentSlotType.FEET, effect, properties);
        }

        @Nonnull
        @Override
        public String getDescriptionId() {
            return TRANSLATION_KEY;
        }
    }

    public static class Leggings extends DragonScaleArmorItem {
        private static final String TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_leggings";

        public Leggings(DragonScaleMaterial material, DragonScaleArmorEffect effect, Properties properties) {
            super(material, EquipmentSlotType.LEGS, effect, properties);
        }

        @Nonnull
        @Override
        public String getDescriptionId() {
            return TRANSLATION_KEY;
        }
    }

    public static class Chestplate extends DragonScaleArmorItem {
        private static final String TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_chestplate";

        public Chestplate(DragonScaleMaterial material, DragonScaleArmorEffect effect, Properties properties) {
            super(material, EquipmentSlotType.CHEST, effect, properties);
        }

        @Nonnull
        @Override
        public String getDescriptionId() {
            return TRANSLATION_KEY;
        }
    }

    public static class Helmet extends DragonScaleArmorItem {
        private static final String TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_helmet";

        public Helmet(DragonScaleMaterial material, DragonScaleArmorEffect effect, Properties properties) {
            super(material, EquipmentSlotType.HEAD, effect, properties);
        }

        @Nonnull
        @Override
        public String getDescriptionId() {
            return TRANSLATION_KEY;
        }
    }
}
