package dev.base;

/**
 * detail: Key-Value Entry
 * @author Ttt
 */
public class DevEntry<K, V>
        extends DevModel {

    // key
    public K mEntryKey;
    // value
    public V mEntryValue;

    public DevEntry() {
    }

    public DevEntry(K entryKey) {
        this.mEntryKey = entryKey;
    }

    public DevEntry(
            K entryKey,
            V entryValue
    ) {
        this.mEntryKey = entryKey;
        this.mEntryValue = entryValue;
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取 Entry Key
     * @return Entry Key
     */
    public K getEntryKey() {
        return mEntryKey;
    }

    /**
     * 设置 Entry Key
     * @param entryKey Entry Key
     * @return {@link DevEntry}
     */
    public DevEntry<K, V> setEntryKey(final K entryKey) {
        this.mEntryKey = entryKey;
        return this;
    }

    // =

    /**
     * 获取 Entry Value
     * @return Entry Value
     */
    public V getEntryValue() {
        return mEntryValue;
    }

    /**
     * 设置 Entry Value
     * @param entryValue Entry Value
     * @return {@link DevEntry}
     */
    public DevEntry<K, V> setEntryValue(final V entryValue) {
        this.mEntryValue = entryValue;
        return this;
    }

    // =

    /**
     * 校验数据正确性
     * @return {@code true} correct, {@code false} error
     */
    @Override
    public boolean isCorrect() {
        return mEntryKey != null;
    }
}