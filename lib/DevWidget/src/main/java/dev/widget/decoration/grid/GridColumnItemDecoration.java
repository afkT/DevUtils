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
 * detail: RecyclerView Grid 列分割线处理
 * @author Ttt
 * <pre>
 *     方便基类复用统一继承 {@link BaseGridItemDecoration}
 *     不会使用到 行 ( row ) 相关的任何字段
 * </pre>
 */
public class GridColumnItemDecoration
        extends BaseGridItemDecoration {

    public GridColumnItemDecoration(
            final int spanCount,
            final float columnHeight
    ) {
        this(spanCount, columnHeight, Color.TRANSPARENT);
    }

    public GridColumnItemDecoration(
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
        int itemCount = state.getItemCount();
        if (itemCount <= 1) return;

        float value = mColumnHeight / mSpanCount;
        int   index = parent.getChildAdapterPosition(view) % mSpanCount;
        if (index == 0) {
            outRect.left  = 0;
            outRect.right = 0;
        } else {
            outRect.left  = (int) (index * value);
            outRect.right = 0;
        }
    }

    @Override
    public void onDraw(
            @NonNull Canvas canvas,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
//        int itemCount = state.getItemCount();
//        if (!mSingleLineDraw && itemCount <= 1) {
//            return;
//        }
//
//        View child    = parent.getChildAt(0);
//        int  position = parent.getChildAdapterPosition(child);
//        if (position == 0) {
//            canvas.drawRect(
//                    child.getLeft() + mLineLeft,
//                    child.getTop() - mLineHeight - this.getOffset(),
//                    child.getRight() - mLineRight,
//                    child.getTop() - this.getOffset(),
//                    mLinePaint
//            );
//        }
    }
}