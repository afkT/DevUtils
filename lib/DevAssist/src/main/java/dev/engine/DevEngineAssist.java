package dev.engine;

import java.util.LinkedHashMap;
import java.util.Map;

import dev.utils.DevFinal;

/**
 * detail: DevEngine Generic Assist
 * @author Ttt
 */
public class DevEngineAssist<Engine> {

    // Engine Map
    private final Map<String, Engine> mEngineMaps = new LinkedHashMap<>();

    /**
     * 获取 Engine
     * @return {@link Engine}
     */
    public Engine getEngine() {
        return getEngine(DevFinal.STR.DEFAULT);
    }

    /**
     * 获取 Engine
     * @param key key
     * @return {@link Engine}
     */
    public Engine getEngine(final String key) {
        return mEngineMaps.get(key);
    }

    /**
     * 设置 Engine
     * @param engine {@link Engine}
     * @return {@link Engine}
     */
    public Engine setEngine(final Engine engine) {
        return setEngine(DevFinal.STR.DEFAULT, engine);
    }

    /**
     * 设置 Engine
     * @param key    key
     * @param engine {@link Engine}
     * @return {@link Engine}
     */
    public Engine setEngine(
            final String key,
            final Engine engine
    ) {
        mEngineMaps.put(key, engine);
        return engine;
    }

    /**
     * 移除 Engine
     */
    public void removeEngine() {
        removeEngine(DevFinal.STR.DEFAULT);
    }

    /**
     * 移除 Engine
     * @param key key
     */
    public void removeEngine(final String key) {
        mEngineMaps.remove(key);
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 获取 Engine Map
     * @return Engine Map
     */
    public Map<String, Engine> getEngineMaps() {
        return mEngineMaps;
    }

    /**
     * 是否存在 Engine
     * @return {@code true} yes, {@code false} no
     */
    public boolean contains() {
        return contains(DevFinal.STR.DEFAULT);
    }

    /**
     * 是否存在 Engine
     * @param key key
     * @return {@code true} yes, {@code false} no
     */
    public boolean contains(final String key) {
        return mEngineMaps.containsKey(key);
    }

    /**
     * 判断 Engine 是否为 null
     * @return {@code true} yes, {@code false} no
     */
    public boolean isEmpty() {
        return isEmpty(DevFinal.STR.DEFAULT);
    }

    /**
     * 判断 Engine 是否为 null
     * @param key key
     * @return {@code true} yes, {@code false} no
     */
    public boolean isEmpty(final String key) {
        return getEngine(key) == null;
    }
}