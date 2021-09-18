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
 * detail: RecyclerView 分割线 ( 在结尾添加一条分割线 )
 * @author Ttt
 * <pre>
 *     可自行改造 {@link #onDrawOver(Canvas, RecyclerView, RecyclerView.State)}
 *     通过 Canvas 绘制所需样式
 * </pre>
 */
public class LastLineItemDecoration
        extends RecyclerView.ItemDecoration {

    // 分割线高度
    private final float   mLineHeight;
    // 分割线画笔
    private final Paint   mLinePaint;
    // 单条数据是否绘制分割线
    private       boolean mSingleLineDraw = false;

    public LastLineItemDecoration(float lineHeight) {
        this(lineHeight, Color.TRANSPARENT);
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

    /**
     * 获取单条数据是否绘制分割线
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSingleLineDraw() {
        return mSingleLineDraw;
    }

    /**
     * 设置单条数据是否绘制分割线
     * @param singleLineDraw {@code true} yes, {@code false} no
     * @return {@link LastLineItemDecoration}
     */
    public LastLineItemDecoration setSingleLineDraw(boolean singleLineDraw) {
        this.mSingleLineDraw = singleLineDraw;
        return this;
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
    public void onDrawOver(
            @NonNull Canvas canvas,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        super.onDrawOver(canvas, parent, state);

        int itemCount    = state.getItemCount();
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
                            child.getLeft(),
                            child.getBottom(),
                            child.getRight(),
                            child.getBottom() + mLineHeight,
                            mLinePaint
                    );
                }
            }
        }
    }
}