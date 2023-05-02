package net.dragonmounts3.entity.dragon.config;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

import static net.dragonmounts3.util.TimeUtil.TICKS_PER_GAME_HOUR;

public enum DragonLifeStage implements IStringSerializable {
    ADULT(0, 1.00f, 1.00f),
    NEWBORN(48 * TICKS_PER_GAME_HOUR, 0.04f, 0.09f),
    INFANT(24 * TICKS_PER_GAME_HOUR, 0.10f, 0.18f),
    PREJUVENILE(32 * TICKS_PER_GAME_HOUR, 0.19f, 0.60f),
    JUVENILE(60 * TICKS_PER_GAME_HOUR, 0.61f, 0.99f);
    public static final String DATA_PARAMETER_KEY = "LifeStage";
    public static final String EGG_TRANSLATION_KEY = "dragon.life_stage.egg";
    private final int duration;
    private final float startSize;
    private final float endSize;
    private final String name;
    private final String text;

    DragonLifeStage(int duration, float startSize, float endSize) {
        this.duration = duration;
        this.startSize = startSize;
        this.endSize = endSize;
        this.name = this.name().toLowerCase();
        this.text = "dragon.life_stage." + this.name;
    }

    public ITextComponent getText() {
        return new TranslationTextComponent(this.text);
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static DragonLifeStage byId(int id) {
        DragonLifeStage[] values = DragonLifeStage.values();
        if (id < 0 || id >= values.length) {
            return DragonLifeStage.ADULT;
        }
        return values[id];
    }

    public static float getSize(DragonLifeStage stage, int age) {
        return stage == ADULT ? 1.00f : MathHelper.lerp(stage.startSize, stage.endSize, (float) Math.abs(age) / stage.duration);
    }
}
