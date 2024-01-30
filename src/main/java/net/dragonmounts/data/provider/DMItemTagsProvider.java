package net.dragonmounts.data.provider;

import net.dragonmounts.DragonMounts;
import net.dragonmounts.data.tags.DMBlockTags;
import net.dragonmounts.data.tags.DMItemTags;
import net.dragonmounts.init.DMBlocks;
import net.dragonmounts.init.DMItems;
import net.dragonmounts.item.DragonScaleBowItem;
import net.dragonmounts.item.DragonScalesItem;
import net.dragonmounts.registry.DragonType;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
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
                .add(DMItems.DIAMOND_SHEARS)
                .add(DMItems.NETHERITE_SHEARS);
        this.tag(ItemTags.PIGLIN_LOVED)
                .add(DMItems.GOLDEN_DRAGON_ARMOR);
        this.tag(ItemTags.PIGLIN_REPELLENTS)
                .add(DMBlocks.DRAGON_CORE.asItem());
        this.tag(DMItemTags.BATONS)
                .addTag(Tags.Items.RODS_WOODEN)
                .addTag(Tags.Items.BONES)
                .add(Items.BAMBOO);
        this.copy(DMBlockTags.DRAGON_EGGS, DMItemTags.DRAGON_EGGS);
        Builder<Item> tagBow = this.tag(DMItemTags.DRAGON_SCALE_BOWS);
        Builder<Item> tagScales = this.tag(DMItemTags.DRAGON_SCALES);
        for (DragonType type : DragonType.REGISTRY) {
            DragonScaleBowItem bow = type.getInstance(DragonScaleBowItem.class, null);
            if (bow != null) {
                tagBow.add(bow);
            }
            DragonScalesItem scales = type.getInstance(DragonScalesItem.class, null);
            if (scales != null) {
                tagScales.add(scales);
            }
        }
    }
}