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
 * detail: RecyclerView Grid 列分割线处理 ( 第一列数据 )
 * @author Ttt
 * <pre>
 *     效果:
 *     第一列数据顶部绘制分割线
 *     <p></p>
 *     方便基类复用统一继承 {@link BaseGridItemDecoration}
 *     不会使用到 行 ( row ) 相关的任何字段
 * </pre>
 */
public class FirstGridColumnItemDecoration
        extends BaseGridItemDecoration {

    public FirstGridColumnItemDecoration(
            final boolean vertical,
            final int spanCount,
            final float columnHeight
    ) {
        this(vertical, spanCount, columnHeight, Color.TRANSPARENT);
    }

    public FirstGridColumnItemDecoration(
            final boolean vertical,
            final int spanCount,
            final float columnHeight,
            @ColorInt final int columnColor
    ) {
        super(true, vertical, spanCount);
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
        float value     = mColumnHeight / mSpanCount;
        int   index     = parent.getChildAdapterPosition(view);
        int   spanIndex = index % mSpanCount;
        if (spanIndex == 0) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set((int) (value * spanIndex), 0, 0, 0);
        }
    }

    private void verticalDraw(
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
                        child.getLeft() - mColumnHeight - mColumnOffset,
                        child.getTop() + mColumnLeft,
                        child.getLeft() - mColumnOffset,
                        child.getBottom() - mColumnRight,
                        mColumnPaint
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
        int itemCount = state.getItemCount();
        if (itemCount <= mSpanCount) return;

        int index = parent.getChildAdapterPosition(view);
        if (index < mSpanCount) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set((int) mColumnHeight, 0, 0, 0);
        }
    }

    private void horizontalDraw(
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