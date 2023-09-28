package net.dragonmounts3.registry;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DeferredRegistry<V extends IForgeRegistryEntry<V>> implements IForgeRegistry<V> {
    private final RegistryBuilder<V> builder;
    protected ForgeRegistry<V> registry;

    public DeferredRegistry(String namespace, String name, Class<V> clazz, RegistryBuilder<V> builder) {
        this.builder = builder.setName(new ResourceLocation(namespace, name)).setType(clazz).addCallback(this);
    }

    private void createRegistry(RegistryEvent.NewRegistry event) {
        this.registry = (ForgeRegistry<V>) this.builder.create();
    }

    public void register(IEventBus bus) {
        bus.addListener(this::createRegistry);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.registry.getRegistryName();
    }

    @Override
    public Class<V> getRegistrySuperType() {
        return this.registry.getRegistrySuperType();
    }

    @Override
    public void register(V value) {
        this.registry.register(value);
    }

    @SafeVarargs
    @Override
    public final void registerAll(V... values) {
        for (V value : values) {
            this.register(value);
        }
    }

    @Override
    public boolean containsKey(ResourceLocation key) {
        return this.registry.containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
        return this.registry.containsValue(value);
    }

    @Override
    public boolean isEmpty() {
        return this.registry.isEmpty();
    }

    @Nullable
    @Override
    public V getValue(ResourceLocation key) {
        return this.registry.getValue(key);
    }

    @Nullable
    @Override
    public ResourceLocation getKey(V value) {
        return this.registry.getKey(value);
    }

    @Nullable
    @Override
    public ResourceLocation getDefaultKey() {
        return this.registry.getDefaultKey();
    }

    @Nonnull
    @Override
    public Set<ResourceLocation> getKeys() {
        return this.registry.getKeys();
    }

    @Nonnull
    @Override
    public Collection<V> getValues() {
        return this.registry.getValues();
    }

    @Nonnull
    @Override
    public Set<Map.Entry<RegistryKey<V>, V>> getEntries() {
        return this.registry.getEntries();
    }

    @Override
    public <T> T getSlaveMap(ResourceLocation slaveMapName, Class<T> type) {
        return this.registry.getSlaveMap(slaveMapName, type);
    }

    @Override
    public Iterator<V> iterator() {
        return this.registry.iterator();
    }

    public int getID(V value) {
        return this.registry.getID(value);
    }

    public int getID(ResourceLocation name) {
        return this.registry.getID(name);
    }

    public V getRaw(ResourceLocation key) {
        return this.registry.getRaw(key);
    }

    public V getValue(int id) {
        return this.registry.getValue(id);
    }

    public RegistryKey<V> getKey(int id) {
        return this.registry.getKey(id);
    }
}
