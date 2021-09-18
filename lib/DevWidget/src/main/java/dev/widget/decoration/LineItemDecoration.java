package dev.widget.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

/**
 * detail: RecyclerView 分割线
 * @author Ttt
 * <pre>
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
        extends RecyclerView.ItemDecoration {

    // 分割线高度
    private       float mLineHeight;
    // 分割线画笔
    private final Paint mLinePaint;

    public LineItemDecoration(float lineHeight) {
        this(lineHeight, Color.BLACK);
    }

    public LineItemDecoration(
            float lineHeight,
            @ColorInt int lineColor
    ) {
        this.mLineHeight = lineHeight;
        this.mLinePaint  = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mLinePaint.setColor(lineColor);
    }

    // ==========
    // = 对外公开 =
    // ==========

    /**
     * 获取分割线画笔
     * @return 分割线画笔
     */
    public Paint getLinePaint() {
        return mLinePaint;
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
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (parent.getChildAdapterPosition(child) != 0) {
                canvas.drawRect(
                        child.getLeft(),
                        child.getTop() - mLineHeight,
                        child.getRight(),
                        child.getTop(),
                        mLinePaint
                );
            }
        }
    }
}