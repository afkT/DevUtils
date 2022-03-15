package dev.widget.decoration.linear;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.widget.decoration.BaseColorItemDecoration;

/**
 * detail: RecyclerView Linear 分割线处理 ( 第一条数据 )
 * @author Ttt
 * <pre>
 *     效果:
 *     第一条数据顶部添加一条分割线
 * </pre>
 */
public class FirstLinearColorItemDecoration
        extends BaseColorItemDecoration {

    public FirstLinearColorItemDecoration(
            final boolean vertical,
            final float height
    ) {
        super(vertical, height);
    }

    public FirstLinearColorItemDecoration(
            final boolean vertical,
            final float height,
            @ColorInt final int color
    ) {
        super(vertical, height, color);
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
        if (parent.getChildAdapterPosition(view) == 0) {
            int itemCount = state.getItemCount();
            if (!mSingleDraw && itemCount <= 1) {
                return;
            }
            outRect.set(0, (int) mHeight, 0, 0);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }

    private void verticalDraw(
            final Canvas canvas,
            final RecyclerView parent,
            final RecyclerView.State state
    ) {
        int itemCount = state.getItemCount();
        if (!mSingleDraw && itemCount <= 1) {
            return;
        }

        View child    = parent.getChildAt(0);
        int  position = parent.getChildAdapterPosition(child);
        if (position == 0) {
            canvas.drawRect(
                    child.getLeft() + mLeft,
                    child.getTop() - mHeight - mOffset,
                    child.getRight() - mRight,
                    child.getTop() - mOffset,
                    mPaint
            );
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
        if (parent.getChildAdapterPosition(view) == 0) {
            int itemCount = state.getItemCount();
            if (!mSingleDraw && itemCount <= 1) {
                return;
            }
            outRect.set((int) mHeight, 0, 0, 0);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }

    private void horizontalDraw(
            final Canvas canvas,
            final RecyclerView parent,
            final RecyclerView.State state
    ) {
        int itemCount = state.getItemCount();
        if (!mSingleDraw && itemCount <= 1) {
            return;
        }

        View child    = parent.getChildAt(0);
        int  position = parent.getChildAdapterPosition(child);
        if (position == 0) {
            canvas.drawRect(
                    child.getLeft() - mHeight - mOffset,
                    child.getTop() + mLeft,
                    child.getLeft() - mOffset,
                    child.getBottom() - mRight,
                    mPaint
            );
        }
    }
}