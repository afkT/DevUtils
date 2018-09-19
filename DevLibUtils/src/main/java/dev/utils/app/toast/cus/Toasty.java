package dev.utils.app.toast.cus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import dev.utils.LogPrintUtils;
import dev.utils.R;

/**
 * detail: Toast 工具类(美化后,使用Layout显示)
 * Created by Ttt 
 * https://github.com/GrenderG/Toasty
 */
@SuppressLint("InflateParams")
public final class Toasty {

    private Toasty() {
    }

    // 日志TAG
    private static final String TAG = Toasty.class.getSimpleName();

    /** 内部持有唯一 */
    private static Toast mToast;

    // ===================
    // == Toast配置信息 ==
    // ===================

    // === Toast 使用的配置信息 ===
    @ColorInt
    static int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF"); // 白色
    @ColorInt
    static int ERROR_COLOR = Color.parseColor("#D50000"); // 红色
    @ColorInt
    static int INFO_COLOR = Color.parseColor("#3F51B5"); // 海洋蓝
    @ColorInt
    static int SUCCESS_COLOR = Color.parseColor("#388E3C"); // 绿色
    @ColorInt
    static int WARNING_COLOR = Color.parseColor("#FFA900"); // 橙色
    @ColorInt
    static int NORMAL_COLOR = Color.parseColor("#353A3E"); // 灰色
    // 默认字体
    static final Typeface LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
    // 当前字体样式
    static Typeface currentTypeface = LOADED_TOAST_TYPEFACE;
    // 字体大小
    static int textSize = 16; // in SP
    // 是否渲染图标
    static boolean tintIcon = true;
    // 是否使用新的Toast
    static boolean isNewToast = true;

    /**
     * detail: Toast 配置
     * Created by Ttt
     */
    public static class Config {

        @ColorInt
        int DEFAULT_TEXT_COLOR = Toasty.DEFAULT_TEXT_COLOR;
        @ColorInt
        int ERROR_COLOR = Toasty.ERROR_COLOR;
        @ColorInt
        int INFO_COLOR = Toasty.INFO_COLOR;
        @ColorInt
        int SUCCESS_COLOR = Toasty.SUCCESS_COLOR;
        @ColorInt
        int WARNING_COLOR = Toasty.WARNING_COLOR;
        // 当前字体样式
        Typeface typeface = Toasty.currentTypeface;
        // 字体大小
        int textSize = Toasty.textSize;
        // 是否渲染图标
        boolean tintIcon = Toasty.tintIcon;
        // 是否使用新的Toast
        boolean isNewToast = Toasty.isNewToast;

        // 私有构造函数
        Config() {
        }

        @CheckResult
        public static Config getInstance() {
            return new Config();
        }

        // ==

        @CheckResult
        public Config setTextColor(@ColorInt int textColor) {
            DEFAULT_TEXT_COLOR = textColor;
            return this;
        }

        @CheckResult
        public Config setErrorColor(@ColorInt int errorColor) {
            ERROR_COLOR = errorColor;
            return this;
        }

        @CheckResult
        public Config setInfoColor(@ColorInt int infoColor) {
            INFO_COLOR = infoColor;
            return this;
        }

        @CheckResult
        public Config setSuccessColor(@ColorInt int successColor) {
            SUCCESS_COLOR = successColor;
            return this;
        }

        @CheckResult
        public Config setWarningColor(@ColorInt int warningColor) {
            WARNING_COLOR = warningColor;
            return this;
        }

        @CheckResult
        public Config setToastTypeface(@NonNull Typeface typeface) {
            this.typeface = typeface;
            return this;
        }

        @CheckResult
        public Config setTextSize(int sizeInSp) {
            this.textSize = sizeInSp;
            return this;
        }

        @CheckResult
        public Config setTintIcon(boolean tintIcon) {
            this.tintIcon = tintIcon;
            return this;
        }

        @CheckResult
        public Config setNewToast(boolean isNewToast) {
            this.isNewToast = isNewToast;
            return this;
        }

        public int getTextColor() {
            return DEFAULT_TEXT_COLOR;
        }

        public int getNormalColor() {
            return NORMAL_COLOR;
        }

        public int getErrorColor() {
            return ERROR_COLOR;
        }

        public int getInfoColor() {
            return INFO_COLOR;
        }

        public int getSuccessColor() {
            return SUCCESS_COLOR;
        }

