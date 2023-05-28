package net.dragonmounts3.data.provider;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.data.tags.DMEntityTypeTags;
import net.dragonmounts3.init.DMEntities;
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