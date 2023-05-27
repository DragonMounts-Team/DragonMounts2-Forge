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
                .add(ModItems.DIAMOND_SHEARS.get())
                .add(ModItems.NETHERITE_SHEARS.get());
        this.tag(ItemTags.PIGLIN_LOVED)
                .add(ModItems.GOLDEN_DRAGON_ARMOR.get());
        this.tag(DMItemTags.DRAGON_FOOD)//ItemTags.FISHES includes Items.PUFFERFISH
                .add(Items.COD)
                .add(Items.COOKED_COD)
                .add(Items.SALMON)
                .add(Items.COOKED_SALMON)
                .add(Items.TROPICAL_FISH)
                .add(Items.COD_BUCKET)
                .add(Items.SALMON_BUCKET)
                .add(Items.TROPICAL_FISH_BUCKET)
                .add(Items.CHICKEN)
                .add(Items.COOKED_CHICKEN)
                .add(Items.BEEF)
                .add(Items.COOKED_BEEF)
                .add(Items.MUTTON)
                .add(Items.COOKED_MUTTON)
                .add(Items.RABBIT)
                .add(Items.RABBIT_STEW)
                .add(Items.COOKED_RABBIT)
                .add(Items.PORKCHOP)
                .add(Items.COOKED_PORKCHOP);
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