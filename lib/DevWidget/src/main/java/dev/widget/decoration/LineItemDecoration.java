package dev.widget.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

/**
 * detail: RecyclerView 分割线
 * @author Ttt
 * <pre>
 *     效果:
 *     每个 Item 下面绘制 Line, 最后一条数据不绘制 ( 绘制 ItemCount - 1 条 Line 效果 )
 *     <p></p>
 *     使用:
 *     {@link RecyclerView#addItemDecoration(RecyclerView.ItemDecoration)}
 *     <p></p>
 *     也可以使用内置 {@link DividerItemDecoration}
 *     自定义分割线使用方法
 *     DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
 *     decoration.setDrawable(Drawable)
 *     recyclerView.addItemDecoration(decoration)
 * </pre>
 */
public class LineItemDecoration
        extends BaseItemDecoration {

    public LineItemDecoration(float lineHeight) {
        super(lineHeight);
    }

    public LineItemDecoration(
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
            outRect.set(0, (int) mLineHeight, 0, 0);
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
        if (itemCount <= 1) return;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (parent.getChildAdapterPosition(child) != 0) {
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
}