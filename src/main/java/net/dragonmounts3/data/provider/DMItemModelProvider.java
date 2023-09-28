package net.dragonmounts3.data.provider;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.item.DragonScaleBowItem;
import net.dragonmounts3.item.DragonScaleShieldItem;
import net.dragonmounts3.item.DragonSpawnEggItem;
import net.dragonmounts3.registry.DragonType;
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
        ModelFile shieldModel = getExistingFile(prefix("item/shield/shield"));
        ModelFile shieldBlockingModel = getExistingFile(prefix("item/shield/shield_blocking"));
        ResourceLocation blockingPredicate = new ResourceLocation("blocking");
        ModelFile vanillaSpawnModel = getExistingFile(new ResourceLocation("item/template_spawn_egg"));
        for (DragonType type : DragonType.REGISTRY) {//Do NOT load other mods at the same time!
            DragonScaleBowItem bow = type.getInstance(DragonScaleBowItem.class, null);
            if (bow != null) {
                StringBuilder root = new StringBuilder(Objects.requireNonNull(bow.getRegistryName()).getPath())
                        .append("_");
                StringBuilder texture = new StringBuilder("items/bow/")
                        .append(bow.getDragonType().getSerializedName().getPath())
                        //Notice:                 ↓
                        .append("_dragon_scale_bow_");
                int rootLength = root.length();
                int textureLength = texture.length();
                for (int i = 0; i < 3; ++i) {
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
                        .predicate(pullingPredicate, 1.00F)
                        .model(pulling[0])
                        .end()
                        .override()
                        .predicate(pullingPredicate, 1.00F)
                        .predicate(pullPredicate, 0.65F)
                        .model(pulling[1])
                        .end()
                        .override()
                        .predicate(pullingPredicate, 1.00F)
                        .predicate(pullPredicate, 0.90F)
                        .model(pulling[2])
                        .end();
            }
            DragonScaleShieldItem shield = type.getInstance(DragonScaleShieldItem.class, null);
            if (shield != null) {
                ResourceLocation texture = prefix("entities/dragon_scale_shield/" + shield.getDragonType().getSerializedName().getPath());
                String root = Objects.requireNonNull(shield.getRegistryName()).getPath();
                ModelFile blocking = this.getBuilder(root + "_blocking")
                        .parent(shieldBlockingModel)
                        .texture("base", texture);
                this.getBuilder(root)
                        .parent(shieldModel)
                        .texture("base", texture)
                        .override()
                        .predicate(blockingPredicate, 1.00F)
                        .model(blocking)
                        .end();

            }
            DragonSpawnEggItem egg = type.getInstance(DragonSpawnEggItem.class, null);
            if (egg != null) {
                this.getBuilder(Objects.requireNonNull(egg.getRegistryName()).getPath()).parent(vanillaSpawnModel);
            }
        }
    }
}