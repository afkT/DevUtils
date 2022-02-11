package dev.widget.decoration.linear;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.widget.decoration.BaseItemDecoration;

/**
 * detail: RecyclerView 分割线 ( 在开头添加一条分割线 )
 * @author Ttt
 * <pre>
 *     可自行改造 {@link #onDrawOver(Canvas, RecyclerView, RecyclerView.State)}
 *     通过 Canvas 绘制所需样式
 * </pre>
 */
public class FirstLineItemDecoration
        extends BaseItemDecoration {

    public FirstLineItemDecoration(float lineHeight) {
        super(lineHeight);
    }

    public FirstLineItemDecoration(
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
            outRect.set(0, (int) mLineHeight, 0, 0);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }

    @Override
    public void onDrawOver(
            @NonNull Canvas canvas,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        super.onDrawOver(canvas, parent, state);

        int itemCount = state.getItemCount();
        if (!mSingleLineDraw && itemCount <= 1) {
            return;
        }

        View child    = parent.getChildAt(0);
        int  position = parent.getChildAdapterPosition(child);
        if (position == 0) {
            canvas.drawRect(
                    child.getLeft() + mLineLeft,
                    child.getTop() - mLineHeight,
                    child.getRight() - mLineRight,
                    child.getTop(),
                    mLinePaint
            );
        }
    }
}