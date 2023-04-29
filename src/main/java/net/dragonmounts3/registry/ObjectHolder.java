package net.dragonmounts3.registry;

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
public class ObjectHolder<K, E extends IForgeRegistryEntry<? super E>> implements Iterable<E> {
    private final HashMap<K, E> map = new HashMap<>();

    public final RegistryObject<E> register(
            final DeferredRegister<? super E> register,
            final String name,
            final K key,
            final E value
    ) {
        if (map.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        map.put(key, value);
        return register.register(name, () -> value);
    }

    @Nullable
    public final E get(K type) {
        return map.get(type);
    }

    public final Collection<E> values() {
        return map.values();
    }

    @Nonnull
    @Override
    public final Iterator<E> iterator() {
        return map.values().iterator();
    }
}
