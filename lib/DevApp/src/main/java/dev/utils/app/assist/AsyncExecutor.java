package dev.utils.app.assist;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import dev.utils.LogPrintUtils;

/**
 * detail: 异步执行辅助类
 * @author MaTianyu
 * @author Ttt
 */
public class AsyncExecutor {

    // 日志 TAG
    private final String TAG = AsyncExecutor.class.getSimpleName();

    // 主线程 Handler
    private Handler         mHandler = new Handler(Looper.getMainLooper());
    // 线程池
    private ExecutorService mThreadPool;

    public AsyncExecutor() {
        this(null);
    }

    /**
     * 构造函数
     * @param pool {@link ExecutorService}
     */
    public AsyncExecutor(final ExecutorService pool) {
        if (mThreadPool != null) {
            shutdownNow();
        }
        mThreadPool = (pool == null) ? Executors.newCachedThreadPool() : pool;
    }

    /**
     * 立即关闭线程池任务
     */
    public synchronized void shutdownNow() {
        if (mThreadPool != null && !mThreadPool.isShutdown()) {
            mThreadPool.shutdownNow();
            mThreadPool = null;
        }
    }

    /**
     * 将任务投入线程池执行
     * @param worker 任务 {@link Worker}
     * @param <T>    泛型
     * @return {@link FutureTask<T>}
     */
    public <T> FutureTask<T> execute(final Worker<T> worker) {
        if (worker != null) {
            Callable<T> call = new Callable<T>() {
                @Override
                public T call() throws Exception {
                    return postResult(worker, worker.doInBackground());
                }
            };
            FutureTask<T> task = new FutureTask<T>(call) {
                @Override
                protected void done() {
                    try {
                        get();
                    } catch (InterruptedException e) {
                        LogPrintUtils.eTag(TAG, e, "execute");
                        worker.abort();
                        postCancel(worker);
                    } catch (ExecutionException e) {
                        LogPrintUtils.eTag(TAG, e, "execute");
                        throw new RuntimeException("An error occured while executing doInBackground()", e.getCause());
                    } catch (CancellationException e) {
                        worker.abort();
                        postCancel(worker);
                        LogPrintUtils.eTag(TAG, e, "execute");
                    }
                }
            };
            mThreadPool.execute(task);
            return task;
        }
        return null;
    }

    /**
     * 将子线程结果传递到 UI 线程
     * @param worker 任务 {@link Worker}
     * @param result 执行数据
     * @param <T>    泛型
     * @return 执行数据
     */
    private <T> T postResult(final Worker<T> worker, final T result) {
        if (worker == null) return result;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (worker != null) {
                    worker.onPostExecute(result);
                }
            }
        });
        return result;
    }

    /**
     * 将子线程结果传递到 UI 线程
     * @param worker 任务 {@link Worker}
     */
    private void postCancel(final Worker worker) {
        if (worker == null) return;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                worker.onCanceled();
            }
        });
    }

    /**
     * 执行任务
     * @param call {@link Callable}
     * @param <T>  泛型
     * @return {@link FutureTask<T>}
     */
    public <T> FutureTask<T> execute(final Callable<T> call) {
        if (call != null) {
            FutureTask<T> task = new FutureTask<>(call);
            mThreadPool.execute(task);
            return task;
        }
        return null;
    }

    /**
     * detail: 任务
     * @param <T> 泛型
     * @author Ttt
     */
    public abstract class Worker<T> {

        /**
         * 后台运行
         * @return {@link T} 泛型类型
         */
        protected abstract T doInBackground();

        /**
         * 将子线程结果传递到 UI 线程
         * @param data 数据
         */
        protected void onPostExecute(T data) {
        }

        /**
         * 取消任务
         */
        protected void onCanceled() {
        }

        /**
         * 中止任务
         */
        protected void abort() {
        }
    }
}