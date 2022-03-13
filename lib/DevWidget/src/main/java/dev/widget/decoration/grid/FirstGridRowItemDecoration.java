package dev.widget.decoration.grid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.widget.decoration.BaseGridItemDecoration;

/**
 * detail: RecyclerView Grid 行分割线处理 ( 第一行数据 )
 * @author Ttt
 * <pre>
 *     效果:
 *     第一行数据顶部绘制分割线
 *     <p></p>
 *     方便基类复用统一继承 {@link BaseGridItemDecoration}
 *     不会使用到 列 ( Column ) 相关的任何字段
 * </pre>
 */
public class FirstGridRowItemDecoration
        extends BaseGridItemDecoration {

    public FirstGridRowItemDecoration(
            final boolean vertical,
            final int spanCount,
            final float rowHeight
    ) {
        this(vertical, spanCount, rowHeight, Color.TRANSPARENT);
    }

    public FirstGridRowItemDecoration(
            final boolean vertical,
            final int spanCount,
            final float rowHeight,
            @ColorInt final int rowColor
    ) {
        super(vertical, spanCount);
        this.mRowHeight = rowHeight;
        this.mRowPaint.setColor(rowColor);
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
        if (isVertical()) {
            verticalItemOffsets(outRect, view, parent, state);
        } else if (isHorizontal()) {
            horizontalItemOffsets(outRect, view, parent, state);
        }
    }

    @Override
    public void onDraw(
            @NonNull Canvas canvas,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        if (isVertical()) {
            verticalDraw(canvas, parent, state);
        } else if (isHorizontal()) {
            horizontalDraw(canvas, parent, state);
        }
    }

    // ============
    // = vertical =
    // ============

    private void verticalItemOffsets(
            final Rect outRect,
            final View view,
            final RecyclerView parent,
            final RecyclerView.State state
    ) {
        int itemCount = state.getItemCount();
        if (itemCount <= mSpanCount) return;

        int index = parent.getChildAdapterPosition(view);
        if (index < mSpanCount) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, (int) mRowHeight, 0, 0);
        }
    }

    private void verticalDraw(
            final Canvas canvas,
            final RecyclerView parent,
            final RecyclerView.State state
    ) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int  index = parent.getChildAdapterPosition(child);
            if (index >= mSpanCount) {
                canvas.drawRect(
                        child.getLeft() + mRowLeft,
                        child.getTop() - mRowHeight - mRowOffset,
                        child.getRight() - mRowRight,
                        child.getTop() - mRowOffset,
                        mRowPaint
                );
            }
        }
    }

    // ==============
    // = horizontal =
    // ==============

    private void horizontalItemOffsets(
            final Rect outRect,
            final View view,
            final RecyclerView parent,
            final RecyclerView.State state
    ) {
        float value     = mRowHeight / mSpanCount;
        int   index     = parent.getChildAdapterPosition(view);
        int   spanIndex = index % mSpanCount;
        if (spanIndex == 0) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, (int) (value * spanIndex), 0, 0);
        }
    }

    private void horizontalDraw(
            final Canvas canvas,
            final RecyclerView parent,
            final RecyclerView.State state
    ) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child     = parent.getChildAt(i);
            int  index     = parent.getChildAdapterPosition(child);
            int  spanIndex = index % mSpanCount;
            if (spanIndex != 0) {
                canvas.drawRect(
                        child.getLeft() + mRowLeft,
                        child.getTop() - mRowHeight - mRowOffset,
                        child.getRight() - mRowRight,
                        child.getTop() - mRowOffset,
                        mRowPaint
                );
            }
        }
    }
}