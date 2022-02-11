package dev.utils.app.assist.floating;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

import dev.utils.app.assist.DelayAssist;

/**
 * detail: 悬浮窗通用代码
 * @author Ttt
 */
public class DevFloatingCommon
        implements DelayAssist.Callback {

    // 触摸 View
    private       View              mView;
    // 触摸事件
    private       MotionEvent       mEvent;
    // 悬浮窗触摸事件接口
    private       IFloatingListener mListener;
    // 触摸时间
    private       long              mDownTime    = 0L;
    // 触摸点记录
    private final PointF            mPoint       = new PointF();
    // 首次触摸点记录
    private final PointF            mFirstPoint  = new PointF();
    // 延迟触发辅助类
    private final DelayAssist       mDelayAssist = new DelayAssist(this);

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 实时更新方法
     * <pre>
     *     通过 {@link IFloatingTouch#onTouchEvent(View, MotionEvent)} 方法回调
     *     实时调用此方法进行更新
     * </pre>
     * @param view  触摸 View
     * @param event 触摸事件
     * @return DevFloatingCommon
     */
    public DevFloatingCommon update(
            final View view,
            final MotionEvent event
    ) {
        this.mView  = view;
        this.mEvent = event;
        return this;
    }

    // ===========
    // = get/set =
    // ===========

    public View getView() {
        return mView;
    }

    public MotionEvent getEvent() {
        return mEvent;
    }

    public IFloatingListener getListener() {
        return mListener;
    }

    public long getDownTime() {
        return mDownTime;
    }

    public PointF getPoint() {
        return mPoint;
    }

    public PointF getFirstPoint() {
        return mFirstPoint;
    }

    public DelayAssist getDelayAssist() {
        return mDelayAssist;
    }

    // =============
    // = 事件相关方法 =
    // =============

    /**
     * 手势按下
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
     * 手势移动
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

    /**
     * 手势抬起
     * @param event 触摸事件
     */
    public void actionUp(final MotionEvent event) {
        mDelayAssist.remove();
    }

    // =

    /**
     * 悬浮窗 View 点击事件
     * @param view     {@link View}
     * @param event    触摸事件
     * @param listener 悬浮窗触摸事件
     * @return {@code true} 消费事件, {@code false} 不消费事件
     */
    public boolean onClick(
            final View view,
            final MotionEvent event,
            final IFloatingListener listener
    ) {
        if (isValidClickByTime(listener)) {
            return listener.onClick(view, event, mFirstPoint);
        }
        return false;
    }

    /**
     * 悬浮窗 View 长按事件
     * @param view     {@link View}
     * @param event    触摸事件
     * @param listener 悬浮窗触摸事件
     * @return {@code true} 消费事件, {@code false} 不消费事件
     */
    public boolean onLongClick(
            final View view,
            final MotionEvent event,
            final IFloatingListener listener
    ) {
        if (isValidLongClickByTime(listener)) {
            return listener.onLongClick(view, event, mFirstPoint);
        }
        return false;
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 获取时间差 ( 当前时间 - 触摸时间 )
     * @return 时间差
     */
    public long getDiffTime() {
        return System.currentTimeMillis() - mDownTime;
    }

    /**
     * 是否有效间隔时间
     * @param time 时间间隔
     * @return {@code true} yes, {@code false} no
     */
    public boolean isValidTime(final long time) {
        long diffTime = getDiffTime();
        return (time > 0 && diffTime <= time);
    }

    /**
     * 通过时间判断点击是否有效
     * @param listener 悬浮窗触摸事件
     * @return {@code true} yes, {@code false} no
     */
    public boolean isValidClickByTime(final IFloatingListener listener) {
        if (listener != null) {
            return isValidTime(listener.getClickIntervalTime());
        }
        return false;
    }

    /**
     * 通过时间判断长按是否有效
     * @param listener 悬浮窗触摸事件
     * @return {@code true} yes, {@code false} no
     */
    public boolean isValidLongClickByTime(final IFloatingListener listener) {
        if (listener != null) {
            return isValidTime(listener.getLongClickIntervalTime());
        }
        return false;
    }

    // =================
    // = 延时长按校验相关 =
    // =================

    /**
     * 开始校验长按
     * @param listener 悬浮窗触摸事件
     */
    public void postLongClick(final IFloatingListener listener) {
        mDelayAssist.remove();
        if (listener != null) {
            this.mListener = listener;
            long time = listener.getLongClickIntervalTime();
            if (time > 0) mDelayAssist.post();
        }
    }

    // ========================
    // = DelayAssist.Callback =
    // ========================

    @Override
    public void callback(Object object) {
        if (mListener != null && mView != null && mEvent != null) {
            mListener.onLongClick(mView, mEvent, mFirstPoint);
        }
    }
}