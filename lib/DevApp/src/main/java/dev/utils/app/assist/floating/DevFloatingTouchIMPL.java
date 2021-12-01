package dev.utils.app.assist.floating;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

/**
 * detail: DevApp 悬浮窗触摸辅助类实现
 * @author Ttt
 */
public abstract class DevFloatingTouchIMPL
        implements IFloatingTouch {

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
}