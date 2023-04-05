package net.dragonmounts3.inits;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.client.renderer.ModItemStackTileEntityRenderer;
import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.objects.blocks.BlockDragonEgg;
import net.dragonmounts3.objects.blocks.BlockDragonNest;
import net.dragonmounts3.objects.blocks.BlockDragonShulkerBox;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static net.dragonmounts3.inits.ModItemGroups.block;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DragonMounts.create(ForgeRegistries.BLOCKS);
    public static final RegistryObject<Block> DRAGON_NEST = register("dragon_nest", BlockDragonNest::new, block());
    public static final RegistryObject<Block> DRAGON_SHULKER_BOX = register("dragon_shulker_box", BlockDragonShulkerBox::new, block().setISTER(() -> ModItemStackTileEntityRenderer::getInstance));
    public static final RegistryObject<Block> AETHER_DRAGON_EGG = register("aether_dragon_egg", () -> new BlockDragonEgg(DragonType.AETHER), block());
    public static final RegistryObject<Block> ENCHANT_DRAGON_EGG = register("enchant_dragon_egg", () -> new BlockDragonEgg(DragonType.ENCHANT), block());
    public static final RegistryObject<Block> ENDER_DRAGON_EGG = register("ender_dragon_egg", () -> new BlockDragonEgg(DragonType.ENDER), block());
    public static final RegistryObject<Block> FIRE_DRAGON_EGG = register("fire_dragon_egg", () -> new BlockDragonEgg(DragonType.FIRE), block());
    public static final RegistryObject<Block> FOREST_DRAGON_EGG = register("forest_dragon_egg", () -> new BlockDragonEgg(DragonType.FOREST), block());
    public static final RegistryObject<Block> ICE_DRAGON_EGG = register("ice_dragon_egg", () -> new BlockDragonEgg(DragonType.ICE), block());
    public static final RegistryObject<Block> MOONLIGHT_DRAGON_EGG = register("moonlight_dragon_egg", () -> new BlockDragonEgg(DragonType.MOONLIGHT), block());
    public static final RegistryObject<Block> NETHER_DRAGON_EGG = register("nether_dragon_egg", () -> new BlockDragonEgg(DragonType.NETHER), block());
    public static final RegistryObject<Block> SCULK_DRAGON_EGG = register("sculk_dragon_egg", () -> new BlockDragonEgg(DragonType.SCULK), block().fireResistant());
    public static final RegistryObject<Block> SKELETON_DRAGON_EGG = register("skeleton_dragon_egg", () -> new BlockDragonEgg(DragonType.SKELETON), block());
    public static final RegistryObject<Block> STORM_DRAGON_EGG = register("storm_dragon_egg", () -> new BlockDragonEgg(DragonType.STORM), block());
    public static final RegistryObject<Block> SUNLIGHT_DRAGON_EGG = register("sunlight_dragon_egg", () -> new BlockDragonEgg(DragonType.SUNLIGHT), block());
    public static final RegistryObject<Block> TERRA_DRAGON_EGG = register("terra_dragon_egg", () -> new BlockDragonEgg(DragonType.TERRA), block());
    public static final RegistryObject<Block> WATER_DRAGON_EGG = register("water_dragon_egg", () -> new BlockDragonEgg(DragonType.WATER), block());
    public static final RegistryObject<Block> WITHER_DRAGON_EGG = register("wither_dragon_egg", () -> new BlockDragonEgg(DragonType.WITHER), block());
    public static final RegistryObject<Block> ZOMBIE_DRAGON_EGG = register("zombie_dragon_egg", () -> new BlockDragonEgg(DragonType.ZOMBIE), block());

    public static RegistryObject<Block> register(String name, Supplier<Block> block, Properties properties) {
        RegistryObject<Block> registry = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(registry.get(), properties));
        return registry;
    }
}