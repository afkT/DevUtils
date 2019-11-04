package dev.utils.app;

import android.view.View;

/**
 * detail: List View ( 列表 View ) 相关工具类
 * @author Ttt
 * <pre>
 * </pre>
 */
public final class ListViewUtils {

    // 日志 TAG
    private static final String TAG = ListViewUtils.class.getSimpleName();

    private ListViewUtils() {
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // =============
    // = ViewUtils =
    // =============

    /**
     * View 内容滚动位置 - 相对于初始位置移动
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @return {@link View}
     */
    public static View scrollTo(final View view, final int x, final int y) {
        if (view != null) view.scrollTo(x, y);
        return view;
    }

    /**
     * View 内部滚动位置 - 相对于上次移动的最后位置移动
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @return {@link View}
     */
    public static View scrollBy(final View view, final int x, final int y) {
        if (view != null) view.scrollBy(x, y);
        return view;
    }

    /**
     * 设置 View 滑动的 X 轴坐标
     * @param view  {@link View}
     * @param value X 轴坐标
     * @return {@link View}
     */
    public static View setScrollX(final View view, final int value) {
        if (view != null) view.setScrollX(value);
        return view;
    }

    /**
     * 设置 View 滑动的 Y 轴坐标
     * @param view  {@link View}
     * @param value Y 轴坐标
     * @return {@link View}
     */
    public static View setScrollY(final View view, final int value) {
        if (view != null) view.setScrollY(value);
        return view;
    }

    /**
     * 获取 View 滑动的 X 轴坐标
     * @param view {@link View}
     * @return 滑动的 X 轴坐标
     */
    public static int getScrollX(final View view) {
        return view != null ? view.getScrollX() : 0;
    }

    /**
     * 获取 View 滑动的 Y 轴坐标
     * @param view {@link View}
     * @return 滑动的 Y 轴坐标
     */
    public static int getScrollY(final View view) {
        return view != null ? view.getScrollY() : 0;
    }
}