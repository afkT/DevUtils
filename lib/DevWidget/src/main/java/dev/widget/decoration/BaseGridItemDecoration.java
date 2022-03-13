package dev.widget.decoration;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.recyclerview.widget.RecyclerView;

/**
 * detail: 基础 RecyclerView Grid 分割线处理
 * @author Ttt
 * <pre>
 *     纯色值绘制 行、列 分割线
 * </pre>
 */
public class BaseGridItemDecoration
        extends RecyclerView.ItemDecoration {

    // 分割线绘制方向 ( RecyclerView Orientation )
    private   int mOrientation = RecyclerView.VERTICAL;
    // Span 总数 ( Grid 列 )
    protected int mSpanCount;

    // =====
    // = 行 =
    // =====

    // 行边界测算
    protected final Rect  mRowBounds = new Rect();
    // 行分割线画笔
    protected final Paint mRowPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 行分割线高度
    protected       float mRowHeight = 0.0F;
    // 行分割线距左边距
    protected       float mRowLeft   = 0.0F;
    // 行分割线距右边距
    protected       float mRowRight  = 0.0F;
    // 行分割线偏差值 ( 用于解决多个 ItemDecoration 叠加覆盖问题 )
    protected       float mRowOffset = 0.0F;

    // =====
    // = 列 =
    // =====

    // 列边界测算
    protected final Rect  mColumnBounds = new Rect();
    // 列分割线画笔
    protected final Paint mColumnPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 列分割线高度
    protected       float mColumnHeight = 0.0F;
    // 列分割线距左边距
    protected       float mColumnLeft   = 0.0F;
    // 列分割线距右边距
    protected       float mColumnRight  = 0.0F;
    // 列分割线偏差值 ( 用于解决多个 ItemDecoration 叠加覆盖问题 )
    protected       float mColumnOffset = 0.0F;

    // ==========
    // = 构造函数 =
    // ==========

    public BaseGridItemDecoration(
            final boolean vertical,
            final int spanCount
    ) {
        if (vertical) {
            setVertical();
        } else {
            setHorizontal();
        }
        this.mSpanCount = spanCount;
        this.mRowPaint.setColor(Color.TRANSPARENT);
        this.mColumnPaint.setColor(Color.TRANSPARENT);
    }

    // ==========
    // = 对外公开 =
    // ==========

    public int getSpanCount() {
        return mSpanCount;
    }

    public BaseGridItemDecoration setSpanCount(final int spanCount) {
        this.mSpanCount = spanCount;
        return this;
    }

    /**
     * 是否分割线绘制方向为 VERTICAL
     * @return {@code true} yes, {@code false} no
     */
    public boolean isVertical() {
        return this.mOrientation == RecyclerView.VERTICAL;
    }

    /**
     * 设置分割线绘制方向为 VERTICAL
     * @return {@link BaseGridItemDecoration}
     */
    public BaseGridItemDecoration setVertical() {
        this.mOrientation = RecyclerView.VERTICAL;
        return this;
    }

    /**
     * 是否分割线绘制方向为 HORIZONTAL
     * @return {@code true} yes, {@code false} no
     */
    public boolean isHorizontal() {
        return this.mOrientation == RecyclerView.HORIZONTAL;
    }

    /**
     * 设置分割线绘制方向为 HORIZONTAL
     * @return {@link BaseGridItemDecoration}
     */
    public BaseGridItemDecoration setHorizontal() {
        this.mOrientation = RecyclerView.HORIZONTAL;
        return this;
    }

    // =====
    // = 行 =
    // =====

    public Paint getRowPaint() {
        return mRowPaint;
    }

    public float getRowHeight() {
        return mRowHeight;
    }

    public BaseGridItemDecoration setRowHeight(final float rowHeight) {
        this.mRowHeight = rowHeight;
        return this;
    }

    public float getRowLeft() {
        return mRowLeft;
    }

    public BaseGridItemDecoration setRowLeft(final float rowLeft) {
        this.mRowLeft = rowLeft;
        return this;
    }

    public float getRowRight() {
        return mRowRight;
    }

    public BaseGridItemDecoration setRowRight(final float rowRight) {
        this.mRowRight = rowRight;
        return this;
    }

    public BaseGridItemDecoration setRowLeftRight(
            final float rowLeft,
            final float rowRight
    ) {
        this.mRowLeft  = rowLeft;
        this.mRowRight = rowRight;
        return this;
    }

    public float getRowOffset() {
        return mRowOffset;
    }

    public BaseGridItemDecoration setRowOffset(final float rowOffset) {
        this.mRowOffset = rowOffset;
        return this;
    }

    // =====
    // = 列 =
    // =====

    public Paint getColumnPaint() {
        return mColumnPaint;
    }

    public float getColumnHeight() {
        return mColumnHeight;
    }

    public BaseGridItemDecoration setColumnHeight(final float columnHeight) {
        this.mColumnHeight = columnHeight;
        return this;
    }

    public float getColumnLeft() {
        return mColumnLeft;
    }

    public BaseGridItemDecoration setColumnLeft(final float columnLeft) {
        this.mColumnLeft = columnLeft;
        return this;
    }

    public float getColumnRight() {
        return mColumnRight;
    }

    public BaseGridItemDecoration setColumnRight(final float columnRight) {
        this.mColumnRight = columnRight;
        return this;
    }

    public BaseGridItemDecoration setColumnLeftRight(
            final float columnLeft,
            final float columnRight
    ) {
        this.mColumnLeft  = columnLeft;
        this.mColumnRight = columnRight;
        return this;
    }

    public float getColumnOffset() {
        return mColumnOffset;
    }

    public BaseGridItemDecoration setColumnOffset(final float columnOffset) {
        this.mColumnOffset = columnOffset;
        return this;
    }
}