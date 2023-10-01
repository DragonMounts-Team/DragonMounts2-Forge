package net.dragonmounts3.init;

import net.dragonmounts3.DragonMountsConfig;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.settings.ToggleableKeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class DMKeyBindings {
    public static final KeyBinding DESCENT = new ToggleableKeyBinding(
            "key.dragonmounts.descent",
            GLFW.GLFW_KEY_Z,
            "key.categories.movement",
            DragonMountsConfig.CLIENT.toggle_descent::get
    );

    public static void register() {
        ClientRegistry.registerKeyBinding(DESCENT);
    }
}
