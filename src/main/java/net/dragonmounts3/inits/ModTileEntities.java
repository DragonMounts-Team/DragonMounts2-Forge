package net.dragonmounts3.inits;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.client.renderer.block.DragonShulkerBoxRenderer;
import net.dragonmounts3.objects.blocks.entity.TileEntityDragonShulkerBox;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY = DragonMounts.create(ForgeRegistries.TILE_ENTITIES);

    public static final RegistryObject<TileEntityType<TileEntityDragonShulkerBox>> DRAGON_SHULKER_BOX = TILE_ENTITY.register("dragon_shulker_box", () -> TileEntityType.Builder.of(TileEntityDragonShulkerBox::new, ModBlocks.DRAGON_SHULKER_BOX.get()).build(null));

    @OnlyIn(Dist.CLIENT)
    public static void registerTileEntityRenders() {
        ClientRegistry.bindTileEntityRenderer(DRAGON_SHULKER_BOX.get(), DragonShulkerBoxRenderer::new);
    }
}
