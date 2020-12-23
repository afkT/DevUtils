package dev.utils.app.share;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dev.DevUtils;

/**
 * detail: SPUtils 快捷工具类
 * @author Ttt
 */
public final class SharedUtils {

    private SharedUtils() {
    }

    /**
     * 保存数据
     * @param key   保存的 key
     * @param value 保存的 value
     * @param <T>   泛型
     */
    public static <T> void put(
            final String key,
            final T value
    ) {
        SPUtils.getPreference(DevUtils.getContext()).put(key, value);
    }

    /**
     * 保存 Map 集合 ( 只能是 Integer、Long、Boolean、Float、String、Set)
     * @param map {@link Map}
     * @param <T> 泛型
     */
    public static <T> void putAll(final Map<String, T> map) {
        SPUtils.getPreference(DevUtils.getContext()).putAll(map);
    }

    /**
     * 保存 List 集合
     * @param key  保存的 key
     * @param list 保存的 value
     */
    public static void putAll(
            final String key,
            final List<String> list
    ) {
        SPUtils.getPreference(DevUtils.getContext()).putAll(key, list);
    }

    /**
     * 保存 List 集合, 并且自定义保存顺序
     * @param key        保存的 key
     * @param list       保存的 value
     * @param comparator 排序 {@link Comparator}
     */
    public static void putAll(
            final String key,
            final List<String> list,
            final Comparator<String> comparator
    ) {
        SPUtils.getPreference(DevUtils.getContext()).putAll(key, list, comparator);
    }

    /**
     * 根据 key 获取数据
     * @param key  保存的 key
     * @param type 数据类型
     * @param <T>  泛型
     * @return 存储的数据
     */
    public static <T> T get(
            final String key,
            final IPreference.DataType type
    ) {
        return SPUtils.getPreference(DevUtils.getContext()).get(key, type);
    }

    /**
     * 获取全部数据
     * @return 存储的数据
     */
    public static Map<String, ?> getAll() {
        return SPUtils.getPreference(DevUtils.getContext()).getAll();
    }

    /**
     * 获取 List 集合
     * @param key 保存的 key
     * @return 存储的数据
     */
    public static List<String> getAll(final String key) {
        return SPUtils.getPreference(DevUtils.getContext()).getAll(key);
    }

    /**
     * 移除数据
     * @param key 保存的 key
     */
    public static void remove(final String key) {
        SPUtils.getPreference(DevUtils.getContext()).remove(key);
    }

    /**
     * 移除集合的数据
     * @param keys 保存的 key 集合
     */
    public static void removeAll(final List<String> keys) {
        SPUtils.getPreference(DevUtils.getContext()).removeAll(keys);
    }

    /**
     * 移除数组的数据
     * @param keys 保存的 key 数组
     */
    public static void removeAll(final String[] keys) {
        SPUtils.getPreference(DevUtils.getContext()).removeAll(keys);
    }

    /**
     * 是否存在 key
     * @param key 保存的 key
     * @return {@code true} yes, {@code false} no
     */
    public static boolean contains(final String key) {
        return SPUtils.getPreference(DevUtils.getContext()).contains(key);
    }

    /**
     * 清除全部数据
     */
    public static void clear() {
        SPUtils.getPreference(DevUtils.getContext()).clear();
    }

    // =

    /**
     * 获取 int 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public static int getInt(final String key) {
        return SPUtils.getPreference(DevUtils.getContext()).getInt(key);
    }

    /**
     * 获取 float 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public static float getFloat(final String key) {
        return SPUtils.getPreference(DevUtils.getContext()).getFloat(key);
    }

    /**
     * 获取 long 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public static long getLong(final String key) {
        return SPUtils.getPreference(DevUtils.getContext()).getLong(key);
    }

    /**
     * 获取 boolean 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public static boolean getBoolean(final String key) {
        return SPUtils.getPreference(DevUtils.getContext()).getBoolean(key);
    }

    /**
     * 获取 String 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public static String getString(final String key) {
        return SPUtils.getPreference(DevUtils.getContext()).getString(key);
    }

    /**
     * 获取 Set 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public static Set<String> getSet(final String key) {
        return SPUtils.getPreference(DevUtils.getContext()).getSet(key);
    }
}