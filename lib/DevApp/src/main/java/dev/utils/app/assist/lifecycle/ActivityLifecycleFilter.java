package dev.utils.app.assist.lifecycle;

import android.app.Activity;

/**
 * detail: Activity 生命周期 过滤判断接口
 * @author Ttt
 */
public interface ActivityLifecycleFilter {

    /**
     * 判断是否过滤该类 ( 不进行添加等操作 )
     * @param activity {@link Activity}
     * @return {@code true} yes, {@code false} no
     */
    boolean filter(Activity activity);
}