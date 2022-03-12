package dev.widget.decoration.linear.horizontal;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.widget.decoration.BaseItemDecoration;
import dev.widget.decoration.linear.LastLineItemDecoration;

/**
 * detail: RecyclerView 分割线 ( 在结尾添加一条分割线 )
 * @author Ttt
 * <pre>
 *     {@link LastLineItemDecoration} 横向滑动版实现
 *     不合并为一个实现类是防止在 {@link #onDraw} 中多次获取方向并进行判断处理
 * </pre>
 */
public class LastLineHorizontalItemDecoration
        extends BaseItemDecoration {

    public LastLineHorizontalItemDecoration(float lineHeight) {
        super(lineHeight);
    }

    public LastLineHorizontalItemDecoration(
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
        if (parent.getChildAdapterPosition(view) == itemCount - 1) {
            if (!mSingleLineDraw && itemCount <= 1) {
                return;
            }
            outRect.set(0, 0, (int) mLineHeight, 0);
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

        int lastPosition = (itemCount - 1);
        if (lastPosition >= 0) {
            int childCount = parent.getChildCount();
            int last       = (childCount - 1);
            if (last >= 0) {
                // 判断当前屏幕最后一个 View 是否最后一条索引
                View child    = parent.getChildAt(last);
                int  position = parent.getChildAdapterPosition(child);
                if (position == lastPosition) {
                    parent.getDecoratedBoundsWithMargins(child, mBounds);
                    final float right = mBounds.right;
                    final float left  = right - mLineHeight;

                    canvas.drawRect(
                            left - mOffset,
                            child.getTop() + mLineLeft,
                            right - mOffset,
                            child.getBottom() - mLineRight,
                            mLinePaint
                    );
                }
            }
        }
    }
}