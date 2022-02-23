package dev.widget.decoration;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.recyclerview.widget.RecyclerView;

/**
 * detail: 基础 RecyclerView Grid 分割线处理
 * @author Ttt
 */
public class BaseGridItemDecoration
        extends RecyclerView.ItemDecoration {

    // Span 总数 ( Grid 列 )
    protected final int mSpanCount;

    // =====
    // = 行 =
    // =====

    // 行边界测算
    protected final Rect  mRowBounds = new Rect();
    // 行分割线画笔
    protected final Paint mRowPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 行分割线高度
    protected       float mRowHeight = 0.0F;
    // 行分割线距左边距
    protected       float mRowLeft   = 0.0F;
    // 行分割线距右边距
    protected       float mRowRight  = 0.0F;
    // 行分割线偏差值 ( 用于解决多个 ItemDecoration 叠加覆盖问题 )
    protected       float mRowOffset = 0.0F;

    // =====
    // = 列 =
    // =====

    // 列边界测算
    protected final Rect  mColumnBounds = new Rect();
    // 列分割线画笔
    protected final Paint mColumnPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 列分割线高度
    protected       float mColumnHeight = 0.0F;
    // 列分割线距左边距
    protected       float mColumnLeft   = 0.0F;
    // 列分割线距右边距
    protected       float mColumnRight  = 0.0F;
    // 列分割线偏差值 ( 用于解决多个 ItemDecoration 叠加覆盖问题 )
    protected       float mColumnOffset = 0.0F;

    // ==========
    // = 构造函数 =
    // ==========

    public BaseGridItemDecoration(int spanCount) {
        this.mSpanCount = spanCount;
        this.mRowPaint.setColor(Color.TRANSPARENT);
        this.mColumnPaint.setColor(Color.TRANSPARENT);
    }

    // ==========
    // = 对外公开 =
    // ==========
}