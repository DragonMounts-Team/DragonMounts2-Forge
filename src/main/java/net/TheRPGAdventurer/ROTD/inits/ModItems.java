package net.TheRPGAdventurer.ROTD.inits;

import net.TheRPGAdventurer.ROTD.DragonMounts;
import net.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import net.TheRPGAdventurer.ROTD.objects.items.ItemDragonScales;
import mcp.MethodsReturnNonnullByDefault;
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
    public static final RegistryObject<Item> ForestDragonScales = createDragonScalesItem("forest_dragonscales", EnumItemBreedTypes.FOREST);
    public static final RegistryObject<Item> FireDragonScales = createDragonScalesItem("fire_dragonscales", EnumItemBreedTypes.FIRE);
    public static final RegistryObject<Item> FireDragonScales2 = createDragonScalesItem("fire2_dragonscales", EnumItemBreedTypes.FIRE);
    public static final RegistryObject<Item> IceDragonScales = createDragonScalesItem("ice_dragonscales", EnumItemBreedTypes.ICE);
    public static final RegistryObject<Item> WaterDragonScales = createDragonScalesItem("water_dragonscales", EnumItemBreedTypes.WATER);
    public static final RegistryObject<Item> AetherDragonScales = createDragonScalesItem("aether_dragonscales", EnumItemBreedTypes.AETHER);
    public static final RegistryObject<Item> NetherDragonScales = createDragonScalesItem("nether_dragonscales", EnumItemBreedTypes.NETHER);
    public static final RegistryObject<Item> NetherDragonScales2 = createDragonScalesItem("nether2_dragonscales", EnumItemBreedTypes.NETHER);
    public static final RegistryObject<Item> EnderDragonScales = createDragonScalesItem("ender_dragonscales", EnumItemBreedTypes.END);
    public static final RegistryObject<Item> SunlightDragonScales = createDragonScalesItem("sunlight_dragonscales", EnumItemBreedTypes.SUNLIGHT);
    public static final RegistryObject<Item> SunlightDragonScales2 = createDragonScalesItem("sunlight2_dragonscales", EnumItemBreedTypes.SUNLIGHT);
    public static final RegistryObject<Item> EnchantDragonScales = createDragonScalesItem("enchant_dragonscales", EnumItemBreedTypes.ENCHANT);
    public static final RegistryObject<Item> StormDragonScales = createDragonScalesItem("storm_dragonscales", EnumItemBreedTypes.STORM);
    public static final RegistryObject<Item> StormDragonScales2 = createDragonScalesItem("storm2_dragonscales", EnumItemBreedTypes.STORM);
    public static final RegistryObject<Item> TerraDragonScales = createDragonScalesItem("terra_dragonscales", EnumItemBreedTypes.TERRA);
    public static final RegistryObject<Item> TerraDragonScales2 = createDragonScalesItem("terra2_dragonscales", EnumItemBreedTypes.TERRA2);
    public static final RegistryObject<Item> ZombieDragonScales = createDragonScalesItem("zombie_dragonscales", EnumItemBreedTypes.ZOMBIE);
    public static final RegistryObject<Item> MoonlightDragonScales = createDragonScalesItem("moonlight_dragonscales", EnumItemBreedTypes.MOONLIGHT);

    private static RegistryObject<Item> createDragonScalesItem(String name, EnumItemBreedTypes types) {
        return ITEMS.register(name, () -> new ItemDragonScales(new Item.Properties().tab(MAIN_TAB), types));
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