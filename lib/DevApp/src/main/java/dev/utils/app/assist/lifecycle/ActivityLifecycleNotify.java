package dev.utils.app.assist.lifecycle;

import android.app.Activity;

import dev.DevUtils;

/**
 * detail: Activity 生命周期 通知接口
 * @author Ttt
 */
public interface ActivityLifecycleNotify {

    /**
     * 添加 APP 状态改变事件监听
     * @param object   key
     * @param listener APP 状态改变监听事件
     */
    void addOnAppStatusChangedListener(
            Object object,
            DevUtils.OnAppStatusChangedListener listener
    );

    /**
     * 移除 APP 状态改变事件监听
     * @param object key
     */
    void removeOnAppStatusChangedListener(Object object);

    /**
     * 移除全部 APP 状态改变事件监听
     */
    void removeAllOnAppStatusChangedListener();

    // =

    /**
     * 添加 Activity 销毁通知事件
     * @param activity {@link Activity}
     * @param listener Activity 销毁通知事件
     */
    void addOnActivityDestroyedListener(
            Activity activity,
            DevUtils.OnActivityDestroyedListener listener
    );

    /**
     * 移除 Activity 销毁通知事件
     * @param activity {@link Activity}
     */
    void removeOnActivityDestroyedListener(Activity activity);

    /**
     * 移除全部 Activity 销毁通知事件
     */
    void removeAllOnActivityDestroyedListener();
}