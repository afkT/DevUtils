package dev.widget.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

import dev.utils.app.WidgetUtils;
import dev.widget.utils.WidgetAttrs;

/**
 * detail: 自定义 Gallery 滑动控制
 * @author Ttt
 * @Deprecated 推荐使用 {@link CustomRecyclerView} + LinearSnapHelper 实现
 * <pre>
 *     app:dev_slide=""
 *     app:dev_maxWidth=""
 *     app:dev_maxHeight=""
 * </pre>
 */
@Deprecated
public class CustomGallery
        extends Gallery {

    private WidgetAttrs mWidgetAttrs;

    public CustomGallery(Context context) {
        super(context);
    }

    public CustomGallery(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public CustomGallery(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomGallery(
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
    public boolean onFling(
            MotionEvent e1,
            MotionEvent e2,
            float velocityX,
            float velocityY
    ) {
        int keyCode;
        if (isScrollingLeft(e1, e2)) {
            keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
        } else {
            keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(keyCode, null);
        return true;
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
     * 判断是否向左滑动
     * @param e1 {@link MotionEvent}
     * @param e2 {@link MotionEvent}
     * @return {@code true} yes, {@code false} no
     */
    private boolean isScrollingLeft(
            MotionEvent e1,
            MotionEvent e2
    ) {
        return e2.getX() > e1.getX();
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
     * @return {@link CustomGallery}
     */
    public CustomGallery setMaxWidth(int maxWidth) {
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
     * @return {@link CustomGallery}
     */
    public CustomGallery setMaxHeight(int maxHeight) {
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
     * @return {@link CustomGallery}
     */
    public CustomGallery setSlide(boolean isSlide) {
        mWidgetAttrs.setSlide(isSlide);
        return this;
    }

    /**
     * 切换滑动控制状态
     * @return {@link CustomGallery}
     */
    public CustomGallery toggleSlide() {
        mWidgetAttrs.toggleSlide();
        return this;
    }
}