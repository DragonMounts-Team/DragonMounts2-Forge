package net.dragonmounts3.objects;

import net.dragonmounts3.inits.ModItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum EnumDragonItemTier implements IItemTier {
    UNKNOWN(4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.EMPTY),
    AETHER(5, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.AETHER_DRAGON_SCALES.get()))),
    WATER(4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.WATER_DRAGON_SCALES.get()))),
    ICE(4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.ICE_DRAGON_SCALES.get()))),
    FIRE(4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.FIRE_DRAGON_SCALES.get()))),
    FOREST(4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.FOREST_DRAGON_SCALES.get()))),
    NETHER(5, 2700, 8.0F, 6.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.NETHER_DRAGON_SCALES.get()))),
    ENDER(5, 3000, 8.0F, 6.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.ENDER_DRAGON_SCALES.get()))),
    ENCHANT(4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.ENCHANT_DRAGON_SCALES.get()))),
    SUNLIGHT(4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.SUNLIGHT_DRAGON_SCALES.get()))),
    MOONLIGHT(4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.MOONLIGHT_DRAGON_SCALES.get()))),
    STORM(4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.STORM_DRAGON_SCALES.get()))),
    TERRA(4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.TERRA_DRAGON_SCALES.get()))),
    ZOMBIE(4, 2700, 8.0F, 5.0F, 11, () -> Ingredient.of(new ItemStack(ModItems.ZOMBIE_DRAGON_SCALES.get())));
    final int level;
    final int uses;
    final float speed;
    final float damage;
    final int enchantmentValue;
    final LazyValue<Ingredient> repairIngredient;

    EnumDragonItemTier(
            int level,
            int uses,
            float speed,
            float damage,
            int enchantmentValue,
            Supplier<Ingredient> repairIngredient
    ) {
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

    @Nonnull
    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
