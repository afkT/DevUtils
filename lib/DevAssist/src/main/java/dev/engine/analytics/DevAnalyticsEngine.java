package dev.engine.analytics;

/**
 * detail: Analytics Engine
 * @author Ttt
 */
public final class DevAnalyticsEngine {

    private DevAnalyticsEngine() {
    }

    private static IAnalyticsEngine sEngine;

    /**
     * 获取 Analytics Engine
     * @return {@link IAnalyticsEngine}
     */
    public static IAnalyticsEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Analytics Engine
     * @param engine {@link IAnalyticsEngine}
     */
    public static void setEngine(final IAnalyticsEngine engine) {
        DevAnalyticsEngine.sEngine = engine;
    }
}