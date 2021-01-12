package dev.engine.log;

/**
 * detail: Log Engine
 * @author Ttt
 */
public final class DevLogEngine {

    private DevLogEngine() {
    }

    private static ILogEngine sEngine;

    /**
     * 获取 LogEngine
     * @return {@link ILogEngine}
     */
    public static ILogEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 LogEngine
     * @param engine {@link ILogEngine}
     */
    public static void setEngine(final ILogEngine engine) {
        DevLogEngine.sEngine = engine;
    }
}