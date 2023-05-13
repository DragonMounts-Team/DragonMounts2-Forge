package net.dragonmounts3.client.renderer.dragon;

import net.dragonmounts3.api.DragonType;
import net.minecraft.util.ResourceLocation;

import static net.dragonmounts3.DragonMounts.prefix;

public enum TextureManager {
    AETHER(DragonType.AETHER),
    ENCHANT(DragonType.ENCHANT),
    ENDER(DragonType.ENDER),
    FIRE(DragonType.FIRE),
    FOREST(DragonType.FOREST),
    ICE(DragonType.ICE),
    MOONLIGHT(DragonType.MOONLIGHT),
    NETHER(DragonType.NETHER),
    SCULK(DragonType.SCULK),
    SKELETON(DragonType.SKELETON),
    STORM(DragonType.STORM),
    SUNLIGHT(DragonType.SUNLIGHT),
    TERRA(DragonType.TERRA),
    WATER(DragonType.WATER),
    WITHER(DragonType.WITHER),
    ZOMBIE(DragonType.ZOMBIE);
    public final static String ROOT = "textures/entities/dragon/";
    public final static ResourceLocation CHEST = prefix(ROOT + "chest.png");
    public final static ResourceLocation SADDLE = prefix(ROOT + "saddle.png");

    public static TextureManager get(DragonType type) {
        return TextureManager.values()[type.ordinal()];
    }

    public final ResourceLocation body;
    public final ResourceLocation glow;

    TextureManager(DragonType type) {
        String name = type.getSerializedName();
        this.body = prefix(ROOT + name + "/body.png");
        this.glow = prefix(ROOT + name + "/glow.png");
    }
}
