package net.dragonmounts3.inits;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.client.renderer.ModItemStackTileEntityRenderer;
import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.block.DragonEggBlock;
import net.dragonmounts3.block.DragonNestBlock;
import net.dragonmounts3.block.DragonCoreBlock;
import net.dragonmounts3.objects.DragonTypifiedObjectHolder;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static net.dragonmounts3.inits.ModItemGroups.block;
import static net.dragonmounts3.inits.ModItemGroups.none;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DragonMounts.create(ForgeRegistries.BLOCKS);
    public static final DragonTypifiedObjectHolder<DragonEggBlock> DRAGON_EGG = new DragonTypifiedObjectHolder<>();
    public static final RegistryObject<Block> DRAGON_NEST = register("dragon_nest", DragonNestBlock::new, block());
    public static final RegistryObject<Block> DRAGON_CORE = register("dragon_core", DragonCoreBlock::new, none().setISTER(() -> ModItemStackTileEntityRenderer::getInstance));
    public static final RegistryObject<DragonEggBlock> AETHER_DRAGON_EGG = registerDragonEggBlock("aether_dragon_egg", new DragonEggBlock(DragonType.AETHER), block());
    public static final RegistryObject<DragonEggBlock> ENCHANT_DRAGON_EGG = registerDragonEggBlock("enchant_dragon_egg", new DragonEggBlock(DragonType.ENCHANT), block());
    public static final RegistryObject<DragonEggBlock> ENDER_DRAGON_EGG = registerDragonEggBlock("ender_dragon_egg", new DragonEggBlock(DragonType.ENDER), block());
    public static final RegistryObject<DragonEggBlock> FIRE_DRAGON_EGG = registerDragonEggBlock("fire_dragon_egg", new DragonEggBlock(DragonType.FIRE), block());
    public static final RegistryObject<DragonEggBlock> FOREST_DRAGON_EGG = registerDragonEggBlock("forest_dragon_egg", new DragonEggBlock(DragonType.FOREST), block());
    public static final RegistryObject<DragonEggBlock> ICE_DRAGON_EGG = registerDragonEggBlock("ice_dragon_egg", new DragonEggBlock(DragonType.ICE), block());
    public static final RegistryObject<DragonEggBlock> MOONLIGHT_DRAGON_EGG = registerDragonEggBlock("moonlight_dragon_egg", new DragonEggBlock(DragonType.MOONLIGHT), block());
    public static final RegistryObject<DragonEggBlock> NETHER_DRAGON_EGG = registerDragonEggBlock("nether_dragon_egg", new DragonEggBlock(DragonType.NETHER), block());
    public static final RegistryObject<DragonEggBlock> SCULK_DRAGON_EGG = registerDragonEggBlock("sculk_dragon_egg", new DragonEggBlock(DragonType.SCULK), block().fireResistant());
    public static final RegistryObject<DragonEggBlock> SKELETON_DRAGON_EGG = registerDragonEggBlock("skeleton_dragon_egg", new DragonEggBlock(DragonType.SKELETON), block());
    public static final RegistryObject<DragonEggBlock> STORM_DRAGON_EGG = registerDragonEggBlock("storm_dragon_egg", new DragonEggBlock(DragonType.STORM), block());
    public static final RegistryObject<DragonEggBlock> SUNLIGHT_DRAGON_EGG = registerDragonEggBlock("sunlight_dragon_egg", new DragonEggBlock(DragonType.SUNLIGHT), block());
    public static final RegistryObject<DragonEggBlock> TERRA_DRAGON_EGG = registerDragonEggBlock("terra_dragon_egg", new DragonEggBlock(DragonType.TERRA), block());
    public static final RegistryObject<DragonEggBlock> WATER_DRAGON_EGG = registerDragonEggBlock("water_dragon_egg", new DragonEggBlock(DragonType.WATER), block());
    public static final RegistryObject<DragonEggBlock> WITHER_DRAGON_EGG = registerDragonEggBlock("wither_dragon_egg", new DragonEggBlock(DragonType.WITHER), block());
    public static final RegistryObject<DragonEggBlock> ZOMBIE_DRAGON_EGG = registerDragonEggBlock("zombie_dragon_egg", new DragonEggBlock(DragonType.ZOMBIE), block());

    public static RegistryObject<Block> register(String name, Supplier<Block> block, Properties properties) {
        RegistryObject<Block> registry = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(registry.get(), properties));
        return registry;
    }

    public static RegistryObject<DragonEggBlock> registerDragonEggBlock(String name, DragonEggBlock block, Properties properties) {
        RegistryObject<DragonEggBlock> registry = DRAGON_EGG.register(BLOCKS, name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(registry.get(), properties));
        return registry;
    }
}