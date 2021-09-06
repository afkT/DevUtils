package dev.utils.app.helper.view;

import android.view.View;

import dev.utils.app.helper.dev.DevHelper;
import dev.utils.app.helper.quick.QuickHelper;

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

    // =============
    // = ViewUtils =
    // =============


}