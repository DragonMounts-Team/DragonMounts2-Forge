package net.dragonmounts.data.provider;

import net.dragonmounts.DragonMounts;
import net.dragonmounts.block.HatchableDragonEggBlock;
import net.dragonmounts.data.tag.DMBlockTags;
import net.dragonmounts.init.DMBlocks;
import net.dragonmounts.registry.DragonType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class DMBlockTagsProvider extends BlockTagsProvider {
    public DMBlockTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, DragonMounts.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(BlockTags.PIGLIN_REPELLENTS)
                .add(DMBlocks.DRAGON_CORE);
        Builder<Block> tag = this.tag(DMBlockTags.DRAGON_EGGS).add(Blocks.DRAGON_EGG);
        for (DragonType type : DragonType.REGISTRY) {
            HatchableDragonEggBlock egg = type.getInstance(HatchableDragonEggBlock.class, null);
            if (egg != null) tag.add(egg);
        }
    }
}