package dev.widget.control;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * detail: Gallery 滑动控制
 * @author Ttt
 */
public class ControlSlideGallery extends Gallery {

    // 是否允许滑动
    private boolean mSlide = true;

    public ControlSlideGallery(Context context) {
        super(context);
    }

    public ControlSlideGallery(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ControlSlideGallery(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
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
    public boolean onTouchEvent(MotionEvent event) {
        if (mSlide) {
            return super.onTouchEvent(event);
        } else {
            return false;
        }
    }

    /**
     * 判断是否向左滑动
     * @param e1 {@link MotionEvent}
     * @param e2 {@link MotionEvent}
     * @return {@code true} yes, {@code false} no
     */
    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
        return e2.getX() > e1.getX();
    }

    /**
     * 是否允许滑动
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSlide() {
        return mSlide;
    }

    /**
     * 设置是否允许滑动
     * @param isSlide {@code true} yes, {@code false} no
     * @return {@link ControlSlideGallery}
     */
    public ControlSlideGallery setSlide(boolean isSlide) {
        this.mSlide = isSlide;
        return this;
    }

    /**
     * 切换滑动控制状态
     * @return {@link ControlSlideGallery}
     */
    public ControlSlideGallery toggleSlide() {
        this.mSlide = !this.mSlide;
        return this;
    }
}