        public int getWarningColor() {
            return WARNING_COLOR;
        }

        public Typeface getTypeface() {
            return typeface;
        }

        public int getTextSize() {
            return textSize;
        }

        public boolean isTintIcon() {
            return tintIcon;
        }

        public boolean isNewToast() {
            return isNewToast;
        }

        // =

        /** 应用配置参数生效 */
        public void apply() {
            Toasty.DEFAULT_TEXT_COLOR = DEFAULT_TEXT_COLOR;
            Toasty.ERROR_COLOR = ERROR_COLOR;
            Toasty.INFO_COLOR = INFO_COLOR;
            Toasty.SUCCESS_COLOR = SUCCESS_COLOR;
            Toasty.WARNING_COLOR = WARNING_COLOR;
            Toasty.currentTypeface = typeface;
            Toasty.textSize = textSize;
            Toasty.tintIcon = tintIcon;
            Toasty.isNewToast = isNewToast;
        }

        /** 重置默认参数 */
        public static void reset() {
            Toasty.DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");
            Toasty.ERROR_COLOR = Color.parseColor("#D50000");
            Toasty.INFO_COLOR = Color.parseColor("#3F51B5");
            Toasty.SUCCESS_COLOR = Color.parseColor("#388E3C");
            Toasty.WARNING_COLOR = Color.parseColor("#FFA900");
            Toasty.currentTypeface = LOADED_TOAST_TYPEFACE;
            Toasty.textSize = 16;
            Toasty.tintIcon = true;
            Toasty.isNewToast = false;
        }
    }

    // ===================
    // == Toast显示操作 ==
    // ===================

    // === normal ===

    public static void normal(@NonNull Context context, @NonNull CharSequence message) {
        normal(context, message, Toast.LENGTH_SHORT, null);
    }

    public static void normal(@NonNull Context context, @NonNull CharSequence message, Drawable icon) {
        normal(context, message, Toast.LENGTH_SHORT, icon);
    }

    public static void normal(@NonNull Context context, @NonNull CharSequence message, int duration) {
        normal(context, message, duration, null);
    }

    public static void normal(@NonNull Context context, @NonNull CharSequence message, int duration, Drawable icon) {
        custom(context, message, icon, NORMAL_COLOR, duration, true);
    }

    // === warning ===

    public static void warning(@NonNull Context context, @NonNull CharSequence message) {
        warning(context, message, Toast.LENGTH_SHORT, true);
    }

    public static void warning(@NonNull Context context, @NonNull CharSequence message, int duration) {
        warning(context, message, duration, true);
    }

    public static void warning(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        custom(context, message, ToastyUtils.getDrawable(context, R.drawable.dev_toast_ic_error_outline_white), WARNING_COLOR, duration, withIcon);
    }

    // === info ===

    public static void info(@NonNull Context context, @NonNull CharSequence message) {
        info(context, message, Toast.LENGTH_SHORT, true);
    }

    public static void info(@NonNull Context context, @NonNull CharSequence message, int duration) {
        info(context, message, duration, true);
    }

    public static void info(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        custom(context, message, ToastyUtils.getDrawable(context, R.drawable.dev_toast_ic_info_outline_white), INFO_COLOR, duration, withIcon);
    }

    // === success ===

    public static void success(@NonNull Context context, @NonNull CharSequence message) {
        success(context, message, Toast.LENGTH_SHORT, true);
    }

    public static void success(@NonNull Context context, @NonNull CharSequence message, int duration) {
        success(context, message, duration, true);
    }

    public static void success(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        custom(context, message, ToastyUtils.getDrawable(context, R.drawable.dev_toast_ic_check_white), SUCCESS_COLOR, duration, withIcon);
    }

    // === error ===

    public static void error(@NonNull Context context, @NonNull CharSequence message) {
        error(context, message, Toast.LENGTH_SHORT, true);
    }

    public static void error(@NonNull Context context, @NonNull CharSequence message, int duration) {
        error(context, message, duration, true);
    }

    public static void error(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        custom(context, message, ToastyUtils.getDrawable(context, R.drawable.dev_toast_ic_clear_white), ERROR_COLOR, duration, withIcon);
    }

    // === custom ===

    public static void custom(@NonNull Context context, @NonNull CharSequence message) {
        custom(context, message, null, -1, Toast.LENGTH_SHORT, false);
    }

