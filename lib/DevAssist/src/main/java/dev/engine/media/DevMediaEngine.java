package dev.engine.media;

/**
 * detail: Media Selector Engine
 * @author Ttt
 */
public final class DevMediaEngine {

    private DevMediaEngine() {
    }

    private static IMediaEngine sEngine;

    /**
     * 获取 MediaEngine
     * @return {@link IMediaEngine}
     */
    public static IMediaEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 MediaEngine
     * @param engine {@link IMediaEngine}
     */
    public static void setEngine(final IMediaEngine engine) {
        DevMediaEngine.sEngine = engine;
    }
}