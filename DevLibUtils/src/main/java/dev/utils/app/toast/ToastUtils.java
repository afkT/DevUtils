package dev.utils.app.toast;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Simple Toast 工具类(简单的 Toast 工具类, 支持子线程弹出 Toast)
 * @author Ttt
 * <pre>
 *      支持子线程弹出 Toast, 可通过开关配置
 *      内部解决 Android 7.1.1 崩溃问题
 *      但无处理 部分ROM 如魅族、小米、三星等关闭应用通知，无法显示 Toast 问题
 * </pre>
 */
public final class ToastUtils {

    private ToastUtils() {
    }

    // 日志 TAG
    private static final String TAG = ToastUtils.class.getSimpleName();
    // Toast 判断过滤
    private static ToastUtils.Filter sToastFilter = null;
    // 内部持有单个Toast
    private static Toast mToast = null;
    // 判断是否使用 Handler
    private static boolean mIsHandler = true;
    // 内部 Handler
    private static final Handler sHandler = new Handler(Looper.getMainLooper());
    // Null 值
    private static String mNullText = "text is null";

    // ============
    // = 部分配置 =
    // ============

    // 判断是否使用配置
    private static boolean mUseConfig = true;
    // Toast 的重心、X、Y 轴偏移
    private static int mGravity, mX, mY;
    // 水平边距、垂直边距
    private static float mHorizontalMargin, mVerticalMargin;

    /**
     * 重置默认参数
     */
    public static void reset() {
        mIsHandler = true;
        mUseConfig = true;
        mNullText = "text is null";
        mGravity = mX = mY = 0;
        mHorizontalMargin = mVerticalMargin = 0.0f;
    }

    /**
     * 设置 Toast 过滤器
     * @param toastFilter
     */
    public static void setToastFilter(final ToastUtils.Filter toastFilter) {
        ToastUtils.sToastFilter = toastFilter;
    }

    /**
     * 设置是否使用 Handler 显示 Toast
     * @param isHandler
     */
    public static void setIsHandler(final boolean isHandler) {
        ToastUtils.mIsHandler = isHandler;
    }

    /**
     * 设置 Text 为 null 的文本
     * @param nullText
     */
    public static void setNullText(final String nullText) {
        ToastUtils.mNullText = nullText;
    }

    /**
     * 判断是否使用配置
     * @param useConfig
     */
    public static void setUseConfig(final boolean useConfig) {
        ToastUtils.mUseConfig = useConfig;
    }

    /**
     * 设置 Toast 显示在屏幕上的位置。
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        ToastUtils.mGravity = gravity;
        ToastUtils.mX = xOffset;
        ToastUtils.mY = yOffset;
    }

    /**
     * 设置边距
     * @param horizontalMargin
     * @param verticalMargin
     */
    public static void setMargin(final float horizontalMargin, final float verticalMargin) {
        ToastUtils.mHorizontalMargin = horizontalMargin;
        ToastUtils.mVerticalMargin = verticalMargin;
    }

    // =================
    // = 统一显示Toast =
    // =================

    // ======================
    // = Toast.LENGTH_SHORT =
    // ======================

    /**
     * 显示 LENGTH_SHORT Toast
     * @param text
     * @param objs
     */
    public static void showShort(final String text, final Object... objs) {
        showShort(null, text, objs);
    }

    /**
     * 显示 LENGTH_SHORT Toast
     * @param context
     * @param text
     * @param objs
     */
    public static void showShort(final Context context, final String text, final Object... objs) {
        handlerToastStr(true, context, text, Toast.LENGTH_SHORT, objs);
    }

    // =

    /**
     * * 显示 LENGTH_SHORT Toast
     * @param resId
     * @param objs
     */
    public static void showShort(final int resId, final Object... objs) {
        showShort(null, resId, objs);
    }

    /**
     * * 显示 LENGTH_SHORT Toast
     * @param context
     * @param resId
     * @param objs
     */
    public static void showShort(final Context context, final int resId, final Object... objs) {
        handlerToastRes(true, context, resId, Toast.LENGTH_SHORT, objs);
    }

    // =====================
    // = Toast.LENGTH_LONG =
    // =====================

    /**
     * 显示 LENGTH_LONG Toast
     * @param text
     * @param objs
     */
    public static void showLong(final String text, final Object... objs) {
        showLong(null, text, objs);
    }

