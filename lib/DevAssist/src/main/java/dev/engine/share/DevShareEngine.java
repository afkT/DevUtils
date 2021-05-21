package dev.engine.share;

/**
 * detail: Share Engine
 * @author Ttt
 */
public final class DevShareEngine {

    private DevShareEngine() {
    }

    private static IShareEngine sEngine;

    /**
     * 获取 Share Engine
     * @return {@link IShareEngine}
     */
    public static IShareEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Share Engine
     * @param engine {@link IShareEngine}
     */
    public static void setEngine(final IShareEngine engine) {
        DevShareEngine.sEngine = engine;
    }
}