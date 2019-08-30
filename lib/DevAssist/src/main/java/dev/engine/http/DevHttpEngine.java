package dev.engine.http;

/**
 * detail: Http Engine
 * @author Ttt
 */
public final class DevHttpEngine {

    private DevHttpEngine() {
    }

    // IHttpEngine
    private static IHttpEngine sHttpEngine;

    /**
     * 初始化 Engine
     * @param httpEngine {@link IHttpEngine}
     */
    public static void initEngine(IHttpEngine httpEngine) {
        DevHttpEngine.sHttpEngine = httpEngine;
    }
}
