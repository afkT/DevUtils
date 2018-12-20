package dev.utils.app.toast.style;

import android.graphics.Typeface;
import android.text.TextUtils;

import dev.utils.app.toast.IToastStyle;

/**
 * detail: Toast 默认样式
 * Created by Ttt
 */
public class DefaultToastStyle implements IToastStyle {

    @Override
    public String getAlias() {
        return DefaultToastStyle.class.getSimpleName();
    }

    @Override
    public int getGravity() {
        // mTN.mGravity config_toastDefaultGravity 默认值
        // return Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        return -1;
    }

    @Override
    public int getXOffset() {
        return 0;
    }

    @Override
    public int getYOffset() {
        return 0;
    }

    @Override
    public int getZ() {
        return -1;
    }

    @Override
    public int getCornerRadius() {
        return -1;
    }

    @Override
    public int getBackgroundColor() {
        return -1;
    }

    @Override
    public int getTextColor() {
        return -1;
    }

    @Override
    public float getTextSize() {
        return -1;
    }

    @Override
    public int getMaxLines() {
        return -1;
    }

    @Override
    public TextUtils.TruncateAt getEllipsize() {
        return null;
    }

    @Override
    public Typeface getTypeface() {
        return null;
    }

    @Override
    public int getPaddingLeft() {
        return 0;
    }

    @Override
    public int getPaddingTop() {
        return 0;
    }

    @Override
    public int getPaddingRight() {
        return 0;
    }

    @Override
    public int getPaddingBottom() {
        return 0;
    }
}
