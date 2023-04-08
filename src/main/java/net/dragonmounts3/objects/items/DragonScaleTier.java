package net.dragonmounts3.objects.items;

import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.objects.IDragonTypified;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import javax.annotation.Nonnull;

public enum DragonScaleTier implements IItemTier, IDragonTypified {
    AETHER(DragonType.AETHER, 5, 2700, 8.0F, 5.0F, 11),
    ENCHANT(DragonType.ENCHANT, 4, 2700, 8.0F, 5.0F, 11),
    ENDER(DragonType.ENDER, 5, 3000, 8.0F, 6.0F, 11),
    FIRE(DragonType.FIRE, 4, 2700, 8.0F, 5.0F, 11),
    FOREST(DragonType.FOREST, 4, 2700, 8.0F, 5.0F, 11),
    ICE(DragonType.ICE, 4, 2700, 8.0F, 5.0F, 11),
    MOONLIGHT(DragonType.MOONLIGHT, 4, 2700, 8.0F, 5.0F, 11),
    NETHER(DragonType.NETHER, 5, 2700, 8.0F, 6.0F, 11),
    SCULK(DragonType.SCULK, 5, 3000, 8.0F, 6.0F, 11),
    STORM(DragonType.STORM, 4, 2700, 8.0F, 5.0F, 11),
    SUNLIGHT(DragonType.SUNLIGHT, 4, 2700, 8.0F, 5.0F, 11),
    TERRA(DragonType.TERRA, 4, 2700, 8.0F, 5.0F, 11),
    WATER(DragonType.WATER, 4, 2700, 8.0F, 5.0F, 11),
    ZOMBIE(DragonType.ZOMBIE, 4, 2700, 8.0F, 5.0F, 11);

    DragonScaleTier(
            DragonType type,
            int level,
            int uses,
            float speed,
            float damage,
            int enchantmentValue
    ) {
        this.type = type;
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyValue<>(() -> Ingredient.of(new ItemStack(type.getScales())));
    }

    private final DragonType type;
    final int level;
    final int uses;
    final float speed;
    final float damage;
    final int enchantmentValue;
    final LazyValue<Ingredient> repairIngredient;

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

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}
