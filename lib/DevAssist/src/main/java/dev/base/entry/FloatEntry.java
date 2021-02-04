package dev.base.entry;

import dev.base.DevEntry;

public class FloatEntry<V>
        extends DevEntry<Float, V> {

    public FloatEntry() {
    }

    public FloatEntry(final Float key) {
        super(key);
    }

    public FloatEntry(
            final Float key,
            final V value
    ) {
        super(key, value);
    }
}