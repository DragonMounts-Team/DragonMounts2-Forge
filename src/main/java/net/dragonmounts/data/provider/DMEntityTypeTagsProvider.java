package net.dragonmounts.data.provider;

import net.dragonmounts.DragonMounts;
import net.dragonmounts.data.tags.DMEntityTypeTags;
import net.dragonmounts.init.DMEntities;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class DMEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public DMEntityTypeTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, DragonMounts.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(DMEntityTypeTags.DRAGONS)
                .add(DMEntities.HATCHABLE_DRAGON_EGG.get())
                .add(DMEntities.TAMEABLE_DRAGON.get());
    }

}