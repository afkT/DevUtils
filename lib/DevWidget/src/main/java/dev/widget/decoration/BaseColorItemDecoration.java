package dev.widget.decoration;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

/**
 * detail: RecyclerView 分割线绘制基类
 * @author Ttt
 * <pre>
 *     纯色值绘制分割线
 * </pre>
 */
public class BaseColorItemDecoration
        extends RecyclerView.ItemDecoration {

    // 单条数据是否绘制分割线
    protected boolean mSingleDraw = true;

    // ==========
    // = 通用属性 =
    // ==========

    // 分割线绘制方向 ( RecyclerView Orientation )
    private         int   mOrientation = RecyclerView.VERTICAL;
    // 分割线边界测算
    protected final Rect  mBounds      = new Rect();
    // 分割线画笔
    protected final Paint mPaint       = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 分割线高度 ( Horizontal 为宽度 )
    protected       float mHeight;
    // 分割线距左边距 ( Horizontal 为上边距 )
    protected       float mLeft        = 0.0F;
    // 分割线距右边距 ( Horizontal 为下边距 )
    protected       float mRight       = 0.0F;
    // 分割线偏差值 ( 用于解决多个 ItemDecoration 叠加覆盖问题 )
    protected       float mOffset      = 0.0F;

    // ==========
    // = 构造函数 =
    // ==========

    public BaseColorItemDecoration(
            final boolean vertical,
            final float height
    ) {
        this(vertical, height, Color.TRANSPARENT);
    }

    public BaseColorItemDecoration(
            final boolean vertical,
            final float height,
            @ColorInt final int color
    ) {
        if (vertical) {
            setVertical();
        } else {
            setHorizontal();
        }
        this.mHeight = height;
        this.mPaint.setColor(color);
    }

    // ==========
    // = 对外公开 =
    // ==========

    /**
     * 获取单条数据是否绘制分割线
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSingleDraw() {
        return mSingleDraw;
    }

    /**
     * 设置单条数据是否绘制分割线
     * @param singleDraw {@code true} yes, {@code false} no
     * @return {@link BaseColorItemDecoration}
     */
    public BaseColorItemDecoration setSingleDraw(final boolean singleDraw) {
        this.mSingleDraw = singleDraw;
        return this;
    }

    // =

    /**
     * 判断分割线绘制方向是否为 VERTICAL
     * @return {@code true} yes, {@code false} no
     */
    public boolean isVertical() {
        return this.mOrientation == RecyclerView.VERTICAL;
    }

    /**
     * 判断分割线绘制方向是否为 HORIZONTAL
     * @return {@code true} yes, {@code false} no
     */
    public boolean isHorizontal() {
        return this.mOrientation == RecyclerView.HORIZONTAL;
    }

    /**
     * 设置分割线绘制方向为 VERTICAL
     * @return {@link BaseColorItemDecoration}
     */
    public BaseColorItemDecoration setVertical() {
        this.mOrientation = RecyclerView.VERTICAL;
        return this;
    }

    /**
     * 设置分割线绘制方向为 HORIZONTAL
     * @return {@link BaseColorItemDecoration}
     */
    public BaseColorItemDecoration setHorizontal() {
        this.mOrientation = RecyclerView.HORIZONTAL;
        return this;
    }

    // =

    /**
     * 获取分割线画笔
     * @return 分割线画笔
     */
    public Paint getPaint() {
        return mPaint;
    }

    /**
     * 获取分割线高度
     * @return 分割线高度
     */
    public float getHeight() {
        return mHeight;
    }

    /**
     * 设置分割线高度
     * @param height 分割线高度
     * @return {@link BaseColorItemDecoration}
     */
    public BaseColorItemDecoration setHeight(final float height) {
        this.mHeight = height;
        return this;
    }

    /**
     * 获取分割线距左边距
     * @return 分割线距左边距
     */
    public float getLeft() {
        return mLeft;
    }

    /**
     * 设置分割线距左边距
     * @param left 分割线距左边距
     * @return {@link BaseColorItemDecoration}
     */
    public BaseColorItemDecoration setLeft(final float left) {
        this.mLeft = left;
        return this;
    }

    /**
     * 获取分割线距右边距
     * @return 分割线距右边距
     */
    public float getRight() {
        return mRight;
    }

    /**
     * 设置分割线距右边距
     * @param right 分割线距右边距
     * @return {@link BaseColorItemDecoration}
     */
    public BaseColorItemDecoration setRight(final float right) {
        this.mRight = right;
        return this;
    }

    /**
     * 设置分割线距左、右边距
     * @param left  分割线距左边距
     * @param right 分割线距右边距
     * @return {@link BaseColorItemDecoration}
     */
    public BaseColorItemDecoration setLeftRight(
            final float left,
            final float right
    ) {
        this.mLeft  = left;
        this.mRight = right;
        return this;
    }

    /**
     * 获取分割线偏差值
     * @return 分割线偏差值
     */
    public float getOffset() {
        return mOffset;
    }

    /**
     * 设置分割线偏差值
     * @param offset 分割线偏差值
     * @return {@link BaseColorItemDecoration}
     */
    public BaseColorItemDecoration setOffset(final float offset) {
        this.mOffset = offset;
        return this;
    }
}