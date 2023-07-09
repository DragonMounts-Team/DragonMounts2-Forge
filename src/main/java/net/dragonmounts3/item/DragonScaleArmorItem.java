package net.dragonmounts3.item;

import net.dragonmounts3.api.*;
import net.dragonmounts3.init.DMCapabilities;
import net.minecraft.client.Minecraft;
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
import java.util.Map;

import static net.dragonmounts3.DragonMounts.ITEM_TRANSLATION_KEY_PREFIX;
import static net.dragonmounts3.util.TimeUtil.formatAsFloat;

public class DragonScaleArmorItem extends ArmorItem implements IDragonTypified, IArmorEffectSource {
    private static final String[] TRANSLATION_KEY_SUFFIX = new String[]{"boots", "leggings", "chestplate", "helmet"};
    private final String translationKey;
    protected DragonType type;
    public final DragonArmorEffect effect;

    public DragonScaleArmorItem(
            DragonScaleMaterial material,
            EquipmentSlotType slot,
            DragonArmorEffect effect,
            Properties properties
    ) {
        super(material, slot, properties);
        this.type = material.getDragonType();
        this.translationKey = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_" + TRANSLATION_KEY_SUFFIX[slot.getIndex()];
        this.effect = effect;
    }

    @Override
    public void putEffect(Map<IArmorEffect, Integer> map, PlayerEntity player, ItemStack stack) {
        if (this.effect != null) {
            if (map.containsKey(this.effect)) {
                map.put(this.effect, map.get(this.effect) + 1);
            } else {
                map.put(this.effect, 1);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> components, @Nonnull ITooltipFlag flag) {
        components.add(this.type.getText());
        if (this.effect == null) return;
        components.add(StringTextComponent.EMPTY);
        components.add(new TranslationTextComponent("tooltip.dragonmounts.armor_effect_piece_4"));
        components.add(new TranslationTextComponent(this.effect.description));
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(DMCapabilities.DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                int value = cooldown.get(this.type);
                if (value > 0) {
                    components.add(new TranslationTextComponent("tooltip.dragonmounts.armor_effect_remaining_cooldown", formatAsFloat(value)));
                } else if (this.effect.cooldown > 0) {
                    components.add(new TranslationTextComponent("tooltip.dragonmounts.armor_effect_cooldown", formatAsFloat(this.effect.cooldown)));
                }
            });
        } else if (this.effect.cooldown > 0) {
            //throw new NullPointerException();
            components.add(new TranslationTextComponent("tooltip.dragonmounts.armor_effect_cooldown", formatAsFloat(this.effect.cooldown)));
        }
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return this.translationKey;
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}
