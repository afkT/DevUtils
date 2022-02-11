package dev.utils.app.assist.floating;

import android.view.MotionEvent;
import android.view.View;

/**
 * detail: DevApp 悬浮窗触摸辅助类实现
 * @author Ttt
 */
public abstract class DevFloatingTouchIMPL
        implements IFloatingTouch {

    // ==========
    // = 具体功能 =
    // ==========

    // 悬浮窗通用代码
    private final DevFloatingCommon mCommon = new DevFloatingCommon();

    @Override
    public boolean onTouchEvent(
            View view,
            MotionEvent event
    ) {
        mCommon.update(view, event);
        if (event != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mCommon.actionDown(event);
                    // 开始校验长按
                    mCommon.postLongClick(mListener);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int[] points = mCommon.actionMove(event);
                    int dx = points[0];
                    int dy = points[1];
                    // 更新 View Layout
                    updateViewLayout(view, dx, dy);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mCommon.actionUp(event);
                    if (mCommon.onClick(view, event, mListener)) {
                        return true;
                    }
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

    /**
     * 获取悬浮窗触摸事件接口
     * @return 悬浮窗触摸事件接口
     */
    @Override
    public IFloatingListener getFloatingListener() {
        return mListener;
    }

    /**
     * 获取悬浮窗触摸事件接口
     * @param listener 悬浮窗触摸事件接口
     */
    @Override
    public void setFloatingListener(final IFloatingListener listener) {
        this.mListener = listener;
    }

    // =

    /**
     * 获取悬浮窗通用代码
     * @return 悬浮窗通用代码
     */
    public DevFloatingCommon getCommon() {
        return mCommon;
    }
}