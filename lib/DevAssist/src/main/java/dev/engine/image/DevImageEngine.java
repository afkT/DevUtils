package dev.engine.image;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * detail: Image Engine
 * @author Ttt
 */
public final class DevImageEngine {

    private DevImageEngine() {
    }

    private static IImageEngine sEngine;

    /**
     * 获取 Image Engine
     * @return {@link IImageEngine}
     */
    public static IImageEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Image Engine
     * @param engine {@link IImageEngine}
     */
    public static void setEngine(final IImageEngine engine) {
        DevImageEngine.sEngine = engine;
    }

    // =

    private static final Map<String, IImageEngine> sEngineMaps = new LinkedHashMap<>();

    /**
     * 获取 Image Engine
     * @param key key
     * @return {@link IImageEngine}
     */
    public static IImageEngine getEngine(final String key) {
        return sEngineMaps.get(key);
    }

    /**
     * 设置 Image Engine
     * @param key    key
     * @param engine {@link IImageEngine}
     */
    public static void setEngine(
            final String key,
            final IImageEngine engine
    ) {
        sEngineMaps.put(key, engine);
    }

    /**
     * 是否存在 Image Engine
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
    public static Map<String, IImageEngine> getsEngineMaps() {
        return sEngineMaps;
    }
}