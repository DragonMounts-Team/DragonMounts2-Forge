package net.dragonmounts.init;

import net.dragonmounts.DragonMounts;
import net.dragonmounts.client.gui.DragonCoreScreen;
import net.dragonmounts.client.gui.DragonInventoryScreen;
import net.dragonmounts.inventory.DragonCoreContainer;
import net.dragonmounts.inventory.DragonInventoryContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DMContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DragonMounts.create(ForgeRegistries.CONTAINERS);
    public static final RegistryObject<ContainerType<DragonInventoryContainer>> DRAGON_INVENTORY = register("dragon_inventory", DragonInventoryContainer::fromPacket);
    public static final RegistryObject<ContainerType<DragonCoreContainer>> DRAGON_CORE = register("dragon_core", DragonCoreContainer::new);

    private static <T extends Container> RegistryObject<ContainerType<T>> register(String name, IContainerFactory<T> factory) {
        return CONTAINERS.register(name, () -> IForgeContainerType.create(factory));
    }

    public static void registerScreens() {
        ScreenManager.register(DRAGON_INVENTORY.get(), DragonInventoryScreen::new);
        ScreenManager.register(DRAGON_CORE.get(), DragonCoreScreen::new);
    }
}
