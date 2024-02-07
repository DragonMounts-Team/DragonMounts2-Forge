package net.dragonmounts.data.provider;

import net.dragonmounts.DragonMounts;
import net.dragonmounts.init.DragonVariants;
import net.dragonmounts.registry.DragonVariant;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DMBlockStateProvider extends BlockStateProvider {
    public DMBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, DragonMounts.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile skull = this.models().getExistingFile(new ResourceLocation("block/skull"));
        for (DragonVariant variant : DragonVariants.VALUES) {
            this.simpleBlock(variant.headBlock, skull);
            this.simpleBlock(variant.headWallBlock, skull);
        }
    }
}