package dev.widget.decoration;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

/**
 * detail: 基础 RecyclerView Linear 分割线处理
 * @author Ttt
 * <pre>
 *     纯色值绘制分割线
 * </pre>
 */
public class BaseLinearItemDecoration
        extends RecyclerView.ItemDecoration {

    // 分割线边界测算
    protected final Rect    mLineBounds     = new Rect();
    // 分割线画笔
    protected final Paint   mLinePaint      = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 分割线高度 ( 横向为宽度 )
    protected final float   mLineHeight;
    // 分割线距左边距 ( 横向为上边距 )
    protected       float   mLineLeft       = 0.0F;
    // 分割线距右边距 ( 横向为下边距 )
    protected       float   mLineRight      = 0.0F;
    // 分割线偏差值 ( 用于解决多个 ItemDecoration 叠加覆盖问题 )
    protected       float   mLineOffset     = 0.0F;
    // 单条数据是否绘制分割线
    protected       boolean mSingleLineDraw = true;

    // ==========
    // = 构造函数 =
    // ==========

    public BaseLinearItemDecoration(final float lineHeight) {
        this(lineHeight, Color.TRANSPARENT);
    }

    public BaseLinearItemDecoration(
            final float lineHeight,
            @ColorInt final int lineColor
    ) {
        this.mLineHeight = lineHeight;
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
     * 获取分割线距左边距
     * @return 分割线距左边距
     */
    public float getLineLeft() {
        return mLineLeft;
    }

    /**
     * 设置分割线距左边距
     * @param lineLeft 分割线距左边距
     * @return {@link BaseLinearItemDecoration}
     */
    public BaseLinearItemDecoration setLineLeft(final float lineLeft) {
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
     * @return {@link BaseLinearItemDecoration}
     */
    public BaseLinearItemDecoration setLineRight(final float lineRight) {
        this.mLineRight = lineRight;
        return this;
    }

    /**
     * 设置分割线距左、右边距
     * @param lineLeft  分割线距左边距
     * @param lineRight 分割线距右边距
     * @return {@link BaseLinearItemDecoration}
     */
    public BaseLinearItemDecoration setLineLeftRight(
            final float lineLeft,
            final float lineRight
    ) {
        this.mLineLeft  = lineLeft;
        this.mLineRight = lineRight;
        return this;
    }

    /**
     * 获取 Item 分割线偏差值
     * @return Item 分割线偏差值
     */
    public float getLineOffset() {
        return mLineOffset;
    }

    /**
     * 设置 Item 分割线偏差值
     * @param lineOffset 分割线偏差值
     * @return {@link BaseLinearItemDecoration}
     */
    public BaseLinearItemDecoration setLineOffset(float lineOffset) {
        this.mLineOffset = lineOffset;
        return this;
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
     * @return {@link BaseLinearItemDecoration}
     */
    public BaseLinearItemDecoration setSingleLineDraw(final boolean singleLineDraw) {
        this.mSingleLineDraw = singleLineDraw;
        return this;
    }
}