package net.dragonmounts3.inits;

import mcp.MethodsReturnNonnullByDefault;
import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.objects.items.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.dragonmounts3.DragonMounts.MOD_ID;

@MethodsReturnNonnullByDefault
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DragonMounts.create(ForgeRegistries.ITEMS);

    //Scales Start
    public static final RegistryObject<Item> FOREST_DRAGON_SCALES = createDragonScalesItem("forest_dragon_scales", DragonType.FOREST);
    public static final RegistryObject<Item> FIRE_DRAGON_SCALES = createDragonScalesItem("fire_dragon_scales", DragonType.FIRE);
    public static final RegistryObject<Item> ICE_DRAGON_SCALES = createDragonScalesItem("ice_dragon_scales", DragonType.ICE);
    public static final RegistryObject<Item> WATER_DRAGON_SCALES = createDragonScalesItem("water_dragon_scales", DragonType.WATER);
    public static final RegistryObject<Item> AETHER_DRAGON_SCALES = createDragonScalesItem("aether_dragon_scales", DragonType.AETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_SCALES = createDragonScalesItem("nether_dragon_scales", DragonType.NETHER);
    public static final RegistryObject<Item> ENDER_DRAGON_SCALES = createDragonScalesItem("ender_dragon_scales", DragonType.ENDER);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_SCALES = createDragonScalesItem("sunlight_dragon_scales", DragonType.SUNLIGHT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_SCALES = createDragonScalesItem("enchant_dragon_scales", DragonType.ENCHANT);
    public static final RegistryObject<Item> STORM_DRAGON_SCALES = createDragonScalesItem("storm_dragon_scales", DragonType.STORM);
    public static final RegistryObject<Item> TERRA_DRAGON_SCALES = createDragonScalesItem("terra_dragon_scales", DragonType.TERRA);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_SCALES = createDragonScalesItem("zombie_dragon_scales", DragonType.ZOMBIE);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_SCALES = createDragonScalesItem("moonlight_dragon_scales", DragonType.MOONLIGHT);
    //Dragon Armor
    public static final RegistryObject<Item> IRON_DRAGON_ARMOR = createDragonArmorItem("iron_dragon_armor", 5);
    public static final RegistryObject<Item> GOLDEN_DRAGON_ARMOR = createDragonArmorItem("golden_dragon_armor", 7);
    public static final RegistryObject<Item> DIAMOND_DRAGON_ARMOR = createDragonArmorItem("diamond_dragon_armor", 11);
    public static final RegistryObject<Item> EMERALD_DRAGON_ARMOR = createDragonArmorItem("emerald_dragon_armor", 11);
    public static final RegistryObject<Item> NETHERITE_DRAGON_ARMOR = createDragonArmorItem("netherite_dragon_armor", 15);
    //Aether Equipments
    public static final RegistryObject<Item> AETHER_DRAGON_AXE = createDragonAxeItem("aether_dragon_axe", DragonScaleTier.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_BOOTS = createDragonScaleArmorItem("aether_dragon_scale_boots", DragonScaleMaterial.AETHER, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> AETHER_DRAGON_BOW = createDragonBowItem("aether_dragon_bow", DragonScaleTier.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_CHESTPLATE = createDragonScaleArmorItem("aether_dragon_scale_chestplate", DragonScaleMaterial.AETHER, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> AETHER_DRAGON_HELMET = createDragonScaleArmorItem("aether_dragon_scale_helmet", DragonScaleMaterial.AETHER, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> AETHER_DRAGON_HOE = createDragonHoeItem("aether_dragon_hoe", DragonScaleTier.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_LEGGINGS = createDragonScaleArmorItem("aether_dragon_scale_leggings", DragonScaleMaterial.AETHER, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> AETHER_DRAGON_PICKAXE = createDragonPickaxeItem("aether_dragon_pickaxe", DragonScaleTier.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_SHIELD = createDragonShieldItem("aether_dragon_shield", DragonScaleMaterial.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_SHOVEL = createDragonShovelItem("aether_dragon_shovel", DragonScaleTier.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_SWORD = createDragonSwordItem("aether_dragon_sword", DragonScaleTier.AETHER);
    //Water Equipments
    public static final RegistryObject<Item> WATER_DRAGON_AXE = createDragonAxeItem("water_dragon_axe", DragonScaleTier.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_BOOTS = createDragonScaleArmorItem("water_dragon_scale_boots", DragonScaleMaterial.WATER, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> WATER_DRAGON_BOW = createDragonBowItem("water_dragon_bow", DragonScaleTier.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_CHESTPLATE = createDragonScaleArmorItem("water_dragon_scale_chestplate", DragonScaleMaterial.WATER, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> WATER_DRAGON_HELMET = createDragonScaleArmorItem("water_dragon_scale_helmet", DragonScaleMaterial.WATER, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> WATER_DRAGON_HOE = createDragonHoeItem("water_dragon_hoe", DragonScaleTier.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_LEGGINGS = createDragonScaleArmorItem("water_dragon_scale_leggings", DragonScaleMaterial.WATER, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> WATER_DRAGON_PICKAXE = createDragonPickaxeItem("water_dragon_pickaxe", DragonScaleTier.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_SHIELD = createDragonShieldItem("water_dragon_shield", DragonScaleMaterial.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_SHOVEL = createDragonShovelItem("water_dragon_shovel", DragonScaleTier.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_SWORD = createDragonSwordItem("water_dragon_sword", DragonScaleTier.WATER);
    //Ice Equipments
    public static final RegistryObject<Item> ICE_DRAGON_AXE = createDragonAxeItem("ice_dragon_axe", DragonScaleTier.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_BOOTS = createDragonScaleArmorItem("ice_dragon_scale_boots", DragonScaleMaterial.ICE, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> ICE_DRAGON_BOW = createDragonBowItem("ice_dragon_bow", DragonScaleTier.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_CHESTPLATE = createDragonScaleArmorItem("ice_dragon_scale_chestplate", DragonScaleMaterial.ICE, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> ICE_DRAGON_HELMET = createDragonScaleArmorItem("ice_dragon_scale_helmet", DragonScaleMaterial.ICE, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> ICE_DRAGON_HOE = createDragonHoeItem("ice_dragon_hoe", DragonScaleTier.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_LEGGINGS = createDragonScaleArmorItem("ice_dragon_scale_leggings", DragonScaleMaterial.ICE, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> ICE_DRAGON_PICKAXE = createDragonPickaxeItem("ice_dragon_pickaxe", DragonScaleTier.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_SHIELD = createDragonShieldItem("ice_dragon_shield", DragonScaleMaterial.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_SHOVEL = createDragonShovelItem("ice_dragon_shovel", DragonScaleTier.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_SWORD = createDragonSwordItem("ice_dragon_sword", DragonScaleTier.ICE);
    //Fire Equipments
    public static final RegistryObject<Item> FIRE_DRAGON_AXE = createDragonAxeItem("fire_dragon_axe", DragonScaleTier.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_BOOTS = createDragonScaleArmorItem("fire_dragon_scale_boots", DragonScaleMaterial.FIRE, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> FIRE_DRAGON_BOW = createDragonBowItem("fire_dragon_bow", DragonScaleTier.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_CHESTPLATE = createDragonScaleArmorItem("fire_dragon_scale_chestplate", DragonScaleMaterial.FIRE, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> FIRE_DRAGON_HELMET = createDragonScaleArmorItem("fire_dragon_scale_helmet", DragonScaleMaterial.FIRE, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> FIRE_DRAGON_HOE = createDragonHoeItem("fire_dragon_hoe", DragonScaleTier.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_LEGGINGS = createDragonScaleArmorItem("fire_dragon_scale_leggings", DragonScaleMaterial.FIRE, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> FIRE_DRAGON_PICKAXE = createDragonPickaxeItem("fire_dragon_pickaxe", DragonScaleTier.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_SHIELD = createDragonShieldItem("fire_dragon_shield", DragonScaleMaterial.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_SHOVEL = createDragonShovelItem("fire_dragon_shovel", DragonScaleTier.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_SWORD = createDragonSwordItem("fire_dragon_sword", DragonScaleTier.FIRE);
    //Forest Equipments
    public static final RegistryObject<Item> FOREST_DRAGON_AXE = createDragonAxeItem("forest_dragon_axe", DragonScaleTier.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_BOOTS = createDragonScaleArmorItem("forest_dragon_scale_boots", DragonScaleMaterial.FOREST, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> FOREST_DRAGON_BOW = createDragonBowItem("forest_dragon_bow", DragonScaleTier.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_CHESTPLATE = createDragonScaleArmorItem("forest_dragon_scale_chestplate", DragonScaleMaterial.FOREST, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> FOREST_DRAGON_HELMET = createDragonScaleArmorItem("forest_dragon_scale_helmet", DragonScaleMaterial.FOREST, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> FOREST_DRAGON_HOE = createDragonHoeItem("forest_dragon_hoe", DragonScaleTier.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_LEGGINGS = createDragonScaleArmorItem("forest_dragon_scale_leggings", DragonScaleMaterial.FOREST, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> FOREST_DRAGON_PICKAXE = createDragonPickaxeItem("forest_dragon_pickaxe", DragonScaleTier.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_SHIELD = createDragonShieldItem("forest_dragon_shield", DragonScaleMaterial.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_SHOVEL = createDragonShovelItem("forest_dragon_shovel", DragonScaleTier.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_SWORD = createDragonSwordItem("forest_dragon_sword", DragonScaleTier.FOREST);
    //Nether Equipments
    public static final RegistryObject<Item> NETHER_DRAGON_AXE = createDragonAxeItem("nether_dragon_axe", DragonScaleTier.NETHER, 6.0F, -2.9F);
    public static final RegistryObject<Item> NETHER_DRAGON_BOOTS = createDragonScaleArmorItem("nether_dragon_scale_boots", DragonScaleMaterial.NETHER, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> NETHER_DRAGON_BOW = createDragonBowItem("nether_dragon_bow", DragonScaleTier.NETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_CHESTPLATE = createDragonScaleArmorItem("nether_dragon_scale_chestplate", DragonScaleMaterial.NETHER, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> NETHER_DRAGON_HELMET = createDragonScaleArmorItem("nether_dragon_scale_helmet", DragonScaleMaterial.NETHER, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> NETHER_DRAGON_HOE = createDragonHoeItem("nether_dragon_hoe", DragonScaleTier.NETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_LEGGINGS = createDragonScaleArmorItem("nether_dragon_scale_leggings", DragonScaleMaterial.NETHER, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> NETHER_DRAGON_PICKAXE = createDragonPickaxeItem("nether_dragon_pickaxe", DragonScaleTier.NETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_SHIELD = createDragonShieldItem("nether_dragon_shield", DragonScaleMaterial.NETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_SHOVEL = createDragonShovelItem("nether_dragon_shovel", DragonScaleTier.NETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_SWORD = createDragonSwordItem("nether_dragon_sword", DragonScaleTier.NETHER);
    //Ender Equipments
    public static final RegistryObject<Item> ENDER_DRAGON_AXE = createDragonAxeItem("ender_dragon_axe", DragonScaleTier.ENDER, 3.0F, -2.9F);
    public static final RegistryObject<Item> ENDER_DRAGON_BOOTS = createDragonScaleArmorItem("ender_dragon_scale_boots", DragonScaleMaterial.ENDER, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> ENDER_DRAGON_BOW = createDragonBowItem("ender_dragon_bow", DragonScaleTier.ENDER);
    public static final RegistryObject<Item> ENDER_DRAGON_CHESTPLATE = createDragonScaleArmorItem("ender_dragon_scale_chestplate", DragonScaleMaterial.ENDER, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> ENDER_DRAGON_HELMET = createDragonScaleArmorItem("ender_dragon_scale_helmet", DragonScaleMaterial.ENDER, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> ENDER_DRAGON_HOE = createDragonHoeItem("ender_dragon_hoe", DragonScaleTier.ENDER);
    public static final RegistryObject<Item> ENDER_DRAGON_LEGGINGS = createDragonScaleArmorItem("ender_dragon_scale_leggings", DragonScaleMaterial.ENDER, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> ENDER_DRAGON_PICKAXE = createDragonPickaxeItem("ender_dragon_pickaxe", DragonScaleTier.ENDER);
    public static final RegistryObject<Item> ENDER_DRAGON_SHIELD = createDragonShieldItem("ender_dragon_shield", DragonScaleMaterial.ENDER);
    public static final RegistryObject<Item> ENDER_DRAGON_SHOVEL = createDragonShovelItem("ender_dragon_shovel", DragonScaleTier.ENDER);
    public static final RegistryObject<Item> ENDER_DRAGON_SWORD = createDragonSwordItem("ender_dragon_sword", DragonScaleTier.ENDER);
    //Enchant Equipments
    public static final RegistryObject<Item> ENCHANT_DRAGON_AXE = createDragonAxeItem("enchant_dragon_axe", DragonScaleTier.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_BOOTS = createDragonScaleArmorItem("enchant_dragon_scale_boots", DragonScaleMaterial.ENCHANT, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> ENCHANT_DRAGON_BOW = createDragonBowItem("enchant_dragon_bow", DragonScaleTier.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_CHESTPLATE = createDragonScaleArmorItem("enchant_dragon_scale_chestplate", DragonScaleMaterial.ENCHANT, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> ENCHANT_DRAGON_HELMET = createDragonScaleArmorItem("enchant_dragon_scale_helmet", DragonScaleMaterial.ENCHANT, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> ENCHANT_DRAGON_HOE = createDragonHoeItem("enchant_dragon_hoe", DragonScaleTier.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_LEGGINGS = createDragonScaleArmorItem("enchant_dragon_scale_leggings", DragonScaleMaterial.ENCHANT, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> ENCHANT_DRAGON_PICKAXE = createDragonPickaxeItem("enchant_dragon_pickaxe", DragonScaleTier.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_SHIELD = createDragonShieldItem("enchant_dragon_shield", DragonScaleMaterial.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_SHOVEL = createDragonShovelItem("enchant_dragon_shovel", DragonScaleTier.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_SWORD = createDragonSwordItem("enchant_dragon_sword", DragonScaleTier.ENCHANT);
    //Sunlight Equipments
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_AXE = createDragonAxeItem("sunlight_dragon_axe", DragonScaleTier.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_BOOTS = createDragonScaleArmorItem("sunlight_dragon_scale_boots", DragonScaleMaterial.SUNLIGHT, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_BOW = createDragonBowItem("sunlight_dragon_bow", DragonScaleTier.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_CHESTPLATE = createDragonScaleArmorItem("sunlight_dragon_scale_chestplate", DragonScaleMaterial.SUNLIGHT, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_HELMET = createDragonScaleArmorItem("sunlight_dragon_scale_helmet", DragonScaleMaterial.SUNLIGHT, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_HOE = createDragonHoeItem("sunlight_dragon_hoe", DragonScaleTier.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_LEGGINGS = createDragonScaleArmorItem("sunlight_dragon_scale_leggings", DragonScaleMaterial.SUNLIGHT, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_PICKAXE = createDragonPickaxeItem("sunlight_dragon_pickaxe", DragonScaleTier.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_SHIELD = createDragonShieldItem("sunlight_dragon_shield", DragonScaleMaterial.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_SHOVEL = createDragonShovelItem("sunlight_dragon_shovel", DragonScaleTier.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_SWORD = createDragonSwordItem("sunlight_dragon_sword", DragonScaleTier.SUNLIGHT);
    //Moonlight Equipments
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_AXE = createDragonAxeItem("moonlight_dragon_axe", DragonScaleTier.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_BOOTS = createDragonScaleArmorItem("moonlight_dragon_scale_boots", DragonScaleMaterial.MOONLIGHT, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_BOW = createDragonBowItem("moonlight_dragon_bow", DragonScaleTier.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_CHESTPLATE = createDragonScaleArmorItem("moonlight_dragon_scale_chestplate", DragonScaleMaterial.MOONLIGHT, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_HELMET = createDragonScaleArmorItem("moonlight_dragon_scale_helmet", DragonScaleMaterial.MOONLIGHT, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_HOE = createDragonHoeItem("moonlight_dragon_hoe", DragonScaleTier.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_LEGGINGS = createDragonScaleArmorItem("moonlight_dragon_scale_leggings", DragonScaleMaterial.MOONLIGHT, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_PICKAXE = createDragonPickaxeItem("moonlight_dragon_pickaxe", DragonScaleTier.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_SHIELD = createDragonShieldItem("moonlight_dragon_shield", DragonScaleMaterial.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_SHOVEL = createDragonShovelItem("moonlight_dragon_shovel", DragonScaleTier.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_SWORD = createDragonSwordItem("moonlight_dragon_sword", DragonScaleTier.MOONLIGHT);
    //Storm Equipments
    public static final RegistryObject<Item> STORM_DRAGON_AXE = createDragonAxeItem("storm_dragon_axe", DragonScaleTier.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_BOOTS = createDragonScaleArmorItem("storm_dragon_scale_boots", DragonScaleMaterial.STORM, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> STORM_DRAGON_BOW = createDragonBowItem("storm_dragon_bow", DragonScaleTier.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_CHESTPLATE = createDragonScaleArmorItem("storm_dragon_scale_chestplate", DragonScaleMaterial.STORM, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> STORM_DRAGON_HELMET = createDragonScaleArmorItem("storm_dragon_scale_helmet", DragonScaleMaterial.STORM, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> STORM_DRAGON_HOE = createDragonHoeItem("storm_dragon_hoe", DragonScaleTier.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_LEGGINGS = createDragonScaleArmorItem("storm_dragon_scale_leggings", DragonScaleMaterial.STORM, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> STORM_DRAGON_PICKAXE = createDragonPickaxeItem("storm_dragon_pickaxe", DragonScaleTier.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_SHIELD = createDragonShieldItem("storm_dragon_shield", DragonScaleMaterial.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_SHOVEL = createDragonShovelItem("storm_dragon_shovel", DragonScaleTier.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_SWORD = createDragonSwordItem("storm_dragon_sword", DragonScaleTier.STORM);
    //Terra Equipments
    public static final RegistryObject<Item> TERRA_DRAGON_AXE = createDragonAxeItem("terra_dragon_axe", DragonScaleTier.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_BOOTS = createDragonScaleArmorItem("terra_dragon_scale_boots", DragonScaleMaterial.TERRA, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> TERRA_DRAGON_BOW = createDragonBowItem("terra_dragon_bow", DragonScaleTier.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_CHESTPLATE = createDragonScaleArmorItem("terra_dragon_scale_chestplate", DragonScaleMaterial.TERRA, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> TERRA_DRAGON_HELMET = createDragonScaleArmorItem("terra_dragon_scale_helmet", DragonScaleMaterial.TERRA, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> TERRA_DRAGON_HOE = createDragonHoeItem("terra_dragon_hoe", DragonScaleTier.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_LEGGINGS = createDragonScaleArmorItem("terra_dragon_scale_leggings", DragonScaleMaterial.TERRA, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> TERRA_DRAGON_PICKAXE = createDragonPickaxeItem("terra_dragon_pickaxe", DragonScaleTier.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_SHIELD = createDragonShieldItem("terra_dragon_shield", DragonScaleMaterial.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_SHOVEL = createDragonShovelItem("terra_dragon_shovel", DragonScaleTier.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_SWORD = createDragonSwordItem("terra_dragon_sword", DragonScaleTier.TERRA);
    //Zombie Equipments
    public static final RegistryObject<Item> ZOMBIE_DRAGON_AXE = createDragonAxeItem("zombie_dragon_axe", DragonScaleTier.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_BOOTS = createDragonScaleArmorItem("zombie_dragon_scale_boots", DragonScaleMaterial.ZOMBIE, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_BOW = createDragonBowItem("zombie_dragon_bow", DragonScaleTier.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_CHESTPLATE = createDragonScaleArmorItem("zombie_dragon_scale_chestplate", DragonScaleMaterial.ZOMBIE, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_HELMET = createDragonScaleArmorItem("zombie_dragon_scale_helmet", DragonScaleMaterial.ZOMBIE, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_HOE = createDragonHoeItem("zombie_dragon_hoe", DragonScaleTier.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_LEGGINGS = createDragonScaleArmorItem("zombie_dragon_scale_leggings", DragonScaleMaterial.ZOMBIE, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_PICKAXE = createDragonPickaxeItem("zombie_dragon_pickaxe", DragonScaleTier.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_SHIELD = createDragonShieldItem("zombie_dragon_shield", DragonScaleMaterial.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_SHOVEL = createDragonShovelItem("zombie_dragon_shovel", DragonScaleTier.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_SWORD = createDragonSwordItem("zombie_dragon_sword", DragonScaleTier.ZOMBIE);
    //Shears
    public static final RegistryObject<Item> DIAMOND_SHEARS = createTieredShearsItem("diamond_shears", ItemTier.DIAMOND);
    public static final RegistryObject<Item> NETHERITE_SHEARS = createTieredShearsItem("netherite_shears", ItemTier.NETHERITE);

    private static RegistryObject<Item> createDragonArmorItem(String name, int protection) {
        return ITEMS.register(name, () -> new ItemDragonArmor(protection, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonAxeItem(String name, DragonScaleTier tier, float attackDamageModifier, float attackSpeedModifier) {
        return ITEMS.register(name, () -> new ItemDragonAxe(tier, attackDamageModifier, attackSpeedModifier, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonAxeItem(String name, DragonScaleTier tier) {
        return ITEMS.register(name, () -> new ItemDragonAxe(tier, 5.0F, -2.8F/*Minecraft: -3.0F*/, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonBowItem(String name, DragonScaleTier tier) {
        return ITEMS.register(name, () -> new ItemDragonBow(tier, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonHoeItem(String name, DragonScaleTier tier) {
        return ITEMS.register(name, () -> new ItemDragonHoe(tier, (int) -tier.getAttackDamageBonus(), tier.getAttackDamageBonus() - 3.0F, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonPickaxeItem(String name, DragonScaleTier tier) {
        return ITEMS.register(name, () -> new ItemDragonPickaxe(tier, 1, -2.8F, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonScaleArmorItem(String name, DragonScaleMaterial material, EquipmentSlotType slot) {
        return ITEMS.register(name, () -> new ItemDragonScaleArmor(material, slot, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonScalesItem(String name, DragonType type) {
        return ITEMS.register(name, () -> new ItemDragonScales(type, new Item.Properties().tab(ITEM_TAB)));
    }

    private static RegistryObject<Item> createDragonShieldItem(String name, DragonScaleMaterial material) {
        return ITEMS.register(name, () -> new ItemDragonShield(material, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonShovelItem(String name, DragonScaleTier tier) {
        return ITEMS.register(name, () -> new ItemDragonShovel(tier, 1.5F, -3.0F, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonSwordItem(String name, DragonScaleTier tier) {
        return ITEMS.register(name, () -> new ItemDragonSword(tier, 3, -2.0F/*Minecraft: -2.4F*/, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createTieredShearsItem(String name, IItemTier tier) {
        return ITEMS.register(name, () -> new ItemTieredShears(tier, new Item.Properties().tab(ITEM_TAB)));
    }

    public static final ItemGroup BLOCK_TAB = new ItemGroup(MOD_ID + ".blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.BARRIER);
        }
    };

    public static final ItemGroup ITEM_TAB = new ItemGroup(MOD_ID + ".items") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.BARRIER);
        }
    };

    public static final ItemGroup EQUIPMENT_TAB = new ItemGroup(MOD_ID + ".equipments") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.BARRIER);
        }
    };

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
        ItemModelsProperties.register(AETHER_DRAGON_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(WATER_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(WATER_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(WATER_DRAGON_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(ICE_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(ICE_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(ICE_DRAGON_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(FIRE_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(FIRE_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(FIRE_DRAGON_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(FOREST_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(FOREST_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(FOREST_DRAGON_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(NETHER_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(NETHER_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(NETHER_DRAGON_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(ENDER_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(ENDER_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(ENDER_DRAGON_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(ENCHANT_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(ENCHANT_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(ENCHANT_DRAGON_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(SUNLIGHT_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(SUNLIGHT_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(SUNLIGHT_DRAGON_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(MOONLIGHT_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(MOONLIGHT_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(MOONLIGHT_DRAGON_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(STORM_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(STORM_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(STORM_DRAGON_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(TERRA_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(TERRA_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(TERRA_DRAGON_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
        ItemModelsProperties.register(ZOMBIE_DRAGON_BOW.get(), new ResourceLocation("pull"), ModItems::getPullItemProperty);
        ItemModelsProperties.register(ZOMBIE_DRAGON_BOW.get(), new ResourceLocation("pulling"), ModItems::getPullingItemProperty);
        ItemModelsProperties.register(ZOMBIE_DRAGON_SHIELD.get(), new ResourceLocation("blocking"), ModItems::getBlockingItemProperty);
    }
}