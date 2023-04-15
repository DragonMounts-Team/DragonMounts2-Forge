package net.dragonmounts3.inits;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.client.gui.DragonCoreScreen;
import net.dragonmounts3.inventory.ContainerDragonCore;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DragonMounts.create(ForgeRegistries.CONTAINERS);
    public static final RegistryObject<ContainerType<ContainerDragonCore>> DRAGON_CORE = register("dragon_core", ContainerDragonCore::new);

    private static <T extends Container> RegistryObject<ContainerType<T>> register(String name, IContainerFactory<T> factory) {
        return CONTAINERS.register(name, () -> IForgeContainerType.create(factory));
    }

    public static void registerScreens() {
        ScreenManager.register(DRAGON_CORE.get(), DragonCoreScreen::new);
    }
}
