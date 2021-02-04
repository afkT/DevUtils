package dev.base.entry;

import dev.base.DevEntry;

public class StringEntry<V>
        extends DevEntry<String, V> {

    public StringEntry() {
    }

    public StringEntry(final String key) {
        super(key);
    }

    public StringEntry(
            final String key,
            final V value
    ) {
        super(key, value);
    }
}