package net.dragonmounts3.init;

import mcp.MethodsReturnNonnullByDefault;
import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.api.DragonScaleMaterial;
import net.dragonmounts3.api.DragonScaleTier;
import net.dragonmounts3.block.HatchableDragonEggBlock;
import net.dragonmounts3.item.*;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.dragonmounts3.init.DMItemGroups.*;

@MethodsReturnNonnullByDefault
public class DMItems {
    public static final DeferredRegister<Item> ITEMS = DragonMounts.create(ForgeRegistries.ITEMS);
    //Scales Start
    public static final RegistryObject<DragonScalesItem> FOREST_DRAGON_SCALES = createDragonScalesItem("forest_dragon_scales", DragonTypes.FOREST, item());
    public static final RegistryObject<DragonScalesItem> FIRE_DRAGON_SCALES = createDragonScalesItem("fire_dragon_scales", DragonTypes.FIRE, item());
    public static final RegistryObject<DragonScalesItem> ICE_DRAGON_SCALES = createDragonScalesItem("ice_dragon_scales", DragonTypes.ICE, item());
    public static final RegistryObject<DragonScalesItem> WATER_DRAGON_SCALES = createDragonScalesItem("water_dragon_scales", DragonTypes.WATER, item());
    public static final RegistryObject<DragonScalesItem> AETHER_DRAGON_SCALES = createDragonScalesItem("aether_dragon_scales", DragonTypes.AETHER, item());
    public static final RegistryObject<DragonScalesItem> NETHER_DRAGON_SCALES = createDragonScalesItem("nether_dragon_scales", DragonTypes.NETHER, item());
    public static final RegistryObject<DragonScalesItem> ENDER_DRAGON_SCALES = createDragonScalesItem("ender_dragon_scales", DragonTypes.ENDER, item());
    public static final RegistryObject<DragonScalesItem> SUNLIGHT_DRAGON_SCALES = createDragonScalesItem("sunlight_dragon_scales", DragonTypes.SUNLIGHT, item());
    public static final RegistryObject<DragonScalesItem> ENCHANT_DRAGON_SCALES = createDragonScalesItem("enchant_dragon_scales", DragonTypes.ENCHANT, item());
    public static final RegistryObject<DragonScalesItem> STORM_DRAGON_SCALES = createDragonScalesItem("storm_dragon_scales", DragonTypes.STORM, item());
    public static final RegistryObject<DragonScalesItem> TERRA_DRAGON_SCALES = createDragonScalesItem("terra_dragon_scales", DragonTypes.TERRA, item());
    public static final RegistryObject<DragonScalesItem> ZOMBIE_DRAGON_SCALES = createDragonScalesItem("zombie_dragon_scales", DragonTypes.ZOMBIE, item());
    public static final RegistryObject<DragonScalesItem> MOONLIGHT_DRAGON_SCALES = createDragonScalesItem("moonlight_dragon_scales", DragonTypes.MOONLIGHT, item());
    public static final RegistryObject<DragonScalesItem> SCULK_DRAGON_SCALES = createDragonScalesItem("sculk_dragon_scales", DragonTypes.SCULK, item().fireResistant());
    //Dragon Armor
    public static final RegistryObject<Item> IRON_DRAGON_ARMOR = createDragonArmorItem("iron_dragon_armor", DragonArmorItem.TEXTURE_PREFIX + "iron.png", 5, tool().stacksTo(1));
    public static final RegistryObject<Item> GOLDEN_DRAGON_ARMOR = createDragonArmorItem("golden_dragon_armor", DragonArmorItem.TEXTURE_PREFIX + "gold.png", 7, tool().stacksTo(1));
    public static final RegistryObject<Item> DIAMOND_DRAGON_ARMOR = createDragonArmorItem("diamond_dragon_armor", DragonArmorItem.TEXTURE_PREFIX + "diamond.png", 11, tool().stacksTo(1));
    public static final RegistryObject<Item> EMERALD_DRAGON_ARMOR = createDragonArmorItem("emerald_dragon_armor", DragonArmorItem.TEXTURE_PREFIX + "emerald.png", 11, tool().stacksTo(1));
    public static final RegistryObject<Item> NETHERITE_DRAGON_ARMOR = createDragonArmorItem("netherite_dragon_armor", DragonArmorItem.TEXTURE_PREFIX + "netherite.png", 15, tool().stacksTo(1).fireResistant());
    //Dragon Scale Swords
    public static final RegistryObject<DragonScaleSwordItem> AETHER_DRAGON_SCALE_SWORD = createDragonScaleSwordItem("aether_dragon_sword", DragonScaleTier.AETHER, tool());
    public static final RegistryObject<DragonScaleSwordItem> WATER_DRAGON_SCALE_SWORD = createDragonScaleSwordItem("water_dragon_sword", DragonScaleTier.WATER, tool());
    public static final RegistryObject<DragonScaleSwordItem> ICE_DRAGON_SCALE_SWORD = createDragonScaleSwordItem("ice_dragon_sword", DragonScaleTier.ICE, tool());
    public static final RegistryObject<DragonScaleSwordItem> FIRE_DRAGON_SCALE_SWORD = createDragonScaleSwordItem("fire_dragon_sword", DragonScaleTier.FIRE, tool());
    public static final RegistryObject<DragonScaleSwordItem> FOREST_DRAGON_SCALE_SWORD = createDragonScaleSwordItem("forest_dragon_sword", DragonScaleTier.FOREST, tool());
    public static final RegistryObject<DragonScaleSwordItem> NETHER_DRAGON_SCALE_SWORD = createDragonScaleSwordItem("nether_dragon_sword", DragonScaleTier.NETHER, tool());
    public static final RegistryObject<DragonScaleSwordItem> ENDER_DRAGON_SCALE_SWORD = createDragonScaleSwordItem("ender_dragon_sword", DragonScaleTier.ENDER, tool());
    public static final RegistryObject<DragonScaleSwordItem> ENCHANT_DRAGON_SCALE_SWORD = createDragonScaleSwordItem("enchant_dragon_sword", DragonScaleTier.ENCHANT, tool());
    public static final RegistryObject<DragonScaleSwordItem> SUNLIGHT_DRAGON_SCALE_SWORD = createDragonScaleSwordItem("sunlight_dragon_sword", DragonScaleTier.SUNLIGHT, tool());
    public static final RegistryObject<DragonScaleSwordItem> MOONLIGHT_DRAGON_SCALE_SWORD = createDragonScaleSwordItem("moonlight_dragon_sword", DragonScaleTier.MOONLIGHT, tool());
    public static final RegistryObject<DragonScaleSwordItem> STORM_DRAGON_SCALE_SWORD = createDragonScaleSwordItem("storm_dragon_sword", DragonScaleTier.STORM, tool());
    public static final RegistryObject<DragonScaleSwordItem> TERRA_DRAGON_SCALE_SWORD = createDragonScaleSwordItem("terra_dragon_sword", DragonScaleTier.TERRA, tool());
    public static final RegistryObject<DragonScaleSwordItem> ZOMBIE_DRAGON_SCALE_SWORD = createDragonScaleSwordItem("zombie_dragon_sword", DragonScaleTier.ZOMBIE, tool());
    public static final RegistryObject<DragonScaleSwordItem> SCULK_DRAGON_SCALE_SWORD = createDragonScaleSwordItem("sculk_dragon_sword", DragonScaleTier.SCULK, tool().fireResistant());
    //Dragon Scale Axes
    public static final RegistryObject<DragonScaleAxeItem> AETHER_DRAGON_SCALE_AXE = createDragonScaleAxeItem("aether_dragon_axe", DragonScaleTier.AETHER, tool());
    public static final RegistryObject<DragonScaleAxeItem> WATER_DRAGON_SCALE_AXE = createDragonScaleAxeItem("water_dragon_axe", DragonScaleTier.WATER, tool());
    public static final RegistryObject<DragonScaleAxeItem> ICE_DRAGON_SCALE_AXE = createDragonScaleAxeItem("ice_dragon_axe", DragonScaleTier.ICE, tool());
    public static final RegistryObject<DragonScaleAxeItem> FIRE_DRAGON_SCALE_AXE = createDragonScaleAxeItem("fire_dragon_axe", DragonScaleTier.FIRE, tool());
    public static final RegistryObject<DragonScaleAxeItem> FOREST_DRAGON_SCALE_AXE = createDragonScaleAxeItem("forest_dragon_axe", DragonScaleTier.FOREST, tool());
    public static final RegistryObject<DragonScaleAxeItem> NETHER_DRAGON_SCALE_AXE = createDragonScaleAxeItem("nether_dragon_axe", DragonScaleTier.NETHER, 6.0F, -2.9F, tool());
    public static final RegistryObject<DragonScaleAxeItem> ENDER_DRAGON_SCALE_AXE = createDragonScaleAxeItem("ender_dragon_axe", DragonScaleTier.ENDER, 6.0F, -2.9F, tool());
    public static final RegistryObject<DragonScaleAxeItem> ENCHANT_DRAGON_SCALE_AXE = createDragonScaleAxeItem("enchant_dragon_axe", DragonScaleTier.ENCHANT, tool());
    public static final RegistryObject<DragonScaleAxeItem> SUNLIGHT_DRAGON_SCALE_AXE = createDragonScaleAxeItem("sunlight_dragon_axe", DragonScaleTier.SUNLIGHT, tool());
    public static final RegistryObject<DragonScaleAxeItem> MOONLIGHT_DRAGON_SCALE_AXE = createDragonScaleAxeItem("moonlight_dragon_axe", DragonScaleTier.MOONLIGHT, tool());
    public static final RegistryObject<DragonScaleAxeItem> STORM_DRAGON_SCALE_AXE = createDragonScaleAxeItem("storm_dragon_axe", DragonScaleTier.STORM, tool());
    public static final RegistryObject<DragonScaleAxeItem> TERRA_DRAGON_SCALE_AXE = createDragonScaleAxeItem("terra_dragon_axe", DragonScaleTier.TERRA, tool());
    public static final RegistryObject<DragonScaleAxeItem> ZOMBIE_DRAGON_SCALE_AXE = createDragonScaleAxeItem("zombie_dragon_axe", DragonScaleTier.ZOMBIE, tool());
    public static final RegistryObject<DragonScaleAxeItem> SCULK_DRAGON_SCALE_AXE = createDragonScaleAxeItem("sculk_dragon_axe", DragonScaleTier.SCULK, tool().fireResistant());
    //Dragon Scale Bows
    public static final RegistryObject<DragonScaleBowItem> AETHER_DRAGON_SCALE_BOW = createDragonScaleBowItem("aether_dragon_scale_bow", DragonScaleTier.AETHER, tool());
    public static final RegistryObject<DragonScaleBowItem> WATER_DRAGON_SCALE_BOW = createDragonScaleBowItem("water_dragon_scale_bow", DragonScaleTier.WATER, tool());
    public static final RegistryObject<DragonScaleBowItem> ICE_DRAGON_SCALE_BOW = createDragonScaleBowItem("ice_dragon_scale_bow", DragonScaleTier.ICE, tool());
    public static final RegistryObject<DragonScaleBowItem> FIRE_DRAGON_SCALE_BOW = createDragonScaleBowItem("fire_dragon_scale_bow", DragonScaleTier.FIRE, tool());
    public static final RegistryObject<DragonScaleBowItem> FOREST_DRAGON_SCALE_BOW = createDragonScaleBowItem("forest_dragon_scale_bow", DragonScaleTier.FOREST, tool());
    public static final RegistryObject<DragonScaleBowItem> NETHER_DRAGON_SCALE_BOW = createDragonScaleBowItem("nether_dragon_scale_bow", DragonScaleTier.NETHER, tool());
    public static final RegistryObject<DragonScaleBowItem> ENDER_DRAGON_SCALE_BOW = createDragonScaleBowItem("ender_dragon_scale_bow", DragonScaleTier.ENDER, tool());
    public static final RegistryObject<DragonScaleBowItem> ENCHANT_DRAGON_SCALE_BOW = createDragonScaleBowItem("enchant_dragon_scale_bow", DragonScaleTier.ENCHANT, tool());
    public static final RegistryObject<DragonScaleBowItem> SUNLIGHT_DRAGON_SCALE_BOW = createDragonScaleBowItem("sunlight_dragon_scale_bow", DragonScaleTier.SUNLIGHT, tool());
    public static final RegistryObject<DragonScaleBowItem> MOONLIGHT_DRAGON_SCALE_BOW = createDragonScaleBowItem("moonlight_dragon_scale_bow", DragonScaleTier.MOONLIGHT, tool());
    public static final RegistryObject<DragonScaleBowItem> STORM_DRAGON_SCALE_BOW = createDragonScaleBowItem("storm_dragon_scale_bow", DragonScaleTier.STORM, tool());
    public static final RegistryObject<DragonScaleBowItem> TERRA_DRAGON_SCALE_BOW = createDragonScaleBowItem("terra_dragon_scale_bow", DragonScaleTier.TERRA, tool());
    public static final RegistryObject<DragonScaleBowItem> ZOMBIE_DRAGON_SCALE_BOW = createDragonScaleBowItem("zombie_dragon_scale_bow", DragonScaleTier.ZOMBIE, tool());
    public static final RegistryObject<DragonScaleBowItem> SCULK_DRAGON_SCALE_BOW = createDragonScaleBowItem("sculk_dragon_scale_bow", DragonScaleTier.SCULK, tool().fireResistant());
    //Dragon Scale Shields
    public static final RegistryObject<DragonScaleShieldItem> AETHER_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("aether_dragon_scale_shield", DragonScaleMaterial.AETHER, tool());
    public static final RegistryObject<DragonScaleShieldItem> WATER_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("water_dragon_scale_shield", DragonScaleMaterial.WATER, tool());
    public static final RegistryObject<DragonScaleShieldItem> ICE_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("ice_dragon_scale_shield", DragonScaleMaterial.ICE, tool());
    public static final RegistryObject<DragonScaleShieldItem> FIRE_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("fire_dragon_scale_shield", DragonScaleMaterial.FIRE, tool());
    public static final RegistryObject<DragonScaleShieldItem> FOREST_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("forest_dragon_scale_shield", DragonScaleMaterial.FOREST, tool());
    public static final RegistryObject<DragonScaleShieldItem> NETHER_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("nether_dragon_scale_shield", DragonScaleMaterial.NETHER, tool());
    public static final RegistryObject<DragonScaleShieldItem> ENDER_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("ender_dragon_scale_shield", DragonScaleMaterial.ENDER, tool());
    public static final RegistryObject<DragonScaleShieldItem> ENCHANT_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("enchant_dragon_scale_shield", DragonScaleMaterial.ENCHANT, tool());
    public static final RegistryObject<DragonScaleShieldItem> SUNLIGHT_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("sunlight_dragon_scale_shield", DragonScaleMaterial.SUNLIGHT, tool());
    public static final RegistryObject<DragonScaleShieldItem> MOONLIGHT_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("moonlight_dragon_scale_shield", DragonScaleMaterial.MOONLIGHT, tool());
    public static final RegistryObject<DragonScaleShieldItem> STORM_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("storm_dragon_scale_shield", DragonScaleMaterial.STORM, tool());
    public static final RegistryObject<DragonScaleShieldItem> TERRA_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("terra_dragon_scale_shield", DragonScaleMaterial.TERRA, tool());
    public static final RegistryObject<DragonScaleShieldItem> ZOMBIE_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("zombie_dragon_scale_shield", DragonScaleMaterial.ZOMBIE, tool());
    public static final RegistryObject<DragonScaleShieldItem> SCULK_DRAGON_SCALE_SHIELD = createDragonScaleShieldItem("sculk_dragon_scale_shield", DragonScaleMaterial.SCULK, tool().fireResistant());
    //Dragon Scale Tools - Aether
    public static final RegistryObject<DragonScaleShovelItem> AETHER_DRAGON_SCALE_SHOVEL = createDragonScaleShovelItem("aether_dragon_shovel", DragonScaleTier.AETHER, tool());
    public static final RegistryObject<DragonScalePickaxeItem> AETHER_DRAGON_SCALE_PICKAXE = createDragonScalePickaxeItem("aether_dragon_pickaxe", DragonScaleTier.AETHER, tool());
    public static final RegistryObject<DragonScaleHoeItem> AETHER_DRAGON_SCALE_HOE = createDragonScaleHoeItem("aether_dragon_hoe", DragonScaleTier.AETHER, tool());
    //Dragon Scale Tools - Water
    public static final RegistryObject<DragonScaleShovelItem> WATER_DRAGON_SCALE_SHOVEL = createDragonScaleShovelItem("water_dragon_shovel", DragonScaleTier.WATER, tool());
    public static final RegistryObject<DragonScalePickaxeItem> WATER_DRAGON_SCALE_PICKAXE = createDragonScalePickaxeItem("water_dragon_pickaxe", DragonScaleTier.WATER, tool());
    public static final RegistryObject<DragonScaleHoeItem> WATER_DRAGON_SCALE_HOE = createDragonScaleHoeItem("water_dragon_hoe", DragonScaleTier.WATER, tool());
    //Dragon Scale Tools - Ice
    public static final RegistryObject<DragonScaleShovelItem> ICE_DRAGON_SCALE_SHOVEL = createDragonScaleShovelItem("ice_dragon_shovel", DragonScaleTier.ICE, tool());
    public static final RegistryObject<DragonScalePickaxeItem> ICE_DRAGON_SCALE_PICKAXE = createDragonScalePickaxeItem("ice_dragon_pickaxe", DragonScaleTier.ICE, tool());
    public static final RegistryObject<DragonScaleHoeItem> ICE_DRAGON_SCALE_HOE = createDragonScaleHoeItem("ice_dragon_hoe", DragonScaleTier.ICE, tool());
    //Dragon Scale Tools - Fire
    public static final RegistryObject<DragonScaleShovelItem> FIRE_DRAGON_SCALE_SHOVEL = createDragonScaleShovelItem("fire_dragon_shovel", DragonScaleTier.FIRE, tool());
    public static final RegistryObject<DragonScalePickaxeItem> FIRE_DRAGON_SCALE_PICKAXE = createDragonScalePickaxeItem("fire_dragon_pickaxe", DragonScaleTier.FIRE, tool());
    public static final RegistryObject<DragonScaleHoeItem> FIRE_DRAGON_SCALE_HOE = createDragonScaleHoeItem("fire_dragon_hoe", DragonScaleTier.FIRE, tool());
    //Dragon Scale Tools - Forest
    public static final RegistryObject<DragonScaleShovelItem> FOREST_DRAGON_SCALE_SHOVEL = createDragonScaleShovelItem("forest_dragon_shovel", DragonScaleTier.FOREST, tool());
    public static final RegistryObject<DragonScalePickaxeItem> FOREST_DRAGON_SCALE_PICKAXE = createDragonScalePickaxeItem("forest_dragon_pickaxe", DragonScaleTier.FOREST, tool());
    public static final RegistryObject<DragonScaleHoeItem> FOREST_DRAGON_SCALE_HOE = createDragonScaleHoeItem("forest_dragon_hoe", DragonScaleTier.FOREST, tool());
    //Dragon Scale Tools - Nether
    public static final RegistryObject<DragonScaleShovelItem> NETHER_DRAGON_SCALE_SHOVEL = createDragonScaleShovelItem("nether_dragon_shovel", DragonScaleTier.NETHER, tool());
    public static final RegistryObject<DragonScalePickaxeItem> NETHER_DRAGON_SCALE_PICKAXE = createDragonScalePickaxeItem("nether_dragon_pickaxe", DragonScaleTier.NETHER, tool());
    public static final RegistryObject<DragonScaleHoeItem> NETHER_DRAGON_SCALE_HOE = createDragonScaleHoeItem("nether_dragon_hoe", DragonScaleTier.NETHER, tool());
    //Dragon Scale Tools - Ender
    public static final RegistryObject<DragonScaleShovelItem> ENDER_DRAGON_SCALE_SHOVEL = createDragonScaleShovelItem("ender_dragon_shovel", DragonScaleTier.ENDER, tool());
    public static final RegistryObject<DragonScalePickaxeItem> ENDER_DRAGON_SCALE_PICKAXE = createDragonScalePickaxeItem("ender_dragon_pickaxe", DragonScaleTier.ENDER, tool());
    public static final RegistryObject<DragonScaleHoeItem> ENDER_DRAGON_SCALE_HOE = createDragonScaleHoeItem("ender_dragon_hoe", DragonScaleTier.ENDER, tool());
    //Dragon Scale Tools - Enchant
    public static final RegistryObject<DragonScaleShovelItem> ENCHANT_DRAGON_SCALE_SHOVEL = createDragonScaleShovelItem("enchant_dragon_shovel", DragonScaleTier.ENCHANT, tool());
    public static final RegistryObject<DragonScalePickaxeItem> ENCHANT_DRAGON_SCALE_PICKAXE = createDragonScalePickaxeItem("enchant_dragon_pickaxe", DragonScaleTier.ENCHANT, tool());
    public static final RegistryObject<DragonScaleHoeItem> ENCHANT_DRAGON_SCALE_HOE = createDragonScaleHoeItem("enchant_dragon_hoe", DragonScaleTier.ENCHANT, tool());
    //Dragon Scale Tools - Sunlight
    public static final RegistryObject<DragonScaleShovelItem> SUNLIGHT_DRAGON_SCALE_SHOVEL = createDragonScaleShovelItem("sunlight_dragon_shovel", DragonScaleTier.SUNLIGHT, tool());
    public static final RegistryObject<DragonScalePickaxeItem> SUNLIGHT_DRAGON_SCALE_PICKAXE = createDragonScalePickaxeItem("sunlight_dragon_pickaxe", DragonScaleTier.SUNLIGHT, tool());
    public static final RegistryObject<DragonScaleHoeItem> SUNLIGHT_DRAGON_SCALE_HOE = createDragonScaleHoeItem("sunlight_dragon_hoe", DragonScaleTier.SUNLIGHT, tool());
    //Dragon Scale Tools - Moonlight
    public static final RegistryObject<DragonScaleShovelItem> MOONLIGHT_DRAGON_SCALE_SHOVEL = createDragonScaleShovelItem("moonlight_dragon_shovel", DragonScaleTier.MOONLIGHT, tool());
    public static final RegistryObject<DragonScalePickaxeItem> MOONLIGHT_DRAGON_SCALE_PICKAXE = createDragonScalePickaxeItem("moonlight_dragon_pickaxe", DragonScaleTier.MOONLIGHT, tool());
    public static final RegistryObject<DragonScaleHoeItem> MOONLIGHT_DRAGON_SCALE_HOE = createDragonScaleHoeItem("moonlight_dragon_hoe", DragonScaleTier.MOONLIGHT, tool());
    //Dragon Scale Tools - Storm
    public static final RegistryObject<DragonScaleShovelItem> STORM_DRAGON_SCALE_SHOVEL = createDragonScaleShovelItem("storm_dragon_shovel", DragonScaleTier.STORM, tool());
    public static final RegistryObject<DragonScalePickaxeItem> STORM_DRAGON_SCALE_PICKAXE = createDragonScalePickaxeItem("storm_dragon_pickaxe", DragonScaleTier.STORM, tool());
    public static final RegistryObject<DragonScaleHoeItem> STORM_DRAGON_SCALE_HOE = createDragonScaleHoeItem("storm_dragon_hoe", DragonScaleTier.STORM, tool());
    //Dragon Scale Tools - Terra
    public static final RegistryObject<DragonScaleShovelItem> TERRA_DRAGON_SCALE_SHOVEL = createDragonScaleShovelItem("terra_dragon_shovel", DragonScaleTier.TERRA, tool());
    public static final RegistryObject<DragonScalePickaxeItem> TERRA_DRAGON_SCALE_PICKAXE = createDragonScalePickaxeItem("terra_dragon_pickaxe", DragonScaleTier.TERRA, tool());
    public static final RegistryObject<DragonScaleHoeItem> TERRA_DRAGON_SCALE_HOE = createDragonScaleHoeItem("terra_dragon_hoe", DragonScaleTier.TERRA, tool());
    //Dragon Scale Tools - Zombie
    public static final RegistryObject<DragonScaleShovelItem> ZOMBIE_DRAGON_SCALE_SHOVEL = createDragonScaleShovelItem("zombie_dragon_shovel", DragonScaleTier.ZOMBIE, tool());
    public static final RegistryObject<DragonScalePickaxeItem> ZOMBIE_DRAGON_SCALE_PICKAXE = createDragonScalePickaxeItem("zombie_dragon_pickaxe", DragonScaleTier.ZOMBIE, tool());
    public static final RegistryObject<DragonScaleHoeItem> ZOMBIE_DRAGON_SCALE_HOE = createDragonScaleHoeItem("zombie_dragon_hoe", DragonScaleTier.ZOMBIE, tool());
    //Dragon Scale Tools - Sculk
    public static final RegistryObject<DragonScaleShovelItem> SCULK_DRAGON_SCALE_SHOVEL = createDragonScaleShovelItem("sculk_dragon_shovel", DragonScaleTier.SCULK, tool().fireResistant());
    public static final RegistryObject<DragonScalePickaxeItem> SCULK_DRAGON_SCALE_PICKAXE = createDragonScalePickaxeItem("sculk_dragon_pickaxe", DragonScaleTier.SCULK, tool().fireResistant());
    public static final RegistryObject<DragonScaleHoeItem> SCULK_DRAGON_SCALE_HOE = createDragonScaleHoeItem("sculk_dragon_hoe", DragonScaleTier.SCULK, tool().fireResistant());
    //Dragon Scale Armors - Aether
    public static final RegistryObject<DragonScaleArmorItem> AETHER_DRAGON_SCALE_HELMET = createDragonScaleHelmetItem("aether_dragon_scale_helmet", DragonScaleMaterial.AETHER, DragonScaleArmorEffect.AETHER, tool());
    public static final RegistryObject<DragonScaleArmorItem> AETHER_DRAGON_SCALE_CHESTPLATE = createDragonScaleChestplateItem("aether_dragon_scale_chestplate", DragonScaleMaterial.AETHER, DragonScaleArmorEffect.AETHER, tool());
    public static final RegistryObject<DragonScaleArmorItem> AETHER_DRAGON_SCALE_LEGGINGS = createDragonScaleLeggingsItem("aether_dragon_scale_leggings", DragonScaleMaterial.AETHER, DragonScaleArmorEffect.AETHER, tool());
    public static final RegistryObject<DragonScaleArmorItem> AETHER_DRAGON_SCALE_BOOTS = createDragonScaleBootsItem("aether_dragon_scale_boots", DragonScaleMaterial.AETHER, DragonScaleArmorEffect.AETHER, tool());
    //Dragon Scale Armors - Water
    public static final RegistryObject<DragonScaleArmorItem> WATER_DRAGON_SCALE_HELMET = createDragonScaleHelmetItem("water_dragon_scale_helmet", DragonScaleMaterial.WATER, DragonScaleArmorEffect.WATER, tool());
    public static final RegistryObject<DragonScaleArmorItem> WATER_DRAGON_SCALE_CHESTPLATE = createDragonScaleChestplateItem("water_dragon_scale_chestplate", DragonScaleMaterial.WATER, DragonScaleArmorEffect.WATER, tool());
    public static final RegistryObject<DragonScaleArmorItem> WATER_DRAGON_SCALE_LEGGINGS = createDragonScaleLeggingsItem("water_dragon_scale_leggings", DragonScaleMaterial.WATER, DragonScaleArmorEffect.WATER, tool());
    public static final RegistryObject<DragonScaleArmorItem> WATER_DRAGON_SCALE_BOOTS = createDragonScaleBootsItem("water_dragon_scale_boots", DragonScaleMaterial.WATER, DragonScaleArmorEffect.WATER, tool());
    //Dragon Scale Armors - Ice
    public static final RegistryObject<DragonScaleArmorItem> ICE_DRAGON_SCALE_HELMET = createDragonScaleHelmetItem("ice_dragon_scale_helmet", DragonScaleMaterial.ICE, DragonScaleArmorEffect.ICE, tool());
    public static final RegistryObject<DragonScaleArmorItem> ICE_DRAGON_SCALE_CHESTPLATE = createDragonScaleChestplateItem("ice_dragon_scale_chestplate", DragonScaleMaterial.ICE, DragonScaleArmorEffect.ICE, tool());
    public static final RegistryObject<DragonScaleArmorItem> ICE_DRAGON_SCALE_LEGGINGS = createDragonScaleLeggingsItem("ice_dragon_scale_leggings", DragonScaleMaterial.ICE, DragonScaleArmorEffect.ICE, tool());
    public static final RegistryObject<DragonScaleArmorItem> ICE_DRAGON_SCALE_BOOTS = createDragonScaleBootsItem("ice_dragon_scale_boots", DragonScaleMaterial.ICE, DragonScaleArmorEffect.ICE, tool());
    //Dragon Scale Armors - Fire
    public static final RegistryObject<DragonScaleArmorItem> FIRE_DRAGON_SCALE_HELMET = createDragonScaleHelmetItem("fire_dragon_scale_helmet", DragonScaleMaterial.FIRE, DragonScaleArmorEffect.FIRE, tool());
    public static final RegistryObject<DragonScaleArmorItem> FIRE_DRAGON_SCALE_CHESTPLATE = createDragonScaleChestplateItem("fire_dragon_scale_chestplate", DragonScaleMaterial.FIRE, DragonScaleArmorEffect.FIRE, tool());
    public static final RegistryObject<DragonScaleArmorItem> FIRE_DRAGON_SCALE_LEGGINGS = createDragonScaleLeggingsItem("fire_dragon_scale_leggings", DragonScaleMaterial.FIRE, DragonScaleArmorEffect.FIRE, tool());
    public static final RegistryObject<DragonScaleArmorItem> FIRE_DRAGON_SCALE_BOOTS = createDragonScaleBootsItem("fire_dragon_scale_boots", DragonScaleMaterial.FIRE, DragonScaleArmorEffect.FIRE, tool());
    //Dragon Scale Armors - Forest
    public static final RegistryObject<DragonScaleArmorItem> FOREST_DRAGON_SCALE_HELMET = createDragonScaleHelmetItem("forest_dragon_scale_helmet", DragonScaleMaterial.FOREST, DragonScaleArmorEffect.FOREST, tool());
    public static final RegistryObject<DragonScaleArmorItem> FOREST_DRAGON_SCALE_CHESTPLATE = createDragonScaleChestplateItem("forest_dragon_scale_chestplate", DragonScaleMaterial.FOREST, DragonScaleArmorEffect.FOREST, tool());
    public static final RegistryObject<DragonScaleArmorItem> FOREST_DRAGON_SCALE_LEGGINGS = createDragonScaleLeggingsItem("forest_dragon_scale_leggings", DragonScaleMaterial.FOREST, DragonScaleArmorEffect.FOREST, tool());
    public static final RegistryObject<DragonScaleArmorItem> FOREST_DRAGON_SCALE_BOOTS = createDragonScaleBootsItem("forest_dragon_scale_boots", DragonScaleMaterial.FOREST, DragonScaleArmorEffect.FOREST, tool());
    //Dragon Scale Armors - Nether
    public static final RegistryObject<DragonScaleArmorItem> NETHER_DRAGON_SCALE_HELMET = createDragonScaleHelmetItem("nether_dragon_scale_helmet", DragonScaleMaterial.NETHER, DragonScaleArmorEffect.NETHER, tool());
    public static final RegistryObject<DragonScaleArmorItem> NETHER_DRAGON_SCALE_CHESTPLATE = createDragonScaleChestplateItem("nether_dragon_scale_chestplate", DragonScaleMaterial.NETHER, DragonScaleArmorEffect.NETHER, tool());
    public static final RegistryObject<DragonScaleArmorItem> NETHER_DRAGON_SCALE_LEGGINGS = createDragonScaleLeggingsItem("nether_dragon_scale_leggings", DragonScaleMaterial.NETHER, DragonScaleArmorEffect.NETHER, tool());
    public static final RegistryObject<DragonScaleArmorItem> NETHER_DRAGON_SCALE_BOOTS = createDragonScaleBootsItem("nether_dragon_scale_boots", DragonScaleMaterial.NETHER, DragonScaleArmorEffect.NETHER, tool());
    //Dragon Scale Armors - Ender
    public static final RegistryObject<DragonScaleArmorItem> ENDER_DRAGON_SCALE_HELMET = createDragonScaleHelmetItem("ender_dragon_scale_helmet", DragonScaleMaterial.ENDER, DragonScaleArmorEffect.ENDER, tool());
    public static final RegistryObject<DragonScaleArmorItem> ENDER_DRAGON_SCALE_CHESTPLATE = createDragonScaleChestplateItem("ender_dragon_scale_chestplate", DragonScaleMaterial.ENDER, DragonScaleArmorEffect.ENDER, tool());
    public static final RegistryObject<DragonScaleArmorItem> ENDER_DRAGON_SCALE_LEGGINGS = createDragonScaleLeggingsItem("ender_dragon_scale_leggings", DragonScaleMaterial.ENDER, DragonScaleArmorEffect.ENDER, tool());
    public static final RegistryObject<DragonScaleArmorItem> ENDER_DRAGON_SCALE_BOOTS = createDragonScaleBootsItem("ender_dragon_scale_boots", DragonScaleMaterial.ENDER, DragonScaleArmorEffect.ENDER, tool());
    //Dragon Scale Armors - Enchant
    public static final RegistryObject<DragonScaleArmorItem> ENCHANT_DRAGON_SCALE_HELMET = createDragonScaleHelmetItem("enchant_dragon_scale_helmet", DragonScaleMaterial.ENCHANT, DragonScaleArmorEffect.ENCHANT, tool());
    public static final RegistryObject<DragonScaleArmorItem> ENCHANT_DRAGON_SCALE_CHESTPLATE = createDragonScaleChestplateItem("enchant_dragon_scale_chestplate", DragonScaleMaterial.ENCHANT, DragonScaleArmorEffect.ENCHANT, tool());
    public static final RegistryObject<DragonScaleArmorItem> ENCHANT_DRAGON_SCALE_LEGGINGS = createDragonScaleLeggingsItem("enchant_dragon_scale_leggings", DragonScaleMaterial.ENCHANT, DragonScaleArmorEffect.ENCHANT, tool());
    public static final RegistryObject<DragonScaleArmorItem> ENCHANT_DRAGON_SCALE_BOOTS = createDragonScaleBootsItem("enchant_dragon_scale_boots", DragonScaleMaterial.ENCHANT, DragonScaleArmorEffect.ENCHANT, tool());
    //Dragon Scale Armors - Sunlight
    public static final RegistryObject<DragonScaleArmorItem> SUNLIGHT_DRAGON_SCALE_HELMET = createDragonScaleHelmetItem("sunlight_dragon_scale_helmet", DragonScaleMaterial.SUNLIGHT, DragonScaleArmorEffect.SUNLIGHT, tool());
    public static final RegistryObject<DragonScaleArmorItem> SUNLIGHT_DRAGON_SCALE_CHESTPLATE = createDragonScaleChestplateItem("sunlight_dragon_scale_chestplate", DragonScaleMaterial.SUNLIGHT, DragonScaleArmorEffect.SUNLIGHT, tool());
    public static final RegistryObject<DragonScaleArmorItem> SUNLIGHT_DRAGON_SCALE_LEGGINGS = createDragonScaleLeggingsItem("sunlight_dragon_scale_leggings", DragonScaleMaterial.SUNLIGHT, DragonScaleArmorEffect.SUNLIGHT, tool());
    public static final RegistryObject<DragonScaleArmorItem> SUNLIGHT_DRAGON_SCALE_BOOTS = createDragonScaleBootsItem("sunlight_dragon_scale_boots", DragonScaleMaterial.SUNLIGHT, DragonScaleArmorEffect.SUNLIGHT, tool());
    //Dragon Scale Armors - Moonlight
    public static final RegistryObject<DragonScaleArmorItem> MOONLIGHT_DRAGON_SCALE_HELMET = createDragonScaleHelmetItem("moonlight_dragon_scale_helmet", DragonScaleMaterial.MOONLIGHT, DragonScaleArmorEffect.MOONLIGHT, tool());
    public static final RegistryObject<DragonScaleArmorItem> MOONLIGHT_DRAGON_SCALE_CHESTPLATE = createDragonScaleChestplateItem("moonlight_dragon_scale_chestplate", DragonScaleMaterial.MOONLIGHT, DragonScaleArmorEffect.MOONLIGHT, tool());
    public static final RegistryObject<DragonScaleArmorItem> MOONLIGHT_DRAGON_SCALE_LEGGINGS = createDragonScaleLeggingsItem("moonlight_dragon_scale_leggings", DragonScaleMaterial.MOONLIGHT, DragonScaleArmorEffect.MOONLIGHT, tool());
    public static final RegistryObject<DragonScaleArmorItem> MOONLIGHT_DRAGON_SCALE_BOOTS = createDragonScaleBootsItem("moonlight_dragon_scale_boots", DragonScaleMaterial.MOONLIGHT, DragonScaleArmorEffect.MOONLIGHT, tool());
    //Dragon Scale Armors - Storm
    public static final RegistryObject<DragonScaleArmorItem> STORM_DRAGON_SCALE_HELMET = createDragonScaleHelmetItem("storm_dragon_scale_helmet", DragonScaleMaterial.STORM, DragonScaleArmorEffect.STORM, tool());
    public static final RegistryObject<DragonScaleArmorItem> STORM_DRAGON_SCALE_CHESTPLATE = createDragonScaleChestplateItem("storm_dragon_scale_chestplate", DragonScaleMaterial.STORM, DragonScaleArmorEffect.STORM, tool());
    public static final RegistryObject<DragonScaleArmorItem> STORM_DRAGON_SCALE_LEGGINGS = createDragonScaleLeggingsItem("storm_dragon_scale_leggings", DragonScaleMaterial.STORM, DragonScaleArmorEffect.STORM, tool());
    public static final RegistryObject<DragonScaleArmorItem> STORM_DRAGON_SCALE_BOOTS = createDragonScaleBootsItem("storm_dragon_scale_boots", DragonScaleMaterial.STORM, DragonScaleArmorEffect.STORM, tool());
    //Dragon Scale Armors - Terra
    public static final RegistryObject<DragonScaleArmorItem> TERRA_DRAGON_SCALE_HELMET = createDragonScaleHelmetItem("terra_dragon_scale_helmet", DragonScaleMaterial.TERRA, DragonScaleArmorEffect.TERRA, tool());
    public static final RegistryObject<DragonScaleArmorItem> TERRA_DRAGON_SCALE_CHESTPLATE = createDragonScaleChestplateItem("terra_dragon_scale_chestplate", DragonScaleMaterial.TERRA, DragonScaleArmorEffect.TERRA, tool());
    public static final RegistryObject<DragonScaleArmorItem> TERRA_DRAGON_SCALE_LEGGINGS = createDragonScaleLeggingsItem("terra_dragon_scale_leggings", DragonScaleMaterial.TERRA, DragonScaleArmorEffect.TERRA, tool());
    public static final RegistryObject<DragonScaleArmorItem> TERRA_DRAGON_SCALE_BOOTS = createDragonScaleBootsItem("terra_dragon_scale_boots", DragonScaleMaterial.TERRA, DragonScaleArmorEffect.TERRA, tool());
    //Dragon Scale Armors - Zombie
    public static final RegistryObject<DragonScaleArmorItem> ZOMBIE_DRAGON_SCALE_HELMET = createDragonScaleHelmetItem("zombie_dragon_scale_helmet", DragonScaleMaterial.ZOMBIE, DragonScaleArmorEffect.ZOMBIE, tool());
    public static final RegistryObject<DragonScaleArmorItem> ZOMBIE_DRAGON_SCALE_CHESTPLATE = createDragonScaleChestplateItem("zombie_dragon_scale_chestplate", DragonScaleMaterial.ZOMBIE, DragonScaleArmorEffect.ZOMBIE, tool());
    public static final RegistryObject<DragonScaleArmorItem> ZOMBIE_DRAGON_SCALE_LEGGINGS = createDragonScaleLeggingsItem("zombie_dragon_scale_leggings", DragonScaleMaterial.ZOMBIE, DragonScaleArmorEffect.ZOMBIE, tool());
    public static final RegistryObject<DragonScaleArmorItem> ZOMBIE_DRAGON_SCALE_BOOTS = createDragonScaleBootsItem("zombie_dragon_scale_boots", DragonScaleMaterial.ZOMBIE, DragonScaleArmorEffect.ZOMBIE, tool());
    //Dragon Scale Armors - Sculk
    public static final RegistryObject<DragonScaleArmorItem> SCULK_DRAGON_SCALE_HELMET = createDragonScaleHelmetItem("sculk_dragon_scale_helmet", DragonScaleMaterial.SCULK, null, tool().fireResistant());
    public static final RegistryObject<DragonScaleArmorItem> SCULK_DRAGON_SCALE_CHESTPLATE = createDragonScaleChestplateItem("sculk_dragon_scale_chestplate", DragonScaleMaterial.SCULK, null, tool().fireResistant());
    public static final RegistryObject<DragonScaleArmorItem> SCULK_DRAGON_SCALE_LEGGINGS = createDragonScaleLeggingsItem("sculk_dragon_scale_leggings", DragonScaleMaterial.SCULK, null, tool().fireResistant());
    public static final RegistryObject<DragonScaleArmorItem> SCULK_DRAGON_SCALE_BOOTS = createDragonScaleBootsItem("sculk_dragon_scale_boots", DragonScaleMaterial.SCULK, null, tool().fireResistant());
    //Dragon Spawn Eggs
    public static final RegistryObject<DragonSpawnEggItem> AETHER_DRAGON_SPAWN_EGG = createDragonSpawnEgg("aether_dragon_spawn_egg", DragonTypes.AETHER, 0x05C3D2, 0x281EE8, item());
    public static final RegistryObject<DragonSpawnEggItem> ENCHANT_DRAGON_SPAWN_EGG = createDragonSpawnEgg("enchant_dragon_spawn_egg", DragonTypes.ENCHANT, 0xCC0DE0, 0xFFFFFF, item());
    public static final RegistryObject<DragonSpawnEggItem> ENDER_DRAGON_SPAWN_EGG = createDragonSpawnEgg("ender_dragon_spawn_egg", DragonTypes.ENDER, 0x08080C, 0x79087E, item());
    public static final RegistryObject<DragonSpawnEggItem> FIRE_DRAGON_SPAWN_EGG = createDragonSpawnEgg("fire_dragon_spawn_egg", DragonTypes.FIRE, 0x620508, 0xF7A502, item());
    public static final RegistryObject<DragonSpawnEggItem> FOREST_DRAGON_SPAWN_EGG = createDragonSpawnEgg("forest_dragon_spawn_egg", DragonTypes.FOREST, 0x0C9613, 0x036408, item());
    public static final RegistryObject<DragonSpawnEggItem> ICE_DRAGON_SPAWN_EGG = createDragonSpawnEgg("ice_dragon_spawn_egg", DragonTypes.ICE, 0xFFFFFF, 0x02D0EE, item());
    public static final RegistryObject<DragonSpawnEggItem> MOONLIGHT_DRAGON_SPAWN_EGG = createDragonSpawnEgg("moonlight_dragon_spawn_egg", DragonTypes.MOONLIGHT, 0x00164E, 0xFEFEFE, item());
    public static final RegistryObject<DragonSpawnEggItem> NETHER_DRAGON_SPAWN_EGG = createDragonSpawnEgg("nether_dragon_spawn_egg", DragonTypes.NETHER, 0x632F1B, 0xE7A621, item());
    public static final RegistryObject<DragonSpawnEggItem> SCULK_DRAGON_SPAWN_EGG = createDragonSpawnEgg("sculk_dragon_spawn_egg", DragonTypes.SCULK, 0x0F4649, 0x39D6E0, item());
    public static final RegistryObject<DragonSpawnEggItem> SKELETON_DRAGON_SPAWN_EGG = createDragonSpawnEgg("skeleton_dragon_spawn_egg", DragonTypes.SKELETON, 0xFFFFFF, 0x474F51, item());
    public static final RegistryObject<DragonSpawnEggItem> STORM_DRAGON_SPAWN_EGG = createDragonSpawnEgg("storm_dragon_spawn_egg", DragonTypes.STORM, 0x010B0F, 0x0FA8CE, item());
    public static final RegistryObject<DragonSpawnEggItem> SUNLIGHT_DRAGON_SPAWN_EGG = createDragonSpawnEgg("sunlight_dragon_spawn_egg", DragonTypes.SUNLIGHT, 0xF4950D, 0xF4E10D, item());
    public static final RegistryObject<DragonSpawnEggItem> TERRA_DRAGON_SPAWN_EGG = createDragonSpawnEgg("terra_dragon_spawn_egg", DragonTypes.TERRA, 0x674517, 0xE6B10D, item());
    public static final RegistryObject<DragonSpawnEggItem> WATER_DRAGON_SPAWN_EGG = createDragonSpawnEgg("water_dragon_spawn_egg", DragonTypes.WATER, 0x546FAD, 0x2B427E, item());
    public static final RegistryObject<DragonSpawnEggItem> WITHER_DRAGON_SPAWN_EGG = createDragonSpawnEgg("wither_dragon_spawn_egg", DragonTypes.WITHER, 0x8A9999, 0x474F51, item());
    public static final RegistryObject<DragonSpawnEggItem> ZOMBIE_DRAGON_SPAWN_EGG = createDragonSpawnEgg("zombie_dragon_spawn_egg", DragonTypes.ZOMBIE, 0x66664B, 0xB6D035, item());
    //Shears
    public static final RegistryObject<Item> DIAMOND_SHEARS = createTieredShearsItem("diamond_shears", ItemTier.DIAMOND, item());
    public static final RegistryObject<Item> NETHERITE_SHEARS = createTieredShearsItem("netherite_shears", ItemTier.NETHERITE, item().fireResistant());
    //Carriages
    public static final RegistryObject<CarriageItem> ACACIA_CARRIAGE = ITEMS.register("acacia_carriage", () -> new CarriageItem(CarriageTypes.ACACIA, item()));
    public static final RegistryObject<CarriageItem> BIRCH_CARRIAGE = ITEMS.register("birch_carriage", () -> new CarriageItem(CarriageTypes.BIRCH, item()));
    public static final RegistryObject<CarriageItem> DARK_OAK_CARRIAGE = ITEMS.register("dark_oak_carriage", () -> new CarriageItem(CarriageTypes.DARK_OAK, item()));
    public static final RegistryObject<CarriageItem> JUNGLE_CARRIAGE = ITEMS.register("jungle_carriage", () -> new CarriageItem(CarriageTypes.JUNGLE, item()));
    public static final RegistryObject<CarriageItem> OAK_CARRIAGE = ITEMS.register("oak_carriage", () -> new CarriageItem(CarriageTypes.OAK, item()));
    public static final RegistryObject<CarriageItem> SPRUCE_CARRIAGE = ITEMS.register("spruce_carriage", () -> new CarriageItem(CarriageTypes.SPRUCE, item()));
    //Dragon Amulets
    public static final RegistryObject<DragonAmuletItem> DRAGON_AMULET = ITEMS.register("dragon_amulet", () -> new DragonAmuletItem(item()));
    public static final RegistryObject<FilledDragonAmuletItem> FOREST_DRAGON_AMULET = createDragonAmuletItem("forest_dragon_amulet", DragonTypes.FOREST, none());
    public static final RegistryObject<FilledDragonAmuletItem> FIRE_DRAGON_AMULET = createDragonAmuletItem("fire_dragon_amulet", DragonTypes.FIRE, none());
    public static final RegistryObject<FilledDragonAmuletItem> ICE_DRAGON_AMULET = createDragonAmuletItem("ice_dragon_amulet", DragonTypes.ICE, none());
    public static final RegistryObject<FilledDragonAmuletItem> WATER_DRAGON_AMULET = createDragonAmuletItem("water_dragon_amulet", DragonTypes.WATER, none());
    public static final RegistryObject<FilledDragonAmuletItem> AETHER_DRAGON_AMULET = createDragonAmuletItem("aether_dragon_amulet", DragonTypes.AETHER, none());
    public static final RegistryObject<FilledDragonAmuletItem> NETHER_DRAGON_AMULET = createDragonAmuletItem("nether_dragon_amulet", DragonTypes.NETHER, none());
    public static final RegistryObject<FilledDragonAmuletItem> ENDER_DRAGON_AMULET = createDragonAmuletItem("ender_dragon_amulet", DragonTypes.ENDER, none());
    public static final RegistryObject<FilledDragonAmuletItem> SUNLIGHT_DRAGON_AMULET = createDragonAmuletItem("sunlight_dragon_amulet", DragonTypes.SUNLIGHT, none());
    public static final RegistryObject<FilledDragonAmuletItem> ENCHANT_DRAGON_AMULET = createDragonAmuletItem("enchant_dragon_amulet", DragonTypes.ENCHANT, none());
    public static final RegistryObject<FilledDragonAmuletItem> STORM_DRAGON_AMULET = createDragonAmuletItem("storm_dragon_amulet", DragonTypes.STORM, none());
    public static final RegistryObject<FilledDragonAmuletItem> TERRA_DRAGON_AMULET = createDragonAmuletItem("terra_dragon_amulet", DragonTypes.TERRA, none());
    public static final RegistryObject<FilledDragonAmuletItem> ZOMBIE_DRAGON_AMULET = createDragonAmuletItem("zombie_dragon_amulet", DragonTypes.ZOMBIE, none());
    public static final RegistryObject<FilledDragonAmuletItem> MOONLIGHT_DRAGON_AMULET = createDragonAmuletItem("moonlight_dragon_amulet", DragonTypes.MOONLIGHT, none());
    public static final RegistryObject<FilledDragonAmuletItem> SCULK_DRAGON_AMULET = createDragonAmuletItem("sculk_dragon_amulet", DragonTypes.SCULK, none().fireResistant());
    public static final RegistryObject<FilledDragonAmuletItem> SKELETON_DRAGON_AMULET = createDragonAmuletItem("skeleton_dragon_amulet", DragonTypes.SKELETON, none());
    public static final RegistryObject<FilledDragonAmuletItem> WITHER_DRAGON_AMULET = createDragonAmuletItem("wither_dragon_amulet", DragonTypes.WITHER, none());
    //Dragon Essences
    public static final RegistryObject<DragonEssenceItem> FOREST_DRAGON_ESSENCE = createDragonEssenceItem("forest_dragon_essence", DragonTypes.FOREST, none());
    public static final RegistryObject<DragonEssenceItem> FIRE_DRAGON_ESSENCE = createDragonEssenceItem("fire_dragon_essence", DragonTypes.FIRE, none());
    public static final RegistryObject<DragonEssenceItem> ICE_DRAGON_ESSENCE = createDragonEssenceItem("ice_dragon_essence", DragonTypes.ICE, none());
    public static final RegistryObject<DragonEssenceItem> WATER_DRAGON_ESSENCE = createDragonEssenceItem("water_dragon_essence", DragonTypes.WATER, none());
    public static final RegistryObject<DragonEssenceItem> AETHER_DRAGON_ESSENCE = createDragonEssenceItem("aether_dragon_essence", DragonTypes.AETHER, none());
    public static final RegistryObject<DragonEssenceItem> NETHER_DRAGON_ESSENCE = createDragonEssenceItem("nether_dragon_essence", DragonTypes.NETHER, none());
    public static final RegistryObject<DragonEssenceItem> ENDER_DRAGON_ESSENCE = createDragonEssenceItem("ender_dragon_essence", DragonTypes.ENDER, none());
    public static final RegistryObject<DragonEssenceItem> SUNLIGHT_DRAGON_ESSENCE = createDragonEssenceItem("sunlight_dragon_essence", DragonTypes.SUNLIGHT, none());
    public static final RegistryObject<DragonEssenceItem> ENCHANT_DRAGON_ESSENCE = createDragonEssenceItem("enchant_dragon_essence", DragonTypes.ENCHANT, none());
    public static final RegistryObject<DragonEssenceItem> STORM_DRAGON_ESSENCE = createDragonEssenceItem("storm_dragon_essence", DragonTypes.STORM, none());
    public static final RegistryObject<DragonEssenceItem> TERRA_DRAGON_ESSENCE = createDragonEssenceItem("terra_dragon_essence", DragonTypes.TERRA, none());
    public static final RegistryObject<DragonEssenceItem> ZOMBIE_DRAGON_ESSENCE = createDragonEssenceItem("zombie_dragon_essence", DragonTypes.ZOMBIE, none());
    public static final RegistryObject<DragonEssenceItem> MOONLIGHT_DRAGON_ESSENCE = createDragonEssenceItem("moonlight_dragon_essence", DragonTypes.MOONLIGHT, none());
    public static final RegistryObject<DragonEssenceItem> SCULK_DRAGON_ESSENCE = createDragonEssenceItem("sculk_dragon_essence", DragonTypes.SCULK, none().fireResistant());
    public static final RegistryObject<DragonEssenceItem> SKELETON_DRAGON_ESSENCE = createDragonEssenceItem("skeleton_dragon_essence", DragonTypes.SKELETON, none());
    public static final RegistryObject<DragonEssenceItem> WITHER_DRAGON_ESSENCE = createDragonEssenceItem("wither_dragon_essence", DragonTypes.WITHER, none());
    public static final RegistryObject<Item> DRAGON_WHISTLE = ITEMS.register("dragon_whistle", () -> new DragonWhistleItem(item()));

