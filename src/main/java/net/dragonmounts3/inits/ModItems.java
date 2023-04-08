package net.dragonmounts3.inits;

import mcp.MethodsReturnNonnullByDefault;
import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.objects.entity.carriage.CarriageType;
import net.dragonmounts3.objects.items.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.dragonmounts3.inits.ModItemGroups.*;

@MethodsReturnNonnullByDefault
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DragonMounts.create(ForgeRegistries.ITEMS);

    //Scales Start
    public static final RegistryObject<Item> FOREST_DRAGON_SCALES = createDragonScalesItem("forest_dragon_scales", DragonType.FOREST, item());
    public static final RegistryObject<Item> FIRE_DRAGON_SCALES = createDragonScalesItem("fire_dragon_scales", DragonType.FIRE, item());
    public static final RegistryObject<Item> ICE_DRAGON_SCALES = createDragonScalesItem("ice_dragon_scales", DragonType.ICE, item());
    public static final RegistryObject<Item> WATER_DRAGON_SCALES = createDragonScalesItem("water_dragon_scales", DragonType.WATER, item());
    public static final RegistryObject<Item> AETHER_DRAGON_SCALES = createDragonScalesItem("aether_dragon_scales", DragonType.AETHER, item());
    public static final RegistryObject<Item> NETHER_DRAGON_SCALES = createDragonScalesItem("nether_dragon_scales", DragonType.NETHER, item());
    public static final RegistryObject<Item> ENDER_DRAGON_SCALES = createDragonScalesItem("ender_dragon_scales", DragonType.ENDER, item());
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_SCALES = createDragonScalesItem("sunlight_dragon_scales", DragonType.SUNLIGHT, item());
    public static final RegistryObject<Item> ENCHANT_DRAGON_SCALES = createDragonScalesItem("enchant_dragon_scales", DragonType.ENCHANT, item());
    public static final RegistryObject<Item> STORM_DRAGON_SCALES = createDragonScalesItem("storm_dragon_scales", DragonType.STORM, item());
    public static final RegistryObject<Item> TERRA_DRAGON_SCALES = createDragonScalesItem("terra_dragon_scales", DragonType.TERRA, item());
    public static final RegistryObject<Item> ZOMBIE_DRAGON_SCALES = createDragonScalesItem("zombie_dragon_scales", DragonType.ZOMBIE, item());
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_SCALES = createDragonScalesItem("moonlight_dragon_scales", DragonType.MOONLIGHT, item());
    public static final RegistryObject<Item> SCULK_DRAGON_SCALES = createDragonScalesItem("sculk_dragon_scales", DragonType.SCULK, item().fireResistant());
    //Dragon Armor
    public static final RegistryObject<Item> IRON_DRAGON_ARMOR = createDragonArmorItem("iron_dragon_armor", 5, tool());
    public static final RegistryObject<Item> GOLDEN_DRAGON_ARMOR = createDragonArmorItem("golden_dragon_armor", 7, tool());
    public static final RegistryObject<Item> DIAMOND_DRAGON_ARMOR = createDragonArmorItem("diamond_dragon_armor", 11, tool());
    public static final RegistryObject<Item> EMERALD_DRAGON_ARMOR = createDragonArmorItem("emerald_dragon_armor", 11, tool());
    public static final RegistryObject<Item> NETHERITE_DRAGON_ARMOR = createDragonArmorItem("netherite_dragon_armor", 15, tool().fireResistant());
    //Dragon Scale Swords
    public static final RegistryObject<Item> AETHER_DRAGON_SWORD = createDragonSwordItem("aether_dragon_sword", DragonScaleTier.AETHER, tool());
    public static final RegistryObject<Item> WATER_DRAGON_SWORD = createDragonSwordItem("water_dragon_sword", DragonScaleTier.WATER, tool());
    public static final RegistryObject<Item> ICE_DRAGON_SWORD = createDragonSwordItem("ice_dragon_sword", DragonScaleTier.ICE, tool());
    public static final RegistryObject<Item> FIRE_DRAGON_SWORD = createDragonSwordItem("fire_dragon_sword", DragonScaleTier.FIRE, tool());
    public static final RegistryObject<Item> FOREST_DRAGON_SWORD = createDragonSwordItem("forest_dragon_sword", DragonScaleTier.FOREST, tool());
    public static final RegistryObject<Item> NETHER_DRAGON_SWORD = createDragonSwordItem("nether_dragon_sword", DragonScaleTier.NETHER, tool());
    public static final RegistryObject<Item> ENDER_DRAGON_SWORD = createDragonSwordItem("ender_dragon_sword", DragonScaleTier.ENDER, tool());
    public static final RegistryObject<Item> ENCHANT_DRAGON_SWORD = createDragonSwordItem("enchant_dragon_sword", DragonScaleTier.ENCHANT, tool());
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_SWORD = createDragonSwordItem("sunlight_dragon_sword", DragonScaleTier.SUNLIGHT, tool());
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_SWORD = createDragonSwordItem("moonlight_dragon_sword", DragonScaleTier.MOONLIGHT, tool());
    public static final RegistryObject<Item> STORM_DRAGON_SWORD = createDragonSwordItem("storm_dragon_sword", DragonScaleTier.STORM, tool());
    public static final RegistryObject<Item> TERRA_DRAGON_SWORD = createDragonSwordItem("terra_dragon_sword", DragonScaleTier.TERRA, tool());
    public static final RegistryObject<Item> ZOMBIE_DRAGON_SWORD = createDragonSwordItem("zombie_dragon_sword", DragonScaleTier.ZOMBIE, tool());
    public static final RegistryObject<Item> SCULK_DRAGON_SWORD = createDragonSwordItem("sculk_dragon_sword", DragonScaleTier.SCULK, tool().fireResistant());
    //Dragon Scale Axes
    public static final RegistryObject<Item> AETHER_DRAGON_AXE = createDragonAxeItem("aether_dragon_axe", DragonScaleTier.AETHER, tool());
    public static final RegistryObject<Item> WATER_DRAGON_AXE = createDragonAxeItem("water_dragon_axe", DragonScaleTier.WATER, tool());
    public static final RegistryObject<Item> ICE_DRAGON_AXE = createDragonAxeItem("ice_dragon_axe", DragonScaleTier.ICE, tool());
    public static final RegistryObject<Item> FIRE_DRAGON_AXE = createDragonAxeItem("fire_dragon_axe", DragonScaleTier.FIRE, tool());
    public static final RegistryObject<Item> FOREST_DRAGON_AXE = createDragonAxeItem("forest_dragon_axe", DragonScaleTier.FOREST, tool());
    public static final RegistryObject<Item> NETHER_DRAGON_AXE = createDragonAxeItem("nether_dragon_axe", DragonScaleTier.NETHER, 6.0F, -2.9F, tool());
    public static final RegistryObject<Item> ENDER_DRAGON_AXE = createDragonAxeItem("ender_dragon_axe", DragonScaleTier.ENDER, 3.0F, -2.9F, tool());
    public static final RegistryObject<Item> ENCHANT_DRAGON_AXE = createDragonAxeItem("enchant_dragon_axe", DragonScaleTier.ENCHANT, tool());
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_AXE = createDragonAxeItem("sunlight_dragon_axe", DragonScaleTier.SUNLIGHT, tool());
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_AXE = createDragonAxeItem("moonlight_dragon_axe", DragonScaleTier.MOONLIGHT, tool());
    public static final RegistryObject<Item> STORM_DRAGON_AXE = createDragonAxeItem("storm_dragon_axe", DragonScaleTier.STORM, tool());
    public static final RegistryObject<Item> TERRA_DRAGON_AXE = createDragonAxeItem("terra_dragon_axe", DragonScaleTier.TERRA, tool());
    public static final RegistryObject<Item> ZOMBIE_DRAGON_AXE = createDragonAxeItem("zombie_dragon_axe", DragonScaleTier.ZOMBIE, tool());
    public static final RegistryObject<Item> SCULK_DRAGON_AXE = createDragonAxeItem("sculk_dragon_axe", DragonScaleTier.SCULK, tool().fireResistant());
    //Dragon Scale Bows
    public static final RegistryObject<Item> AETHER_DRAGON_BOW = createDragonBowItem("aether_dragon_bow", DragonScaleTier.AETHER, tool());
    public static final RegistryObject<Item> WATER_DRAGON_BOW = createDragonBowItem("water_dragon_bow", DragonScaleTier.WATER, tool());
    public static final RegistryObject<Item> ICE_DRAGON_BOW = createDragonBowItem("ice_dragon_bow", DragonScaleTier.ICE, tool());
    public static final RegistryObject<Item> FIRE_DRAGON_BOW = createDragonBowItem("fire_dragon_bow", DragonScaleTier.FIRE, tool());
    public static final RegistryObject<Item> FOREST_DRAGON_BOW = createDragonBowItem("forest_dragon_bow", DragonScaleTier.FOREST, tool());
    public static final RegistryObject<Item> NETHER_DRAGON_BOW = createDragonBowItem("nether_dragon_bow", DragonScaleTier.NETHER, tool());
    public static final RegistryObject<Item> ENDER_DRAGON_BOW = createDragonBowItem("ender_dragon_bow", DragonScaleTier.ENDER, tool());
    public static final RegistryObject<Item> ENCHANT_DRAGON_BOW = createDragonBowItem("enchant_dragon_bow", DragonScaleTier.ENCHANT, tool());
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_BOW = createDragonBowItem("sunlight_dragon_bow", DragonScaleTier.SUNLIGHT, tool());
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_BOW = createDragonBowItem("moonlight_dragon_bow", DragonScaleTier.MOONLIGHT, tool());
    public static final RegistryObject<Item> STORM_DRAGON_BOW = createDragonBowItem("storm_dragon_bow", DragonScaleTier.STORM, tool());
    public static final RegistryObject<Item> TERRA_DRAGON_BOW = createDragonBowItem("terra_dragon_bow", DragonScaleTier.TERRA, tool());
    public static final RegistryObject<Item> ZOMBIE_DRAGON_BOW = createDragonBowItem("zombie_dragon_bow", DragonScaleTier.ZOMBIE, tool());
    public static final RegistryObject<Item> SCULK_DRAGON_BOW = createDragonBowItem("sculk_dragon_bow", DragonScaleTier.SCULK, tool().fireResistant());
    //Dragon Scale Shields
    public static final RegistryObject<ItemDragonScaleShield> AETHER_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("aether_dragon_scale_shield", DragonScaleMaterial.AETHER, tool());
    public static final RegistryObject<ItemDragonScaleShield> WATER_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("water_dragon_scale_shield", DragonScaleMaterial.WATER, tool());
    public static final RegistryObject<ItemDragonScaleShield> ICE_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("ice_dragon_scale_shield", DragonScaleMaterial.ICE, tool());
    public static final RegistryObject<ItemDragonScaleShield> FIRE_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("fire_dragon_scale_shield", DragonScaleMaterial.FIRE, tool());
    public static final RegistryObject<ItemDragonScaleShield> FOREST_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("forest_dragon_scale_shield", DragonScaleMaterial.FOREST, tool());
    public static final RegistryObject<ItemDragonScaleShield> NETHER_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("nether_dragon_scale_shield", DragonScaleMaterial.NETHER, tool());
    public static final RegistryObject<ItemDragonScaleShield> ENDER_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("ender_dragon_scale_shield", DragonScaleMaterial.ENDER, tool());
    public static final RegistryObject<ItemDragonScaleShield> ENCHANT_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("enchant_dragon_scale_shield", DragonScaleMaterial.ENCHANT, tool());
    public static final RegistryObject<ItemDragonScaleShield> SUNLIGHT_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("sunlight_dragon_scale_shield", DragonScaleMaterial.SUNLIGHT, tool());
    public static final RegistryObject<ItemDragonScaleShield> MOONLIGHT_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("moonlight_dragon_scale_shield", DragonScaleMaterial.MOONLIGHT, tool());
    public static final RegistryObject<ItemDragonScaleShield> STORM_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("storm_dragon_scale_shield", DragonScaleMaterial.STORM, tool());
    public static final RegistryObject<ItemDragonScaleShield> TERRA_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("terra_dragon_scale_shield", DragonScaleMaterial.TERRA, tool());
    public static final RegistryObject<ItemDragonScaleShield> ZOMBIE_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("zombie_dragon_scale_shield", DragonScaleMaterial.ZOMBIE, tool());
    public static final RegistryObject<ItemDragonScaleShield> SCULK_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("sculk_dragon_scale_shield", DragonScaleMaterial.SCULK, tool().fireResistant());
    //Dragon Scale Tools - Aether
    public static final RegistryObject<Item> AETHER_DRAGON_SHOVEL = createDragonShovelItem("aether_dragon_shovel", DragonScaleTier.AETHER, tool());
    public static final RegistryObject<Item> AETHER_DRAGON_PICKAXE = createDragonPickaxeItem("aether_dragon_pickaxe", DragonScaleTier.AETHER, tool());
    public static final RegistryObject<Item> AETHER_DRAGON_HOE = createDragonHoeItem("aether_dragon_hoe", DragonScaleTier.AETHER, tool());
    //Dragon Scale Tools - Water
    public static final RegistryObject<Item> WATER_DRAGON_SHOVEL = createDragonShovelItem("water_dragon_shovel", DragonScaleTier.WATER, tool());
    public static final RegistryObject<Item> WATER_DRAGON_PICKAXE = createDragonPickaxeItem("water_dragon_pickaxe", DragonScaleTier.WATER, tool());
    public static final RegistryObject<Item> WATER_DRAGON_HOE = createDragonHoeItem("water_dragon_hoe", DragonScaleTier.WATER, tool());
    //Dragon Scale Tools - Ice
    public static final RegistryObject<Item> ICE_DRAGON_SHOVEL = createDragonShovelItem("ice_dragon_shovel", DragonScaleTier.ICE, tool());
    public static final RegistryObject<Item> ICE_DRAGON_PICKAXE = createDragonPickaxeItem("ice_dragon_pickaxe", DragonScaleTier.ICE, tool());
    public static final RegistryObject<Item> ICE_DRAGON_HOE = createDragonHoeItem("ice_dragon_hoe", DragonScaleTier.ICE, tool());
    //Dragon Scale Tools - Fire
    public static final RegistryObject<Item> FIRE_DRAGON_SHOVEL = createDragonShovelItem("fire_dragon_shovel", DragonScaleTier.FIRE, tool());
    public static final RegistryObject<Item> FIRE_DRAGON_PICKAXE = createDragonPickaxeItem("fire_dragon_pickaxe", DragonScaleTier.FIRE, tool());
    public static final RegistryObject<Item> FIRE_DRAGON_HOE = createDragonHoeItem("fire_dragon_hoe", DragonScaleTier.FIRE, tool());
    //Dragon Scale Tools - Forest
    public static final RegistryObject<Item> FOREST_DRAGON_SHOVEL = createDragonShovelItem("forest_dragon_shovel", DragonScaleTier.FOREST, tool());
    public static final RegistryObject<Item> FOREST_DRAGON_PICKAXE = createDragonPickaxeItem("forest_dragon_pickaxe", DragonScaleTier.FOREST, tool());
    public static final RegistryObject<Item> FOREST_DRAGON_HOE = createDragonHoeItem("forest_dragon_hoe", DragonScaleTier.FOREST, tool());
    //Dragon Scale Tools - Nether
    public static final RegistryObject<Item> NETHER_DRAGON_SHOVEL = createDragonShovelItem("nether_dragon_shovel", DragonScaleTier.NETHER, tool());
    public static final RegistryObject<Item> NETHER_DRAGON_PICKAXE = createDragonPickaxeItem("nether_dragon_pickaxe", DragonScaleTier.NETHER, tool());
    public static final RegistryObject<Item> NETHER_DRAGON_HOE = createDragonHoeItem("nether_dragon_hoe", DragonScaleTier.NETHER, tool());
    //Dragon Scale Tools - Ender
    public static final RegistryObject<Item> ENDER_DRAGON_SHOVEL = createDragonShovelItem("ender_dragon_shovel", DragonScaleTier.ENDER, tool());
    public static final RegistryObject<Item> ENDER_DRAGON_PICKAXE = createDragonPickaxeItem("ender_dragon_pickaxe", DragonScaleTier.ENDER, tool());
    public static final RegistryObject<Item> ENDER_DRAGON_HOE = createDragonHoeItem("ender_dragon_hoe", DragonScaleTier.ENDER, tool());
    //Dragon Scale Tools - Enchant
    public static final RegistryObject<Item> ENCHANT_DRAGON_SHOVEL = createDragonShovelItem("enchant_dragon_shovel", DragonScaleTier.ENCHANT, tool());
    public static final RegistryObject<Item> ENCHANT_DRAGON_PICKAXE = createDragonPickaxeItem("enchant_dragon_pickaxe", DragonScaleTier.ENCHANT, tool());
    public static final RegistryObject<Item> ENCHANT_DRAGON_HOE = createDragonHoeItem("enchant_dragon_hoe", DragonScaleTier.ENCHANT, tool());
    //Dragon Scale Tools - Sunlight
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_SHOVEL = createDragonShovelItem("sunlight_dragon_shovel", DragonScaleTier.SUNLIGHT, tool());
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_PICKAXE = createDragonPickaxeItem("sunlight_dragon_pickaxe", DragonScaleTier.SUNLIGHT, tool());
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_HOE = createDragonHoeItem("sunlight_dragon_hoe", DragonScaleTier.SUNLIGHT, tool());
    //Dragon Scale Tools - Moonlight
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_SHOVEL = createDragonShovelItem("moonlight_dragon_shovel", DragonScaleTier.MOONLIGHT, tool());
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_PICKAXE = createDragonPickaxeItem("moonlight_dragon_pickaxe", DragonScaleTier.MOONLIGHT, tool());
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_HOE = createDragonHoeItem("moonlight_dragon_hoe", DragonScaleTier.MOONLIGHT, tool());
    //Dragon Scale Tools - Storm
    public static final RegistryObject<Item> STORM_DRAGON_SHOVEL = createDragonShovelItem("storm_dragon_shovel", DragonScaleTier.STORM, tool());
    public static final RegistryObject<Item> STORM_DRAGON_PICKAXE = createDragonPickaxeItem("storm_dragon_pickaxe", DragonScaleTier.STORM, tool());
    public static final RegistryObject<Item> STORM_DRAGON_HOE = createDragonHoeItem("storm_dragon_hoe", DragonScaleTier.STORM, tool());
    //Dragon Scale Tools - Terra
    public static final RegistryObject<Item> TERRA_DRAGON_SHOVEL = createDragonShovelItem("terra_dragon_shovel", DragonScaleTier.TERRA, tool());
    public static final RegistryObject<Item> TERRA_DRAGON_PICKAXE = createDragonPickaxeItem("terra_dragon_pickaxe", DragonScaleTier.TERRA, tool());
    public static final RegistryObject<Item> TERRA_DRAGON_HOE = createDragonHoeItem("terra_dragon_hoe", DragonScaleTier.TERRA, tool());
    //Dragon Scale Tools - Zombie
    public static final RegistryObject<Item> ZOMBIE_DRAGON_SHOVEL = createDragonShovelItem("zombie_dragon_shovel", DragonScaleTier.ZOMBIE, tool());
    public static final RegistryObject<Item> ZOMBIE_DRAGON_PICKAXE = createDragonPickaxeItem("zombie_dragon_pickaxe", DragonScaleTier.ZOMBIE, tool());
    public static final RegistryObject<Item> ZOMBIE_DRAGON_HOE = createDragonHoeItem("zombie_dragon_hoe", DragonScaleTier.ZOMBIE, tool());
    //Dragon Scale Tools - Sculk
    public static final RegistryObject<Item> SCULK_DRAGON_SHOVEL = createDragonShovelItem("sculk_dragon_shovel", DragonScaleTier.SCULK, tool().fireResistant());
    public static final RegistryObject<Item> SCULK_DRAGON_PICKAXE = createDragonPickaxeItem("sculk_dragon_pickaxe", DragonScaleTier.SCULK, tool().fireResistant());
    public static final RegistryObject<Item> SCULK_DRAGON_HOE = createDragonHoeItem("sculk_dragon_hoe", DragonScaleTier.SCULK, tool().fireResistant());
    //Dragon Scale Armors - Aether
    public static final RegistryObject<Item> AETHER_DRAGON_HELMET = createDragonScaleArmorItem("aether_dragon_scale_helmet", DragonScaleMaterial.AETHER, EquipmentSlotType.HEAD, tool());
    public static final RegistryObject<Item> AETHER_DRAGON_CHESTPLATE = createDragonScaleArmorItem("aether_dragon_scale_chestplate", DragonScaleMaterial.AETHER, EquipmentSlotType.CHEST, tool());
    public static final RegistryObject<Item> AETHER_DRAGON_LEGGINGS = createDragonScaleArmorItem("aether_dragon_scale_leggings", DragonScaleMaterial.AETHER, EquipmentSlotType.LEGS, tool());
    public static final RegistryObject<Item> AETHER_DRAGON_BOOTS = createDragonScaleArmorItem("aether_dragon_scale_boots", DragonScaleMaterial.AETHER, EquipmentSlotType.FEET, tool());
    //Dragon Scale Armors - Water
    public static final RegistryObject<Item> WATER_DRAGON_HELMET = createDragonScaleArmorItem("water_dragon_scale_helmet", DragonScaleMaterial.WATER, EquipmentSlotType.HEAD, tool());
    public static final RegistryObject<Item> WATER_DRAGON_CHESTPLATE = createDragonScaleArmorItem("water_dragon_scale_chestplate", DragonScaleMaterial.WATER, EquipmentSlotType.CHEST, tool());
    public static final RegistryObject<Item> WATER_DRAGON_LEGGINGS = createDragonScaleArmorItem("water_dragon_scale_leggings", DragonScaleMaterial.WATER, EquipmentSlotType.LEGS, tool());
    public static final RegistryObject<Item> WATER_DRAGON_BOOTS = createDragonScaleArmorItem("water_dragon_scale_boots", DragonScaleMaterial.WATER, EquipmentSlotType.FEET, tool());
    //Dragon Scale Armors - Ice
    public static final RegistryObject<Item> ICE_DRAGON_HELMET = createDragonScaleArmorItem("ice_dragon_scale_helmet", DragonScaleMaterial.ICE, EquipmentSlotType.HEAD, tool());
    public static final RegistryObject<Item> ICE_DRAGON_CHESTPLATE = createDragonScaleArmorItem("ice_dragon_scale_chestplate", DragonScaleMaterial.ICE, EquipmentSlotType.CHEST, tool());
    public static final RegistryObject<Item> ICE_DRAGON_LEGGINGS = createDragonScaleArmorItem("ice_dragon_scale_leggings", DragonScaleMaterial.ICE, EquipmentSlotType.LEGS, tool());
    public static final RegistryObject<Item> ICE_DRAGON_BOOTS = createDragonScaleArmorItem("ice_dragon_scale_boots", DragonScaleMaterial.ICE, EquipmentSlotType.FEET, tool());
    //Dragon Scale Armors - Fire
    public static final RegistryObject<Item> FIRE_DRAGON_HELMET = createDragonScaleArmorItem("fire_dragon_scale_helmet", DragonScaleMaterial.FIRE, EquipmentSlotType.HEAD, tool());
    public static final RegistryObject<Item> FIRE_DRAGON_CHESTPLATE = createDragonScaleArmorItem("fire_dragon_scale_chestplate", DragonScaleMaterial.FIRE, EquipmentSlotType.CHEST, tool());
    public static final RegistryObject<Item> FIRE_DRAGON_LEGGINGS = createDragonScaleArmorItem("fire_dragon_scale_leggings", DragonScaleMaterial.FIRE, EquipmentSlotType.LEGS, tool());
    public static final RegistryObject<Item> FIRE_DRAGON_BOOTS = createDragonScaleArmorItem("fire_dragon_scale_boots", DragonScaleMaterial.FIRE, EquipmentSlotType.FEET, tool());
    //Dragon Scale Armors - Forest
    public static final RegistryObject<Item> FOREST_DRAGON_HELMET = createDragonScaleArmorItem("forest_dragon_scale_helmet", DragonScaleMaterial.FOREST, EquipmentSlotType.HEAD, tool());
    public static final RegistryObject<Item> FOREST_DRAGON_CHESTPLATE = createDragonScaleArmorItem("forest_dragon_scale_chestplate", DragonScaleMaterial.FOREST, EquipmentSlotType.CHEST, tool());
    public static final RegistryObject<Item> FOREST_DRAGON_LEGGINGS = createDragonScaleArmorItem("forest_dragon_scale_leggings", DragonScaleMaterial.FOREST, EquipmentSlotType.LEGS, tool());
    public static final RegistryObject<Item> FOREST_DRAGON_BOOTS = createDragonScaleArmorItem("forest_dragon_scale_boots", DragonScaleMaterial.FOREST, EquipmentSlotType.FEET, tool());
    //Dragon Scale Armors - Nether
    public static final RegistryObject<Item> NETHER_DRAGON_HELMET = createDragonScaleArmorItem("nether_dragon_scale_helmet", DragonScaleMaterial.NETHER, EquipmentSlotType.HEAD, tool());
    public static final RegistryObject<Item> NETHER_DRAGON_CHESTPLATE = createDragonScaleArmorItem("nether_dragon_scale_chestplate", DragonScaleMaterial.NETHER, EquipmentSlotType.CHEST, tool());
    public static final RegistryObject<Item> NETHER_DRAGON_LEGGINGS = createDragonScaleArmorItem("nether_dragon_scale_leggings", DragonScaleMaterial.NETHER, EquipmentSlotType.LEGS, tool());
    public static final RegistryObject<Item> NETHER_DRAGON_BOOTS = createDragonScaleArmorItem("nether_dragon_scale_boots", DragonScaleMaterial.NETHER, EquipmentSlotType.FEET, tool());
    //Dragon Scale Armors - Ender
    public static final RegistryObject<Item> ENDER_DRAGON_HELMET = createDragonScaleArmorItem("ender_dragon_scale_helmet", DragonScaleMaterial.ENDER, EquipmentSlotType.HEAD, tool());
    public static final RegistryObject<Item> ENDER_DRAGON_CHESTPLATE = createDragonScaleArmorItem("ender_dragon_scale_chestplate", DragonScaleMaterial.ENDER, EquipmentSlotType.CHEST, tool());
    public static final RegistryObject<Item> ENDER_DRAGON_LEGGINGS = createDragonScaleArmorItem("ender_dragon_scale_leggings", DragonScaleMaterial.ENDER, EquipmentSlotType.LEGS, tool());
    public static final RegistryObject<Item> ENDER_DRAGON_BOOTS = createDragonScaleArmorItem("ender_dragon_scale_boots", DragonScaleMaterial.ENDER, EquipmentSlotType.FEET, tool());
    //Dragon Scale Armors - Enchant
    public static final RegistryObject<Item> ENCHANT_DRAGON_HELMET = createDragonScaleArmorItem("enchant_dragon_scale_helmet", DragonScaleMaterial.ENCHANT, EquipmentSlotType.HEAD, tool());
    public static final RegistryObject<Item> ENCHANT_DRAGON_CHESTPLATE = createDragonScaleArmorItem("enchant_dragon_scale_chestplate", DragonScaleMaterial.ENCHANT, EquipmentSlotType.CHEST, tool());
    public static final RegistryObject<Item> ENCHANT_DRAGON_LEGGINGS = createDragonScaleArmorItem("enchant_dragon_scale_leggings", DragonScaleMaterial.ENCHANT, EquipmentSlotType.LEGS, tool());
    public static final RegistryObject<Item> ENCHANT_DRAGON_BOOTS = createDragonScaleArmorItem("enchant_dragon_scale_boots", DragonScaleMaterial.ENCHANT, EquipmentSlotType.FEET, tool());
    //Dragon Scale Armors - Sunlight
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_HELMET = createDragonScaleArmorItem("sunlight_dragon_scale_helmet", DragonScaleMaterial.SUNLIGHT, EquipmentSlotType.HEAD, tool());
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_CHESTPLATE = createDragonScaleArmorItem("sunlight_dragon_scale_chestplate", DragonScaleMaterial.SUNLIGHT, EquipmentSlotType.CHEST, tool());
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_LEGGINGS = createDragonScaleArmorItem("sunlight_dragon_scale_leggings", DragonScaleMaterial.SUNLIGHT, EquipmentSlotType.LEGS, tool());
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_BOOTS = createDragonScaleArmorItem("sunlight_dragon_scale_boots", DragonScaleMaterial.SUNLIGHT, EquipmentSlotType.FEET, tool());
    //Dragon Scale Armors - Moonlight
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_HELMET = createDragonScaleArmorItem("moonlight_dragon_scale_helmet", DragonScaleMaterial.MOONLIGHT, EquipmentSlotType.HEAD, tool());
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_CHESTPLATE = createDragonScaleArmorItem("moonlight_dragon_scale_chestplate", DragonScaleMaterial.MOONLIGHT, EquipmentSlotType.CHEST, tool());
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_LEGGINGS = createDragonScaleArmorItem("moonlight_dragon_scale_leggings", DragonScaleMaterial.MOONLIGHT, EquipmentSlotType.LEGS, tool());
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_BOOTS = createDragonScaleArmorItem("moonlight_dragon_scale_boots", DragonScaleMaterial.MOONLIGHT, EquipmentSlotType.FEET, tool());
    //Dragon Scale Armors - Storm
    public static final RegistryObject<Item> STORM_DRAGON_HELMET = createDragonScaleArmorItem("storm_dragon_scale_helmet", DragonScaleMaterial.STORM, EquipmentSlotType.HEAD, tool());
    public static final RegistryObject<Item> STORM_DRAGON_CHESTPLATE = createDragonScaleArmorItem("storm_dragon_scale_chestplate", DragonScaleMaterial.STORM, EquipmentSlotType.CHEST, tool());
    public static final RegistryObject<Item> STORM_DRAGON_LEGGINGS = createDragonScaleArmorItem("storm_dragon_scale_leggings", DragonScaleMaterial.STORM, EquipmentSlotType.LEGS, tool());
    public static final RegistryObject<Item> STORM_DRAGON_BOOTS = createDragonScaleArmorItem("storm_dragon_scale_boots", DragonScaleMaterial.STORM, EquipmentSlotType.FEET, tool());
    //Dragon Scale Armors - Terra
    public static final RegistryObject<Item> TERRA_DRAGON_HELMET = createDragonScaleArmorItem("terra_dragon_scale_helmet", DragonScaleMaterial.TERRA, EquipmentSlotType.HEAD, tool());
    public static final RegistryObject<Item> TERRA_DRAGON_CHESTPLATE = createDragonScaleArmorItem("terra_dragon_scale_chestplate", DragonScaleMaterial.TERRA, EquipmentSlotType.CHEST, tool());
    public static final RegistryObject<Item> TERRA_DRAGON_LEGGINGS = createDragonScaleArmorItem("terra_dragon_scale_leggings", DragonScaleMaterial.TERRA, EquipmentSlotType.LEGS, tool());
    public static final RegistryObject<Item> TERRA_DRAGON_BOOTS = createDragonScaleArmorItem("terra_dragon_scale_boots", DragonScaleMaterial.TERRA, EquipmentSlotType.FEET, tool());
    //Dragon Scale Armors - Zombie
    public static final RegistryObject<Item> ZOMBIE_DRAGON_HELMET = createDragonScaleArmorItem("zombie_dragon_scale_helmet", DragonScaleMaterial.ZOMBIE, EquipmentSlotType.HEAD, tool());
    public static final RegistryObject<Item> ZOMBIE_DRAGON_CHESTPLATE = createDragonScaleArmorItem("zombie_dragon_scale_chestplate", DragonScaleMaterial.ZOMBIE, EquipmentSlotType.CHEST, tool());
    public static final RegistryObject<Item> ZOMBIE_DRAGON_LEGGINGS = createDragonScaleArmorItem("zombie_dragon_scale_leggings", DragonScaleMaterial.ZOMBIE, EquipmentSlotType.LEGS, tool());
    public static final RegistryObject<Item> ZOMBIE_DRAGON_BOOTS = createDragonScaleArmorItem("zombie_dragon_scale_boots", DragonScaleMaterial.ZOMBIE, EquipmentSlotType.FEET, tool());
    //Dragon Scale Armors - Sculk
    public static final RegistryObject<Item> SCULK_DRAGON_HELMET = createDragonScaleArmorItem("sculk_dragon_scale_helmet", DragonScaleMaterial.SCULK, EquipmentSlotType.HEAD, tool().fireResistant());
    public static final RegistryObject<Item> SCULK_DRAGON_CHESTPLATE = createDragonScaleArmorItem("sculk_dragon_scale_chestplate", DragonScaleMaterial.SCULK, EquipmentSlotType.CHEST, tool().fireResistant());
    public static final RegistryObject<Item> SCULK_DRAGON_LEGGINGS = createDragonScaleArmorItem("sculk_dragon_scale_leggings", DragonScaleMaterial.SCULK, EquipmentSlotType.LEGS, tool().fireResistant());
    public static final RegistryObject<Item> SCULK_DRAGON_BOOTS = createDragonScaleArmorItem("sculk_dragon_scale_boots", DragonScaleMaterial.SCULK, EquipmentSlotType.FEET, tool().fireResistant());
    //Dragon Scale Armors - Shears
    public static final RegistryObject<Item> DIAMOND_SHEARS = createTieredShearsItem("diamond_shears", ItemTier.DIAMOND, item());
    public static final RegistryObject<Item> NETHERITE_SHEARS = createTieredShearsItem("netherite_shears", ItemTier.NETHERITE, item().fireResistant());
    //Dragon Scale Armors - Carriages
    public static final RegistryObject<Item> ACACIA_CARRIAGE = createCarriageItem("acacia_carriage", CarriageType.ACACIA, item());
    public static final RegistryObject<Item> BIRCH_CARRIAGE = createCarriageItem("birch_carriage", CarriageType.BIRCH, item());
    public static final RegistryObject<Item> DARK_OAK_CARRIAGE = createCarriageItem("dark_oak_carriage", CarriageType.DARK_OAK, item());
    public static final RegistryObject<Item> JUNGLE_CARRIAGE = createCarriageItem("jungle_carriage", CarriageType.JUNGLE, item());
    public static final RegistryObject<Item> OAK_CARRIAGE = createCarriageItem("oak_carriage", CarriageType.OAK, item());
    public static final RegistryObject<Item> SPRUCE_CARRIAGE = createCarriageItem("spruce_carriage", CarriageType.SPRUCE, item());
    //Dragon Amulet
    public static final RegistryObject<Item> DRAGON_AMULET = ITEMS.register("dragon_amulet", () -> new ItemBaseDragonAmulet(item()));
    public static final RegistryObject<ItemDragonAmulet> FOREST_DRAGON_AMULET = createDragonAmuletItem("forest_dragon_amulet", DragonType.FOREST, none());
    public static final RegistryObject<ItemDragonAmulet> FIRE_DRAGON_AMULET = createDragonAmuletItem("fire_dragon_amulet", DragonType.FIRE, none());
    public static final RegistryObject<ItemDragonAmulet> ICE_DRAGON_AMULET = createDragonAmuletItem("ice_dragon_amulet", DragonType.ICE, none());
    public static final RegistryObject<ItemDragonAmulet> WATER_DRAGON_AMULET = createDragonAmuletItem("water_dragon_amulet", DragonType.WATER, none());
    public static final RegistryObject<ItemDragonAmulet> AETHER_DRAGON_AMULET = createDragonAmuletItem("aether_dragon_amulet", DragonType.AETHER, none());
    public static final RegistryObject<ItemDragonAmulet> NETHER_DRAGON_AMULET = createDragonAmuletItem("nether_dragon_amulet", DragonType.NETHER, none());
    public static final RegistryObject<ItemDragonAmulet> ENDER_DRAGON_AMULET = createDragonAmuletItem("ender_dragon_amulet", DragonType.ENDER, none());
    public static final RegistryObject<ItemDragonAmulet> SUNLIGHT_DRAGON_AMULET = createDragonAmuletItem("sunlight_dragon_amulet", DragonType.SUNLIGHT, none());
    public static final RegistryObject<ItemDragonAmulet> ENCHANT_DRAGON_AMULET = createDragonAmuletItem("enchant_dragon_amulet", DragonType.ENCHANT, none());
    public static final RegistryObject<ItemDragonAmulet> STORM_DRAGON_AMULET = createDragonAmuletItem("storm_dragon_amulet", DragonType.STORM, none());
    public static final RegistryObject<ItemDragonAmulet> TERRA_DRAGON_AMULET = createDragonAmuletItem("terra_dragon_amulet", DragonType.TERRA, none());
    public static final RegistryObject<ItemDragonAmulet> ZOMBIE_DRAGON_AMULET = createDragonAmuletItem("zombie_dragon_amulet", DragonType.ZOMBIE, none());
    public static final RegistryObject<ItemDragonAmulet> MOONLIGHT_DRAGON_AMULET = createDragonAmuletItem("moonlight_dragon_amulet", DragonType.MOONLIGHT, none());
    public static final RegistryObject<ItemDragonAmulet> SCULK_DRAGON_AMULET = createDragonAmuletItem("sculk_dragon_amulet", DragonType.SCULK, none().fireResistant());
    public static final RegistryObject<ItemDragonAmulet> SKELETON_DRAGON_AMULET = createDragonAmuletItem("skeleton_dragon_amulet", DragonType.SKELETON, none());
    public static final RegistryObject<ItemDragonAmulet> WITHER_DRAGON_AMULET = createDragonAmuletItem("wither_dragon_amulet", DragonType.WITHER, none());
    //Dragon Essences
    public static final RegistryObject<Item> FOREST_DRAGON_ESSENCE = createDragonEssenceItem("forest_dragon_essence", DragonType.FOREST, none());
    public static final RegistryObject<Item> FIRE_DRAGON_ESSENCE = createDragonEssenceItem("fire_dragon_essence", DragonType.FIRE, none());
    public static final RegistryObject<Item> ICE_DRAGON_ESSENCE = createDragonEssenceItem("ice_dragon_essence", DragonType.ICE, none());
    public static final RegistryObject<Item> WATER_DRAGON_ESSENCE = createDragonEssenceItem("water_dragon_essence", DragonType.WATER, none());
    public static final RegistryObject<Item> AETHER_DRAGON_ESSENCE = createDragonEssenceItem("aether_dragon_essence", DragonType.AETHER, none());
    public static final RegistryObject<Item> NETHER_DRAGON_ESSENCE = createDragonEssenceItem("nether_dragon_essence", DragonType.NETHER, none());
    public static final RegistryObject<Item> ENDER_DRAGON_ESSENCE = createDragonEssenceItem("ender_dragon_essence", DragonType.ENDER, none());
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_ESSENCE = createDragonEssenceItem("sunlight_dragon_essence", DragonType.SUNLIGHT, none());
    public static final RegistryObject<Item> ENCHANT_DRAGON_ESSENCE = createDragonEssenceItem("enchant_dragon_essence", DragonType.ENCHANT, none());
    public static final RegistryObject<Item> STORM_DRAGON_ESSENCE = createDragonEssenceItem("storm_dragon_essence", DragonType.STORM, none());
    public static final RegistryObject<Item> TERRA_DRAGON_ESSENCE = createDragonEssenceItem("terra_dragon_essence", DragonType.TERRA, none());
    public static final RegistryObject<Item> ZOMBIE_DRAGON_ESSENCE = createDragonEssenceItem("zombie_dragon_essence", DragonType.ZOMBIE, none());
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_ESSENCE = createDragonEssenceItem("moonlight_dragon_essence", DragonType.MOONLIGHT, none());
    public static final RegistryObject<Item> SCULK_DRAGON_ESSENCE = createDragonEssenceItem("sculk_dragon_essence", DragonType.SCULK, none().fireResistant());
    public static final RegistryObject<Item> SKELETON_DRAGON_ESSENCE = createDragonEssenceItem("skeleton_dragon_essence", DragonType.SKELETON, none());
    public static final RegistryObject<Item> WITHER_DRAGON_ESSENCE = createDragonEssenceItem("wither_dragon_essence", DragonType.WITHER, none());
    public static final RegistryObject<Item> DRAGON_WHISTLE = ITEMS.register("dragon_whistle", () -> new ItemDragonWhistle(item()));

    private static RegistryObject<Item> createCarriageItem(String name, CarriageType type, Properties properties) {
        return ITEMS.register(name, () -> new ItemCarriage(type, properties));
    }

    private static RegistryObject<ItemDragonAmulet> createDragonAmuletItem(String name, DragonType type, Properties properties) {
        return ITEMS.register(name, () -> new ItemDragonAmulet(type, properties));
    }

    private static RegistryObject<Item> createDragonArmorItem(String name, int protection, Properties properties) {
        return ITEMS.register(name, () -> new ItemDragonArmor(protection, properties));
    }

    private static RegistryObject<Item> createDragonAxeItem(String name, DragonScaleTier tier, float attackDamageModifier, float attackSpeedModifier, Properties properties) {
        return ITEMS.register(name, () -> new ItemDragonAxe(tier, attackDamageModifier, attackSpeedModifier, properties));
    }

    private static RegistryObject<Item> createDragonAxeItem(String name, DragonScaleTier tier, Properties properties) {
        return ITEMS.register(name, () -> new ItemDragonAxe(tier, 5.0F, -2.8F/*Minecraft: -3.0F*/, properties));
    }

    private static RegistryObject<Item> createDragonBowItem(String name, DragonScaleTier tier, Properties properties) {
        return ITEMS.register(name, () -> new ItemDragonBow(tier, properties));
    }

    private static RegistryObject<Item> createDragonEssenceItem(String name, DragonType type, Properties properties) {
        return ITEMS.register(name, () -> new ItemDragonEssence(type, properties));
    }

    private static RegistryObject<Item> createDragonHoeItem(String name, DragonScaleTier tier, Properties properties) {
        return ITEMS.register(name, () -> new ItemDragonHoe(tier, (int) -tier.getAttackDamageBonus(), tier.getAttackDamageBonus() - 3.0F, properties));
    }

    private static RegistryObject<Item> createDragonPickaxeItem(String name, DragonScaleTier tier, Properties properties) {
        return ITEMS.register(name, () -> new ItemDragonPickaxe(tier, 1, -2.8F, properties));
    }

    private static RegistryObject<Item> createDragonScaleArmorItem(String name, DragonScaleMaterial material, EquipmentSlotType slot, Properties properties) {
        return ITEMS.register(name, () -> new ItemDragonScaleArmor(material, slot, properties));
    }

    private static RegistryObject<Item> createDragonScalesItem(String name, DragonType type, Properties properties) {
        return ITEMS.register(name, () -> new ItemDragonScales(type, properties));
    }

    private static RegistryObject<ItemDragonScaleShield> createDragonScaleShieldItem(String name, DragonScaleMaterial material, Properties properties) {
        return ITEMS.register(name, () -> new ItemDragonScaleShield(material, properties));
    }

    private static RegistryObject<Item> createDragonShovelItem(String name, DragonScaleTier tier, Properties properties) {
        return ITEMS.register(name, () -> new ItemDragonShovel(tier, 1.5F, -3.0F, properties));
    }

    private static RegistryObject<Item> createDragonSwordItem(String name, DragonScaleTier tier, Properties properties) {
        return ITEMS.register(name, () -> new ItemDragonSword(tier, 3, -2.0F/*Minecraft: -2.4F*/, properties));
    }

    private static RegistryObject<Item> createTieredShearsItem(String name, IItemTier tier, Properties properties) {
        return ITEMS.register(name, () -> new ItemTieredShears(tier, properties));
    }

    @OnlyIn(Dist.CLIENT)
    private static float getBlockingItemProperty(@Nonnull ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
        return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
    }

    @OnlyIn(Dist.CLIENT)
    private static float getPullItemProperty(@Nonnull ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
        if (entity == null) return 0.0F;
        else
            return entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
    }

    @OnlyIn(Dist.CLIENT)
    private static float getPullingItemProperty(@Nonnull ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
        return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
    }

    @OnlyIn(Dist.CLIENT)
    public static void addItemModelProperties() {
        ItemModelsProperties.register(AETHER_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(AETHER_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(AETHER_DRAGON_SCALE_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(WATER_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(WATER_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(WATER_DRAGON_SCALE_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(ICE_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(ICE_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(ICE_DRAGON_SCALE_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(FIRE_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(FIRE_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(FIRE_DRAGON_SCALE_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(FOREST_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(FOREST_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(FOREST_DRAGON_SCALE_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(NETHER_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(NETHER_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(NETHER_DRAGON_SCALE_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(ENDER_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(ENDER_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(ENDER_DRAGON_SCALE_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(ENCHANT_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(ENCHANT_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(ENCHANT_DRAGON_SCALE_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(SUNLIGHT_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(SUNLIGHT_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(SUNLIGHT_DRAGON_SCALE_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(MOONLIGHT_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(MOONLIGHT_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(MOONLIGHT_DRAGON_SCALE_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(STORM_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(STORM_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(STORM_DRAGON_SCALE_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(TERRA_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(TERRA_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(TERRA_DRAGON_SCALE_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(ZOMBIE_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(ZOMBIE_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(ZOMBIE_DRAGON_SCALE_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(SCULK_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(SCULK_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(SCULK_DRAGON_SCALE_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
    }
}