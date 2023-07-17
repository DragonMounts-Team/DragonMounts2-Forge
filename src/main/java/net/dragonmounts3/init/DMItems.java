package net.dragonmounts3.init;

import mcp.MethodsReturnNonnullByDefault;
import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.api.*;
import net.dragonmounts3.entity.carriage.CarriageType;
import net.dragonmounts3.item.*;
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

import static net.dragonmounts3.init.DMItemGroups.*;

@MethodsReturnNonnullByDefault
public class DMItems {

    public static final DeferredRegister<Item> ITEMS = DragonMounts.create(ForgeRegistries.ITEMS);
    public static final TypifiedRegistry<CarriageType, CarriageItem> CARRIAGE = new TypifiedRegistry<>();
    public static final DragonTypifiedRegistry<DragonEssenceItem> DRAGON_ESSENCE = new DragonTypifiedRegistry<>();
    public static final DragonTypifiedRegistry<DragonScalesItem> DRAGON_SCALES = new DragonTypifiedRegistry<>();
    public static final DragonTypifiedRegistry<DragonScaleAxeItem> DRAGON_SCALE_AXE = new DragonTypifiedRegistry<>();
    public static final DragonTypifiedRegistry<DragonScaleArmorItem> DRAGON_SCALE_BOOTS = new DragonTypifiedRegistry<>();
    public static final DragonTypifiedRegistry<DragonScaleBowItem> DRAGON_SCALE_BOW = new DragonTypifiedRegistry<>();
    public static final DragonTypifiedRegistry<DragonScaleArmorItem> DRAGON_SCALE_CHESTPLATE = new DragonTypifiedRegistry<>();
    public static final DragonTypifiedRegistry<DragonScaleArmorItem> DRAGON_SCALE_HELMET = new DragonTypifiedRegistry<>();
    public static final DragonTypifiedRegistry<DragonScaleHoeItem> DRAGON_SCALE_HOE = new DragonTypifiedRegistry<>();
    public static final DragonTypifiedRegistry<DragonScaleArmorItem> DRAGON_SCALE_LEGGINGS = new DragonTypifiedRegistry<>();
    public static final DragonTypifiedRegistry<DragonScalePickaxeItem> DRAGON_SCALE_PICKAXE = new DragonTypifiedRegistry<>();
    public static final DragonTypifiedRegistry<DragonScaleShieldItem> DRAGON_SCALE_SHIELD = new DragonTypifiedRegistry<>();
    public static final DragonTypifiedRegistry<DragonScaleShovelItem> DRAGON_SCALE_SHOVEL = new DragonTypifiedRegistry<>();
    public static final DragonTypifiedRegistry<DragonScaleSwordItem> DRAGON_SCALE_SWORD = new DragonTypifiedRegistry<>();
    public static final DragonTypifiedRegistry<DragonSpawnEggItem> DRAGON_SPAWN_EGG = new DragonTypifiedRegistry<>();
    public static final DragonTypifiedRegistry<FilledDragonAmuletItem> FILLED_DRAGON_AMULET = new DragonTypifiedRegistry<>();
    //Scales Start
    public static final RegistryObject<DragonScalesItem> FOREST_DRAGON_SCALES = createDragonScalesItem("forest_dragon_scales", DragonType.FOREST, item());
    public static final RegistryObject<DragonScalesItem> FIRE_DRAGON_SCALES = createDragonScalesItem("fire_dragon_scales", DragonType.FIRE, item());
    public static final RegistryObject<DragonScalesItem> ICE_DRAGON_SCALES = createDragonScalesItem("ice_dragon_scales", DragonType.ICE, item());
    public static final RegistryObject<DragonScalesItem> WATER_DRAGON_SCALES = createDragonScalesItem("water_dragon_scales", DragonType.WATER, item());
    public static final RegistryObject<DragonScalesItem> AETHER_DRAGON_SCALES = createDragonScalesItem("aether_dragon_scales", DragonType.AETHER, item());
    public static final RegistryObject<DragonScalesItem> NETHER_DRAGON_SCALES = createDragonScalesItem("nether_dragon_scales", DragonType.NETHER, item());
    public static final RegistryObject<DragonScalesItem> ENDER_DRAGON_SCALES = createDragonScalesItem("ender_dragon_scales", DragonType.ENDER, item());
    public static final RegistryObject<DragonScalesItem> SUNLIGHT_DRAGON_SCALES = createDragonScalesItem("sunlight_dragon_scales", DragonType.SUNLIGHT, item());
    public static final RegistryObject<DragonScalesItem> ENCHANT_DRAGON_SCALES = createDragonScalesItem("enchant_dragon_scales", DragonType.ENCHANT, item());
    public static final RegistryObject<DragonScalesItem> STORM_DRAGON_SCALES = createDragonScalesItem("storm_dragon_scales", DragonType.STORM, item());
    public static final RegistryObject<DragonScalesItem> TERRA_DRAGON_SCALES = createDragonScalesItem("terra_dragon_scales", DragonType.TERRA, item());
    public static final RegistryObject<DragonScalesItem> ZOMBIE_DRAGON_SCALES = createDragonScalesItem("zombie_dragon_scales", DragonType.ZOMBIE, item());
    public static final RegistryObject<DragonScalesItem> MOONLIGHT_DRAGON_SCALES = createDragonScalesItem("moonlight_dragon_scales", DragonType.MOONLIGHT, item());
    public static final RegistryObject<DragonScalesItem> SCULK_DRAGON_SCALES = createDragonScalesItem("sculk_dragon_scales", DragonType.SCULK, item().fireResistant());
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
    public static final RegistryObject<DragonSpawnEggItem> AETHER_DRAGON_SPAWN_EGG = createDragonSpawnEgg("aether_dragon_spawn_egg", DragonType.AETHER, 0x05C3D2, 0x281EE8, item());
    public static final RegistryObject<DragonSpawnEggItem> ENCHANT_DRAGON_SPAWN_EGG = createDragonSpawnEgg("enchant_dragon_spawn_egg", DragonType.ENCHANT, 0xCC0DE0, 0xFFFFFF, item());
    public static final RegistryObject<DragonSpawnEggItem> ENDER_DRAGON_SPAWN_EGG = createDragonSpawnEgg("ender_dragon_spawn_egg", DragonType.ENDER, 0x08080C, 0x79087E, item());
    public static final RegistryObject<DragonSpawnEggItem> FIRE_DRAGON_SPAWN_EGG = createDragonSpawnEgg("fire_dragon_spawn_egg", DragonType.FIRE, 0x620508, 0xF7A502, item());
    public static final RegistryObject<DragonSpawnEggItem> FOREST_DRAGON_SPAWN_EGG = createDragonSpawnEgg("forest_dragon_spawn_egg", DragonType.FOREST, 0x0C9613, 0x036408, item());
    public static final RegistryObject<DragonSpawnEggItem> ICE_DRAGON_SPAWN_EGG = createDragonSpawnEgg("ice_dragon_spawn_egg", DragonType.ICE, 0xFFFFFF, 0x02D0EE, item());
    public static final RegistryObject<DragonSpawnEggItem> MOONLIGHT_DRAGON_SPAWN_EGG = createDragonSpawnEgg("moonlight_dragon_spawn_egg", DragonType.MOONLIGHT, 0x00164E, 0xFEFEFE, item());
    public static final RegistryObject<DragonSpawnEggItem> NETHER_DRAGON_SPAWN_EGG = createDragonSpawnEgg("nether_dragon_spawn_egg", DragonType.NETHER, 0x632F1B, 0xE7A621, item());
    public static final RegistryObject<DragonSpawnEggItem> SCULK_DRAGON_SPAWN_EGG = createDragonSpawnEgg("sculk_dragon_spawn_egg", DragonType.SCULK, 0x0F4649, 0x39D6E0, item());
    public static final RegistryObject<DragonSpawnEggItem> SKELETON_DRAGON_SPAWN_EGG = createDragonSpawnEgg("skeleton_dragon_spawn_egg", DragonType.SKELETON, 0xFFFFFF, 0x474F51, item());
    public static final RegistryObject<DragonSpawnEggItem> STORM_DRAGON_SPAWN_EGG = createDragonSpawnEgg("storm_dragon_spawn_egg", DragonType.STORM, 0x010B0F, 0x0FA8CE, item());
    public static final RegistryObject<DragonSpawnEggItem> SUNLIGHT_DRAGON_SPAWN_EGG = createDragonSpawnEgg("sunlight_dragon_spawn_egg", DragonType.SUNLIGHT, 0xF4950D, 0xF4E10D, item());
    public static final RegistryObject<DragonSpawnEggItem> TERRA_DRAGON_SPAWN_EGG = createDragonSpawnEgg("terra_dragon_spawn_egg", DragonType.TERRA, 0x674517, 0xE6B10D, item());
    public static final RegistryObject<DragonSpawnEggItem> WATER_DRAGON_SPAWN_EGG = createDragonSpawnEgg("water_dragon_spawn_egg", DragonType.WATER, 0x546FAD, 0x2B427E, item());
    public static final RegistryObject<DragonSpawnEggItem> WITHER_DRAGON_SPAWN_EGG = createDragonSpawnEgg("wither_dragon_spawn_egg", DragonType.WITHER, 0x8A9999, 0x474F51, item());
    public static final RegistryObject<DragonSpawnEggItem> ZOMBIE_DRAGON_SPAWN_EGG = createDragonSpawnEgg("zombie_dragon_spawn_egg", DragonType.ZOMBIE, 0x66664B, 0xB6D035, item());
    //Shears
    public static final RegistryObject<Item> DIAMOND_SHEARS = createTieredShearsItem("diamond_shears", ItemTier.DIAMOND, item());
    public static final RegistryObject<Item> NETHERITE_SHEARS = createTieredShearsItem("netherite_shears", ItemTier.NETHERITE, item().fireResistant());
    //Carriages
    public static final RegistryObject<CarriageItem> ACACIA_CARRIAGE = createCarriageItem("acacia_carriage", CarriageType.ACACIA, item());
    public static final RegistryObject<CarriageItem> BIRCH_CARRIAGE = createCarriageItem("birch_carriage", CarriageType.BIRCH, item());
    public static final RegistryObject<CarriageItem> DARK_OAK_CARRIAGE = createCarriageItem("dark_oak_carriage", CarriageType.DARK_OAK, item());
    public static final RegistryObject<CarriageItem> JUNGLE_CARRIAGE = createCarriageItem("jungle_carriage", CarriageType.JUNGLE, item());
    public static final RegistryObject<CarriageItem> OAK_CARRIAGE = createCarriageItem("oak_carriage", CarriageType.OAK, item());
    public static final RegistryObject<CarriageItem> SPRUCE_CARRIAGE = createCarriageItem("spruce_carriage", CarriageType.SPRUCE, item());
    //Dragon Amulets
    public static final RegistryObject<DragonAmuletItem> DRAGON_AMULET = ITEMS.register("dragon_amulet", () -> new DragonAmuletItem(item()));
    public static final RegistryObject<FilledDragonAmuletItem> FOREST_DRAGON_AMULET = createDragonAmuletItem("forest_dragon_amulet", DragonType.FOREST, none());
    public static final RegistryObject<FilledDragonAmuletItem> FIRE_DRAGON_AMULET = createDragonAmuletItem("fire_dragon_amulet", DragonType.FIRE, none());
    public static final RegistryObject<FilledDragonAmuletItem> ICE_DRAGON_AMULET = createDragonAmuletItem("ice_dragon_amulet", DragonType.ICE, none());
    public static final RegistryObject<FilledDragonAmuletItem> WATER_DRAGON_AMULET = createDragonAmuletItem("water_dragon_amulet", DragonType.WATER, none());
    public static final RegistryObject<FilledDragonAmuletItem> AETHER_DRAGON_AMULET = createDragonAmuletItem("aether_dragon_amulet", DragonType.AETHER, none());
    public static final RegistryObject<FilledDragonAmuletItem> NETHER_DRAGON_AMULET = createDragonAmuletItem("nether_dragon_amulet", DragonType.NETHER, none());
    public static final RegistryObject<FilledDragonAmuletItem> ENDER_DRAGON_AMULET = createDragonAmuletItem("ender_dragon_amulet", DragonType.ENDER, none());
    public static final RegistryObject<FilledDragonAmuletItem> SUNLIGHT_DRAGON_AMULET = createDragonAmuletItem("sunlight_dragon_amulet", DragonType.SUNLIGHT, none());
    public static final RegistryObject<FilledDragonAmuletItem> ENCHANT_DRAGON_AMULET = createDragonAmuletItem("enchant_dragon_amulet", DragonType.ENCHANT, none());
    public static final RegistryObject<FilledDragonAmuletItem> STORM_DRAGON_AMULET = createDragonAmuletItem("storm_dragon_amulet", DragonType.STORM, none());
    public static final RegistryObject<FilledDragonAmuletItem> TERRA_DRAGON_AMULET = createDragonAmuletItem("terra_dragon_amulet", DragonType.TERRA, none());
    public static final RegistryObject<FilledDragonAmuletItem> ZOMBIE_DRAGON_AMULET = createDragonAmuletItem("zombie_dragon_amulet", DragonType.ZOMBIE, none());
    public static final RegistryObject<FilledDragonAmuletItem> MOONLIGHT_DRAGON_AMULET = createDragonAmuletItem("moonlight_dragon_amulet", DragonType.MOONLIGHT, none());
    public static final RegistryObject<FilledDragonAmuletItem> SCULK_DRAGON_AMULET = createDragonAmuletItem("sculk_dragon_amulet", DragonType.SCULK, none().fireResistant());
    public static final RegistryObject<FilledDragonAmuletItem> SKELETON_DRAGON_AMULET = createDragonAmuletItem("skeleton_dragon_amulet", DragonType.SKELETON, none());
    public static final RegistryObject<FilledDragonAmuletItem> WITHER_DRAGON_AMULET = createDragonAmuletItem("wither_dragon_amulet", DragonType.WITHER, none());
    //Dragon Essences
    public static final RegistryObject<DragonEssenceItem> FOREST_DRAGON_ESSENCE = createDragonEssenceItem("forest_dragon_essence", DragonType.FOREST, none());
    public static final RegistryObject<DragonEssenceItem> FIRE_DRAGON_ESSENCE = createDragonEssenceItem("fire_dragon_essence", DragonType.FIRE, none());
    public static final RegistryObject<DragonEssenceItem> ICE_DRAGON_ESSENCE = createDragonEssenceItem("ice_dragon_essence", DragonType.ICE, none());
    public static final RegistryObject<DragonEssenceItem> WATER_DRAGON_ESSENCE = createDragonEssenceItem("water_dragon_essence", DragonType.WATER, none());
    public static final RegistryObject<DragonEssenceItem> AETHER_DRAGON_ESSENCE = createDragonEssenceItem("aether_dragon_essence", DragonType.AETHER, none());
    public static final RegistryObject<DragonEssenceItem> NETHER_DRAGON_ESSENCE = createDragonEssenceItem("nether_dragon_essence", DragonType.NETHER, none());
    public static final RegistryObject<DragonEssenceItem> ENDER_DRAGON_ESSENCE = createDragonEssenceItem("ender_dragon_essence", DragonType.ENDER, none());
    public static final RegistryObject<DragonEssenceItem> SUNLIGHT_DRAGON_ESSENCE = createDragonEssenceItem("sunlight_dragon_essence", DragonType.SUNLIGHT, none());
    public static final RegistryObject<DragonEssenceItem> ENCHANT_DRAGON_ESSENCE = createDragonEssenceItem("enchant_dragon_essence", DragonType.ENCHANT, none());
    public static final RegistryObject<DragonEssenceItem> STORM_DRAGON_ESSENCE = createDragonEssenceItem("storm_dragon_essence", DragonType.STORM, none());
    public static final RegistryObject<DragonEssenceItem> TERRA_DRAGON_ESSENCE = createDragonEssenceItem("terra_dragon_essence", DragonType.TERRA, none());
    public static final RegistryObject<DragonEssenceItem> ZOMBIE_DRAGON_ESSENCE = createDragonEssenceItem("zombie_dragon_essence", DragonType.ZOMBIE, none());
    public static final RegistryObject<DragonEssenceItem> MOONLIGHT_DRAGON_ESSENCE = createDragonEssenceItem("moonlight_dragon_essence", DragonType.MOONLIGHT, none());
    public static final RegistryObject<DragonEssenceItem> SCULK_DRAGON_ESSENCE = createDragonEssenceItem("sculk_dragon_essence", DragonType.SCULK, none().fireResistant());
    public static final RegistryObject<DragonEssenceItem> SKELETON_DRAGON_ESSENCE = createDragonEssenceItem("skeleton_dragon_essence", DragonType.SKELETON, none());
    public static final RegistryObject<DragonEssenceItem> WITHER_DRAGON_ESSENCE = createDragonEssenceItem("wither_dragon_essence", DragonType.WITHER, none());
    public static final RegistryObject<Item> DRAGON_WHISTLE = ITEMS.register("dragon_whistle", () -> new DragonWhistleItem(item()));

