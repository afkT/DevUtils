package dev.utils.app.helper.view;

import android.view.View;
import android.view.ViewGroup;

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
     * @param views    View[]
     * @return Helper
     */
    T setOnClicks(
            View.OnClickListener listener,
            View... views
    );

    /**
     * 设置长按事件
     * @param listener {@link View.OnLongClickListener}
     * @param views    View[]
     * @return Helper
     */
    T setOnLongClicks(
            View.OnLongClickListener listener,
            View... views
    );

    /**
     * 设置触摸事件
     * @param listener {@link View.OnTouchListener}
     * @param views    View[]
     * @return Helper
     */
    T setOnTouchs(
            View.OnTouchListener listener,
            View... views
    );

    // =============
    // = ViewUtils =
    // =============

    /**
     * 设置是否限制子 View 在其边界内绘制
     * @param clipChildren {@code true} yes, {@code false} no
     * @param viewGroups   ViewGroup[]
     * @return Helper
     */
    T setClipChildrens(
            boolean clipChildren,
            ViewGroup... viewGroups
    );

    /**
     * 设置 View LayoutParams
     * @param view   {@link View}
     * @param params LayoutParams
     * @return Helper
     */
    T setLayoutParams(
            View view,
            ViewGroup.LayoutParams params
    );
}