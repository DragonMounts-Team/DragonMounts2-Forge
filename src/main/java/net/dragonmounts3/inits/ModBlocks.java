package net.dragonmounts3.inits;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.objects.blocks.BlockDragonNest;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DragonMounts.create(ForgeRegistries.BLOCKS);
    public static final RegistryObject<Block> DRAGON_NEST = register("dragon_nest", BlockDragonNest::new);

    public static RegistryObject<Block> register(String name, Supplier<Block> block) {
        RegistryObject<Block> register = BLOCKS.register(name, block);
        Item.Properties properties = new Item.Properties().tab(ModItems.BLOCK_TAB);
        ModItems.ITEMS.register(name, () -> new BlockItem(register.get(), properties));
        return register;
    }

}