package dev.engine.image;

import android.widget.ImageView;

/**
 * detail: Image Engine
 * @author Ttt
 * <pre>
 *     Application: DevImageEngine.initEngine()
 *     use: DevImageEngine.xxx
 *     test if: getTranscodeType() == Bitmap.class
 * </pre>
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
    public static void initEngine(final IImageEngine imageEngine) {
        DevImageEngine.sImageEngine = imageEngine;
    }

    // ================
    // = IImageEngine =
    // ================

    // ===========
    // = 图片显示 =
    // ===========

    /**
     * 图片显示
     * @param uri       Image Uri
     * @param imageView ImageView
     */
    public static void displayImage(final String uri, final ImageView imageView) {
        if (sImageEngine != null) {
            sImageEngine.displayImage(uri, imageView);
        }
    }

    /**
     * 图片显示
     * @param uri          Image Uri
     * @param imageView    ImageView
     * @param defaultImage Default resource image
     */
    public static void displayImage(final String uri, final ImageView imageView, final int defaultImage) {
        if (sImageEngine != null) {
            sImageEngine.displayImage(uri, imageView, defaultImage);
        }
    }

    /**
     * 图片显示
     * @param uri       Image Uri
     * @param imageView ImageView
     * @param isDefault 是否使用默认配置
     */
    public static void displayImage(final String uri, final ImageView imageView, final boolean isDefault) {
        if (sImageEngine != null) {
            sImageEngine.displayImage(uri, imageView, isDefault);
        }
    }

    /**
     * 图片显示
     * @param uri       Image Uri
     * @param imageView ImageView
     * @param config    {@link IImageEngine.ImageConfig} 加载配置
     */
    public static void displayImage(final String uri, final ImageView imageView, final IImageEngine.ImageConfig config) {
        if (sImageEngine != null) {
            sImageEngine.displayImage(uri, imageView, config);
        }
    }

    // ===========
    // = 图片加载 =
    // ===========

    /**
     * 图片加载
     * @param uri      Image Uri
     * @param callback 图片加载回调
     */
    public static void loadImage(final String uri, final IImageEngine.ImageCallback callback) {
        if (sImageEngine != null) {
            sImageEngine.loadImage(uri, callback);
        }
    }

    /**
     * 图片加载
     * @param uri      Image Uri
     * @param callback 图片加载回调
     * @param config   {@link IImageEngine.ImageConfig} 加载配置
     */
    public static void loadImage(final String uri, final IImageEngine.ImageCallback callback, final IImageEngine.ImageConfig config) {
        if (sImageEngine != null) {
            sImageEngine.loadImage(uri, callback, config);
        }
    }

    // ===========
    // = 其他方法 =
    // ===========

    /**
     * 清除磁盘缓存
     */
    public static void clearDiskCache() {
        if (sImageEngine != null) {
            sImageEngine.clearDiskCache();
        }
    }

    /**
     * 清除内存缓存
     */
    public static void clearMemoryCache() {
        if (sImageEngine != null) {
            sImageEngine.clearMemoryCache();
        }
    }
}