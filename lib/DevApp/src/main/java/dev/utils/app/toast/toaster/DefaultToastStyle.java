package dev.utils.app.toast.toaster;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import androidx.annotation.ColorInt;

/**
 * detail: Toast 默认样式
 * @author Ttt
 */
public class DefaultToastStyle
        implements IToast.Style {

    /**
     * 获取 Toast 的重心
     * @return Toast 的重心
     */
    @Override
    public int getGravity() {
        return 0;
    }

    /**
     * 获取 X 轴偏移
     * @return X 轴偏移
     */
    @Override
    public int getXOffset() {
        return 0;
    }

    /**
     * 获取 Y 轴偏移
     * @return Y 轴偏移
     */
    @Override
    public int getYOffset() {
        return 0;
    }

    /**
     * 获取水平边距
     * @return 水平边距
     */
    @Override
    public int getHorizontalMargin() {
        return 0;
    }

    /**
     * 获取垂直边距
     * @return 垂直边距
     */
    @Override
    public int getVerticalMargin() {
        return 0;
    }

    /**
     * 获取 Toast Z 轴坐标阴影
     * @return Toast Z 轴坐标阴影
     */
    @Override
    public int getZ() {
        return 0;
    }

    /**
     * 获取圆角大小
     * @return 圆角大小
     */
    @Override
    public float getCornerRadius() {
        return 5f;
    }

    /**
     * 获取背景着色颜色
     * @return 背景着色颜色
     */
    @ColorInt
    @Override
    public int getBackgroundTintColor() {
        return 0xB2000000;
    }

    /**
     * 获取背景图片
     * @return 背景图片
     */
    @Override
    public Drawable getBackground() {
        return null;
    }

    // ================
    // = TextView 相关 =
    // ================

    /**
     * 获取文本颜色
     * @return 文本颜色
     */
    @ColorInt
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
     * 获取 TextView padding 边距 ( new int[] { left, top, right, bottom } )
     * @return TextView padding 边距
     */
    @Override
    public int[] getPadding() {
        return new int[]{25, 10, 25, 10};
    }
}