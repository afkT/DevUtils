package dev.utils.app.assist.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * detail: Activity LifecycleCallbacks 抽象类
 * @author Ttt
 */
public abstract class AbstractActivityLifecycle
        implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(
            Activity activity,
            Bundle savedInstanceState
    ) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(
            Activity activity,
            Bundle outState
    ) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}