package net.dragonmounts3.objects;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

@ParametersAreNonnullByDefault
public final class DragonTypifiedObjectHolder<T extends IForgeRegistryEntry<? super T> & IDragonTypified> implements Iterable<T> {
    private final HashMap<DragonType, T> map = new HashMap<>();

    public RegistryObject<T> register(
            final DeferredRegister<? super T> register,
            final String name,
            final T value
    ) {
        final DragonType type = value.getDragonType();
        if (map.containsKey(type)) {
            throw new IllegalArgumentException();
        }
        map.put(type, value);
        return register.register(name, () -> value);
    }

    @Nullable
    public T get(DragonType type) {
        return map.get(type);
    }

    public Collection<T> values() {
        return map.values();
    }

    @Nonnull
    @Override
    public Iterator<T> iterator() {
        return map.values().iterator();
    }
}
