package net.dragonmounts3.data.provider;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.data.tags.DMItemTags;
import net.dragonmounts3.inits.ModItems;
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
                .add(ModItems.DIAMOND_SHEARS.get())
                .add(ModItems.NETHERITE_SHEARS.get());
        this.tag(ItemTags.PIGLIN_LOVED)
                .add(ModItems.GOLDEN_DRAGON_ARMOR.get());
        TagsProvider.Builder<Item> dragonScaleBow = this.tag(DMItemTags.DRAGON_SCALE_BOW);
        for (DragonScaleBowItem item : ModItems.DRAGON_SCALE_BOW) {
            dragonScaleBow.add(item);
        }
        TagsProvider.Builder<Item> dragonScales = this.tag(DMItemTags.DRAGON_SCALES);
        for (DragonScalesItem item : ModItems.DRAGON_SCALES) {
            dragonScales.add(item);
        }
    }
}