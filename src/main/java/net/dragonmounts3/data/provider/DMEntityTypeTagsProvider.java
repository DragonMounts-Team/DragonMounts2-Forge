package net.dragonmounts3.data.provider;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.data.tags.DMEntityTypeTags;
import net.dragonmounts3.inits.ModEntities;
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
                .add(ModEntities.HATCHABLE_DRAGON_EGG.get())
                .add(ModEntities.TAMEABLE_DRAGON.get());
    }

}