package net.dragonmounts3.api;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.TreeMap;

@ParametersAreNonnullByDefault
public final class DragonTypifiedObjectHolder<T extends IForgeRegistryEntry<? super T> & IDragonTypified> extends ObjectHolder<DragonType, T> {

    @Override
    protected Map<DragonType, T> initMap() {
        return new TreeMap<>();
    }

    public RegistryObject<T> register(
            final DeferredRegister<? super T> register,
            final String name,
            final T value
    ) {
        return this.register(register, name, value.getDragonType(), value);
    }
}
