package java.dev.other.work.demo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.dev.other.work.WorkManagerUtils;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import dev.engine.log.DevLogEngine;
import dev.utils.common.DateUtils;

public class ChainWorker {

    // 日志 TAG
    private static final String TAG = ChainWorker.class.getSimpleName();

    /**
     * 模拟工作链
     */
    public static void start() {
        WorkManagerUtils.getInstance().beginWith(
                Arrays.asList(
                        builder(AWorker.class, 1000L),
                        builder(AWorker.class, 3000L)
                )
        )
                .then(builder(BWorker.class, 2000L))
                .then(builder(CWorker.class, 5000L))
                .then(builder(DWorker.class, 10000L))
                .enqueue();
    }

    public static OneTimeWorkRequest builder(
            final Class<? extends ListenableWorker> clazz,
            final long millis
    ) {
        return new OneTimeWorkRequest.Builder(clazz)
                .setInitialDelay(millis, TimeUnit.MILLISECONDS)
                .addTag(clazz.getSimpleName()).build();
    }

    // =

    public static class AWorker
            extends Worker {

        public AWorker(
                @NonNull Context context,
                @NonNull WorkerParameters workerParams
        ) {
            super(context, workerParams);
        }

        @NonNull
        @Override
        public Result doWork() {
            DevLogEngine.getEngine().dTag(TAG, "AWorker " + DateUtils.getDateNow());
            return Result.success();
        }
    }

    public static class BWorker
            extends Worker {

        public BWorker(
                @NonNull Context context,
                @NonNull WorkerParameters workerParams
        ) {
            super(context, workerParams);
        }

        @NonNull
        @Override
        public Result doWork() {
            DevLogEngine.getEngine().dTag(TAG, "BWorker " + DateUtils.getDateNow());
            return Result.success();
        }
    }

    public static class CWorker
            extends Worker {

        public CWorker(
                @NonNull Context context,
                @NonNull WorkerParameters workerParams
        ) {
            super(context, workerParams);
        }

        @NonNull
        @Override
        public Result doWork() {
            DevLogEngine.getEngine().dTag(TAG, "CWorker " + DateUtils.getDateNow());
            return Result.success();
        }
    }

    public static class DWorker
            extends Worker {

        public DWorker(
                @NonNull Context context,
                @NonNull WorkerParameters workerParams
        ) {
            super(context, workerParams);
        }

        @NonNull
        @Override
        public Result doWork() {
            DevLogEngine.getEngine().dTag(TAG, "DWorker " + DateUtils.getDateNow());
            return Result.success();
        }
    }
}