package dev.engine.image;

/**
 * detail: Image Engine
 * @author Ttt
 */
public final class DevImageEngine {

    private DevImageEngine() {
    }

    // IImageEngine
    private static IImageEngine sImageEngine;

    /**
     * 初始化 Engine
     * @param imageEngine {@link IImageEngine}
     */
    public static void initEngine(IImageEngine imageEngine) {
        DevImageEngine.sImageEngine = imageEngine;
    }
}
