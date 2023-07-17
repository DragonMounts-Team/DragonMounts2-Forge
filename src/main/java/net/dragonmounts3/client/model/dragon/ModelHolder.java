package net.dragonmounts3.client.model.dragon;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ModelHolder<K, V extends ModelRenderer> {
    interface Supplier<K, V extends ModelRenderer> {
        V get(Model model, K type);
    }

    protected HashMap<K, V> map = new HashMap<>();
    protected K last = null;
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
        if (key != this.last) {
            this.current = this.map.get(key);
        }
        return this.current;
    }
}
