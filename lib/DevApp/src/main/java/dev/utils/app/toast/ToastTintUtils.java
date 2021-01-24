package dev.utils.app.toast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.R;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.image.ImageUtils;

/**
 * detail: 自定义 View 着色美化 Toast 工具类
 * @author Ttt
 * <pre>
 *     支持子线程弹出 Toast, 可通过开关配置
 *     内部解决 Android 7.1.1 崩溃问题
 *     但无处理 部分 ROM 如魅族、小米、三星等关闭应用通知, 无法显示 Toast 问题
 * </pre>
 */
public final class ToastTintUtils {

    private ToastTintUtils() {
    }

    // 日志 TAG
    private static final String TAG = ToastTintUtils.class.getSimpleName();

    // Toast 判断过滤
    private static       ToastTintUtils.Filter sToastFilter = null;
    // 内部持有单个 Toast
    private static       Toast                 sToast       = null;
    // 判断是否使用 Handler
    private static       boolean               sIsHandler   = true;
    // 内部 Handler
    private static final Handler               sHandler     = new Handler(Looper.getMainLooper());
    // Null 值 ( null 提示值 )
    private static       String                sNullText    = null;

    // ===========
    // = 部分配置 =
    // ===========

    // 判断是否使用配置
    private static boolean sUseConfig = true;
    // Toast 的重心、X、Y 轴偏移
    private static int     sGravity, sX, sY;
    // 水平边距、垂直边距
    private static float sHorizontalMargin, sVerticalMargin;

    // ===========
    // = 样式相关 =
    // ===========

    // 默认样式
    private static final ToastTintUtils.Style sDefaultStyle    = new DefaultStyle();
    // Normal 样式
    private static       ToastTintUtils.Style sNormalStyle     = new NormalStyle();
    // Info 样式
    private static       ToastTintUtils.Style sInfoStyle       = new InfoStyle();
    // Warning 样式
    private static       ToastTintUtils.Style sWarningStyle    = new WarningStyle();
    // Error 样式
    private static       ToastTintUtils.Style sErrorStyle      = new ErrorStyle();
    // Success 样式
    private static       ToastTintUtils.Style sSuccessStyle    = new SuccessStyle();
    // =
    // info icon
    private static       Drawable             sInfoDrawable    = null;
    // warning icon
    private static       Drawable             sWarningDrawable = null;
    // error icon
    private static       Drawable             sErrorDrawable   = null;
    // Success icon
    private static       Drawable             sSuccessDrawable = null;

    /**
     * 重置默认参数
     */
    public static void reset() {
        sIsHandler = true;
        sUseConfig = true;
        sNullText = null;
        sGravity = sX = sY = 0;
        sHorizontalMargin = sVerticalMargin = 0.0f;
    }

    /**
     * 设置 Toast 过滤器
     * @param toastFilter {@link ToastUtils.Filter}
     */
    public static void setToastFilter(final ToastTintUtils.Filter toastFilter) {
        ToastTintUtils.sToastFilter = toastFilter;
    }

    /**
     * 设置是否使用 Handler 显示 Toast
     * @param isHandler {@code true} 使用, {@code false} 不使用
     */
    public static void setIsHandler(final boolean isHandler) {
        ToastTintUtils.sIsHandler = isHandler;
    }

    /**
     * 设置 Text 为 null 的文本
     * @param nullText 显示内容为 null 时, 使用的提示值
     */
    public static void setNullText(final String nullText) {
        ToastTintUtils.sNullText = nullText;
    }

    /**
     * 设置是否使用配置
     * @param useConfig {@code true} 使用, {@code false} 不使用
     */
    public static void setUseConfig(final boolean useConfig) {
        ToastTintUtils.sUseConfig = useConfig;
    }

    /**
     * 设置 Toast 显示在屏幕上的位置
     * @param gravity 重心
     * @param xOffset X 轴偏移
     * @param yOffset Y 轴偏移
     */
    public static void setGravity(
            final int gravity,
            final int xOffset,
            final int yOffset
    ) {
        ToastTintUtils.sGravity = gravity;
        ToastTintUtils.sX = xOffset;
        ToastTintUtils.sY = yOffset;
    }

    /**
     * 设置边距
     * @param horizontalMargin 水平边距
     * @param verticalMargin   垂直边距
     */
    public static void setMargin(
            final float horizontalMargin,
            final float verticalMargin
    ) {
        ToastTintUtils.sHorizontalMargin = horizontalMargin;
        ToastTintUtils.sVerticalMargin = verticalMargin;
    }

    // =

    /**
     * 获取默认样式
     * @return {@link Style} 默认样式
     */
    public static Style getDefaultStyle() {
        return sDefaultStyle;
    }

