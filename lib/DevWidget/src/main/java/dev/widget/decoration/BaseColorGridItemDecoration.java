package dev.widget.decoration;

import android.graphics.Color;

import androidx.annotation.ColorInt;

/**
 * detail: 基础 RecyclerView Grid 分割线处理
 * @author Ttt
 * <pre>
 *     纯色值绘制 行、列 分割线
 * </pre>
 */
public class BaseColorGridItemDecoration
        extends BaseColorItemDecoration {

    // 是否 Grid Column ItemDecoration
    private final boolean mColumnItemDecoration;
    // Span 总数 ( Grid 列 )
    protected     int     mSpanCount;

    // ==========
    // = 构造函数 =
    // ==========

    public BaseColorGridItemDecoration(
            final boolean columnItemDecoration,
            final int spanCount,
            final boolean vertical,
            final float height
    ) {
        this(columnItemDecoration, spanCount, vertical, height, Color.TRANSPARENT);
    }

    public BaseColorGridItemDecoration(
            final boolean columnItemDecoration,
            final int spanCount,
            final boolean vertical,
            final float height,
            @ColorInt final int color
    ) {
        super(vertical, height, color);
        this.mColumnItemDecoration = columnItemDecoration;
        this.mSpanCount            = spanCount;
    }

    // ==========
    // = 对外公开 =
    // ==========

    /**
     * 是否 Grid Row ItemDecoration
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRowItemDecoration() {
        return !mColumnItemDecoration;
    }

    /**
     * 是否 Grid Column ItemDecoration
     * @return {@code true} yes, {@code false} no
     */
    public boolean isColumnItemDecoration() {
        return mColumnItemDecoration;
    }

    /**
     * 获取 Span 总数 ( Grid 列 )
     * @return Span 总数 ( Grid 列 )
     */
    public int getSpanCount() {
        return mSpanCount;
    }

    /**
     * 设置 Span 总数 ( Grid 列 )
     * @param spanCount Span 总数 ( Grid 列 )
     * @return {@link BaseColorGridItemDecoration}
     */
    public BaseColorGridItemDecoration setSpanCount(final int spanCount) {
        this.mSpanCount = spanCount;
        return this;
    }
}