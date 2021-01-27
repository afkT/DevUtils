package dev.widget.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import dev.widget.R;

/**
 * detail: 换行 View
 * @author Ttt
 * <pre>
 *     Google FlexboxLayout 推荐使用该库, 支持 RecyclerView ( FlexboxLayoutManager )
 *     @see <a href="https://github.com/google/flexbox-layout"/>
 *     Android 可伸缩布局 FlexboxLayout ( 支持 RecyclerView 集成 )
 *     @see <a href="https://juejin.im/post/58d1035161ff4b00603ca9c4"/>
 *     <p></p>
 *     app:dev_maxLine=""
 *     app:dev_rowTopMargin=""
 *     app:dev_viewLeftMargin=""
 * </pre>
 */
public class WrapView
        extends ViewGroup {

    // View 换行行数
    private int mRowLine;
    // 支持最大行数
    private int mMaxLine        = Integer.MAX_VALUE;
    // 每一行向上的边距 ( 行间隔 )
    private int mRowTopMargin   = 20;
    // 每个 View 之间的 Left 边距
    private int mViewLeftMargin = 20;

    public WrapView(Context context) {
        super(context);
    }

    public WrapView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public WrapView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WrapView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes
    ) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs);
    }

    /**
     * 初始化
     * @param context {@link Context}
     * @param attrs   {@link AttributeSet}
     */
    private void initAttrs(
            Context context,
            AttributeSet attrs
    ) {
        if (context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DevWidget);
            mMaxLine = a.getInt(R.styleable.DevWidget_dev_maxLine, Integer.MAX_VALUE);
            mRowTopMargin = a.getLayoutDimension(R.styleable.DevWidget_dev_rowTopMargin, 20);
            mViewLeftMargin = a.getLayoutDimension(R.styleable.DevWidget_dev_viewLeftMargin, 20);
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(
            int widthMeasureSpec,
            int heightMeasureSpec
    ) {
        for (int i = 0, size = getChildCount(); i < size; i++) {
            getChildAt(i).measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }
        // 获取高度模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        // 判断高度是否自动处理
        boolean isHeightWrapContent = (MeasureSpec.AT_MOST == heightMode || MeasureSpec.UNSPECIFIED == heightMode);
        // 获取宽度、高度
        int width  = resolveSize(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), widthMeasureSpec);
        int height = resolveSize(getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec), heightMeasureSpec);
        // 设置 View 的宽度高度
        setMeasuredDimension(width, (isHeightWrapContent ? calc(width, mRowTopMargin, mViewLeftMargin) : height));
    }

    @Override
    protected void onLayout(
            boolean changed,
            int l,
            int t,
            int r,
            int b
    ) {
        boolean newLine        = false; // 判断是否换行
        boolean fillWidth      = false; // 判断是否填满宽度 (View 直接超过一行宽度 )
        int     paddingTop     = getPaddingTop(); // 上边距
        int     paddingLeft    = getPaddingLeft(); // 左边距
        int     drawX          = 0; // 已绘制的 X 轴距离
        int     rowLine        = 0; // 当前绘制的行数
        int     calcHeight     = 0; // 计算累加的高度
        int     width          = getWidth() - (paddingLeft + getPaddingRight()); // 宽度减去左右边距
        int     widthSubRight  = getWidth() - getPaddingRight(); // 宽度减去右边距
        int     rowTopMargin   = mRowTopMargin;
        int     viewLeftMargin = mViewLeftMargin;
        // 循环所有子 View
        for (int i = 0, size = getChildCount(); i < size; i++) {
            final View child           = this.getChildAt(i);
            int        childViewWidth  = child.getMeasuredWidth();
            int        childViewHeight = child.getMeasuredHeight();
            // 左边距
            int leftMargin = (drawX == 0) ? 0 : viewLeftMargin;
            // 赋值处理 ( 防止 View 宽度超出剩余宽度 )
            newLine = fillWidth = (childViewWidth > width - leftMargin);
            // 换行了则不需要边距
            if (newLine) leftMargin = 0;
            // 宽度处理
            if (fillWidth) childViewWidth = width - leftMargin;
            // 保存绘制距离
            drawX += childViewWidth + leftMargin;
            // 如果已绘制的宽度大于 View 的宽度则换行
            if (drawX > width) {
                // 表示属于换行
                newLine = true;
                // 累加行数
                rowLine++;
                // 如果超过了则修改边距
                leftMargin = 0;
            } else if (fillWidth) { // 属于铺满的, 则重置处理
                // 表示属于换行
                newLine = true;
                // 属于第一条则不处理 ( 默认加 1 了 )
                if (i != 0) {
                    // 累加行数
                    rowLine++;
                }
            }
            // 重置起始位置
            if (newLine) drawX = childViewWidth + leftMargin;
            // 累加高度 = ( 行数 + 1) * view 的高度 + ( 行数 * 每行向上的边距 ) + 内边距
            calcHeight = (rowLine + 1) * childViewHeight + (rowLine * rowTopMargin) + paddingTop;
            // 防止超出整个 View 宽度
            int right = paddingLeft + drawX;
            if (right > widthSubRight) right = widthSubRight;
            // 绘制 View 位置
            child.layout(right - childViewWidth, calcHeight - childViewHeight, right, calcHeight);
        }
    }

    // ===========
    // = 内部方法 =
    // ===========

    /**
     * 通过 View 宽度计算绘制所需高度
     * @param rootWidth      宽度
     * @param rowTopMargin   每一行向上的边距 ( 行间隔 )
     * @param viewLeftMargin 每个 View 之间的 Left 边距
     * @return 计算 View 高度
     */
    private int calc(
            final int rootWidth,
            final int rowTopMargin,
            final int viewLeftMargin
    ) {
        boolean newLine    = false; // 判断是否换行
        boolean fillWidth  = false; // 判断是否填满宽度 (View 直接超过一行宽度 )
        int     drawX      = 0; // 已绘制的 X 轴距离
        int     rowLine    = 0; // 当前绘制的行数
        int     calcHeight = 0; // 计算累加的高度
        int     width      = rootWidth - (getPaddingLeft() + getPaddingRight()); // 宽度减去左右边距
        // 循环所有子 View
        for (int i = 0, size = getChildCount(); i < size; i++) {
            final View child           = this.getChildAt(i);
            int        childViewWidth  = child.getMeasuredWidth();
            int        childViewHeight = child.getMeasuredHeight();
            // 左边距
            int leftMargin = (drawX == 0) ? 0 : viewLeftMargin;
            // 赋值处理 ( 防止 View 宽度超出剩余宽度 )
            newLine = fillWidth = (childViewWidth > width - leftMargin);
            // 换行了则不需要边距
            if (newLine) leftMargin = 0;
            // 宽度处理
            if (fillWidth) childViewWidth = width - leftMargin;
            // 保存绘制距离
            drawX += childViewWidth + leftMargin;
            // 如果已绘制的宽度大于 View 的宽度则换行
            if (drawX > width) {
                // 表示属于换行
                newLine = true;
                // 累加行数
                rowLine++;
                // 如果超过了则修改边距
                leftMargin = 0;
            } else if (fillWidth) { // 属于铺满的, 则重置处理
                // 表示属于换行
                newLine = true;
                // 属于第一条则不处理 ( 默认加 1 了 )
                if (i != 0) {
                    // 累加行数
                    rowLine++;
                }
            }
            // 重置起始位置
            if (newLine) drawX = childViewWidth + leftMargin;
            // 累加高度 = ( 行数 + 1) * view 的高度 + ( 行数 * 每行向上的边距 )
            calcHeight = (rowLine + 1) * childViewHeight + (rowLine * rowTopMargin);
        }
        // 获取数据总数
        int childCount = getChildCount();
        // 如果超过了最大行, 则递减重新计算
        if (rowLine >= mMaxLine) {
            // 如果已经没有子 View, 则返回边距
            if (childCount == 0) return getPaddingTop() + getPaddingBottom();
            try { // 减去最后一个
                removeViewAt(childCount - 1);
            } catch (Exception e) {
            }
            return calc(rootWidth, rowTopMargin, viewLeftMargin);
        }
        // 保存行数
        mRowLine = (childCount > 0) ? (rowLine + 1) : 0;
        // 保存高度
        return getPaddingTop() + calcHeight + getPaddingBottom();
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 刷新绘制 ( 更新配置信息后, 必须调用 )
     */
    public void refreshDraw() {
        requestLayout();
        postInvalidate();
    }

    /**
     * 获取 View 换行行数
     * @return View 换行行数
     */
    public int getRowLine() {
        return mRowLine;
    }

    /**
     * 获取最大行数
     * @return 最大行数
     */
    public int getMaxLine() {
        return mMaxLine;
    }

    /**
     * 设置最大行数
     * @param maxLine 最大行数
     * @return {@link WrapView}
     */
    public WrapView setMaxLine(int maxLine) {
        this.mMaxLine = maxLine;
        return this;
    }

    /**
     * 获取每一行向上的边距 ( 行间隔 )
     * @return 每一行向上的边距 ( 行间隔 )
     */
    public int getRowTopMargin() {
        return mRowTopMargin;
    }

    /**
     * 设置每一行向上的边距 ( 行间隔 )
     * @param rowTopMargin 每一行向上的边距 ( 行间隔 )
     * @return {@link WrapView}
     */
    public WrapView setRowTopMargin(int rowTopMargin) {
        this.mRowTopMargin = rowTopMargin;
        return this;
    }

    /**
     * 获取每个 View 之间的 Left 边距
     * @return 每个 View 之间的 Left 边距
     */
    public int getViewLeftMargin() {
        return mViewLeftMargin;
    }

    /**
     * 设置每个 View 之间的 Left 边距
     * @param viewLeftMargin 每个 View 之间的 Left 边距
     * @return {@link WrapView}
     */
    public WrapView setViewLeftMargin(int viewLeftMargin) {
        this.mViewLeftMargin = viewLeftMargin;
        return this;
    }

    /**
     * 设置 Row View 边距
     * @param rowTopMargin   每一行向上的边距 ( 行间隔 )
     * @param viewLeftMargin 每个 View 之间的 Left 边距
     * @return {@link WrapView}
     */
    public WrapView setRowViewMargin(
            int rowTopMargin,
            int viewLeftMargin
    ) {
        this.mRowTopMargin = rowTopMargin;
        this.mViewLeftMargin = viewLeftMargin;
        return this;
    }
}