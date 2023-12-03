package net.dragonmounts3.client.model.dragon;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ModelHolder<K, V extends ModelRenderer> {
    public interface Supplier<K, V extends ModelRenderer> {
        V get(Model model, K type);
    }

    protected Reference2ObjectOpenHashMap<K, V> map = new Reference2ObjectOpenHashMap<>();
    protected K key = null;
    protected V current = null;

    public ModelHolder(Model model, Supplier<K, V> supplier, List<K> keys) {
        for (K key : keys) {
            this.map.put(key, supplier.get(model, key));
        }
    }

    public V getCurrent() {
        return this.current;
    }

    public V load(K key) {
        if (key != this.key) {
            this.current = this.map.get(key);
        }
        return this.current;
    }
}
