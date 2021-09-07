package dev.utils.app.helper.view;

import android.view.View;
import android.view.ViewGroup;

import dev.utils.app.ClickUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.dev.DevHelper;
import dev.utils.app.helper.quick.QuickHelper;
import dev.utils.common.ForUtils;

/**
 * detail: View 链式调用快捷设置 Helper 类
 * @author Ttt
 */
public final class QQQQQQQQQQQ
        implements IHelperByView<QQQQQQQQQQQ> {

    private QQQQQQQQQQQ() {
    }

    // ViewHelper
    private static final QQQQQQQQQQQ HELPER = new QQQQQQQQQQQ();

    /**
     * 获取单例 QQQQQQQQQQQ
     * @return {@link QQQQQQQQQQQ}
     */
    public static QQQQQQQQQQQ get() {
        return HELPER;
    }

    // ===========
    // = IHelper =
    // ===========

    /**
     * 获取 DevHelper
     * @return {@link DevHelper}
     */
    @Override
    public DevHelper devHelper() {
        return DevHelper.get();
    }

    /**
     * 获取 QuickHelper
     * @param target 目标 View
     * @return {@link QuickHelper}
     */
    @Override
    public QuickHelper quickHelper(View target) {
        return QuickHelper.get(target);
    }

    /**
     * 获取 ViewHelper
     * @return {@link ViewHelper}
     */
    @Override
    public ViewHelper viewHelper() {
        return ViewHelper.get();
    }

    // =================
    // = IHelperByView =
    // =================

    // ==============
    // = ClickUtils =
    // ==============

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param range 点击范围
     * @param views View[]
     * @return Helper
     */
    @Override
    public QQQQQQQQQQQ addTouchAreas(
            int range,
            View... views
    ) {
        ForUtils.forSimpleArgs(value -> ClickUtils.addTouchArea(value, range), views);
        return this;
    }

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param top    top range
     * @param bottom bottom range
     * @param left   left range
     * @param right  right range
     * @param views  View[]
     * @return Helper
     */
    @Override
    public QQQQQQQQQQQ addTouchAreas(
            int top,
            int bottom,
            int left,
            int right,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ClickUtils.addTouchArea(
                        value, top, bottom, left, right
                ), views
        );
        return this;
    }

    /**
     * 设置点击事件
     * @param listener {@link View.OnClickListener}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public QQQQQQQQQQQ setOnClicks(
            View.OnClickListener listener,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ClickUtils.setOnClick(value, listener), views
        );
        return this;
    }

    /**
     * 设置长按事件
     * @param listener {@link View.OnLongClickListener}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public QQQQQQQQQQQ setOnLongClicks(
            View.OnLongClickListener listener,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ClickUtils.setOnLongClick(value, listener), views
        );
        return this;
    }

    /**
     * 设置触摸事件
     * @param listener {@link View.OnTouchListener}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public QQQQQQQQQQQ setOnTouchs(
            View.OnTouchListener listener,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ClickUtils.setOnTouch(value, listener), views
        );
        return this;
    }

    // =============
    // = ViewUtils =
    // =============

    /**
     * 设置是否限制子 View 在其边界内绘制
     * @param clipChildren {@code true} yes, {@code false} no
     * @param viewGroups   ViewGroup[]
     * @return Helper
     */
    @Override
    public QQQQQQQQQQQ setClipChildrens(
            boolean clipChildren,
            ViewGroup... viewGroups
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setClipChildren(value, clipChildren), viewGroups
        );
        return this;
    }

    /**
     * 设置 View LayoutParams
     * @param view   {@link View}
     * @param params LayoutParams
     * @return Helper
     */
    @Override
    public QQQQQQQQQQQ setLayoutParams(
            View view,
            ViewGroup.LayoutParams params
    ) {
        ViewUtils.setLayoutParams(view, params);
        return this;
    }
}