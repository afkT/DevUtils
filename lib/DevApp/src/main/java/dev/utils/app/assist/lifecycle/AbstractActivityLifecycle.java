package dev.utils.app.assist.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

/**
 * detail: Activity LifecycleCallbacks 抽象类
 * @author Ttt
 */
public abstract class AbstractActivityLifecycle
        implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(
            @NonNull Activity activity,
            Bundle savedInstanceState
    ) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(
            @NonNull Activity activity,
            @NonNull Bundle outState
    ) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }
}