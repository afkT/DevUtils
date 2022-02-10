package dev.utils.app.assist.floating;

import android.view.MotionEvent;
import android.view.View;

/**
 * detail: DevApp 悬浮窗触摸辅助类实现
 * @author Ttt
 */
public abstract class DevFloatingTouchIMPL
        implements IFloatingTouch {

    // 悬浮窗通用代码
    private DevFloatingCommon mCommon = new DevFloatingCommon();

    @Override
    public boolean onTouchEvent(
            View view,
            MotionEvent event
    ) {
        if (event != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mCommon.actionDown(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int[] points = mCommon.actionMove(event);
                    int dx = points[0];
                    int dy = points[1];

                    // 更新 View Layout
                    updateViewLayout(view, dx, dy);
                    break;
            }
        }
        return false;
    }

    // ==========
    // = 事件相关 =
    // ==========

    // 悬浮窗触摸事件接口
    private IFloatingListener mListener;

    @Override
    public IFloatingListener getFloatingListener() {
        return mListener;
    }

    @Override
    public void setFloatingListener(IFloatingListener listener) {
        this.mListener = listener;
    }
}