    private static RegistryObject<FilledDragonAmuletItem> createDragonAmuletItem(String name, DragonType type, Properties properties) {
        FilledDragonAmuletItem item = new FilledDragonAmuletItem(type, properties);
        type.bindInstance(FilledDragonAmuletItem.class, item);
        return ITEMS.register(name, () -> item);
    }


    private static RegistryObject<Item> createDragonArmorItem(String name, String texture, int protection, Properties properties) {
        Item item = new DragonArmorItem(new ResourceLocation(DragonMounts.MOD_ID, texture), protection, properties);
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<DragonScaleAxeItem> createDragonScaleAxeItem(String name, DragonScaleTier tier, float attackDamageModifier, float attackSpeedModifier, Properties properties) {
        DragonScaleAxeItem item = new DragonScaleAxeItem(tier, attackDamageModifier, attackSpeedModifier, properties);
        tier.getDragonType().bindInstance(DragonScaleAxeItem.class, item);
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<DragonScaleAxeItem> createDragonScaleAxeItem(String name, DragonScaleTier tier, Properties properties) {
        return createDragonScaleAxeItem(name, tier, 5.0F, -2.8F, properties);
    }

    private static RegistryObject<DragonScaleBowItem> createDragonScaleBowItem(String name, DragonScaleTier tier, Properties properties) {
        DragonScaleBowItem item = new DragonScaleBowItem(tier, properties);
        tier.getDragonType().bindInstance(DragonScaleBowItem.class, item);
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<DragonEssenceItem> createDragonEssenceItem(String name, DragonType type, Properties properties) {
        DragonEssenceItem item = new DragonEssenceItem(type, properties);
        type.bindInstance(DragonEssenceItem.class, item);
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<DragonScaleHoeItem> createDragonScaleHoeItem(String name, DragonScaleTier tier, Properties properties) {
        DragonScaleHoeItem item = new DragonScaleHoeItem(tier, (int) -tier.getAttackDamageBonus(), tier.getAttackDamageBonus() - 3.0F, properties);
        tier.getDragonType().bindInstance(DragonScaleHoeItem.class, item);
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<DragonScalePickaxeItem> createDragonScalePickaxeItem(String name, DragonScaleTier tier, Properties properties) {
        DragonScalePickaxeItem item = new DragonScalePickaxeItem(tier, 1, -2.8F, properties);
        tier.getDragonType().bindInstance(DragonScalePickaxeItem.class, item);
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<DragonScaleArmorItem> createDragonScaleHelmetItem(String name, DragonScaleMaterial material, DragonScaleArmorEffect effect, Properties properties) {
        DragonScaleArmorItem.Helmet item = new DragonScaleArmorItem.Helmet(material, effect, properties);
        material.getDragonType().bindInstance(DragonScaleArmorItem.Helmet.class, item);
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<DragonScaleArmorItem> createDragonScaleChestplateItem(String name, DragonScaleMaterial material, DragonScaleArmorEffect effect, Properties properties) {
        DragonScaleArmorItem.Chestplate item = new DragonScaleArmorItem.Chestplate(material, effect, properties);
        material.getDragonType().bindInstance(DragonScaleArmorItem.Chestplate.class, item);
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<DragonScaleArmorItem> createDragonScaleLeggingsItem(String name, DragonScaleMaterial material, DragonScaleArmorEffect effect, Properties properties) {
        DragonScaleArmorItem.Leggings item = new DragonScaleArmorItem.Leggings(material, effect, properties);
        material.getDragonType().bindInstance(DragonScaleArmorItem.Leggings.class, item);
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<DragonScaleArmorItem> createDragonScaleBootsItem(String name, DragonScaleMaterial material, DragonScaleArmorEffect effect, Properties properties) {
        DragonScaleArmorItem.Boots item = new DragonScaleArmorItem.Boots(material, effect, properties);
        material.getDragonType().bindInstance(DragonScaleArmorItem.Boots.class, item);
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<DragonScalesItem> createDragonScalesItem(String name, DragonType type, Properties properties) {
        DragonScalesItem item = new DragonScalesItem(type, properties);
        type.bindInstance(DragonScalesItem.class, item);
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<DragonScaleShieldItem> createDragonScaleShieldItem(String name, DragonScaleMaterial material, Properties properties) {
        DragonScaleShieldItem item = new DragonScaleShieldItem(material, properties);
        material.getDragonType().bindInstance(DragonScaleShieldItem.class, item);
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<DragonScaleShovelItem> createDragonScaleShovelItem(String name, DragonScaleTier tier, Properties properties) {
        DragonScaleShovelItem item = new DragonScaleShovelItem(tier, 1.5F, -3.0F, properties);
        tier.getDragonType().bindInstance(DragonScaleShovelItem.class, item);
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<DragonScaleSwordItem> createDragonScaleSwordItem(String name, DragonScaleTier tier, Properties properties) {
        DragonScaleSwordItem item = new DragonScaleSwordItem(tier, 3, -2.0F, properties);
        tier.getDragonType().bindInstance(DragonScaleSwordItem.class, item);
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<DragonSpawnEggItem> createDragonSpawnEgg(String name, DragonType type, int backgroundColor, int highlightColor, Properties properties) {
        DragonSpawnEggItem item = new DragonSpawnEggItem(type, backgroundColor, highlightColor, properties);
        type.bindInstance(DragonSpawnEggItem.class, item);
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<Item> createTieredShearsItem(String name, IItemTier tier, Properties properties) {
        Item item = new TieredShearsItem(tier, properties);
        return ITEMS.register(name, () -> item);
    }

    public static void subscribeEvents() {
        MinecraftForge.EVENT_BUS.addListener(DragonScaleArmorEffect::xpBonus);
        MinecraftForge.EVENT_BUS.addListener(DragonScaleArmorEffect::meleeChanneling);
        MinecraftForge.EVENT_BUS.addListener(DragonScaleArmorEffect::riposte);
        MinecraftForge.EVENT_BUS.addListener(HatchableDragonEggBlock::interact);
    }
}