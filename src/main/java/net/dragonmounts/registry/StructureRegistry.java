package net.dragonmounts.registry;

import it.unimi.dsi.fastutil.objects.Reference2ObjectLinkedOpenHashMap;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.registries.DeferredRegister;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

public class StructureRegistry<K> implements Iterable<StructureRegistry.Holder<? extends IFeatureConfig>> {
    public static class Holder<C extends IFeatureConfig> {
        public final Structure<C> structure;
        public final StructureFeature<C, ? extends Structure<C>> feature;
        public final StructureSeparationSettings settings;

        public Holder(Structure<C> structure, C config, StructureSeparationSettings settings) {
            this.structure = structure;
            this.feature = structure.configured(config);
            this.settings = settings;
        }

        public final StructureFeature<C, ? extends Structure<C>> getFeature() {
            return this.feature;
        }
    }

    private final Map<K, Holder<? extends IFeatureConfig>> map = new Reference2ObjectLinkedOpenHashMap<>();

    @SuppressWarnings({"rawtypes", "unchecked"})
    public final <T extends IFeatureConfig, S extends Structure<T>> Holder<T> register(
            final DeferredRegister<? super S> register,
            final String name,
            final K key,
            final S structure,
            final T config,
            final StructureSeparationSettings settings
    ) {
        if (this.map.containsKey(key)) throw new IllegalArgumentException();
        register.register(name, (Supplier) structure::getStructure);
        Holder<T> holder = new Holder<>(structure, config, settings);
        this.map.put(key, holder);
        return holder;
    }

    public final boolean contains(K key) {
        return this.map.containsKey(key);
    }

    @Nullable
    public final Holder<? extends IFeatureConfig> get(K key) {
        return this.map.get(key);
    }

    public final Collection<Holder<? extends IFeatureConfig>> values() {
        return this.map.values();
    }

    @Nonnull
    @Override
    public final Iterator<Holder<? extends IFeatureConfig>> iterator() {
        return this.map.values().iterator();
    }
}
