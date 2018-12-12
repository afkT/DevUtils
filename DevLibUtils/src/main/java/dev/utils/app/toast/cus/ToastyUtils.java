package dev.utils.app.toast.cus;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;

import dev.utils.LogPrintUtils;
import dev.utils.R;

/**
 * detail: Toasty 快捷操作工具类
 * Created by Ttt
 * https://github.com/GrenderG/Toasty
 */
public final class ToastyUtils {

    private ToastyUtils() {
    }

    // 日志Tag
    private static final String TAG = ToastyUtils.class.getSimpleName();

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
}
