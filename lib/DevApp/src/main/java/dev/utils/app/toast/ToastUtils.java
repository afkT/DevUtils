package dev.utils.app.toast;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.StringRes;

import java.lang.reflect.Field;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Simple Toast 工具类 ( 简单的 Toast 工具类, 支持子线程弹出 Toast )
 * @author Ttt
 * <pre>
 *     支持子线程弹出 Toast, 可通过开关配置
 *     内部解决 Android 7.1.1 崩溃问题
 *     但无处理 部分 ROM 如魅族、小米、三星等关闭应用通知, 无法显示 Toast 问题
 * </pre>
 */
public final class ToastUtils {

    private ToastUtils() {
    }

    // 日志 TAG
    private static final String TAG = ToastUtils.class.getSimpleName();

    // Toast 判断过滤
    private static       ToastUtils.Filter sToastFilter = null;
    // 内部持有单个 Toast
    private static       Toast             sToast       = null;
    // 判断是否使用 Handler
    private static       boolean           sIsHandler   = true;
    // 内部 Handler
    private static final Handler           sHandler     = new Handler(Looper.getMainLooper());
    // Null 值 ( null 提示值 )
    private static       String            sNullText    = null;

    // ===========
    // = 部分配置 =
    // ===========

    // 判断是否使用配置
    private static boolean sUseConfig = true;
    // Toast 的重心、X、Y 轴偏移
    private static int     sGravity, sX, sY;
    // 水平边距、垂直边距
    private static float sHorizontalMargin, sVerticalMargin;

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
    public static void setToastFilter(final ToastUtils.Filter toastFilter) {
        ToastUtils.sToastFilter = toastFilter;
    }

    /**
     * 设置是否使用 Handler 显示 Toast
     * @param isHandler {@code true} 使用, {@code false} 不使用
     */
    public static void setIsHandler(final boolean isHandler) {
        ToastUtils.sIsHandler = isHandler;
    }

    /**
     * 设置 Text 为 null 的文本
     * @param nullText 显示内容为 null 时, 使用的提示值
     */
    public static void setNullText(final String nullText) {
        ToastUtils.sNullText = nullText;
    }

    /**
     * 设置是否使用配置
     * @param useConfig {@code true} 使用, {@code false} 不使用
     */
    public static void setUseConfig(final boolean useConfig) {
        ToastUtils.sUseConfig = useConfig;
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
        ToastUtils.sGravity = gravity;
        ToastUtils.sX = xOffset;
        ToastUtils.sY = yOffset;
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
        ToastUtils.sHorizontalMargin = horizontalMargin;
        ToastUtils.sVerticalMargin = verticalMargin;
    }

    // =================
    // = 统一显示 Toast =
    // =================

    // ======================
    // = Toast.LENGTH_SHORT =
    // ======================

    /**
     * 显示 LENGTH_SHORT Toast
     * @param text       Toast 提示文本
     * @param formatArgs 格式化参数
     */
    public static void showShort(
            final String text,
            final Object... formatArgs
    ) {
        showShort(null, text, formatArgs);
    }

    /**
     * 显示 LENGTH_SHORT Toast
     * @param context    {@link Context}
     * @param text       Toast 提示文本
     * @param formatArgs 格式化参数
     */
    public static void showShort(
            final Context context,
            final String text,
            final Object... formatArgs
    ) {
        handlerToastStr(true, context, text, Toast.LENGTH_SHORT, formatArgs);
    }

    // =

    /**
     * 显示 LENGTH_SHORT Toast
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     */
    public static void showShort(
            @StringRes final int resId,
            final Object... formatArgs
    ) {
        showShort(null, resId, formatArgs);
    }

    /**
     * 显示 LENGTH_SHORT Toast
     * @param context    {@link Context}
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     */
    public static void showShort(
            final Context context,
            @StringRes final int resId,
            final Object... formatArgs
    ) {
        handlerToastRes(true, context, resId, Toast.LENGTH_SHORT, formatArgs);
    }

