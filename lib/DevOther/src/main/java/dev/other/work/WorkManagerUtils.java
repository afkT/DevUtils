package dev.other.work;

import android.app.AlarmManager;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkQuery;
import androidx.work.WorkRequest;
import androidx.work.Worker;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
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
 *     Android 开发 WorkManager 详解
 *     @see <a href="https://www.cnblogs.com/guanxinjing/p/13278814.html"/>
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
 *     <p></p>
 *     获取 {@link WorkInfo} 可通过 {@link WorkInfo#getState()} 调用 isFinished() 判断 Worker 是否已完成
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

    // ===========
    // = get/set =
    // ===========

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

    // ====================
    // = 取消 Request 相关 =
    // ====================

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

    /**
     * 清除已执行 Worker
     * <pre>
     *     可通过 {@link #getWorkInfosByTagLiveData(String)} 校验
     *     执行多次相同 TAG 监听 List<WorkInfo> 数量, 接着调用该方法则能看到返回 List 长度为 0
     * </pre>
     * @return {@link Operation}
     */
    public Operation pruneWork() {
        return getWorkManager().pruneWork();
    }

    /**
     * 获取上次调用 cancelAllWork() 时间戳
     * <pre>
     *     如果没调用过则返回 0L
     * </pre>
     * @return {@link LiveData<Long>}
     */
    public LiveData<Long> getLastCancelAllTimeMillisLiveData() {
        return getWorkManager().getLastCancelAllTimeMillisLiveData();
    }

    /**
     * 获取上次调用 cancelAllWork() 时间戳
     * @return {@link ListenableFuture<Long>}
     */
    public ListenableFuture<Long> getLastCancelAllTimeMillis() {
        return getWorkManager().getLastCancelAllTimeMillis();
    }

    // ============
    // = WorkInfo =
    // ============

    /**
     * 通过 UUID 获取 WorkInfo
     * @param id {@link UUID}
     * @return {@link LiveData<WorkInfo>}
     */
    public LiveData<WorkInfo> getWorkInfoByIdLiveData(final UUID id) {
        if (id == null) return null;
        return getWorkManager().getWorkInfoByIdLiveData(id);
    }

    /**
     * 通过 UUID 获取 WorkInfo
     * @param id {@link UUID}
     * @return {@link ListenableFuture<WorkInfo>}
     */
    public ListenableFuture<WorkInfo> getWorkInfoById(final UUID id) {
        if (id == null) return null;
        return getWorkManager().getWorkInfoById(id);
    }

    /**
     * 通过 TAG 获取 WorkInfo
     * @param tag TAG
     * @return {@link LiveData<List<WorkInfo>>}
     */
    public LiveData<List<WorkInfo>> getWorkInfosByTagLiveData(final String tag) {
        if (TextUtils.isEmpty(tag)) return null;
        return getWorkManager().getWorkInfosByTagLiveData(tag);
    }

    /**
     * 通过 TAG 获取 WorkInfo
     * @param tag TAG
     * @return {@link ListenableFuture<List<WorkInfo>>}
     */
    public ListenableFuture<List<WorkInfo>> getWorkInfosByTag(final String tag) {
        if (TextUtils.isEmpty(tag)) return null;
        return getWorkManager().getWorkInfosByTag(tag);
    }

    /**
     * 通过 Worker 唯一名称获取 WorkInfo
     * @param uniqueWorkName Worker 唯一名称
     * @return {@link LiveData<List<WorkInfo>>}
     */
    public LiveData<List<WorkInfo>> getWorkInfosForUniqueWorkLiveData(final String uniqueWorkName) {
        if (TextUtils.isEmpty(uniqueWorkName)) return null;
        return getWorkManager().getWorkInfosForUniqueWorkLiveData(uniqueWorkName);
    }

    /**
     * 通过 Worker 唯一名称获取 WorkInfo
     * @param uniqueWorkName Worker 唯一名称
     * @return {@link ListenableFuture<List<WorkInfo>>}
     */
    public ListenableFuture<List<WorkInfo>> getWorkInfosForUniqueWork(final String uniqueWorkName) {
        if (TextUtils.isEmpty(uniqueWorkName)) return null;
        return getWorkManager().getWorkInfosForUniqueWork(uniqueWorkName);
    }

    /**
     * 自定义搜索条件获取 WorkInfo
     * @param workQuery {@link WorkQuery}
     * @return {@link LiveData<List<WorkInfo>>}
     */
    public LiveData<List<WorkInfo>> getWorkInfosLiveData(final WorkQuery workQuery) {
        if (workQuery == null) return null;
        return getWorkManager().getWorkInfosLiveData(workQuery);
    }

    /**
     * 自定义搜索条件获取 WorkInfo
     * @param workQuery {@link WorkQuery}
     * @return {@link ListenableFuture<List<WorkInfo>>}
     */
    public ListenableFuture<List<WorkInfo>> getWorkInfos(final WorkQuery workQuery) {
        if (workQuery == null) return null;
        return getWorkManager().getWorkInfos(workQuery);
    }

    // ===============
    // = 执行 Request =
    // ===============

    public Operation enqueue(List<? extends WorkRequest> requests) {
        return null;
    }

    public WorkContinuation beginWith(List<OneTimeWorkRequest> work) {
        return null;
    }

    public WorkContinuation beginUniqueWork(
            String uniqueWorkName,
            ExistingWorkPolicy existingWorkPolicy,
            List<OneTimeWorkRequest> work
    ) {
        return null;
    }

    public Operation enqueueUniqueWork(
            String uniqueWorkName,
            ExistingWorkPolicy existingWorkPolicy,
            List<OneTimeWorkRequest> work
    ) {
        return null;
    }

    public Operation enqueueUniquePeriodicWork(
            String uniqueWorkName,
            ExistingPeriodicWorkPolicy existingPeriodicWorkPolicy,
            PeriodicWorkRequest periodicWork
    ) {
        return null;
    }
}