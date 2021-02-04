package dev.base;

import dev.utils.common.ObjectUtils;

/**
 * detail: Key-Value Entry
 * @author Ttt
 */
public class DevEntry<K, V> {

    // key
    public K mKey;
    // value
    public V mValue;

    public DevEntry() {
    }

    public DevEntry(final K key) {
        this.mKey = key;
    }

    public DevEntry(
            final K key,
            final V value
    ) {
        this.mKey = key;
        this.mValue = value;
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取 Key
     * @return Key
     */
    public K getKey() {
        return mKey;
    }

    /**
     * 设置 Key
     * @param key Key
     * @return {@link DevEntry}
     */
    public DevEntry<K, V> setKey(final K key) {
        this.mKey = key;
        return this;
    }

    /**
     * 获取 Value
     * @return Value
     */
    public V getValue() {
        return mValue;
    }

    /**
     * 设置 Value
     * @param value Value
     * @return {@link DevEntry}
     */
    public DevEntry<K, V> setValue(final V value) {
        this.mValue = value;
        return this;
    }

    // ===========
    // = 判断方法 =
    // ===========

    /**
     * 判断 Key 是否一致
     * @param key 待校验 Key
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsKey(final K key) {
        return key != null && ObjectUtils.equals(this.mKey, key);
    }

    /**
     * 判断 Value 是否一致
     * @param value 待校验 Value
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsValue(final V value) {
        return value != null && ObjectUtils.equals(this.mValue, value);
    }
}