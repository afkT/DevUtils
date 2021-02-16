package dev.other.work.demo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

import dev.engine.log.DevLogEngine;
import dev.other.work.WorkManagerUtils;
import dev.utils.DevFinal;

/**
 * detail: 打印日志 Worker ( 用于演示 )
 * @author Ttt
 * <pre>
 *     从 doWork() 返回的 Result 会通知 WorkManager 服务工作是否成功, 以及工作失败时是否应重试工作
 *     Result.success(): 工作成功完成
 *     Result.failure(): 工作失败
 *     Result.retry(): 工作失败, 根据其重试政策在其他时间尝试
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

    /**
     * 只会 worker 在运行时执行 onStopped
     * 已经执行完成去取消任务是不会触发 onStopped 方法的
     */
    @Override
    public void onStopped() {
        super.onStopped();

        DevLogEngine.getEngine().dTag(TAG, "LogWorker - onStopped()");
    }

    @NonNull
    @Override
    public Result doWork() {
        // 执行的代码
        code();

//        // 表示执行成功
//        return Result.success();

        // 也可以输出数据
        return Result.success(
                createData("xx")
        );
    }

    /**
     * 执行的代码
     */
    private void code() {
        DevLogEngine.getEngine().dTag(TAG, "LogWorker - code() => " + getLog());

        // 模拟操作进度, 并进行通知
        for (int i = 0; i <= 100; i++) {
            Data data = new Data.Builder().putInt(
                    DevFinal.PROGRESS, i
            ).build();
            setProgressAsync(data);
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }
    }

    // =============
    // = InputData =
    // =============

    /**
     * 创建 Data 用于 Worker 传输
     * @param log 打印日志内容
     * @return {@link Data}
     */
    private static Data createData(final String log) {
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
                .setInputData(createData(log))
                .keepResultsForAtLeast(10, TimeUnit.SECONDS) // 结果延迟保存时间
                .addTag(TAG);
    }

    // ===========
    // = 监听相关 =
    // ===========

    /**
     * 监听 WorkRequest 状态
     */
    public static void observe(
            LifecycleOwner owner,
            WorkRequest workRequest
    ) {
        if (owner == null || workRequest == null) return;
        if (workRequest.getId() == null) return;
        WorkManagerUtils.getInstance().getWorkInfoByIdLiveData(
                workRequest.getId()
        ).observe(owner, workInfo -> {
            if (workInfo != null) {

//                DevLogEngine.getEngine().dTag(TAG, "Worker 是否完成: " + workInfo.getState().isFinished());

                switch (workInfo.getState()) {
                    case BLOCKED:
                        DevLogEngine.getEngine().dTag(TAG, "堵塞");
                        break;
                    case RUNNING:
                        int progress = workInfo.getProgress().getInt(DevFinal.PROGRESS, 0);
//                        DevLogEngine.getEngine().dTag(TAG, "正在运行");
                        DevLogEngine.getEngine().dTag(TAG, String.format(
                                "正在运行, 进度: %d%%", progress
                        ));
                        break;
                    case ENQUEUED:
                        DevLogEngine.getEngine().dTag(TAG, "任务入队");
                        break;
                    case CANCELLED:
                        DevLogEngine.getEngine().dTag(TAG, "取消");
                        break;
                    case FAILED:
                        DevLogEngine.getEngine().dTag(TAG, "失败");
                        break;
                    case SUCCEEDED:
                        DevLogEngine.getEngine().dTag(TAG, "成功");
                        // doWork return Result 传入了 Data 则会存在数据
                        String content = workInfo.getOutputData().getString(DevFinal.CONTENT);
                        DevLogEngine.getEngine().dTag(TAG, "content: " + content);
                        break;
                }
            }
        });
    }
}