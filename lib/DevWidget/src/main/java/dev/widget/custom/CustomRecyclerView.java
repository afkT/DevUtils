package dev.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

import dev.utils.app.WidgetUtils;
import dev.widget.utils.WidgetAttrs;

/**
 * detail: 自定义 RecyclerView 滑动监听、滑动控制
 * @author Ttt
 * <pre>
 *     app:dev_slide=""
 *     app:dev_maxWidth=""
 *     app:dev_maxHeight=""
 *     <p></p>
 *     解决 ScrollView 嵌套 RecyclerView 的显示及滑动问题
 *     @see <a href="https://segmentfault.com/a/1190000011553735"/>
 *     RecyclerView 使用 GridLayoutManager 间距设置
 *     @see <a href="https://www.jianshu.com/p/f85923bd14ba"/>
 *     Android 可伸缩布局 - FlexboxLayout ( 支持 RecyclerView 集成 )
 *     @see <a href="https://juejin.im/post/58d1035161ff4b00603ca9c4"/>
 *     android Recycleview 中 item 没有填满屏幕的问题
 *     @see <a href="https://blog.csdn.net/shanshan_1117/article/details/79363971"/>
 *     android RecyclerView 悬浮吸顶效果
 *     @see <a href="https://www.zhangshengrong.com/p/JKN8Ejo5X6"/>
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
public class CustomRecyclerView
        extends RecyclerView {

    private WidgetAttrs    mWidgetAttrs;
    // 滑动监听回调
    private ScrollCallback mCallback = null;
    // 距离左边距离
    private int            mScrollX  = 0;
    // 距离顶部距离
    private int            mScrollY  = 0;

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public CustomRecyclerView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
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
        mWidgetAttrs = new WidgetAttrs(context, attrs);
    }

    @Override
    protected void onMeasure(
            int widthMeasureSpec,
            int heightMeasureSpec
    ) {
        int[] measureSpecs = WidgetUtils.viewMeasure(
                this, widthMeasureSpec, heightMeasureSpec,
                mWidgetAttrs.getMaxWidth(), mWidgetAttrs.getMaxHeight()
        );
        super.onMeasure(measureSpecs[0], measureSpecs[1]);
    }

    @Override
    public void onScrolled(
            int dx,
            int dy
    ) {
        super.onScrolled(dx, dy);
        mScrollX += dx;
        mScrollY += dy;
        // 防止出现负数
        mScrollX = Math.abs(mScrollX);
        mScrollY = Math.abs(mScrollY);
        // 触发回调
        if (mCallback != null) {
            mCallback.onScrollChanged(this, dx, dy, mScrollX, mScrollY);
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        // 触发回调
        if (mCallback != null) {
            mCallback.onScrollStateChanged(this, state);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mWidgetAttrs.isSlide()) return false;
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!mWidgetAttrs.isSlide()) return false;
        return super.onInterceptTouchEvent(arg0);
    }

    /**
     * 获取 View 最大显示宽度
     * @return View 最大显示宽度
     */
    public int getMaxWidth() {
        return mWidgetAttrs.getMaxWidth();
    }

    /**
     * 设置 View 最大显示宽度
     * @param maxWidth View 最大显示宽度
     * @return {@link CustomRecyclerView}
     */
    public CustomRecyclerView setMaxWidth(int maxWidth) {
        mWidgetAttrs.setMaxWidth(maxWidth);
        return this;
    }

    /**
     * 获取 View 最大显示高度
     * @return View 最大显示高度
     */
    public int getMaxHeight() {
        return mWidgetAttrs.getMaxHeight();
    }

    /**
     * 设置 View 最大显示高度
     * @param maxHeight View 最大显示高度
     * @return {@link CustomRecyclerView}
     */
    public CustomRecyclerView setMaxHeight(int maxHeight) {
        mWidgetAttrs.setMaxHeight(maxHeight);
        return this;
    }

    /**
     * 是否允许滑动
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSlide() {
        return mWidgetAttrs.isSlide();
    }

    /**
     * 设置是否允许滑动
     * @param isSlide {@code true} yes, {@code false} no
     * @return {@link CustomRecyclerView}
     */
    public CustomRecyclerView setSlide(boolean isSlide) {
        mWidgetAttrs.setSlide(isSlide);
        return this;
    }

    /**
     * 切换滑动控制状态
     * @return {@link CustomRecyclerView}
     */
    public CustomRecyclerView toggleSlide() {
        mWidgetAttrs.toggleSlide();
        return this;
    }

    /**
     * 设置滑动监听回调
     * @param callback {@link ScrollCallback}
     * @return {@link CustomRecyclerView}
     */
    public CustomRecyclerView setScrollCallback(ScrollCallback callback) {
        this.mCallback = callback;
        return this;
    }

    /**
     * detail: 滑动监听回调
     * @author Ttt
     */
    public interface ScrollCallback {

        /**
         * 滑动状态通知
         * @param recyclerView {@link RecyclerView}
         * @param state        滑动状态
         */
        void onScrollStateChanged(
                RecyclerView recyclerView,
                int state
        );

        /**
         * 滑动改变通知
         * @param recyclerView {@link RecyclerView}
         * @param dx           左边滑动距离
         * @param dy           顶部滑动距离
         * @param scrollX      距离左边距离
         * @param scrollY      距离顶部距离
         */
        void onScrollChanged(
                RecyclerView recyclerView,
                int dx,
                int dy,
                int scrollX,
                int scrollY
        );
    }
}