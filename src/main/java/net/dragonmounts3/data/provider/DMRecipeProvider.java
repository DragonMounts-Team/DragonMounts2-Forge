package net.dragonmounts3.data.provider;

import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.data.tags.DMItemTags;
import net.dragonmounts3.entity.carriage.CarriageType;
import net.dragonmounts3.init.DMBlocks;
import net.dragonmounts3.init.DMItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.SmithingRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Consumer;

import static net.dragonmounts3.DragonMounts.prefix;
import static net.minecraft.data.CookingRecipeBuilder.blasting;
import static net.minecraft.data.CookingRecipeBuilder.smelting;
import static net.minecraft.data.ShapedRecipeBuilder.shaped;
import static net.minecraft.data.SmithingRecipeBuilder.smithing;

public class DMRecipeProvider extends RecipeProvider {

    public DMRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        smelting(Ingredient.of(DMItems.IRON_DRAGON_ARMOR.get()), Items.IRON_INGOT, 0.1f, 200).unlockedBy("has_armor", has(DMItems.IRON_DRAGON_ARMOR.get())).save(consumer, prefix("iron_ingot_form_smelting"));
        smelting(Ingredient.of(DMItems.GOLDEN_DRAGON_ARMOR.get()), Items.GOLD_INGOT, 0.1f, 200).unlockedBy("has_armor", has(DMItems.GOLDEN_DRAGON_ARMOR.get())).save(consumer, prefix("gold_ingot_form_smelting"));
        blasting(Ingredient.of(DMItems.IRON_DRAGON_ARMOR.get()), Items.IRON_INGOT, 0.1f, 100).unlockedBy("has_armor", has(DMItems.IRON_DRAGON_ARMOR.get())).save(consumer, prefix("iron_ingot_form_blasting"));
        blasting(Ingredient.of(DMItems.GOLDEN_DRAGON_ARMOR.get()), Items.GOLD_INGOT, 0.1f, 100).unlockedBy("has_armor", has(DMItems.GOLDEN_DRAGON_ARMOR.get())).save(consumer, prefix("gold_ingot_form_blasting"));
        dragonArmor(consumer, Tags.Items.INGOTS_IRON, Tags.Items.STORAGE_BLOCKS_IRON, DMItems.IRON_DRAGON_ARMOR.get());
        dragonArmor(consumer, Tags.Items.INGOTS_GOLD, Tags.Items.STORAGE_BLOCKS_GOLD, DMItems.GOLDEN_DRAGON_ARMOR.get());
        dragonArmor(consumer, Tags.Items.GEMS_EMERALD, Tags.Items.STORAGE_BLOCKS_EMERALD, DMItems.EMERALD_DRAGON_ARMOR.get());
        dragonArmor(consumer, Tags.Items.GEMS_DIAMOND, Tags.Items.STORAGE_BLOCKS_DIAMOND, DMItems.DIAMOND_DRAGON_ARMOR.get());
        netheriteBlockSmithingBuilder(DMItems.DIAMOND_DRAGON_ARMOR.get(), DMItems.NETHERITE_DRAGON_ARMOR.get()).save(consumer, prefix("netherite_dragon_armor_from_diamond"));
        netheriteBlockSmithingBuilder(DMItems.EMERALD_DRAGON_ARMOR.get(), DMItems.NETHERITE_DRAGON_ARMOR.get()).save(consumer, prefix("netherite_dragon_armor_from_emerald"));
        netheriteIngotSmithingBuilder(DMItems.DIAMOND_SHEARS.get(), DMItems.NETHERITE_SHEARS.get()).save(consumer, DMItems.NETHERITE_SHEARS.getId());
        for (DragonType type : DragonType.values()) {
            Item scale = DMItems.DRAGON_SCALES.get(type);
            if (scale != null) {
                dragonScaleAxe(consumer, scale, DMItems.DRAGON_SCALE_AXE.get(type));
                dragonScaleBoots(consumer, scale, DMItems.DRAGON_SCALE_BOOTS.get(type));
                dragonScaleBow(consumer, scale, DMItems.DRAGON_SCALE_BOW.get(type));
                dragonScaleChestplate(consumer, scale, DMItems.DRAGON_SCALE_CHESTPLATE.get(type));
                dragonScaleHelmet(consumer, scale, DMItems.DRAGON_SCALE_HELMET.get(type));
                dragonScaleHoe(consumer, scale, DMItems.DRAGON_SCALE_HOE.get(type));
                dragonScaleLeggings(consumer, scale, DMItems.DRAGON_SCALE_LEGGINGS.get(type));
                dragonScalePickaxe(consumer, scale, DMItems.DRAGON_SCALE_PICKAXE.get(type));
                dragonScaleShovel(consumer, scale, DMItems.DRAGON_SCALE_SHOVEL.get(type));
                dragonScaleShield(consumer, scale, DMItems.DRAGON_SCALE_SHIELD.get(type));
                dragonScaleSword(consumer, scale, DMItems.DRAGON_SCALE_SWORD.get(type));
            }
        }
        for (CarriageType type : CarriageType.values()) {
            Item carriage = DMItems.CARRIAGE.get(type);
            if (carriage != null) {
                Block planks = type.getPlanks();
                shaped(carriage)
                        .define('X', planks)
                        .define('#', Tags.Items.LEATHER)
                        .pattern("X X")
                        .pattern("###")
                        .unlockedBy("has_planks", has(ItemTags.PLANKS))
                        .unlockedBy("has_leather", has(Tags.Items.LEATHER))
                        .save(consumer);
            }
        }
        shaped(DMItems.DIAMOND_SHEARS.get())
                .define('X', Tags.Items.GEMS_DIAMOND)
                .pattern(" X")
                .pattern("X ")
                .unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND))
                .save(consumer);
        shaped(Items.DISPENSER)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('#', Tags.Items.COBBLESTONE)
                .define('X', DMItemTags.DRAGON_SCALE_BOW)
                .pattern("###")
                .pattern("#X#")
                .pattern("#R#")
                .unlockedBy("has_bow", has(DMItemTags.DRAGON_SCALE_BOW))
                .save(consumer, prefix(Objects.requireNonNull(Items.DISPENSER.getRegistryName()).getPath()));
        shaped(DMItems.DRAGON_AMULET.get())
                .define('#', Tags.Items.STRING)
                .define('Y', Tags.Items.COBBLESTONE)
                .define('X', Tags.Items.ENDER_PEARLS)
                .pattern(" Y ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_pearls", has(Tags.Items.ENDER_PEARLS))
                .save(consumer);
        shaped(DMBlocks.DRAGON_NEST.get())
                .define('X', Tags.Items.RODS_WOODEN)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_sticks", has(Tags.Items.RODS_WOODEN))
                .save(consumer);
        shaped(DMItems.DRAGON_WHISTLE.get())
                .define('P', Tags.Items.RODS_WOODEN)
                .define('#', Tags.Items.ENDER_PEARLS)
                .define('X', Tags.Items.STRING)
                .pattern("P#")
                .pattern("#X")
                .unlockedBy("has_pearls", has(Tags.Items.ENDER_PEARLS))
                .save(consumer);
        shaped(Items.SADDLE)
                .define('X', Tags.Items.LEATHER)
                .define('#', Tags.Items.INGOTS_IRON)
                .pattern("XXX")
                .pattern("X#X")
                .unlockedBy("easter_egg", has(Items.SADDLE))
                .save(consumer, prefix("easter_egg"));
    }

    private static void dragonArmor(Consumer<IFinishedRecipe> consumer, ITag<Item> ingot, ITag<Item> block, Item result) {
        if (result != null)
            shaped(result).define('#', ingot).define('X', block).pattern("X #").pattern(" XX").pattern("## ").unlockedBy("has_ingot", has(ingot)).unlockedBy("has_block", has(block)).save(consumer);
    }

    private static void dragonScaleAxe(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        if (result != null)
            shaped(result).define('#', Tags.Items.RODS_WOODEN).define('X', scales).pattern("XX").pattern("X#").pattern(" #").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
    }

    private static void dragonScaleBoots(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        if (result != null)
            shaped(result).define('X', scales).pattern("X X").pattern("X X").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
    }

    private static void dragonScaleBow(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        if (result != null)
            shaped(result).define('#', scales).define('X', Tags.Items.STRING).pattern(" #X").pattern("# X").pattern(" #X").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
    }

    private static void dragonScaleChestplate(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        if (result != null)
            shaped(result).define('X', scales).pattern("X X").pattern("XXX").pattern("XXX").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
    }

    private static void dragonScaleHelmet(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        if (result != null)
            shaped(result).define('X', scales).pattern("XXX").pattern("X X").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
    }

    private static void dragonScaleHoe(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        if (result != null)
            shaped(result).define('#', Tags.Items.RODS_WOODEN).define('X', scales).pattern("XX").pattern(" #").pattern(" #").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
    }

    private static void dragonScaleLeggings(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        if (result != null)
            shaped(result).define('X', scales).pattern("XXX").pattern("X X").pattern("X X").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
    }

    private static void dragonScalePickaxe(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        if (result != null)
            shaped(result).define('#', Tags.Items.RODS_WOODEN).define('X', scales).pattern("XXX").pattern(" # ").pattern(" # ").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
    }

    private static void dragonScaleShield(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        if (result != null)
            shaped(result).define('X', Tags.Items.INGOTS_IRON).define('W', scales).pattern("WXW").pattern("WWW").pattern(" W ").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
    }

    private static void dragonScaleShovel(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        if (result != null)
            shaped(result).define('#', Tags.Items.RODS_WOODEN).define('X', scales).pattern("X").pattern("#").pattern("#").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
    }

    private static void dragonScaleSword(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        if (result != null)
            shaped(result).define('#', Tags.Items.RODS_WOODEN).define('X', scales).pattern("X").pattern("X").pattern("#").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
    }

    private static SmithingRecipeBuilder netheriteIngotSmithingBuilder(Item base, Item result) {
        return smithing(Ingredient.of(base), Ingredient.of(Tags.Items.INGOTS_NETHERITE), result)
                .unlocks("has_ingredient", has(base))
                .unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT));
    }

    private static SmithingRecipeBuilder netheriteBlockSmithingBuilder(Item base, Item result) {
        return smithing(Ingredient.of(base), Ingredient.of(Tags.Items.STORAGE_BLOCKS_NETHERITE), result)
                .unlocks("has_ingredient", has(base))
                .unlocks("has_block_of_netherite", has(Items.NETHERITE_INGOT));
    }
}