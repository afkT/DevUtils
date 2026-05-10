package dev.utils.app;

import android.app.job.JobScheduler;
import android.app.job.PendingJobReasonsInfo;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Collections;
import java.util.List;

import dev.utils.LogPrintUtils;

/**
 * detail: {@link JobScheduler} 工具类（含 Android 16 任务诊断 API）
 * @author Ttt
 * @see <a href="https://developer.android.com/about/versions/16/behavior-changes-all">所有应用在 Android 16 上的行为变更</a>
 * @see <a href="https://developer.android.com/about/versions/16/features#better-job-introspection">Better job introspection</a>
 */
public final class JobSchedulerUtils {

    private JobSchedulerUtils() {
    }

    // 日志 TAG
    private static final String TAG = JobSchedulerUtils.class.getSimpleName();

    /**
     * 获取 {@link JobScheduler}
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
     * Android 16+：返回某 Job 当前处于 pending 的多个原因（相对 {@link JobScheduler#getPendingJobReason(int)} 更完整）
     * @param context {@link Context}
     * @param jobId   Job id
     * @return 原因常量数组，失败或无服务时返回空数组
     */
    @RequiresApi(Build.VERSION_CODES.BAKLAVA)
    @NonNull
    public static int[] getPendingJobReasons(
            final Context context,
            final int jobId
    ) {
        JobScheduler js = getJobScheduler(context);
        if (js == null) {
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
    @RequiresApi(Build.VERSION_CODES.BAKLAVA)
    @NonNull
    public static List<PendingJobReasonsInfo> getPendingJobReasonsHistory(
            final Context context,
            final int jobId
    ) {
        JobScheduler js = getJobScheduler(context);
        if (js == null) {
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
}
