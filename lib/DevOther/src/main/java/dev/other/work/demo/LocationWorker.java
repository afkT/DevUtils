package dev.other.work.demo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.NetworkType;
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
//            constraints.setRequiresDeviceIdle(true); // 在待机状态下执行，需要 API 23
//        }
        // 重复间隔必须大于或等于 PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
        // 而灵活间隔必须大于或等于 PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS
        // 每隔 15 分钟执行一次, 并设置 Worker 约束条件
        return new PeriodicWorkRequest.Builder(
                LocationWorker.class, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS
        ).setConstraints(
                constraints.build()
        ).addTag(TAG).build();
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