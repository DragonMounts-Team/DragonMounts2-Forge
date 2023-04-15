package net.dragonmounts3.objects;

import net.dragonmounts3.entity.dragon.config.AetherDragonConfig;
import net.dragonmounts3.entity.dragon.config.DragonConfig;
import net.dragonmounts3.entity.dragon.config.EnderDragonConfig;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public enum DragonType implements IStringSerializable {
    AETHER(TextFormatting.AQUA, new AetherDragonConfig()),
    ENCHANT(TextFormatting.LIGHT_PURPLE, new EnderDragonConfig()),
    ENDER(TextFormatting.DARK_PURPLE, new EnderDragonConfig()),
    FIRE(TextFormatting.RED, new EnderDragonConfig()),
    FOREST(TextFormatting.DARK_GREEN, new EnderDragonConfig()),
    GHOST(TextFormatting.YELLOW, new EnderDragonConfig()),
    ICE(TextFormatting.WHITE, new EnderDragonConfig()),
    MOONLIGHT(TextFormatting.DARK_BLUE, new EnderDragonConfig()),
    NETHER(TextFormatting.DARK_RED, new EnderDragonConfig()),
    SCULK(TextFormatting.BLUE, new EnderDragonConfig()),
    SKELETON(TextFormatting.GRAY, new EnderDragonConfig()),
    STORM(TextFormatting.GRAY, new EnderDragonConfig()),
    SUNLIGHT(TextFormatting.YELLOW, new EnderDragonConfig()),
    TERRA(TextFormatting.GOLD, new EnderDragonConfig()),
    WATER(TextFormatting.DARK_AQUA, new EnderDragonConfig()),
    WITHER(TextFormatting.DARK_GRAY, new EnderDragonConfig()),
    ZOMBIE(TextFormatting.DARK_GREEN, new EnderDragonConfig());

    DragonType(TextFormatting style, DragonConfig config) {
        this.style = style;
        this.config = config;
        this.name = name().toLowerCase();
        this.text = "dragon." + this.name;
    }

    public final TextFormatting style;
    public final DragonConfig config;
    public final String name;
    private final String text;

    public DragonConfig getConfig() {
        return this.config;
    }

    public ITextComponent getText() {
        return new TranslationTextComponent(this.text).withStyle(this.style);
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }
}
