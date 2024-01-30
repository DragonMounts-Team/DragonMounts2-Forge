package net.dragonmounts.client.gui;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractLazyConfigOption<T> extends AbstractOption {
    public final ForgeConfigSpec.ConfigValue<T> config;

    public AbstractLazyConfigOption(String caption, ForgeConfigSpec.ConfigValue<T> config, @Nullable ITextComponent tooltip) {
        super(caption);
        this.config = config;
        if (tooltip != null) {
            this.setTooltip(Minecraft.getInstance().font.split(tooltip, 200));
        }
    }

    public abstract void save();

    @Nonnull
    public ITextComponent pixelValueLabel(int value) {
        return super.pixelValueLabel(value);
    }

    @Nonnull
    @Override
    public ITextComponent percentValueLabel(double percentage) {
        return new TranslationTextComponent("options.percent_value", this.getCaption(), percentage * 100);
    }

    @Nonnull
    @Override
    public ITextComponent percentAddValueLabel(int value) {
        return new TranslationTextComponent("options.percent_add_value", this.getCaption(), value);
    }

    @Nonnull
    @Override
    public ITextComponent genericValueLabel(@Nonnull ITextComponent value) {
        return new TranslationTextComponent("options.generic_value", this.getCaption(), value);
    }

    @Nonnull
    @Override
    public ITextComponent genericValueLabel(int value) {
        return super.genericValueLabel(value);
    }
}
