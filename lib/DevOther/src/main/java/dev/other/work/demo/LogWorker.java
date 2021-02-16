package dev.other.work.demo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import dev.engine.log.DevLogEngine;
import dev.utils.DevFinal;

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

//        // 也可以输出数据
//        return Result.success(
//                createInputData("xx")
//        );
    }

    /**
     * 执行的代码
     */
    private void code() {
        DevLogEngine.getEngine().dTag(TAG, "LogWorker - code() => " + getLog());
    }

    // =============
    // = InputData =
    // =============

    /**
     * 创建 Data 用于 Worker 传输
     * @param log 打印日志内容
     * @return {@link Data}
     */
    private static Data createInputData(final String log) {
        Data.Builder builder = new Data.Builder();
        builder.putString(DevFinal.CONTENT, log);
        return builder.build();
    }

    /**
     * 获取传递的数据
     * @return Log Content
     */
    private String getLog() {
        return getInputData().getString(DevFinal.CONTENT);
    }

    // ===================
    // = 快捷创建 Request =
    // ===================

    /**
     * 快捷创建 {@link OneTimeWorkRequest.Builder}
     * @param log 传递待打印的日志内容
     * @return {@link OneTimeWorkRequest.Builder}
     */
    public static OneTimeWorkRequest.Builder builder(final String log) {
        return new OneTimeWorkRequest.Builder(LogWorker.class)
                .setInputData(createInputData(log))
                .addTag(TAG);
    }
}