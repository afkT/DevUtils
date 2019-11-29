package afkt.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * detail: 自定义 HorizontalScrollView 监听滑动改变
 * @author Ttt
 */
public class CustomHorizontalScrollView extends HorizontalScrollView {

    // 是否允许滑动
    private boolean mIsSlide = true;
    // 是否监听滑动
    private boolean mIsSlideListener = true;
    // 滑动监听回调
    private ScrollCallBack mScrollCallBack = null;

    public CustomHorizontalScrollView(Context context) {
        super(context);
    }

    public CustomHorizontalScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHorizontalScrollView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int left, int top, int oldLeft, int oldTop) {
        super.onScrollChanged(left, top, oldLeft, oldTop);
        if (mIsSlideListener && mScrollCallBack != null) {
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
     * 切换滑动状态
     * @return {@link CustomHorizontalScrollView}
     */
    public CustomHorizontalScrollView toggleSlide() {
        this.mIsSlide = !this.mIsSlide;
        return this;
    }

    /**
     * 是否监听滑动
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSlideListener() {
        return mIsSlideListener;
    }

    /**
     * 设置是否监听滑动
     * @param slideListener {@code true} yes, {@code false} no
     * @return {@link CustomHorizontalScrollView}
     */
    public CustomHorizontalScrollView setSlideListener(boolean slideListener) {
        this.mIsSlideListener = slideListener;
        return this;
    }

    /**
     * 设置滑动回调
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