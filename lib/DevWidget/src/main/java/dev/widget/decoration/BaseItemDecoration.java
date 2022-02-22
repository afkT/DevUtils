package dev.widget.decoration;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

/**
 * detail: 基础 RecyclerView 分割线处理
 * @author Ttt
 */
public class BaseItemDecoration
        extends RecyclerView.ItemDecoration {

    // 边界测算
    protected final Rect    mBounds         = new Rect();
    // 分割线高度 ( 横向为宽度 )
    protected final float   mLineHeight;
    // 分割线画笔
    protected final Paint   mLinePaint;
    // 单条数据是否绘制分割线
    protected       boolean mSingleLineDraw = true;
    // 分割线距左边距 ( 横向为上边距 )
    protected       float   mLineLeft       = 0.0F;
    // 分割线距右边距 ( 横向为下边距 )
    protected       float   mLineRight      = 0.0F;
    // 偏差值 ( 用于解决多个 ItemDecoration 叠加覆盖问题 )
    protected       float   mOffset         = 0.0F;

    public BaseItemDecoration(final float lineHeight) {
        this(lineHeight, Color.TRANSPARENT);
    }

    public BaseItemDecoration(
            final float lineHeight,
            @ColorInt final int lineColor
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
     * 获取分割线高度
     * @return 分割线高度
     */
    public float getLineHeight() {
        return mLineHeight;
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
    public BaseItemDecoration setSingleLineDraw(final boolean singleLineDraw) {
        this.mSingleLineDraw = singleLineDraw;
        return this;
    }

    /**
     * 获取分割线距左边距 ( 横向为上边距 )
     * @return 分割线距左边距 ( 横向为上边距 )
     */
    public float getLineLeft() {
        return mLineLeft;
    }

    /**
     * 设置分割线距左边距 ( 横向为上边距 )
     * @param lineLeft 分割线距左边距 ( 横向为上边距 )
     * @return {@link BaseItemDecoration}
     */
    public BaseItemDecoration setLineLeft(final float lineLeft) {
        this.mLineLeft = lineLeft;
        return this;
    }

    /**
     * 获取分割线距右边距 ( 横向为下边距 )
     * @return 分割线距右边距 ( 横向为下边距 )
     */
    public float getLineRight() {
        return mLineRight;
    }

    /**
     * 设置分割线距右边距 ( 横向为下边距 )
     * @param lineRight 分割线距右边距 ( 横向为下边距 )
     * @return {@link BaseItemDecoration}
     */
    public BaseItemDecoration setLineRight(final float lineRight) {
        this.mLineRight = lineRight;
        return this;
    }

    /**
     * 设置分割线距左、右边距
     * @param lineLeft  分割线距左边距 ( 横向为上边距 )
     * @param lineRight 分割线距右边距 ( 横向为下边距 )
     * @return {@link BaseItemDecoration}
     */
    public BaseItemDecoration setLineLeftRight(
            final float lineLeft,
            final float lineRight
    ) {
        this.mLineLeft  = lineLeft;
        this.mLineRight = lineRight;
        return this;
    }

    /**
     * 获取 Item 偏差值
     * @return Item 偏差值
     */
    public float getOffset() {
        return mOffset;
    }

    /**
     * 设置 Item 偏差值
     * @param offset 偏差值
     * @return {@link BaseItemDecoration}
     */
    public BaseItemDecoration setOffset(float offset) {
        this.mOffset = offset;
        return this;
    }
}