    private static RegistryObject<CarriageItem> createCarriageItem(String name, CarriageType type, Properties properties) {
        return CARRIAGE.register(ITEMS, name, type, new CarriageItem(type, properties));
    }

    private static RegistryObject<FilledDragonAmuletItem> createDragonAmuletItem(String name, DragonType type, Properties properties) {
        return FILLED_DRAGON_AMULET.register(ITEMS, name, new FilledDragonAmuletItem(type, properties));
    }

    private static RegistryObject<Item> createDragonArmorItem(String name, String texture, int protection, Properties properties) {
        return ITEMS.register(name, () -> new DragonArmorItem(DragonMounts.prefix(texture), protection, properties));
    }

    private static RegistryObject<DragonScaleAxeItem> createDragonScaleAxeItem(String name, DragonScaleTier tier, float attackDamageModifier, float attackSpeedModifier, Properties properties) {
        return DRAGON_SCALE_AXE.register(ITEMS, name, new DragonScaleAxeItem(tier, attackDamageModifier, attackSpeedModifier, properties));
    }

    private static RegistryObject<DragonScaleAxeItem> createDragonScaleAxeItem(String name, DragonScaleTier tier, Properties properties) {
        return DRAGON_SCALE_AXE.register(ITEMS, name, new DragonScaleAxeItem(tier, 5.0F, -2.8F/*Minecraft: -3.0F*/, properties));
    }

