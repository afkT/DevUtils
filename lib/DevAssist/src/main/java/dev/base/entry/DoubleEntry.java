package dev.base.entry;

import dev.base.DevEntry;

public class DoubleEntry<V>
        extends DevEntry<Double, V> {

    public DoubleEntry() {
    }

    public DoubleEntry(final Double key) {
        super(key);
    }

    public DoubleEntry(
            final Double key,
            final V value
    ) {
        super(key, value);
    }
}