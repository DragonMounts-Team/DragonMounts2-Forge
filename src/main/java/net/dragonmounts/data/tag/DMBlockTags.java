package net.dragonmounts.data.tag;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;

import static net.dragonmounts.DragonMounts.makeId;

public class DMBlockTags {
    public static final Tags.IOptionalNamedTag<Block> DRAGON_EGGS = tag("dragon_eggs");

    private static Tags.IOptionalNamedTag<Block> tag(String name) {
        return BlockTags.createOptional(makeId(name));
    }
}
