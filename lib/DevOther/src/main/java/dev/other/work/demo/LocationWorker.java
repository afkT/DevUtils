package dev.other.work.demo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

import dev.engine.log.DevLogEngine;
import dev.utils.common.DateUtils;

/**
 * detail: 模拟后台间隔性进行定位
 * @author Ttt
 * <pre>
 *     定时性获取当前位置信息
 *     可通过 {@link androidx.work.Data} 记录触发次数
 * </pre>
 */
public class LocationWorker
        extends Worker {

    // 日志 TAG
    private static final String TAG = LocationWorker.class.getSimpleName();

    public LocationWorker(
            @NonNull Context context,
            @NonNull WorkerParameters workerParams
    ) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // 获取当前位置
        DevLogEngine.getEngine().dTag(TAG, "触发定位方法 " + DateUtils.getDateNow());

        return Result.success();
    }

    // ===================
    // = 快捷创建 Request =
    // ===================

    /**
     * 快捷创建循环 Worker Request
     * <pre>
     *     BackoffPolicy 有两个值
     *     {@link BackoffPolicy#LINEAR}: 每次重试的时间线性增加, 比如第一次 10 秒, 第二次就是 20 秒
     *     {@link BackoffPolicy#EXPONENTIAL}: 每次重试时间指数增加, 比如第一次 10 秒, 后续次数为 20、40、80 秒
     * </pre>
     * @return {@link PeriodicWorkRequest}
     */
    public static PeriodicWorkRequest builder() {
        // 约束条件
        Constraints.Builder constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)  // 网络状态
                .setRequiresBatteryNotLow(true)                 // 不在电量不足时执行
//                .setRequiresCharging(true)                      // 在充电时执行
                .setRequiresStorageNotLow(true)                 // 不在存储容量不足时执行
                ;
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            constraints.setRequiresDeviceIdle(true); // 在待机状态下执行, 需要 API 23
//        }
        // 重复间隔必须大于或等于 PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
        // 而灵活间隔必须大于或等于 PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS
        // 每隔 15 分钟执行一次, 并设置 Worker 约束条件
        return new PeriodicWorkRequest.Builder(
                LocationWorker.class, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS
        ).setConstraints(
                constraints.build()
        ).keepResultsForAtLeast(10, TimeUnit.SECONDS) // 结果延迟保存时间
                // 退避策略 线性增加 10 秒重试
                .setBackoffCriteria(BackoffPolicy.LINEAR, OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .addTag(TAG)
                .build();
    }

    private PeriodicWorkRequest test() {
        // 以下是在每小时的最后 15 分钟内运行的定期工作的示例
        return new PeriodicWorkRequest.Builder(
                LocationWorker.class,
                1, TimeUnit.HOURS,
                15, TimeUnit.MINUTES
        ).build();
    }
}