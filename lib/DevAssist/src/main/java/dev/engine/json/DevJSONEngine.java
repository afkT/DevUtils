package dev.engine.json;

import java.util.Map;

import dev.engine.DevEngineAssist;
import dev.engine.json.IJSONEngine.EngineConfig;

/**
 * detail: JSON Engine
 * @author Ttt
 */
public final class DevJSONEngine {

    private DevJSONEngine() {
    }

    private static final DevEngineAssist<IJSONEngine<? super EngineConfig>> sAssist = new DevEngineAssist<>();

    /**
     * 获取 Engine
     * @return {@link IJSONEngine}
     */
    public static IJSONEngine<? super EngineConfig> getEngine() {
        return sAssist.getEngine();
    }

    /**
     * 获取 Engine
     * @param key key
     * @return {@link IJSONEngine}
     */
    public static IJSONEngine<? super EngineConfig> getEngine(final String key) {
        return sAssist.getEngine(key);
    }

    /**
     * 设置 Engine
     * @param engine {@link IJSONEngine}
     * @return {@link IJSONEngine}
     */
    public static IJSONEngine setEngine(final IJSONEngine engine) {
        return sAssist.setEngine(engine);
    }

    /**
     * 设置 Engine
     * @param key    key
     * @param engine {@link IJSONEngine}
     * @return {@link IJSONEngine}
     */
    public static IJSONEngine setEngine(
            final String key,
            final IJSONEngine engine
    ) {
        return sAssist.setEngine(key, engine);
    }

    /**
     * 移除 Engine
     */
    public static void removeEngine() {
        sAssist.removeEngine();
    }

    /**
     * 移除 Engine
     * @param key key
     */
    public static void removeEngine(final String key) {
        sAssist.removeEngine(key);
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 获取 DevEngine Generic Assist
     * @return DevEngine Generic Assist
     */
    public static DevEngineAssist<IJSONEngine<? super EngineConfig>> getAssist() {
        return sAssist;
    }

    /**
     * 获取 Engine Map
     * @return Engine Map
     */
    public static Map<String, IJSONEngine<? super EngineConfig>> getEngineMaps() {
        return sAssist.getEngineMaps();
    }

    /**
     * 是否存在 Engine
     * @return {@code true} yes, {@code false} no
     */
    public static boolean contains() {
        return sAssist.contains();
    }

    /**
     * 是否存在 Engine
     * @param key key
     * @return {@code true} yes, {@code false} no
     */
    public static boolean contains(final String key) {
        return sAssist.contains(key);
    }

    /**
     * 判断 Engine 是否为 null
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty() {
        return sAssist.isEmpty();
    }

    /**
     * 判断 Engine 是否为 null
     * @param key key
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final String key) {
        return sAssist.isEmpty(key);
    }
}