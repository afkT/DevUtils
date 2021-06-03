package dev.engine.permission;

import java.util.HashMap;
import java.util.Map;

/**
 * detail: Permission Engine
 * @author Ttt
 */
public final class DevPermissionEngine {

    private DevPermissionEngine() {
    }

    private static IPermissionEngine sEngine;

    /**
     * 获取 Permission Engine
     * @return {@link IPermissionEngine}
     */
    public static IPermissionEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Permission Engine
     * @param engine {@link IPermissionEngine}
     */
    public static void setEngine(final IPermissionEngine engine) {
        DevPermissionEngine.sEngine = engine;
    }

    // =

    private static final Map<String, IPermissionEngine> sEngineMaps = new HashMap<>();

    /**
     * 获取 Permission Engine
     * @param key key
     * @return {@link IPermissionEngine}
     */
    public static IPermissionEngine getEngine(final String key) {
        return sEngineMaps.get(key);
    }

    /**
     * 设置 Permission Engine
     * @param key    key
     * @param engine {@link IPermissionEngine}
     */
    public static void setEngine(
            final String key,
            final IPermissionEngine engine
    ) {
        sEngineMaps.put(key, engine);
    }

    /**
     * 是否存在 Permission Engine
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
    public static Map<String, IPermissionEngine> getsEngineMaps() {
        return sEngineMaps;
    }
}