    private static RegistryObject<DragonScaleBowItem> createDragonScaleBowItem(String name, DragonScaleTier tier, Properties properties) {
        return DRAGON_SCALE_BOW.register(ITEMS, name, new DragonScaleBowItem(tier, properties));
    }

    private static RegistryObject<DragonEssenceItem> createDragonEssenceItem(String name, DragonType type, Properties properties) {
        return DRAGON_ESSENCE.register(ITEMS, name, new DragonEssenceItem(type, properties));
    }

    private static RegistryObject<DragonScaleHoeItem> createDragonScaleHoeItem(String name, DragonScaleTier tier, Properties properties) {
        return DRAGON_SCALE_HOE.register(ITEMS, name, new DragonScaleHoeItem(tier, (int) -tier.getAttackDamageBonus(), tier.getAttackDamageBonus() - 3.0F, properties));
    }

    private static RegistryObject<DragonScalePickaxeItem> createDragonScalePickaxeItem(String name, DragonScaleTier tier, Properties properties) {
        return DRAGON_SCALE_PICKAXE.register(ITEMS, name, new DragonScalePickaxeItem(tier, 1, -2.8F, properties));
    }

    private static RegistryObject<DragonScaleArmorItem> createDragonScaleHelmetItem(String name, DragonScaleMaterial material, DragonScaleArmorEffect effect, Properties properties) {
        return DRAGON_SCALE_HELMET.register(ITEMS, name, new DragonScaleArmorItem(material, EquipmentSlotType.HEAD, effect, properties));
    }