    // =====================
    // = Toast.LENGTH_LONG =
    // =====================

    /**
     * 显示 LENGTH_LONG Toast
     * @param text       Toast 提示文本
     * @param formatArgs 格式化参数
     */
    public static void showLong(
            final String text,
            final Object... formatArgs
    ) {
        showLong(null, text, formatArgs);
    }

    /**
     * 显示 LENGTH_LONG Toast
     * @param context    {@link Context}
     * @param text       Toast 提示文本
     * @param formatArgs 格式化参数
     */
    public static void showLong(
            final Context context,
            final String text,
            final Object... formatArgs
    ) {
        handlerToastStr(true, context, text, Toast.LENGTH_LONG, formatArgs);
    }

    // =

    /**
     * 显示 LENGTH_LONG Toast
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     */
    public static void showLong(
            @StringRes final int resId,
            final Object... formatArgs
    ) {
        showLong(null, resId, formatArgs);
    }

    /**
     * 显示 LENGTH_LONG Toast
     * @param context    {@link Context}
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     */
    public static void showLong(
            final Context context,
            @StringRes final int resId,
            final Object... formatArgs
    ) {
        handlerToastRes(true, context, resId, Toast.LENGTH_LONG, formatArgs);
    }

    // =============
    // = Toast 方法 =
    // =============

    /**
     * 显示 Toast
     * @param resId    R.string.id
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void showToast(
            @StringRes final int resId,
            final int duration
    ) {
        showToast(null, resId, duration);
    }

    /**
     * 显示 Toast
     * @param context  {@link Context}
     * @param resId    R.string.id
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void showToast(
            final Context context,
            @StringRes final int resId,
            final int duration
    ) {
        handlerToastRes(true, context, resId, duration);
    }

    /**
     * 显示 Toast
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void showToast(
            final String text,
            final int duration
    ) {
        priShowToastText(true, null, text, duration);
    }

    /**
     * 显示 Toast
     * @param context  {@link Context}
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void showToast(
            final Context context,
            final String text,
            final int duration
    ) {
        priShowToastText(true, context, text, duration);
    }

    // ===================
    // = 非统一显示 Toast =
    // ===================

    // ======================
    // = Toast.LENGTH_SHORT =
    // ======================

    /**
     * 显示 new LENGTH_SHORT Toast
     * @param text       Toast 提示文本
     * @param formatArgs 格式化参数
     */
    public static void showShortNew(
            final String text,
            final Object... formatArgs
    ) {
        showShortNew(null, text, formatArgs);
    }

    /**
     * 显示 new LENGTH_SHORT Toast
     * @param context    {@link Context}
     * @param text       Toast 提示文本
     * @param formatArgs 格式化参数
     */
    public static void showShortNew(
            final Context context,
            final String text,
            final Object... formatArgs
    ) {
        handlerToastStr(false, context, text, Toast.LENGTH_SHORT, formatArgs);
    }

    // =

    /**
     * 显示 new LENGTH_SHORT Toast
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     */
    public static void showShortNew(
            @StringRes final int resId,
            final Object... formatArgs
    ) {
        showShortNew(null, resId, formatArgs);
    }

    /**
     * 显示 new LENGTH_SHORT Toast
     * @param context    {@link Context}
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     */
    public static void showShortNew(
            final Context context,
            @StringRes final int resId,
            final Object... formatArgs
    ) {
        handlerToastRes(false, context, resId, Toast.LENGTH_SHORT, formatArgs);
    }

    // =====================
    // = Toast.LENGTH_LONG =
    // =====================

    /**
     * 显示 new LENGTH_LONG Toast
     * @param text       Toast 提示文本
     * @param formatArgs 格式化参数
     */
    public static void showLongNew(
            final String text,
            final Object... formatArgs
    ) {
        showLongNew(null, text, formatArgs);
    }

