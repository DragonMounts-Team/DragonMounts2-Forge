package net.dragonmounts3.item;

import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.IDragonTypified;
import net.dragonmounts3.inits.ModItems;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nonnull;

public enum DragonScaleMaterial implements IArmorMaterial, IDragonTypified {
    AETHER(DragonType.AETHER, 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0),
    ENCHANT(DragonType.ENCHANT, 50, new int[]{4, 7, 8, 4}, 30, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0),
    ENDER(DragonType.ENDER, 70, new int[]{4, 7, 9, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 9.0F, 0),
    FIRE(DragonType.FIRE, 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0),
    FOREST(DragonType.FOREST, 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0),
    ICE(DragonType.ICE, 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0),
    MOONLIGHT(DragonType.MOONLIGHT, 50, new int[]{3, 7, 8, 3}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0),
    NETHER(DragonType.NETHER, 55, new int[]{4, 7, 9, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 8.0F, 0),
    SCULK(DragonType.SCULK, 70, new int[]{4, 7, 9, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 9.0F, 0),
    STORM(DragonType.STORM, 50, new int[]{3, 7, 8, 3}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0),
    SUNLIGHT(DragonType.SUNLIGHT, 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0),
    TERRA(DragonType.TERRA, 50, new int[]{3, 7, 8, 3}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0),
    WATER(DragonType.WATER, 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0),
    ZOMBIE(DragonType.ZOMBIE, 50, new int[]{3, 7, 8, 3}, 11, SoundEvents.ARMOR_EQUIP_GOLD, 7.0F, 0);

    DragonScaleMaterial(
            DragonType type,
            int durabilityMultiplier,
            int[] slotProtections,
            int enchantmentValue,
            SoundEvent sound,
            float toughness,
            float knockbackResistance
    ) {
        this.type = type;
        this.name = name().toLowerCase() + "_dragon_scale";
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = new LazyValue<>(() -> Ingredient.of(new ItemStack(ModItems.DRAGON_SCALES.get(type))));
    }

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final DragonType type;
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairIngredient;

    public int getDurabilityForShield() {
        return 50 * this.durabilityMultiplier;
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

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}
