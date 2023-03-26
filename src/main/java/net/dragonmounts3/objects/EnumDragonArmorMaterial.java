package net.dragonmounts3.objects;

import net.dragonmounts3.inits.ModItems;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum EnumDragonArmorMaterial implements IArmorMaterial {
    UNKNOWN(50, new int[]{3, 7, 8, 3}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0, () -> Ingredient.EMPTY),
    AETHER(50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0, () -> Ingredient.of(new ItemStack(ModItems.AETHER_DRAGON_SCALES.get()))),
    WATER(50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0, () -> Ingredient.of(new ItemStack(ModItems.WATER_DRAGON_SCALES.get()))),
    ICE(50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0, () -> Ingredient.of(new ItemStack(ModItems.ICE_DRAGON_SCALES.get()))),
    FIRE(50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0, () -> Ingredient.of(new ItemStack(ModItems.FIRE_DRAGON_SCALES.get()))),
    FOREST(50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0, () -> Ingredient.of(new ItemStack(ModItems.FOREST_DRAGON_SCALES.get()))),
    NETHER(55, new int[]{4, 7, 9, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 8.0F, 0, () -> Ingredient.of(new ItemStack(ModItems.NETHER_DRAGON_SCALES.get()))),
    ENDER(70, new int[]{4, 7, 9, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 9.0F, 0, () -> Ingredient.of(new ItemStack(ModItems.ENDER_DRAGON_SCALES.get()))),
    ENCHANT(50, new int[]{4, 7, 8, 4}, 30, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0, () -> Ingredient.of(new ItemStack(ModItems.ENCHANT_DRAGON_SCALES.get()))),
    SUNLIGHT(50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0, () -> Ingredient.of(new ItemStack(ModItems.SUNLIGHT_DRAGON_SCALES.get()))),
    MOONLIGHT(50, new int[]{3, 7, 8, 3}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0, () -> Ingredient.of(new ItemStack(ModItems.MOONLIGHT_DRAGON_SCALES.get()))),
    STORM(50, new int[]{3, 7, 8, 3}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0, () -> Ingredient.of(new ItemStack(ModItems.STORM_DRAGON_SCALES.get()))),
    TERRA(50, new int[]{3, 7, 8, 3}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0, () -> Ingredient.of(new ItemStack(ModItems.TERRA_DRAGON_SCALES.get()))),
    ZOMBIE(50, new int[]{3, 7, 8, 3}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0, () -> Ingredient.of(new ItemStack(ModItems.ZOMBIE_DRAGON_SCALES.get())));
    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairIngredient;

    EnumDragonArmorMaterial(int durabilityMultiplier, int[] slotProtections, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = toString().toLowerCase() + "_dragonscale";
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = new LazyValue<>(repairIngredient);
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlotType slot) {
        return HEALTH_PER_SLOT[slot.getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType slot) {
        return this.slotProtections[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Nonnull
    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Nonnull
    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Nonnull
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