    /**
     * 显示 LENGTH_LONG Toast
     * @param context
     * @param text
     * @param objs
     */
    public static void showLong(final Context context, final String text, final Object... objs) {
        handlerToastStr(true, context, text, Toast.LENGTH_LONG, objs);
    }

    // =

    /**
     * 显示 LENGTH_LONG Toast
     * @param resId
     * @param objs
     */
    public static void showLong(final int resId, final Object... objs) {
        showLong(null, resId, objs);
    }

    /**
     * 显示 LENGTH_LONG Toast
     * @param context
     * @param resId
     * @param objs
     */
    public static void showLong(final Context context, final int resId, final Object... objs) {
        handlerToastRes(true, context, resId, Toast.LENGTH_LONG, objs);
    }

    // =============
    // = Toast方法 =
    // =============

    /**
     * 显示 Toast
     * @param resId
     * @param duration
     */
    public static void showToast(final int resId, final int duration) {
        showToast(null, resId, duration);
    }

    /**
     * 显示 Toast
     * @param context
     * @param resId
     * @param duration
     */
    public static void showToast(final Context context, final int resId, final int duration) {
        handlerToastRes(true, context, resId, duration);
    }

    /**
     * 显示 Toast
     * @param text
     * @param duration
     */
    public static void showToast(final String text, final int duration) {
        priShowToastText(true, null, text, duration);
    }

    /**
     * 显示 Toast
     * @param context
     * @param text
     * @param duration
     */
    public static void showToast(final Context context, final String text, final int duration) {
        priShowToastText(true, context, text, duration);
    }

    // ===================
    // = 非统一显示Toast =
    // ===================

    // ======================
    // = Toast.LENGTH_SHORT =
    // ======================

    /**
     * 显示 new LENGTH_SHORT Toast
     * @param text
     * @param objs
     */
    public static void showShortNew(final String text, final Object... objs) {
        showShortNew(null, text, objs);
    }

    /**
     * 显示 new LENGTH_SHORT Toast
     * @param context
     * @param text
     * @param objs
     */
    public static void showShortNew(final Context context, final String text, final Object... objs) {
        handlerToastStr(false, context, text, Toast.LENGTH_SHORT, objs);
    }

    // =

    /**
     * 显示 new LENGTH_SHORT Toast
     * @param resId
     * @param objs
     */
    public static void showShortNew(final int resId, final Object... objs) {
        showShortNew(null, resId, objs);
    }

    /**
     * 显示 new LENGTH_SHORT Toast
     * @param context
     * @param resId
     * @param objs
     */
    public static void showShortNew(final Context context, final int resId, final Object... objs) {
        handlerToastRes(false, context, resId, Toast.LENGTH_SHORT, objs);
    }

    // =====================
    // = Toast.LENGTH_LONG =
    // =====================

    /**
     * 显示 new LENGTH_LONG Toast
     * @param text
     * @param objs
     */
    public static void showLongNew(final String text, final Object... objs) {
        showLongNew(null, text, objs);
    }

    /**
     * 显示 new LENGTH_LONG Toast
     * @param context
     * @param text
     * @param objs
     */
    public static void showLongNew(final Context context, final String text, final Object... objs) {
        handlerToastStr(false, context, text, Toast.LENGTH_LONG, objs);
    }

    // =

    /**
     * 显示 new LENGTH_LONG Toast
     * @param resId
     * @param objs
     */
    public static void showLongNew(final int resId, final Object... objs) {
        showLongNew(null, resId, objs);
    }

    /**
     * 显示 new LENGTH_LONG Toast
     * @param context
     * @param resId
     * @param objs
     */
    public static void showLongNew(final Context context, final int resId, final Object... objs) {
        handlerToastRes(false, context, resId, Toast.LENGTH_LONG, objs);
    }

    // =============
    // = Toast方法 =
    // =============

    /**
     * 显示新的 Toast
     * @param resId
     * @param duration
     */
    public static void showToastNew(final int resId, final int duration) {
        showToastNew(null, resId, duration);
    }

    /**
     * 显示新的 Toast
     * @param context
     * @param resId
     * @param duration
     */
    public static void showToastNew(final Context context, final int resId, final int duration) {
        handlerToastRes(false, context, resId, duration);
    }

    /**
     * 显示新的 Toast
     * @param text
     * @param duration
     */
    public static void showToastNew(final String text, final int duration) {
        priShowToastText(false, null, text, duration);
    }

