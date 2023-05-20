package net.dragonmounts3.client;

import net.minecraft.util.ResourceLocation;

import static net.dragonmounts3.DragonMounts.prefix;

public class DragonResources {
    public final static String TEXTURES_ROOT = "textures/entities/dragon/";
    public final static ResourceLocation DEFAULT_CHEST = prefix(TEXTURES_ROOT + "chest.png");
    public final static ResourceLocation DEFAULT_SADDLE = prefix(TEXTURES_ROOT + "saddle.png");
    public final ResourceLocation chest;
    public final ResourceLocation saddle;
    public final ResourceLocation body;
    public final ResourceLocation glow;

    public DragonResources(String name) {
        this.chest = DEFAULT_CHEST;
        this.saddle = DEFAULT_SADDLE;
        this.body = prefix(TEXTURES_ROOT + name + "/body.png");
        this.glow = prefix(TEXTURES_ROOT + name + "/glow.png");
    }
}
