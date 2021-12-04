package dev.utils.app.assist.lifecycle;

import android.app.Activity;

/**
 * detail: Activity 生命周期 相关信息获取接口
 * @author Ttt
 */
public interface ActivityLifecycleGet {

    /**
     * 获取最顶部 ( 当前或最后一个显示 ) Activity
     * @return {@link Activity}
     */
    Activity getTopActivity();

    /**
     * 判断某个 Activity 是否 Top Activity
     * @param activityClassName Activity.class.getCanonicalName()
     * @return {@code true} yes, {@code false} no
     */
    boolean isTopActivity(String activityClassName);

    /**
     * 判断某个 Class(Activity) 是否 Top Activity
     * @param clazz Activity.class or this.getClass()
     * @return {@code true} yes, {@code false} no
     */
    boolean isTopActivity(Class<?> clazz);

    /**
     * 判断应用是否在后台 ( 不可见 )
     * @return {@code true} yes, {@code false} no
     */
    boolean isBackground();

    /**
     * 获取 Activity 总数
     * @return 已打开 Activity 总数
     */
    int getActivityCount();
}