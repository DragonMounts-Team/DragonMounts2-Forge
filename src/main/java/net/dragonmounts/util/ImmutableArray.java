package net.dragonmounts.util;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.function.Consumer;

public class ImmutableArray<T> implements Iterable<T> {
    final T[] values;
    public final Class<T> clazz;
    public final int length;

    @SafeVarargs
    public ImmutableArray(Class<T> clazz, final T... values) {
        this.clazz = clazz;
        this.values = values;
        this.length = values.length;
    }

    @Nonnull
    @Override
    public IteratorImpl iterator() {
        return new IteratorImpl();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        for (int i = 0; i < this.length; ++i) {
            action.accept(this.values[i]);
        }
    }

    public final class IteratorImpl implements Iterator<T> {
        int i = 0;

        @Override
        public boolean hasNext() {
            return this.i < ImmutableArray.this.length;
        }

        @Override
        public T next() {
            return ImmutableArray.this.values[i++];
        }
    }
}
