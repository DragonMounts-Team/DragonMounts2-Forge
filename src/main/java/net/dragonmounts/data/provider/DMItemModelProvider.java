package net.dragonmounts.data.provider;

import net.dragonmounts.DragonMounts;
import net.dragonmounts.init.DragonVariants;
import net.dragonmounts.item.DragonScaleBowItem;
import net.dragonmounts.item.DragonScaleShieldItem;
import net.dragonmounts.item.DragonSpawnEggItem;
import net.dragonmounts.registry.DragonType;
import net.dragonmounts.registry.DragonVariant;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import static net.dragonmounts.DragonMounts.makeId;

public class DMItemModelProvider extends ItemModelProvider {

    public DMItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DragonMounts.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ResourceLocation pullPredicate = new ResourceLocation("pull");
        ResourceLocation pullingPredicate = new ResourceLocation("pulling");
        ResourceLocation blockingPredicate = new ResourceLocation("blocking");
        ModelFile bowModel = getExistingFile(new ResourceLocation("item/bow"));
        ModelFile shieldModel = getExistingFile(makeId("item/shield/shield"));
        ModelFile shieldBlockingModel = getExistingFile(makeId("item/shield/shield_blocking"));
        ModelFile spawnEggModel = getExistingFile(new ResourceLocation("item/template_spawn_egg"));
        ModelFile[] pulling = new ModelFile[3];
        for (DragonType type : DragonType.REGISTRY) {
            DragonScaleBowItem bow = type.getInstance(DragonScaleBowItem.class, null);
            DragonScaleShieldItem shield = type.getInstance(DragonScaleShieldItem.class, null);
            DragonSpawnEggItem egg = type.getInstance(DragonSpawnEggItem.class, null);
            if (bow != null) {
                //noinspection DataFlowIssue
                StringBuilder root = new StringBuilder(bow.getRegistryName().getPath())
                        .append("_");
                StringBuilder texture = new StringBuilder("items/bow/")
                        .append(bow.getDragonType().getSerializedName().getPath())
                        //Notice:                 ↓
                        .append("_dragon_scale_bow_");
                int rootLength = root.length();
                int textureLength = texture.length();
                for (int i = 0; i < 3; ++i) {
                    pulling[i] = this.getBuilder(root.append(i).toString())
                            .parent(bowModel)
                            .texture("layer0", makeId(texture.append(i).toString()));
                    root.setLength(rootLength);
                    texture.setLength(textureLength);
                }
                this.getBuilder(root.substring(0, rootLength - 1))
                        .parent(bowModel)
                        .texture("layer0", makeId(texture.substring(0, textureLength - 1)))
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
            if (shield != null) {
                ResourceLocation texture = makeId("entities/dragon_scale_shield/" + shield.getDragonType().getSerializedName().getPath());
                //noinspection DataFlowIssue
                String root = shield.getRegistryName().getPath();
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
            if (egg != null) {//noinspection DataFlowIssue
                this.getBuilder(egg.getRegistryName().getPath()).parent(spawnEggModel);
            }
        }
        ModelFile dragonHeadModel = getExistingFile(new ResourceLocation("item/dragon_head"));
        for (DragonVariant variant : DragonVariants.BUILTIN_VALUES) {//noinspection DataFlowIssue
            this.getBuilder(variant.headItem.getRegistryName().getPath()).parent(dragonHeadModel);
        }
    }
}