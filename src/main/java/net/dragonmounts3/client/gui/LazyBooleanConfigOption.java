package net.dragonmounts3.client.gui;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

public class LazyBooleanConfigOption extends AbstractOption {
    public static final Function<LazyBooleanConfigOption, ITextComponent> DEFAULT_STRINGIFY =
            option -> DialogTexts.optionStatus(option.getCaption(), option.get());
    public static final Function<LazyBooleanConfigOption, ITextComponent> TOGGLE_STRINGIFY =
            option -> option.genericValueLabel(new TranslationTextComponent(option.get() ? "options.key.toggle" : "options.key.hold"));
    private final Function<LazyBooleanConfigOption, ITextComponent> stringify;
    public final ForgeConfigSpec.ConfigValue<Boolean> config;
    @Nullable
    private final ITextComponent tooltipText;
    public boolean value;

    public LazyBooleanConfigOption(
            String caption,
            ForgeConfigSpec.ConfigValue<Boolean> config,
            Function<LazyBooleanConfigOption, ITextComponent> stringify,
            @Nullable ITextComponent tooltipText
    ) {
        super(caption);
        this.config = config;
        this.tooltipText = tooltipText;
        this.stringify = stringify;
        this.value = config.get();
    }

    public void set(String value) {
        this.set("true".equals(value));
    }

    public void toggle() {
        this.set(!this.get());
    }

    private void set(boolean value) {
        this.value = value;
    }

    public boolean get() {
        return this.value;
    }

    public void save() {
        this.config.set(this.value);
    }

    @Nonnull
    public Widget createButton(@Nullable GameSettings options, int pX, int pY, int pWidth) {
        if (this.tooltipText != null) {
            this.setTooltip(Minecraft.getInstance().font.split(this.tooltipText, 200));
        }
        return new OptionButton(pX, pY, pWidth, 20, this, this.stringify.apply(this), button -> {
            this.toggle();
            button.setMessage(this.stringify.apply(this));
        });
    }
}
