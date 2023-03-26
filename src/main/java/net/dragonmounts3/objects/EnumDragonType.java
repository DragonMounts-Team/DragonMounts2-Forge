package net.dragonmounts3.objects;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public enum EnumDragonType {
    AETHER(TextFormatting.DARK_AQUA, EnumDragonItemTier.AETHER, EnumDragonArmorMaterial.AETHER),
    WATER(TextFormatting.BLUE, EnumDragonItemTier.WATER, EnumDragonArmorMaterial.WATER),
    ICE(TextFormatting.AQUA, EnumDragonItemTier.ICE, EnumDragonArmorMaterial.ICE),
    FIRE(TextFormatting.RED, EnumDragonItemTier.FIRE, EnumDragonArmorMaterial.FIRE),
    FOREST(TextFormatting.DARK_GREEN, EnumDragonItemTier.FOREST, EnumDragonArmorMaterial.FOREST),
    SKELETON(TextFormatting.WHITE, EnumDragonItemTier.UNKNOWN, EnumDragonArmorMaterial.UNKNOWN),
    WITHER(TextFormatting.DARK_GRAY, EnumDragonItemTier.UNKNOWN, EnumDragonArmorMaterial.UNKNOWN),
    NETHER(TextFormatting.DARK_RED, EnumDragonItemTier.NETHER, EnumDragonArmorMaterial.NETHER),
    ENDER(TextFormatting.DARK_PURPLE, EnumDragonItemTier.ENDER, EnumDragonArmorMaterial.ENDER),
    ENCHANT(TextFormatting.LIGHT_PURPLE, EnumDragonItemTier.ENCHANT, EnumDragonArmorMaterial.ENCHANT),
    SUNLIGHT(TextFormatting.YELLOW, EnumDragonItemTier.SUNLIGHT, EnumDragonArmorMaterial.SUNLIGHT),
    MOONLIGHT(TextFormatting.BLUE, EnumDragonItemTier.MOONLIGHT, EnumDragonArmorMaterial.MOONLIGHT),
    STORM(TextFormatting.BLUE, EnumDragonItemTier.STORM, EnumDragonArmorMaterial.STORM),
    TERRA(TextFormatting.GOLD, EnumDragonItemTier.TERRA, EnumDragonArmorMaterial.TERRA),
    GHOST(TextFormatting.YELLOW, EnumDragonItemTier.UNKNOWN, EnumDragonArmorMaterial.UNKNOWN),
    ZOMBIE(TextFormatting.DARK_GREEN, EnumDragonItemTier.ZOMBIE, EnumDragonArmorMaterial.ZOMBIE);
    //LIGHT(TextFormatting.GRAY);
    //DARK(TextFormatting.GRAY);
    //Specter(TextFormatting.WHITE);

    public final TextFormatting color;

    public final EnumDragonItemTier tier;

    public final EnumDragonArmorMaterial material;

    EnumDragonType(TextFormatting color, EnumDragonItemTier tier, EnumDragonArmorMaterial material) {
        this.color = color;
        this.tier = tier;
        this.material = material;
    }

    public ITextComponent getText() {
        return new TranslationTextComponent("dragon." + toString().toLowerCase()).withStyle(color);
    }
}