package dev.base;

/**
 * detail: Key-Value Entry
 * @author Ttt
 */
public class DevBaseEntry<K, V> extends DevBaseModel {

    // key
    public K entryKey;
    // value
    public V entryValue;

    public DevBaseEntry() {
    }

    public DevBaseEntry(K entryKey) {
        this.entryKey = entryKey;
    }

    public DevBaseEntry(K entryKey, V entryValue) {
        this.entryKey = entryKey;
        this.entryValue = entryValue;
    }

    // ================
    // = 对外公开方法 =
    // ================

    /**
     * 获取 Entry Key
     * @return Entry Key
     */
    public K getEntryKey() {
        return entryKey;
    }

    /**
     * 设置 Entry Key
     * @param entryKey Entry Key
     * @return {@link DevBaseEntry}
     */
    public DevBaseEntry<K, V> setEntryKey(final K entryKey) {
        this.entryKey = entryKey;
        return this;
    }

    // =

    /**
     * 获取 Entry Value
     * @return Entry Value
     */
    public V getEntryValue() {
        return entryValue;
    }

    /**
     * 设置 Entry Value
     * @param entryValue Entry Value
     * @return {@link DevBaseEntry}
     */
    public DevBaseEntry<K, V> setEntryValue(final V entryValue) {
        this.entryValue = entryValue;
        return this;
    }

    // =

    /**
     * 校验数据正确性
     * @return {@code true} correct, {@code false} error
     */
    @Override
    public boolean isCorrect() {
        return entryKey != null;
    }
}
