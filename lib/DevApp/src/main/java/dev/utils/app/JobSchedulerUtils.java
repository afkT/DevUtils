package dev.utils.app;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.app.job.JobWorkItem;
import android.app.job.PendingJobReasonsInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.utils.LogPrintUtils;

/**
 * detail: JobScheduler 工具类
 * @author Ttt
 * <pre>
 *     需在 Manifest 中注册 {@link android.app.job.JobService} 子类，例如:
 *     {@code <service android:name=".MyJobService"
 *         android:permission="android.permission.BIND_JOB_SERVICE"
 *         android:exported="true">}
 *     {@code <intent-filter><action android:name="android.app.job.JobService" /></intent-filter></service>}
 *     <p></p>
 *     官方文档:
 *     @see <a href="https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started">JobScheduler 入门</a>
 *     @see <a href="https://developer.android.com/develop/background-work/background-tasks/optimize-battery">任务与电量</a>
 *     Android 16 Job 配额与诊断:
 *     @see <a href="https://developer.android.com/about/versions/16/behavior-changes-all">所有应用在 Android 16 上的行为变更</a>
 *     Android 17 {@link #getPendingJobReasonStats(Context, int)}:
 *     @see <a href="https://developer.android.com/about/versions/17/features">Android 17 Features</a>
 * </pre>
 */
public final class JobSchedulerUtils {

    private JobSchedulerUtils() {
    }

    // 日志 TAG
    private static final String TAG = JobSchedulerUtils.class.getSimpleName();

    // ===============
    // = JobScheduler =
    // ===============

    /**
     * 获取系统 JobScheduler
     * @param context {@link Context}
     * @return {@link JobScheduler}
     */
    public static JobScheduler getJobScheduler(final Context context) {
        if (context == null) {
            return null;
        }
        try {
            return (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "getJobScheduler");
            return null;
        }
    }

