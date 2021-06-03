package dev.engine.storage;

import java.util.HashMap;
import java.util.Map;

/**
 * detail: Storage Engine
 * @author Ttt
 */
public final class DevStorageEngine {

    private DevStorageEngine() {
    }

    private static IStorageEngine sEngine;

    /**
     * 获取 Storage Engine
     * @return {@link IStorageEngine}
     */
    public static IStorageEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Storage Engine
     * @param engine {@link IStorageEngine}
     */
    public static void setEngine(final IStorageEngine engine) {
        DevStorageEngine.sEngine = engine;
    }

    // =

    private static final Map<String, IStorageEngine> sEngineMaps = new HashMap<>();

    /**
     * 获取 Storage Engine
     * @param key key
     * @return {@link IStorageEngine}
     */
    public static IStorageEngine getEngine(final String key) {
        return sEngineMaps.get(key);
    }

    /**
     * 设置 Storage Engine
     * @param key    key
     * @param engine {@link IStorageEngine}
     */
    public static void setEngine(
            final String key,
            final IStorageEngine engine
    ) {
        sEngineMaps.put(key, engine);
    }

    /**
     * 是否存在 Storage Engine
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
    public static Map<String, IStorageEngine> getsEngineMaps() {
        return sEngineMaps;
    }
}