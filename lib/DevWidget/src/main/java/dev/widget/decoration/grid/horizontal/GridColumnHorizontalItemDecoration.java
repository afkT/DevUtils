package dev.widget.decoration.grid.horizontal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.widget.decoration.BaseGridItemDecoration;
import dev.widget.decoration.grid.GridColumnItemDecoration;

/**
 * detail: RecyclerView Grid 列分割线处理
 * @author Ttt
 * <pre>
 *     {@link GridColumnItemDecoration} 横向滑动版实现
 * </pre>
 */
public class GridColumnHorizontalItemDecoration
        extends BaseGridItemDecoration {

    public GridColumnHorizontalItemDecoration(
            final int spanCount,
            final float columnHeight
    ) {
        this(spanCount, columnHeight, Color.TRANSPARENT);
    }

    public GridColumnHorizontalItemDecoration(
            final int spanCount,
            final float columnHeight,
            @ColorInt final int columnColor
    ) {
        super(spanCount);
        this.mColumnHeight = columnHeight;
        this.mColumnPaint.setColor(columnColor);
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
        float value     = mColumnHeight / mSpanCount;
        int   index     = parent.getChildAdapterPosition(view);
        int   spanIndex = index % mSpanCount;
        if (spanIndex == 0) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set((int) (value * spanIndex), 0, 0, 0);
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
                        child.getLeft() - mColumnHeight - mColumnOffset,
                        child.getTop() + mColumnLeft,
                        child.getLeft() - mColumnOffset,
                        child.getBottom() - mColumnRight,
                        mColumnPaint
                );
            }
        }
    }
}