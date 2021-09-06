package dev.utils.app.helper;

import android.view.View;

import dev.utils.app.helper.dev.DevHelper;
import dev.utils.app.helper.quick.QuickHelper;
import dev.utils.app.helper.view.ViewHelper;

/**
 * detail: Helper 通用方法接口
 * @author Ttt
 */
public interface IHelper {

    // ==========
    // = Helper =
    // ==========

    /**
     * 获取 DevHelper
     * @return {@link DevHelper}
     */
    DevHelper devHelper();

    /**
     * 获取 QuickHelper
     * @param target 目标 View
     * @return {@link QuickHelper}
     */
    QuickHelper quickHelper(View target);

    /**
     * 获取 ViewHelper
     * @return {@link ViewHelper}
     */
    ViewHelper viewHelper();
}