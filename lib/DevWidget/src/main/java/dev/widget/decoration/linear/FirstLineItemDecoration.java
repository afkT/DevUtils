package dev.widget.decoration.linear;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.widget.decoration.BaseLinearItemDecoration;

/**
 * detail: RecyclerView Linear 分割线处理 ( 第一条数据 )
 * @author Ttt
 * <pre>
 *     效果:
 *     第一条数据顶部添加一条分割线
 * </pre>
 */
public class FirstLineItemDecoration
        extends BaseLinearItemDecoration {

    public FirstLineItemDecoration(
            final boolean vertical,
            final float lineHeight
    ) {
        super(vertical, lineHeight);
    }

    public FirstLineItemDecoration(
            final boolean vertical,
            final float lineHeight,
            @ColorInt final int lineColor
    ) {
        super(vertical, lineHeight, lineColor);
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
            Rect outRect,
            View view,
            RecyclerView parent,
            RecyclerView.State state
    ) {
        if (parent.getChildAdapterPosition(view) == 0) {
            int itemCount = state.getItemCount();
            if (!mSingleLineDraw && itemCount <= 1) {
                return;
            }
            outRect.set(0, (int) mLineHeight, 0, 0);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }

    private void verticalDraw(
            Canvas canvas,
            RecyclerView parent,
            RecyclerView.State state
    ) {
        int itemCount = state.getItemCount();
        if (!mSingleLineDraw && itemCount <= 1) {
            return;
        }

        View child    = parent.getChildAt(0);
        int  position = parent.getChildAdapterPosition(child);
        if (position == 0) {
            canvas.drawRect(
                    child.getLeft() + mLineLeft,
                    child.getTop() - mLineHeight - mLineOffset,
                    child.getRight() - mLineRight,
                    child.getTop() - mLineOffset,
                    mLinePaint
            );
        }
    }

    // ==============
    // = horizontal =
    // ==============

    private void horizontalItemOffsets(
            Rect outRect,
            View view,
            RecyclerView parent,
            RecyclerView.State state
    ) {
        if (parent.getChildAdapterPosition(view) == 0) {
            int itemCount = state.getItemCount();
            if (!mSingleLineDraw && itemCount <= 1) {
                return;
            }
            outRect.set((int) mLineHeight, 0, 0, 0);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }

    private void horizontalDraw(
            Canvas canvas,
            RecyclerView parent,
            RecyclerView.State state
    ) {
        int itemCount = state.getItemCount();
        if (!mSingleLineDraw && itemCount <= 1) {
            return;
        }

        View child    = parent.getChildAt(0);
        int  position = parent.getChildAdapterPosition(child);
        if (position == 0) {
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