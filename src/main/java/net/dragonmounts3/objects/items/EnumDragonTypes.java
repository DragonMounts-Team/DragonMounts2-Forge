package net.dragonmounts3.objects.items;

import net.dragonmounts3.inits.ModItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.text.TextFormatting;

import java.util.function.Supplier;

public enum EnumDragonTypes implements IItemTier {
    AETHER(TextFormatting.DARK_AQUA, 5, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.AetherDragonScales.get()))),
    WATER(TextFormatting.BLUE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.WaterDragonScales.get()))),
    SYLPHID(TextFormatting.BLUE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.WaterDragonScales.get()))),//{{sic}}: Register Sylphid for other purposes
    ICE(TextFormatting.AQUA, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.IceDragonScales.get()))),
    FIRE(TextFormatting.RED, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.FireDragonScales.get()))),
    FIRE2(TextFormatting.GOLD, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.FireDragonScales2.get()))),
    FOREST(TextFormatting.DARK_GREEN, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.ForestDragonScales.get()))),
    SKELETON(TextFormatting.WHITE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.EMPTY),//temporary data
    WITHER(TextFormatting.DARK_GRAY, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.EMPTY),//temporary data
    NETHER(TextFormatting.DARK_RED, 5, 2700, 8.0F, 6.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.NetherDragonScales.get()))),
    NETHER2(TextFormatting.DARK_RED, 5, 2700, 8.0F, 6.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.NetherDragonScales2.get()))),
    END(TextFormatting.DARK_PURPLE, 5, 3000, 8.0F, 6.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.EnderDragonScales.get()))),
    ENCHANT(TextFormatting.LIGHT_PURPLE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.EnchantDragonScales.get()))),
    SUNLIGHT(TextFormatting.YELLOW, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.SunlightDragonScales.get()))),
    SUNLIGHT2(TextFormatting.LIGHT_PURPLE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.SunlightDragonScales2.get()))),
    MOONLIGHT(TextFormatting.BLUE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.MoonlightDragonScales.get()))),
    STORM(TextFormatting.BLUE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.StormDragonScales.get()))),
    STORM2(TextFormatting.BLUE, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.StormDragonScales2.get()))),
    TERRA(TextFormatting.GOLD, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.TerraDragonScales.get()))),
    TERRA2(TextFormatting.GOLD, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.TerraDragonScales2.get()))),
    GHOST(TextFormatting.YELLOW, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.EMPTY),//temporary data
    ZOMBIE(TextFormatting.DARK_GREEN, 4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.ZombieDragonScales.get())));
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

}