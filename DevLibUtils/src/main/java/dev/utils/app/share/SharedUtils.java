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

    /** 全局上下文 */
    private static Context mContext;

    /**
     * 初始化操作
     * @param context
     */
    public static void init(Context context) {
        if (mContext == null && context != null) {
            // 初始化全局上下文
            mContext = context.getApplicationContext();
        }
    }
    
    public static <T> void put(String key, T value) {
        SPUtils.getPreference(mContext).put(key, value);
    }

    public static <T> void putAll(Map<String, T> map) {
        SPUtils.getPreference(mContext).putAll(map);
    }

    public static void putAll(String key, List<String> list) {
        SPUtils.getPreference(mContext).putAll(key, list);
    }

    public static void putAll(String key, List<String> list, Comparator<String> comparator) {
        SPUtils.getPreference(mContext).putAll(key, list, comparator);
    }
    
    public static <T> T get(String key, IPreference.DataType type) {
        return SPUtils.getPreference(mContext).get(key, type);
    }
    
    public static Map<String, ?> getAll() {
        return SPUtils.getPreference(mContext).getAll();
    }
    
    public static List<String> getAll(String key) {
        return SPUtils.getPreference(mContext).getAll(key);
    }
    
    public static void remove(String key) {
        SPUtils.getPreference(mContext).remove(key);
    }
    
    public static void removeAll(List<String> keys) {
        SPUtils.getPreference(mContext).removeAll(keys);
    }
    
    public static void removeAll(String[] keys) {
        SPUtils.getPreference(mContext).removeAll(keys);
    }
    
    public static boolean contains(String key) {
        return SPUtils.getPreference(mContext).contains(key);
    }
    
    public static void clear() {
        SPUtils.getPreference(mContext).clear();
    }

    // ==
    
    public static int getInt(String key) {
        return SPUtils.getPreference(mContext).getInt(key);
    }
    
    public static float getFloat(String key) {
        return SPUtils.getPreference(mContext).getFloat(key);
    }
    
    public static long getLong(String key) {
        return SPUtils.getPreference(mContext).getLong(key);
    }
    
    public static boolean getBoolean(String key) {
        return SPUtils.getPreference(mContext).getBoolean(key);
    }
    
    public static String getString(String key) {
        return SPUtils.getPreference(mContext).getString(key);
    }
    
    public static Set<String> getSet(String key) {
        return SPUtils.getPreference(mContext).getSet(key);
    }
}
