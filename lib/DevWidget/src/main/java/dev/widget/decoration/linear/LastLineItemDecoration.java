package dev.widget.decoration.linear;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.widget.decoration.BaseLinearItemDecoration;

/**
 * detail: RecyclerView Linear 分割线处理 ( 最后一条数据 )
 * @author Ttt
 * <pre>
 *     效果:
 *     最后一条数据底部添加一条分割线
 * </pre>
 */
public class LastLineItemDecoration
        extends BaseLinearItemDecoration {

    public LastLineItemDecoration(
            final boolean vertical,
            final float lineHeight
    ) {
        super(vertical, lineHeight);
    }

    public LastLineItemDecoration(
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
        int itemCount = state.getItemCount();
        if (parent.getChildAdapterPosition(view) == itemCount - 1) {
            if (!mSingleLineDraw && itemCount <= 1) {
                return;
            }
            outRect.set(0, 0, 0, (int) mLineHeight);
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

        int lastPosition = (itemCount - 1);
        if (lastPosition >= 0) {
            int childCount = parent.getChildCount();
            int last       = (childCount - 1);
            if (last >= 0) {
                // 判断当前屏幕最后一个 View 是否最后一条索引
                View child    = parent.getChildAt(last);
                int  position = parent.getChildAdapterPosition(child);
                if (position == lastPosition) {
                    parent.getDecoratedBoundsWithMargins(child, mLineBounds);
                    final float bottom = mLineBounds.bottom;
                    final float top    = bottom - mLineHeight;

                    canvas.drawRect(
                            child.getLeft() + mLineLeft,
                            top - mLineOffset,
                            child.getRight() - mLineRight,
                            bottom - mLineOffset,
                            mLinePaint
                    );
                }
            }
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
        int itemCount = state.getItemCount();
        if (parent.getChildAdapterPosition(view) == itemCount - 1) {
            if (!mSingleLineDraw && itemCount <= 1) {
                return;
            }
            outRect.set(0, 0, (int) mLineHeight, 0);
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

        int lastPosition = (itemCount - 1);
        if (lastPosition >= 0) {
            int childCount = parent.getChildCount();
            int last       = (childCount - 1);
            if (last >= 0) {
                // 判断当前屏幕最后一个 View 是否最后一条索引
                View child    = parent.getChildAt(last);
                int  position = parent.getChildAdapterPosition(child);
                if (position == lastPosition) {
                    parent.getDecoratedBoundsWithMargins(child, mLineBounds);
                    final float right = mLineBounds.right;
                    final float left  = right - mLineHeight;

                    canvas.drawRect(
                            left - mLineOffset,
                            child.getTop() + mLineLeft,
                            right - mLineOffset,
                            child.getBottom() - mLineRight,
                            mLinePaint
                    );
                }
            }
        }
    }
}