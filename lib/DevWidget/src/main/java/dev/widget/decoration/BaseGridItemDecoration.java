package dev.widget.decoration;

import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

/**
 * detail: 基础 RecyclerView Grid 分割线处理
 * @author Ttt
 */
public class BaseGridItemDecoration
        extends RecyclerView.ItemDecoration {

    // 分割线高度
    protected final float   mLineHeight;
//    // 分割线宽度
//    protected final float   mLineWidth;
    // 分割线画笔
    protected final Paint   mLinePaint;
    // Span 总数 ( Grid )
    protected       int     mSpanCount      = 0;

    public BaseGridItemDecoration(float lineHeight) {
        this(lineHeight, Color.TRANSPARENT);
    }

    public BaseGridItemDecoration(
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
     * 获取 Span 总数
     * @return Span 总数
     */
    public int getSpanCount() {
        return mSpanCount;
    }

    /**
     * 设置 Span 总数
     * @param spanCount Span 总数
     * @return {@link BaseGridItemDecoration}
     */
    public BaseGridItemDecoration setSpanCount(int spanCount) {
        this.mSpanCount = spanCount;
        return this;
    }
}