    /**
     * 显示新的 Toast
     * @param context
     * @param text
     * @param duration
     */
    public static void showToastNew(final Context context, final String text, final int duration) {
        priShowToastText(false, context, text, duration);
    }

    // ==============
    // = 显示 Toast =
    // ==============

    /**
     * 内部私有方法, 最终显示 Toast
     * @param isSingle
     * @param context
     * @param text
     * @param duration
     * @Toast
     */
    private static void priShowToastText(final boolean isSingle, final Context context, final String text, final int duration) {
        if (mIsHandler) {
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
     * @param isSingle
     * @param context
     * @param text
     * @param duration
     * @return
     */
    public static Toast newToastText(final boolean isSingle, Context context, String text, final int duration) {
        if (context == null) {
            context = DevUtils.getContext();
        }
        // 判断是否过滤
        if (!sPriToastFilter.filter(text)) {
            return null;
        }
        // 处理内容
        text = sPriToastFilter.handlerContent(text);
        // 设置为null, 便于提示排查
        if (TextUtils.isEmpty(text)) {
            text = mNullText;
            // 如果还是为null, 则不处理
            if (TextUtils.isEmpty(text)) {
                return null;
            }
        }
        // 判断是否显示唯一, 单独共用一个
        if (isSingle) {
            try {
                // 关闭旧的 Toast
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }
                // 解决 MIUI 会显示应用名称问题
                mToast = Toast.makeText(context, null, duration);
                mToast.setText(text);
                // 判断是否使用配置
                if (mUseConfig) {
                    // 设置属性配置
                    if (mGravity != 0) {
                        mToast.setGravity(mGravity, mX, mY);
                    }
                    mToast.setMargin(mHorizontalMargin, mVerticalMargin);
                }
                // 反射 Hook Toast 解决 Android 7.1.1 崩溃问题
                reflectToastHandler(mToast);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "newToastText");
            }
            return mToast;
        } else {
            Toast toast = null;
            try {
                // 解决 MIUI 会显示应用名称问题
                toast = Toast.makeText(context, null, duration);
                toast.setText(text);
                // 判断是否使用配置
                if (mUseConfig) {
                    // 设置属性配置
                    if (mGravity != 0) {
                        toast.setGravity(mGravity, mX, mY);
                    }
                    toast.setMargin(mHorizontalMargin, mVerticalMargin);
                }
                // 反射 Hook Toast 解决 Android 7.1.1 崩溃问题
                reflectToastHandler(toast);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "newToastText");
            }
            return toast;
        }
    }

    // ===================
    // = 显示 View Toast =
    // ===================

    /**
     * 显示 View Toast 方法
     * @param view
     */
    public static void showToastView(final View view) {
        showToastView(true, null, view, Toast.LENGTH_SHORT);
    }

    /**
     * 显示 View Toast 方法
     * @param view
     * @param duration
     */
    public static void showToastView(final View view, final int duration) {
        showToastView(true, null, view, duration);
    }

    /**
     * 显示 View Toast 方法
     * @param isSingle
     * @param view
     */
    public static void showToastView(final boolean isSingle, final View view) {
        showToastView(isSingle, null, view, Toast.LENGTH_SHORT);
    }

    /**
     * 显示 View Toast 方法
     * @param isSingle
     * @param view
     * @param duration
     */
    public static void showToastView(final boolean isSingle, final View view, final int duration) {
        showToastView(isSingle, null, view, duration);
    }

    /**
     * 显示 View Toast 方法
     * @param isSingle
     * @param context
     * @param view
     * @param duration
     */
    public static void showToastView(final boolean isSingle, final Context context, final View view, final int duration) {
        if (view == null) return;
        if (mIsHandler) {
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
     * @param isSingle
     * @param context
     * @param view
     * @param duration
     * @return
     */
    public static Toast newToastView(final boolean isSingle, Context context, final View view, final int duration) {
        if (context == null) {
            context = DevUtils.getContext();
        }
        // 判断是否过滤
        if (!sPriToastFilter.filter(view)) {
            return null;
        }
        // 防止 Context 为null
        if (context == null) {
            return null;
        } else if (view == null) { // 防止显示的View 为null
            return null;
        }
        // 判断是否显示唯一, 单独共用一个
        if (isSingle) {
            try {
                // 关闭旧的 Toast
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }
                // 解决 MIUI 会显示应用名称问题
                mToast = new Toast(context);
                mToast.setView(view);
                mToast.setDuration(duration);
                // 判断是否使用配置
                if (mUseConfig) {
                    // 设置属性配置
                    if (mGravity != 0) {
                        mToast.setGravity(mGravity, mX, mY);
                    }
                    mToast.setMargin(mHorizontalMargin, mVerticalMargin);
                }
                // 反射 Hook Toast 解决 Android 7.1.1 崩溃问题
                reflectToastHandler(mToast);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "newToastView");
            }
            return mToast;
        } else {
            Toast toast = null;
            try {
                // 解决 MIUI 会显示应用名称问题
                toast = new Toast(context);
                toast.setView(view);
                toast.setDuration(duration);
                // 判断是否使用配置
                if (mUseConfig) {
                    // 设置属性配置
                    if (mGravity != 0) {
                        toast.setGravity(mGravity, mX, mY);
                    }
                    toast.setMargin(mHorizontalMargin, mVerticalMargin);
                }
                // 反射 Hook Toast 解决 Android 7.1.1 崩溃问题
                reflectToastHandler(toast);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "newToastView");
            }
            return toast;
        }
    }

    // ================
    // = 内部处理方法 =
    // ================

    /**
     * 处理 R.string 资源Toast的格式化
     * @param isSingle 是否单独共用显示一个
     * @param context
     * @param resId
     * @param duration
     * @param objs
     */
    private static void handlerToastRes(final boolean isSingle, Context context, final int resId, final int duration, final Object... objs) {
        if (context == null) {
            context = DevUtils.getContext();
        }
        if (context != null) {
            String text = null;
            try {
                // 获取字符串并且进行格式化
                if (objs != null && objs.length != 0) {
                    text = context.getString(resId, objs);
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
     * 处理字符串Toast的格式化
     * @param context
     * @param text
     * @param duration
     * @param objs
     */
    private static void handlerToastStr(final boolean isSingle, Context context, final String text, final int duration, final Object... objs) {
        if (context == null) {
            context = DevUtils.getContext();
        }
        // 防止 Context 为null
        if (context != null) {
            // 表示需要格式化字符串,只是为了减少 format步骤,增加判断，为null不影响
            if (objs != null && objs.length != 0) {
                if (text != null) { // String.format() 中的 objs 可以为null,但是 text不能为null
                    try {
                        priShowToastText(isSingle, context, String.format(text, objs), duration);
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

    // ===============================
    // = 解决 Android 7.1.1 崩溃问题 =
    // ===============================

    /**
     * 反射 Hook Toast 设置 Handler
     * @param toast
     */
    private static void reflectToastHandler(final Toast toast) {
        if (toast == null) return;
        // 反射设置 Toat Handler 解决 Android7.1.1Toast 崩溃 问题
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            try {
                Field field_tn = Toast.class.getDeclaredField("mTN");
                field_tn.setAccessible(true);

                Object mTN = field_tn.get(toast);
                Field field_handler = field_tn.getType().getDeclaredField("mHandler");
                field_handler.setAccessible(true);

                Handler handler = (Handler) field_handler.get(mTN);
                field_handler.set(mTN, new SafeHandler(handler));
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * detail: Toast 安全显示 Handler
     * @author Ttt
     */
    private static final class SafeHandler extends Handler {

        private Handler mHandler;

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
            } catch (Exception ignored) {
            }
        }
    }

    // ============
    // = 其他接口 =
    // ============

    /**
     * detail: Toast 过滤器
     * @author Ttt
     */
    public interface Filter {

        /**
         * 判断是否显示
         * @param view
         * @return {@code true} 接着执行, {@code false} 过滤不处理
         */
        boolean filter(View view);

        /**
         * 判断是否显示
         * @param content
         * @return {@code true} 接着执行, {@code false} 过滤不处理
         */
        boolean filter(String content);

        /**
         * 获取 Toast 显示的文案
         * @param content
         * @return 处理后的内容
         */
        String handlerContent(String content);
    }

    // ==============================
    // = ToastUtils.Filter 实现方法 =
    // ==============================

    /**
     * 内部 Toast Filter 实现对象
     */
    private final static ToastUtils.Filter sPriToastFilter = new Filter() {

        /**
         * 判断是否显示
         * @param view
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
         * @param content
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
         * @param content
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
