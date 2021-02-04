package dev.base.entry;

import dev.base.DevEntry;

public class LongEntry<V>
        extends DevEntry<Long, V> {

    public LongEntry() {
    }

    public LongEntry(final Long key) {
        super(key);
    }

    public LongEntry(
            final Long key,
            final V value
    ) {
        super(key, value);
    }
}