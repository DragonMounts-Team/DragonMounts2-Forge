package net.dragonmounts.client.gui;

import net.minecraft.client.gui.IBidiTooltip;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class ConfigSliderWidget extends AbstractSlider implements IBidiTooltip {
    protected final LazySliderConfigOption option;

    public ConfigSliderWidget(int x, int y, int width, int height, LazySliderConfigOption option) {
        super(x, y, width, height, StringTextComponent.EMPTY, option.toPct(option.get()));
        this.option = option;
        this.updateMessage();
    }

    @Override
    protected void applyValue() {
        this.option.set(this.option.toValue(this.value));
    }

    @Override
    protected void updateMessage() {
        this.setMessage(this.option.getMessage());
    }

    @Nonnull
    @Override
    public Optional<List<IReorderingProcessor>> getTooltip() {
        return this.option.getTooltip();
    }
}
