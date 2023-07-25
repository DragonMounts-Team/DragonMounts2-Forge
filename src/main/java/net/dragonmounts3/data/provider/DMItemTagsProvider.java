package net.dragonmounts3.data.provider;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.data.tags.DMBlockTags;
import net.dragonmounts3.data.tags.DMItemTags;
import net.dragonmounts3.init.DMBlocks;
import net.dragonmounts3.init.DMItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class DMItemTagsProvider extends ItemTagsProvider {
    public DMItemTagsProvider(DataGenerator generator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, blockTagsProvider, DragonMounts.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(Tags.Items.SHEARS)
                .add(DMItems.DIAMOND_SHEARS.get())
                .add(DMItems.NETHERITE_SHEARS.get());
        this.tag(ItemTags.PIGLIN_LOVED)
                .add(DMItems.GOLDEN_DRAGON_ARMOR.get());
        this.tag(ItemTags.PIGLIN_REPELLENTS)
                .add(DMBlocks.DRAGON_CORE.get().asItem());
        this.tag(DMItemTags.BATONS)
                .addTag(Tags.Items.RODS_WOODEN)
                .addTag(Tags.Items.BONES)
                .add(Items.BAMBOO);
        this.copy(DMBlockTags.DRAGON_EGGS, DMItemTags.DRAGON_EGGS);
        DMItems.DRAGON_SCALE_BOW.forEach(this.tag(DMItemTags.DRAGON_SCALE_BOWS)::add);
        DMItems.DRAGON_SCALES.forEach(this.tag(DMItemTags.DRAGON_SCALES)::add);
    }
}