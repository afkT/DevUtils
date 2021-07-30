package dev.engine.push;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * detail: Push Engine
 * @author Ttt
 */
public final class DevPushEngine {

    private DevPushEngine() {
    }

    private static IPushEngine sEngine;

    /**
     * 获取 Push Engine
     * @return {@link IPushEngine}
     */
    public static IPushEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Push Engine
     * @param engine {@link IPushEngine}
     */
    public static void setEngine(final IPushEngine engine) {
        DevPushEngine.sEngine = engine;
    }

    // =

    private static final Map<String, IPushEngine> sEngineMaps = new LinkedHashMap<>();

    /**
     * 获取 Push Engine
     * @param key key
     * @return {@link IPushEngine}
     */
    public static IPushEngine getEngine(final String key) {
        return sEngineMaps.get(key);
    }

    /**
     * 设置 Push Engine
     * @param key    key
     * @param engine {@link IPushEngine}
     */
    public static void setEngine(
            final String key,
            final IPushEngine engine
    ) {
        sEngineMaps.put(key, engine);
    }

    /**
     * 是否存在 Push Engine
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
    public static Map<String, IPushEngine> getsEngineMaps() {
        return sEngineMaps;
    }
}