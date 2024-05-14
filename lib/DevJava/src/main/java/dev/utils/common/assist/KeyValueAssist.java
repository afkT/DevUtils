package dev.utils.common.assist;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * detail: 键值对 Assist
 * @author Ttt
 */
public class KeyValueAssist<Key, Value> {

    // KV Map
    private final Map<Key, Value> mMaps = new LinkedHashMap<>();

    /**
     * 获取 Value
     * @param key {@link Key}
     * @return {@link Value}
     */
    public Value getValue(final Key key) {
        return mMaps.get(key);
    }

    /**
     * 设置 Value
     * @param key   {@link Key}
     * @param value {@link Value}
     * @return {@link Value}
     */
    public Value setValue(
            final Key key,
            final Value value
    ) {
        mMaps.put(key, value);
        return value;
    }

    /**
     * 移除 Value
     * @param key {@link Key}
     */
    public void removeValue(final Key key) {
        mMaps.remove(key);
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 获取 Key Value Map
     * @return Key Value Map
     */
    public Map<Key, Value> getKeyValueMaps() {
        return mMaps;
    }

    /**
     * 是否存在 Key
     * @param key {@link Key}
     * @return {@code true} yes, {@code false} no
     */
    public boolean containsKey(final Key key) {
        return mMaps.containsKey(key);
    }

    /**
     * 是否存在 Value
     * @param value {@link Value}
     * @return {@code true} yes, {@code false} no
     */
    public boolean containsValue(final Value value) {
        return mMaps.containsValue(value);
    }

    /**
     * 判断 Value 是否为 null
     * @param key {@link Key}
     * @return {@code true} yes, {@code false} no
     */
    public boolean isEmpty(final Key key) {
        return getValue(key) == null;
    }
}