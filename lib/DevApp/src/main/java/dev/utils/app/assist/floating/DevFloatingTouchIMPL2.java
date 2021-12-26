package dev.utils.app.assist.floating;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

import dev.utils.app.ScreenUtils;
import dev.utils.app.ViewUtils;

/**
 * detail: DevApp 悬浮窗触摸辅助类实现
 * @author Ttt
 */
public class DevFloatingTouchIMPL2
        implements IFloatingTouch {

    public DevFloatingTouchIMPL2() {
    }

    public DevFloatingTouchIMPL2(boolean edgeCheck) {
        this.mEdgeCheck = edgeCheck;
    }

    // ==========
    // = 具体功能 =
    // ==========

    // 触摸点记录
    private final PointF mPoint = new PointF();

    @Override
    public boolean onTouchEvent(
            View view,
            MotionEvent event
    ) {
        if (event != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPoint.x = event.getRawX();
                    mPoint.y = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float x = event.getRawX();
                    float y = event.getRawY();

                    int dx = (int) (x - mPoint.x);
                    int dy = (int) (y - mPoint.y);

                    mPoint.x = x;
                    mPoint.y = y;

                    // 更新 View Layout
                    updateViewLayout(view, dx, dy);
                    break;
            }
        }
        return false;
    }

    @Override
    public void updateViewLayout(
            View view,
            int dx,
            int dy
    ) {
        mX += dx;
        mY += dy;

        if (mX <= 0) mX = 0;
        if (mY <= 0) mY = 0;

        // 是否边缘检测
        if (mEdgeCheck) {
            int viewWidth    = ViewUtils.getWidth(view);
            int viewHeight   = ViewUtils.getHeight(view);
            int screenWidth  = ScreenUtils.getScreenWidth();
            int screenHeight = ScreenUtils.getScreenHeight();

            int diffWidth  = screenWidth - viewWidth;
            int diffHeight = screenHeight - viewHeight;

            if (mX > diffWidth) mX = diffWidth;
            if (viewHeight + mY > diffHeight) mY = diffHeight + viewHeight;
        }
        // 设置边距
        ViewUtils.setMargin(view, mX, mY, 0, 0);
    }

    // ===========
    // = get/set =
    // ===========

    // 当前 X 轴位置
    private int mX = 0;
    // 当前 Y 轴位置
    private int mY = 0;

    public int getX() {
        return mX;
    }

    public DevFloatingTouchIMPL2 setX(final int x) {
        this.mX = x;
        return this;
    }

    public int getY() {
        return mY;
    }

    public DevFloatingTouchIMPL2 setY(final int y) {
        this.mY = y;
        return this;
    }

    // =

    // 是否边缘检测
    private boolean mEdgeCheck = true;

    public boolean hasEdgeCheck() {
        return mEdgeCheck;
    }

    public DevFloatingTouchIMPL2 setEdgeCheck(final boolean edgeCheck) {
        this.mEdgeCheck = edgeCheck;
        return this;
    }
}