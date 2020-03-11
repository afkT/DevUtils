package dev.widget.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * detail: 自定义 ViewPager 滑动监听、滑动控制
 * @author Ttt
 */
public class CustomViewPager extends ViewPager {

    // 是否允许滑动
    private boolean mIsSlide = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
     * @return {@link CustomViewPager}
     */
    public CustomViewPager setSlide(boolean isSlide) {
        this.mIsSlide = isSlide;
        return this;
    }

    /**
     * 切换滑动控制状态
     * @return {@link CustomViewPager}
     */
    public CustomViewPager toggleSlide() {
        this.mIsSlide = !this.mIsSlide;
        return this;
    }

    // ============
    // = 滑动方向 =
    // ============

    /**
     * detail: 滑动方向监听
     * @author Ttt
     * <pre>
     *     viewpager.setOnPageChangeListener(new CustomViewPager.OnDirectionListener() {
     *             @Override
     *             public void onSlideDirection(boolean left, boolean right) {
     *             }
     *             @Override
     *             public void onPageSelected(int index) {
     *             }
     *         });
     * </pre>
     */
    public static abstract class OnDirectionListener implements OnPageChangeListener {

        // 最后滑动的位置
        private int mLastValue = -1;
        // 是否滑动中
        private boolean mIsScrolling;
        // 是否滑向左边、右边
        private boolean mIsLeft, mIsRight;
        // 是否向左滑动
        protected boolean mLeftScroll = false;

        @Override
        public void onPageScrolled(int pos, float arg1, int arg2) {
            // pos  当前页面及你点击滑动的页面
            // arg1 当前页面偏移的百分比
            // arg2 当前页面偏移的像素位置

            if (mIsScrolling) {
                if (mLastValue > arg2) {
                    mIsRight = true;
                    mIsLeft = false;
                    mLeftScroll = false;
                } else if (mLastValue < arg2) {
                    mIsRight = false;
                    mIsLeft = true;
                    mLeftScroll = true;
                } else if (mLastValue == arg2) {
                    mIsRight = mIsLeft = false;
                }
                // 触发滑动方向回调
                onSlideDirection(mIsLeft, mIsRight);
            }
            mLastValue = arg2;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // 有三种状态
            // state == 0 表示什么都没做
            // state == 1 表示正在滑动
            // state == 2 表示滑动完毕了

            // 判断是否滑动中
            mIsScrolling = (state == 1);

            if (state == 2) {
                // 触发滑动方向回调
                onSlideDirection(mIsLeft, mIsRight);
                // 重置方向
                mIsRight = mIsLeft = false;
            }
        }

        /**
         * 滑动方向
         * @param left  是否向左滑动
         * @param right 是否向右滑动
         */
        public abstract void onSlideDirection(boolean left, boolean right);
    }
}