package net.dragonmounts3.api.variant;

import java.util.*;

public class VariantManager {
    private final HashMap<String, AbstractVariant> map = new LinkedHashMap<>();

    public void add(AbstractVariant variant) {
        String name = variant.getSerializedName();
        if (this.map.containsKey(name)) {
            throw new IllegalArgumentException();
        }
        this.map.put(name, variant);
    }

    public AbstractVariant get(Random random) {
        if (this.map.isEmpty()) return DragonVariants.ENDER_FEMALE;
        Collection<AbstractVariant> values = this.map.values();
        Iterator<AbstractVariant> iterator = values.iterator();
        for (int i = random.nextInt(values.size()); i > 0; --i) {
            iterator.next();
        }
        return iterator.next();
    }

    public AbstractVariant get(String name) {
        return this.map.getOrDefault(name, DragonVariants.ENDER_FEMALE);
    }

    public AbstractVariant getNext(String name) {
        if (this.map.isEmpty()) return DragonVariants.ENDER_FEMALE;
        Iterator<Map.Entry<String, AbstractVariant>> iterator = this.map.entrySet().iterator();
        Map.Entry<String, AbstractVariant> first = iterator.next();
        if (name != null) {
            Map.Entry<String, AbstractVariant> current = first;
            while (!name.equals(current.getKey())) {
                if (iterator.hasNext()) {
                    current = iterator.next();
                }
            }
            if (iterator.hasNext()) {
                return iterator.next().getValue();
            }
        }
        return first.getValue();
    }
}
