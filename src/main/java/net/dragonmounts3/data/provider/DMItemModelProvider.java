package net.dragonmounts3.data.provider;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.inits.ModItems;
import net.dragonmounts3.item.DragonScaleBowItem;
import net.dragonmounts3.item.DragonScaleShieldItem;
import net.dragonmounts3.item.DragonSpawnEggItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;

import static net.dragonmounts3.DragonMounts.prefix;

public class DMItemModelProvider extends ItemModelProvider {

    public DMItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DragonMounts.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModelFile vanillaBowModel = getExistingFile(new ResourceLocation("item/bow"));
        ModelFile[] pulling = new ModelFile[3];
        ResourceLocation pullPredicate = new ResourceLocation("pull");
        ResourceLocation pullingPredicate = new ResourceLocation("pulling");
        for (DragonScaleBowItem item : ModItems.DRAGON_SCALE_BOW) {
            StringBuilder root = new StringBuilder(Objects.requireNonNull(item.getRegistryName()).getPath())
                    .append("_");
            StringBuilder texture = new StringBuilder("items/bow/")
                    .append(item.getDragonType().getSerializedName())
                    //pay attention to        ▼
                    .append("_dragon_scale_bow_");
            int rootLength = root.length();
            int textureLength = texture.length();
            for (int i = 0; i <= 2; i++) {
                pulling[i] = this.getBuilder(root.append(i).toString())
                        .parent(vanillaBowModel)
                        .texture("layer0", prefix(texture.append(i).toString()));
                root.setLength(rootLength);
                texture.setLength(textureLength);
            }
            this.getBuilder(root.substring(0, rootLength - 1))
                    .parent(vanillaBowModel)
                    .texture("layer0", prefix(texture.substring(0, textureLength - 1)))
                    .override()
                    .predicate(pullingPredicate, 1.00f)
                    .model(pulling[0])
                    .end()
                    .override()
                    .predicate(pullingPredicate, 1.00f)
                    .predicate(pullPredicate, 0.65f)
                    .model(pulling[1])
                    .end()
                    .override()
                    .predicate(pullingPredicate, 1.00f)
                    .predicate(pullPredicate, 0.90f)
                    .model(pulling[2])
                    .end();
        }
        ModelFile shieldModel = getExistingFile(prefix("item/shield/shield"));
        ModelFile shieldBlockingModel = getExistingFile(prefix("item/shield/shield_blocking"));
        ResourceLocation blockingPredicate = new ResourceLocation("blocking");
        for (DragonScaleShieldItem item : ModItems.DRAGON_SCALE_SHIELD) {
            ResourceLocation texture = prefix("entities/dragon_scale_shield/" + item.getDragonType().getSerializedName());
            String root = Objects.requireNonNull(item.getRegistryName()).getPath();
            ModelFile blocking = this.getBuilder(root + "_blocking")
                    .parent(shieldBlockingModel)
                    .texture("base", texture);
            this.getBuilder(root)
                    .parent(shieldModel)
                    .texture("base", texture)
                    .override()
                    .predicate(blockingPredicate, 1.00f)
                    .model(blocking)
                    .end();
        }
        ModelFile vanillaSpawnModel = getExistingFile(new ResourceLocation("item/template_spawn_egg"));
        for (DragonSpawnEggItem item : ModItems.DRAGON_SPAWN_EGG) {
            this.getBuilder(Objects.requireNonNull(item.getRegistryName()).getPath()).parent(vanillaSpawnModel);
        }
    }
}