package dev.utils.app.toast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
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

/**
 * detail: 自定义View着色美化 Toast 工具类
 * @author Ttt
 * <pre>
 *      支持子线程弹出 Toast, 可通过开关配置
 *      内部解决 Android 7.1.1 崩溃问题
 *      但无处理 部分ROM 如魅族、小米、三星等关闭应用通知，无法显示 Toast 问题
 * </pre>
 */
public final class ToastTintUtils {

    private ToastTintUtils() {
    }

    // 日志 TAG
    private static final String TAG = ToastTintUtils.class.getSimpleName();
    // Toast 判断过滤
    private static ToastTintUtils.Filter sToastFilter = null;
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

    // ============
    // = 样式相关 =
    // ============

    // 默认样式
    private static ToastTintUtils.Style defaultStyle = new DefaultStyle();
    // Normal 样式
    private static ToastTintUtils.Style normalStyle = new NormalStyle();
    // Info 样式
    private static ToastTintUtils.Style infoStyle = new InfoStyle();
    // Warning 样式
    private static ToastTintUtils.Style warningStyle = new WarningStyle();
    // Error 样式
    private static ToastTintUtils.Style errorStyle = new ErrorStyle();
    // Success 样式
    private static ToastTintUtils.Style successStyle = new SuccessStyle();
    // =
    // info icon
    private static Drawable infoDrawable = null;
    // warning icon
    private static Drawable warningDrawable = null;
    // error icon
    private static Drawable errorDrawable = null;
    // Success icon
    private static Drawable successDrawable = null;

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
    public static void setToastFilter(final ToastTintUtils.Filter toastFilter) {
        ToastTintUtils.sToastFilter = toastFilter;
    }

    /**
     * 设置是否使用 Handler 显示 Toast
     * @param isHandler
     */
    public static void setIsHandler(final boolean isHandler) {
        ToastTintUtils.mIsHandler = isHandler;
    }

    /**
     * 设置 Text 为 null 的文本
     * @param nullText
     */
    public static void setNullText(final String nullText) {
        ToastTintUtils.mNullText = nullText;
    }

    /**
     * 判断是否使用配置
     * @param useConfig
     */
    public static void setUseConfig(final boolean useConfig) {
        ToastTintUtils.mUseConfig = useConfig;
    }

    /**
     * 设置 Toast 显示在屏幕上的位置。
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        ToastTintUtils.mGravity = gravity;
        ToastTintUtils.mX = xOffset;
        ToastTintUtils.mY = yOffset;
    }

    /**
     * 设置边距
     * @param horizontalMargin
     * @param verticalMargin
     */
    public static void setMargin(final float horizontalMargin, final float verticalMargin) {
        ToastTintUtils.mHorizontalMargin = horizontalMargin;
        ToastTintUtils.mVerticalMargin = verticalMargin;
    }

    // =

    /**
     * 获取默认样式
     * @return
     */
    public static Style getDefaultStyle() {
        return defaultStyle;
    }

    /**
     * 获取 Normal 样式
     * @return
     */
    public static Style getNormalStyle() {
        return normalStyle;
    }

    /**
     * 获取 Info 样式
     * @return
     */
    public static Style getInfoStyle() {
        return infoStyle;
    }

    /**
     * 获取 Warning 样式
     * @return
     */
    public static Style getWarningStyle() {
        return warningStyle;
    }

    /**
     * 获取 Error 样式
     * @return
     */
    public static Style getErrorStyle() {
        return errorStyle;
    }

    /**
     * 获取 Success 样式
     * @return
     */
    public static Style getSuccessStyle() {
        return successStyle;
    }

    // =

    /**
     * 设置 Normal 样式
     * @param normalStyle
     */
    public static void setNormalStyle(final Style normalStyle) {
        ToastTintUtils.normalStyle = normalStyle;
    }

    /**
     * 设置 Info 样式
     * @param infoStyle
     */
    public static void setInfoStyle(final Style infoStyle) {
        ToastTintUtils.infoStyle = infoStyle;
    }

    /**
     * 设置 Warning 样式
     * @param warningStyle
     */
    public static void setWarningStyle(final Style warningStyle) {
        ToastTintUtils.warningStyle = warningStyle;
    }

