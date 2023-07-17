package net.dragonmounts3.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

import static net.dragonmounts3.DragonMountsConfig.CLIENT;
import static net.dragonmounts3.DragonMountsConfig.COMMON;
import static net.dragonmounts3.client.gui.LazyBooleanConfigOption.DEFAULT_STRINGIFY;
import static net.dragonmounts3.client.gui.LazyBooleanConfigOption.TOGGLE_STRINGIFY;


public class DMConfigScreen extends Screen {
    protected final Screen parent;
    protected OptionsRowList list;

    protected LazyBooleanConfigOption debug;
    protected LazyBooleanConfigOption cameraControl;
    protected LazyBooleanConfigOption toggleDescent;
    protected LazySliderConfigOption thirdPersonZoom;

    public DMConfigScreen(Minecraft minecraft, Screen parent) {
        super(new TranslationTextComponent("options.dragonmounts.config"));
        this.parent = parent;
        this.minecraft = minecraft;
    }

    protected void init() {
        this.debug = new LazyBooleanConfigOption("options.dragonmounts.debug", COMMON.debug, DEFAULT_STRINGIFY, null);
        this.cameraControl = new LazyBooleanConfigOption("options.dragonmounts.camera_control", CLIENT.camera_control, DEFAULT_STRINGIFY, null);
        this.toggleDescent = new LazyBooleanConfigOption("key.dragonmounts.descent", CLIENT.toggle_descent, TOGGLE_STRINGIFY, null);
        this.thirdPersonZoom = new LazySliderConfigOption("options.dragonmounts.third_person_zoom", CLIENT.third_person_zoom, 0.0, 64.0, 0.25,
                button -> button.genericValueLabel(new StringTextComponent(String.format("%.2f", button.value)))
        );
        //noinspection DataFlowIssue
        this.list = new OptionsRowList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
        this.list.addSmall(this.cameraControl, this.thirdPersonZoom);
        this.list.addSmall(this.debug, this.toggleDescent);
        this.children.add(this.list);
        this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, DialogTexts.GUI_DONE, v -> this.onClose()));
    }

    @Override
    public void onClose() {
        //noinspection DataFlowIssue
        this.minecraft.setScreen(this.parent);
    }

    @Override
    public void removed() {
        this.debug.save();
        this.cameraControl.save();
        this.toggleDescent.save();
        this.thirdPersonZoom.save();
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack, 0);
        this.list.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title.copy(), this.width >> 1, 20, 0xFFFFFF);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
