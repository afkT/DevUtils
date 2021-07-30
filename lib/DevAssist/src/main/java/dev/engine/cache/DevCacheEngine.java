package dev.engine.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * detail: Cache Engine
 * @author Ttt
 */
public final class DevCacheEngine {

    private DevCacheEngine() {
    }

    private static ICacheEngine sEngine;

    /**
     * 获取 Cache Engine
     * @return {@link ICacheEngine}
     */
    public static ICacheEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Cache Engine
     * @param engine {@link ICacheEngine}
     */
    public static void setEngine(final ICacheEngine engine) {
        DevCacheEngine.sEngine = engine;
    }

    // =

    private static final Map<String, ICacheEngine> sEngineMaps = new LinkedHashMap<>();

    /**
     * 获取 Cache Engine
     * @param key key
     * @return {@link ICacheEngine}
     */
    public static ICacheEngine getEngine(final String key) {
        return sEngineMaps.get(key);
    }

    /**
     * 设置 Cache Engine
     * @param key    key
     * @param engine {@link ICacheEngine}
     */
    public static void setEngine(
            final String key,
            final ICacheEngine engine
    ) {
        sEngineMaps.put(key, engine);
    }

    /**
     * 是否存在 Cache Engine
     * @param key key
     * @return {@code true} yes, {@code false} no
     */
    public static boolean contains(final String key) {
        return sEngineMaps.containsKey(key);
    }

    /**
     * 获取 Engine Map
     * @return Engine Map
     */
    public static Map<String, ICacheEngine> getsEngineMaps() {
        return sEngineMaps;
    }
}