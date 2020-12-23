package dev.utils.app.toast.toaster;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;

/**
 * detail: Toast 工具类 ( 支持子线程弹出 Toast, 处理无通知权限 )
 * @author Ttt
 * <pre>
 *     支持子线程弹出 Toast, 可通过开关配置
 *     内部解决 Android 7.1.1 崩溃问题
 *     已处理 部分 ROM 如魅族、小米、三星等关闭应用通知, 无法显示 Toast 问题
 *     <p></p>
 *     缺点: 同时间只能显示一个 Toast
 * </pre>
 */
public final class DevToast {

    private DevToast() {
    }

    // 包下 IToastImpl 类持有对象
    private static final IToast.Operate sToast = new IToastImpl();

    /**
     * 重置默认参数
     */
    public static void reset() {
        sToast.reset();
    }

    /**
     * 设置是否使用 Handler 显示 Toast
     * @param isHandler {@code true} 使用, {@code false} 不使用
     */
    public static void setIsHandler(final boolean isHandler) {
        sToast.setIsHandler(isHandler);
    }

    /**
     * 设置 Text 为 null 的文本
     * @param nullText 显示内容为 null 时, 使用的提示值
     */
    public static void setNullText(final String nullText) {
        sToast.setNullText(nullText);
    }

    /**
     * 设置 Toast 文案长度转换 显示时间
     * @param textLengthConvertDuration Toast 文案长度转换界限
     */
    public static void setTextLength(final int textLengthConvertDuration) {
        sToast.setTextLength(textLengthConvertDuration);
    }

    // =

    /**
     * 初始化调用 ( 内部已调用 )
     * @param context {@link Context}
     */
    public static void init(final Context context) {
        sToast.init(context);
    }

    // ===========
    // = 配置方法 =
    // ===========

    /**
     * 使用单次 Toast 样式配置
     * @param toastStyle Toast 样式
     * @return {@link IToast.Operate}
     */
    public static IToast.Operate style(final IToast.Style toastStyle) {
        return sToast.style(toastStyle);
    }

    /**
     * 使用默认 Toast 样式
     * @return {@link IToast.Operate}
     */
    public static IToast.Operate defaultStyle() {
        return sToast.defaultStyle();
    }

    /**
     * 获取 Toast 样式配置
     * @return Toast 样式配置
     */
    public static IToast.Style getToastStyle() {
        return sToast.getToastStyle();
    }

    /**
     * 初始化 Toast 样式配置
     * @param toastStyle Toast 样式配置
     */
    public static void initStyle(final IToast.Style toastStyle) {
        sToast.initStyle(toastStyle);
    }

    /**
     * 初始化 Toast 过滤器
     * @param toastFilter Toast 过滤器
     */
    public static void initToastFilter(final IToast.Filter toastFilter) {
        sToast.initToastFilter(toastFilter);
    }

    /**
     * 设置 Toast 显示的 View
     * @param view Toast 显示的 View
     */
    public static void setView(final View view) {
        sToast.setView(view);
    }

    /**
     * 设置 Toast 显示的 View
     * @param layoutId R.layout.id
     */
    public static void setView(@LayoutRes final int layoutId) {
        sToast.setView(layoutId);
    }

    // ===========
    // = 操作方法 =
    // ===========

    /**
     * 显示 Toast
     * @param text       Toast 提示文本
     * @param formatArgs 格式化参数
     */
    public static void show(
            final String text,
            final Object... formatArgs
    ) {
        sToast.show(text, formatArgs);
    }

    /**
     * 显示 R.string.id Toast
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     */
    public static void show(
            @StringRes final int resId,
            final Object... formatArgs
    ) {
        sToast.show(resId, formatArgs);
    }

    /**
     * 通过 View 显示 Toast
     * @param view Toast 显示的 View
     */
    public static void show(final View view) {
        sToast.show(view);
    }

    /**
     * 通过 View 显示 Toast
     * @param view     Toast 显示的 View
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void show(
            final View view,
            final int duration
    ) {
        sToast.show(view, duration);
    }

    // =

    /**
     * 取消当前显示的 Toast
     */
    public static void cancel() {
        sToast.cancel();
    }
}