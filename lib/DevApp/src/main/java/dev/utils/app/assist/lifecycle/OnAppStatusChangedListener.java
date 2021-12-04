package dev.utils.app.assist.lifecycle;

/**
 * detail: APP 状态改变事件
 * @author Ttt
 */
public interface OnAppStatusChangedListener {

    /**
     * 切换到前台
     */
    void onForeground();

    /**
     * 切换到后台
     */
    void onBackground();
}