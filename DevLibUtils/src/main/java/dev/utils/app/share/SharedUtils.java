package dev.utils.app.share;

import android.content.Context;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * detail: SPUtils 工具类 - 直接单独使用
 * @author Ttt
 */
public final class SharedUtils {

    private SharedUtils() {
    }

    // 全局 Context
    private static Context sContext;

    /**
     * 初始化操作
     * @param context {@link Context}
     */
    public static void init(final Context context) {
        if (sContext == null && context != null) {
            // 初始化全局 Context
            sContext = context.getApplicationContext();
        }
    }

    // =

    /**
     * 保存数据
     * @param key   保存的 key
     * @param value 保存的 value
     * @param <T>   泛型
     */
    public static <T> void put(final String key, final T value) {
        SPUtils.getPreference(sContext).put(key, value);
    }

    /**
     * 保存 Map 集合(只能是 Integer、Long、Boolean、Float、String、Set)
     * @param map {@link Map}
     * @param <T> 泛型
     */
    public static <T> void putAll(final Map<String, T> map) {
        SPUtils.getPreference(sContext).putAll(map);
    }

    /**
     * 保存 List 集合
     * @param key  保存的 key
     * @param list 保存的 value
     */
    public static void putAll(final String key, final List<String> list) {
        SPUtils.getPreference(sContext).putAll(key, list);
    }

    /**
     * 保存 List 集合, 并且自定义保存顺序
     * @param key        保存的 key
     * @param list       保存的 value
     * @param comparator 排序 {@link Comparator}
     */
    public static void putAll(final String key, final List<String> list, final Comparator<String> comparator) {
        SPUtils.getPreference(sContext).putAll(key, list, comparator);
    }

    /**
     * 根据 key 获取数据
     * @param key  保存的 key
     * @param type 数据类型
     * @param <T>  泛型
     * @return 存储的数据
     */
    public static <T> T get(final String key, final IPreference.DataType type) {
        return SPUtils.getPreference(sContext).get(key, type);
    }

    /**
     * 获取全部数据
     * @return 存储的数据
     */
    public static Map<String, ?> getAll() {
        return SPUtils.getPreference(sContext).getAll();
    }

    /**
     * 获取 List 集合
     * @param key 保存的 key
     * @return 存储的数据
     */
    public static List<String> getAll(final String key) {
        return SPUtils.getPreference(sContext).getAll(key);
    }

    /**
     * 移除数据
     * @param key 保存的 key
     */
    public static void remove(final String key) {
        SPUtils.getPreference(sContext).remove(key);
    }

    /**
     * 移除集合的数据
     * @param keys 保存的 key 集合
     */
    public static void removeAll(final List<String> keys) {
        SPUtils.getPreference(sContext).removeAll(keys);
    }

    /**
     * 移除数组的数据
     * @param keys 保存的 key 数组
     */
    public static void removeAll(final String[] keys) {
        SPUtils.getPreference(sContext).removeAll(keys);
    }

    /**
     * 是否存在 key
     * @param key 保存的 key
     * @return {@code true} yes, {@code false} no
     */
    public static boolean contains(final String key) {
        return SPUtils.getPreference(sContext).contains(key);
    }

    /**
     * 清除全部数据
     */
    public static void clear() {
        SPUtils.getPreference(sContext).clear();
    }

    // =

    /**
     * 获取 int 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public static int getInt(final String key) {
        return SPUtils.getPreference(sContext).getInt(key);
    }

    /**
     * 获取 float 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public static float getFloat(final String key) {
        return SPUtils.getPreference(sContext).getFloat(key);
    }

    /**
     * 获取 long 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public static long getLong(final String key) {
        return SPUtils.getPreference(sContext).getLong(key);
    }

    /**
     * 获取 boolean 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public static boolean getBoolean(final String key) {
        return SPUtils.getPreference(sContext).getBoolean(key);
    }

    /**
     * 获取 String 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public static String getString(final String key) {
        return SPUtils.getPreference(sContext).getString(key);
    }

    /**
     * 获取 Set 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public static Set<String> getSet(final String key) {
        return SPUtils.getPreference(sContext).getSet(key);
    }
}
