package dev.function;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.utils.LogPrintUtils;
import dev.utils.common.thread.DevThreadPool;

/**
 * detail: 执行方法类
 * @author Ttt
 */
public final class Function {

    private Function() {
    }

    // 默认线程池对象
    private static final ExecutorService sThreadPool = Executors.newFixedThreadPool(DevThreadPool.getCalcThreads());

    /**
     * detail: 方法体
     * @author Ttt
     */
    public interface Method {

        void method(Operation operation);
    }

    /**
     * detail: 方法体 ( 存在异常触发 )
     * @author Ttt
     * <pre>
     *     前提属于调用 try-catch 方法
     * </pre>
     */
    public interface Method2
            extends Method {

        void error(
                Operation operation,
                Throwable error
        );
    }

    // =========
    // = 包装类 =
    // =========

    /**
     * detail: Function 操作包装类
     * @author Ttt
     */
    public static final class Operation {

        // 日志 TAG
        private final String TAG;

        public Operation() {
            this(Operation.class.getSimpleName());
        }

        public Operation(final String tag) {
            this.TAG = tag;
        }

        // ===========
        // = 捕获异常 =
        // ===========

        /**
         * 捕获异常处理
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation tryCatch(final Method method) {
            return tryCatch(TAG, method);
        }


        /**
         * 捕获异常处理
         * @param tag    输出日志
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation tryCatch(
                final String tag,
                final Method method
        ) {
            if (method != null) {
                try {
                    method.method(Operation.this);
                } catch (Throwable e) {
                    LogPrintUtils.eTag(tag, e, "tryCatch");
                    if (method instanceof Method2) {
                        ((Method2) method).error(Operation.this, e);
                    }
                }
            }
            return this;
        }

        // ===========
        // = 线程方法 =
        // ===========

        /**
         * 后台线程执行
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation thread(final Method method) {
            if (method != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        method.method(Operation.this);
                    }
                }).start();
            }
            return this;
        }

        /**
         * 后台线程池执行
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation threadPool(final Method method) {
            return threadPool(sThreadPool, method);
        }

        /**
         * 后台线程池执行
         * @param pool   线程池
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation threadPool(
                final ExecutorService pool,
                final Method method
        ) {
            if (pool != null && method != null) {
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        method.method(Operation.this);
                    }
                });
            }
            return this;
        }

        // ==================
        // = 线程捕获异常方法 =
        // ==================
    }
}