    private static RegistryObject<DragonScaleArmorItem> createDragonScaleChestplateItem(String name, DragonScaleMaterial material, DragonScaleArmorEffect effect, Properties properties) {
        return DRAGON_SCALE_CHESTPLATE.register(ITEMS, name, new DragonScaleArmorItem(material, EquipmentSlotType.CHEST, effect, properties));
    }

    private static RegistryObject<DragonScaleArmorItem> createDragonScaleLeggingsItem(String name, DragonScaleMaterial material, DragonScaleArmorEffect effect, Properties properties) {
        return DRAGON_SCALE_LEGGINGS.register(ITEMS, name, new DragonScaleArmorItem(material, EquipmentSlotType.LEGS, effect, properties));
    }

    private static RegistryObject<DragonScaleArmorItem> createDragonScaleBootsItem(String name, DragonScaleMaterial material, DragonScaleArmorEffect effect, Properties properties) {
        return DRAGON_SCALE_BOOTS.register(ITEMS, name, new DragonScaleArmorItem(material, EquipmentSlotType.FEET, effect, properties));
    }

    private static RegistryObject<DragonScalesItem> createDragonScalesItem(String name, DragonType type, Properties properties) {
        return DRAGON_SCALES.register(ITEMS, name, new DragonScalesItem(type, properties));
    }

