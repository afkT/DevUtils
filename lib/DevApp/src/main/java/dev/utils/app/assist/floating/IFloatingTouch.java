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
     * @return {@code true} 消费事件, {@code false} 不消费事件
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

    // ==========
    // = 事件相关 =
    // ==========

    /**
     * 获取悬浮窗触摸事件接口
     * @return 悬浮窗触摸事件接口
     */
    IFloatingListener getFloatingListener();

    /**
     * 获取悬浮窗触摸事件接口
     * @param listener 悬浮窗触摸事件接口
     */
    void setFloatingListener(IFloatingListener listener);
}