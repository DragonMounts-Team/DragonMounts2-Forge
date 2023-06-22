package net.dragonmounts3.data.provider;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.data.tags.DMItemTags;
import net.dragonmounts3.init.DMItems;
import net.dragonmounts3.item.DragonScaleBowItem;
import net.dragonmounts3.item.DragonScalesItem;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
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
        TagsProvider.Builder<Item> dragonScaleBow = this.tag(DMItemTags.DRAGON_SCALE_BOW);
        for (DragonScaleBowItem item : DMItems.DRAGON_SCALE_BOW) {
            dragonScaleBow.add(item);
        }
        TagsProvider.Builder<Item> dragonScales = this.tag(DMItemTags.DRAGON_SCALES);
        for (DragonScalesItem item : DMItems.DRAGON_SCALES) {
            dragonScales.add(item);
        }
    }
}