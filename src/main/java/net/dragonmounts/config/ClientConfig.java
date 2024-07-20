package net.dragonmounts.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ClientConfig {
    private static ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ClientConfig INSTANCE = new ClientConfig(BUILDER);
    public final ForgeConfigSpec.ConfigValue<Boolean> debug;
    public final ForgeConfigSpec.ConfigValue<Double> camera_distance;
    public final ForgeConfigSpec.ConfigValue<Double> camera_offset;
    public final ForgeConfigSpec.ConfigValue<Boolean> converge_pitch_angle;
    public final ForgeConfigSpec.ConfigValue<Boolean> converge_yaw_angle;
    public final ForgeConfigSpec.ConfigValue<Boolean> hover_animation;
    public final ForgeConfigSpec.ConfigValue<Boolean> toggle_descent;
    public final ForgeConfigSpec.ConfigValue<Boolean> redirect_inventory;

    private ClientConfig(ForgeConfigSpec.Builder builder) {
        this.debug = builder
                .comment("Debug mode. You need to restart Minecraft for the change to take effect. Unless you're a developer or are told to activate it, you don't want to set this to true.")
                .define("debug", false);
        this.camera_distance = builder
                .comment("Zoom out for third person 2 while riding the the dragon and dragon carriages DO NOT EXAGGERATE IF YOU DON'T WANT CORRUPTED WORLDS")
                .define("camera_distance", 20.0D);
        this.camera_offset = builder
                .comment("Third Person Camera Horizontal Offset")
                .define("camera_offset", 0.0D);
        this.converge_pitch_angle = builder
                .comment("Pitch Angle Convergence")
                .define("converge_pitch_angle", true);
        this.converge_yaw_angle = builder
                .comment("Yaw Angle Convergence")
                .define("converge_yaw_angle", true);
        this.hover_animation = builder
                .comment("Enables hover animation for dragons")
                .define("hover_animation", true);
        this.toggle_descent = builder
                .comment("Enables the dragon to keep descending")
                .define("toggle_descent", false);
        this.redirect_inventory = builder
                .comment("Open your mount's inventory instead of your")
                .define("redirect_inventory", true);
    }

    public static void init(ModLoadingContext context) {
        if (BUILDER != null) {
            context.registerConfig(ModConfig.Type.CLIENT, BUILDER.build());
            BUILDER = null;
        }
    }
}
