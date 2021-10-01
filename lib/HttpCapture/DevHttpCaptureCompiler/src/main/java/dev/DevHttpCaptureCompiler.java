package dev;

import android.content.Context;
import android.content.Intent;

import dev.capture.DevHttpCaptureMainActivity;
import dev.capture.UtilsCompiler;
import dev.utils.DevFinal;
import dev.utils.LogPrintUtils;

/**
 * detail: OKHttp 抓包工具库
 * @author Ttt
 */
public final class DevHttpCaptureCompiler {

    private DevHttpCaptureCompiler() {
    }

    // 日志 TAG
    private static final String TAG = DevHttpCaptureCompiler.class.getSimpleName();

    // =============
    // = 对外提供方法 =
    // =============

    /**
     * 结束所有 Activity
     */
    public static void finishAllActivity() {
        UtilsCompiler.getInstance().finishAllActivity();
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
        return start(context, null);
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
        // 关闭全部页面
        finishAllActivity();
        try {
            Intent intent = new Intent(context, DevHttpCaptureMainActivity.class);
            intent.putExtra(DevFinal.MODULE, moduleName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "start by module: %s", moduleName);
        }
        return false;
    }

    /**
     * 添加接口所属功能注释
     * <pre>
     *     url 匹配规则 ( 拆分 ? 前为 key 进行匹配 )
     * </pre>
     * @param url      请求接口链接
     * @param function 接口所属功能注释
     */
    public static void putUrlFunction(
            final String url,
            final String function
    ) {
        UtilsCompiler.getInstance().putUrlFunction(
                url, function
        );
    }

    /**
     * 移除接口所属功能注释
     * @param url 请求接口链接
     */
    public static void removeUrlFunction(final String url) {
        UtilsCompiler.getInstance().removeUrlFunction(url);
    }
}