package dev.other.work.demo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import dev.engine.log.DevLogEngine;

/**
 * detail: 打印日志 Worker ( 用于演示 )
 * @author Ttt
 * <pre>
 *     从 doWork() 返回的 Result 会通知 WorkManager 服务工作是否成功, 以及工作失败时是否应重试工作
 *     Result.success(): 工作成功完成
 *     Result.failure(): 工作失败
 *     Result.retry(): 工作失败, 应根据其重试政策在其他时间尝试
 * </pre>
 */
public class LogWorker
        extends Worker {

    // 日志 TAG
    private static final String TAG = LogWorker.class.getSimpleName();

    public LogWorker(
            @NonNull Context context,
            @NonNull WorkerParameters workerParams
    ) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // 执行的代码
        code();

        // 表示执行成功
        return Result.success();
    }

    /**
     * 执行的代码
     */
    private void code() {
        DevLogEngine.getEngine().dTag(TAG, "LogWorker - code");
    }
}