package net.dragonmounts3.objects.items;

import net.dragonmounts3.inits.ModItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Supplier;

public enum EnumDragonTypes implements IItemTier {
    AETHER(TextFormatting.DARK_AQUA, 5, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.AETHER_DRAGON_SCALES.get()))),
    WATER(TextFormatting.BLUE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.WATER_DRAGON_SCALES.get()))),
    SYLPHID(TextFormatting.BLUE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.WATER_DRAGON_SCALES.get()))),//{{sic}}: Register Sylphid for other purposes
    ICE(TextFormatting.AQUA, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.ICE_DRAGON_SCALES.get()))),
    FIRE(TextFormatting.RED, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.FIRE_DRAGON_SCALES.get()), new ItemStack(ModItems.FIRE_DRAGON_SCALES_2.get()))),
    FIRE2(TextFormatting.GOLD, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.FIRE_DRAGON_SCALES.get()), new ItemStack(ModItems.FIRE_DRAGON_SCALES_2.get()))),
    FOREST(TextFormatting.DARK_GREEN, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.FOREST_DRAGON_SCALES.get()))),
    SKELETON(TextFormatting.WHITE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.EMPTY),//temporary data
    WITHER(TextFormatting.DARK_GRAY, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.EMPTY),//temporary data
    NETHER(TextFormatting.DARK_RED, 5, 2700, 8.0F, 6.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.NETHER_DRAGON_SCALES.get()), new ItemStack(ModItems.NETHER_DRAGON_SCALES_2.get()))),
    NETHER2(TextFormatting.DARK_RED, 5, 2700, 8.0F, 6.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.NETHER_DRAGON_SCALES.get()), new ItemStack(ModItems.NETHER_DRAGON_SCALES_2.get()))),
    END(TextFormatting.DARK_PURPLE, 5, 3000, 8.0F, 6.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.ENDER_DRAGON_SCALES.get()))),
    ENCHANT(TextFormatting.LIGHT_PURPLE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.ENCHANT_DRAGON_SCALES.get()))),
    SUNLIGHT(TextFormatting.YELLOW, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.SUNLIGHT_DRAGON_SCALES.get()), new ItemStack(ModItems.SUNLIGHT_DRAGON_SCALES_2.get()))),
    SUNLIGHT2(TextFormatting.LIGHT_PURPLE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.SUNLIGHT_DRAGON_SCALES.get()), new ItemStack(ModItems.SUNLIGHT_DRAGON_SCALES_2.get()))),
    MOONLIGHT(TextFormatting.BLUE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.MOONLIGHT_DRAGON_SCALES.get()))),
    STORM(TextFormatting.BLUE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.STORM_DRAGON_SCALES.get()), new ItemStack(ModItems.STORM_DRAGON_SCALES_2.get()))),
    STORM2(TextFormatting.BLUE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.STORM_DRAGON_SCALES.get()), new ItemStack(ModItems.STORM_DRAGON_SCALES_2.get()))),
    TERRA(TextFormatting.GOLD, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.TERRA_DRAGON_SCALES.get()), new ItemStack(ModItems.TERRA_DRAGON_SCALES_2.get()))),
    TERRA2(TextFormatting.GOLD, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.TERRA_DRAGON_SCALES.get()), new ItemStack(ModItems.TERRA_DRAGON_SCALES_2.get()))),
    GHOST(TextFormatting.YELLOW, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.EMPTY),//temporary data
    ZOMBIE(TextFormatting.DARK_GREEN, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.ZOMBIE_DRAGON_SCALES.get())));
    //LIGHT(TextFormatting.GRAY);
    //DARK(TextFormatting.GRAY);
    //Specter(TextFormatting.WHITE);

    public final TextFormatting color;
    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyValue<Ingredient> repairIngredient;

    EnumDragonTypes(
            TextFormatting color,
            int level,
            int uses,
            float speed,
            float damage,
            int enchantmentValue,
            Supplier<Ingredient> repairIngredient
    ) {
        this.color = color;
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyValue<>(repairIngredient);
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public ITextComponent getName() {
        return new TranslationTextComponent("dragon." + toString().toLowerCase()).withStyle(color);
    }
}