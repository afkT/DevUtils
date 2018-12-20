package dev.utils.app.toast;

import android.graphics.Typeface;
import android.text.TextUtils;

/**
 * detail: Toast 样式配置
 * Created by Ttt
 */
public interface IToastStyle {

    // 别名, 进行标记判断样式
    String getAlias();

    // =

    // Toast 的重心
    int getGravity();

    // X轴偏移
    int getXOffset();

    // Y轴偏移
    int getYOffset();

    // =

    // Toast Z轴坐标阴影
    int getZ();

    // 圆角大小
    int getCornerRadius();

    // 背景颜色
    int getBackgroundColor();

    // 文本颜色
    int getTextColor();

    // 文本大小
    float getTextSize();

    // 最大行数
    int getMaxLines();

    // Ellipsize 效果
    TextUtils.TruncateAt getEllipsize();

    // 字体样式
    Typeface getTypeface();

    // =

    // 左边内边距
    int getPaddingLeft();

    // 顶部内边距
    int getPaddingTop();

    // 右边内边距
    int getPaddingRight();

    // 底部内边距
    int getPaddingBottom();
}