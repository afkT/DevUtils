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

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import dev.DevUtils;
import dev.utils.common.CollectionUtils;

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

    public Operation enqueue(final WorkRequest work) {
        return enqueue(Collections.singletonList(work));
    }

    /**
     * 加入 Worker 执行队列
     * @param work {@link WorkRequest} 集合
     * @return {@link Operation}
     */
    public Operation enqueue(final List<? extends WorkRequest> work) {
        if (CollectionUtils.isEmpty(work)) return null;
        return getWorkManager().enqueue(work);
    }

    // =

    public Operation enqueueUniqueWork(
            final String uniqueWorkName,
            final ExistingWorkPolicy existingWorkPolicy,
            final OneTimeWorkRequest work
    ) {
        return enqueueUniqueWork(
                uniqueWorkName,
                existingWorkPolicy,
                Collections.singletonList(work));
    }

    /**
     * 加入唯一一次性 Worker 执行队列
     * @param uniqueWorkName     Worker 唯一名称
     * @param existingWorkPolicy uniqueWorkName 发生冲突 ( 重复 ) 时的策略
     * @param work               一次性 Worker 集合
     * @return {@link Operation}
     */
    public Operation enqueueUniqueWork(
            final String uniqueWorkName,
            final ExistingWorkPolicy existingWorkPolicy,
            final List<OneTimeWorkRequest> work
    ) {
        if (TextUtils.isEmpty(uniqueWorkName)) return null;
        if (existingWorkPolicy == null) return null;
        if (CollectionUtils.isEmpty(work)) return null;
        return getWorkManager().enqueueUniqueWork(
                uniqueWorkName, existingWorkPolicy, work
        );
    }

    // =

    /**
     * 加入唯一定期 Worker 执行队列
     * @param uniqueWorkName             Worker 唯一名称
     * @param existingPeriodicWorkPolicy uniqueWorkName 发生冲突 ( 重复 ) 时的策略
     * @param periodicWork               定期 Worker Request
     * @return {@link Operation}
     */
    public Operation enqueueUniquePeriodicWork(
            final String uniqueWorkName,
            final ExistingPeriodicWorkPolicy existingPeriodicWorkPolicy,
            final PeriodicWorkRequest periodicWork
    ) {
        if (TextUtils.isEmpty(uniqueWorkName)) return null;
        if (existingPeriodicWorkPolicy == null) return null;
        if (periodicWork == null) return null;
        return getWorkManager().enqueueUniquePeriodicWork(
                uniqueWorkName, existingPeriodicWorkPolicy, periodicWork
        );
    }

    // ================
    // = Worker 工作链 =
    // ================

    public WorkContinuation beginWith(final OneTimeWorkRequest work) {
        return beginWith(Collections.singletonList(work));
    }

    /**
     * 创建 Worker 工作链
     * <pre>
     *     工作链用于指定多个依存任务并定义这些任务的运行顺序
     *     可通过返回的 {@link WorkContinuation} 调用 {@link WorkContinuation#then(OneTimeWorkRequest)} 加入队列
     *     最终调用 {@link WorkContinuation#enqueue()} 对工作链执行操作
     *     <p></p>
     *     每次调用 WorkContinuation.then(...) 都会返回一个新的 WorkContinuation 实例
     *     如果添加了 OneTimeWorkRequest 实例的 List, 这些请求可能会并行运行
     * </pre>
     * @param work {@link OneTimeWorkRequest} 集合
     * @return {@link WorkContinuation}
     */
    public WorkContinuation beginWith(final List<OneTimeWorkRequest> work) {
        if (CollectionUtils.isEmpty(work)) return null;
        return getWorkManager().beginWith(work);
    }

    // =

    public final WorkContinuation beginUniqueWork(
            final String uniqueWorkName,
            final ExistingWorkPolicy existingWorkPolicy,
            final OneTimeWorkRequest work
    ) {
        return beginUniqueWork(uniqueWorkName, existingWorkPolicy, Collections.singletonList(work));
    }

    /**
     * 创建唯一性 Worker 工作链
     * @param uniqueWorkName     Worker 唯一名称
     * @param existingWorkPolicy uniqueWorkName 发生冲突 ( 重复 ) 时的策略
     * @param work               {@link OneTimeWorkRequest} 集合
     * @return {@link Operation}
     */
    public WorkContinuation beginUniqueWork(
            final String uniqueWorkName,
            final ExistingWorkPolicy existingWorkPolicy,
            final List<OneTimeWorkRequest> work
    ) {
        if (TextUtils.isEmpty(uniqueWorkName)) return null;
        if (existingWorkPolicy == null) return null;
        if (CollectionUtils.isEmpty(work)) return null;
        return getWorkManager().beginUniqueWork(
                uniqueWorkName, existingWorkPolicy, work
        );
    }
}