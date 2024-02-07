package net.dragonmounts.api;

import net.dragonmounts.capability.ArmorEffectManager;
import net.dragonmounts.capability.IArmorEffectManager;
import net.dragonmounts.registry.CooldownCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dragonmounts.util.TimeUtil.formatAsFloat;

public interface IDragonScaleArmorEffect extends IArmorEffect {
    default void appendTriggerInfo(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> tooltips) {
        tooltips.add(new TranslationTextComponent("tooltip.dragonmounts.armor_effect_piece_4"));
    }

    default void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> tooltips) {}

    class Advanced extends CooldownCategory implements IDragonScaleArmorEffect {
        public final int cooldown;
        private String description;

        public Advanced(int cooldown) {
            this.cooldown = cooldown;
        }

        protected String getOrCreateDescription() {
            if (this.description == null) {
                this.description = Util.makeDescriptionId("tooltip.armor_effect", this.getSerializedName());
            }
            return this.description;
        }

        protected String getDescription() {
            return this.getOrCreateDescription();
        }

        public void appendCooldownInfo(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> tooltips) {
            tooltips.add(new TranslationTextComponent(this.getDescription()));
            int value = ArmorEffectManager.getLocalCooldown(this);
            if (value > 0) {
                tooltips.add(new TranslationTextComponent("tooltip.dragonmounts.armor_effect_remaining_cooldown", formatAsFloat(value)));
            } else if (this.cooldown > 0) {
                tooltips.add(new TranslationTextComponent("tooltip.dragonmounts.armor_effect_cooldown", formatAsFloat(this.cooldown)));
            }
        }

        @Override
        public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> tooltips) {
            tooltips.add(StringTextComponent.EMPTY);
            this.appendTriggerInfo(stack, world, tooltips);
            this.appendCooldownInfo(stack, world, tooltips);
        }

        public Advanced withRegistryName(String name) {
            super.setRegistryName(name);
            return this;
        }

        @Override
        public boolean activate(IArmorEffectManager manager, PlayerEntity player, int level) {
            return level > 3;
        }
    }
}
