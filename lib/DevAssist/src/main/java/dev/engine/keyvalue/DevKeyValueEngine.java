package dev.engine.keyvalue;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * detail: Key-Value Engine
 * @author Ttt
 */
public final class DevKeyValueEngine {

    private DevKeyValueEngine() {
    }

    private static IKeyValueEngine sEngine;

    /**
     * 获取 Key-Value Engine
     * @return {@link IKeyValueEngine}
     */
    public static IKeyValueEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Key-Value Engine
     * @param engine {@link IKeyValueEngine}
     */
    public static void setEngine(final IKeyValueEngine engine) {
        DevKeyValueEngine.sEngine = engine;
    }

    // =

    private static final Map<String, IKeyValueEngine> sEngineMaps = new LinkedHashMap<>();

    /**
     * 获取 Key-Value Engine
     * @param key key
     * @return {@link IKeyValueEngine}
     */
    public static IKeyValueEngine getEngine(final String key) {
        return sEngineMaps.get(key);
    }

    /**
     * 设置 Key-Value Engine
     * @param key    key
     * @param engine {@link IKeyValueEngine}
     */
    public static void setEngine(
            final String key,
            final IKeyValueEngine engine
    ) {
        sEngineMaps.put(key, engine);
    }

    /**
     * 是否存在 Key-Value Engine
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
    public static Map<String, IKeyValueEngine> getsEngineMaps() {
        return sEngineMaps;
    }
}