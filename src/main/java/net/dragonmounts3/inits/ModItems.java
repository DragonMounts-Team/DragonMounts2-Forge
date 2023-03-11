package net.dragonmounts3.inits;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.objects.items.EnumDragonTypes;
import net.dragonmounts3.objects.items.ItemDragonScales;
import mcp.MethodsReturnNonnullByDefault;
import net.dragonmounts3.objects.items.ItemDragonSword;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@MethodsReturnNonnullByDefault
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DragonMounts.create(ForgeRegistries.ITEMS);

    //Scales Start
    public static final RegistryObject<Item> ForestDragonScales = createDragonScalesItem("forest_dragonscales", EnumDragonTypes.FOREST);
    public static final RegistryObject<Item> FireDragonScales = createDragonScalesItem("fire_dragonscales", EnumDragonTypes.FIRE);
    public static final RegistryObject<Item> FireDragonScales2 = createDragonScalesItem("fire2_dragonscales", EnumDragonTypes.FIRE);
    public static final RegistryObject<Item> IceDragonScales = createDragonScalesItem("ice_dragonscales", EnumDragonTypes.ICE);
    public static final RegistryObject<Item> WaterDragonScales = createDragonScalesItem("water_dragonscales", EnumDragonTypes.WATER);
    public static final RegistryObject<Item> AetherDragonScales = createDragonScalesItem("aether_dragonscales", EnumDragonTypes.AETHER);
    public static final RegistryObject<Item> NetherDragonScales = createDragonScalesItem("nether_dragonscales", EnumDragonTypes.NETHER);
    public static final RegistryObject<Item> NetherDragonScales2 = createDragonScalesItem("nether2_dragonscales", EnumDragonTypes.NETHER);
    public static final RegistryObject<Item> EnderDragonScales = createDragonScalesItem("ender_dragonscales", EnumDragonTypes.END);
    public static final RegistryObject<Item> SunlightDragonScales = createDragonScalesItem("sunlight_dragonscales", EnumDragonTypes.SUNLIGHT);
    public static final RegistryObject<Item> SunlightDragonScales2 = createDragonScalesItem("sunlight2_dragonscales", EnumDragonTypes.SUNLIGHT);
    public static final RegistryObject<Item> EnchantDragonScales = createDragonScalesItem("enchant_dragonscales", EnumDragonTypes.ENCHANT);
    public static final RegistryObject<Item> StormDragonScales = createDragonScalesItem("storm_dragonscales", EnumDragonTypes.STORM);
    public static final RegistryObject<Item> StormDragonScales2 = createDragonScalesItem("storm2_dragonscales", EnumDragonTypes.STORM);
    public static final RegistryObject<Item> TerraDragonScales = createDragonScalesItem("terra_dragonscales", EnumDragonTypes.TERRA);
    public static final RegistryObject<Item> TerraDragonScales2 = createDragonScalesItem("terra2_dragonscales", EnumDragonTypes.TERRA2);
    public static final RegistryObject<Item> ZombieDragonScales = createDragonScalesItem("zombie_dragonscales", EnumDragonTypes.ZOMBIE);
    public static final RegistryObject<Item> MoonlightDragonScales = createDragonScalesItem("moonlight_dragonscales", EnumDragonTypes.MOONLIGHT);
    public static final RegistryObject<Item> enderDragonSword = createDragonSwordItem("ender_dragon_sword", EnumDragonTypes.END);

    private static RegistryObject<Item> createDragonSwordItem(String name, EnumDragonTypes type) {
        return ITEMS.register(name, () -> new ItemDragonSword(type, 3, -2F/*Minecraft Sword: -2.4F*/, new Item.Properties().tab(ARMORY_TAB)));
    }

    private static RegistryObject<Item> createDragonScalesItem(String name, EnumDragonTypes type) {
        return ITEMS.register(name, () -> new ItemDragonScales(new Item.Properties().tab(MAIN_TAB), type));
    }

    public static final ItemGroup MAIN_TAB = new ItemGroup("main_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.BARRIER);
        }
    };

    public static final ItemGroup ARMORY_TAB = new ItemGroup("armory_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.BARRIER);
        }
    };

}