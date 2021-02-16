package dev.other.work;

import android.app.AlarmManager;
import android.text.TextUtils;

import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
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
 *     <p></p>
 *     WorkManager 相关类:
 *     {@link Worker}: 继承此类并重写 doWork() 方法, 在此处将要执行的实际工作的代码放在后台
 *     {@link WorkRequest}: WorkRequest ( 及其子类 ) 则定义工作 ( Worker ) 运行方式和时间
 *     {@link WorkManager}: 安排您的 WorkRequest 并使其运行
 *     <p></p>
 *     WorkRequest 内置子类:
 *     {@link OneTimeWorkRequest}: WorkRequest 只会执行一次
 *     {@link PeriodicWorkRequest}: WorkRequest 一个周期将重复执行
 *     <p></p>
 *     tips:
 *     WorkManager 适用于可延期工作, 即不需要立即运行但需要可靠运行的工作, 即使退出应用或重启设备也不影响工作的执行
 *     可以配合 {@link AlarmManager} ( {@link dev.utils.app.AlarmUtils} ) 触发进行精确时间执行 Worker
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
     * 设置 WorkManager
     * @param workManager {@link WorkManager}
     * @return {@link WorkManagerUtils}
     */
    public WorkManagerUtils setWorkManager(final WorkManager workManager) {
        if (workManager != null) this.mWorkManager = workManager;
        return this;
    }

    // ===========
    // = 取消任务 =
    // ===========

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
     * 通过 UUID 取消未完成 Worker
     * @param id {@link UUID}
     * @return {@link Operation}
     */
    public Operation cancelWorkById(final UUID id) {
        if (id == null) return null;
        return getWorkManager().cancelWorkById(id);
    }

    /**
     * 通过 TAG 取消未完成 Worker
     * @param tag TAG
     * @return {@link Operation}
     */
    public Operation cancelAllWorkByTag(final String tag) {
        if (TextUtils.isEmpty(tag)) return null;
        return getWorkManager().cancelAllWorkByTag(tag);
    }

    /**
     * 通过 Worker 唯一名称取消未完成 Worker
     * @param uniqueWorkName Worker 唯一名称
     * @return {@link Operation}
     */
    public Operation cancelUniqueWork(final String uniqueWorkName) {
        if (TextUtils.isEmpty(uniqueWorkName)) return null;
        return getWorkManager().cancelUniqueWork(uniqueWorkName);
    }
}