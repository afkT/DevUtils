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
     * 获取 Http Engine
     * @return {@link IHttpEngine}
     */
    public static IHttpEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 Http Engine
     * @param engine {@link IHttpEngine}
     */
    public static void setEngine(final IHttpEngine engine) {
        DevHttpEngine.sEngine = engine;
    }
}