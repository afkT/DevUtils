package dev.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * detail: 自定义 RecyclerView 滑动监听、滑动控制
 * @author Ttt
 * <pre>
 *     解决 ScrollView 嵌套 RecyclerView 的显示及滑动问题
 *     @see <a href="https://segmentfault.com/a/1190000011553735"/>
 *     RecyclerView 使用 GridLayoutManager 间距设置
 *     @see <a href="https://www.jianshu.com/p/f85923bd14ba"/>
 *     Android 可伸缩布局 －FlexboxLayout ( 支持 RecyclerView 集成 )
 *     @see <a href="https://juejin.im/post/58d1035161ff4b00603ca9c4"/>
 *     android Recycleview 中 item 没有填满屏幕的问题
 *     @see <a href="https://blog.csdn.net/shanshan_1117/article/details/79363971"/>
 *     android RecyclerView 悬浮吸顶效果
 *     @see <a href="https://www.zhangshengrong.com/p/JKN8Ejo5X6/"/>
 *     @see <a href="https://github.com/LidongWen/MultiTypeAdapter"/>
 *     <p></p>
 *     RecyclerView 用 LinearLayout/RelativeLayout 包住、使用 {@link CustomNestedScrollView}
 *     <p></p>
 *     // 此方法常用作判断是否能下拉刷新, 来解决滑动冲突
 *     int findFirstCompletelyVisibleItemPosition = ((LinearLayoutManager) manager).findFirstCompletelyVisibleItemPosition();
 *     // 最后一个完整的可见的 item 位置
 *     int findLastCompletelyVisibleItemPosition = ((LinearLayoutManager) manager).findLastCompletelyVisibleItemPosition();
 *     // 最后一个可见的位置
 *     int findLastVisibleItemPosition = ((LinearLayoutManager) manager).findLastVisibleItemPosition();
 *     // 第一个可见的位置
 *     int firstPosition = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
 * </pre>
 */
public class CustomRecyclerView extends RecyclerView {

    // 是否允许滑动
    private boolean mIsSlide = true;
    // 滑动监听回调
    private ScrollCallBack mScrollCallBack = null;
    // 距离左边距离
    private int mScrollX = 0;
    // 距离顶部距离
    private int mScrollY = 0;

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        mScrollX += dx;
        mScrollY += dy;
        // 防止出现负数
        mScrollX = Math.abs(mScrollX);
        mScrollY = Math.abs(mScrollY);
        // 触发回调
        if (mScrollCallBack != null) {
            mScrollCallBack.onScrollChanged(this, dx, dy, mScrollX, mScrollY);
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        // 触发回调
        if (mScrollCallBack != null) {
            mScrollCallBack.onScrollStateChanged(this, state);
        }
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
     * @return {@link CustomRecyclerView}
     */
    public CustomRecyclerView setSlide(boolean isSlide) {
        this.mIsSlide = isSlide;
        return this;
    }

    /**
     * 切换滑动控制状态
     * @return {@link CustomRecyclerView}
     */
    public CustomRecyclerView toggleSlide() {
        this.mIsSlide = !this.mIsSlide;
        return this;
    }

    /**
     * 设置滑动监听回调
     * @param scrollCallBack {@link ScrollCallBack}
     * @return {@link CustomRecyclerView}
     */
    public CustomRecyclerView setScrollCallBack(ScrollCallBack scrollCallBack) {
        this.mScrollCallBack = scrollCallBack;
        return this;
    }

    /**
     * detail: 滑动监听回调
     * @author Ttt
     */
    public interface ScrollCallBack {

        /**
         * 滑动状态通知
         * @param recyclerView {@link RecyclerView}
         * @param state        滑动状态
         */
        void onScrollStateChanged(RecyclerView recyclerView, int state);

        /**
         * 滑动改变通知
         * @param recyclerView {@link RecyclerView}
         * @param dx           左边滑动距离
         * @param dy           顶部滑动距离
         * @param scrollX      距离左边距离
         * @param scrollY      距离顶部距离
         */
        void onScrollChanged(RecyclerView recyclerView, int dx, int dy, int scrollX, int scrollY);
    }
}