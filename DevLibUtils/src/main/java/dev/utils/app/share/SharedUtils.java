package dev.utils.app.share;

import android.content.Context;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * detail: SPUtils 工具类 - 直接单独使用
 * Created by Ttt
 */
public final class SharedUtils {

    private SharedUtils(){
    }

    /** 全局 Context */
    private static Context mContext;

    /**
     * 初始化操作
     * @param context
     */
    public static void init(Context context) {
        if (mContext == null && context != null) {
            // 初始化全局 Context
            mContext = context.getApplicationContext();
        }
    }

    /**
     * 保存一个数据
     * @param key
     * @param value
     */
    public static <T> void put(String key, T value) {
        SPUtils.getPreference(mContext).put(key, value);
    }

    /**
     * 保存一个Map集合(只能是 Integer,Long,Boolean,Float,String,Set)
     * @param map
     */
    public static <T> void putAll(Map<String, T> map) {
        SPUtils.getPreference(mContext).putAll(map);
    }

    /**
     * 保存一个List集合
     * @param key
     * @param list
     */
    public static void putAll(String key, List<String> list) {
        SPUtils.getPreference(mContext).putAll(key, list);
    }

    /**
     * 保存一个List集合,并且自定义保存顺序
     * @param key
     * @param list
     * @param comparator
     */
    public static void putAll(String key, List<String> list, Comparator<String> comparator) {
        SPUtils.getPreference(mContext).putAll(key, list, comparator);
    }

    /**
     * 根据key取出一个数据
     * @param key
     */
    public static <T> T get(String key, IPreference.DataType type) {
        return SPUtils.getPreference(mContext).get(key, type);
    }

    /**
     * 取出全部数据
     * @return
     */
    public static Map<String, ?> getAll() {
        return SPUtils.getPreference(mContext).getAll();
    }

    /**
     * 取出一个List集合
     * @param key
     * @return
     */
    public static List<String> getAll(String key) {
        return SPUtils.getPreference(mContext).getAll(key);
    }

    /**
     * 移除一个数据
     * @param key
     * @return
     */
    public static void remove(String key) {
        SPUtils.getPreference(mContext).remove(key);
    }

    /**
     * 移除一个集合的数据
     * @param keys
     * @return
     */
    public static void removeAll(List<String> keys) {
        SPUtils.getPreference(mContext).removeAll(keys);
    }

    /**
     * 移除一个数组的数据
     * @param keys
     * @return
     */
    public static void removeAll(String[] keys) {
        SPUtils.getPreference(mContext).removeAll(keys);
    }

    /**
     * 是否存在key
     * @return
     */
    public static boolean contains(String key) {
        return SPUtils.getPreference(mContext).contains(key);
    }

    /**
     * 清除全部数据
     */
    public static void clear() {
        SPUtils.getPreference(mContext).clear();
    }

    // ==

    /**
     * 获取int类型的数据
     * @return
     */
    public static int getInt(String key) {
        return SPUtils.getPreference(mContext).getInt(key);
    }

    /**
     * 获取Float类型的数据
     * @param key
     * @return
     */
    public static float getFloat(String key) {
        return SPUtils.getPreference(mContext).getFloat(key);
    }

    /**
     * 获取long类型的数据
     * @param key
     * @return
     */
    public static long getLong(String key) {
        return SPUtils.getPreference(mContext).getLong(key);
    }

    /**
     * 获取boolean类型的数据
     * @param key
     * @return
     */
    public static boolean getBoolean(String key) {
        return SPUtils.getPreference(mContext).getBoolean(key);
    }

    /**
     * 获取String类型的数据
     * @param key
     * @return
     */
    public static String getString(String key) {
        return SPUtils.getPreference(mContext).getString(key);
    }

    /**
     * 获取Set类型的数据
     * @param key
     * @return
     */
    public static Set<String> getSet(String key) {
        return SPUtils.getPreference(mContext).getSet(key);
    }
}
