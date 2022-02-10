package dev.utils.app.assist.floating;

import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * detail: 悬浮窗通用代码
 * @author Ttt
 */
public class DevFloatingCommon {

    // 触摸点记录
    private final PointF mPoint      = new PointF();
    // 首次触摸点记录
    private final PointF mFirstPoint = new PointF();
    // 触摸时间
    private       long   mDownTime   = 0L;

    /**
     * 触摸按下
     * @param event 触摸事件
     */
    public void actionDown(final MotionEvent event) {
        mPoint.x = event.getRawX();
        mPoint.y = event.getRawY();
        // 首次触摸点记录
        mFirstPoint.x = event.getRawX();
        mFirstPoint.y = event.getRawY();

        mDownTime = System.currentTimeMillis();
    }

    /**
     * 触摸移动
     * @param event 触摸事件
     * @return 移动误差值
     */
    public int[] actionMove(final MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();

        int dx = (int) (x - mPoint.x);
        int dy = (int) (y - mPoint.y);

        mPoint.x = x;
        mPoint.y = y;
        return new int[]{dx, dy};
    }
}