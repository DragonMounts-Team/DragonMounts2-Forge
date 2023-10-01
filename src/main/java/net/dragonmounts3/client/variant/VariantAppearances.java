package net.dragonmounts3.client.variant;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.init.DragonVariants;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;

import static net.dragonmounts3.DragonMounts.MOD_ID;
import static net.dragonmounts3.DragonMounts.prefix;
import static net.dragonmounts3.client.variant.VariantAppearance.TEXTURES_ROOT;

public class VariantAppearances {
    public static AgeableAppearance createAgeableAppearance(String namespace, String path, boolean hasTailHorns, boolean hasSideTailScale) {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + path);
        int length = builder.length();
        return new AgeableAppearance(
                new ResourceLocation(namespace, builder.append("/body.png").toString()),
                new ResourceLocation(namespace, builder.insert(length, "/baby").toString()),
                new ResourceLocation(namespace, builder.replace(length + 6, length + 10, "glow").toString()),
                new ResourceLocation(namespace, builder.delete(length, length + 5).toString()),
                hasTailHorns,
                hasSideTailScale
        );
    }

    public static VariantAppearance createDefaultAppearance(String namespace, String path, boolean hasTailHorns, boolean hasSideTailScale) {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + path);
        int length = builder.length();
        return new DefaultAppearance(
                new ResourceLocation(namespace, builder.append("/body.png").toString()),
                new ResourceLocation(namespace, builder.replace(length + 6, length + 10, "glow").toString()),
                hasTailHorns,
                hasSideTailScale
        );
    }

    public static final VariantAppearance AETHER_FEMALE;
    public static final VariantAppearance AETHER_MALE;
    public static final VariantAppearance AETHER_NEW;
    public static final VariantAppearance ENCHANT_FEMALE = createAgeableAppearance(MOD_ID, "enchant/female", false, false);
    public static final VariantAppearance ENCHANT_MALE = createAgeableAppearance(MOD_ID, "enchant/male", false, false);
    public static final VariantAppearance ENDER_FEMALE;
    public static final VariantAppearance ENDER_MALE;
    public static final VariantAppearance FIRE_FEMALE = createAgeableAppearance(MOD_ID, "fire/female", false, false);
    public static final VariantAppearance FIRE_MALE = createAgeableAppearance(MOD_ID, "fire/male", false, false);
    public static final VariantAppearance FOREST_FEMALE;
    public static final VariantAppearance FOREST_MALE;
    public static final VariantAppearance FOREST_DRY_FEMALE;
    public static final VariantAppearance FOREST_DRY_MALE;
    public static final VariantAppearance FOREST_TAIGA_FEMALE;
    public static final VariantAppearance FOREST_TAIGA_MALE;
    public static final VariantAppearance ICE_FEMALE;
    public static final VariantAppearance ICE_MALE;
    public static final VariantAppearance MOONLIGHT_FEMALE = createDefaultAppearance(MOD_ID, "moonlight/female", false, false);
    public static final VariantAppearance MOONLIGHT_MALE = createDefaultAppearance(MOD_ID, "moonlight/male", false, false);
    public static final VariantAppearance NETHER_FEMALE = createAgeableAppearance(MOD_ID, "nether/female", false, false);
    public static final VariantAppearance NETHER_MALE = createAgeableAppearance(MOD_ID, "nether/male", false, false);
    public static final VariantAppearance SKELETON_FEMALE;
    public static final VariantAppearance SKELETON_MALE;
    public static final VariantAppearance STORM_FEMALE;
    public static final VariantAppearance STORM_MALE = createAgeableAppearance(MOD_ID, "storm/male", true, false);
    public static final VariantAppearance SUNLIGHT_FEMALE = createAgeableAppearance(MOD_ID, "sunlight/female", false, false);
    public static final VariantAppearance SUNLIGHT_MALE = createAgeableAppearance(MOD_ID, "sunlight/male", false, false);
    public static final VariantAppearance TERRA_FEMALE = createAgeableAppearance(MOD_ID, "terra/female", false, false);
    public static final VariantAppearance TERRA_MALE = createAgeableAppearance(MOD_ID, "terra/male", false, false);
    public static final VariantAppearance WATER_FEMALE = createAgeableAppearance(MOD_ID, "water/female", true, false);
    public static final VariantAppearance WATER_MALE = createAgeableAppearance(MOD_ID, "water/male", true, false);
    public static final VariantAppearance WITHER_FEMALE = createDefaultAppearance(MOD_ID, "wither/female", true, false);
    public static final VariantAppearance WITHER_MALE = createDefaultAppearance(MOD_ID, "wither/male", true, false);
    public static final VariantAppearance ZOMBIE_FEMALE;
    public static final VariantAppearance ZOMBIE_MALE;
    public static final VariantAppearance SCULK = new VariantAppearance(1.6F) {
        public final ResourceLocation body = new ResourceLocation(MOD_ID, TEXTURES_ROOT + "sculk/body.png");
        public final RenderType decal = RenderType.entityDecal(this.body);

        @Override
        public boolean hasTailHorns(TameableDragonEntity dragon) {
            return false;
        }

        @Override
        public boolean hasSideTailScale(TameableDragonEntity dragon) {
            return false;
        }

        @Override
        public ResourceLocation getBody(TameableDragonEntity dragon) {
            return this.body;
        }

        @Override
        public RenderType getGlow(TameableDragonEntity dragon) {
            return VariantAppearances.ENDER_FEMALE.getGlow(dragon);
        }

        @Override
        public RenderType getDecal(TameableDragonEntity dragon) {
            return this.decal;
        }

        @Override
        public RenderType getGlowDecal(TameableDragonEntity dragon) {
            return VariantAppearances.ENDER_FEMALE.getGlowDecal(dragon);
        }
    };

    static {
        ResourceLocation glow = prefix(TEXTURES_ROOT + "aether/glow.png");
        ResourceLocation babyGlow = prefix(TEXTURES_ROOT + "aether/baby_glow.png");
        AETHER_FEMALE = new AgeableAppearance(prefix(TEXTURES_ROOT + "aether/female/body.png"), prefix(TEXTURES_ROOT + "aether/female/baby_body.png"), babyGlow, glow, false, false);
        AETHER_MALE = new AgeableAppearance(prefix(TEXTURES_ROOT + "aether/male/body.png"), prefix(TEXTURES_ROOT + "aether/male/baby_body.png"), babyGlow, glow, false, false);
        AETHER_NEW = new DefaultAppearance(prefix(TEXTURES_ROOT + "aether/new/body.png"), prefix(TEXTURES_ROOT + "aether/new/glow.png"), false, false);
    }

    static {
        ResourceLocation glow = prefix(TEXTURES_ROOT + "ender/glow.png");
        ENDER_FEMALE = new DefaultAppearance(prefix(TEXTURES_ROOT + "ender/female/body.png"), glow, false, false);
        ENDER_MALE = new AgeableAppearance(prefix(TEXTURES_ROOT + "ender/male/body.png"), prefix(TEXTURES_ROOT + "ender/male/baby_body.png"), glow, glow, false, false);
    }

    static {
        ResourceLocation glow = prefix(TEXTURES_ROOT + "forest/glow.png");
        ResourceLocation babyBody = prefix(TEXTURES_ROOT + "forest/forest/baby_body.png");
        FOREST_FEMALE = new AgeableAppearance(prefix(TEXTURES_ROOT + "forest/forest/female_body.png"), babyBody, glow, glow, false, false);
        FOREST_MALE = new AgeableAppearance(prefix(TEXTURES_ROOT + "forest/forest/male_body.png"), babyBody, glow, glow, false, false);
        babyBody = prefix(TEXTURES_ROOT + "forest/dry/baby_body.png");
        FOREST_DRY_FEMALE = new AgeableAppearance(prefix(TEXTURES_ROOT + "forest/dry/female_body.png"), babyBody, glow, glow, false, false);
        FOREST_DRY_MALE = new AgeableAppearance(prefix(TEXTURES_ROOT + "forest/dry/male_body.png"), babyBody, glow, glow, false, false);
        FOREST_TAIGA_FEMALE = new DefaultAppearance(prefix(TEXTURES_ROOT + "forest/taiga/female_body.png"), glow, false, false);
        FOREST_TAIGA_MALE = new DefaultAppearance(prefix(TEXTURES_ROOT + "forest/taiga/male_body.png"), glow, false, false);
    }

    static {
        ResourceLocation babyGlow = prefix(TEXTURES_ROOT + "ice/baby_glow.png");
        ResourceLocation maleBody = prefix(TEXTURES_ROOT + "ice/male/body.png");
        ICE_FEMALE = new AgeableAppearance(
                prefix(TEXTURES_ROOT + "ice/female/body.png"),
                prefix(TEXTURES_ROOT + "ice/female/baby_body.png"),
                babyGlow,
                prefix(TEXTURES_ROOT + "ice/female/glow.png"),
                false,
                true
        );
        ICE_MALE = new AgeableAppearance(maleBody, maleBody, babyGlow, prefix(TEXTURES_ROOT + "ice/male/glow.png"), false, true);
    }

    static {
        ResourceLocation glow = prefix(TEXTURES_ROOT + "skeleton/glow.png");
        SKELETON_FEMALE = new DefaultAppearance(prefix(TEXTURES_ROOT + "skeleton/female_body.png"), glow, false, false);
        SKELETON_MALE = new DefaultAppearance(prefix(TEXTURES_ROOT + "skeleton/male_body.png"), glow, false, false);
    }

    static {
        ResourceLocation body = prefix(TEXTURES_ROOT + "storm/female/body.png");
        STORM_FEMALE = new AgeableAppearance(body, body, prefix(TEXTURES_ROOT + "storm/female/baby_glow.png"), prefix(TEXTURES_ROOT + "storm/female/glow.png"), true, false);
    }

    static {
        ResourceLocation body = prefix(TEXTURES_ROOT + "zombie/body.png");
        ZOMBIE_FEMALE = new DefaultAppearance(body, prefix(TEXTURES_ROOT + "zombie/female_glow.png"), false, false);
        ZOMBIE_MALE = new DefaultAppearance(body, prefix(TEXTURES_ROOT + "zombie/male_glow.png"), false, false);
    }

    public static void bindAppearance() {
        DragonVariants.AETHER_FEMALE.setAppearance(AETHER_FEMALE);
        DragonVariants.AETHER_MALE.setAppearance(AETHER_MALE);
        DragonVariants.AETHER_NEW.setAppearance(AETHER_NEW);
        DragonVariants.ENCHANT_FEMALE.setAppearance(ENCHANT_FEMALE);
        DragonVariants.ENCHANT_MALE.setAppearance(ENCHANT_MALE);
        DragonVariants.ENDER_FEMALE.setAppearance(ENDER_FEMALE);
        DragonVariants.ENDER_MALE.setAppearance(ENDER_MALE);
        DragonVariants.FIRE_FEMALE.setAppearance(FIRE_FEMALE);
        DragonVariants.FIRE_MALE.setAppearance(FIRE_MALE);
        DragonVariants.FOREST_FEMALE.setAppearance(FOREST_FEMALE);
        DragonVariants.FOREST_MALE.setAppearance(FOREST_MALE);
        DragonVariants.FOREST_DRY_FEMALE.setAppearance(FOREST_DRY_FEMALE);
        DragonVariants.FOREST_DRY_MALE.setAppearance(FOREST_DRY_MALE);
        DragonVariants.FOREST_TAIGA_FEMALE.setAppearance(FOREST_TAIGA_FEMALE);
        DragonVariants.FOREST_TAIGA_MALE.setAppearance(FOREST_TAIGA_MALE);
        DragonVariants.ICE_FEMALE.setAppearance(ICE_FEMALE);
        DragonVariants.ICE_MALE.setAppearance(ICE_MALE);
        DragonVariants.MOONLIGHT_FEMALE.setAppearance(MOONLIGHT_FEMALE);
        DragonVariants.MOONLIGHT_MALE.setAppearance(MOONLIGHT_MALE);
        DragonVariants.NETHER_FEMALE.setAppearance(NETHER_FEMALE);
        DragonVariants.NETHER_MALE.setAppearance(NETHER_MALE);
        DragonVariants.SCULK.setAppearance(SCULK);
        DragonVariants.SKELETON_FEMALE.setAppearance(SKELETON_FEMALE);
        DragonVariants.SKELETON_MALE.setAppearance(SKELETON_MALE);
        DragonVariants.STORM_FEMALE.setAppearance(STORM_FEMALE);
        DragonVariants.STORM_MALE.setAppearance(STORM_MALE);
        DragonVariants.SUNLIGHT_FEMALE.setAppearance(SUNLIGHT_FEMALE);
        DragonVariants.SUNLIGHT_MALE.setAppearance(SUNLIGHT_MALE);
        DragonVariants.TERRA_FEMALE.setAppearance(TERRA_FEMALE);
        DragonVariants.TERRA_MALE.setAppearance(TERRA_MALE);
        DragonVariants.WATER_FEMALE.setAppearance(WATER_FEMALE);
        DragonVariants.WATER_MALE.setAppearance(WATER_MALE);
        DragonVariants.WITHER_FEMALE.setAppearance(WITHER_FEMALE);
        DragonVariants.WITHER_MALE.setAppearance(WITHER_MALE);
        DragonVariants.ZOMBIE_FEMALE.setAppearance(ZOMBIE_FEMALE);
        DragonVariants.ZOMBIE_MALE.setAppearance(ZOMBIE_MALE);
    }
}
