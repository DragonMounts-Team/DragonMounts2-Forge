package net.dragonmounts3.api.variant;

import net.minecraft.util.ResourceLocation;

import static net.dragonmounts3.DragonMounts.MOD_ID;
import static net.dragonmounts3.DragonMounts.prefix;
import static net.dragonmounts3.api.variant.AbstractVariant.TEXTURES_ROOT;

public class DragonVariants {
    public static final String DATA_PARAMETER_KEY = "Variant";
    public static final AbstractVariant AETHER_FEMALE;
    public static final AbstractVariant AETHER_MALE;
    public static final AbstractVariant AETHER_NEW;
    public static final AbstractVariant ENCHANT_FEMALE = AgeableVariant.create(MOD_ID, "enchant/female", false, false);
    public static final AbstractVariant ENCHANT_MALE = AgeableVariant.create(MOD_ID, "enchant/male", false, false);
    public static final AbstractVariant ENDER_FEMALE;
    public static final AbstractVariant ENDER_MALE;
    public static final AbstractVariant FIRE_FEMALE = AgeableVariant.create(MOD_ID, "fire/female", false, false);
    public static final AbstractVariant FIRE_MALE = AgeableVariant.create(MOD_ID, "fire/male", false, false);
    public static final AbstractVariant FOREST_FEMALE;
    public static final AbstractVariant FOREST_MALE;
    public static final AbstractVariant FOREST_DRY_FEMALE;
    public static final AbstractVariant FOREST_DRY_MALE;
    public static final AbstractVariant FOREST_TAIGA_FEMALE;
    public static final AbstractVariant FOREST_TAIGA_MALE;
    public static final AbstractVariant ICE_FEMALE;
    public static final AbstractVariant ICE_MALE;
    public static final AbstractVariant MOONLIGHT_FEMALE = DefaultVariant.create(MOD_ID, "moonlight/female", false, false);
    public static final AbstractVariant MOONLIGHT_MALE = DefaultVariant.create(MOD_ID, "moonlight/male", false, false);
    public static final AbstractVariant NETHER_FEMALE = AgeableVariant.create(MOD_ID, "nether/female", false, false);
    public static final AbstractVariant NETHER_MALE = AgeableVariant.create(MOD_ID, "nether/male", false, false);
    public static final AbstractVariant SCULK = new SculkVariant(MOD_ID, "sculk");
    public static final AbstractVariant SKELETON_FEMALE;
    public static final AbstractVariant SKELETON_MALE;
    public static final AbstractVariant STORM_FEMALE;
    public static final AbstractVariant STORM_MALE = AgeableVariant.create(MOD_ID, "storm/male", true, false);
    public static final AbstractVariant SUNLIGHT_FEMALE = AgeableVariant.create(MOD_ID, "sunlight/female", false, false);
    public static final AbstractVariant SUNLIGHT_MALE = AgeableVariant.create(MOD_ID, "sunlight/male", false, false);
    public static final AbstractVariant TERRA_FEMALE = AgeableVariant.create(MOD_ID, "terra/female", false, false);
    public static final AbstractVariant TERRA_MALE = AgeableVariant.create(MOD_ID, "terra/male", false, false);
    public static final AbstractVariant WATER_FEMALE = AgeableVariant.create(MOD_ID, "water/female", true, false);
    public static final AbstractVariant WATER_MALE = AgeableVariant.create(MOD_ID, "water/male", true, false);
    public static final AbstractVariant WITHER_FEMALE = DefaultVariant.create(MOD_ID, "wither/female", true, false);
    public static final AbstractVariant WITHER_MALE = DefaultVariant.create(MOD_ID, "wither/male", true, false);
    public static final AbstractVariant ZOMBIE_FEMALE;
    public static final AbstractVariant ZOMBIE_MALE;

