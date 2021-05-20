package dev.engine.json;

/**
 * detail: JSON Engine
 * @author Ttt
 */
public final class DevJSONEngine {

    private DevJSONEngine() {
    }

    private static IJSONEngine sEngine;

    /**
     * 获取 JSON Engine
     * @return {@link IJSONEngine}
     */
    public static IJSONEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 JSON Engine
     * @param engine {@link IJSONEngine}
     */
    public static void setEngine(final IJSONEngine engine) {
        DevJSONEngine.sEngine = engine;
    }
}