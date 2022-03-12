package dev.widget.decoration.grid.horizontal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.widget.decoration.BaseGridItemDecoration;
import dev.widget.decoration.grid.GridRowItemDecoration;

/**
 * detail: RecyclerView Grid 行分割线处理
 * @author Ttt
 * <pre>
 *     {@link GridRowItemDecoration} 横向滑动版实现
 * </pre>
 */
public class GridRowHorizontalItemDecoration
        extends BaseGridItemDecoration {

    public GridRowHorizontalItemDecoration(
            final int spanCount,
            final float rowHeight
    ) {
        this(spanCount, rowHeight, Color.TRANSPARENT);
    }

    public GridRowHorizontalItemDecoration(
            final int spanCount,
            final float rowHeight,
            @ColorInt final int rowColor
    ) {
        super(spanCount);
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
        float value     = mRowHeight / mSpanCount;
        int   index     = parent.getChildAdapterPosition(view);
        int   spanIndex = index % mSpanCount;
        if (spanIndex == 0) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, (int) (value * spanIndex), 0, 0);
        }
    }

    @Override
    public void onDraw(
            @NonNull Canvas canvas,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
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