    /**
     * 获取 Normal 样式
     * @return {@link Style} Normal 样式
     */
    public static Style getNormalStyle() {
        return sNormalStyle;
    }

    /**
     * 获取 Info 样式
     * @return {@link Style} Info 样式
     */
    public static Style getInfoStyle() {
        return sInfoStyle;
    }

    /**
     * 获取 Warning 样式
     * @return {@link Style} Warning 样式
     */
    public static Style getWarningStyle() {
        return sWarningStyle;
    }

    /**
     * 获取 Error 样式
     * @return {@link Style} Error 样式
     */
    public static Style getErrorStyle() {
        return sErrorStyle;
    }

    /**
     * 获取 Success 样式
     * @return {@link Style} Success 样式
     */
    public static Style getSuccessStyle() {
        return sSuccessStyle;
    }

    // =

    /**
     * 设置 Normal 样式
     * @param normalStyle Normal 样式
     */
    public static void setNormalStyle(final Style normalStyle) {
        ToastTintUtils.sNormalStyle = normalStyle;
    }

    /**
     * 设置 Info 样式
     * @param infoStyle Info 样式
     */
    public static void setInfoStyle(final Style infoStyle) {
        ToastTintUtils.sInfoStyle = infoStyle;
    }

    /**
     * 设置 Warning 样式
     * @param warningStyle Warning 样式
     */
    public static void setWarningStyle(final Style warningStyle) {
        ToastTintUtils.sWarningStyle = warningStyle;
    }

    /**
     * 设置 Error 样式
     * @param errorStyle Error 样式
     */
    public static void setErrorStyle(final Style errorStyle) {
        ToastTintUtils.sErrorStyle = errorStyle;
    }

    /**
     * 设置 Success 样式
     * @param successStyle Success 样式
     */
    public static void setSuccessStyle(final Style successStyle) {
        ToastTintUtils.sSuccessStyle = successStyle;
    }

    /**
     * 获取 Info 样式 icon
     * @return {@link Drawable} Info 样式 icon
     */
    public static Drawable getInfoDrawable() {
        if (sInfoDrawable != null) {
            return sInfoDrawable;
        }
        sInfoDrawable = ResourceUtils.getDrawable(R.drawable.dev_toast_icon_info_white);
        return sInfoDrawable;
    }

    /**
     * 获取 Warning 样式 icon
     * @return {@link Drawable} Warning 样式 icon
     */
    public static Drawable getWarningDrawable() {
        if (sWarningDrawable != null) {
            return sWarningDrawable;
        }
        sWarningDrawable = ResourceUtils.getDrawable(R.drawable.dev_toast_icon_warning_white);
        return sWarningDrawable;
    }

    /**
     * 获取 Error 样式 icon
     * @return {@link Drawable} Error 样式 icon
     */
    public static Drawable getErrorDrawable() {
        if (sErrorDrawable != null) {
            return sErrorDrawable;
        }
        sErrorDrawable = ResourceUtils.getDrawable(R.drawable.dev_toast_icon_error_white);
        return sErrorDrawable;
    }

    /**
     * 获取 Success 样式 icon
     * @return {@link Drawable} Success 样式 icon
     */
    public static Drawable getSuccessDrawable() {
        if (sSuccessDrawable != null) {
            return sSuccessDrawable;
        }
        sSuccessDrawable = ResourceUtils.getDrawable(R.drawable.dev_toast_icon_success_white);
        return sSuccessDrawable;
    }

    // ====================
    // = 显示 normal Toast =
    // ====================

    /**
     * normal 样式 Toast
     * @param text Toast 提示文本
     */
    public static void normal(final String text) {
        custom(true, null, sNormalStyle, text, Toast.LENGTH_SHORT, null);
    }

