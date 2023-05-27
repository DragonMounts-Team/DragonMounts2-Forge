package net.dragonmounts3.api;

import net.dragonmounts3.inits.ModItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class DragonScaleTier implements IItemTier, IDragonTypified {
    public static final DragonScaleTier AETHER;
    public static final DragonScaleTier ENCHANT;
    public static final DragonScaleTier ENDER;
    public static final DragonScaleTier FIRE;
    public static final DragonScaleTier FOREST;
    public static final DragonScaleTier ICE;
    public static final DragonScaleTier MOONLIGHT;
    public static final DragonScaleTier NETHER;
    public static final DragonScaleTier SCULK;
    public static final DragonScaleTier STORM;
    public static final DragonScaleTier SUNLIGHT;
    public static final DragonScaleTier TERRA;
    public static final DragonScaleTier WATER;
    public static final DragonScaleTier ZOMBIE;

    static {
        Builder builder = new Builder(4, 2700, 8.0F, 5.0F).setEnchantmentValue(11);
        FIRE = builder.build(DragonType.FIRE);
        FOREST = builder.build(DragonType.FOREST);
        ICE = builder.build(DragonType.ICE);
        MOONLIGHT = builder.build(DragonType.MOONLIGHT);
        STORM = builder.build(DragonType.STORM);
        SUNLIGHT = builder.build(DragonType.SUNLIGHT);
        TERRA = builder.build(DragonType.TERRA);
        WATER = builder.build(DragonType.WATER);
        ZOMBIE = builder.build(DragonType.ZOMBIE);
        ENCHANT = builder.setEnchantmentValue(30).build(DragonType.ENCHANT);
        builder = new Builder(5, 3000, 8.0F, 6.0F).setEnchantmentValue(11);
        ENDER = builder.build(DragonType.ENDER);
        SCULK = builder.build(DragonType.SCULK);
        AETHER = new Builder(5, 2700, 8.0F, 5.0F).setEnchantmentValue(11).build(DragonType.AETHER);
        NETHER = new Builder(5, 2700, 8.0F, 6.0F).setEnchantmentValue(11).build(DragonType.NETHER);
    }

    private final DragonType type;
    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyValue<Ingredient> repairIngredient;

    public DragonScaleTier(DragonType type, Builder builder) {
        this.type = type;
        this.level = builder.level;
        this.uses = builder.uses;
        this.speed = builder.speed;
        this.damage = builder.damage;
        this.enchantmentValue = builder.enchantmentValue;
        this.repairIngredient = builder.repairIngredient == null ? new LazyValue<>(() -> Ingredient.of(new ItemStack(ModItems.DRAGON_SCALES.get(type)))) : builder.repairIngredient;
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

    @Override
    public DragonType getDragonType() {
        return this.type;
    }

    public static class Builder {
        public final int level;
        public final int uses;
        public final float speed;
        public final float damage;
        public int enchantmentValue = 1;
        public LazyValue<Ingredient> repairIngredient = null;

        public Builder(int level, int uses, float speed, float damage) {
            this.level = level;
            this.uses = uses;
            this.speed = speed;
            this.damage = damage;
        }

        public Builder setEnchantmentValue(int enchantmentValue) {
            this.enchantmentValue = enchantmentValue;
            return this;
        }

        public Builder setRepairIngredient(Supplier<Ingredient> supplier) {
            this.repairIngredient = new LazyValue<>(supplier);
            return this;
        }

        public DragonScaleTier build(DragonType type) {
            return new DragonScaleTier(type, this);
        }

    }
}
/*
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
        this.repairIngredient = new LazyValue<>(() -> Ingredient.of(new ItemStack(ModItems.DRAGON_SCALES.get(type))));
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
}*/
