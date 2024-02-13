package net.dragonmounts.util;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class ModelHolder<K, V extends ModelRenderer> {
    protected final Reference2ObjectOpenHashMap<K, V> map = new Reference2ObjectOpenHashMap<>();
    protected K key = null;
    protected V current = null;

    @SafeVarargs
    public ModelHolder(Function<K, V> function, K... keys) {
        for (K key : keys) {
            this.map.put(key, function.apply(key));
        }
    }

    public V getCurrent() {
        return this.current;
    }

    public V load(K key) {
        if (key != this.key) {
            this.current = this.map.get(this.key = key);
        }
        return this.current;
    }
}
