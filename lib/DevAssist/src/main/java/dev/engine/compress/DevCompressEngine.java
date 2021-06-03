package dev.engine.compress;

import java.util.HashMap;
import java.util.Map;

/**
 * detail: Image Compress Engine
 * @author Ttt
 */
public final class DevCompressEngine {

    private DevCompressEngine() {
    }

    private static ICompressEngine sEngine;

    /**
     * 获取 Compress Engine
     * @return {@link ICompressEngine}
     */
    public static ICompressEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Compress Engine
     * @param engine {@link ICompressEngine}
     */
    public static void setEngine(final ICompressEngine engine) {
        DevCompressEngine.sEngine = engine;
    }

    // =

    private static final Map<String, ICompressEngine> sEngineMaps = new HashMap<>();

    /**
     * 获取 Compress Engine
     * @param key key
     * @return {@link ICompressEngine}
     */
    public static ICompressEngine getEngine(final String key) {
        return sEngineMaps.get(key);
    }

    /**
     * 设置 Compress Engine
     * @param key    key
     * @param engine {@link ICompressEngine}
     */
    public static void setEngine(
            final String key,
            final ICompressEngine engine
    ) {
        sEngineMaps.put(key, engine);
    }

    /**
     * 是否存在 Compress Engine
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
    public static Map<String, ICompressEngine> getsEngineMaps() {
        return sEngineMaps;
    }
}