package net.dragonmounts3.objects;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.LazyValue;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Supplier;

import static net.dragonmounts3.inits.ModItems.*;

//This enumeration will be initialized before the constants
@SuppressWarnings({"Convert2MethodRef", "FunctionalExpressionCanBeFolded"})
public enum DragonType {
    AETHER(TextFormatting.DARK_AQUA, () -> AETHER_DRAGON_SCALES.get()),
    ENCHANT(TextFormatting.LIGHT_PURPLE, () -> ENCHANT_DRAGON_SCALES.get()),
    ENDER(TextFormatting.DARK_PURPLE, () -> ENDER_DRAGON_SCALES.get()),
    FIRE(TextFormatting.RED, () -> FIRE_DRAGON_SCALES.get()),
    FOREST(TextFormatting.DARK_GREEN, () -> FOREST_DRAGON_SCALES.get()),
    GHOST(TextFormatting.YELLOW, DragonType::EMPTY),
    ICE(TextFormatting.AQUA, () -> ICE_DRAGON_SCALES.get()),
    MOONLIGHT(TextFormatting.BLUE, () -> MOONLIGHT_DRAGON_SCALES.get()),
    NETHER(TextFormatting.DARK_RED, () -> NETHER_DRAGON_SCALES.get()),
    SCULK(TextFormatting.AQUA, DragonType::EMPTY),
    SKELETON(TextFormatting.WHITE, DragonType::EMPTY),
    STORM(TextFormatting.BLUE, () -> STORM_DRAGON_SCALES.get()),
    SUNLIGHT(TextFormatting.YELLOW, () -> SUNLIGHT_DRAGON_SCALES.get()),
    TERRA(TextFormatting.GOLD, () -> TERRA_DRAGON_SCALES.get()),
    WATER(TextFormatting.BLUE, () -> WATER_DRAGON_SCALES.get()),
    WITHER(TextFormatting.DARK_GRAY, DragonType::EMPTY),
    ZOMBIE(TextFormatting.DARK_GREEN, () -> ZOMBIE_DRAGON_SCALES.get());

    DragonType(TextFormatting color, Supplier<Item> item) {
        this.color = color;
        this.item = new LazyValue<>(item);
        this.text = "dragon." + name().toLowerCase();
    }

    public final TextFormatting color;
    private final LazyValue<Item> item;
    private final String text;

    public Item getItem() {
        return this.item.get();
    }

    public ITextComponent getText() {
        return new TranslationTextComponent(this.text).withStyle(this.color);
    }

    private static Item EMPTY() {
        return Items.AIR;
    }
}
