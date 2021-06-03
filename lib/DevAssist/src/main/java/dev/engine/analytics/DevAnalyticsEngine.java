package dev.engine.analytics;

import java.util.HashMap;
import java.util.Map;

/**
 * detail: Analytics Engine
 * @author Ttt
 */
public final class DevAnalyticsEngine {

    private DevAnalyticsEngine() {
    }

    private static IAnalyticsEngine sEngine;

    /**
     * 获取 Analytics Engine
     * @return {@link IAnalyticsEngine}
     */
    public static IAnalyticsEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Analytics Engine
     * @param engine {@link IAnalyticsEngine}
     */
    public static void setEngine(final IAnalyticsEngine engine) {
        DevAnalyticsEngine.sEngine = engine;
    }

    // =

    private static final Map<String, IAnalyticsEngine> sEngineMaps = new HashMap<>();

    /**
     * 获取 Analytics Engine
     * @param key key
     * @return {@link IAnalyticsEngine}
     */
    public static IAnalyticsEngine getEngine(final String key) {
        return sEngineMaps.get(key);
    }

    /**
     * 设置 Analytics Engine
     * @param key    key
     * @param engine {@link IAnalyticsEngine}
     */
    public static void setEngine(
            final String key,
            final IAnalyticsEngine engine
    ) {
        sEngineMaps.put(key, engine);
    }

    /**
     * 是否存在 Analytics Engine
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
    public static Map<String, IAnalyticsEngine> getsEngineMaps() {
        return sEngineMaps;
    }
}