    public static void custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon) {
        custom(context, message, icon, -1, Toast.LENGTH_SHORT, true);
    }

    public static void custom(@NonNull Context context, @NonNull CharSequence message, @DrawableRes int iconRes, @ColorInt int tintColor) {
        custom(context, message, ToastyUtils.getDrawable(context, iconRes), tintColor, Toast.LENGTH_SHORT, true);
    }
    /**
     * 通用自定义显示Toast
     * @param context
     * @param message 显示的内容
     * @param icon 图标
     * @param tintColor 背景颜色渲染
     * @param duration 显示时间
     * @param withIcon 是否显示图标
     */
    public static void custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon, @ColorInt int tintColor, int duration, boolean withIcon) {
        custom(context, message, icon, tintColor, duration, withIcon, tintIcon);
    }

    /**
     * 通用自定义显示Toast
     * @param context
     * @param message 显示的内容
     * @param icon 图标
     * @param tintColor 背景颜色渲染
     * @param duration 显示时间
     * @param withIcon 是否显示图标
     * @param tintIcon 是否处理图标
     */
    public static void custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon, @ColorInt int tintColor, int duration, boolean withIcon, boolean tintIcon) {
        // 初始化View
        View view = inflaterView(context, message, icon, tintColor, withIcon, tintIcon);
        // 显示Toast
        showToasty(context, view, duration, isNewToast);
    }

    /**
     * 实例化 Layout View
     * @param context
     * @param message
     * @param icon
     * @param tintColor
     * @param withIcon
     * @param tintIcon
     * @return
     */
    static View inflaterView(@NonNull Context context, @NonNull CharSequence message, Drawable icon, @ColorInt int tintColor, boolean withIcon, boolean tintIcon){
        if (context != null){
            try {
                // 引入View
                final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dev_toast_layout, null);
                // 初始化View
                final ImageView toastIcon = (ImageView) toastLayout.findViewById(R.id.vid_dtl_toast_igview);
                final TextView toastTextView = (TextView) toastLayout.findViewById(R.id.vid_dtl_toast_tv);
                // 背景图片
                Drawable drawableFrame;
                // 判断是否渲染背景
                if (tintColor != -1) { // 根据背景色进行渲染透明图片
                    drawableFrame = ToastyUtils.tint9PatchDrawableFrame(context, tintColor);
                } else { // 获取背景透明图片
                    drawableFrame = ToastyUtils.getDrawable(context, R.drawable.dev_toast_frame);
                }
                // 进行设置背景
                ToastyUtils.setBackground(toastLayout, drawableFrame);
                // 判断是否显示图标
                if (withIcon && icon != null) {
                    // 是否渲染图标
                    if (tintIcon) {
                        // 进行渲染图标
                        icon = ToastyUtils.tintIcon(icon, DEFAULT_TEXT_COLOR);
                    }
                    // 设置图标背景图
                    ToastyUtils.setBackground(toastIcon, icon);
                } else {
                    // 隐藏图标
                    toastIcon.setVisibility(View.GONE);
                }
                // 设置字体颜色
                toastTextView.setTextColor(DEFAULT_TEXT_COLOR);
                // 设置消息内容
                toastTextView.setText(message);
                // 设置字体
                toastTextView.setTypeface(currentTypeface);
                // 设置字体大小
                toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                // 返回View
                return toastLayout;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "inflaterView");
            }
        }
        // 默认返回null
        return null;
    }

    /**
     * 最终显示Toast方法
     * @param context
     * @param view
     * @param duration
     * @param isNewToast
     */
    public static void showToasty(Context context, View view, int duration, boolean isNewToast) {
        // 防止 Context 为null
        if (context == null){
            return;
        } else if (view == null) { // 防止显示的View 为null
            return;
        }
        try {
            // 判断是否显示新的 Toast
            if (isNewToast){
                // 生成新的Toast
                Toast toast = new Toast(context);
                // 设置显示的View
                toast.setView(view);
                // 设置显示的时间
                toast.setDuration(duration);
                // 显示Toast
                toast.show();
            } else {
                if (mToast == null){
                    // 生成新的Toast
                    mToast = new Toast(context);
                }
                // 设置显示的View
                mToast.setView(view);
                // 设置显示的时间
                mToast.setDuration(duration);
                // 显示Toast
                mToast.show();
            }
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "showToasty");
        }
    }
}
