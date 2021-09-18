package dev.widget.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

/**
 * detail: RecyclerView 分割线
 * @author Ttt
 * <pre>
 *     效果:
 *     每个 Item 下面绘制 Line, 最后一条数据不绘制 ( 绘制 ItemCount - 1 条 Line 效果 )
 *     <p></p>
 *     使用:
 *     {@link RecyclerView#addItemDecoration(RecyclerView.ItemDecoration)}
 *     <p></p>
 *     也可以使用内置 {@link DividerItemDecoration}
 *     自定义分割线使用方法
 *     DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
 *     decoration.setDrawable(Drawable)
 *     recyclerView.addItemDecoration(decoration)
 * </pre>
 */
public class LineItemDecoration
        extends RecyclerView.ItemDecoration {

    // 分割线高度
    private final float mLineHeight;
    // 分割线画笔
    private final Paint mLinePaint;
    // 分割线距左边距
    private       float mLineLeft  = 0.0f;
    // 分割线距右边距
    private       float mLineRight = 0.0f;

    public LineItemDecoration(float lineHeight) {
        this(lineHeight, Color.TRANSPARENT);
    }

    public LineItemDecoration(
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
     * 获取分割线距左边距
     * @return 分割线距左边距
     */
    public float getLineLeft() {
        return mLineLeft;
    }

    /**
     * 设置分割线距左边距
     * @param lineLeft 分割线距左边距
     * @return {@link LineItemDecoration}
     */
    public LineItemDecoration setLineLeft(float lineLeft) {
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
     * @return {@link LineItemDecoration}
     */
    public LineItemDecoration setLineRight(float lineRight) {
        this.mLineRight = lineRight;
        return this;
    }

    /**
     * 设置分割线距左、右边距
     * @param lineLeft  分割线距左边距
     * @param lineRight 分割线距右边距
     * @return {@link LineItemDecoration}
     */
    public LineItemDecoration setLineLeftRight(
            float lineLeft,
            float lineRight
    ) {
        this.mLineLeft  = lineLeft;
        this.mLineRight = lineRight;
        return this;
    }

    // ==========
    // = 处理方法 =
    // ==========

    @Override
    public void getItemOffsets(
            @NonNull Rect outRect,
            @NonNull View view,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, (int) mLineHeight, 0, 0);
        }
    }

    @Override
    public void onDrawOver(
            @NonNull Canvas canvas,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        super.onDrawOver(canvas, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (parent.getChildAdapterPosition(child) != 0) {
                canvas.drawRect(
                        child.getLeft() + mLineLeft,
                        child.getTop() - mLineHeight,
                        child.getRight() - mLineRight,
                        child.getTop(),
                        mLinePaint
                );
            }
        }
    }
}