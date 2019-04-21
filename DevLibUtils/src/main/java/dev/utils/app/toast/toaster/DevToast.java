package dev.utils.app.toast.toaster;

import android.app.Application;
import android.view.View;

/**
 * detail: Toast 工具类 (支持子线程弹出 Toast, 处理无通知权限)
 * @author Ttt
 * <pre>
 *      支持子线程弹出 Toast, 可通过开关配置
 *      内部解决 Android 7.1.1 崩溃问题
 *      已处理 部分ROM 如魅族、小米、三星等关闭应用通知，无法显示 Toast 问题
 *      <p></p>
 *      缺点: 同时间只能显示一个 Toast
 * </pre>
 */
public final class DevToast {

    private DevToast() {
    }

    // 包下 IToastImpl 类持有对象
    private static final IToast.Operate toast = new IToastImpl();

    /**
     * 重置默认参数
     */
    public static void reset() {
        toast.reset();
    }

    /**
     * 设置是否使用 Handler 显示 Toast
     * @param isHandler
     */
    public static void setIsHandler(final boolean isHandler) {
        toast.setIsHandler(isHandler);
    }

    /**
     * 设置 Text 为 null 的文本
     * @param nullText
     */
    public static void setNullText(final String nullText) {
        toast.setNullText(nullText);
    }

    /**
     * 设置 Toast 文案长度转换 显示时间
     * @param textLengthConvertDuration
     */
    public static void setTextLength(final int textLengthConvertDuration) {
        toast.setTextLength(textLengthConvertDuration);
    }

    // =

    /**
     * Application 初始化调用 (必须调用 -> 内部已经调用)
     * @param application
     */
    public static void init(final Application application) {
        toast.init(application);
    }

    // = 配置方法 =

    /**
     * 使用单次 Toast 样式配置
     * @param toastStyle Toast 样式
     * @return
     */
    public static IToast.Operate style(final IToast.Style toastStyle) {
        return toast.style(toastStyle);
    }

    /**
     * 使用默认 Toast 样式
     * @return
     */
    public static IToast.Operate defaultStyle() {
        return toast.defaultStyle();
    }

    /**
     * 获取 Toast 样式配置
     * @return Toast 样式配置
     */
    public static IToast.Style getToastStyle() {
        return toast.getToastStyle();
    }

    /**
     * 初始化 Toast 样式配置(非单次,一直持续)
     * @param toastStyle Toast 样式配置
     */
    public static void initStyle(final IToast.Style toastStyle) {
        toast.initStyle(toastStyle);
    }

    /**
     * 初始化 Toast 过滤器
     * @param toastFilter
     */
    public static void initToastFilter(final IToast.Filter toastFilter) {
        toast.initToastFilter(toastFilter);
    }

    /**
     * 设置 Toast 显示的View
     * @param view
     */
    public static void setView(final View view) {
        toast.setView(view);
    }

    /**
     * 设置 Toast 显示的View
     * @param layoutId
     */
    public static void setView(final int layoutId) {
        toast.setView(layoutId);
    }

    // ============
    // = 操作方法 =
    // ============

    /**
     * 显示 Toast
     * @param content
     * @param args
     */
    public static void show(final String content, final Object... args) {
        toast.show(content, args);
    }

    /**
     * 显示 R.string.resId Toast
     * @param resId
     * @param args
     */
    public static void show(final int resId, final Object... args) {
        toast.show(resId, args);
    }

    /**
     * 通过 View 显示 Toast
     * @param view
     */
    public static void show(final View view) {
        toast.show(view);
    }

    /**
     * 通过 View 显示 Toast
     * @param view
     * @param duration
     */
    public static void show(final View view, final int duration) {
        toast.show(view, duration);
    }

    // =

    /**
     * 取消当前显示的 Toast
     */
    public static void cancel() {
        toast.cancel();
    }
}
