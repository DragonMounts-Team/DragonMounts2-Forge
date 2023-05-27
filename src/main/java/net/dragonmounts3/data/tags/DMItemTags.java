package net.dragonmounts3.data.tags;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

import static net.dragonmounts3.DragonMounts.prefix;

public class DMItemTags {
    public static final Tags.IOptionalNamedTag<Item> DRAGON_SCALE_BOW = tag("dragon_scale_bow");
    public static final Tags.IOptionalNamedTag<Item> DRAGON_SCALES = tag("dragon_scales");
    public static final Tags.IOptionalNamedTag<Item> DRAGON_FOOD = tag("dragon_food");

    private static Tags.IOptionalNamedTag<Item> tag(String name) {
        return ItemTags.createOptional(prefix(name));
    }
}
