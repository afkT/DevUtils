package dev.utils.app.assist.floating;

import android.graphics.Point;
import android.view.View;

import dev.utils.app.BarUtils;
import dev.utils.app.ScreenUtils;
import dev.utils.app.ViewUtils;

/**
 * detail: DevApp 悬浮窗边缘检测辅助类实现
 * @author Ttt
 */
public class DevFloatingEdgeImpl
        implements IFloatingEdge {

    // 最大显示高度
    private int mMaxHeight    = 0;
    // 向上边距 ( 可自行定义 )
    private int mMarginTop    = 0;
    // 向下边距 ( 可自行定义 )
    private int mMarginBottom = 0;

    @Override
    public Point calculateEdge(
            View view,
            int x,
            int y
    ) {
        if (x <= 0) x = 0;
        if (y <= 0) y = 0;

        // 宽度 ( X 轴 ) 计算
        int viewWidth   = ViewUtils.getWidth(view);
        int screenWidth = ScreenUtils.getScreenWidth();

        // 高度 ( Y 轴 ) 计算
        int viewHeight   = ViewUtils.getHeight(view);
        int screenHeight = ScreenUtils.getScreenHeight();
        // View 最大显示高度计算
        int viewMaxHeight = Math.max(mMaxHeight, viewHeight);
        this.mMaxHeight = viewMaxHeight;

        int diffWidth  = screenWidth - viewWidth;
        int diffHeight = screenHeight - viewMaxHeight - mMarginTop - mMarginBottom;

        if (x >= diffWidth) x = diffWidth;
        if (y >= diffHeight) y = diffHeight;

        return new Point(x, y);
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 View 最大显示高度
     * @return View 最大显示高度
     */
    public int getMaxHeight() {
        return mMaxHeight;
    }

    /**
     * 设置 View 最大显示高度
     * @param maxHeight View 最大显示高度
     * @return {@link DevFloatingEdgeImpl}
     */
    public DevFloatingEdgeImpl setMaxHeight(final int maxHeight) {
        this.mMaxHeight = maxHeight;
        return this;
    }

    /**
     * 获取向上边距
     * @return 向上边距
     */
    public int getMarginTop() {
        return mMarginTop;
    }

    /**
     * 设置向上边距
     * @param margin 向上边距
     * @return {@link DevFloatingEdgeImpl}
     */
    public DevFloatingEdgeImpl setMarginTop(final int margin) {
        this.mMarginTop = margin;
        return this;
    }

    /**
     * 获取向下边距
     * @return 向下边距
     */
    public int getMarginBottom() {
        return mMarginBottom;
    }

    /**
     * 设置向下边距
     * @param margin 向下边距
     * @return {@link DevFloatingEdgeImpl}
     */
    public DevFloatingEdgeImpl setMarginBottom(final int margin) {
        this.mMarginBottom = margin;
        return this;
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 设置向上边距为状态栏高度
     * @return {@link DevFloatingEdgeImpl}
     */
    public DevFloatingEdgeImpl setStatusBarHeightMargin() {
        return setMarginTop(BarUtils.getStatusBarHeight2());
    }

    /**
     * 设置向下边距为底部导航栏高度
     * @return {@link DevFloatingEdgeImpl}
     */
    public DevFloatingEdgeImpl setNavigationBarHeightMargin() {
        if (ScreenUtils.checkDeviceHasNavigationBar()) {
            return setMarginBottom(ScreenUtils.getNavigationBarHeight());
        }
        return this;
    }
}