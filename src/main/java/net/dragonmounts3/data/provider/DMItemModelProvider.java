package net.dragonmounts3.data.provider;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.inits.ModItems;
import net.dragonmounts3.item.DragonScaleShieldItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static net.dragonmounts3.DragonMounts.prefix;

public class DMItemModelProvider extends ItemModelProvider {

    public DMItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DragonMounts.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (DragonScaleShieldItem item : ModItems.DRAGON_SCALE_SHIELD) {
            String texture = "entities/dragon_scale_shield/" + item.getDragonType().getSerializedName();
            String base = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
            ModelFile blocking = this.getBuilder(base + "_blocking")
                    .parent(getExistingFile(prefix("item/shield/shield_blocking")))
                    .texture("base", prefix(texture));
            this.getBuilder(base)
                    .parent(getExistingFile(prefix("item/shield/shield")))
                    .texture("base", prefix(texture))
                    .override()
                    .predicate(new ResourceLocation("blocking"), 1)
                    .model(blocking)
                    .end();
        }
    }
}