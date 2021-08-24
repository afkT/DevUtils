package dev.engine.storage;

import java.util.Map;

import dev.engine.DevEngineAssist;
import dev.engine.storage.IStorageEngine.EngineItem;
import dev.engine.storage.IStorageEngine.EngineResult;

/**
 * detail: Storage Engine
 * @author Ttt
 */
public final class DevStorageEngine {

    private DevStorageEngine() {
    }

    private static final DevEngineAssist<IStorageEngine<? super EngineItem, ? super EngineResult>> sAssist = new DevEngineAssist<>();

    /**
     * 获取 Engine
     * @return {@link IStorageEngine}
     */
    public static IStorageEngine<? super EngineItem, ? super EngineResult> getEngine() {
        return sAssist.getEngine();
    }

    /**
     * 获取 Engine
     * @param key key
     * @return {@link IStorageEngine}
     */
    public static IStorageEngine<? super EngineItem, ? super EngineResult> getEngine(final String key) {
        return sAssist.getEngine(key);
    }

    /**
     * 设置 Engine
     * @param engine {@link IStorageEngine}
     * @return {@link IStorageEngine}
     */
    public static IStorageEngine setEngine(final IStorageEngine engine) {
        return sAssist.setEngine(engine);
    }

    /**
     * 设置 Engine
     * @param key    key
     * @param engine {@link IStorageEngine}
     * @return {@link IStorageEngine}
     */
    public static IStorageEngine setEngine(
            final String key,
            final IStorageEngine engine
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
    public static DevEngineAssist<IStorageEngine<? super EngineItem, ? super EngineResult>> getAssist() {
        return sAssist;
    }

    /**
     * 获取 Engine Map
     * @return Engine Map
     */
    public static Map<String, IStorageEngine<? super EngineItem, ? super EngineResult>> getEngineMaps() {
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