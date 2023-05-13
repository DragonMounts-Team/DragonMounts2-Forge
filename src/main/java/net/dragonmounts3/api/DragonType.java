package net.dragonmounts3.api;

import net.dragonmounts3.entity.dragon.config.*;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum DragonType implements IStringSerializable {
    AETHER(0x0294BD, new AetherDragonConfig()),
    ENCHANT(0x8359AE, new EnchantDragonConfig()),
    ENDER(0xAB39BE, new EnderDragonConfig()),
    FIRE(0x960B0F, new FireDragonConfig()),
    FOREST(0x298317, new ForestDragonConfig()),
    ICE(0x00F2FF, new IceDragonConfig()),
    MOONLIGHT(0x2C427C, new MoonlightDragonConfig()),
    NETHER(0xE5B81B, new NetherDragonConfig()),
    SCULK(0x29DFEB, new SculkDragonConfig()),
    SKELETON(0xFFFFFF, new SkeletonDragonConfig()),
    STORM(0xF5F1E9, new StormDragonConfig()),
    SUNLIGHT(0xFFDE00, new SunlightDragonConfig()),
    TERRA(0xA56C21, new TerraDragonConfig()),
    WATER(0x4F69A8, new WaterDragonConfig()),
    WITHER(0x50260A, new WitherDragonConfig()),
    ZOMBIE(0x5A5602, new ZombieDragonConfig());

    DragonType(int color, DragonConfig config) {
        this.style = Style.EMPTY.withColor(Color.fromRgb(color));
        this.color = color;
        this.config = config;
        this.name = this.name().toLowerCase();
        this.text = "dragon.type." + this.name;
    }

    private static final DragonType[] VALUES = values();
    private static final Map<String, DragonType> BY_NAME = Arrays.stream(VALUES).collect(Collectors.toMap(DragonType::getSerializedName, (value) -> value));
    public static final String DATA_PARAMETER_KEY = "DragonType";

    public static DragonType byId(int id) {
        if (id < 0 || id >= VALUES.length) {
            return DragonType.ENDER;
        }
        return VALUES[id];
    }

    public static DragonType byName(String string) {
        DragonType value = BY_NAME.get(string);
        return value == null ? ENDER : value;
    }


    public static boolean isSkeleton(DragonType type) {
        return type == DragonType.SKELETON || type == DragonType.WITHER;
    }

    public final Style style;
    public final DragonConfig config;
    public final String name;
    private final String text;
    public final int color;

    public DragonConfig getConfig() {
        return this.config;
    }

    public ITextComponent getText() {
        return new TranslationTextComponent(this.text).withStyle(this.style);
    }

    public int getColor() {
        return this.color;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }
}
