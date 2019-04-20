package dev.utils.app.toast.toaster;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * detail: Toast 默认样式
 * @author Ttt
 */
public class DefaultToastStyle implements IToast.Style {

    /**
     * Toast 的重心
     * @return
     */
    @Override
    public int getGravity() {
        return 0;
    }

    /**
     * X轴偏移
     * @return
     */
    @Override
    public int getXOffset() {
        return 0;
    }

    /**
     * Y轴偏移
     * @return
     */
    @Override
    public int getYOffset() {
        return 0;
    }

    /**
     * 获取水平边距
     * @return
     */
    @Override
    public int getHorizontalMargin() {
        return 0;
    }

    /**
     * 获取垂直边距
     * @return
     */
    @Override
    public int getVerticalMargin() {
        return 0;
    }

    /**
     * Toast Z轴坐标阴影
     * @return
     */
    @Override
    public int getZ() {
        return 0;
    }

    /**
     * 圆角大小
     * @return
     */
    @Override
    public float getCornerRadius() {
        return 5f;
    }

    /**
     * 背景着色颜色
     * @return
     */
    @Override
    public int getBackgroundTintColor() {
        return 0xB2000000;
    }

    /**
     * 背景图片
     * @return
     */
    @Override
    public Drawable getBackground() {
        return null;
    }

    // =================
    // = TextView 相关 =
    // =================

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
     * TextView padding 边距 - new int[] { left, top, right, bottom }
     * @return
     */
    @Override
    public int[] getPadding() {
        return new int[]{25, 10, 25, 10};
    }
}
