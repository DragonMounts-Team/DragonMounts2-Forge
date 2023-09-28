package net.dragonmounts3.init;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.registry.DragonType;
import net.dragonmounts3.registry.DragonVariant;
import net.dragonmounts3.variant.AgeableVariant;
import net.dragonmounts3.variant.DefaultVariant;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static net.dragonmounts3.DragonMounts.MOD_ID;
import static net.dragonmounts3.DragonMounts.prefix;
import static net.dragonmounts3.registry.DragonVariant.TEXTURES_ROOT;

public class DragonVariants {
    public static DragonVariant createDefaultVariant(String namespace, String path, DragonType type, boolean hasTailHorns, boolean hasSideTailScale) {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + path);
        int length = builder.length();
        return new DefaultVariant(
                type,
                new ResourceLocation(namespace, builder.append("/body.png").toString()),
                new ResourceLocation(namespace, builder.replace(length + 6, length + 10, "glow").toString()),
                hasTailHorns,
                hasSideTailScale
        ).setRegistryName(namespace, path.replace('/', '_'));
    }

    public static AgeableVariant createAgeableVariant(String namespace, String path, DragonType type, boolean hasTailHorns, boolean hasSideTailScale) {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + path);
        int length = builder.length();
        return (AgeableVariant) new AgeableVariant(
                type,
                new ResourceLocation(namespace, builder.append("/body.png").toString()),
                new ResourceLocation(namespace, builder.insert(length, "/baby").toString()),
                new ResourceLocation(namespace, builder.replace(length + 6, length + 10, "glow").toString()),
                new ResourceLocation(namespace, builder.delete(length, length + 5).toString()),
                hasTailHorns,
                hasSideTailScale
        ).setRegistryName(namespace, path.replace('/', '_'));
    }

    public static final DragonVariant AETHER_FEMALE;
    public static final DragonVariant AETHER_MALE;
    public static final DragonVariant AETHER_NEW;
    public static final DragonVariant ENCHANT_FEMALE = createAgeableVariant(MOD_ID, "enchant/female", DragonTypes.ENCHANT, false, false);
    public static final DragonVariant ENCHANT_MALE = createAgeableVariant(MOD_ID, "enchant/male", DragonTypes.ENCHANT, false, false);
    public static final DragonVariant ENDER_FEMALE;
    public static final DragonVariant ENDER_MALE;
    public static final DragonVariant FIRE_FEMALE = createAgeableVariant(MOD_ID, "fire/female", DragonTypes.FIRE, false, false);
    public static final DragonVariant FIRE_MALE = createAgeableVariant(MOD_ID, "fire/male", DragonTypes.FIRE, false, false);
    public static final DragonVariant FOREST_FEMALE;
    public static final DragonVariant FOREST_MALE;
    public static final DragonVariant FOREST_DRY_FEMALE;
    public static final DragonVariant FOREST_DRY_MALE;
    public static final DragonVariant FOREST_TAIGA_FEMALE;
    public static final DragonVariant FOREST_TAIGA_MALE;
    public static final DragonVariant ICE_FEMALE;
    public static final DragonVariant ICE_MALE;
    public static final DragonVariant MOONLIGHT_FEMALE = createDefaultVariant(MOD_ID, "moonlight/female", DragonTypes.MOONLIGHT, false, false);
    public static final DragonVariant MOONLIGHT_MALE = createDefaultVariant(MOD_ID, "moonlight/male", DragonTypes.MOONLIGHT, false, false);
    public static final DragonVariant NETHER_FEMALE = createAgeableVariant(MOD_ID, "nether/female", DragonTypes.NETHER, false, false);
    public static final DragonVariant NETHER_MALE = createAgeableVariant(MOD_ID, "nether/male", DragonTypes.NETHER, false, false);
    public static final DragonVariant SKELETON_FEMALE;
    public static final DragonVariant SKELETON_MALE;
    public static final DragonVariant STORM_FEMALE;
    public static final DragonVariant STORM_MALE = createAgeableVariant(MOD_ID, "storm/male", DragonTypes.STORM, true, false);
    public static final DragonVariant SUNLIGHT_FEMALE = createAgeableVariant(MOD_ID, "sunlight/female", DragonTypes.SUNLIGHT, false, false);
    public static final DragonVariant SUNLIGHT_MALE = createAgeableVariant(MOD_ID, "sunlight/male", DragonTypes.SUNLIGHT, false, false);
    public static final DragonVariant TERRA_FEMALE = createAgeableVariant(MOD_ID, "terra/female", DragonTypes.TERRA, false, false);
    public static final DragonVariant TERRA_MALE = createAgeableVariant(MOD_ID, "terra/male", DragonTypes.TERRA, false, false);
    public static final DragonVariant WATER_FEMALE = createAgeableVariant(MOD_ID, "water/female", DragonTypes.WATER, true, false);
    public static final DragonVariant WATER_MALE = createAgeableVariant(MOD_ID, "water/male", DragonTypes.WATER, true, false);
    public static final DragonVariant WITHER_FEMALE = createDefaultVariant(MOD_ID, "wither/female", DragonTypes.WITHER, true, false);
    public static final DragonVariant WITHER_MALE = createDefaultVariant(MOD_ID, "wither/male", DragonTypes.WITHER, true, false);
    public static final DragonVariant ZOMBIE_FEMALE;
    public static final DragonVariant ZOMBIE_MALE;
    public static final DragonVariant SCULK = new DragonVariant(DragonTypes.SCULK, 1.6F) {
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
            return DragonVariants.ENDER_FEMALE.getGlow(dragon);
        }

        @Override
        public RenderType getDecal(TameableDragonEntity dragon) {
            return this.decal;
        }

        @Override
        public RenderType getGlowDecal(TameableDragonEntity dragon) {
            return DragonVariants.ENDER_FEMALE.getGlowDecal(dragon);
        }
    }.setRegistryName(MOD_ID, "sculk");

    static {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + "aether/");
        int length = builder.length();
        ResourceLocation glow = prefix(builder.append("glow.png").toString());
        ResourceLocation babyGlow = prefix(builder.insert(length, "baby_").toString());
        builder.setLength(length);
        AETHER_MALE = new AgeableVariant(DragonTypes.AETHER, prefix(builder.append("male/body.png").toString()), prefix(builder.insert(length + 5, "baby_").toString()), babyGlow, glow, false, false).setRegistryName(MOD_ID, "aether_male");
        builder.setLength(length);
        AETHER_FEMALE = new AgeableVariant(DragonTypes.AETHER, prefix(builder.append("female/body.png").toString()), prefix(builder.insert(length + 7, "baby_").toString()), babyGlow, glow, false, false).setRegistryName(MOD_ID, "aether_female");
        builder.setLength(length);
        AETHER_NEW = new DefaultVariant(DragonTypes.AETHER, prefix(builder.append("new/body.png").toString()), prefix(builder.replace(length + 4, length + 8, "glow").toString()), false, false).setRegistryName(MOD_ID, "aether_new");
    }

    static {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + "ender/");
        int length = builder.length();
        ResourceLocation glow = prefix(builder.append("glow.png").toString());
        builder.setLength(length);
        ENDER_MALE = new AgeableVariant(DragonTypes.ENDER, prefix(builder.append("male/body.png").toString()), prefix(builder.insert(length + 5, "baby_").toString()), glow, glow, false, false).setRegistryName(MOD_ID, "ender_male");
        builder.setLength(length);
        ENDER_FEMALE = new DefaultVariant(DragonTypes.ENDER, prefix(builder.append("female/body.png").toString()), glow, false, false).setRegistryName(DragonVariant.DEFAULT_KEY);
    }

    static {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + "forest/");
        int length = builder.length();
        int start = length + 4;
        ResourceLocation glow = prefix(builder.append("glow.png").toString());
        builder.setLength(length);
        ResourceLocation body = prefix(builder.append("dry/baby_body.png").toString());
        FOREST_DRY_MALE = new AgeableVariant(DragonTypes.FOREST, prefix(builder.replace(start, start + 4, "male").toString()), body, glow, glow, false, false).setRegistryName(MOD_ID, "forest_dry_male");
        FOREST_DRY_FEMALE = new AgeableVariant(DragonTypes.FOREST, prefix(builder.insert(start, "fe").toString()), body, glow, glow, false, false).setRegistryName(MOD_ID, "forest_dry_female");
        builder.setLength(length);
        start = length + 6;
        body = prefix(builder.append("taiga/baby_body.png").toString());
        FOREST_TAIGA_MALE = new AgeableVariant(DragonTypes.FOREST, prefix(builder.replace(start, start + 4, "male").toString()), body, glow, glow, false, false).setRegistryName(MOD_ID, "forest_taiga_male");
        FOREST_TAIGA_FEMALE = new AgeableVariant(DragonTypes.FOREST, prefix(builder.insert(start, "fe").toString()), body, glow, glow, false, false).setRegistryName(MOD_ID, "forest_taiga_female");
        builder.setLength(length);
        body = prefix(builder.append("forest/male.png").toString());
        FOREST_MALE = new AgeableVariant(DragonTypes.FOREST, body, body, glow, glow, false, false).setRegistryName(MOD_ID, "forest_male");
        body = prefix(builder.insert(length + 7, "fe").toString());
        FOREST_FEMALE = new AgeableVariant(DragonTypes.FOREST, body, body, glow, glow, false, false).setRegistryName(MOD_ID, "forest_female");
    }

    static {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + "ice/");
        int length = builder.length();
        ResourceLocation babyGlow = prefix(builder.append("baby_glow.png").toString());
        builder.setLength(length);
        ResourceLocation maleBody = prefix(builder.append("male/body.png").toString());
        ResourceLocation femaleBody = prefix(builder.insert(length, "fe").toString());
        builder.setLength(length);
        ICE_MALE = new AgeableVariant(DragonTypes.ICE, maleBody, maleBody, babyGlow, prefix(builder.append("male/glow.png").toString()), false, true).setRegistryName(MOD_ID, "ice_male");
        ICE_FEMALE = new AgeableVariant(DragonTypes.ICE, femaleBody, femaleBody, babyGlow, prefix(builder.insert(length, "fe").toString()), false, true).setRegistryName(MOD_ID, "ice_female");
    }

    static {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + "skeleton/");
        int length = builder.length();
        ResourceLocation glow = prefix(builder.append("glow.png").toString());
        builder.setLength(length);
        SKELETON_MALE = new DefaultVariant(DragonTypes.SKELETON, prefix(builder.append("male_body.png").toString()), glow, false, false).setRegistryName(MOD_ID, "skeleton_male");
        SKELETON_FEMALE = new DefaultVariant(DragonTypes.SKELETON, prefix(builder.insert(length, "fe").toString()), glow, false, false).setRegistryName(MOD_ID, "skeleton_female");
    }

    static {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + "storm/female/");
        int length = builder.length();
        ResourceLocation body = prefix(builder.append("body.png").toString());
        builder.setLength(length);
        STORM_FEMALE = new AgeableVariant(
                DragonTypes.STORM,
                body,
                body,
                prefix(builder.append("baby_glow.png").toString()),
                prefix(builder.delete(length, length + 5).toString()),
                true,
                false
        ).setRegistryName(MOD_ID, "storm_female");
    }

    static {
        StringBuilder builder = new StringBuilder(TEXTURES_ROOT + "zombie/");
        int length = builder.length();
        ResourceLocation body = prefix(builder.append("body.png").toString());
        builder.setLength(length);
        ZOMBIE_MALE = new DefaultVariant(DragonTypes.ZOMBIE, body, prefix(builder.append("male_glow.png").toString()), false, false).setRegistryName(MOD_ID, "zombie_male");
        ZOMBIE_FEMALE = new DefaultVariant(DragonTypes.ZOMBIE, body, prefix(builder.insert(length, "fe").toString()), false, false).setRegistryName(MOD_ID, "zombie_female");
    }

    public static void register(RegistryEvent.Register<DragonVariant> event) {
        IForgeRegistry<DragonVariant> registry = event.getRegistry();
        registry.register(AETHER_FEMALE);
        registry.register(AETHER_MALE);
        registry.register(AETHER_NEW);
        registry.register(ENCHANT_FEMALE);
        registry.register(ENCHANT_MALE);
        registry.register(ENDER_FEMALE);
        registry.register(ENDER_MALE);
        registry.register(FIRE_FEMALE);
        registry.register(FIRE_MALE);
        registry.register(FOREST_DRY_FEMALE);
        registry.register(FOREST_DRY_MALE);
        registry.register(FOREST_TAIGA_FEMALE);
        registry.register(FOREST_TAIGA_MALE);
        registry.register(FOREST_FEMALE);
        registry.register(FOREST_MALE);
        registry.register(ICE_FEMALE);
        registry.register(ICE_MALE);
        registry.register(MOONLIGHT_FEMALE);
        registry.register(MOONLIGHT_MALE);
        registry.register(NETHER_FEMALE);
        registry.register(NETHER_MALE);
        registry.register(SCULK);
        registry.register(SKELETON_FEMALE);
        registry.register(SKELETON_MALE);
        registry.register(STORM_FEMALE);
        registry.register(STORM_MALE);
        registry.register(SUNLIGHT_FEMALE);
        registry.register(SUNLIGHT_MALE);
        registry.register(TERRA_FEMALE);
        registry.register(TERRA_MALE);
        registry.register(WATER_FEMALE);
        registry.register(WATER_MALE);
        registry.register(WITHER_FEMALE);
        registry.register(WITHER_MALE);
        registry.register(ZOMBIE_FEMALE);
        registry.register(ZOMBIE_MALE);
    }
}
