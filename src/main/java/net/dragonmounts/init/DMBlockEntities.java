package net.dragonmounts.init;

import net.dragonmounts.DragonMounts;
import net.dragonmounts.block.AbstractDragonHeadBlock;
import net.dragonmounts.block.entity.DragonCoreBlockEntity;
import net.dragonmounts.block.entity.DragonHeadBlockEntity;
import net.dragonmounts.client.renderer.block.DragonCoreRenderer;
import net.dragonmounts.client.renderer.block.DragonHeadRenderer;
import net.dragonmounts.registry.DragonVariant;
import net.dragonmounts.util.Values;
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
    public static final RegistryObject<TileEntityType<DragonHeadBlockEntity>> DRAGON_HEAD;

    static {
        Values<DragonVariant> variants = DragonVariants.BUILTIN_VALUES;
        AbstractDragonHeadBlock[] blocks = new AbstractDragonHeadBlock[variants.length << 1];
        int i = 0;
        for (DragonVariant variant : variants) {
            blocks[i++] = variant.headBlock;
            blocks[i++] = variant.headWallBlock;
        }
        //noinspection DataFlowIssue
        DRAGON_HEAD = BLOCK_ENTITY.register("dragon_head", () -> TileEntityType.Builder.of(DragonHeadBlockEntity::new, blocks).build(null));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerBlockEntityRenders() {
        ClientRegistry.bindTileEntityRenderer(DRAGON_CORE.get(), DragonCoreRenderer::new);
        ClientRegistry.bindTileEntityRenderer(DRAGON_HEAD.get(), DragonHeadRenderer::new);
    }
}
