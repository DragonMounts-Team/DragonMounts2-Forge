package net.dragonmounts.client.gui;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

public class LazyBooleanConfigOption extends AbstractLazyConfigOption<Boolean> {
    public static final Function<LazyBooleanConfigOption, ITextComponent> DEFAULT_STRINGIFY =
            option -> DialogTexts.optionStatus(option.getCaption(), option.get());
    public static final Function<LazyBooleanConfigOption, ITextComponent> TOGGLE_STRINGIFY =
            option -> option.genericValueLabel(new TranslationTextComponent(option.get() ? "options.key.toggle" : "options.key.hold"));
    public final Function<LazyBooleanConfigOption, ITextComponent> stringify;
    protected boolean value;

    public LazyBooleanConfigOption(
            String caption,
            ForgeConfigSpec.ConfigValue<Boolean> config,
            Function<LazyBooleanConfigOption, ITextComponent> stringify,
            @Nullable ITextComponent tooltip
    ) {
        super(caption, config, tooltip);
        this.stringify = stringify;
        this.value = config.get();
    }

    public void toggle() {
        this.set(!this.get());
    }

    public void set(boolean value) {
        this.value = value;
    }

    public boolean get() {
        return this.value;
    }

    @Override
    public void save() {
        this.config.set(this.value);
    }

    @Nonnull
    @Override
    public Widget createButton(@Nullable GameSettings options, int x, int y, int width) {
        return new OptionButton(x, y, width, 20, this, this.stringify.apply(this), button -> {
            this.toggle();
            button.setMessage(this.stringify.apply(this));
        });
    }
}
