package dev.other.work;

import android.text.TextUtils;

import androidx.work.Operation;
import androidx.work.WorkManager;
import androidx.work.Worker;

import java.util.UUID;

import dev.DevUtils;

/**
 * detail: WorkManager 工具类
 * @author Ttt
 * <pre>
 *     使用 WorkManager 调度任务
 *     @see <a href="https://developer.android.google.cn/topic/libraries/architecture/workmanager"/>
 *     @see <a href="https://developer.android.google.cn/topic/libraries/architecture/workmanager/basics"/>
 *     WorkManager
 *     @see <a href="https://developer.android.google.cn/jetpack/androidx/releases/work"/>
 * </pre>
 */
public final class WorkManagerUtils {

    private WorkManagerUtils() {
    }

    // 日志 TAG
    private static final String TAG = WorkManagerUtils.class.getSimpleName();

    // WorkManager
    private                 WorkManager      mWorkManager;
    // WorkManagerUtils 实例
    private static volatile WorkManagerUtils sInstance;

    /**
     * 获取 WorkManagerUtils 实例
     * @return {@link WorkManagerUtils}
     */
    public static WorkManagerUtils getInstance() {
        if (sInstance == null) {
            synchronized (WorkManagerUtils.class) {
                if (sInstance == null) {
                    sInstance = new WorkManagerUtils();
                }
            }
        }
        return sInstance;
    }

    // =

    /**
     * 获取 WorkManager
     * @return {@link WorkManager}
     */
    public WorkManager getWorkManager() {
        if (mWorkManager != null) return mWorkManager;
        mWorkManager = WorkManager.getInstance(DevUtils.getContext());
        return mWorkManager;
    }

    /**
     * 取消所有未完成的 Worker
     * <pre>
     *     防止影响其他 {@link Worker}, 推荐使用其他取消方法
     * </pre>
     * @return {@link Operation}
     */
    public Operation cancelAllWork() {
        return getWorkManager().cancelAllWork();
    }

    /**
     * 通过 UUID 取消未完成任务
     * @param id {@link UUID}
     * @return {@link Operation}
     */
    public Operation cancelWorkById(final UUID id) {
        if (id == null) return null;
        return getWorkManager().cancelWorkById(id);
    }

    /**
     * 通过 TAG 取消未完成任务
     * @param tag TAG
     * @return {@link Operation}
     */
    public Operation cancelAllWorkByTag(final String tag) {
        if (TextUtils.isEmpty(tag)) return null;
        return getWorkManager().cancelAllWorkByTag(tag);
    }

    /**
     * 通过 Worker 唯一名称取消未完成任务
     * @param uniqueWorkName Worker 唯一名称
     * @return {@link Operation}
     */
    public Operation cancelUniqueWork(final String uniqueWorkName) {
        if (TextUtils.isEmpty(uniqueWorkName)) return null;
        return getWorkManager().cancelUniqueWork(uniqueWorkName);
    }
}