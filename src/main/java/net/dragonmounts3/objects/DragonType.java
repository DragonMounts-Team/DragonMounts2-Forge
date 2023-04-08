package net.dragonmounts3.objects;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.LazyValue;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

import static net.dragonmounts3.inits.ModItems.*;

//This enumeration will be initialized before the constants
@SuppressWarnings({"Convert2MethodRef", "FunctionalExpressionCanBeFolded"})
public enum DragonType implements IStringSerializable {
    AETHER(TextFormatting.AQUA, () -> AETHER_DRAGON_AMULET.get(), () -> AETHER_DRAGON_SCALES.get()),
    ENCHANT(TextFormatting.LIGHT_PURPLE, () -> ENCHANT_DRAGON_AMULET.get(), () -> ENCHANT_DRAGON_SCALES.get()),
    ENDER(TextFormatting.DARK_PURPLE, () -> ENDER_DRAGON_AMULET.get(), () -> ENDER_DRAGON_SCALES.get()),
    FIRE(TextFormatting.RED, () -> FIRE_DRAGON_AMULET.get(), () -> FIRE_DRAGON_SCALES.get()),
    FOREST(TextFormatting.DARK_GREEN, () -> FOREST_DRAGON_AMULET.get(), () -> FOREST_DRAGON_SCALES.get()),
    GHOST(TextFormatting.YELLOW, DragonType::EMPTY),
    ICE(TextFormatting.WHITE, () -> ICE_DRAGON_AMULET.get(), () -> ICE_DRAGON_SCALES.get()),
    MOONLIGHT(TextFormatting.DARK_BLUE, () -> MOONLIGHT_DRAGON_AMULET.get(), () -> MOONLIGHT_DRAGON_SCALES.get()),
    NETHER(TextFormatting.DARK_RED, () -> NETHER_DRAGON_AMULET.get(), () -> NETHER_DRAGON_SCALES.get()),
    SCULK(TextFormatting.BLUE, () -> SCULK_DRAGON_AMULET.get(), () -> SCULK_DRAGON_SCALES.get()),
    SKELETON(TextFormatting.GRAY, () -> SKELETON_DRAGON_AMULET.get(), DragonType::EMPTY),
    STORM(TextFormatting.GRAY, () -> STORM_DRAGON_AMULET.get(), () -> STORM_DRAGON_SCALES.get()),
    SUNLIGHT(TextFormatting.YELLOW, () -> SUNLIGHT_DRAGON_AMULET.get(), () -> SUNLIGHT_DRAGON_SCALES.get()),
    TERRA(TextFormatting.GOLD, () -> TERRA_DRAGON_AMULET.get(), () -> TERRA_DRAGON_SCALES.get()),
    WATER(TextFormatting.DARK_AQUA, () -> WATER_DRAGON_AMULET.get(), () -> WATER_DRAGON_SCALES.get()),
    WITHER(TextFormatting.DARK_GRAY, () -> WITHER_DRAGON_AMULET.get(), DragonType::EMPTY),
    ZOMBIE(TextFormatting.DARK_GREEN, () -> ZOMBIE_DRAGON_AMULET.get(), () -> ZOMBIE_DRAGON_SCALES.get());

    DragonType(TextFormatting style, Supplier<Item> amulet, Supplier<Item> scales) {
        this.style = style;
        this.amulet = new LazyValue<>(amulet);
        this.scales = new LazyValue<>(scales);
        this.name = name().toLowerCase();
        this.text = "dragon." + this.name;
    }

    DragonType(TextFormatting style, Supplier<Item> amulet) {
        this(style, amulet, DragonType::EMPTY);
    }

    public final TextFormatting style;
    private final LazyValue<Item> amulet;
    private final LazyValue<Item> scales;
    public final String name;
    private final String text;

    public Item getAmulet() {
        return this.amulet.get();
    }

    public Item getScales() {
        return this.scales.get();
    }

    public ITextComponent getText() {
        return new TranslationTextComponent(this.text).withStyle(this.style);
    }

    private static Item EMPTY() {
        return Items.AIR;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }
}
