package dev.engine.http;

/**
 * detail: Http Engine
 * @author Ttt
 */
public final class DevHttpEngine {

    private DevHttpEngine() {
    }

    private static IHttpEngine sEngine;

    /**
     * 获取 HttpEngine
     * @return {@link IHttpEngine}
     */
    public static IHttpEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 HttpEngine
     * @param engine {@link IHttpEngine}
     */
    public static void setEngine(final IHttpEngine engine) {
        DevHttpEngine.sEngine = engine;
    }
}