    static {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + "aether/");
        int length = builder.length();
        ResourceLocation glow = prefix(builder.append("glow.png").toString());
        ResourceLocation babyGlow = prefix(builder.insert(length, "baby_").toString());
        builder.setLength(length);
        AETHER_MALE = new AgeableVariant("male", prefix(builder.append("male/body.png").toString()), prefix(builder.insert(length + 5, "baby_").toString()), babyGlow, glow, false, false);
        builder.setLength(length);
        AETHER_FEMALE = new AgeableVariant("female", prefix(builder.append("female/body.png").toString()), prefix(builder.insert(length + 7, "baby_").toString()), babyGlow, glow, false, false);
        builder.setLength(length);
        AETHER_NEW = new DefaultVariant("new", prefix(builder.append("new/body.png").toString()), prefix(builder.replace(length + 4, length + 8, "glow").toString()), false, false);
    }

    static {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + "ender/");
        int length = builder.length();
        ResourceLocation glow = prefix(builder.append("glow.png").toString());
        builder.setLength(length);
        ENDER_MALE = new AgeableVariant("male", prefix(builder.append("male/body.png").toString()), prefix(builder.insert(length + 5, "baby_").toString()), glow, glow, false, false);
        builder.setLength(length);
        ENDER_FEMALE = new DefaultVariant("female", prefix(builder.append("female/body.png").toString()), glow, false, false);
    }

    static {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + "forest/");
        int length = builder.length();
        int start = length + 4;
        ResourceLocation glow = prefix(builder.append("glow.png").toString());
        builder.setLength(length);
        ResourceLocation body = prefix(builder.append("dry/baby_body.png").toString());
        FOREST_DRY_MALE = new AgeableVariant("dry_male", prefix(builder.replace(start, start + 4, "male").toString()), body, glow, glow, false, false);
        FOREST_DRY_FEMALE = new AgeableVariant("dry_female", prefix(builder.insert(start, "fe").toString()), body, glow, glow, false, false);
        builder.setLength(length);
        start = length + 6;
        body = prefix(builder.append("taiga/baby_body.png").toString());
        FOREST_TAIGA_MALE = new AgeableVariant("taiga_male", prefix(builder.replace(start, start + 4, "male").toString()), body, glow, glow, false, false);
        FOREST_TAIGA_FEMALE = new AgeableVariant("taiga_female", prefix(builder.insert(start, "fe").toString()), body, glow, glow, false, false);
        builder.setLength(length);
        body = prefix(builder.append("forest/male.png").toString());
        FOREST_MALE = new AgeableVariant("forest_male", body, body, glow, glow, false, false);
        body = prefix(builder.insert(length + 7, "fe").toString());
        FOREST_FEMALE = new AgeableVariant("forest_female", body, body, glow, glow, false, false);
    }

    static {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + "ice/");
        int length = builder.length();
        ResourceLocation babyGlow = prefix(builder.append("baby_glow.png").toString());
        builder.setLength(length);
        ResourceLocation maleBody = prefix(builder.append("male/body.png").toString());
        ResourceLocation femaleBody = prefix(builder.insert(length, "fe").toString());
        builder.setLength(length);
        ICE_MALE = new AgeableVariant("male", maleBody, maleBody, babyGlow, prefix(builder.append("male/glow.png").toString()), false, true);
        ICE_FEMALE = new AgeableVariant("female", femaleBody, femaleBody, babyGlow, prefix(builder.insert(length, "fe").toString()), false, true);
    }

    static {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + "skeleton/");
        int length = builder.length();
        ResourceLocation glow = prefix(builder.append("glow.png").toString());
        builder.setLength(length);
        SKELETON_MALE = new DefaultVariant("male", prefix(builder.append("male_body.png").toString()), glow, false, false);
        SKELETON_FEMALE = new DefaultVariant("female", prefix(builder.insert(length, "fe").toString()), glow, false, false);
    }

    static {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + "storm/female/");
        int length = builder.length();
        ResourceLocation body = prefix(builder.append("body.png").toString());
        builder.setLength(length);
        STORM_FEMALE = new AgeableVariant(
                "female",
                body,
                body,
                prefix(builder.append("baby_glow.png").toString()),
                prefix(builder.delete(length, length + 5).toString()),
                true,
                false
        );
    }

    static {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + "zombie/");
        int length = builder.length();
        ResourceLocation body = prefix(builder.append("body.png").toString());
        builder.setLength(length);
        ZOMBIE_MALE = new DefaultVariant("male", body, prefix(builder.append("male_glow.png").toString()), false, false);
        ZOMBIE_FEMALE = new DefaultVariant("female", body, prefix(builder.insert(length, "fe").toString()), false, false);
    }
}