    /**
     * normal 样式 Toast
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void normal(
            final String text,
            final int duration
    ) {
        custom(true, null, sNormalStyle, text, duration, null);
    }

    /**
     * normal 样式 Toast
     * @param text Toast 提示文本
     * @param icon Toast icon Drawable
     */
    public static void normal(
            final String text,
            final Drawable icon
    ) {
        custom(true, null, sNormalStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * normal 样式 Toast
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param icon     Toast icon Drawable
     */
    public static void normal(
            final String text,
            final int duration,
            final Drawable icon
    ) {
        custom(true, null, sNormalStyle, text, duration, icon);
    }

    // =

    /**
     * normal 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     */
    public static void normal(
            final boolean isSingle,
            final String text
    ) {
        custom(isSingle, null, sNormalStyle, text, Toast.LENGTH_SHORT, null);
    }

    /**
     * normal 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void normal(
            final boolean isSingle,
            final String text,
            final int duration
    ) {
        custom(isSingle, null, sNormalStyle, text, duration, null);
    }

    /**
     * normal 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     * @param icon     Toast icon Drawable
     */
    public static void normal(
            final boolean isSingle,
            final String text,
            final Drawable icon
    ) {
        custom(isSingle, null, sNormalStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * normal 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param icon     Toast icon Drawable
     */
    public static void normal(
            final boolean isSingle,
            final String text,
            final int duration,
            final Drawable icon
    ) {
        custom(isSingle, null, sNormalStyle, text, duration, icon);
    }

    // ==================
    // = 显示 info Toast =
    // ==================

    /**
     * info 样式 Toast
     * @param text Toast 提示文本
     */
    public static void info(final String text) {
        custom(true, null, sInfoStyle, text, Toast.LENGTH_SHORT, getInfoDrawable());
    }

    /**
     * info 样式 Toast
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void info(
            final String text,
            final int duration
    ) {
        custom(true, null, sInfoStyle, text, duration, getInfoDrawable());
    }

    /**
     * info 样式 Toast
     * @param text Toast 提示文本
     * @param icon Toast icon Drawable
     */
    public static void info(
            final String text,
            final Drawable icon
    ) {
        custom(true, null, sInfoStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * info 样式 Toast
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param icon     Toast icon Drawable
     */
    public static void info(
            final String text,
            final int duration,
            final Drawable icon
    ) {
        custom(true, null, sInfoStyle, text, duration, icon);
    }

    // =

    /**
     * info 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     */
    public static void info(
            final boolean isSingle,
            final String text
    ) {
        custom(isSingle, null, sInfoStyle, text, Toast.LENGTH_SHORT, getInfoDrawable());
    }

    /**
     * info 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void info(
            final boolean isSingle,
            final String text,
            final int duration
    ) {
        custom(isSingle, null, sInfoStyle, text, duration, getInfoDrawable());
    }

    /**
     * info 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     * @param icon     Toast icon Drawable
     */
    public static void info(
            final boolean isSingle,
            final String text,
            final Drawable icon
    ) {
        custom(isSingle, null, sInfoStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * info 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param icon     Toast icon Drawable
     */
    public static void info(
            final boolean isSingle,
            final String text,
            final int duration,
            final Drawable icon
    ) {
        custom(isSingle, null, sInfoStyle, text, duration, icon);
    }

    // =====================
    // = 显示 warning Toast =
    // =====================

    /**
     * warning 样式 Toast
     * @param text Toast 提示文本
     */
    public static void warning(final String text) {
        custom(true, null, sWarningStyle, text, Toast.LENGTH_SHORT, getWarningDrawable());
    }

    /**
     * warning 样式 Toast
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void warning(
            final String text,
            final int duration
    ) {
        custom(true, null, sWarningStyle, text, duration, getWarningDrawable());
    }

    /**
     * warning 样式 Toast
     * @param text Toast 提示文本
     * @param icon Toast icon Drawable
     */
    public static void warning(
            final String text,
            final Drawable icon
    ) {
        custom(true, null, sWarningStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * warning 样式 Toast
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param icon     Toast icon Drawable
     */
    public static void warning(
            final String text,
            final int duration,
            final Drawable icon
    ) {
        custom(true, null, sWarningStyle, text, duration, icon);
    }

    // =

    /**
     * warning 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     */
    public static void warning(
            final boolean isSingle,
            final String text
    ) {
        custom(isSingle, null, sWarningStyle, text, Toast.LENGTH_SHORT, getWarningDrawable());
    }

    /**
     * warning 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void warning(
            final boolean isSingle,
            final String text,
            final int duration
    ) {
        custom(isSingle, null, sWarningStyle, text, duration, getWarningDrawable());
    }

    /**
     * warning 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     * @param icon     Toast icon Drawable
     */
    public static void warning(
            final boolean isSingle,
            final String text,
            final Drawable icon
    ) {
        custom(isSingle, null, sWarningStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * warning 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param icon     Toast icon Drawable
     */
    public static void warning(
            final boolean isSingle,
            final String text,
            final int duration,
            final Drawable icon
    ) {
        custom(isSingle, null, sWarningStyle, text, duration, icon);
    }

    // ===================
    // = 显示 error Toast =
    // ===================

    /**
     * error 样式 Toast
     * @param text Toast 提示文本
     */
    public static void error(final String text) {
        custom(true, null, sErrorStyle, text, Toast.LENGTH_SHORT, getErrorDrawable());
    }

    /**
     * error 样式 Toast
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void error(
            final String text,
            final int duration
    ) {
        custom(true, null, sErrorStyle, text, duration, getErrorDrawable());
    }

    /**
     * error 样式 Toast
     * @param text Toast 提示文本
     * @param icon Toast icon Drawable
     */
    public static void error(
            final String text,
            final Drawable icon
    ) {
        custom(true, null, sErrorStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * error 样式 Toast
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param icon     Toast icon Drawable
     */
    public static void error(
            final String text,
            final int duration,
            final Drawable icon
    ) {
        custom(true, null, sErrorStyle, text, duration, icon);
    }

    // =

    /**
     * error 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     */
    public static void error(
            final boolean isSingle,
            final String text
    ) {
        custom(isSingle, null, sErrorStyle, text, Toast.LENGTH_SHORT, getErrorDrawable());
    }

    /**
     * error 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void error(
            final boolean isSingle,
            final String text,
            final int duration
    ) {
        custom(isSingle, null, sErrorStyle, text, duration, getErrorDrawable());
    }

    /**
     * error 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     * @param icon     Toast icon Drawable
     */
    public static void error(
            final boolean isSingle,
            final String text,
            final Drawable icon
    ) {
        custom(isSingle, null, sErrorStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * error 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param icon     Toast icon Drawable
     */
    public static void error(
            final boolean isSingle,
            final String text,
            final int duration,
            final Drawable icon
    ) {
        custom(isSingle, null, sErrorStyle, text, duration, icon);
    }

    // =====================
    // = 显示 success Toast =
    // =====================

    /**
     * success 样式 Toast
     * @param text Toast 提示文本
     */
    public static void success(final String text) {
        custom(true, null, sSuccessStyle, text, Toast.LENGTH_SHORT, getSuccessDrawable());
    }

    /**
     * success 样式 Toast
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void success(
            final String text,
            final int duration
    ) {
        custom(true, null, sSuccessStyle, text, duration, getSuccessDrawable());
    }

    /**
     * success 样式 Toast
     * @param text Toast 提示文本
     * @param icon Toast icon Drawable
     */
    public static void success(
            final String text,
            final Drawable icon
    ) {
        custom(true, null, sSuccessStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * success 样式 Toast
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param icon     Toast icon Drawable
     */
    public static void success(
            final String text,
            final int duration,
            final Drawable icon
    ) {
        custom(true, null, sSuccessStyle, text, duration, icon);
    }

    // =

    /**
     * success 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     */
    public static void success(
            final boolean isSingle,
            final String text
    ) {
        custom(isSingle, null, sSuccessStyle, text, Toast.LENGTH_SHORT, getSuccessDrawable());
    }

    /**
     * success 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void success(
            final boolean isSingle,
            final String text,
            final int duration
    ) {
        custom(isSingle, null, sSuccessStyle, text, duration, getSuccessDrawable());
    }

    /**
     * success 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     * @param icon     Toast icon Drawable
     */
    public static void success(
            final boolean isSingle,
            final String text,
            final Drawable icon
    ) {
        custom(isSingle, null, sSuccessStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * success 样式 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param icon     Toast icon Drawable
     */
    public static void success(
            final boolean isSingle,
            final String text,
            final int duration,
            final Drawable icon
    ) {
        custom(isSingle, null, sSuccessStyle, text, duration, icon);
    }

    // ====================
    // = 显示 custom Toast =
    // ====================

    /**
     * custom Toast
     * @param style Toast 样式 {@link ToastTintUtils.Style}
     * @param text  Toast 提示文本
     */
    public static void custom(
            final ToastTintUtils.Style style,
            final String text
    ) {
        custom(true, null, style, text, Toast.LENGTH_SHORT, null);
    }

    /**
     * custom Toast
     * @param style    Toast 样式 {@link ToastTintUtils.Style}
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void custom(
            final ToastTintUtils.Style style,
            final String text,
            final int duration
    ) {
        custom(true, null, style, text, duration, null);
    }

    /**
     * custom Toast
     * @param style Toast 样式 {@link ToastTintUtils.Style}
     * @param text  Toast 提示文本
     * @param icon  Toast icon Drawable
     */
    public static void custom(
            final ToastTintUtils.Style style,
            final String text,
            final Drawable icon
    ) {
        custom(true, null, style, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * custom Toast
     * @param style    Toast 样式 {@link ToastTintUtils.Style}
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param icon     Toast icon Drawable
     */
    public static void custom(
            final ToastTintUtils.Style style,
            final String text,
            final int duration,
            final Drawable icon
    ) {
        custom(true, null, style, text, duration, icon);
    }

    // =

    /**
     * custom Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param style    Toast 样式 {@link ToastTintUtils.Style}
     * @param text     Toast 提示文本
     */
    public static void custom(
            final boolean isSingle,
            final ToastTintUtils.Style style,
            final String text
    ) {
        custom(isSingle, null, style, text, Toast.LENGTH_SHORT, null);
    }

    /**
     * custom Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param style    Toast 样式 {@link ToastTintUtils.Style}
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void custom(
            final boolean isSingle,
            final ToastTintUtils.Style style,
            final String text,
            final int duration
    ) {
        custom(isSingle, null, style, text, duration, null);
    }

    /**
     * custom Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param style    Toast 样式 {@link ToastTintUtils.Style}
     * @param text     Toast 提示文本
     * @param icon     Toast icon Drawable
     */
    public static void custom(
            final boolean isSingle,
            final ToastTintUtils.Style style,
            final String text,
            final Drawable icon
    ) {
        custom(isSingle, null, style, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * custom Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param style    Toast 样式 {@link ToastTintUtils.Style}
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param icon     Toast icon Drawable
     */
    public static void custom(
            final boolean isSingle,
            final ToastTintUtils.Style style,
            final String text,
            final int duration,
            final Drawable icon
    ) {
        custom(isSingle, null, style, text, duration, icon);
    }

    // =

    /**
     * custom Toast
     * @param context {@link Context}
     * @param style   Toast 样式 {@link ToastTintUtils.Style}
     * @param text    Toast 提示文本
     */
    public static void custom(
            final Context context,
            final ToastTintUtils.Style style,
            final String text
    ) {
        custom(true, context, style, text, Toast.LENGTH_SHORT, null);
    }

    /**
     * custom Toast
     * @param context  {@link Context}
     * @param style    Toast 样式 {@link ToastTintUtils.Style}
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void custom(
            final Context context,
            final ToastTintUtils.Style style,
            final String text,
            final int duration
    ) {
        custom(true, context, style, text, duration, null);
    }

    /**
     * custom Toast
     * @param context {@link Context}
     * @param style   Toast 样式 {@link ToastTintUtils.Style}
     * @param text    Toast 提示文本
     * @param icon    Toast icon Drawable
     */
    public static void custom(
            final Context context,
            final ToastTintUtils.Style style,
            final String text,
            final Drawable icon
    ) {
        custom(true, context, style, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * custom Toast
     * @param context  {@link Context}
     * @param style    Toast 样式 {@link ToastTintUtils.Style}
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param icon     Toast icon Drawable
     */
    public static void custom(
            final Context context,
            final ToastTintUtils.Style style,
            final String text,
            final int duration,
            final Drawable icon
    ) {
        custom(true, context, style, text, duration, icon);
    }

    // =

    /**
     * custom Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param context  {@link Context}
     * @param style    Toast 样式 {@link ToastTintUtils.Style}
     * @param text     Toast 提示文本
     */
    public static void custom(
            final boolean isSingle,
            final Context context,
            final ToastTintUtils.Style style,
            final String text
    ) {
        custom(isSingle, context, style, text, Toast.LENGTH_SHORT, null);
    }

    /**
     * custom Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param context  {@link Context}
     * @param style    Toast 样式 {@link ToastTintUtils.Style}
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void custom(
            final boolean isSingle,
            final Context context,
            final ToastTintUtils.Style style,
            final String text,
            final int duration
    ) {
        custom(isSingle, context, style, text, duration, null);
    }

    /**
     * custom Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param context  {@link Context}
     * @param style    Toast 样式 {@link ToastTintUtils.Style}
     * @param text     Toast 提示文本
     * @param icon     Toast icon Drawable
     */
    public static void custom(
            final boolean isSingle,
            final Context context,
            final ToastTintUtils.Style style,
            final String text,
            final Drawable icon
    ) {
        custom(isSingle, context, style, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * custom Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param context  {@link Context}
     * @param style    Toast 样式 {@link ToastTintUtils.Style}
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param icon     Toast icon Drawable
     */
    public static void custom(
            final boolean isSingle,
            final Context context,
            final ToastTintUtils.Style style,
            final String text,
            final int duration,
            final Drawable icon
    ) {
        // 获取 View
        View view = inflaterView(context, style, text, icon);
        // 显示 Toast
        showToastView(isSingle, context, view, duration);
    }

    // =============
    // = 内部 Toast =
    // =============

    /**
     * 显示 View Toast 方法
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param context  {@link Context}
     * @param view     Toast 显示的 View
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    private static void showToastView(
            final boolean isSingle,
            final Context context,
            final View view,
            final int duration
    ) {
        if (view == null) return;
        if (sIsHandler) {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Toast toast = newToastView(isSingle, context, view, duration);
                        if (toast != null) {
                            toast.show();
                        }
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "showToastView");
                    }
                }
            });
        } else {
            try {
                Toast toast = newToastView(isSingle, context, view, duration);
                if (toast != null) {
                    toast.show();
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "showToastView");
            }
        }
    }

    /**
     * 获取一个新的 View Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param context  {@link Context}
     * @param view     Toast 显示的 View
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @return {@link Toast}
     */
    private static Toast newToastView(
            final boolean isSingle,
            Context context,
            final View view,
            final int duration
    ) {
        if (context == null) {
            context = DevUtils.getContext();
        }
        // 防止 Context 为 null
        if (context == null) {
            return null;
        } else if (view == null) { // 防止显示的 View 为 null
            return null;
        }
        // 判断是否显示唯一, 单独共用一个
        if (isSingle) {
            try {
                // 关闭旧的 Toast
                if (sToast != null) {
                    sToast.cancel();
                    sToast = null;
                }
                // 解决 MIUI 会显示应用名称问题
                sToast = new Toast(context);
                sToast.setView(view);
                sToast.setDuration(duration);
                // 判断是否使用配置
                if (sUseConfig) {
                    // 设置属性配置
                    if (sGravity != 0) {
                        sToast.setGravity(sGravity, sX, sY);
                    }
                    sToast.setMargin(sHorizontalMargin, sVerticalMargin);
                }
                // 反射 Hook Toast 解决 Android 7.1.1 崩溃问题
                reflectToastHandler(sToast);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "newToastView");
            }
            return sToast;
        } else {
            Toast toast = null;
            try {
                // 解决 MIUI 会显示应用名称问题
                toast = new Toast(context);
                toast.setView(view);
                toast.setDuration(duration);
                // 判断是否使用配置
                if (sUseConfig) {
                    // 设置属性配置
                    if (sGravity != 0) {
                        toast.setGravity(sGravity, sX, sY);
                    }
                    toast.setMargin(sHorizontalMargin, sVerticalMargin);
                }
                // 反射 Hook Toast 解决 Android 7.1.1 崩溃问题
                reflectToastHandler(toast);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "newToastView");
            }
            return toast;
        }
    }

    /**
     * 实例化 Layout View
     * @param context {@link Context}
     * @param style   Toast 样式 {@link ToastTintUtils.Style}
     * @param text    Toast 提示文本
     * @param icon    Toast icon Drawable
     * @return {@link View} inflate dev_toast_layout
     */
    private static View inflaterView(
            Context context,
            final ToastTintUtils.Style style,
            final String text,
            Drawable icon
    ) {
        if (context == null) {
            context = DevUtils.getContext();
        }
        // 如果样式为 null, 则不处理
        if (style == null) {
            return null;
        }
        // 提示文本
        String toastText = text;
        // 判断是否过滤
        if (!sPriToastFilter.filter(toastText)) {
            return null;
        }
        // 处理内容
        toastText = sPriToastFilter.handlerContent(toastText);
        // 设置为 null, 便于提示排查
        if (TextUtils.isEmpty(toastText)) {
            toastText = sNullText;
            // 如果还是为 null, 则不处理
            if (TextUtils.isEmpty(toastText)) {
                return null;
            }
        }
        if (context != null) {
            try {
                // 引入 View
                final View toastLayout = LayoutInflater.from(context).inflate(dev.utils.R.layout.dev_toast_layout, null);
                // 初始化 View
                final ImageView toastIcon     = toastLayout.findViewById(dev.utils.R.id.vid_dtl_toast_igview);
                final TextView  toastTextView = toastLayout.findViewById(dev.utils.R.id.vid_dtl_toast_tv);

                // ================
                // = TextView 相关 =
                // ================

                // 设置文案
                toastTextView.setText(toastText);
                // 设置字体颜色
                if (style.getTextColor() != 0) {
                    toastTextView.setTextColor(style.getTextColor());
                }
                // 设置字体大小
                if (style.getTextSize() != 0f) {
                    toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, style.getTextSize());
                }
                // 设置最大行数
                if (style.getMaxLines() >= 1) {
                    toastTextView.setMaxLines(style.getMaxLines());
                }
                // 设置 Ellipsize 效果
                if (style.getEllipsize() != null) {
                    toastTextView.setEllipsize(style.getEllipsize());
                }
                // 设置字体样式
                if (style.getTypeface() != null) {
                    toastTextView.setTypeface(style.getTypeface());
                }

                // =================
                // = ImageView 相关 =
                // =================

                // 判断是否使用图标
                if (icon != null) {
                    // 判断是否渲染图标
                    if (style.isTintIcon() && style.getTintIconColor() != 0) {
                        icon = ImageUtils.setColorFilter(icon, style.getTintIconColor());
                    }
                    // 设置 ImageView 图片
                    ViewUtils.setBackground(toastIcon, icon);
                } else {
                    // 隐藏图标
                    toastIcon.setVisibility(View.GONE);
                }

                // =================
                // = 背景 View 相关 =
                // =================

                // 背景图片
                Drawable drawableFrame = style.getBackground();
                // 判断是否为 null
                if (drawableFrame == null) {
                    drawableFrame = ResourceUtils.getNinePatchDrawable(dev.utils.R.drawable.dev_toast_frame);
                    // 判断是否需要着色
                    if (style.getBackgroundTintColor() != 0) { // 根据背景色进行渲染透明图片
                        drawableFrame = ImageUtils.setColorFilter(drawableFrame, style.getBackgroundTintColor());
                    }
                }
                // 设置 View 背景
                ViewUtils.setBackground(toastLayout, drawableFrame);
                // 返回 View
                return toastLayout;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "inflaterView");
            }
        }
        return null;
    }

    // ==============================
    // = 解决 Android 7.1.1 崩溃问题 =
    // ==============================

    /**
     * 反射 Hook Toast 设置 Handler
     * @param toast {@link Toast}
     */
    private static void reflectToastHandler(final Toast toast) {
        if (toast == null) return;
        // 反射设置 Toast Handler 解决 Android 7.1.1 Toast 崩溃问题
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            try {
                Field field_tn = Toast.class.getDeclaredField("mTN");
                field_tn.setAccessible(true);

                Object mTN           = field_tn.get(toast);
                Field  field_handler = field_tn.getType().getDeclaredField("mHandler");
                field_handler.setAccessible(true);

                Handler handler = (Handler) field_handler.get(mTN);
                field_handler.set(mTN, new SafeHandler(handler));
            } catch (Exception ignore) {
            }
        }
    }

    /**
     * detail: Toast 安全显示 Handler
     * @author Ttt
     */
    private static final class SafeHandler
            extends Handler {

        private final Handler mHandler;

        SafeHandler(Handler handler) {
            mHandler = handler;
        }

        @Override
        public void handleMessage(Message msg) {
            mHandler.handleMessage(msg);
        }

        @Override
        public void dispatchMessage(Message msg) {
            try {
                mHandler.dispatchMessage(msg);
            } catch (Exception ignore) {
            }
        }
    }

    // ===========
    // = 样式相关 =
    // ===========

    /**
     * detail: Toast 自定义 View 着色等相关 样式配置
     * @author Ttt
     */
    public interface Style {

        /**
         * 获取文本颜色
         * @return 文本颜色
         */
        int getTextColor();

        /**
         * 获取字体大小
         * @return 字体大小
         */
        float getTextSize();

        /**
         * 获取背景着色颜色
         * @return 背景着色颜色
         */
        int getBackgroundTintColor();

        /**
         * 获取背景图片
         * @return 背景图片
         */
        Drawable getBackground();

        /**
         * 获取最大行数
         * @return 最大行数
         */
        int getMaxLines();

        /**
         * 获取 Ellipsize 效果
         * @return Ellipsize 效果
         */
        TextUtils.TruncateAt getEllipsize();

        /**
         * 获取字体样式
         * @return 字体样式
         */
        Typeface getTypeface();

        /**
         * 获取图标着色颜色
         * @return 图标着色颜色
         */
        int getTintIconColor();

        /**
         * 是否渲染图标 ( getTintIconColor() 着色渲染 )
         * @return {@code true} yes, {@code false} no
         */
        boolean isTintIcon();
    }

    // =

    /**
     * detail: 默认样式
     * @author Ttt
     */
    public static class DefaultStyle
            implements Style {

        /**
         * 获取获取文本颜色
         * @return 文本颜色
         */
        @Override
        public int getTextColor() {
            return Color.WHITE;
        }

        /**
         * 获取字体大小
         * @return 字体大小
         */
        @Override
        public float getTextSize() {
            return 16f;
        }

        /**
         * 获取背景着色颜色
         * @return 背景着色颜色
         */
        @Override
        public int getBackgroundTintColor() {
            return 0;
        }

        /**
         * 获取背景图片
         * @return 背景图片
         */
        @Override
        public Drawable getBackground() {
            return null;
        }

        /**
         * 获取最大行数
         * @return 最大行数
         */
        @Override
        public int getMaxLines() {
            return 0;
        }

        /**
         * 获取 Ellipsize 效果
         * @return Ellipsize 效果
         */
        @Override
        public TextUtils.TruncateAt getEllipsize() {
            return null;
        }

        /**
         * 获取字体样式
         * @return 字体样式
         */
        @Override
        public Typeface getTypeface() {
            // return Typeface.create("sans-serif-condensed", Typeface.NORMAL);
            return null;
        }

        /**
         * 获取图标着色颜色
         * @return 图标着色颜色
         */
        @Override
        public int getTintIconColor() {
            return Color.WHITE;
        }

        /**
         * 是否渲染图标 ( getTintIconColor() 着色渲染 )
         * @return {@code true} yes, {@code false} no
         */
        @Override
        public boolean isTintIcon() {
            return false;
        }
    }

    /**
     * detail: Normal 样式 ( 灰色 )
     * @author Ttt
     */
    public static class NormalStyle
            extends DefaultStyle {

        /**
         * 获取背景着色颜色
         * @return 背景着色颜色
         */
        @Override
        public int getBackgroundTintColor() {
            return Color.parseColor("#353A3E");
        }

        /**
         * 是否渲染图标 ( getTintIconColor() 着色渲染 )
         * @return {@code true} yes, {@code false} no
         */
        @Override
        public boolean isTintIcon() {
            return true;
        }
    }

    /**
     * detail: Info 样式 ( 海洋蓝 )
     * @author Ttt
     */
    public static class InfoStyle
            extends DefaultStyle {

        /**
         * 获取背景着色颜色
         * @return 背景着色颜色
         */
        @Override
        public int getBackgroundTintColor() {
            return Color.parseColor("#3F51B5");
        }

        /**
         * 是否渲染图标 ( getTintIconColor() 着色渲染 )
         * @return {@code true} yes, {@code false} no
         */
        @Override
        public boolean isTintIcon() {
            return true;
        }
    }

    /**
     * detail: Warning 样式 ( 橙色 )
     * @author Ttt
     */
    public static class WarningStyle
            extends DefaultStyle {

        /**
         * 获取背景着色颜色
         * @return 背景着色颜色
         */
        @Override
        public int getBackgroundTintColor() {
            return Color.parseColor("#FFA900");
        }

        /**
         * 是否渲染图标 ( getTintIconColor() 着色渲染 )
         * @return {@code true} yes, {@code false} no
         */
        @Override
        public boolean isTintIcon() {
            return true;
        }
    }

    /**
     * detail: Error 样式 ( 红色 )
     * @author Ttt
     */
    public static class ErrorStyle
            extends DefaultStyle {

        /**
         * 获取背景着色颜色
         * @return 背景着色颜色
         */
        @Override
        public int getBackgroundTintColor() {
            return Color.parseColor("#D50000");
        }

        /**
         * 是否渲染图标 ( getTintIconColor() 着色渲染 )
         * @return {@code true} yes, {@code false} no
         */
        @Override
        public boolean isTintIcon() {
            return true;
        }
    }

    /**
     * detail: Success 样式 ( 绿色 )
     * @author Ttt
     */
    public static class SuccessStyle
            extends DefaultStyle {

        /**
         * 获取背景着色颜色
         * @return 背景着色颜色
         */
        @Override
        public int getBackgroundTintColor() {
            return Color.parseColor("#388E3C");
        }

        /**
         * 是否渲染图标 ( getTintIconColor() 着色渲染 )
         * @return {@code true} yes, {@code false} no
         */
        @Override
        public boolean isTintIcon() {
            return true;
        }
    }

    // ===========
    // = 其他接口 =
    // ===========

    /**
     * detail: Toast 过滤器
     * @author Ttt
     */
    public interface Filter {

        /**
         * 判断是否显示
         * @param content Toast 显示文案
         * @return {@code true} 接着执行, {@code false} 过滤不处理
         */
        boolean filter(String content);

        /**
         * 获取 Toast 显示的文案
         * @param content Toast 显示文案
         * @return 处理后的内容
         */
        String handlerContent(String content);
    }

    // =================================
    // = ToastTintUtils.Filter 实现方法 =
    // =================================

    /**
     * 内部 Toast Filter 实现对象
     */
    private static final ToastTintUtils.Filter sPriToastFilter = new ToastTintUtils.Filter() {

        /**
         * 判断是否显示
         * @param content Toast 显示文案
         * @return {@code true} 接着执行, {@code false} 过滤不处理
         */
        @Override
        public boolean filter(String content) {
            if (sToastFilter != null) {
                return sToastFilter.filter(content);
            }
            return true;
        }

        /**
         * 获取 Toast 显示的文案
         * @param content Toast 显示文案
         * @return 处理后的内容
         */
        @Override
        public String handlerContent(String content) {
            if (sToastFilter != null) {
                return sToastFilter.handlerContent(content);
            }
            return content;
        }
    };
}