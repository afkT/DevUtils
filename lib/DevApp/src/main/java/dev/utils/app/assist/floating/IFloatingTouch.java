package dev.utils.app.assist.floating;

import android.view.MotionEvent;
import android.view.View;

/**
 * detail: 悬浮窗触摸辅助类接口
 * @author Ttt
 */
public interface IFloatingTouch {

    /**
     * 悬浮窗 View 触摸事件
     * @param view  {@link View}
     * @param event 触摸事件
     * @return True if the event was handled, false otherwise.
     */
    boolean onTouchEvent(
            View view,
            MotionEvent event
    );

    /**
     * 更新 View Layout
     * @param view {@link View}
     * @param dx   累加 X 轴坐标
     * @param dy   累加 Y 轴坐标
     */
    void updateViewLayout(
            View view,
            int dx,
            int dy
    );
}