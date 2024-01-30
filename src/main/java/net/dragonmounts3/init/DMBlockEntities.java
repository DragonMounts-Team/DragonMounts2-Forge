package net.dragonmounts3.init;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.block.entity.DragonCoreBlockEntity;
import net.dragonmounts3.client.renderer.block.DragonCoreRenderer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DMBlockEntities {

    public static final DeferredRegister<TileEntityType<?>> BLOCK_ENTITY = DragonMounts.create(ForgeRegistries.TILE_ENTITIES);

    public static final RegistryObject<TileEntityType<DragonCoreBlockEntity>> DRAGON_CORE = BLOCK_ENTITY.register("dragon_core", () -> TileEntityType.Builder.of(DragonCoreBlockEntity::new, DMBlocks.DRAGON_CORE).build(null));

    @OnlyIn(Dist.CLIENT)
    public static void registerBlockEntityRenders() {
        ClientRegistry.bindTileEntityRenderer(DRAGON_CORE.get(), DragonCoreRenderer::new);
    }
}
