package net.dragonmounts3.inits;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.client.renderer.ModItemStackTileEntityRenderer;
import net.dragonmounts3.objects.blocks.BlockDragonNest;
import net.dragonmounts3.objects.blocks.BlockDragonShulkerBox;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DragonMounts.create(ForgeRegistries.BLOCKS);
    public static final RegistryObject<Block> DRAGON_NEST = register("dragon_nest", BlockDragonNest::new);
    public static final RegistryObject<Block> DRAGON_SHULKER_BOX = registerWithISTER("dragon_shulker_box", BlockDragonShulkerBox::new);

    public static RegistryObject<Block> register(String name, Supplier<Block> block, Properties properties) {
        RegistryObject<Block> register = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(register.get(), properties));
        return register;
    }

    public static RegistryObject<Block> register(String name, Supplier<Block> block) {
        return register(name, block, new Properties().tab(ModItems.BLOCK_TAB));
    }

    public static RegistryObject<Block> registerWithISTER(String name, Supplier<Block> block) {
        return register(name, block, new Properties().tab(ModItems.BLOCK_TAB).setISTER(() -> ModItemStackTileEntityRenderer::getInstance));
    }
}