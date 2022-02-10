package dev.utils.app.assist.floating;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import dev.utils.app.ViewUtils;

/**
 * detail: DevApp 悬浮窗触摸辅助类实现
 * @author Ttt
 */
public class DevFloatingTouchIMPL2
        implements IFloatingTouch {

    public DevFloatingTouchIMPL2() {
        this(new DevFloatingEdgeIMPL());
    }

    public DevFloatingTouchIMPL2(final IFloatingEdge floatingEdge) {
        this.mFloatingEdge = floatingEdge;
    }

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
                    mCommon.actionUp(event);
                    if (mCommon.onClick(view, event, mListener)) {
                        return true;
                    }
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

        if (mFloatingEdge != null) {
            Point edgePoint = mFloatingEdge.calculateEdge(view, mX, mY);
            if (edgePoint != null) {
                mX = edgePoint.x;
                mY = edgePoint.y;
            }
        }
        // 设置边距
        ViewUtils.setMargin(view, mX, mY, 0, 0);
    }

    // ===========
    // = get/set =
    // ===========

    // 当前 X 轴坐标
    private int mX = 0;
    // 当前 Y 轴坐标
    private int mY = 0;

    /**
     * 获取 X 轴坐标
     * @return X 轴坐标
     */
    public int getX() {
        return mX;
    }

    /**
     * 设置 X 轴坐标
     * @param x X 轴坐标
     * @return DevFloatingTouchIMPL2
     */
    public DevFloatingTouchIMPL2 setX(final int x) {
        this.mX = x;
        return this;
    }

    /**
     * 获取 Y 轴坐标
     * @return Y 轴坐标
     */
    public int getY() {
        return mY;
    }

    /**
     * 设置 Y 轴坐标
     * @param y Y 轴坐标
     * @return DevFloatingTouchIMPL2
     */
    public DevFloatingTouchIMPL2 setY(final int y) {
        this.mY = y;
        return this;
    }

    // =

    // 悬浮窗边缘检测接口
    private IFloatingEdge mFloatingEdge;

    /**
     * 获取悬浮窗边缘检测接口实现
     * @return IFloatingEdge
     */
    public IFloatingEdge getFloatingEdge() {
        return mFloatingEdge;
    }

    /**
     * 设置悬浮窗边缘检测接口实现
     * @param floatingEdge 悬浮窗边缘检测接口
     * @return DevFloatingTouchIMPL2
     */
    public DevFloatingTouchIMPL2 setFloatingEdge(final IFloatingEdge floatingEdge) {
        this.mFloatingEdge = floatingEdge;
        return this;
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
    public void setFloatingListener(final IFloatingListener listener) {
        this.mListener = listener;
    }

    // =

    public DevFloatingCommon getCommon() {
        return mCommon;
    }
}