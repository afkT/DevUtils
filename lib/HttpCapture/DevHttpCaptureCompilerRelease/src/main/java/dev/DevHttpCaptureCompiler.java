package dev;

import android.content.Context;

import dev.capture.UrlFunctionGet;

/**
 * detail: OkHttp 抓包工具库
 * @author Ttt
 * <pre>
 *     GitHub
 *     @see <a href="https://github.com/afkT/DevUtils"/>
 *     DevApp Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md"/>
 *     DevAssist Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md"/>
 *     DevBase README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md"/>
 *     DevBaseMVVM README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/README.md"/>
 *     DevEngine README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md"/>
 *     DevHttpCapture Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md"/>
 *     DevHttpManager Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md"/>
 *     DevRetrofit Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md"/>
 *     DevJava Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md"/>
 *     DevWidget Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md"/>
 *     DevEnvironment Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/Environment"/>
 * </pre>
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
     * 跳转抓包数据可视化 Activity
     * @param context {@link Context}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean start(final Context context) {
        return false;
    }

    /**
     * 跳转抓包数据可视化 Activity
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