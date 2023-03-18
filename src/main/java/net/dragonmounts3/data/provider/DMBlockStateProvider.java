package net.dragonmounts3.data.provider;

import net.dragonmounts3.DragonMounts;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DMBlockStateProvider extends BlockStateProvider {

    public DMBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, DragonMounts.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }

}