package net.dragonmounts3.init;

import net.dragonmounts3.DragonMountsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.settings.ToggleableKeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.loading.FMLLoader;
import org.lwjgl.glfw.GLFW;

public class DMKeyBindings {
    public static final KeyBinding DESCENT = new ToggleableKeyBinding(
            "key.dragonmounts.descent",
            GLFW.GLFW_KEY_Z,
            "key.categories.movement",
            DragonMountsConfig.CLIENT.toggle_descent::get
    );

    public static void register() {
        //noinspection ConstantValue
        if (FMLLoader.getDist().isClient() && Minecraft.getInstance() != null) {
            ClientRegistry.registerKeyBinding(DESCENT);
        }
    }
}
