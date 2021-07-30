package dev.engine.json;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * detail: JSON Engine
 * @author Ttt
 */
public final class DevJSONEngine {

    private DevJSONEngine() {
    }

    private static IJSONEngine sEngine;

    /**
     * 获取 JSON Engine
     * @return {@link IJSONEngine}
     */
    public static IJSONEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 JSON Engine
     * @param engine {@link IJSONEngine}
     */
    public static void setEngine(final IJSONEngine engine) {
        DevJSONEngine.sEngine = engine;
    }

    // =

    private static final Map<String, IJSONEngine> sEngineMaps = new LinkedHashMap<>();

    /**
     * 获取 JSON Engine
     * @param key key
     * @return {@link IJSONEngine}
     */
    public static IJSONEngine getEngine(final String key) {
        return sEngineMaps.get(key);
    }

    /**
     * 设置 JSON Engine
     * @param key    key
     * @param engine {@link IJSONEngine}
     */
    public static void setEngine(
            final String key,
            final IJSONEngine engine
    ) {
        sEngineMaps.put(key, engine);
    }

    /**
     * 是否存在 JSON Engine
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
    public static Map<String, IJSONEngine> getsEngineMaps() {
        return sEngineMaps;
    }
}