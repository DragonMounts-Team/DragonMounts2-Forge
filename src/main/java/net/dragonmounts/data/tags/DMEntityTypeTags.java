package net.dragonmounts.data.tags;

import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.Tags;

import static net.dragonmounts.DragonMounts.prefix;

public class DMEntityTypeTags {
    public static final Tags.IOptionalNamedTag<EntityType<?>> DRAGONS = EntityTypeTags.createOptional(prefix("dragons"));
}
