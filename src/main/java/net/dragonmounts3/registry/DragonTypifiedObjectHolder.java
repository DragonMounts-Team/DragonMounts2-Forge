package net.dragonmounts3.registry;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public final class DragonTypifiedObjectHolder<T extends IForgeRegistryEntry<? super T> & IDragonTypified> extends ObjectHolder<DragonType, T> {
    public RegistryObject<T> register(
            final DeferredRegister<? super T> register,
            final String name,
            final T value
    ) {
        return register(register, name, value.getDragonType(), value);
    }
}
