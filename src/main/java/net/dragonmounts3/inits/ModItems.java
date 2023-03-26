package net.dragonmounts3.inits;

import mcp.MethodsReturnNonnullByDefault;
import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.objects.EnumDragonType;
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
    public static final RegistryObject<Item> FOREST_DRAGON_SCALES = createDragonScalesItem("forest_dragonscales", EnumDragonType.FOREST);
    public static final RegistryObject<Item> FIRE_DRAGON_SCALES = createDragonScalesItem("fire_dragonscales", EnumDragonType.FIRE);
    public static final RegistryObject<Item> ICE_DRAGON_SCALES = createDragonScalesItem("ice_dragonscales", EnumDragonType.ICE);
    public static final RegistryObject<Item> WATER_DRAGON_SCALES = createDragonScalesItem("water_dragonscales", EnumDragonType.WATER);
    public static final RegistryObject<Item> AETHER_DRAGON_SCALES = createDragonScalesItem("aether_dragonscales", EnumDragonType.AETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_SCALES = createDragonScalesItem("nether_dragonscales", EnumDragonType.NETHER);
    public static final RegistryObject<Item> ENDER_DRAGON_SCALES = createDragonScalesItem("ender_dragonscales", EnumDragonType.ENDER);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_SCALES = createDragonScalesItem("sunlight_dragonscales", EnumDragonType.SUNLIGHT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_SCALES = createDragonScalesItem("enchant_dragonscales", EnumDragonType.ENCHANT);
    public static final RegistryObject<Item> STORM_DRAGON_SCALES = createDragonScalesItem("storm_dragonscales", EnumDragonType.STORM);
    public static final RegistryObject<Item> TERRA_DRAGON_SCALES = createDragonScalesItem("terra_dragonscales", EnumDragonType.TERRA);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_SCALES = createDragonScalesItem("zombie_dragonscales", EnumDragonType.ZOMBIE);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_SCALES = createDragonScalesItem("moonlight_dragonscales", EnumDragonType.MOONLIGHT);
    //Aether Equipments
    public static final RegistryObject<Item> AETHER_DRAGON_AXE = createDragonAxeItem("aether_dragon_axe", EnumDragonType.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_BOOTS = createDragonArmorItem("aether_dragonscale_boots", EnumDragonType.AETHER, EquipmentSlotType.FEET);
    public static final RegistryObject<Item> AETHER_DRAGON_BOW = createDragonBowItem("aether_dragon_bow", EnumDragonType.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_CHESTPLATE = createDragonArmorItem("aether_dragonscale_chestplate", EnumDragonType.AETHER, EquipmentSlotType.CHEST);
    public static final RegistryObject<Item> AETHER_DRAGON_HELMET = createDragonArmorItem("aether_dragonscale_helmet", EnumDragonType.AETHER, EquipmentSlotType.HEAD);
    public static final RegistryObject<Item> AETHER_DRAGON_HOE = createDragonHoeItem("aether_dragon_hoe", EnumDragonType.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_LEGGINGS = createDragonArmorItem("aether_dragonscale_leggings", EnumDragonType.AETHER, EquipmentSlotType.LEGS);
    public static final RegistryObject<Item> AETHER_DRAGON_PICKAXE = createDragonPickaxeItem("aether_dragon_pickaxe", EnumDragonType.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_SHIELD = createDragonShieldItem("aether_dragon_shield", EnumDragonType.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_SHOVEL = createDragonShovelItem("aether_dragon_shovel", EnumDragonType.AETHER);
    public static final RegistryObject<Item> AETHER_DRAGON_SWORD = createDragonSwordItem("aether_dragon_sword", EnumDragonType.AETHER);
    //Water Equipments
    public static final RegistryObject<Item> WATER_DRAGON_AXE = createDragonAxeItem("water_dragon_axe", EnumDragonType.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_BOW = createDragonBowItem("water_dragon_bow", EnumDragonType.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_HOE = createDragonHoeItem("water_dragon_hoe", EnumDragonType.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_PICKAXE = createDragonPickaxeItem("water_dragon_pickaxe", EnumDragonType.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_SHIELD = createDragonShieldItem("water_dragon_shield", EnumDragonType.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_SHOVEL = createDragonShovelItem("water_dragon_shovel", EnumDragonType.WATER);
    public static final RegistryObject<Item> WATER_DRAGON_SWORD = createDragonSwordItem("water_dragon_sword", EnumDragonType.WATER);
    //Ice Equipments
    public static final RegistryObject<Item> ICE_DRAGON_AXE = createDragonAxeItem("ice_dragon_axe", EnumDragonType.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_BOW = createDragonBowItem("ice_dragon_bow", EnumDragonType.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_HOE = createDragonHoeItem("ice_dragon_hoe", EnumDragonType.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_PICKAXE = createDragonPickaxeItem("ice_dragon_pickaxe", EnumDragonType.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_SHIELD = createDragonShieldItem("ice_dragon_shield", EnumDragonType.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_SHOVEL = createDragonShovelItem("ice_dragon_shovel", EnumDragonType.ICE);
    public static final RegistryObject<Item> ICE_DRAGON_SWORD = createDragonSwordItem("ice_dragon_sword", EnumDragonType.ICE);
    //Fire Equipments
    public static final RegistryObject<Item> FIRE_DRAGON_AXE = createDragonAxeItem("fire_dragon_axe", EnumDragonType.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_BOW = createDragonBowItem("fire_dragon_bow", EnumDragonType.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_HOE = createDragonHoeItem("fire_dragon_hoe", EnumDragonType.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_PICKAXE = createDragonPickaxeItem("fire_dragon_pickaxe", EnumDragonType.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_SHIELD = createDragonShieldItem("fire_dragon_shield", EnumDragonType.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_SHOVEL = createDragonShovelItem("fire_dragon_shovel", EnumDragonType.FIRE);
    public static final RegistryObject<Item> FIRE_DRAGON_SWORD = createDragonSwordItem("fire_dragon_sword", EnumDragonType.FIRE);
    //Forest Equipments
    public static final RegistryObject<Item> FOREST_DRAGON_AXE = createDragonAxeItem("forest_dragon_axe", EnumDragonType.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_BOW = createDragonBowItem("forest_dragon_bow", EnumDragonType.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_HOE = createDragonHoeItem("forest_dragon_hoe", EnumDragonType.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_PICKAXE = createDragonPickaxeItem("forest_dragon_pickaxe", EnumDragonType.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_SHIELD = createDragonShieldItem("forest_dragon_shield", EnumDragonType.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_SHOVEL = createDragonShovelItem("forest_dragon_shovel", EnumDragonType.FOREST);
    public static final RegistryObject<Item> FOREST_DRAGON_SWORD = createDragonSwordItem("forest_dragon_sword", EnumDragonType.FOREST);
    //Nether Equipments
    public static final RegistryObject<Item> NETHER_DRAGON_AXE = createDragonAxeItem("nether_dragon_axe", EnumDragonType.NETHER, 6.0F, -2.9F);
    public static final RegistryObject<Item> NETHER_DRAGON_BOW = createDragonBowItem("nether_dragon_bow", EnumDragonType.NETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_HOE = createDragonHoeItem("nether_dragon_hoe", EnumDragonType.NETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_PICKAXE = createDragonPickaxeItem("nether_dragon_pickaxe", EnumDragonType.NETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_SHIELD = createDragonShieldItem("nether_dragon_shield", EnumDragonType.NETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_SHOVEL = createDragonShovelItem("nether_dragon_shovel", EnumDragonType.NETHER);
    public static final RegistryObject<Item> NETHER_DRAGON_SWORD = createDragonSwordItem("nether_dragon_sword", EnumDragonType.NETHER);
    //Ender Equipments
    public static final RegistryObject<Item> ENDER_DRAGON_AXE = createDragonAxeItem("ender_dragon_axe", EnumDragonType.ENDER, 3.0F, -2.9F);
    public static final RegistryObject<Item> ENDER_DRAGON_BOW = createDragonBowItem("ender_dragon_bow", EnumDragonType.ENDER);
    public static final RegistryObject<Item> ENDER_DRAGON_HOE = createDragonHoeItem("ender_dragon_hoe", EnumDragonType.ENDER);
    public static final RegistryObject<Item> ENDER_DRAGON_PICKAXE = createDragonPickaxeItem("ender_dragon_pickaxe", EnumDragonType.ENDER);
    public static final RegistryObject<Item> ENDER_DRAGON_SHIELD = createDragonShieldItem("ender_dragon_shield", EnumDragonType.ENDER);
    public static final RegistryObject<Item> ENDER_DRAGON_SHOVEL = createDragonShovelItem("ender_dragon_shovel", EnumDragonType.ENDER);
    public static final RegistryObject<Item> ENDER_DRAGON_SWORD = createDragonSwordItem("ender_dragon_sword", EnumDragonType.ENDER);
    //Enchant Equipments
    public static final RegistryObject<Item> ENCHANT_DRAGON_AXE = createDragonAxeItem("enchant_dragon_axe", EnumDragonType.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_BOW = createDragonBowItem("enchant_dragon_bow", EnumDragonType.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_HOE = createDragonHoeItem("enchant_dragon_hoe", EnumDragonType.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_PICKAXE = createDragonPickaxeItem("enchant_dragon_pickaxe", EnumDragonType.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_SHIELD = createDragonShieldItem("enchant_dragon_shield", EnumDragonType.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_SHOVEL = createDragonShovelItem("enchant_dragon_shovel", EnumDragonType.ENCHANT);
    public static final RegistryObject<Item> ENCHANT_DRAGON_SWORD = createDragonSwordItem("enchant_dragon_sword", EnumDragonType.ENCHANT);
    //Sunlight Equipments
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_AXE = createDragonAxeItem("sunlight_dragon_axe", EnumDragonType.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_BOW = createDragonBowItem("sunlight_dragon_bow", EnumDragonType.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_HOE = createDragonHoeItem("sunlight_dragon_hoe", EnumDragonType.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_PICKAXE = createDragonPickaxeItem("sunlight_dragon_pickaxe", EnumDragonType.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_SHIELD = createDragonShieldItem("sunlight_dragon_shield", EnumDragonType.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_SHOVEL = createDragonShovelItem("sunlight_dragon_shovel", EnumDragonType.SUNLIGHT);
    public static final RegistryObject<Item> SUNLIGHT_DRAGON_SWORD = createDragonSwordItem("sunlight_dragon_sword", EnumDragonType.SUNLIGHT);
    //Moonlight Equipments
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_AXE = createDragonAxeItem("moonlight_dragon_axe", EnumDragonType.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_BOW = createDragonBowItem("moonlight_dragon_bow", EnumDragonType.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_HOE = createDragonHoeItem("moonlight_dragon_hoe", EnumDragonType.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_PICKAXE = createDragonPickaxeItem("moonlight_dragon_pickaxe", EnumDragonType.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_SHIELD = createDragonShieldItem("moonlight_dragon_shield", EnumDragonType.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_SHOVEL = createDragonShovelItem("moonlight_dragon_shovel", EnumDragonType.MOONLIGHT);
    public static final RegistryObject<Item> MOONLIGHT_DRAGON_SWORD = createDragonSwordItem("moonlight_dragon_sword", EnumDragonType.MOONLIGHT);
    //Storm Equipments
    public static final RegistryObject<Item> STORM_DRAGON_AXE = createDragonAxeItem("storm_dragon_axe", EnumDragonType.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_BOW = createDragonBowItem("storm_dragon_bow", EnumDragonType.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_HOE = createDragonHoeItem("storm_dragon_hoe", EnumDragonType.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_PICKAXE = createDragonPickaxeItem("storm_dragon_pickaxe", EnumDragonType.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_SHIELD = createDragonShieldItem("storm_dragon_shield", EnumDragonType.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_SHOVEL = createDragonShovelItem("storm_dragon_shovel", EnumDragonType.STORM);
    public static final RegistryObject<Item> STORM_DRAGON_SWORD = createDragonSwordItem("storm_dragon_sword", EnumDragonType.STORM);
    //Terra Equipments
    public static final RegistryObject<Item> TERRA_DRAGON_AXE = createDragonAxeItem("terra_dragon_axe", EnumDragonType.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_BOW = createDragonBowItem("terra_dragon_bow", EnumDragonType.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_HOE = createDragonHoeItem("terra_dragon_hoe", EnumDragonType.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_PICKAXE = createDragonPickaxeItem("terra_dragon_pickaxe", EnumDragonType.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_SHIELD = createDragonShieldItem("terra_dragon_shield", EnumDragonType.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_SHOVEL = createDragonShovelItem("terra_dragon_shovel", EnumDragonType.TERRA);
    public static final RegistryObject<Item> TERRA_DRAGON_SWORD = createDragonSwordItem("terra_dragon_sword", EnumDragonType.TERRA);
    //Zombie Equipments
    public static final RegistryObject<Item> ZOMBIE_DRAGON_AXE = createDragonAxeItem("zombie_dragon_axe", EnumDragonType.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_BOW = createDragonBowItem("zombie_dragon_bow", EnumDragonType.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_HOE = createDragonHoeItem("zombie_dragon_hoe", EnumDragonType.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_PICKAXE = createDragonPickaxeItem("zombie_dragon_pickaxe", EnumDragonType.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_SHIELD = createDragonShieldItem("zombie_dragon_shield", EnumDragonType.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_SHOVEL = createDragonShovelItem("zombie_dragon_shovel", EnumDragonType.ZOMBIE);
    public static final RegistryObject<Item> ZOMBIE_DRAGON_SWORD = createDragonSwordItem("zombie_dragon_sword", EnumDragonType.ZOMBIE);

    private static RegistryObject<Item> createDragonArmorItem(String name, EnumDragonType type, EquipmentSlotType slot) {
        return ITEMS.register(name, () -> new ItemDragonArmor(type, slot, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonAxeItem(String name, EnumDragonType type, float attackDamageModifier, float attackSpeedModifier) {
        return ITEMS.register(name, () -> new ItemDragonAxe(type, attackDamageModifier, attackSpeedModifier, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonAxeItem(String name, EnumDragonType type) {
        return ITEMS.register(name, () -> new ItemDragonAxe(type, 5.0F, -2.8F/*Minecraft: -3.0F*/, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonBowItem(String name, EnumDragonType type) {
        return ITEMS.register(name, () -> new ItemDragonBow(type, new Item.Properties().defaultDurability(725).tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonHoeItem(String name, EnumDragonType type) {
        return ITEMS.register(name, () -> new ItemDragonHoe(type, (int) -type.tier.getAttackDamageBonus(), type.tier.getAttackDamageBonus() - 3.0F, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonPickaxeItem(String name, EnumDragonType type) {
        return ITEMS.register(name, () -> new ItemDragonPickaxe(type, 1, -2.8F, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonScalesItem(String name, EnumDragonType type) {
        return ITEMS.register(name, () -> new ItemDragonScales(new Item.Properties().tab(ITEM_TAB), type));
    }

    private static RegistryObject<Item> createDragonShieldItem(String name, EnumDragonType type) {
        return ITEMS.register(name, () -> new ItemDragonShield(type, new Item.Properties().defaultDurability(2500).tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonShovelItem(String name, EnumDragonType type) {
        return ITEMS.register(name, () -> new ItemDragonShovel(type, 1.5F, -3.0F, new Item.Properties().tab(EQUIPMENT_TAB)));
    }

    private static RegistryObject<Item> createDragonSwordItem(String name, EnumDragonType type) {
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