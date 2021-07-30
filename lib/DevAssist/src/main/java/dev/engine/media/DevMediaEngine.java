package dev.engine.media;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * detail: Media Selector Engine
 * @author Ttt
 */
public final class DevMediaEngine {

    private DevMediaEngine() {
    }

    private static IMediaEngine sEngine;

    /**
     * 获取 Media Engine
     * @return {@link IMediaEngine}
     */
    public static IMediaEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Media Engine
     * @param engine {@link IMediaEngine}
     */
    public static void setEngine(final IMediaEngine engine) {
        DevMediaEngine.sEngine = engine;
    }

    // =

    private static final Map<String, IMediaEngine> sEngineMaps = new LinkedHashMap<>();

    /**
     * 获取 Media Engine
     * @param key key
     * @return {@link IMediaEngine}
     */
    public static IMediaEngine getEngine(final String key) {
        return sEngineMaps.get(key);
    }

    /**
     * 设置 Media Engine
     * @param key    key
     * @param engine {@link IMediaEngine}
     */
    public static void setEngine(
            final String key,
            final IMediaEngine engine
    ) {
        sEngineMaps.put(key, engine);
    }

    /**
     * 是否存在 Media Engine
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
    public static Map<String, IMediaEngine> getsEngineMaps() {
        return sEngineMaps;
    }
}