package net.dragonmounts3.init;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.DragonTypifiedRegistry;
import net.dragonmounts3.block.DragonCoreBlock;
import net.dragonmounts3.block.DragonNestBlock;
import net.dragonmounts3.block.HatchableDragonEggBlock;
import net.dragonmounts3.client.renderer.DMItemStackTileEntityRenderer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static net.dragonmounts3.init.DMItemGroups.block;
import static net.dragonmounts3.init.DMItemGroups.none;

public class DMBlocks {
    public static final DeferredRegister<Block> BLOCKS = DragonMounts.create(ForgeRegistries.BLOCKS);
    public static final DragonTypifiedRegistry<HatchableDragonEggBlock> HATCHABLE_DRAGON_EGG = new DragonTypifiedRegistry<>();
    public static final RegistryObject<Block> DRAGON_NEST = register("dragon_nest", DragonNestBlock::new, block());
    public static final RegistryObject<Block> DRAGON_CORE = register("dragon_core", DragonCoreBlock::new, none().setISTER(() -> DMItemStackTileEntityRenderer::getInstance));
    public static final RegistryObject<HatchableDragonEggBlock> AETHER_DRAGON_EGG = registerDragonEggBlock("aether_dragon_egg", new HatchableDragonEggBlock(DragonType.AETHER), block());
    public static final RegistryObject<HatchableDragonEggBlock> ENCHANT_DRAGON_EGG = registerDragonEggBlock("enchant_dragon_egg", new HatchableDragonEggBlock(DragonType.ENCHANT), block());
    public static final RegistryObject<HatchableDragonEggBlock> ENDER_DRAGON_EGG = registerDragonEggBlock("ender_dragon_egg", new HatchableDragonEggBlock(DragonType.ENDER), block());
    public static final RegistryObject<HatchableDragonEggBlock> FIRE_DRAGON_EGG = registerDragonEggBlock("fire_dragon_egg", new HatchableDragonEggBlock(DragonType.FIRE), block());
    public static final RegistryObject<HatchableDragonEggBlock> FOREST_DRAGON_EGG = registerDragonEggBlock("forest_dragon_egg", new HatchableDragonEggBlock(DragonType.FOREST), block());
    public static final RegistryObject<HatchableDragonEggBlock> ICE_DRAGON_EGG = registerDragonEggBlock("ice_dragon_egg", new HatchableDragonEggBlock(DragonType.ICE), block());
    public static final RegistryObject<HatchableDragonEggBlock> MOONLIGHT_DRAGON_EGG = registerDragonEggBlock("moonlight_dragon_egg", new HatchableDragonEggBlock(DragonType.MOONLIGHT), block());
    public static final RegistryObject<HatchableDragonEggBlock> NETHER_DRAGON_EGG = registerDragonEggBlock("nether_dragon_egg", new HatchableDragonEggBlock(DragonType.NETHER), block());
    public static final RegistryObject<HatchableDragonEggBlock> SCULK_DRAGON_EGG = registerDragonEggBlock("sculk_dragon_egg", new HatchableDragonEggBlock(DragonType.SCULK), block().fireResistant());
    public static final RegistryObject<HatchableDragonEggBlock> SKELETON_DRAGON_EGG = registerDragonEggBlock("skeleton_dragon_egg", new HatchableDragonEggBlock(DragonType.SKELETON), block());
    public static final RegistryObject<HatchableDragonEggBlock> STORM_DRAGON_EGG = registerDragonEggBlock("storm_dragon_egg", new HatchableDragonEggBlock(DragonType.STORM), block());
    public static final RegistryObject<HatchableDragonEggBlock> SUNLIGHT_DRAGON_EGG = registerDragonEggBlock("sunlight_dragon_egg", new HatchableDragonEggBlock(DragonType.SUNLIGHT), block());
    public static final RegistryObject<HatchableDragonEggBlock> TERRA_DRAGON_EGG = registerDragonEggBlock("terra_dragon_egg", new HatchableDragonEggBlock(DragonType.TERRA), block());
    public static final RegistryObject<HatchableDragonEggBlock> WATER_DRAGON_EGG = registerDragonEggBlock("water_dragon_egg", new HatchableDragonEggBlock(DragonType.WATER), block());
    public static final RegistryObject<HatchableDragonEggBlock> WITHER_DRAGON_EGG = registerDragonEggBlock("wither_dragon_egg", new HatchableDragonEggBlock(DragonType.WITHER), block());
    public static final RegistryObject<HatchableDragonEggBlock> ZOMBIE_DRAGON_EGG = registerDragonEggBlock("zombie_dragon_egg", new HatchableDragonEggBlock(DragonType.ZOMBIE), block());

    public static RegistryObject<Block> register(String name, Supplier<Block> block, Properties properties) {
        RegistryObject<Block> registry = BLOCKS.register(name, block);
        DMItems.ITEMS.register(name, () -> new BlockItem(registry.get(), properties));
        return registry;
    }

    public static RegistryObject<HatchableDragonEggBlock> registerDragonEggBlock(String name, HatchableDragonEggBlock block, Properties properties) {
        RegistryObject<HatchableDragonEggBlock> registry = HATCHABLE_DRAGON_EGG.register(BLOCKS, name, block);
        DMItems.ITEMS.register(name, () -> new BlockItem(registry.get(), properties));
        return registry;
    }
}