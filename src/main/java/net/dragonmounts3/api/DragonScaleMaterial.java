package net.dragonmounts3.api;

import net.dragonmounts3.init.DMItems;
import net.dragonmounts3.init.DragonTypes;
import net.dragonmounts3.item.DragonScalesItem;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

import static net.dragonmounts3.DragonMounts.MOD_ID;

public class DragonScaleMaterial implements IArmorMaterial, IDragonTypified {
    public static final DragonScaleMaterial AETHER;
    public static final DragonScaleMaterial ENCHANT;
    public static final DragonScaleMaterial ENDER;
    public static final DragonScaleMaterial FIRE;
    public static final DragonScaleMaterial FOREST;
    public static final DragonScaleMaterial ICE;
    public static final DragonScaleMaterial MOONLIGHT;
    public static final DragonScaleMaterial NETHER;
    public static final DragonScaleMaterial SCULK;
    public static final DragonScaleMaterial STORM;
    public static final DragonScaleMaterial SUNLIGHT;
    public static final DragonScaleMaterial TERRA;
    public static final DragonScaleMaterial WATER;
    public static final DragonScaleMaterial ZOMBIE;

    static {
        Builder builder = new Builder(0x03080703, 50)
                .setEnchantmentValue(11)
                .setToughness(7.0F);
        MOONLIGHT = builder.build(DragonTypes.MOONLIGHT);
        STORM = builder.build(DragonTypes.STORM);
        TERRA = builder.build(DragonTypes.TERRA);
        ZOMBIE = builder.build(DragonTypes.ZOMBIE);
        builder = new Builder(0x04080704, 50)
                .setEnchantmentValue(11)
                .setToughness(7.0F);
        AETHER = builder.build(DragonTypes.AETHER);
        FIRE = builder.build(DragonTypes.FIRE);
        FOREST = builder.build(DragonTypes.FOREST);
        ICE = builder.build(DragonTypes.ICE);
        SUNLIGHT = builder.build(DragonTypes.SUNLIGHT);
        WATER = builder.build(DragonTypes.WATER);
        ENCHANT = builder.setEnchantmentValue(30).build(DragonTypes.ENCHANT);
        builder = new Builder(0x04090704, 70)
                .setEnchantmentValue(11)
                .setToughness(9.0F);
        ENDER = builder.build(DragonTypes.ENDER);
        SCULK = builder.build(DragonTypes.SCULK);
        NETHER = new Builder(0x04090704, 55)
                .setEnchantmentValue(11)
                .setToughness(8.0F)
                .build(DragonTypes.NETHER);
    }

    private static final int SHIELD_DURABILITY = 50;
    private static final int[] SLOT_BASED_DURABILITY = new int[]{13, 15, 16, 11};
    private final String name;
    private final DragonType type;
    private final int protection;
    private final int durabilityFactor;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairIngredient;

    public DragonScaleMaterial(String namespace, DragonType type, Builder builder) {
        ResourceLocation key = type.getRegistryName();
        this.name = namespace + ':' + (key == null ? DragonType.DEFAULT_KEY : key).getPath();
        this.type = type;
        this.protection = builder.protection;
        this.durabilityFactor = builder.durabilityFactor;
        this.enchantmentValue = builder.enchantmentValue;
        this.sound = builder.sound;
        this.toughness = builder.toughness;
        this.knockbackResistance = builder.knockbackResistance;
        this.repairIngredient = builder.repairIngredient == null ? new LazyValue<>(() -> Ingredient.of(new ItemStack(type.getInstance(DragonScalesItem.class, DMItems.ENDER_DRAGON_SCALES.get())))) : builder.repairIngredient;
    }

    public int getDurabilityForShield() {
        return SHIELD_DURABILITY * this.durabilityFactor;
    }


    @Override
    public int getDurabilityForSlot(EquipmentSlotType slot) {
        return SLOT_BASED_DURABILITY[slot.getIndex()] * this.durabilityFactor;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType slot) {
        return this.protection >> (slot.getIndex() << 3) & 0xFF;
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

    public static class Builder {
        public final int protection;
        public final int durabilityFactor;
        public int enchantmentValue = 1;
        public SoundEvent sound = SoundEvents.ARMOR_EQUIP_GOLD;
        public float toughness = 0;
        public float knockbackResistance = 0;
        public LazyValue<Ingredient> repairIngredient = null;

        public Builder(int protection, int durabilityFactor) {
            this.protection = protection;
            this.durabilityFactor = durabilityFactor;
        }

        public Builder setEnchantmentValue(int enchantmentValue) {
            this.enchantmentValue = enchantmentValue;
            return this;
        }

        public Builder setSound(SoundEvent sound) {
            this.sound = sound;
            return this;
        }

        public Builder setToughness(float toughness) {
            this.toughness = toughness;
            return this;
        }

        public Builder setKnockbackResistance(float knockbackResistance) {
            this.knockbackResistance = knockbackResistance;
            return this;
        }

        public Builder setRepairIngredient(Supplier<Ingredient> supplier) {
            this.repairIngredient = new LazyValue<>(supplier);
            return this;
        }

        public DragonScaleMaterial build(DragonType type) {
            return new DragonScaleMaterial(MOD_ID, type, this);
        }

        public DragonScaleMaterial build(String namespace, DragonType type) {
            return new DragonScaleMaterial(namespace, type, this);
        }
    }
}
