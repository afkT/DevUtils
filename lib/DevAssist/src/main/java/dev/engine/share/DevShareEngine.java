package dev.engine.share;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * detail: Share Engine
 * @author Ttt
 */
public final class DevShareEngine {

    private DevShareEngine() {
    }

    private static IShareEngine sEngine;

    /**
     * 获取 Share Engine
     * @return {@link IShareEngine}
     */
    public static IShareEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Share Engine
     * @param engine {@link IShareEngine}
     */
    public static void setEngine(final IShareEngine engine) {
        DevShareEngine.sEngine = engine;
    }

    // =

    private static final Map<String, IShareEngine> sEngineMaps = new LinkedHashMap<>();

    /**
     * 获取 Share Engine
     * @param key key
     * @return {@link IShareEngine}
     */
    public static IShareEngine getEngine(final String key) {
        return sEngineMaps.get(key);
    }

    /**
     * 设置 Share Engine
     * @param key    key
     * @param engine {@link IShareEngine}
     */
    public static void setEngine(
            final String key,
            final IShareEngine engine
    ) {
        sEngineMaps.put(key, engine);
    }

    /**
     * 是否存在 Share Engine
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
    public static Map<String, IShareEngine> getsEngineMaps() {
        return sEngineMaps;
    }
}