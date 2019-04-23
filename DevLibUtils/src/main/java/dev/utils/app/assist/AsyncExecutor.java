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
 * detail: 异步执行
 * @author MaTianyu
 * @author Ttt (重写、规范注释、逻辑判断等)
 */
public class AsyncExecutor {

    // 日志 TAG
    private final String TAG = AsyncExecutor.class.getSimpleName();
    // 线程池
    private ExecutorService threadPool;
    // 主线程 Hander
    private Handler handler = new Handler(Looper.getMainLooper());

    public AsyncExecutor() {
        this(null);
    }

    public AsyncExecutor(final ExecutorService pool) {
        if (threadPool != null) {
            shutdownNow();
        }
        // 进行赋值
        threadPool = pool;
        // 防止为null
        if (threadPool == null) {
            threadPool = Executors.newCachedThreadPool();
        }
    }

    /**
     * 立即关闭线程池任务
     */
    public synchronized void shutdownNow() {
        if (threadPool != null && !threadPool.isShutdown()) {
            threadPool.shutdownNow();
            threadPool = null;
        }
    }

    /**
     * 将任务投入线程池执行
     * @param worker
     * @return {@link FutureTask<T> }
     */
    public <T> FutureTask<T> execute(final Worker<T> worker) {
        if (worker == null) return null;
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
        threadPool.execute(task);
        return task;
    }

    /**
     * 将子线程结果传递到UI线程
     * @param worker
     * @param result
     * @return <T>
     */
    private <T> T postResult(final Worker<T> worker, final T result) {
        if (worker == null) return result;
        handler.post(new Runnable() {
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
     * 将子线程结果传递到UI线程
     * @param worker
     */
    private void postCancel(final Worker worker) {
        if (worker == null) return;
        handler.post(new Runnable() {
            @Override
            public void run() {
                worker.onCanceled();
            }
        });
    }

    /**
     * 执行任务
     * @param call
     * @param <T>  泛型
     * @return {@link FutureTask<T>}
     */
    public <T> FutureTask<T> execute(final Callable<T> call) {
        if (call == null) return null;
        FutureTask<T> task = new FutureTask<>(call);
        threadPool.execute(task);
        return task;
    }

    /**
     * detail: 任务
     * @author Ttt
     */
    public abstract class Worker<T> {

        /**
         * 后台运行
         * @return
         */
        protected abstract T doInBackground();

        /**
         * 将子线程结果传递到UI线程
         * @param data
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
