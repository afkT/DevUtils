package dev.widget.decoration.linear.horizontal;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.widget.decoration.BaseItemDecoration;
import dev.widget.decoration.linear.FirstLineItemDecoration;

/**
 * detail: RecyclerView 分割线 ( 在开头添加一条分割线 )
 * @author Ttt
 * <pre>
 *     {@link FirstLineItemDecoration} 横向滑动版实现
 * </pre>
 */
public class FirstLineHorizontalItemDecoration
        extends BaseItemDecoration {

    public FirstLineHorizontalItemDecoration(float lineHeight) {
        super(lineHeight);
    }

    public FirstLineHorizontalItemDecoration(
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

    @Override
    public void onDraw(
            @NonNull Canvas canvas,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        super.onDraw(canvas, parent, state);

        int itemCount = state.getItemCount();
        if (!mSingleLineDraw && itemCount <= 1) {
            return;
        }

        View child    = parent.getChildAt(0);
        int  position = parent.getChildAdapterPosition(child);
        if (position == 0) {
            canvas.drawRect(
                    child.getLeft() - mLineHeight - mOffset,
                    child.getTop() + mLineLeft,
                    child.getLeft() - mOffset,
                    child.getBottom() - mLineRight,
                    mLinePaint
            );
        }
    }
}