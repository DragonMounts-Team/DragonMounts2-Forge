package net.dragonmounts3.client.gui;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

public class LazySliderConfigOption extends AbstractOption {
    private final Function<LazySliderConfigOption, ITextComponent> stringify;
    public final ForgeConfigSpec.ConfigValue<Double> config;
    public final double minValue;
    public final double maxValue;
    public final double distance;
    public final double steps;
    public double value;

    public LazySliderConfigOption(
            String caption,
            ForgeConfigSpec.ConfigValue<Double> config,
            double minValue,
            double maxValue,
            double steps,
            Function<LazySliderConfigOption, ITextComponent> stringify
    ) {
        super(caption);
        this.config = config;
        this.value = config.get();
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.distance = maxValue - minValue;
        this.steps = steps;
        this.stringify = stringify;
    }

    @Nonnull
    public Widget createButton(@Nullable GameSettings options, int pX, int pY, int pWidth) {
        return new ConfigSliderWidget(pX, pY, pWidth, 20, this);
    }

    public double toPct(double value) {
        return MathHelper.clamp((value - this.minValue) / this.distance, 0.0D, 1.0D);
    }

    public double toValue(double value) {
        value = this.minValue + value * this.distance;
        if (this.steps > 0.0F) {
            return MathHelper.clamp(this.steps * Math.round(value / this.steps), this.minValue, this.maxValue);
        }
        return MathHelper.clamp(value, this.minValue, this.maxValue);
    }

    public void set(double value) {
        this.value = value;
    }

    public double get() {
        return this.value;
    }

    public void save() {
        this.config.set(this.value);
    }

    public ITextComponent getMessage() {
        return this.stringify.apply(this);
    }

    @Nonnull
    public ITextComponent pixelValueLabel(int value) {
        return super.pixelValueLabel(value);
    }

    @Nonnull
    @Override
    public ITextComponent percentValueLabel(double percentage) {
        return new TranslationTextComponent("options.percent_value", this.getCaption(), (int) (percentage * 100.0D));
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