    private static RegistryObject<DragonScaleShieldItem> createDragonScaleShieldItem(String name, DragonScaleMaterial material, Properties properties) {
        return DRAGON_SCALE_SHIELD.register(ITEMS, name, new DragonScaleShieldItem(material, properties));
    }

    private static RegistryObject<DragonScaleShovelItem> createDragonScaleShovelItem(String name, DragonScaleTier tier, Properties properties) {
        return DRAGON_SCALE_SHOVEL.register(ITEMS, name, new DragonScaleShovelItem(tier, 1.5F, -3.0F, properties));
    }

    private static RegistryObject<DragonScaleSwordItem> createDragonScaleSwordItem(String name, DragonScaleTier tier, Properties properties) {
        return DRAGON_SCALE_SWORD.register(ITEMS, name, new DragonScaleSwordItem(tier, 3, -2.0F/*Minecraft: -2.4F*/, properties));
    }

    private static RegistryObject<DragonSpawnEggItem> createDragonSpawnEgg(String name, DragonType type, int backgroundColor, int highlightColor, Properties properties) {
        return DRAGON_SPAWN_EGG.register(ITEMS, name, new DragonSpawnEggItem(type, backgroundColor, highlightColor, properties));
    }

    private static RegistryObject<Item> createTieredShearsItem(String name, IItemTier tier, Properties properties) {
        return ITEMS.register(name, () -> new TieredShearsItem(tier, properties));
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
        for (DragonScaleBowItem item : DRAGON_SCALE_BOW) {
            ItemModelsProperties.register(item, new ResourceLocation("pull"), DMItems::getPullItemProperty);
            ItemModelsProperties.register(item, new ResourceLocation("pulling"), DMItems::getPullingItemProperty);
        }
        for (DragonScaleShieldItem item : DRAGON_SCALE_SHIELD) {
            ItemModelsProperties.register(item, new ResourceLocation("blocking"), DMItems::getBlockingItemProperty);
        }
    }
}