package net.dragonmounts.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonmounts.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.List;

import static net.dragonmounts.client.gui.LazyBooleanConfigOption.DEFAULT_STRINGIFY;
import static net.dragonmounts.client.gui.LazyBooleanConfigOption.TOGGLE_STRINGIFY;
import static net.minecraft.client.gui.screen.SettingsScreen.tooltipAt;


public class DMConfigScreen extends Screen {
    protected final Screen parent;
    protected OptionsRowList list;
    protected LazyBooleanConfigOption debug;
    protected LazySliderConfigOption cameraDistance;
    protected LazySliderConfigOption cameraOffset;
    protected LazyBooleanConfigOption convergePitch;
    protected LazyBooleanConfigOption convergeYaw;
    protected LazyBooleanConfigOption hoverAnimation;
    protected LazyBooleanConfigOption redirectInventory;
    protected LazyBooleanConfigOption toggleDescent;

    public DMConfigScreen(Minecraft minecraft, Screen parent) {
        super(new TranslationTextComponent("options.dragonmounts.config"));
        this.parent = parent;
        this.minecraft = minecraft;
    }

    protected void init() {
        ClientConfig config = ClientConfig.INSTANCE;
        this.debug = new LazyBooleanConfigOption("options.dragonmounts.debug", config.debug, DEFAULT_STRINGIFY, null);
        TranslationTextComponent cameraNote = new TranslationTextComponent("options.dragonmounts.camera.note");
        this.cameraDistance = new LazySliderConfigOption("options.dragonmounts.camera_distance", config.camera_distance, 0.0D, 64.0D, 0.25D, LazySliderConfigOption.STRINGIFY_X_2F, cameraNote);
        this.cameraOffset = new LazySliderConfigOption("options.dragonmounts.camera_offset", config.camera_offset, -16.0D, 16.0D, 0.25D, LazySliderConfigOption.STRINGIFY_X_2F, cameraNote);
        this.convergePitch = new LazyBooleanConfigOption("options.dragonmounts.converge_pitch_angle", config.converge_pitch_angle, DEFAULT_STRINGIFY, null);
        this.convergeYaw = new LazyBooleanConfigOption("options.dragonmounts.converge_yaw_angle", config.converge_yaw_angle, DEFAULT_STRINGIFY, null);
        this.hoverAnimation = new LazyBooleanConfigOption("options.dragonmounts.hover_animation", config.hover_animation, DEFAULT_STRINGIFY, null);
        this.redirectInventory = new LazyBooleanConfigOption("options.dragonmounts.redirect_inventory", config.redirect_inventory, DEFAULT_STRINGIFY, new TranslationTextComponent("options.dragonmounts.redirect_inventory.note"));
        this.toggleDescent = new LazyBooleanConfigOption("key.dragonmounts.descent", config.toggle_descent, TOGGLE_STRINGIFY, null);
        //noinspection DataFlowIssue
        this.list = new OptionsRowList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
        this.list.addBig(this.cameraDistance);
        this.list.addBig(this.cameraOffset);
        this.list.addSmall(this.debug, this.toggleDescent);
        this.list.addSmall(this.convergePitch, this.convergeYaw);
        this.list.addSmall(this.hoverAnimation, this.redirectInventory);
        this.children.add(this.list);
        this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, DialogTexts.GUI_DONE, $ -> this.onClose()));
    }

    @Override
    public void onClose() {
        //noinspection DataFlowIssue
        this.minecraft.setScreen(this.parent);
    }

    @Override
    public void removed() {
        this.debug.save();
        this.cameraDistance.save();
        this.cameraOffset.save();
        this.convergePitch.save();
        this.convergeYaw.save();
        this.hoverAnimation.save();
        this.redirectInventory.save();
        this.toggleDescent.save();
    }

    @Override
    public void render(@Nonnull MatrixStack matrices, int x, int y, float ticks) {
        this.renderBackground(matrices, 0);
        this.list.render(matrices, x, y, ticks);
        drawCenteredString(matrices, this.font, this.title.copy(), this.width >> 1, 20, 0xFFFFFF);
        super.render(matrices, x, y, ticks);
        if (this.isDragging()) return;
        List<IReorderingProcessor> list = tooltipAt(this.list, x, y);
        if (list != null) {
            this.renderTooltip(matrices, list, x, y);
        }
    }
}
