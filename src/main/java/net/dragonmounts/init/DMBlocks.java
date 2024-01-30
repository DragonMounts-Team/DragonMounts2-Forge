package net.dragonmounts.init;

import net.dragonmounts.DragonMounts;
import net.dragonmounts.block.DragonCoreBlock;
import net.dragonmounts.block.DragonNestBlock;
import net.dragonmounts.block.HatchableDragonEggBlock;
import net.dragonmounts.client.renderer.DMItemStackTileEntityRenderer;
import net.dragonmounts.registry.DragonType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.dragonmounts.init.DMItemGroups.block;
import static net.dragonmounts.init.DMItemGroups.none;
import static net.dragonmounts.init.DMItems.ITEMS;

public class DMBlocks {
    public static final DeferredRegister<Block> BLOCKS = DragonMounts.create(ForgeRegistries.BLOCKS);
    public static final DragonNestBlock DRAGON_NEST = register("dragon_nest", new DragonNestBlock(), block());
    public static final DragonCoreBlock DRAGON_CORE = register("dragon_core", new DragonCoreBlock(), none().setISTER(() -> DMItemStackTileEntityRenderer::getInstance));
    public static final HatchableDragonEggBlock AETHER_DRAGON_EGG = registerDragonEggBlock("aether_dragon_egg", DragonTypes.AETHER, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock ENCHANT_DRAGON_EGG = registerDragonEggBlock("enchant_dragon_egg", DragonTypes.ENCHANT, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock ENDER_DRAGON_EGG = registerDragonEggBlock("ender_dragon_egg", DragonTypes.ENDER, block().rarity(Rarity.EPIC));
    public static final HatchableDragonEggBlock FIRE_DRAGON_EGG = registerDragonEggBlock("fire_dragon_egg", DragonTypes.FIRE, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock FOREST_DRAGON_EGG = registerDragonEggBlock("forest_dragon_egg", DragonTypes.FOREST, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock ICE_DRAGON_EGG = registerDragonEggBlock("ice_dragon_egg", DragonTypes.ICE, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock MOONLIGHT_DRAGON_EGG = registerDragonEggBlock("moonlight_dragon_egg", DragonTypes.MOONLIGHT, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock NETHER_DRAGON_EGG = registerDragonEggBlock("nether_dragon_egg", DragonTypes.NETHER, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock SCULK_DRAGON_EGG = registerDragonEggBlock("sculk_dragon_egg", DragonTypes.SCULK, block().fireResistant().rarity(Rarity.RARE));
    public static final HatchableDragonEggBlock SKELETON_DRAGON_EGG = registerDragonEggBlock("skeleton_dragon_egg", DragonTypes.SKELETON, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock STORM_DRAGON_EGG = registerDragonEggBlock("storm_dragon_egg", DragonTypes.STORM, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock SUNLIGHT_DRAGON_EGG = registerDragonEggBlock("sunlight_dragon_egg", DragonTypes.SUNLIGHT, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock TERRA_DRAGON_EGG = registerDragonEggBlock("terra_dragon_egg", DragonTypes.TERRA, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock WATER_DRAGON_EGG = registerDragonEggBlock("water_dragon_egg", DragonTypes.WATER, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock WITHER_DRAGON_EGG = registerDragonEggBlock("wither_dragon_egg", DragonTypes.WITHER, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock ZOMBIE_DRAGON_EGG = registerDragonEggBlock("zombie_dragon_egg", DragonTypes.ZOMBIE, block().rarity(Rarity.UNCOMMON));

    public static <T extends Block> T register(String name, T block, Item.Properties properties) {
        BlockItem item = new BlockItem(block, properties);
        ITEMS.register(name, () -> item);
        BLOCKS.register(name, () -> block);
        return block;
    }

    public static HatchableDragonEggBlock registerDragonEggBlock(String name, DragonType type, Item.Properties properties) {
        HatchableDragonEggBlock block = new HatchableDragonEggBlock(type, AbstractBlock.Properties.of(Material.EGG, MaterialColor.COLOR_BLACK).strength(0.0F, 9.0F).lightLevel(state -> 1).noOcclusion());
        BlockItem item = new BlockItem(block, properties);
        type.bindInstance(HatchableDragonEggBlock.class, block);
        ITEMS.register(name, () -> item);
        BLOCKS.register(name, () -> block);
        return block;
    }
}