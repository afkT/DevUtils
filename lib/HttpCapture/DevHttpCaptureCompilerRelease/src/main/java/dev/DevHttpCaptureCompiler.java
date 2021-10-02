package dev;

import android.content.Context;

import dev.capture.UrlFunctionGet;

/**
 * detail: OKHttp 抓包工具库
 * @author Ttt
 */
public final class DevHttpCaptureCompiler {

    private DevHttpCaptureCompiler() {
    }

    // =============
    // = 对外提供方法 =
    // =============

    /**
     * 结束所有 Activity
     */
    public static void finishAllActivity() {
    }

    // ==========
    // = 跳转方法 =
    // ==========

    /**
     * 跳转 DevHttpCaptureMainActivity Activity
     * @param context {@link Context}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean start(final Context context) {
        return false;
    }

    /**
     * 跳转 DevHttpCaptureMainActivity Activity
     * @param context    {@link Context}
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean start(
            final Context context,
            final String moduleName
    ) {
        return false;
    }

    /**
     * 添加接口所属功能注释
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param function   接口所属功能注释获取
     */
    public static void putUrlFunction(
            final String moduleName,
            final UrlFunctionGet function
    ) {
    }

    /**
     * 移除接口所属功能注释
     * @param moduleName 模块名 ( 要求唯一性 )
     */
    public static void removeUrlFunction(final String moduleName) {
    }
}