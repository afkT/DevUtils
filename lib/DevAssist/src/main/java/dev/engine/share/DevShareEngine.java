package dev.engine.share;

import java.util.Map;

import dev.engine.DevEngineAssist;
import dev.engine.share.IShareEngine.EngineConfig;
import dev.engine.share.IShareEngine.EngineItem;

/**
 * detail: Share Engine
 * @author Ttt
 */
public final class DevShareEngine {

    private DevShareEngine() {
    }

    private static final DevEngineAssist<IShareEngine<? super EngineConfig, ? super EngineItem>> sAssist = new DevEngineAssist<>();

    /**
     * 获取 Engine
     * @return {@link IShareEngine}
     */
    public static IShareEngine<? super EngineConfig, ? super EngineItem> getEngine() {
        return sAssist.getEngine();
    }

    /**
     * 获取 Engine
     * @param key key
     * @return {@link IShareEngine}
     */
    public static IShareEngine<? super EngineConfig, ? super EngineItem> getEngine(final String key) {
        return sAssist.getEngine(key);
    }

    /**
     * 设置 Engine
     * @param engine {@link IShareEngine}
     * @return {@link IShareEngine}
     */
    public static IShareEngine setEngine(final IShareEngine engine) {
        return sAssist.setEngine(engine);
    }

    /**
     * 设置 Engine
     * @param key    key
     * @param engine {@link IShareEngine}
     * @return {@link IShareEngine}
     */
    public static IShareEngine setEngine(
            final String key,
            final IShareEngine engine
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
    public static DevEngineAssist<IShareEngine<? super EngineConfig, ? super EngineItem>> getAssist() {
        return sAssist;
    }

    /**
     * 获取 Engine Map
     * @return Engine Map
     */
    public static Map<String, IShareEngine<? super EngineConfig, ? super EngineItem>> getEngineMaps() {
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