    /**
     * Android 14 (API 34)+：获取指定命名空间下的 JobScheduler 实例
     * <pre>
     *     前后空白会被系统 trim；trim 后为空串非法，本方法会返回 {@code null} 并打日志。
     * </pre>
     * @param context   {@link Context}
     * @param namespace 命名空间
     * @return 实例；低版本、非法参数或异常时返回 {@code null}
     */
    @Nullable
    public static JobScheduler getJobSchedulerForNamespace(
            final Context context,
            final String namespace
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            LogPrintUtils.wTag(TAG, "getJobSchedulerForNamespace requires API 34+");
            return null;
        }
        JobScheduler base = getJobScheduler(context);
        if (base == null || namespace == null) {
            return null;
        }
        String ns = namespace.trim();
        if (ns.isEmpty()) {
            LogPrintUtils.wTag(TAG, "getJobSchedulerForNamespace: empty namespace after trim");
            return null;
        }
        try {
            return base.forNamespace(ns);
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "getJobSchedulerForNamespace");
            return null;
        }
    }

    /**
     * 解析用于调度的 JobScheduler ( 默认或命名空间 )
     * @param context   {@link Context}
     * @param namespace {@code null} 或空串表示默认命名空间；非空且 API 34+ 时使用 {@link #getJobSchedulerForNamespace(Context, String)}
     * @return 默认或命名空间对应的 {@link JobScheduler}；{@code context == null} 或无法获取时返回 {@code null}
     */
    @Nullable
    private static JobScheduler resolveScheduler(
            final Context context,
            @Nullable final String namespace
    ) {
        if (context == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
                && !TextUtils.isEmpty(namespace)) {
            return getJobSchedulerForNamespace(context, namespace);
        }
        if (!TextUtils.isEmpty(namespace) && Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            LogPrintUtils.wTag(TAG, "resolveScheduler: namespace ignored on API < 34");
        }
        return getJobScheduler(context);
    }

    // ==========
    // = 调度结果 =
    // ==========

    /**
     * 判断调度结果是否成功
     * <pre>
     *     用于 {@link JobScheduler#schedule(JobInfo)} / {@link JobScheduler#enqueue(JobInfo, JobWorkItem)} 的返回值判断
     * </pre>
     * @param result {@link JobScheduler#RESULT_SUCCESS} 等
     * @return {@code true} 调度成功, {@code false} 调度失败
     */
    public static boolean isScheduleSuccess(final int result) {
        return result == JobScheduler.RESULT_SUCCESS;
    }

    // ===================
    // = JobInfo.Builder =
    // ===================

    /**
     * 创建 JobInfo.Builder ( 仅绑定 jobId 与 JobService 组件，其余约束请自行链式设置 )
     * @param context    {@link Context}
     * @param jobId      应用内唯一 Job id
     * @param jobService 继承 {@link JobService} 的 Service 类
     * @return {@link JobInfo.Builder}；{@code context} / {@code jobService} 为 null 时返回 {@code null}
     */
    @Nullable
    public static JobInfo.Builder newJobBuilder(
            final Context context,
            final int jobId,
            final Class<? extends JobService> jobService
    ) {
        if (context == null || jobService == null) {
            return null;
        }
        try {
            ComponentName cn = new ComponentName(context.getApplicationContext(), jobService);
            return new JobInfo.Builder(jobId, cn);
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "newJobBuilder");
            return null;
        }
    }

    // ===============================
    // = schedule / enqueue / cancel =
    // ===============================

    /**
     * 提交或更新 Job ( 默认命名空间 )
     * @param context {@link Context}
     * @param job     {@link JobInfo}
     * @return {@link JobScheduler#RESULT_SUCCESS} 或 {@link JobScheduler#RESULT_FAILURE}
     */
    public static int schedule(
            final Context context,
            final JobInfo job
    ) {
        return schedule(context, job, null);
    }

    /**
     * 提交或更新 Job ( 可选命名空间，仅 API 34+ 生效 )
     * @param context   {@link Context}
     * @param job       {@link JobInfo}
     * @param namespace {@code null} 或空串为默认命名空间
     * @return {@link JobScheduler#RESULT_SUCCESS} 或 {@link JobScheduler#RESULT_FAILURE}
     */
    public static int schedule(
            final Context context,
            final JobInfo job,
            @Nullable final String namespace
    ) {
        if (job == null) {
            return JobScheduler.RESULT_FAILURE;
        }
        JobScheduler js = resolveScheduler(context, namespace);
        if (js == null) {
            return JobScheduler.RESULT_FAILURE;
        }
        try {
            return js.schedule(job);
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "schedule");
            return JobScheduler.RESULT_FAILURE;
        }
    }

    /**
     * 一次性 Job：仅设置最晚执行时间 ( 系统在此时间前择机执行 )
     * @param context    {@link Context}
     * @param jobId      Job id
     * @param jobService {@link JobService} 实现类
     * @param deadlineMs 相对当前的 override deadline ( 毫秒 )
     * @return {@link JobScheduler#RESULT_SUCCESS} 等
     */
    public static int scheduleOnceWithDeadline(
            final Context context,
            final int jobId,
            final Class<? extends JobService> jobService,
            final long deadlineMs
    ) {
        JobInfo.Builder builder = newJobBuilder(context, jobId, jobService);
        if (builder == null) {
            return JobScheduler.RESULT_FAILURE;
        }
        try {
            return schedule(context, builder.setOverrideDeadline(deadlineMs).build());
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "scheduleOnceWithDeadline");
            return JobScheduler.RESULT_FAILURE;
        }
    }

    /**
     * 一次性 Job：最小延迟 + 最晚执行时间
     * @param context      {@link Context}
     * @param jobId        Job id
     * @param jobService   {@link JobService} 实现类
     * @param minLatencyMs 最小延迟 ( 毫秒 )
     * @param deadlineMs   override deadline ( 毫秒 )
     * @return {@link JobScheduler#RESULT_SUCCESS} 等
     */
    public static int scheduleOnceWithLatencyAndDeadline(
            final Context context,
            final int jobId,
            final Class<? extends JobService> jobService,
            final long minLatencyMs,
            final long deadlineMs
    ) {
        JobInfo.Builder builder = newJobBuilder(context, jobId, jobService);
        if (builder == null) {
            return JobScheduler.RESULT_FAILURE;
        }
        try {
            return schedule(
                    context, builder.setMinimumLatency(minLatencyMs)
                            .setOverrideDeadline(deadlineMs)
                            .build()
            );
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "scheduleOnceWithLatencyAndDeadline");
            return JobScheduler.RESULT_FAILURE;
        }
    }

    /**
     * 周期 Job ( 固定间隔 )
     * @param context    {@link Context}
     * @param jobId      Job id
     * @param jobService {@link JobService} 实现类
     * @param intervalMs 周期间隔 ( 毫秒 ) ，需满足系统最小间隔要求
     * @return {@link JobScheduler#RESULT_SUCCESS} 等
     */
    public static int schedulePeriodic(
            final Context context,
            final int jobId,
            final Class<? extends JobService> jobService,
            final long intervalMs
    ) {
        return schedulePeriodic(context, jobId, jobService, intervalMs, 0L, null);
    }

    /**
     * 周期 Job ( 可选 flex 窗口，API 24+ 生效；低版本忽略 flex )
     * @param context    {@link Context}
     * @param jobId      Job id
     * @param jobService {@link JobService} 实现类
     * @param intervalMs 周期间隔 ( 毫秒 )
     * @param flexMs     flex ( 毫秒 ) ；{@code <= 0} 表示使用 {@link JobInfo.Builder#setPeriodic(long)}
     * @param namespace  命名空间，API 34+ 可选
     * @return {@link JobScheduler#RESULT_SUCCESS} 等
     */
    public static int schedulePeriodic(
            final Context context,
            final int jobId,
            final Class<? extends JobService> jobService,
            final long intervalMs,
            final long flexMs,
            @Nullable final String namespace
    ) {
        JobInfo.Builder builder = newJobBuilder(context, jobId, jobService);
        if (builder == null) {
            return JobScheduler.RESULT_FAILURE;
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && flexMs > 0L) {
                builder.setPeriodic(intervalMs, flexMs);
            } else {
                if (flexMs > 0L && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    LogPrintUtils.wTag(TAG, "schedulePeriodic: flex ignored on API < 24");
                }
                builder.setPeriodic(intervalMs);
            }
            return schedule(context, builder.build(), namespace);
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "schedulePeriodic");
            return JobScheduler.RESULT_FAILURE;
        }
    }

    /**
     * Android 12 (API 31)+：在已配置的 JobInfo.Builder 上设置是否加急并提交调度
     * <pre>
     *     请先通过 {@link #newJobBuilder(Context, int, Class)} 或其它方式配置网络、充电等约束，再调用本方法。
     * </pre>
     * @param context   {@link Context}
     * @param builder   非空的 {@link JobInfo.Builder}
     * @param expedited {@link JobInfo.Builder#setExpedited(boolean)}
     * @param namespace 命名空间，API 34+ 可选
     * @return {@link JobScheduler#RESULT_SUCCESS} 等；{@code builder == null} 或低版本返回 {@link JobScheduler#RESULT_FAILURE}
     */
    public static int scheduleWithExpeditedFlag(
            final Context context,
            final JobInfo.Builder builder,
            final boolean expedited,
            @Nullable final String namespace
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            LogPrintUtils.wTag(TAG, "scheduleWithExpeditedFlag requires API 31+");
            return JobScheduler.RESULT_FAILURE;
        }
        if (builder == null) {
            return JobScheduler.RESULT_FAILURE;
        }
        try {
            builder.setExpedited(expedited);
            return schedule(context, builder.build(), namespace);
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "scheduleWithExpeditedFlag");
            return JobScheduler.RESULT_FAILURE;
        }
    }

    /**
     * Android 8.0 (API 26)+：向已调度或运行中的 Job 投递 JobWorkItem
     * @param context {@link Context}
     * @param job     {@link JobInfo}
     * @param work    {@link JobWorkItem}
     * @return {@link JobScheduler#RESULT_SUCCESS} 等
     */
    public static int enqueue(
            final Context context,
            final JobInfo job,
            final JobWorkItem work
    ) {
        return enqueue(context, job, work, null);
    }

    /**
     * Android 8.0+：enqueue ( 可选命名空间，API 34+ )
     * @param context   {@link Context}
     * @param job       {@link JobInfo}
     * @param work      {@link JobWorkItem}
     * @param namespace {@code null} 或空串为默认命名空间；非空且 API 34+ 时使用对应命名空间实例
     * @return {@link JobScheduler#RESULT_SUCCESS} 或 {@link JobScheduler#RESULT_FAILURE}
     */
    public static int enqueue(
            final Context context,
            final JobInfo job,
            final JobWorkItem work,
            @Nullable final String namespace
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            LogPrintUtils.wTag(TAG, "enqueue requires API 26+");
            return JobScheduler.RESULT_FAILURE;
        }
        if (job == null || work == null) {
            return JobScheduler.RESULT_FAILURE;
        }
        JobScheduler js = resolveScheduler(context, namespace);
        if (js == null) {
            return JobScheduler.RESULT_FAILURE;
        }
        try {
            return js.enqueue(job, work);
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "enqueue");
            return JobScheduler.RESULT_FAILURE;
        }
    }

    /**
     * Android 8.0+：用 Intent 快速构造 JobWorkItem 并入队
     * @param context    {@link Context}
     * @param job        {@link JobInfo}
     * @param workIntent 工作描述 ( 注意持久化 Job 下 Intent 持久化限制，参见官方文档 )
     * @return {@link JobScheduler#RESULT_SUCCESS} 等
     */
    public static int enqueueWithIntent(
            final Context context,
            final JobInfo job,
            final Intent workIntent
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            LogPrintUtils.wTag(TAG, "enqueueWithIntent requires API 26+");
            return JobScheduler.RESULT_FAILURE;
        }
        if (workIntent == null) {
            return JobScheduler.RESULT_FAILURE;
        }
        try {
            return enqueue(context, job, new JobWorkItem(workIntent));
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "enqueueWithIntent");
            return JobScheduler.RESULT_FAILURE;
        }
    }

    /**
     * 取消指定 Job ( 默认命名空间 )
     * @param context {@link Context}
     * @param jobId   与 {@link JobInfo.Builder#Builder(int, android.content.ComponentName)} 中 id 一致
     * @return {@code true} 已调用取消且无异常；{@code false} 服务不可用或发生异常
     */
    public static boolean cancel(
            final Context context,
            final int jobId
    ) {
        return cancel(context, jobId, null);
    }

    /**
     * 取消指定 Job ( 可选命名空间，API 34+ )
     * @param context   {@link Context}
     * @param jobId     Job id
     * @param namespace {@code null} 或空串为默认命名空间；非空且 API 34+ 时在对应命名空间上取消
     * @return {@code true} 已调用取消且无异常；{@code false} 服务不可用或发生异常
     */
    public static boolean cancel(
            final Context context,
            final int jobId,
            @Nullable final String namespace
    ) {
        JobScheduler js = resolveScheduler(context, namespace);
        if (js == null) {
            return false;
        }
        try {
            js.cancel(jobId);
            return true;
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "cancel");
            return false;
        }
    }

    /**
     * 取消当前命名空间下本应用已调度的全部 Job ( API 34+ 仅当前 namespace；更早版本为应用全部任务 )
     * @param context {@link Context}
     * @return {@code true} 已调用 {@link JobScheduler#cancelAll()} 且无异常；{@code false} 失败
     */
    public static boolean cancelAll(final Context context) {
        return cancelAll(context, null);
    }

    /**
     * 取消全部 Job ( 可选从命名空间实例上调用，API 34+ )
     * @param context   {@link Context}
     * @param namespace {@code null} 或空串为默认命名空间；非空且 API 34+ 时仅取消该命名空间下任务
     * @return {@code true} 已调用 {@link JobScheduler#cancelAll()} 且无异常；{@code false} 失败
     */
    public static boolean cancelAll(
            final Context context,
            @Nullable final String namespace
    ) {
        JobScheduler js = resolveScheduler(context, namespace);
        if (js == null) {
            return false;
        }
        try {
            js.cancelAll();
            return true;
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "cancelAll");
            return false;
        }
    }

    /**
     * Android 14+：取消 所有命名空间 下本应用已调度的 Job
     * @param context {@link Context}
     * @return {@code true} 已调用 {@link JobScheduler#cancelInAllNamespaces()} 且无异常；{@code false} 失败或 API 低于 34
     */
    public static boolean cancelInAllNamespaces(final Context context) {
        JobScheduler js = getJobScheduler(context);
        if (js == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            LogPrintUtils.wTag(TAG, "cancelInAllNamespaces requires API 34+");
            return false;
        }
        try {
            js.cancelInAllNamespaces();
            return true;
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "cancelInAllNamespaces");
            return false;
        }
    }

    // =======
    // = 查询 =
    // =======

    /**
     * 当前命名空间下所有待处理 ( 含已启动 ) 的 Job 列表
     * @param context {@link Context}
     * @return 任务列表；无服务或异常时返回不可变空列表
     */
    @NonNull
    public static List<JobInfo> getAllPendingJobs(final Context context) {
        return getAllPendingJobs(context, null);
    }

    /**
     * 待处理 Job 列表 ( 可选命名空间，API 34+ )
     * @param context   {@link Context}
     * @param namespace {@code null} 或空串为默认命名空间；非空且 API 34+ 时查询该命名空间
     * @return 任务列表；无服务或异常时返回不可变空列表
     */
    @NonNull
    public static List<JobInfo> getAllPendingJobs(
            final Context context,
            @Nullable final String namespace
    ) {
        JobScheduler js = resolveScheduler(context, namespace);
        if (js == null) {
            return Collections.emptyList();
        }
        try {
            List<JobInfo> list = js.getAllPendingJobs();
            return list != null ? list : Collections.emptyList();
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "getAllPendingJobs");
            return Collections.emptyList();
        }
    }

    /**
     * Android 14+：按命名空间返回待处理 Job ( 多 namespace 一张图 )
     * @param context {@link Context}
     * @return 命名空间到任务列表的映射；API 低于 34、无服务或异常时返回不可变空 Map
     */
    @NonNull
    public static Map<String, List<JobInfo>> getPendingJobsInAllNamespaces(final Context context) {
        JobScheduler js = getJobScheduler(context);
        if (js == null) {
            return Collections.emptyMap();
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            return Collections.emptyMap();
        }
        try {
            Map<String, List<JobInfo>> map = js.getPendingJobsInAllNamespaces();
            return map != null ? map : Collections.emptyMap();
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "getPendingJobsInAllNamespaces");
            return Collections.emptyMap();
        }
    }

    /**
     * 查询指定 id 的 Job 描述；未找到返回 null
     * @param context {@link Context}
     * @param jobId   Job id
     * @return {@link JobInfo}；不存在、API 低于 24、无服务或异常时返回 {@code null}
     */
    @Nullable
    public static JobInfo getPendingJob(
            final Context context,
            final int jobId
    ) {
        return getPendingJob(context, jobId, null);
    }

    /**
     * 查询指定 id 的 Job ( 可选命名空间，API 34+ )
     * @param context   {@link Context}
     * @param jobId     Job id
     * @param namespace {@code null} 或空串为默认命名空间；非空且 API 34+ 时从对应命名空间查询
     * @return {@link JobInfo}；不存在、API 低于 24、无服务或异常时返回 {@code null}
     */
    @Nullable
    public static JobInfo getPendingJob(
            final Context context,
            final int jobId,
            @Nullable final String namespace
    ) {
        JobScheduler js = resolveScheduler(context, namespace);
        if (js == null) {
            return null;
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return js.getPendingJob(jobId);
            }
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "getPendingJob");
        }
        return null;
    }

    /**
     * 是否已存在指定 id 的待调度 / 运行中 Job
     * @param context {@link Context}
     * @param jobId   Job id
     * @return {@code true} 存在对应 {@link JobInfo}, {@code false} 不存在
     */
    public static boolean hasPendingJob(
            final Context context,
            final int jobId
    ) {
        return getPendingJob(context, jobId) != null;
    }

    /**
     * Android 14+：返回某 Job 当前未执行的一个 pending 原因 ( 多原因时仅其一 )
     * @param context {@link Context}
     * @param jobId   Job id
     * @return 原因常量；低版本返回 {@link JobScheduler#PENDING_JOB_REASON_UNDEFINED}
     */
    public static int getPendingJobReason(
            final Context context,
            final int jobId
    ) {
        JobScheduler js = getJobScheduler(context);
        if (js == null) {
            return JobScheduler.PENDING_JOB_REASON_UNDEFINED;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            return JobScheduler.PENDING_JOB_REASON_UNDEFINED;
        }
        try {
            return js.getPendingJobReason(jobId);
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "getPendingJobReason");
            return JobScheduler.PENDING_JOB_REASON_UNDEFINED;
        }
    }

    /**
     * Android 14+：当前应用是否持有运行 user-initiated jobs 所需权限
     * @param context {@link Context}
     * @return {@code true} 持有权限；API 低于 34、无服务、无权限或异常时返回 {@code false}
     */
    public static boolean canRunUserInitiatedJobs(final Context context) {
        JobScheduler js = getJobScheduler(context);
        if (js == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            return false;
        }
        try {
            return js.canRunUserInitiatedJobs();
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "canRunUserInitiatedJobs");
            return false;
        }
    }

    /**
     * Android 16+：返回某 Job 当前处于 pending 的多个原因
     * <pre>
     *      相对 {@link JobScheduler#getPendingJobReason(int)} 更完整
     * </pre>
     * @param context {@link Context}
     * @param jobId   Job id
     * @return 原因常量数组，失败或无服务时返回空数组
     */
    @NonNull
    public static int[] getPendingJobReasons(
            final Context context,
            final int jobId
    ) {
        JobScheduler js = getJobScheduler(context);
        if (js == null) {
            return new int[0];
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.BAKLAVA) {
            return new int[0];
        }
        try {
            int[] reasons = js.getPendingJobReasons(jobId);
            return reasons != null ? reasons : new int[0];
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "getPendingJobReasons");
            return new int[0];
        }
    }

    /**
     * Android 16+：返回某 Job 最近约束变化导致 pending 的历史记录，便于排查 Job 未执行问题
     * @param context {@link Context}
     * @param jobId   Job id
     * @return 历史列表，失败时返回空列表
     */
    @NonNull
    public static List<PendingJobReasonsInfo> getPendingJobReasonsHistory(
            final Context context,
            final int jobId
    ) {
        JobScheduler js = getJobScheduler(context);
        if (js == null) {
            return Collections.emptyList();
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.BAKLAVA) {
            return Collections.emptyList();
        }
        try {
            List<PendingJobReasonsInfo> list = js.getPendingJobReasonsHistory(jobId);
            return list != null ? list : Collections.emptyList();
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "getPendingJobReasonsHistory");
            return Collections.emptyList();
        }
    }

    /**
     * Android 17+：返回某 Job 各 pending 原因及其累计 pending 时长 ( 毫秒 )
     * <pre>
     *     等价 {@link JobScheduler#getPendingJobReasonStats(int)}，
     *     便于一次性获取原因与时长，无需再调 {@link #getPendingJobReasonsHistory(Context, int)} 累加。
     * </pre>
     * @param context {@link Context}
     * @param jobId   Job id
     * @return key 为 pending 原因常量、value 为累计 pending 毫秒；低版本或失败时返回空 {@link Map}
     */
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    @NonNull
    public static Map<Integer, Long> getPendingJobReasonStats(
            final Context context,
            final int jobId
    ) {
        JobScheduler js = getJobScheduler(context);
        if (js == null) {
            return Collections.emptyMap();
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.CINNAMON_BUN) {
            return Collections.emptyMap();
        }
        try {
            Map<Integer, Duration> stats = js.getPendingJobReasonStats(jobId);
            if (stats == null || stats.isEmpty()) {
                return Collections.emptyMap();
            }
            Map<Integer, Long> millisMap = new HashMap<>(stats.size());
            for (Map.Entry<Integer, Duration> entry : stats.entrySet()) {
                Duration duration = entry.getValue();
                millisMap.put(
                        entry.getKey(),
                        duration != null ? duration.toMillis() : 0L
                );
            }
            return millisMap;
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "getPendingJobReasonStats");
            return Collections.emptyMap();
        }
    }
}
