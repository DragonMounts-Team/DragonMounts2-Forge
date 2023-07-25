package net.dragonmounts3.data.tags;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

import static net.dragonmounts3.DragonMounts.prefix;

public class DMItemTags {
    public static final Tags.IOptionalNamedTag<Item> DRAGON_EGGS = tag("dragon_eggs");
    public static final Tags.IOptionalNamedTag<Item> DRAGON_SCALE_BOWS = tag("dragon_scale_bows");
    public static final Tags.IOptionalNamedTag<Item> DRAGON_SCALES = tag("dragon_scales");
    public static final Tags.IOptionalNamedTag<Item> BATONS = tag("batons");

    private static Tags.IOptionalNamedTag<Item> tag(String name) {
        return ItemTags.createOptional(prefix(name));
    }
}
