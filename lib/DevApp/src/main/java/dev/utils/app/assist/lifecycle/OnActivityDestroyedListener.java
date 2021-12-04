package dev.utils.app.assist.lifecycle;

import android.app.Activity;

/**
 * detail: Activity 销毁事件
 * @author Ttt
 */
public interface OnActivityDestroyedListener {

    /**
     * Activity 销毁通知
     * @param activity {@link Activity}
     */
    void onActivityDestroyed(Activity activity);
}