package dev.engine.cache;

import java.util.LinkedHashMap;
import java.util.Map;

import dev.utils.DevFinal;

/**
 * detail: Cache Engine
 * @author Ttt
 */
public final class DevCacheEngine {

    private DevCacheEngine() {
    }

    private static final Map<String, ICacheEngine> sEngineMaps = new LinkedHashMap<>();

    /**
     * 获取 Engine
     * @return {@link ICacheEngine}
     */
    public static ICacheEngine getEngine() {
        return getEngine(DevFinal.DEFAULT);
    }

    /**
     * 获取 Engine
     * @param key key
     * @return {@link ICacheEngine}
     */
    public static ICacheEngine getEngine(final String key) {
        return sEngineMaps.get(key);
    }

    /**
     * 设置 Engine
     * @param engine {@link ICacheEngine}
     * @return {@link ICacheEngine}
     */
    public static ICacheEngine setEngine(final ICacheEngine engine) {
        return setEngine(DevFinal.DEFAULT, engine);
    }

    /**
     * 设置 Engine
     * @param key    key
     * @param engine {@link ICacheEngine}
     * @return {@link ICacheEngine}
     */
    public static ICacheEngine setEngine(
            final String key,
            final ICacheEngine engine
    ) {
        sEngineMaps.put(key, engine);
        return engine;
    }

    /**
     * 移除 Engine
     */
    public static void removeEngine() {
        removeEngine(DevFinal.DEFAULT);
    }

    /**
     * 移除 Engine
     * @param key key
     */
    public static void removeEngine(final String key) {
        sEngineMaps.remove(key);
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 获取 Engine Map
     * @return Engine Map
     */
    public static Map<String, ICacheEngine> getsEngineMaps() {
        return sEngineMaps;
    }

    /**
     * 是否存在 Engine
     * @return {@code true} yes, {@code false} no
     */
    public static boolean contains() {
        return contains(DevFinal.DEFAULT);
    }

    /**
     * 是否存在 Engine
     * @param key key
     * @return {@code true} yes, {@code false} no
     */
    public static boolean contains(final String key) {
        return sEngineMaps.containsKey(key);
    }

    /**
     * 判断 Engine 是否为 null
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty() {
        return isEmpty(DevFinal.DEFAULT);
    }

    /**
     * 判断 Engine 是否为 null
     * @param key key
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final String key) {
        return getEngine(key) == null;
    }
}