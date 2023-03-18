package net.dragonmounts3.inits;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.objects.items.*;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.dragonmounts3.DragonMounts.MOD_ID;

@MethodsReturnNonnullByDefault
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DragonMounts.create(ForgeRegistries.ITEMS);

    //Scales Start
    public static final RegistryObject<Item> FOREST_DRAGON_SCALES = createDragonScalesItem("forest_dragonscales", EnumDragonTypes.FOREST);
    public static final RegistryObject<Item> FIRE_DRAGON_SCALES = createDragonScalesItem("fire_dragonscales", EnumDragonTypes.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_SCALES_2 = createDragonScalesItem("fire2_dragonscales", EnumDragonTypes.FIRE);
    public static final RegistryObject<Item> ICE_DRAGON_SCALES = createDragonScalesItem("ice_dragonscales", EnumDragonTypes.ICE);
    public static final RegistryObject<Item> WATER_DRAGON_SCALES = createDragonScalesItem("water_dragonscales", EnumDragonTypes.WATER);
    public static final RegistryObject<Item> AETHER_DRAGON_SCALES = createDragonScalesItem("aether_dragonscales", EnumDragonTypes.AETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_SCALES = createDragonScalesItem("nether_dragonscales", EnumDragonTypes.NETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_SCALES_2 = createDragonScalesItem("nether2_dragonscales", EnumDragonTypes.NETHER);
    public static final RegistryObject<Item> ENDER_DRAGON_SCALES = createDragonScalesItem("ender_dragonscales", EnumDragonTypes.END);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_SCALES = createDragonScalesItem("sunlight_dragonscales", EnumDragonTypes.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_SCALES_2 = createDragonScalesItem("sunlight2_dragonscales", EnumDragonTypes.SUNLIGHT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_SCALES = createDragonScalesItem("enchant_dragonscales", EnumDragonTypes.ENCHANT);
    public static final RegistryObject<Item> STORM_DRAGON_SCALES = createDragonScalesItem("storm_dragonscales", EnumDragonTypes.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_SCALES_2 = createDragonScalesItem("storm2_dragonscales", EnumDragonTypes.STORM);
    public static final RegistryObject<Item> TERRA_DRAGON_SCALES = createDragonScalesItem("terra_dragonscales", EnumDragonTypes.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_SCALES_2 = createDragonScalesItem("terra2_dragonscales", EnumDragonTypes.TERRA2);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_SCALES = createDragonScalesItem("zombie_dragonscales", EnumDragonTypes.ZOMBIE);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_SCALES = createDragonScalesItem("moonlight_dragonscales", EnumDragonTypes.MOONLIGHT);
    //Aether Equipments
    public static final RegistryObject<Item> AETHER_DRAGON_AXE = createDragonAxeItem("aether_dragon_axe", EnumDragonTypes.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_HOE = createDragonHoeItem("aether_dragon_hoe", EnumDragonTypes.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_PICKAXE = createDragonPickaxeItem("aether_dragon_pickaxe", EnumDragonTypes.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_SHOVEL = createDragonShovelItem("aether_dragon_shovel", EnumDragonTypes.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_SWORD = createDragonSwordItem("aether_dragon_sword", EnumDragonTypes.AETHER);
    //Water Equipments
    public static final RegistryObject<Item> WATER_DRAGON_AXE = createDragonAxeItem("water_dragon_axe", EnumDragonTypes.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_HOE = createDragonHoeItem("water_dragon_hoe", EnumDragonTypes.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_PICKAXE = createDragonPickaxeItem("water_dragon_pickaxe", EnumDragonTypes.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_SHOVEL = createDragonShovelItem("water_dragon_shovel", EnumDragonTypes.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_SWORD = createDragonSwordItem("water_dragon_sword", EnumDragonTypes.WATER);
    //Ice Equipments
    public static final RegistryObject<Item> ICE_DRAGON_AXE = createDragonAxeItem("ice_dragon_axe", EnumDragonTypes.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_HOE = createDragonHoeItem("ice_dragon_hoe", EnumDragonTypes.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_PICKAXE = createDragonPickaxeItem("ice_dragon_pickaxe", EnumDragonTypes.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_SHOVEL = createDragonShovelItem("ice_dragon_shovel", EnumDragonTypes.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_SWORD = createDragonSwordItem("ice_dragon_sword", EnumDragonTypes.ICE);
    //Fire Equipments
    public static final RegistryObject<Item> FIRE_DRAGON_AXE = createDragonAxeItem("fire_dragon_axe", EnumDragonTypes.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_HOE = createDragonHoeItem("fire_dragon_hoe", EnumDragonTypes.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_PICKAXE = createDragonPickaxeItem("fire_dragon_pickaxe", EnumDragonTypes.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_SHOVEL = createDragonShovelItem("fire_dragon_shovel", EnumDragonTypes.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_SWORD = createDragonSwordItem("fire_dragon_sword", EnumDragonTypes.FIRE);
    //Forest Equipments
    public static final RegistryObject<Item> FOREST_DRAGON_AXE = createDragonAxeItem("forest_dragon_axe", EnumDragonTypes.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_HOE = createDragonHoeItem("forest_dragon_hoe", EnumDragonTypes.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_PICKAXE = createDragonPickaxeItem("forest_dragon_pickaxe", EnumDragonTypes.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_SHOVEL = createDragonShovelItem("forest_dragon_shovel", EnumDragonTypes.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_SWORD = createDragonSwordItem("forest_dragon_sword", EnumDragonTypes.FOREST);
    //Nether Equipments
    public static final RegistryObject<Item> NETHER_DRAGON_AXE = createDragonAxeItem("nether_dragon_axe", EnumDragonTypes.NETHER, 6.0F, -2.9F);
    public static final RegistryObject<Item> NETHER_DRAGON_HOE = createDragonHoeItem("nether_dragon_hoe", EnumDragonTypes.NETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_PICKAXE = createDragonPickaxeItem("nether_dragon_pickaxe", EnumDragonTypes.NETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_SHOVEL = createDragonShovelItem("nether_dragon_shovel", EnumDragonTypes.NETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_SWORD = createDragonSwordItem("nether_dragon_sword", EnumDragonTypes.NETHER);
    //Ender Equipments
    public static final RegistryObject<Item> ENDER_DRAGON_AXE = createDragonAxeItem("ender_dragon_axe", EnumDragonTypes.END, 3.0F, -2.9F);
    public static final RegistryObject<Item> ENDER_DRAGON_HOE = createDragonHoeItem("ender_dragon_hoe", EnumDragonTypes.END);
    public static final RegistryObject<Item> ENDER_DRAGON_PICKAXE = createDragonPickaxeItem("ender_dragon_pickaxe", EnumDragonTypes.END);
    public static final RegistryObject<Item> ENDER_DRAGON_SHOVEL = createDragonShovelItem("ender_dragon_shovel", EnumDragonTypes.END);
    public static final RegistryObject<Item> ENDER_DRAGON_SWORD = createDragonSwordItem("ender_dragon_sword", EnumDragonTypes.END);
    //Enchant Equipments
    public static final RegistryObject<Item> ENCHANT_DRAGON_AXE = createDragonAxeItem("enchant_dragon_axe", EnumDragonTypes.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_HOE = createDragonHoeItem("enchant_dragon_hoe", EnumDragonTypes.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_PICKAXE = createDragonPickaxeItem("enchant_dragon_pickaxe", EnumDragonTypes.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_SHOVEL = createDragonShovelItem("enchant_dragon_shovel", EnumDragonTypes.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_SWORD = createDragonSwordItem("enchant_dragon_sword", EnumDragonTypes.ENCHANT);
    //Sunlight Equipments
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_AXE = createDragonAxeItem("sunlight_dragon_axe", EnumDragonTypes.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_HOE = createDragonHoeItem("sunlight_dragon_hoe", EnumDragonTypes.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_PICKAXE = createDragonPickaxeItem("sunlight_dragon_pickaxe", EnumDragonTypes.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_SHOVEL = createDragonShovelItem("sunlight_dragon_shovel", EnumDragonTypes.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_SWORD = createDragonSwordItem("sunlight_dragon_sword", EnumDragonTypes.SUNLIGHT);
    //Moonlight Equipments
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_AXE = createDragonAxeItem("moonlight_dragon_axe", EnumDragonTypes.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_HOE = createDragonHoeItem("moonlight_dragon_hoe", EnumDragonTypes.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_PICKAXE = createDragonPickaxeItem("moonlight_dragon_pickaxe", EnumDragonTypes.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_SHOVEL = createDragonShovelItem("moonlight_dragon_shovel", EnumDragonTypes.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_SWORD = createDragonSwordItem("moonlight_dragon_sword", EnumDragonTypes.MOONLIGHT);
    //Storm Equipments
    public static final RegistryObject<Item> STORM_DRAGON_AXE = createDragonAxeItem("storm_dragon_axe", EnumDragonTypes.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_HOE = createDragonHoeItem("storm_dragon_hoe", EnumDragonTypes.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_PICKAXE = createDragonPickaxeItem("storm_dragon_pickaxe", EnumDragonTypes.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_SHOVEL = createDragonShovelItem("storm_dragon_shovel", EnumDragonTypes.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_SWORD = createDragonSwordItem("storm_dragon_sword", EnumDragonTypes.STORM);
    //Terra Equipments
    public static final RegistryObject<Item> TERRA_DRAGON_AXE = createDragonAxeItem("terra_dragon_axe", EnumDragonTypes.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_HOE = createDragonHoeItem("terra_dragon_hoe", EnumDragonTypes.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_PICKAXE = createDragonPickaxeItem("terra_dragon_pickaxe", EnumDragonTypes.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_SHOVEL = createDragonShovelItem("terra_dragon_shovel", EnumDragonTypes.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_SWORD = createDragonSwordItem("terra_dragon_sword", EnumDragonTypes.TERRA);
    //Zombie Equipments
    public static final RegistryObject<Item> ZOMBIE_DRAGON_AXE = createDragonAxeItem("zombie_dragon_axe", EnumDragonTypes.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_HOE = createDragonHoeItem("zombie_dragon_hoe", EnumDragonTypes.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_PICKAXE = createDragonPickaxeItem("zombie_dragon_pickaxe", EnumDragonTypes.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_SHOVEL = createDragonShovelItem("zombie_dragon_shovel", EnumDragonTypes.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_SWORD = createDragonSwordItem("zombie_dragon_sword", EnumDragonTypes.ZOMBIE);

    private static RegistryObject<Item> createDragonAxeItem(String name, EnumDragonTypes type, float attackDamageModifier, float attackSpeedModifier) {
        return ITEMS.register(name, () -> new ItemDragonAxe(type, attackDamageModifier, attackSpeedModifier, new Item.Properties().tab(EQUIPMENT_TAB)));
    }
    private static RegistryObject<Item> createDragonAxeItem(String name, EnumDragonTypes type) {
        return ITEMS.register(name, () -> new ItemDragonAxe(type, 5.0F, -2.8F/*Minecraft: -3.0F*/, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonHoeItem(String name, EnumDragonTypes type) {
        return ITEMS.register(name, () -> new ItemDragonHoe(type, (int) -type.getAttackDamageBonus(), type.getAttackDamageBonus() - 3.0F, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonPickaxeItem(String name, EnumDragonTypes type) {
        return ITEMS.register(name, () -> new ItemDragonPickaxe(type, 1, -2.8F, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonScalesItem(String name, EnumDragonTypes type) {
        return ITEMS.register(name, () -> new ItemDragonScales(new Item.Properties().tab(ITEM_TAB), type));
    }

    private static RegistryObject<Item> createDragonShovelItem(String name, EnumDragonTypes type) {
        return ITEMS.register(name, () -> new ItemDragonShovel(type, 1.5F, -3.0F, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonSwordItem(String name, EnumDragonTypes type) {
        return ITEMS.register(name, () -> new ItemDragonSword(type, 3, -2.0F/*Minecraft: -2.4F*/, new Item.Properties().tab(EQUIPMENT_TAB)));
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

}