package dev.widget.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * detail: RecyclerView 分割线 ( 在结尾添加分割线 )
 * @author Ttt
 */
public class LastLineItemDecoration
        extends RecyclerView.ItemDecoration {

    // 分割线高度
    private       float mLineHeight;
    // 分割线画笔
    private final Paint mLinePaint;

    public LastLineItemDecoration(float lineHeight) {
        this(lineHeight, Color.BLACK);
    }

    public LastLineItemDecoration(
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
        int childCount = parent.getChildCount();
        if (parent.getChildAdapterPosition(view) == childCount - 1) {
            outRect.set(0, 0, 0, (int) mLineHeight);
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
        int childCount = parent.getChildCount();
        if (childCount - 1 >= 0) {
            View child    = parent.getChildAt(childCount - 1);
            int  position = parent.getChildAdapterPosition(child);
            if (position == childCount - 1) {
                canvas.drawRect(
                        child.getLeft(),
                        child.getTop(),
                        child.getRight(),
                        child.getBottom() + mLineHeight,
                        mLinePaint
                );
            }
        }
    }
}