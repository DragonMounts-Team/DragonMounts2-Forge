package net.dragonmounts3.init;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.block.DragonCoreBlock;
import net.dragonmounts3.block.DragonNestBlock;
import net.dragonmounts3.block.HatchableDragonEggBlock;
import net.dragonmounts3.client.renderer.DMItemStackTileEntityRenderer;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.dragonmounts3.init.DMItemGroups.block;
import static net.dragonmounts3.init.DMItemGroups.none;

public class DMBlocks {
    public static final DeferredRegister<Block> BLOCKS = DragonMounts.create(ForgeRegistries.BLOCKS);
    public static final RegistryObject<DragonNestBlock> DRAGON_NEST = register("dragon_nest", new DragonNestBlock(), block());
    public static final RegistryObject<DragonCoreBlock> DRAGON_CORE = register("dragon_core", new DragonCoreBlock(), none().setISTER(() -> DMItemStackTileEntityRenderer::getInstance));
    public static final RegistryObject<HatchableDragonEggBlock> AETHER_DRAGON_EGG = registerDragonEggBlock("aether_dragon_egg", DragonTypes.AETHER, block().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<HatchableDragonEggBlock> ENCHANT_DRAGON_EGG = registerDragonEggBlock("enchant_dragon_egg", DragonTypes.ENCHANT, block().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<HatchableDragonEggBlock> ENDER_DRAGON_EGG = registerDragonEggBlock("ender_dragon_egg", DragonTypes.ENDER, block().rarity(Rarity.EPIC));
    public static final RegistryObject<HatchableDragonEggBlock> FIRE_DRAGON_EGG = registerDragonEggBlock("fire_dragon_egg", DragonTypes.FIRE, block().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<HatchableDragonEggBlock> FOREST_DRAGON_EGG = registerDragonEggBlock("forest_dragon_egg", DragonTypes.FOREST, block().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<HatchableDragonEggBlock> ICE_DRAGON_EGG = registerDragonEggBlock("ice_dragon_egg", DragonTypes.ICE, block().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<HatchableDragonEggBlock> MOONLIGHT_DRAGON_EGG = registerDragonEggBlock("moonlight_dragon_egg", DragonTypes.MOONLIGHT, block().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<HatchableDragonEggBlock> NETHER_DRAGON_EGG = registerDragonEggBlock("nether_dragon_egg", DragonTypes.NETHER, block().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<HatchableDragonEggBlock> SCULK_DRAGON_EGG = registerDragonEggBlock("sculk_dragon_egg", DragonTypes.SCULK, block().fireResistant().rarity(Rarity.RARE));
    public static final RegistryObject<HatchableDragonEggBlock> SKELETON_DRAGON_EGG = registerDragonEggBlock("skeleton_dragon_egg", DragonTypes.SKELETON, block().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<HatchableDragonEggBlock> STORM_DRAGON_EGG = registerDragonEggBlock("storm_dragon_egg", DragonTypes.STORM, block().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<HatchableDragonEggBlock> SUNLIGHT_DRAGON_EGG = registerDragonEggBlock("sunlight_dragon_egg", DragonTypes.SUNLIGHT, block().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<HatchableDragonEggBlock> TERRA_DRAGON_EGG = registerDragonEggBlock("terra_dragon_egg", DragonTypes.TERRA, block().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<HatchableDragonEggBlock> WATER_DRAGON_EGG = registerDragonEggBlock("water_dragon_egg", DragonTypes.WATER, block().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<HatchableDragonEggBlock> WITHER_DRAGON_EGG = registerDragonEggBlock("wither_dragon_egg", DragonTypes.WITHER, block().rarity(Rarity.UNCOMMON));
    public static final RegistryObject<HatchableDragonEggBlock> ZOMBIE_DRAGON_EGG = registerDragonEggBlock("zombie_dragon_egg", DragonTypes.ZOMBIE, block().rarity(Rarity.UNCOMMON));

    public static <T extends Block> RegistryObject<T> register(String name, T block, Item.Properties properties) {
        BlockItem item = new BlockItem(block, properties);
        DMItems.ITEMS.register(name, () -> item);
        return BLOCKS.register(name, () -> block);
    }

    public static RegistryObject<HatchableDragonEggBlock> registerDragonEggBlock(String name, DragonType type, Item.Properties properties) {
        HatchableDragonEggBlock block = new HatchableDragonEggBlock(type, AbstractBlock.Properties.of(Material.EGG, MaterialColor.COLOR_BLACK).strength(0.0F, 9.0F).lightLevel(state -> 1).noOcclusion());
        BlockItem item = new BlockItem(block, properties);
        type.bindInstance(HatchableDragonEggBlock.class, block);
        DMItems.ITEMS.register(name, () -> item);
        return BLOCKS.register(name, () -> block);
    }
}