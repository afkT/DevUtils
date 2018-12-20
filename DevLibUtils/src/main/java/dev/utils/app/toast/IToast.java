package dev.utils.app.toast;

import android.view.View;

/**
 * detail: Toast 对外提供接口方法
 * Created by Ttt
 */
public interface IToast {

    // ====================
    // ----- 配置方法 -----
    // ====================

    /**
     * 使用单次 Toast 样式配置
     * @param toastStyle Toast 样式
     * @return
     */
    IToast other(IToastStyle toastStyle);

    /**
     * 获取 Toast 样式配置
     * @return Toast 样式配置
     */
    IToastStyle getToastStyle();

    /**
     * 初始化 Toast 样式配置(可以不调用,使用了App默认配置)
     * @return Toast 样式配置
     */
    IToastStyle init();

    /**
     * 手动改变 Toast 样式配置(非单次,一直持续)
     * @param toastStyle Toast 样式配置
     */
    void init(IToastStyle toastStyle);

    // ========= 操作方法 =========

    // = 显示文本的, 通过内容长度来控制, 显示时长 =

    /**
     * 显示 Toast
     * @param content
     */
    void show(String content);

    /**
     * 显示 Toast
     * @param content
     * @param args
     */
    void show(String content, Object... args);

    /**
     * 显示 R.string.resId Toast
     * @param resId
     */
    void show(int resId);

    /**
     * 显示 R.string.resId Toast
     * @param resId
     * @param args
     */
    void show(int resId, Object... args);

    // = 直接显示View的, 通过配置时间来控制 =

    /**
     * 通过 View 显示 Toast
     * @param view
     */
    void show(View view);

    /**
     * 通过 View 显示 Toast
     * @param view
     * @param duration
     */
    void show(View view, int duration);

    // =

    /**
     * 取消当前显示的 Toast
     */
    void cancel();

    /**
     * 取消全部 Toast
     */
    void cancelAll();

}
