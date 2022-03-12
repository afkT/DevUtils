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
 * detail: RecyclerView Grid 行分割线处理
 * @author Ttt
 * <pre>
 *     方便基类复用统一继承 {@link BaseGridItemDecoration}
 *     不会使用到 列 ( Column ) 相关的任何字段
 * </pre>
 */
public class GridRowItemDecoration
        extends BaseGridItemDecoration {

    public GridRowItemDecoration(
            final int spanCount,
            final float rowHeight
    ) {
        this(spanCount, rowHeight, Color.TRANSPARENT);
    }

    public GridRowItemDecoration(
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
        int itemCount = state.getItemCount();
        if (itemCount <= mSpanCount) return;

        int index = parent.getChildAdapterPosition(view);
        if (index < mSpanCount) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, (int) mRowHeight, 0, 0);
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
}