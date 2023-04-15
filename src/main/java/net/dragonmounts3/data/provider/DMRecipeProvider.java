package net.dragonmounts3.data.provider;

import net.dragonmounts3.data.tags.DMItemTags;
import net.dragonmounts3.inits.ModItems;
import net.dragonmounts3.objects.DragonType;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Consumer;

import static net.dragonmounts3.DragonMounts.prefix;

public class DMRecipeProvider extends RecipeProvider {

    public DMRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        netheriteSmithing(consumer, ModItems.DIAMOND_DRAGON_ARMOR.get(), ModItems.NETHERITE_DRAGON_ARMOR.get());
        netheriteSmithing(consumer, ModItems.DIAMOND_SHEARS.get(), ModItems.NETHERITE_SHEARS.get());
        for (DragonType type : DragonType.values()) {
            Item scale = ModItems.DRAGON_SCALES.get(type);
            if (scale != null) {
                Item item = ModItems.DRAGON_SCALE_AXE.get(type);
                if (item != null) {
                    dragonScaleAxe(consumer, scale, item);
                }
                item = ModItems.DRAGON_SCALE_BOOTS.get(type);
                if (item != null) {
                    dragonScaleBoots(consumer, scale, item);
                }
                item = ModItems.DRAGON_SCALE_BOW.get(type);
                if (item != null) {
                    dragonScaleBow(consumer, scale, item);
                }
                item = ModItems.DRAGON_SCALE_CHESTPLATE.get(type);
                if (item != null) {
                    dragonScaleChestplate(consumer, scale, item);
                }
                item = ModItems.DRAGON_SCALE_HELMET.get(type);
                if (item != null) {
                    dragonScaleHelmet(consumer, scale, item);
                }
                item = ModItems.DRAGON_SCALE_HOE.get(type);
                if (item != null) {
                    dragonScaleHoe(consumer, scale, item);
                }
                item = ModItems.DRAGON_SCALE_LEGGINGS.get(type);
                if (item != null) {
                    dragonScaleLeggings(consumer, scale, item);
                }
                item = ModItems.DRAGON_SCALE_PICKAXE.get(type);
                if (item != null) {
                    dragonScalePickaxe(consumer, scale, item);
                }
                item = ModItems.DRAGON_SCALE_SHOVEL.get(type);
                if (item != null) {
                    dragonScaleShovel(consumer, scale, item);
                }
                item = ModItems.DRAGON_SCALE_SHIELD.get(type);
                if (item != null) {
                    dragonScaleShield(consumer, scale, item);
                }
                item = ModItems.DRAGON_SCALE_SWORD.get(type);
                if (item != null) {
                    dragonScaleSword(consumer, scale, item);
                }
            }
        }
    }

    private static void dragonScaleAxe(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        ShapedRecipeBuilder.shaped(result).define('#', Tags.Items.RODS_WOODEN).define('X', scales).pattern("XX").pattern("X#").pattern(" #").unlockedBy("has_dragon_scales", has(DMItemTags.DRAGON_SCALES)).save(consumer);
    }

    private static void dragonScaleBoots(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        ShapedRecipeBuilder.shaped(result).define('X', scales).pattern("X X").pattern("X X").unlockedBy("has_dragon_scales", has(DMItemTags.DRAGON_SCALES)).save(consumer);
    }

    private static void dragonScaleBow(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        ShapedRecipeBuilder.shaped(result).define('#', scales).define('X', Items.STRING).pattern(" #X").pattern("# X").pattern(" #X").unlockedBy("has_dragon_scales", has(DMItemTags.DRAGON_SCALES)).save(consumer);
    }

    private static void dragonScaleChestplate(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        ShapedRecipeBuilder.shaped(result).define('X', scales).pattern("X X").pattern("XXX").pattern("XXX").unlockedBy("has_dragon_scales", has(DMItemTags.DRAGON_SCALES)).save(consumer);
    }

    private static void dragonScaleHelmet(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        ShapedRecipeBuilder.shaped(result).define('X', scales).pattern("XXX").pattern("X X").unlockedBy("has_dragon_scales", has(DMItemTags.DRAGON_SCALES)).save(consumer);
    }

    private static void dragonScaleHoe(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        ShapedRecipeBuilder.shaped(result).define('#', Tags.Items.RODS_WOODEN).define('X', scales).pattern("XX").pattern(" #").pattern(" #").unlockedBy("has_dragon_scales", has(DMItemTags.DRAGON_SCALES)).save(consumer);
    }

    private static void dragonScaleLeggings(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        ShapedRecipeBuilder.shaped(result).define('X', scales).pattern("XXX").pattern("X X").pattern("X X").unlockedBy("has_dragon_scales", has(DMItemTags.DRAGON_SCALES)).save(consumer);
    }

    private static void dragonScalePickaxe(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        ShapedRecipeBuilder.shaped(result).define('#', Tags.Items.RODS_WOODEN).define('X', scales).pattern("XXX").pattern(" # ").pattern(" # ").unlockedBy("has_dragon_scales", has(DMItemTags.DRAGON_SCALES)).save(consumer);
    }

    private static void dragonScaleShield(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        ShapedRecipeBuilder.shaped(result).define('X', Tags.Items.INGOTS_IRON).define('W', scales).pattern("WXW").pattern("WWW").pattern(" W ").unlockedBy("has_dragon_scales", has(DMItemTags.DRAGON_SCALES)).save(consumer);
    }

    private static void dragonScaleShovel(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        ShapedRecipeBuilder.shaped(result).define('#', Tags.Items.RODS_WOODEN).define('X', scales).pattern("X").pattern("#").pattern("#").unlockedBy("has_dragon_scales", has(DMItemTags.DRAGON_SCALES)).save(consumer);
    }

    private static void dragonScaleSword(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        ShapedRecipeBuilder.shaped(result).define('#', Tags.Items.RODS_WOODEN).define('X', scales).pattern("X").pattern("X").pattern("#").unlockedBy("has_dragon_scales", has(DMItemTags.DRAGON_SCALES)).save(consumer);
    }

    private static void netheriteSmithing(Consumer<IFinishedRecipe> consumer, Item ingredient, Item result) {
        SmithingRecipeBuilder.smithing(Ingredient.of(ingredient), Ingredient.of(Items.NETHERITE_INGOT), result).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(consumer, prefix(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result.asItem())).getPath() + "_smithing"));
    }
}