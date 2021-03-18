package dev.engine.cache;

import java.util.LinkedHashMap;

/**
 * detail: Cache Engine
 * @author Ttt
 */
public final class DevCacheEngine {

    private DevCacheEngine() {
    }

    private static LinkedHashMap<String, ICacheEngine> sEngineMaps = new LinkedHashMap<>();

    /**
     * 获取 CacheEngine
     * @param cacheID 缓存 Engine id
     * @return {@link ICacheEngine}
     */
    public static ICacheEngine getEngine(final String cacheID) {
        return sEngineMaps.get(cacheID);
    }

    /**
     * 设置 CacheEngine
     * @param engine {@link ICacheEngine}
     */
    public static void setEngine(final ICacheEngine engine) {
        sEngineMaps.put(engine.getConfig().cacheID, engine);
    }

    /**
     * 判断是否存在 CacheEngine
     * @param cacheID 缓存 Engine id
     * @return {@code true} yes, {@code false} no
     */
    public static boolean contains(final String cacheID) {
        return sEngineMaps.containsKey(cacheID);
    }
}