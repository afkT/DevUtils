package dev.widget.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import dev.widget.R;

/**
 * detail: 自定义 HorizontalScrollView 滑动监听、滑动控制
 * @author Ttt
 */
public class CustomHorizontalScrollView extends HorizontalScrollView {

    // 是否允许滑动
    private boolean mIsSlide = true;
    // 滑动监听回调
    private ScrollCallBack mScrollCallBack = null;
    // 默认值
    private final int DEF_VALUE = 0;
    // 最大显示宽度
    private int mMaxWidth = DEF_VALUE;
    // 最大显示高度
    private int mMaxHeight = DEF_VALUE;

    public CustomHorizontalScrollView(Context context) {
        super(context);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DevWidget);
        mMaxWidth = a.getLayoutDimension(R.styleable.DevWidget_maxWidth, DEF_VALUE);
        mMaxHeight = a.getLayoutDimension(R.styleable.DevWidget_maxHeight, DEF_VALUE);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        if (mMaxWidth > DEF_VALUE && mMaxHeight > DEF_VALUE) {
//            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.AT_MOST);
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
//        } else if (mMaxWidth > DEF_VALUE) {
//            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.AT_MOST);
//        } else if (mMaxHeight > DEF_VALUE) {
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
//        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onScrollChanged(int left, int top, int oldLeft, int oldTop) {
        super.onScrollChanged(left, top, oldLeft, oldTop);
        if (mScrollCallBack != null) {
            mScrollCallBack.onScrollChanged(left, top, oldLeft, oldTop);
        }
    }

    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0; // 解决禁止 ScrollView 内的控件改变之后自动滚动
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.mIsSlide) return false;
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!this.mIsSlide) return false;
        return super.onInterceptTouchEvent(arg0);
    }

    /**
     * 获取 View 最大显示高度
     * @return View 最大显示高度
     */
    public int getMaxHeight() {
        return mMaxHeight;
    }

    /**
     * 设置 View 最大显示高度
     * <pre>
     *     如果未超过此高度，则内部多高就占用多高
     * </pre>
     * @param maxHeight View 最大显示高度
     * @return {@link CustomHorizontalScrollView}
     */
    public CustomHorizontalScrollView setMaxHeight(int maxHeight) {
        this.mMaxHeight = maxHeight;
        return this;
    }

    /**
     * 是否允许滑动
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSlide() {
        return mIsSlide;
    }

    /**
     * 设置是否允许滑动
     * @param isSlide {@code true} yes, {@code false} no
     * @return {@link CustomHorizontalScrollView}
     */
    public CustomHorizontalScrollView setSlide(boolean isSlide) {
        this.mIsSlide = isSlide;
        return this;
    }

    /**
     * 切换滑动控制状态
     * @return {@link CustomHorizontalScrollView}
     */
    public CustomHorizontalScrollView toggleSlide() {
        this.mIsSlide = !this.mIsSlide;
        return this;
    }

    /**
     * 设置滑动监听回调
     * @param scrollCallBack {@link ScrollCallBack}
     * @return {@link CustomHorizontalScrollView}
     */
    public CustomHorizontalScrollView setScrollCallBack(ScrollCallBack scrollCallBack) {
        this.mScrollCallBack = scrollCallBack;
        return this;
    }

    /**
     * detail: 滑动监听回调
     * @author Ttt
     */
    public interface ScrollCallBack {

        /**
         * 滑动改变通知
         * @param left    距离左边距离
         * @param top     距离顶部距离
         * @param oldLeft 旧的 ( 之前 ) 距离左边距离
         * @param oldTop  旧的 ( 之前 ) 距离顶部距离
         */
        void onScrollChanged(int left, int top, int oldLeft, int oldTop);
    }
}