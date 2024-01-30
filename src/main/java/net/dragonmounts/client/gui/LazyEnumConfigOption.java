package net.dragonmounts.client.gui;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

@SuppressWarnings("unused")//万一以后要用呢
public class LazyEnumConfigOption<T extends Enum<T>> extends AbstractLazyConfigOption<T> {
    public final Function<LazyEnumConfigOption<T>, ITextComponent> stringify;
    protected T value;
    protected final T[] values;

    public LazyEnumConfigOption(
            String caption,
            ForgeConfigSpec.ConfigValue<T> config,
            Class<T> clazz,
            Function<LazyEnumConfigOption<T>, ITextComponent> stringify,
            @Nullable ITextComponent tooltip
    ) {
        super(caption, config, tooltip);
        this.values = clazz.getEnumConstants();
        this.stringify = stringify;
        this.value = config.get();
    }

    public void toggle() {
        int i = this.get().ordinal();
        this.set(++i >= this.values.length ? this.values[0] : this.values[i]);
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    @Override
    public void save() {
        this.config.set(this.value);
    }

    @Nonnull
    @Override
    public Widget createButton(@Nullable GameSettings options, int pX, int pY, int pWidth) {
        return new OptionButton(pX, pY, pWidth, 20, this, this.stringify.apply(this), button -> {
            this.toggle();
            button.setMessage(this.stringify.apply(this));
        });
    }
}