    /**
     * 显示 new LENGTH_LONG Toast
     * @param context    {@link Context}
     * @param text       Toast 提示文本
     * @param formatArgs 格式化参数
     */
    public static void showLongNew(
            final Context context,
            final String text,
            final Object... formatArgs
    ) {
        handlerToastStr(false, context, text, Toast.LENGTH_LONG, formatArgs);
    }

    // =

    /**
     * 显示 new LENGTH_LONG Toast
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     */
    public static void showLongNew(
            @StringRes final int resId,
            final Object... formatArgs
    ) {
        showLongNew(null, resId, formatArgs);
    }

    /**
     * 显示 new LENGTH_LONG Toast
     * @param context    {@link Context}
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     */
    public static void showLongNew(
            final Context context,
            @StringRes final int resId,
            final Object... formatArgs
    ) {
        handlerToastRes(false, context, resId, Toast.LENGTH_LONG, formatArgs);
    }

    // =============
    // = Toast 方法 =
    // =============

    /**
     * 显示新的 Toast
     * @param resId    R.string.id
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void showToastNew(
            @StringRes final int resId,
            final int duration
    ) {
        showToastNew(null, resId, duration);
    }

    /**
     * 显示新的 Toast
     * @param context  {@link Context}
     * @param resId    R.string.id
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void showToastNew(
            final Context context,
            @StringRes final int resId,
            final int duration
    ) {
        handlerToastRes(false, context, resId, duration);
    }

    /**
     * 显示新的 Toast
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void showToastNew(
            final String text,
            final int duration
    ) {
        priShowToastText(false, null, text, duration);
    }

    /**
     * 显示新的 Toast
     * @param context  {@link Context}
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void showToastNew(
            final Context context,
            final String text,
            final int duration
    ) {
        priShowToastText(false, context, text, duration);
    }

    // =============
    // = 显示 Toast =
    // =============

    /**
     * 内部私有方法, 最终显示 Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param context  {@link Context}
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    private static void priShowToastText(
            final boolean isSingle,
            final Context context,
            final String text,
            final int duration
    ) {
        if (sIsHandler) {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Toast toast = newToastText(isSingle, context, text, duration);
                        if (toast != null) {
                            toast.show();
                        }
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "priShowToastText");
                    }
                }
            });
        } else {
            try {
                Toast toast = newToastText(isSingle, context, text, duration);
                if (toast != null) {
                    toast.show();
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "priShowToastText");
            }
        }
    }

    /**
     * 获取一个新的 Text Toast
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param context  {@link Context}
     * @param text     Toast 提示文本
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @return {@link Toast}
     */
    public static Toast newToastText(
            final boolean isSingle,
            Context context,
            final String text,
            final int duration
    ) {
        if (context == null) {
            context = DevUtils.getContext();
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
        // 判断是否显示唯一, 单独共用一个
        if (isSingle) {
            try {
                // 关闭旧的 Toast
                if (sToast != null) {
                    sToast.cancel();
                    sToast = null;
                }
                // 解决 MIUI 会显示应用名称问题
                sToast = Toast.makeText(context, "", duration);
                sToast.setText(toastText);
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
                LogPrintUtils.eTag(TAG, e, "newToastText");
            }
            return sToast;
        } else {
            Toast toast = null;
            try {
                // 解决 MIUI 会显示应用名称问题
                toast = Toast.makeText(context, "", duration);
                toast.setText(toastText);
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
                LogPrintUtils.eTag(TAG, e, "newToastText");
            }
            return toast;
        }
    }

    // ==================
    // = 显示 View Toast =
    // ==================

    /**
     * 显示 View Toast 方法
     * @param view Toast 显示的 View
     */
    public static void showToastView(final View view) {
        showToastView(true, null, view, Toast.LENGTH_SHORT);
    }

    /**
     * 显示 View Toast 方法
     * @param view     Toast 显示的 View
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void showToastView(
            final View view,
            final int duration
    ) {
        showToastView(true, null, view, duration);
    }

    /**
     * 显示 View Toast 方法
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param view     Toast 显示的 View
     */
    public static void showToastView(
            final boolean isSingle,
            final View view
    ) {
        showToastView(isSingle, null, view, Toast.LENGTH_SHORT);
    }

    /**
     * 显示 View Toast 方法
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param view     Toast 显示的 View
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void showToastView(
            final boolean isSingle,
            final View view,
            final int duration
    ) {
        showToastView(isSingle, null, view, duration);
    }

    /**
     * 显示 View Toast 方法
     * @param isSingle 是否单例 Toast ( 全局共用 Toast)
     * @param context  {@link Context}
     * @param view     Toast 显示的 View
     * @param duration Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     */
    public static void showToastView(
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
    public static Toast newToastView(
            final boolean isSingle,
            Context context,
            final View view,
            final int duration
    ) {
        if (context == null) {
            context = DevUtils.getContext();
        }
        // 判断是否过滤
        if (!sPriToastFilter.filter(view)) {
            return null;
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

    // ===============
    // = 内部处理方法 =
    // ===============

    /**
     * 处理 R.string 资源 Toast 的格式化
     * @param isSingle   是否单例 Toast ( 全局共用 Toast)
     * @param context    {@link Context}
     * @param resId      R.string.id
     * @param duration   Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG} {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param formatArgs 格式化参数
     */
    private static void handlerToastRes(
            final boolean isSingle,
            Context context,
            @StringRes final int resId,
            final int duration,
            final Object... formatArgs
    ) {
        if (context == null) {
            context = DevUtils.getContext();
        }
        if (context != null) {
            String text = null;
            try {
                if (formatArgs != null && formatArgs.length != 0) {
                    text = context.getString(resId, formatArgs);
                } else {
                    text = context.getString(resId);
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "handlerToastRes");
            }
            priShowToastText(isSingle, context, text, duration);
        }
    }

    /**
     * 处理字符串 Toast 的格式化
     * @param isSingle   是否单例 Toast ( 全局共用 Toast)
     * @param context    {@link Context}
     * @param text       Toast 提示文本
     * @param duration   Toast 显示时长 {@link Toast#LENGTH_SHORT}、{@link Toast#LENGTH_LONG}
     * @param formatArgs 格式化参数
     */
    private static void handlerToastStr(
            final boolean isSingle,
            Context context,
            final String text,
            final int duration,
            final Object... formatArgs
    ) {
        if (context == null) {
            context = DevUtils.getContext();
        }
        // 防止 Context 为 null
        if (context != null) {
            // 表示需要格式化字符串, 只是为了减少 format 步骤, 增加判断, 为 null 不影响
            if (formatArgs != null && formatArgs.length != 0) {
                if (text != null) { // String.format() 中的 formatArgs 可以为 null, 但是 text 不能为 null
                    try {
                        priShowToastText(isSingle, context, String.format(text, formatArgs), duration);
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "handlerToastStr");
                        priShowToastText(isSingle, context, e.getMessage(), duration);
                    }
                } else {
                    priShowToastText(isSingle, context, text, duration);
                }
            } else {
                priShowToastText(isSingle, context, text, duration);
            }
        }
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
    // = 其他接口 =
    // ===========

    /**
     * detail: Toast 过滤器
     * @author Ttt
     */
    public interface Filter {

        /**
         * 判断是否显示
         * @param view Toast 显示的 View
         * @return {@code true} 接着执行, {@code false} 过滤不处理
         */
        boolean filter(View view);

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

    // =============================
    // = ToastUtils.Filter 实现方法 =
    // =============================

    /**
     * 内部 Toast Filter 实现对象
     */
    private static final ToastUtils.Filter sPriToastFilter = new Filter() {

        /**
         * 判断是否显示
         * @param view Toast 显示的 View
         * @return {@code true} 接着执行, {@code false} 过滤不处理
         */
        @Override
        public boolean filter(View view) {
            if (sToastFilter != null) {
                return sToastFilter.filter(view);
            }
            return (view != null);
        }

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