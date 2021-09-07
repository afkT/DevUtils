package dev.utils.app.helper.view;

import android.view.View;

import dev.utils.app.helper.IHelper;

/**
 * detail: ViewHelper 接口
 * @author Ttt
 */
public interface IHelperByView<T>
        extends IHelper<T> {

    // ==============
    // = ClickUtils =
    // ==============

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param range 点击范围
     * @param views View[]
     * @return Helper
     */
    T addTouchAreas(
            int range,
            View... views
    );

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param top    top range
     * @param bottom bottom range
     * @param left   left range
     * @param right  right range
     * @param views  View[]
     * @return Helper
     */
    T addTouchAreas(
            int top,
            int bottom,
            int left,
            int right,
            View... views
    );

    /**
     * 设置点击事件
     * @param listener {@link View.OnClickListener}
     * @param views    View 数组
     * @return Helper
     */
    T setOnClicks(
            final View.OnClickListener listener,
            final View... views
    );

    /**
     * 设置长按事件
     * @param listener {@link View.OnLongClickListener}
     * @param views    View 数组
     * @return Helper
     */
    T setOnLongClicks(
            final View.OnLongClickListener listener,
            final View... views
    );

    /**
     * 设置触摸事件
     * @param listener {@link View.OnTouchListener}
     * @param views    View 数组
     * @return Helper
     */
    T setOnTouchs(
            final View.OnTouchListener listener,
            final View... views
    );

    // =============
    // = ViewUtils =
    // =============


}