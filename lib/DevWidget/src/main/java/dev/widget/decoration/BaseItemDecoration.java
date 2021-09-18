package dev.widget.decoration;

import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

/**
 * detail: 基础 RecyclerView 分割线处理
 * @author Ttt
 */
public class BaseItemDecoration
        extends RecyclerView.ItemDecoration {

    // 分割线高度
    protected final float   mLineHeight;
    // 分割线画笔
    protected final Paint   mLinePaint;
    // 单条数据是否绘制分割线
    protected       boolean mSingleLineDraw = false;
    // 分割线距左边距
    protected       float   mLineLeft       = 0.0f;
    // 分割线距右边距
    protected       float   mLineRight      = 0.0f;

    public BaseItemDecoration(float lineHeight) {
        this(lineHeight, Color.TRANSPARENT);
    }

    public BaseItemDecoration(
            float lineHeight,
            @ColorInt int lineColor
    ) {
        this.mLineHeight = lineHeight;
        this.mLinePaint  = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mLinePaint.setColor(lineColor);
    }

    // ==========
    // = 对外公开 =
    // ==========

    /**
     * 获取分割线画笔
     * @return 分割线画笔
     */
    public Paint getLinePaint() {
        return mLinePaint;
    }

    /**
     * 获取单条数据是否绘制分割线
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSingleLineDraw() {
        return mSingleLineDraw;
    }

    /**
     * 设置单条数据是否绘制分割线
     * @param singleLineDraw {@code true} yes, {@code false} no
     * @return {@link BaseItemDecoration}
     */
    public BaseItemDecoration setSingleLineDraw(boolean singleLineDraw) {
        this.mSingleLineDraw = singleLineDraw;
        return this;
    }

    /**
     * 获取分割线距左边距
     * @return 分割线距左边距
     */
    public float getLineLeft() {
        return mLineLeft;
    }

    /**
     * 设置分割线距左边距
     * @param lineLeft 分割线距左边距
     * @return {@link BaseItemDecoration}
     */
    public BaseItemDecoration setLineLeft(float lineLeft) {
        this.mLineLeft = lineLeft;
        return this;
    }

    /**
     * 获取分割线距右边距
     * @return 分割线距右边距
     */
    public float getLineRight() {
        return mLineRight;
    }

    /**
     * 设置分割线距右边距
     * @param lineRight 分割线距右边距
     * @return {@link BaseItemDecoration}
     */
    public BaseItemDecoration setLineRight(float lineRight) {
        this.mLineRight = lineRight;
        return this;
    }

    /**
     * 设置分割线距左、右边距
     * @param lineLeft  分割线距左边距
     * @param lineRight 分割线距右边距
     * @return {@link BaseItemDecoration}
     */
    public BaseItemDecoration setLineLeftRight(
            float lineLeft,
            float lineRight
    ) {
        this.mLineLeft  = lineLeft;
        this.mLineRight = lineRight;
        return this;
    }
}