    /**
     * 设置 Error 样式
     * @param errorStyle
     */
    public static void setErrorStyle(final Style errorStyle) {
        ToastTintUtils.errorStyle = errorStyle;
    }

    /**
     * 设置 Success 样式
     * @param successStyle
     */
    public static void setSuccessStyle(final Style successStyle) {
        ToastTintUtils.successStyle = successStyle;
    }

    /**
     * 获取 Info 样式 icon
     * @return
     */
    public static Drawable getInfoDrawable() {
        if (infoDrawable != null) {
            return infoDrawable;
        }
        infoDrawable = getDrawable(DevUtils.getContext(), R.drawable.dev_toast_icon_info_white);
        return infoDrawable;
    }

    /**
     * 获取 Warning 样式 icon
     * @return
     */
    public static Drawable getWarningDrawable() {
        if (warningDrawable != null) {
            return warningDrawable;
        }
        warningDrawable = getDrawable(DevUtils.getContext(), R.drawable.dev_toast_icon_warning_white);
        return warningDrawable;
    }

    /**
     * 获取 Error 样式 icon
     * @return
     */
    public static Drawable getErrorDrawable() {
        if (errorDrawable != null) {
            return errorDrawable;
        }
        errorDrawable = getDrawable(DevUtils.getContext(), R.drawable.dev_toast_icon_error_white);
        return errorDrawable;
    }

    /**
     * 获取 Success 样式 icon
     * @return
     */
    public static Drawable getSuccessDrawable() {
        if (successDrawable != null) {
            return successDrawable;
        }
        successDrawable = getDrawable(DevUtils.getContext(), R.drawable.dev_toast_icon_success_white);
        return successDrawable;
    }

    // =====================
    // = 显示 normal Toast =
    // =====================

    /**
     * normal 样式 Toast
     */
    public static void normal(final String text) {
        custom(true, null, normalStyle, text, Toast.LENGTH_SHORT, null);
    }

    /**
     * normal 样式 Toast
     */
    public static void normal(final String text, final int duration) {
        custom(true, null, normalStyle, text, duration, null);
    }

