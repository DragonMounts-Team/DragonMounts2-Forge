package net.dragonmounts.client.gui;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

public class LazySliderConfigOption extends AbstractLazyConfigOption<Double> {
    public static final Function<LazySliderConfigOption, ITextComponent> STRINGIFY_X_2F = button -> button.genericValueLabel(new StringTextComponent(String.format("%.2f", button.value)));
    public final Function<LazySliderConfigOption, ITextComponent> stringify;
    public final double minValue;
    public final double maxValue;
    public final double distance;
    public final double steps;
    protected double value;

    public LazySliderConfigOption(
            String caption,
            ForgeConfigSpec.ConfigValue<Double> config,
            double minValue,
            double maxValue,
            double steps,
            Function<LazySliderConfigOption, ITextComponent> stringify,
            @Nullable ITextComponent tooltip
    ) {
        super(caption, config, tooltip);
        this.value = config.get();
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.distance = maxValue - minValue;
        this.steps = steps;
        this.stringify = stringify;
    }

    @Nonnull
    @Override
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

    @Override
    public void save() {
        this.config.set(this.value);
    }

    public ITextComponent getMessage() {
        return this.stringify.apply(this);
    }
}
