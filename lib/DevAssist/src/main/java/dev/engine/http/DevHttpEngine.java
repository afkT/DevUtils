package dev.engine.http;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * detail: Http Engine
 * @author Ttt
 */
public final class DevHttpEngine {

    private DevHttpEngine() {
    }

    private static IHttpEngine sEngine;

    /**
     * 获取 Http Engine
     * @return {@link IHttpEngine}
     */
    public static IHttpEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Http Engine
     * @param engine {@link IHttpEngine}
     */
    public static void setEngine(final IHttpEngine engine) {
        DevHttpEngine.sEngine = engine;
    }

    // =

    private static final Map<String, IHttpEngine> sEngineMaps = new LinkedHashMap<>();

    /**
     * 获取 Http Engine
     * @param key key
     * @return {@link IHttpEngine}
     */
    public static IHttpEngine getEngine(final String key) {
        return sEngineMaps.get(key);
    }

    /**
     * 设置 Http Engine
     * @param key    key
     * @param engine {@link IHttpEngine}
     */
    public static void setEngine(
            final String key,
            final IHttpEngine engine
    ) {
        sEngineMaps.put(key, engine);
    }

    /**
     * 是否存在 Http Engine
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
    public static Map<String, IHttpEngine> getsEngineMaps() {
        return sEngineMaps;
    }
}