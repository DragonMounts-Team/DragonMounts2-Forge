package net.dragonmounts.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts.util.DoubleRuleValue;
import net.minecraft.client.gui.screen.EditGamerulesScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import java.util.List;

public class DoubleRuleEntry extends EditGamerulesScreen.ValueEntry {
    private final TextFieldWidget input;

    public DoubleRuleEntry(EditGamerulesScreen screen, ITextComponent label, List<IReorderingProcessor> tooltip, String description, DoubleRuleValue rule) {
        screen.super(tooltip, label);
        this.input = new TextFieldWidget(screen.getMinecraft().font, 10, 5, 42, 20, label.copy().append("\n").append(description).append("\n"));
        this.input.setValue(Double.toString(rule.get()));
        this.input.setResponder((text) -> {
            if (rule.tryDeserialize(text)) {
                this.input.setTextColor(14737632);
                screen.clearInvalid(this);
            } else {
                this.input.setTextColor(16711680);
                screen.markInvalid(this);
            }

        });
        this.children.add(this.input);
    }

    public void render(@Nonnull MatrixStack matrixStack, int index, int top, int left, int width, int height, int x, int y, boolean isMouseOver, float partialTicks) {
        this.renderLabel(matrixStack, top, left);
        this.input.x = left + width - 44;
        this.input.y = top;
        this.input.render(matrixStack, x, y, partialTicks);
    }
}
