package dev.engine.image;

/**
 * detail: Image Engine
 * @author Ttt
 */
public final class DevImageEngine {

    private DevImageEngine() {
    }

    private static IImageEngine sEngine;

    /**
     * 获取 ImageEngine
     * @return {@link IImageEngine}
     */
    public static IImageEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 ImageEngine
     * @param engine {@link IImageEngine}
     */
    public static void setEngine(final IImageEngine engine) {
        DevImageEngine.sEngine = engine;
    }
}