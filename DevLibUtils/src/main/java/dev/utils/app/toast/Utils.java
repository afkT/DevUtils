package dev.utils.app.toast;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.R;

/**
 * detail: Toast 内部工具类
 * Created by Ttt
 */
final class Utils {

    private Utils(){
    }

    // 日志 TAG
    private static final String TAG = Utils.class.getSimpleName();
    // NULL 字符串常量
    public static final String NULL = "null";

    /**
     * 获取系统 Toast View TextView
     * @param view
     * @return
     */
    public static TextView getSystemToastTextView(View view){
        return getSystemToastTextView(view, android.R.id.message);
    }

    /**
     * 获取系统 Toast View TextView
     * @param view
     * @param id
     * @return
     */
    public static TextView getSystemToastTextView(View view, int id){
        if (view != null){
            try {
                return view.findViewById(id);
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "getFormatString");
            }
        }
        return null;
    }

    /**
     * 获取格式化字符串
     * @param format
     * @param args
     * @return
     */
    public static String getFormatString(String format, Object... args) {
        try {
            if (args != null && args.length != 0){
                return String.format(format, args);
            } else {
                return format;
            }
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "getFormatString");
        }
        return null;
    }

    /**
     * 获取 R.string 资源的格式化字符串
     * @param resId
     * @param objs
     */
    public static String getFormatRes(int resId, Object... objs) {
        try {
            // 获取字符串并且进行格式化
            if (objs != null && objs.length != 0) {
                return DevUtils.getContext().getString(resId, objs);
            } else {
                return DevUtils.getContext().getString(resId);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getFormatRes");
        }
        return null;
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return
     */
    public static boolean isSpace(final String str) {
        if (str == null) return true;
        for (int i = 0, len = str.length(); i < len; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // =

    /**
     * 设置背景
     * @param view
     * @param drawable
     */
    public static void setBackground(@NonNull View view, Drawable drawable) {
        if (view != null){
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
    public static Drawable getDrawable(Context context, @DrawableRes int id) {
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
    public static Drawable tintIcon(@NonNull Drawable drawable, @ColorInt int tintColor) {
        if (drawable != null){
            try {
                drawable.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
            } catch (Exception e){
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
    public static Drawable tint9PatchDrawableFrame(Context context, @ColorInt int tintColor) {
        try {
            final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(context, R.drawable.dev_toast_frame);
            return tintIcon(toastDrawable, tintColor);
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "tint9PatchDrawableFrame");
        }
        return null;
    }
}
