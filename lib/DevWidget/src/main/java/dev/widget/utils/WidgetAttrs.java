package dev.widget.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import dev.utils.app.WidgetUtils;
import dev.widget.R;

/**
 * detail: DevWidget 属性封装处理类
 * @author Ttt
 */
public class WidgetAttrs {

    // 是否允许滑动
    private boolean mIsSlide   = true;
    // 最大显示宽度
    private int     mMaxWidth  = WidgetUtils.DEF_VALUE;
    // 最大显示高度
    private int     mMaxHeight = WidgetUtils.DEF_VALUE;

    /**
     * 初始化
     * @param context {@link Context}
     * @param attrs   {@link AttributeSet}
     */
    public WidgetAttrs(
            Context context,
            AttributeSet attrs
    ) {
        if (context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DevWidget);
            mIsSlide = a.getBoolean(R.styleable.DevWidget_dev_slide, true);
            mMaxWidth = a.getLayoutDimension(R.styleable.DevWidget_dev_maxWidth, WidgetUtils.DEF_VALUE);
            mMaxHeight = a.getLayoutDimension(R.styleable.DevWidget_dev_maxHeight, WidgetUtils.DEF_VALUE);
            a.recycle();
        }
    }

    /**
     * 获取 View 最大显示宽度
     * @return View 最大显示宽度
     */
    public int getMaxWidth() {
        return mMaxWidth;
    }

    /**
     * 设置 View 最大显示宽度
     * @param maxWidth View 最大显示宽度
     * @return {@link WidgetAttrs}
     */
    public WidgetAttrs setMaxWidth(int maxWidth) {
        this.mMaxWidth = maxWidth;
        return this;
    }

    /**
     * 获取 View 最大显示高度
     * @return View 最大显示高度
     */
    public int getMaxHeight() {
        return mMaxHeight;
    }

    /**
     * 设置 View 最大显示高度
     * @param maxHeight View 最大显示高度
     * @return {@link WidgetAttrs}
     */
    public WidgetAttrs setMaxHeight(int maxHeight) {
        this.mMaxHeight = maxHeight;
        return this;
    }

    /**
     * 是否允许滑动
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSlide() {
        return mIsSlide;
    }

    /**
     * 设置是否允许滑动
     * @param isSlide {@code true} yes, {@code false} no
     * @return {@link WidgetAttrs}
     */
    public WidgetAttrs setSlide(boolean isSlide) {
        this.mIsSlide = isSlide;
        return this;
    }

    /**
     * 切换滑动控制状态
     * @return {@link WidgetAttrs}
     */
    public WidgetAttrs toggleSlide() {
        this.mIsSlide = !this.mIsSlide;
        return this;
    }
}