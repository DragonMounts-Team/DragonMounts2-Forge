package net.dragonmounts.init;

import net.dragonmounts.config.ClientConfig;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.settings.ToggleableKeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class DMKeyBindings {
    public static final KeyBinding DESCENT = new ToggleableKeyBinding(
            "key.dragonmounts.descent",
            GLFW.GLFW_KEY_Z,
            "key.categories.movement",
            ClientConfig.INSTANCE.toggle_descent::get
    );

    public static void register() {
        ClientRegistry.registerKeyBinding(DESCENT);
    }
}
