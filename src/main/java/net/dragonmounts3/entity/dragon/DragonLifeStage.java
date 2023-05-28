package net.dragonmounts3.entity.dragon;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static net.dragonmounts3.util.TimeUtil.TICKS_PER_GAME_HOUR;

public enum DragonLifeStage implements IStringSerializable {
    NEWBORN(48 * TICKS_PER_GAME_HOUR, 0.04f, 0.09f),
    INFANT(24 * TICKS_PER_GAME_HOUR, 0.10f, 0.18f),
    PREJUVENILE(32 * TICKS_PER_GAME_HOUR, 0.19f, 0.60f),
    JUVENILE(60 * TICKS_PER_GAME_HOUR, 0.61f, 0.99f),
    ADULT(0, 1.00f, 1.00f);
    public static final String DATA_PARAMETER_KEY = "LifeStage";
    public static final String EGG_TRANSLATION_KEY = "dragon.life_stage.egg";
    private static final DragonLifeStage[] VALUES = values();
    private static final Map<String, DragonLifeStage> BY_NAME = Arrays.stream(VALUES).collect(Collectors.toMap(DragonLifeStage::getSerializedName, (value) -> value));
    public final int duration;
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
        return id < 0 || id >= VALUES.length ? DragonLifeStage.ADULT : VALUES[id];
    }

    public static DragonLifeStage byName(String string) {
        DragonLifeStage value = BY_NAME.get(string);
        return value == null ? ADULT : value;
    }

    public static float getSize(DragonLifeStage stage, int age) {
        return stage.duration == 0 ? 1.00f : MathHelper.lerp((float) Math.abs(age) / stage.duration, stage.endSize, stage.startSize);
    }
}
