package dev.engine.log;

import java.util.HashMap;
import java.util.Map;

/**
 * detail: Log Engine
 * @author Ttt
 */
public final class DevLogEngine {

    private DevLogEngine() {
    }

    private static ILogEngine sEngine;

    /**
     * 获取 Log Engine
     * @return {@link ILogEngine}
     */
    public static ILogEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Log Engine
     * @param engine {@link ILogEngine}
     */
    public static void setEngine(final ILogEngine engine) {
        DevLogEngine.sEngine = engine;
    }

    // =

    private static final Map<String, ILogEngine> sEngineMaps = new HashMap<>();

    /**
     * 获取 Log Engine
     * @param key key
     * @return {@link ILogEngine}
     */
    public static ILogEngine getEngine(final String key) {
        return sEngineMaps.get(key);
    }

    /**
     * 设置 Log Engine
     * @param key    key
     * @param engine {@link ILogEngine}
     */
    public static void setEngine(
            final String key,
            final ILogEngine engine
    ) {
        sEngineMaps.put(key, engine);
    }

    /**
     * 是否存在 Log Engine
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
    public static Map<String, ILogEngine> getsEngineMaps() {
        return sEngineMaps;
    }
}