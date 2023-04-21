package net.dragonmounts3.objects;

import net.dragonmounts3.entity.dragon.config.*;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public enum DragonType implements IStringSerializable {
    AETHER(TextFormatting.AQUA, 0x0294BD, new AetherDragonConfig()),
    ENCHANT(TextFormatting.LIGHT_PURPLE, 0x8359AE, new EnchantDragonConfig()),
    ENDER(TextFormatting.DARK_PURPLE, 0xAB39BE, new EnderDragonConfig()),
    FIRE(TextFormatting.RED, 0x960B0F, new FireDragonConfig()),
    FOREST(TextFormatting.DARK_GREEN, 0x298317, new ForestDragonConfig()),
    ICE(TextFormatting.WHITE, 0x00F2FF, new IceDragonConfig()),
    MOONLIGHT(TextFormatting.DARK_BLUE, 0x2C427C, new MoonlightDragonConfig()),
    NETHER(TextFormatting.DARK_RED, 0xE5B81B, new NetherDragonConfig()),
    SCULK(TextFormatting.BLUE, 0x29DFEB, new SculkDragonConfig()),
    SKELETON(TextFormatting.GRAY, 0xFFFFFF, new SkeletonDragonConfig()),
    STORM(TextFormatting.GRAY, 0xF5F1E9, new StormDragonConfig()),
    SUNLIGHT(TextFormatting.YELLOW, 0xFFDE00, new SunlightDragonConfig()),
    TERRA(TextFormatting.GOLD, 0xA56C21, new TerraDragonConfig()),
    WATER(TextFormatting.DARK_AQUA, 0x4F69A8, new WaterDragonConfig()),
    WITHER(TextFormatting.DARK_GRAY, 0x50260A, new WitherDragonConfig()),
    ZOMBIE(TextFormatting.DARK_GREEN, 0x5A5602, new ZombieDragonConfig());

    DragonType(TextFormatting style, int color, DragonConfig config) {
        this.style = style;
        this.color = color;
        this.config = config;
        this.name = name().toLowerCase();
        this.text = "dragon." + this.name;
    }

    public static final String DATA_PARAMETER_KEY = "DragonType";

    public final TextFormatting style;
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
