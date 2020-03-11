package dev.widget.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * detail: 自定义 Gallery 滑动控制
 * @author Ttt
 */
public class CustomGallery extends Gallery {

    // 是否允许滑动
    private boolean mIsSlide = true;

    public CustomGallery(Context context) {
        super(context);
    }

    public CustomGallery(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomGallery(Context context, @Nullable AttributeSet attrs, int defStyle) {
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
        return mIsSlide;
    }

    /**
     * 设置是否允许滑动
     * @param isSlide {@code true} yes, {@code false} no
     * @return {@link CustomGallery}
     */
    public CustomGallery setSlide(boolean isSlide) {
        this.mIsSlide = isSlide;
        return this;
    }

    /**
     * 切换滑动控制状态
     * @return {@link CustomGallery}
     */
    public CustomGallery toggleSlide() {
        this.mIsSlide = !this.mIsSlide;
        return this;
    }
}