package dev.widget.decoration.linear.horizontal;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.widget.decoration.BaseLinearItemDecoration;
import dev.widget.decoration.linear.LineItemDecoration;

/**
 * detail: RecyclerView 分割线
 * @author Ttt
 * <pre>
 *     {@link LineItemDecoration} 横向滑动版实现
 * </pre>
 */
public class LineHorizontalItemDecoration
        extends BaseLinearItemDecoration {

    public LineHorizontalItemDecoration(float lineHeight) {
        super(lineHeight);
    }

    public LineHorizontalItemDecoration(
            float lineHeight,
            int lineColor
    ) {
        super(lineHeight, lineColor);
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

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set((int) mLineHeight, 0, 0, 0);
        }
    }

    @Override
    public void onDraw(
            @NonNull Canvas canvas,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        super.onDraw(canvas, parent, state);

        int itemCount = state.getItemCount();
        if (itemCount <= 1) return;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (parent.getChildAdapterPosition(child) != 0) {
                canvas.drawRect(
                        child.getLeft() - mLineHeight - mLineOffset,
                        child.getTop() + mLineLeft,
                        child.getLeft() - mLineOffset,
                        child.getBottom() - mLineRight,
                        mLinePaint
                );
            }
        }
    }
}