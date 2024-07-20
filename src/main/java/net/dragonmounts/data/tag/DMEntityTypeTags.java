package net.dragonmounts.data.tag;

import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.Tags;

import static net.dragonmounts.DragonMounts.makeId;

public class DMEntityTypeTags {
    public static final Tags.IOptionalNamedTag<EntityType<?>> DRAGONS = EntityTypeTags.createOptional(makeId("dragons"));
}
