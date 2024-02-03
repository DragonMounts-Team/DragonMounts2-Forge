package net.dragonmounts.data.provider;

import net.dragonmounts.api.DragonScaleArmorSuit;
import net.dragonmounts.data.tag.DMItemTags;
import net.dragonmounts.init.DMBlocks;
import net.dragonmounts.init.DMItems;
import net.dragonmounts.item.*;
import net.dragonmounts.registry.DragonType;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.SmithingRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Consumer;

import static net.dragonmounts.DragonMounts.prefix;
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
        smelting(Ingredient.of(DMItems.IRON_DRAGON_ARMOR), Items.IRON_INGOT, 0.1F, 200).unlockedBy("has_armor", has(DMItems.IRON_DRAGON_ARMOR)).save(consumer, prefix("iron_ingot_form_smelting"));
        smelting(Ingredient.of(DMItems.GOLDEN_DRAGON_ARMOR), Items.GOLD_INGOT, 0.1F, 200).unlockedBy("has_armor", has(DMItems.GOLDEN_DRAGON_ARMOR)).save(consumer, prefix("gold_ingot_form_smelting"));
        blasting(Ingredient.of(DMItems.IRON_DRAGON_ARMOR), Items.IRON_INGOT, 0.1F, 100).unlockedBy("has_armor", has(DMItems.IRON_DRAGON_ARMOR)).save(consumer, prefix("iron_ingot_form_blasting"));
        blasting(Ingredient.of(DMItems.GOLDEN_DRAGON_ARMOR), Items.GOLD_INGOT, 0.1F, 100).unlockedBy("has_armor", has(DMItems.GOLDEN_DRAGON_ARMOR)).save(consumer, prefix("gold_ingot_form_blasting"));
        dragonArmor(consumer, Tags.Items.INGOTS_IRON, Tags.Items.STORAGE_BLOCKS_IRON, DMItems.IRON_DRAGON_ARMOR);
        dragonArmor(consumer, Tags.Items.INGOTS_GOLD, Tags.Items.STORAGE_BLOCKS_GOLD, DMItems.GOLDEN_DRAGON_ARMOR);
        dragonArmor(consumer, Tags.Items.GEMS_EMERALD, Tags.Items.STORAGE_BLOCKS_EMERALD, DMItems.EMERALD_DRAGON_ARMOR);
        dragonArmor(consumer, Tags.Items.GEMS_DIAMOND, Tags.Items.STORAGE_BLOCKS_DIAMOND, DMItems.DIAMOND_DRAGON_ARMOR);
        netheriteBlockSmithingBuilder(DMItems.DIAMOND_DRAGON_ARMOR, DMItems.NETHERITE_DRAGON_ARMOR).save(consumer, prefix("netherite_dragon_armor_from_diamond"));
        netheriteBlockSmithingBuilder(DMItems.EMERALD_DRAGON_ARMOR, DMItems.NETHERITE_DRAGON_ARMOR).save(consumer, prefix("netherite_dragon_armor_from_emerald"));
        netheriteIngotSmithingBuilder(DMItems.DIAMOND_SHEARS, DMItems.NETHERITE_SHEARS).save(consumer, prefix("netherite_shears"));
        carriage(consumer, DMItems.OAK_CARRIAGE, Blocks.OAK_PLANKS);
        carriage(consumer, DMItems.SPRUCE_CARRIAGE, Blocks.SPRUCE_PLANKS);
        carriage(consumer, DMItems.BIRCH_CARRIAGE, Blocks.BIRCH_PLANKS);
        carriage(consumer, DMItems.JUNGLE_CARRIAGE, Blocks.JUNGLE_PLANKS);
        carriage(consumer, DMItems.ACACIA_CARRIAGE, Blocks.ACACIA_PLANKS);
        carriage(consumer, DMItems.DARK_OAK_CARRIAGE, Blocks.DARK_OAK_PLANKS);
        for (DragonType type : DragonType.REGISTRY)
            dragonScaleDerivatives(consumer, type);
        shaped(DMItems.DIAMOND_SHEARS)
                .define('X', Tags.Items.GEMS_DIAMOND)
                .pattern(" X")
                .pattern("X ")
                .unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND))
                .save(consumer);
        shaped(Items.DISPENSER)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('#', Tags.Items.COBBLESTONE)
                .define('X', DMItemTags.DRAGON_SCALE_BOWS)
                .pattern("###")
                .pattern("#X#")
                .pattern("#R#")
                .unlockedBy("has_bow", has(DMItemTags.DRAGON_SCALE_BOWS))
                .save(consumer, prefix(Objects.requireNonNull(Items.DISPENSER.getRegistryName()).getPath()));
        shaped(DMItems.AMULET)
                .define('#', Tags.Items.STRING)
                .define('Y', Tags.Items.COBBLESTONE)
                .define('X', Tags.Items.ENDER_PEARLS)
                .pattern(" Y ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_pearls", has(Tags.Items.ENDER_PEARLS))
                .save(consumer);
        shaped(DMBlocks.DRAGON_NEST)
                .define('X', Tags.Items.RODS_WOODEN)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_sticks", has(Tags.Items.RODS_WOODEN))
                .save(consumer);
        shaped(DMItems.DRAGON_WHISTLE)
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

    private static void carriage(Consumer<IFinishedRecipe> consumer, IItemProvider carriage, IItemProvider planks) {
        shaped(carriage)
                .define('X', planks)
                .define('#', Tags.Items.LEATHER)
                .pattern("X X")
                .pattern("###")
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .unlockedBy("has_leather", has(Tags.Items.LEATHER))
                .save(consumer);
    }

    private static void dragonScaleDerivatives(Consumer<IFinishedRecipe> consumer, DragonType type) {
        Item scale = type.getInstance(DragonScalesItem.class, null);
        if (scale != null) {
            dragonScaleAxe(consumer, scale, type.getInstance(DragonScaleAxeItem.class, null));
            dragonScaleArmors(consumer, scale, type.getInstance(DragonScaleArmorSuit.class, null));
            dragonScaleBow(consumer, scale, type.getInstance(DragonScaleBowItem.class, null));
            dragonScaleHoe(consumer, scale, type.getInstance(DragonScaleHoeItem.class, null));
            dragonScalePickaxe(consumer, scale, type.getInstance(DragonScalePickaxeItem.class, null));
            dragonScaleShovel(consumer, scale, type.getInstance(DragonScaleShovelItem.class, null));
            dragonScaleShield(consumer, scale, type.getInstance(DragonScaleShieldItem.class, null));
            dragonScaleSword(consumer, scale, type.getInstance(DragonScaleSwordItem.class, null));
        }
    }

    private static void dragonArmor(Consumer<IFinishedRecipe> consumer, ITag<Item> ingot, ITag<Item> block, Item result) {
        if (result != null)
            shaped(result).define('#', ingot).define('X', block).pattern("X #").pattern(" XX").pattern("## ").unlockedBy("has_ingot", has(ingot)).unlockedBy("has_block", has(block)).save(consumer);
    }

    private static void dragonScaleAxe(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        if (result != null)
            shaped(result).define('#', Tags.Items.RODS_WOODEN).define('X', scales).pattern("XX").pattern("X#").pattern(" #").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
    }

    private static void dragonScaleArmors(Consumer<IFinishedRecipe> consumer, Item scales, DragonScaleArmorSuit suit) {
        if (suit != null) {
            shaped(suit.helmet).define('X', scales).pattern("XXX").pattern("X X").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
            shaped(suit.chestplate).define('X', scales).pattern("X X").pattern("XXX").pattern("XXX").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
            shaped(suit.leggings).define('X', scales).pattern("XXX").pattern("X X").pattern("X X").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
            shaped(suit.boots).define('X', scales).pattern("X X").pattern("X X").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
        }
    }

    private static void dragonScaleBow(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        if (result != null)
            shaped(result).define('#', scales).define('X', Tags.Items.STRING).pattern(" #X").pattern("# X").pattern(" #X").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
    }

    private static void dragonScaleHoe(Consumer<IFinishedRecipe> consumer, Item scales, Item result) {
        if (result != null)
            shaped(result).define('#', Tags.Items.RODS_WOODEN).define('X', scales).pattern("XX").pattern(" #").pattern(" #").unlockedBy("has_dragon_scales", has(scales)).save(consumer);
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