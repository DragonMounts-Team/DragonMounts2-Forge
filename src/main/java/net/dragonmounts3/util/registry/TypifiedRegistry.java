package net.dragonmounts3.util.registry;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
public class TypifiedRegistry<K, E extends IForgeRegistryEntry<? super E>> implements Iterable<E> {
    private final Map<K, E> map = this.initMap();

    public final RegistryObject<E> register(
            final DeferredRegister<? super E> register,
            final String name,
            final K key,
            final E value
    ) {
        if (this.map.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        this.map.put(key, value);
        return register.register(name, () -> value);
    }

    protected Map<K, E> initMap() {
        return new LinkedHashMap<>();
    }

    public final boolean contains(K key) {
        return this.map.containsKey(key);
    }

    @Nullable
    public final E get(K key) {
        return this.map.get(key);
    }

    public final Collection<E> values() {
        return this.map.values();
    }

    @Nonnull
    @Override
    public final Iterator<E> iterator() {
        return this.map.values().iterator();
    }
}
