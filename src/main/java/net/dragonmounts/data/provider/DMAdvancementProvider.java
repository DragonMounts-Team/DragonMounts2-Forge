package net.dragonmounts.data.provider;

import net.dragonmounts.data.tags.DMItemTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Blocks;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

import static net.minecraft.advancements.Advancement.Builder.advancement;
import static net.minecraft.advancements.criterion.InventoryChangeTrigger.Instance.hasItems;

public class DMAdvancementProvider extends AdvancementProvider {
    public DMAdvancementProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, helper);
    }

    @Override
    protected void registerAdvancements(@Nonnull Consumer<Advancement> consumer, @Nonnull ExistingFileHelper helper) {
        advancement().parent(advancement().build(new ResourceLocation("minecraft", "end/kill_dragon"))).display(
                Blocks.DRAGON_EGG,
                new TranslationTextComponent("advancements.end.dragon_egg.title"),
                new TranslationTextComponent("advancements.end.dragon_egg.description"),
                null,
                FrameType.GOAL,
                true,
                true,
                false
        ).addCriterion("dragon_egg", hasItems(ItemPredicate.Builder.item().of(DMItemTags.DRAGON_EGGS).build())).save(consumer, "minecraft:end/dragon_egg");
    }
}
