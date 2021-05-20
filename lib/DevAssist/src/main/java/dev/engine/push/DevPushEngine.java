package dev.engine.push;

/**
 * detail: Push Engine
 * @author Ttt
 */
public final class DevPushEngine {

    private DevPushEngine() {
    }

    private static IPushEngine sEngine;

    /**
     * 获取 Push Engine
     * @return {@link IPushEngine}
     */
    public static IPushEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Push Engine
     * @param engine {@link IPushEngine}
     */
    public static void setEngine(final IPushEngine engine) {
        DevPushEngine.sEngine = engine;
    }
}