    /**
     * normal 样式 Toast
     */
    public static void normal(final String text, final Drawable icon) {
        custom(true, null, normalStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * normal 样式 Toast
     */
    public static void normal(final String text, final int duration, final Drawable icon) {
        custom(true, null, normalStyle, text, duration, icon);
    }

    // =

    /**
     * normal 样式 Toast
     */
    public static void normal(final boolean isSingle, final String text) {
        custom(isSingle, null, normalStyle, text, Toast.LENGTH_SHORT, null);
    }

    /**
     * normal 样式 Toast
     */
    public static void normal(final boolean isSingle, final String text, final int duration) {
        custom(isSingle, null, normalStyle, text, duration, null);
    }

    /**
     * normal 样式 Toast
     */
    public static void normal(final boolean isSingle, final String text, final Drawable icon) {
        custom(isSingle, null, normalStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * normal 样式 Toast
     */
    public static void normal(final boolean isSingle, final String text, final int duration, final Drawable icon) {
        custom(isSingle, null, normalStyle, text, duration, icon);
    }

    // ===================
    // = 显示 info Toast =
    // ===================

    /**
     * info 样式 Toast
     */
    public static void info(final String text) {
        custom(true, null, infoStyle, text, Toast.LENGTH_SHORT, getInfoDrawable());
    }

    /**
     * info 样式 Toast
     */
    public static void info(final String text, final int duration) {
        custom(true, null, infoStyle, text, duration, getInfoDrawable());
    }

    /**
     * info 样式 Toast
     */
    public static void info(final String text, final Drawable icon) {
        custom(true, null, infoStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * info 样式 Toast
     */
    public static void info(final String text, final int duration, final Drawable icon) {
        custom(true, null, infoStyle, text, duration, icon);
    }

    // =

    /**
     * info 样式 Toast
     */
    public static void info(final boolean isSingle, final String text) {
        custom(isSingle, null, infoStyle, text, Toast.LENGTH_SHORT, getInfoDrawable());
    }

    /**
     * info 样式 Toast
     */
    public static void info(final boolean isSingle, final String text, final int duration) {
        custom(isSingle, null, infoStyle, text, duration, getInfoDrawable());
    }

    /**
     * info 样式 Toast
     */
    public static void info(final boolean isSingle, final String text, final Drawable icon) {
        custom(isSingle, null, infoStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * info 样式 Toast
     */
    public static void info(final boolean isSingle, final String text, final int duration, final Drawable icon) {
        custom(isSingle, null, infoStyle, text, duration, icon);
    }

    // ======================
    // = 显示 warning Toast =
    // ======================

    /**
     * warning 样式 Toast
     */
    public static void warning(final String text) {
        custom(true, null, warningStyle, text, Toast.LENGTH_SHORT, getWarningDrawable());
    }

    /**
     * warning 样式 Toast
     */
    public static void warning(final String text, final int duration) {
        custom(true, null, warningStyle, text, duration, getWarningDrawable());
    }

    /**
     * warning 样式 Toast
     */
    public static void warning(final String text, final Drawable icon) {
        custom(true, null, warningStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * warning 样式 Toast
     */
    public static void warning(final String text, final int duration, final Drawable icon) {
        custom(true, null, warningStyle, text, duration, icon);
    }

    // =

    /**
     * warning 样式 Toast
     */
    public static void warning(final boolean isSingle, final String text) {
        custom(isSingle, null, warningStyle, text, Toast.LENGTH_SHORT, getWarningDrawable());
    }

    /**
     * warning 样式 Toast
     */
    public static void warning(final boolean isSingle, final String text, final int duration) {
        custom(isSingle, null, warningStyle, text, duration, getWarningDrawable());
    }

    /**
     * warning 样式 Toast
     */
    public static void warning(final boolean isSingle, final String text, final Drawable icon) {
        custom(isSingle, null, warningStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * warning 样式 Toast
     */
    public static void warning(final boolean isSingle, final String text, final int duration, final Drawable icon) {
        custom(isSingle, null, warningStyle, text, duration, icon);
    }

    // ====================
    // = 显示 error Toast =
    // ====================

    /**
     * error 样式 Toast
     */
    public static void error(final String text) {
        custom(true, null, errorStyle, text, Toast.LENGTH_SHORT, getErrorDrawable());
    }

    /**
     * error 样式 Toast
     */
    public static void error(final String text, final int duration) {
        custom(true, null, errorStyle, text, duration, getErrorDrawable());
    }

    /**
     * error 样式 Toast
     */
    public static void error(final String text, final Drawable icon) {
        custom(true, null, errorStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * error 样式 Toast
     */
    public static void error(final String text, final int duration, final Drawable icon) {
        custom(true, null, errorStyle, text, duration, icon);
    }

    // =

    /**
     * error 样式 Toast
     */
    public static void error(final boolean isSingle, final String text) {
        custom(isSingle, null, errorStyle, text, Toast.LENGTH_SHORT, getErrorDrawable());
    }

    /**
     * error 样式 Toast
     */
    public static void error(final boolean isSingle, final String text, final int duration) {
        custom(isSingle, null, errorStyle, text, duration, getErrorDrawable());
    }

    /**
     * error 样式 Toast
     */
    public static void error(final boolean isSingle, final String text, final Drawable icon) {
        custom(isSingle, null, errorStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * error 样式 Toast
     */
    public static void error(final boolean isSingle, final String text, final int duration, final Drawable icon) {
        custom(isSingle, null, errorStyle, text, duration, icon);
    }

    // ======================
    // = 显示 success Toast =
    // ======================

    /**
     * success 样式 Toast
     */
    public static void success(final String text) {
        custom(true, null, successStyle, text, Toast.LENGTH_SHORT, getSuccessDrawable());
    }

    /**
     * success 样式 Toast
     */
    public static void success(final String text, final int duration) {
        custom(true, null, successStyle, text, duration, getSuccessDrawable());
    }

    /**
     * success 样式 Toast
     */
    public static void success(final String text, final Drawable icon) {
        custom(true, null, successStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * success 样式 Toast
     */
    public static void success(final String text, final int duration, final Drawable icon) {
        custom(true, null, successStyle, text, duration, icon);
    }

    // =

    /**
     * success 样式 Toast
     */
    public static void success(final boolean isSingle, final String text) {
        custom(isSingle, null, successStyle, text, Toast.LENGTH_SHORT, getSuccessDrawable());
    }

    /**
     * success 样式 Toast
     */
    public static void success(final boolean isSingle, final String text, final int duration) {
        custom(isSingle, null, successStyle, text, duration, getSuccessDrawable());
    }

    /**
     * success 样式 Toast
     */
    public static void success(final boolean isSingle, final String text, final Drawable icon) {
        custom(isSingle, null, successStyle, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * success 样式 Toast
     */
    public static void success(final boolean isSingle, final String text, final int duration, Drawable icon) {
        custom(isSingle, null, successStyle, text, duration, icon);
    }

    // =====================
    // = 显示 custom Toast =
    // =====================

    /**
     * custom Toast
     */
    public static void custom(final ToastTintUtils.Style style, final String text) {
        custom(true, null, style, text, Toast.LENGTH_SHORT, null);
    }

    /**
     * custom Toast
     */
    public static void custom(final ToastTintUtils.Style style, final String text, final int duration) {
        custom(true, null, style, text, duration, null);
    }

    /**
     * custom Toast
     */
    public static void custom(final ToastTintUtils.Style style, final String text, final Drawable icon) {
        custom(true, null, style, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * custom Toast
     */
    public static void custom(final ToastTintUtils.Style style, final String text, final int duration, final Drawable icon) {
        custom(true, null, style, text, duration, icon);
    }

    // =

    /**
     * custom Toast
     */
    public static void custom(final boolean isSingle, final ToastTintUtils.Style style, final String text) {
        custom(isSingle, null, style, text, Toast.LENGTH_SHORT, null);
    }

    /**
     * custom Toast
     */
    public static void custom(final boolean isSingle, final ToastTintUtils.Style style, final String text, final int duration) {
        custom(isSingle, null, style, text, duration, null);
    }

    /**
     * custom Toast
     */
    public static void custom(final boolean isSingle, final ToastTintUtils.Style style, final String text, final Drawable icon) {
        custom(isSingle, null, style, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * custom Toast
     */
    public static void custom(final boolean isSingle, final ToastTintUtils.Style style, final String text, final int duration, final Drawable icon) {
        custom(isSingle, null, style, text, duration, icon);
    }

    // =

    /**
     * custom Toast
     */
    public static void custom(final Context context, final ToastTintUtils.Style style, final String text) {
        custom(true, context, style, text, Toast.LENGTH_SHORT, null);
    }

    /**
     * custom Toast
     */
    public static void custom(final Context context, final ToastTintUtils.Style style, final String text, final int duration) {
        custom(true, context, style, text, duration, null);
    }

    /**
     * custom Toast
     */
    public static void custom(final Context context, final ToastTintUtils.Style style, final String text, final Drawable icon) {
        custom(true, context, style, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * custom Toast
     */
    public static void custom(final Context context, final ToastTintUtils.Style style, final String text, final int duration, final Drawable icon) {
        custom(true, context, style, text, duration, icon);
    }

    // =

    /**
     * custom Toast
     */
    public static void custom(final boolean isSingle, final Context context, final ToastTintUtils.Style style, final String text) {
        custom(isSingle, context, style, text, Toast.LENGTH_SHORT, null);
    }

    /**
     * custom Toast
     */
    public static void custom(final boolean isSingle, final Context context, final ToastTintUtils.Style style, final String text, final int duration) {
        custom(isSingle, context, style, text, duration, null);
    }

    /**
     * custom Toast
     */
    public static void custom(final boolean isSingle, final Context context, final ToastTintUtils.Style style, final String text, final Drawable icon) {
        custom(isSingle, context, style, text, Toast.LENGTH_SHORT, icon);
    }

    /**
     * custom Toast
     * @param isSingle 是否单独显示
     * @param context
     * @param style    Toast 样式
     * @param text     文案
     * @param duration 显示时长
     * @param icon     图标
     */
    public static void custom(final boolean isSingle, final Context context, final ToastTintUtils.Style style, final String text, final int duration, final Drawable icon) {
        // 获取View
        View view = inflaterView(context, style, text, icon);
        // 显示Toast
        showToastView(isSingle, context, view, duration);
    }

    // ==============
    // = 内部 Toast =
    // ==============

    /**
     * 显示 View Toast 方法
     * @param isSingle
     * @param context
     * @param view
     * @param duration
     */
    private static void showToastView(final boolean isSingle, final Context context, final View view, final int duration) {
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
    private static Toast newToastView(final boolean isSingle, Context context, final View view, final int duration) {
        if (context == null) {
            context = DevUtils.getContext();
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

    /**
     * 实例化 Layout View
     * @param context
     * @param style
     * @param text
     * @param icon
     * @return
     */
    private static View inflaterView(Context context, final ToastTintUtils.Style style, String text, Drawable icon) {
        if (context == null) {
            context = DevUtils.getContext();
        }
        // 如果样式为 null, 则不处理
        if (style == null) {
            return null;
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
        if (context != null) {
            try {
                // 引入View
                final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(dev.utils.R.layout.dev_toast_layout, null);
                // 初始化View
                final ImageView toastIcon = toastLayout.findViewById(dev.utils.R.id.vid_dtl_toast_igview);
                final TextView toastTextView = toastLayout.findViewById(dev.utils.R.id.vid_dtl_toast_tv);

                // =================
                // = TextView 相关 =
                // =================
                // 设置文案
                toastTextView.setText(text);
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
                // 设置Ellipsize 效果
                if (style.getEllipsize() != null) {
                    toastTextView.setEllipsize(style.getEllipsize());
                }
                // 设置字体样式
                if (style.getTypeface() != null) {
                    toastTextView.setTypeface(style.getTypeface());
                }

                // ==================
                // = ImageView 相关 =
                // ==================
                // 判断是否使用图标
                if (icon != null) {
                    // 判断是否渲染图标
                    if (style.isTintIcon() && style.getTintIconColor() != 0) {
                        icon = tintIcon(icon, style.getTintIconColor());
                    }
                    // 设置 ImageView 图片
                    setBackground(toastIcon, icon);
                } else {
                    // 隐藏图标
                    toastIcon.setVisibility(View.GONE);
                }

                // =================
                // = 背景View 相关 =
                // =================
                // 背景图片
                Drawable drawableFrame = style.getBackground();
                // 判断是否为 null
                if (drawableFrame == null) {
                    drawableFrame = getDrawable(context, dev.utils.R.drawable.dev_toast_frame);
                    // 判断是否需要着色
                    if (style.getBackgroundTintColor() != 0) { // 根据背景色进行渲染透明图片
                        drawableFrame = tint9PatchDrawableFrame(context, style.getBackgroundTintColor());
                    }
                }
                // 设置 View 背景
                setBackground(toastLayout, drawableFrame);
                // 返回View
                return toastLayout;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "inflaterView");
            }
        }
        // 默认返回null
        return null;
    }

    // ============
    // = 内部方法 =
    // ============

    /**
     * 设置背景
     * @param view
     * @param drawable
     */
    private static void setBackground(final View view, final Drawable drawable) {
        if (view != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                view.setBackground(drawable);
            else
                view.setBackgroundDrawable(drawable);
        }
    }

    /**
     * 获取 Drawable
     * @param context
     * @param id
     * @return
     */
    private static Drawable getDrawable(final Context context, final @DrawableRes int id) {
        if (context == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return context.getDrawable(id);
        else
            return context.getResources().getDrawable(id);
    }

    /**
     * 图片着色
     * @param drawable
     * @param tintColor
     * @return
     */
    private static Drawable tintIcon(final Drawable drawable, final @ColorInt int tintColor) {
        if (drawable != null) {
            try {
                drawable.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "tintIcon");
            }
        }
        return drawable;
    }

    /**
     * .9 图片着色
     * @param context
     * @param tintColor
     * @return
     */
    private static Drawable tint9PatchDrawableFrame(final Context context, final @ColorInt int tintColor) {
        try {
            final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(context, dev.utils.R.drawable.dev_toast_frame);
            return tintIcon(toastDrawable, tintColor);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "tint9PatchDrawableFrame");
        }
        return null;
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
    // = 样式相关 =
    // ============

    /**
     * detail: Toast 自定义View 着色等相关 样式配置
     * @author Ttt
     */
    public interface Style {

        /**
         * 文本颜色
         * @return
         */
        int getTextColor();

        /**
         * 字体大小
         * @return
         */
        float getTextSize();

        /**
         * 背景着色颜色
         * @return
         */
        int getBackgroundTintColor();

        /**
         * 背景图片
         * @return
         */
        Drawable getBackground();

        /**
         * 最大行数
         * @return
         */
        int getMaxLines();

        /**
         * Ellipsize 效果
         * @return
         */
        TextUtils.TruncateAt getEllipsize();

        /**
         * 字体样式
         * @return
         */
        Typeface getTypeface();

        /**
         * 获取图标着色颜色
         * @return
         */
        int getTintIconColor();

        /**
         * 是否渲染图标 - getTintIconColor() 着色渲染
         * @return
         */
        boolean isTintIcon();
    }

    // =

    /**
     * detail: 默认样式
     * @author Ttt
     */
    public static class DefaultStyle implements Style {

        /**
         * 文本颜色
         * @return
         */
        @Override
        public int getTextColor() {
            return Color.WHITE;
        }

        /**
         * 字体大小
         * @return
         */
        @Override
        public float getTextSize() {
            return 16f;
        }

        /**
         * 背景着色颜色
         * @return
         */
        @Override
        public int getBackgroundTintColor() {
            return 0;
        }

        /**
         * 背景图片
         * @return
         */
        @Override
        public Drawable getBackground() {
            return null;
        }

        /**
         * 最大行数
         * @return
         */
        @Override
        public int getMaxLines() {
            return 0;
        }

        /**
         * Ellipsize 效果
         * @return
         */
        @Override
        public TextUtils.TruncateAt getEllipsize() {
            return null;
        }

        /**
         * 字体样式
         * @return
         */
        @Override
        public Typeface getTypeface() {
            // return Typeface.create("sans-serif-condensed", Typeface.NORMAL);
            return null;
        }

        /**
         * 获取图标着色颜色
         * @return
         */
        @Override
        public int getTintIconColor() {
            return Color.WHITE;
        }

        /**
         * 是否渲染图标 - getTintIconColor() 着色渲染
         * @return
         */
        @Override
        public boolean isTintIcon() {
            return false;
        }
    }

    /**
     * detail: Normal 样式 - 灰色
     * @author Ttt
     */
    public static class NormalStyle extends DefaultStyle {

        /**
         * 背景着色颜色
         */
        @Override
        public int getBackgroundTintColor() {
            return Color.parseColor("#353A3E");
        }

        /**
         * 是否渲染图标 - getTintIconColor() 着色渲染
         */
        @Override
        public boolean isTintIcon() {
            return true;
        }
    }

    /**
     * detail: Info 样式 - 海洋蓝
     * @author Ttt
     */
    public static class InfoStyle extends DefaultStyle {

        /**
         * 背景着色颜色
         */
        @Override
        public int getBackgroundTintColor() {
            return Color.parseColor("#3F51B5");
        }

        /**
         * 是否渲染图标 - getTintIconColor() 着色渲染
         */
        @Override
        public boolean isTintIcon() {
            return true;
        }
    }

    /**
     * detail: Warning 样式 - 橙色
     * @author Ttt
     */
    public static class WarningStyle extends DefaultStyle {

        /**
         * 背景着色颜色
         */
        @Override
        public int getBackgroundTintColor() {
            return Color.parseColor("#FFA900");
        }

        /**
         * 是否渲染图标 - getTintIconColor() 着色渲染
         */
        @Override
        public boolean isTintIcon() {
            return true;
        }
    }

    /**
     * detail: Error 样式 - 红色
     * @author Ttt
     */
    public static class ErrorStyle extends DefaultStyle {

        /**
         * 背景着色颜色
         */
        @Override
        public int getBackgroundTintColor() {
            return Color.parseColor("#D50000");
        }

        /**
         * 是否渲染图标 - getTintIconColor() 着色渲染
         */
        @Override
        public boolean isTintIcon() {
            return true;
        }
    }

    /**
     * detail: Success 样式 - 绿色
     * @author Ttt
     */
    public static class SuccessStyle extends DefaultStyle {

        /**
         * 背景着色颜色
         */
        @Override
        public int getBackgroundTintColor() {
            return Color.parseColor("#388E3C");
        }

        /**
         * 是否渲染图标 - getTintIconColor() 着色渲染
         */
        @Override
        public boolean isTintIcon() {
            return true;
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

    // ==================================
    // = ToastTintUtils.Filter 实现方法 =
    // ==================================

    /**
     * 内部 Toast Filter 实现对象
     */
    private final static ToastTintUtils.Filter sPriToastFilter = new ToastTintUtils.Filter() {

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
