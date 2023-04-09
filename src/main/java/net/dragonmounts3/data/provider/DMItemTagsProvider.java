package net.dragonmounts3.data.provider;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.data.tags.DMItemTags;
import net.dragonmounts3.inits.ModItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
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
        this.tag(DMItemTags.DRAGON_SCALES)
                .add(ModItems.AETHER_DRAGON_SCALES.get())
                .add(ModItems.ENCHANT_DRAGON_SCALES.get())
                .add(ModItems.ENDER_DRAGON_AMULET.get())
                .add(ModItems.FIRE_DRAGON_SCALES.get())
                .add(ModItems.FOREST_DRAGON_SCALES.get())
                .add(ModItems.ICE_DRAGON_SCALES.get())
                .add(ModItems.MOONLIGHT_DRAGON_SCALES.get())
                .add(ModItems.NETHER_DRAGON_SCALES.get())
                .add(ModItems.SCULK_DRAGON_SCALES.get())
                .add(ModItems.STORM_DRAGON_SCALES.get())
                .add(ModItems.SUNLIGHT_DRAGON_SCALES.get())
                .add(ModItems.TERRA_DRAGON_SCALES.get())
                .add(ModItems.WATER_DRAGON_SCALES.get())
                .add(ModItems.ZOMBIE_DRAGON_SCALES.get());
    }
}