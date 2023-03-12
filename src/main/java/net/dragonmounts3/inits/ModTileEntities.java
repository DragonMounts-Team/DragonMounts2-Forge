package net.dragonmounts3.inits;

import net.dragonmounts3.DragonMounts;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY = DragonMounts.create(ForgeRegistries.TILE_ENTITIES);

}
