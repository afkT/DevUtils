package dev.widget.decoration.linear;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.widget.decoration.BaseItemDecoration;

/**
 * detail: RecyclerView 分割线 ( 在结尾添加一条分割线 )
 * @author Ttt
 */
public class LastLineItemDecoration
        extends BaseItemDecoration {

    public LastLineItemDecoration(float lineHeight) {
        super(lineHeight);
    }

    public LastLineItemDecoration(
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
            outRect.set(0, 0, 0, (int) mLineHeight);
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
                    canvas.drawRect(
                            child.getLeft() + mLineLeft,
                            child.getBottom() - this.getOffset(),
                            child.getRight() - mLineRight,
                            child.getBottom() + mLineHeight - this.getOffset(),
                            mLinePaint
                    );
                }
            }
        }
    }
}