package dev.utils.app.assist.lifecycle.current;

import dev.utils.common.assist.KeyValueAssist;

/**
 * detail: 当前生命周期管理类
 * @author Ttt
 */
public final class ThisLifecycleManager {

    private ThisLifecycleManager() {
    }

    // 当前 Activity 生命周期键值对 Assist
    private static final KeyValueAssist<String, ThisActivityLifecycleAssist> sAssist = new KeyValueAssist<>();

    // ================
    // = Activity 监听 =
    // ================

    // ThisActivityLifecycleAssist 实例
    private static volatile ThisActivityLifecycleAssist sInstance;

    /**
     * 获取单例 ThisActivityLifecycleAssist 管理实例
     * @return {@link ThisActivityLifecycleAssist}
     */
    public static ThisActivityLifecycleAssist getSingleThisActivityLifecycle() {
        if (sInstance == null) {
            synchronized (ThisActivityLifecycleAssist.class) {
                if (sInstance == null) {
                    sInstance = new ThisActivityLifecycleAssist();
                }
            }
        }
        return sInstance;
    }
}