package dev.function;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.base.DevObject;
import dev.utils.LogPrintUtils;
import dev.utils.common.StringUtils;
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
        private final String    TAG;
        // 存储数据
        private       DevObject mObject;

        public Operation() {
            this(Operation.class.getSimpleName());
        }

        public Operation(final String tag) {
            if (StringUtils.isNotEmpty(tag)) {
                this.TAG = tag;
            } else {
                this.TAG = Operation.class.getSimpleName();
            }
        }

        // ===============
        // = 对外公开方法 =
        // ===============

        /**
         * 获取 Object
         * @return {@link DevObject}
         */
        public DevObject getObject() {
            return mObject;
        }

        /**
         * 设置 Object
         * @param object {@link DevObject}
         * @return {@link Operation}
         */
        public Operation setObject(final DevObject object) {
            this.mObject = object;
            return this;
        }

        // =

        /**
         * 获取 Operation
         * @return {@link Operation}
         */
        public Operation operation() {
            return new Operation();
        }

        /**
         * 获取 Operation
         * @param tag 日志 TAG
         * @return {@link Operation}
         */
        public Operation operation(final String tag) {
            return new Operation(tag);
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
            if (method != null) {
                try {
                    method.method(Operation.this);
                } catch (Throwable e) {
                    LogPrintUtils.eTag(TAG, e, "tryCatch");
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
            return thread(method, 0L);
        }

        /**
         * 后台线程执行
         * @param method      执行方法
         * @param delayMillis 延迟执行时间 ( 毫秒 )
         * @return {@link Operation}
         */
        public Operation thread(
                final Method method,
                final long delayMillis
        ) {
            if (method != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (delayMillis > 0L) {
                            try {
                                Thread.sleep(delayMillis);
                            } catch (Exception e) {
                            }
                        }
                        method.method(Operation.this);
                    }
                }).start();
            }
            return this;
        }

        // =

        /**
         * 后台线程池执行
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation threadPool(final Method method) {
            return threadPool(sThreadPool, method, 0L);
        }

        /**
         * 后台线程池执行
         * @param method      执行方法
         * @param delayMillis 延迟执行时间 ( 毫秒 )
         * @return {@link Operation}
         */
        public Operation threadPool(
                final Method method,
                final long delayMillis
        ) {
            return threadPool(sThreadPool, method, delayMillis);
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
            return threadPool(pool, method, 0L);
        }

        /**
         * 后台线程池执行
         * @param pool        线程池
         * @param method      执行方法
         * @param delayMillis 延迟执行时间 ( 毫秒 )
         * @return {@link Operation}
         */
        public Operation threadPool(
                final ExecutorService pool,
                final Method method,
                final long delayMillis
        ) {
            if (pool != null && method != null) {
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (delayMillis > 0L) {
                            try {
                                Thread.sleep(delayMillis);
                            } catch (Exception e) {
                            }
                        }
                        method.method(Operation.this);
                    }
                });
            }
            return this;
        }

        // ==================
        // = 线程捕获异常方法 =
        // ==================

        /**
         * 后台线程执行
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation threadCatch(final Method method) {
            return threadCatch(method, 0L);
        }

        /**
         * 后台线程执行
         * @param method      执行方法
         * @param delayMillis 延迟执行时间 ( 毫秒 )
         * @return {@link Operation}
         */
        public Operation threadCatch(
                final Method method,
                final long delayMillis
        ) {
            if (method != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (delayMillis > 0L) {
                            try {
                                Thread.sleep(delayMillis);
                            } catch (Exception e) {
                            }
                        }
                        try {
                            method.method(Operation.this);
                        } catch (Throwable e) {
                            LogPrintUtils.eTag(TAG, e, "threadCatch");
                            if (method instanceof Method2) {
                                ((Method2) method).error(Operation.this, e);
                            }
                        }
                    }
                }).start();
            }
            return this;
        }

        // =

        /**
         * 后台线程池执行
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation threadPoolCatch(final Method method) {
            return threadPoolCatch(sThreadPool, method, 0L);
        }

        /**
         * 后台线程池执行
         * @param method      执行方法
         * @param delayMillis 延迟执行时间 ( 毫秒 )
         * @return {@link Operation}
         */
        public Operation threadPoolCatch(
                final Method method,
                final long delayMillis
        ) {
            return threadPoolCatch(sThreadPool, method, delayMillis);
        }

        /**
         * 后台线程池执行
         * @param pool   线程池
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation threadPoolCatch(
                final ExecutorService pool,
                final Method method
        ) {
            return threadPoolCatch(pool, method, 0L);
        }

        /**
         * 后台线程池执行
         * @param pool        线程池
         * @param method      执行方法
         * @param delayMillis 延迟执行时间 ( 毫秒 )
         * @return {@link Operation}
         */
        public Operation threadPoolCatch(
                final ExecutorService pool,
                final Method method,
                final long delayMillis
        ) {
            if (pool != null && method != null) {
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (delayMillis > 0L) {
                            try {
                                Thread.sleep(delayMillis);
                            } catch (Exception e) {
                            }
                        }
                        try {
                            method.method(Operation.this);
                        } catch (Throwable e) {
                            LogPrintUtils.eTag(TAG, e, "threadPoolCatch");
                            if (method instanceof Method2) {
                                ((Method2) method).error(Operation.this, e);
                            }
                        }
                    }
                });
            }
            return this;
        }
    }

    // ===========
    // = 快捷方法 =
    // ===========

    /**
     * 获取 Operation
     * @return {@link Operation}
     */
    public static Operation operation() {
        return new Operation();
    }

    /**
     * 获取 Operation
     * @param tag 日志 TAG
     * @return {@link Operation}
     */
    public static Operation operation(final String tag) {
        return new Operation(tag);
    }

    /**
     * 设置 Object
     * @param object {@link DevObject}
     * @return {@link Operation}
     */
    public static Operation object(final DevObject object) {
        return new Operation().setObject(object);
    }

    // ===========
    // = 捕获异常 =
    // ===========

    /**
     * 捕获异常处理
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation tryCatch(final Method method) {
        return new Operation().tryCatch(method);
    }

    // ===========
    // = 线程方法 =
    // ===========

    /**
     * 后台线程执行
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation thread(final Method method) {
        return new Operation().thread(method);
    }

    /**
     * 后台线程执行
     * @param method      执行方法
     * @param delayMillis 延迟执行时间 ( 毫秒 )
     * @return {@link Operation}
     */
    public static Operation thread(
            final Method method,
            final long delayMillis
    ) {
        return new Operation().thread(method, delayMillis);
    }

    // =

    /**
     * 后台线程池执行
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation threadPool(final Method method) {
        return new Operation().threadPool(method);
    }

    /**
     * 后台线程池执行
     * @param method      执行方法
     * @param delayMillis 延迟执行时间 ( 毫秒 )
     * @return {@link Operation}
     */
    public static Operation threadPool(
            final Method method,
            final long delayMillis
    ) {
        return new Operation().threadPool(method, delayMillis);
    }

    /**
     * 后台线程池执行
     * @param pool   线程池
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation threadPool(
            final ExecutorService pool,
            final Method method
    ) {
        return new Operation().threadPool(pool, method);
    }

    /**
     * 后台线程池执行
     * @param pool        线程池
     * @param method      执行方法
     * @param delayMillis 延迟执行时间 ( 毫秒 )
     * @return {@link Operation}
     */
    public static Operation threadPool(
            final ExecutorService pool,
            final Method method,
            final long delayMillis
    ) {
        return new Operation().threadPool(pool, method, delayMillis);
    }

    // ==================
    // = 线程捕获异常方法 =
    // ==================

    /**
     * 后台线程执行
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation threadCatch(final Method method) {
        return new Operation().threadCatch(method);
    }

    /**
     * 后台线程执行
     * @param method      执行方法
     * @param delayMillis 延迟执行时间 ( 毫秒 )
     * @return {@link Operation}
     */
    public static Operation threadCatch(
            final Method method,
            final long delayMillis
    ) {
        return new Operation().threadCatch(method, delayMillis);
    }

    // =

    /**
     * 后台线程池执行
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation threadPoolCatch(final Method method) {
        return new Operation().threadPoolCatch(method);
    }

    /**
     * 后台线程池执行
     * @param method      执行方法
     * @param delayMillis 延迟执行时间 ( 毫秒 )
     * @return {@link Operation}
     */
    public static Operation threadPoolCatch(
            final Method method,
            final long delayMillis
    ) {
        return new Operation().threadPoolCatch(method, delayMillis);
    }

    /**
     * 后台线程池执行
     * @param pool   线程池
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation threadPoolCatch(
            final ExecutorService pool,
            final Method method
    ) {
        return new Operation().threadPoolCatch(pool, method);
    }

    /**
     * 后台线程池执行
     * @param pool        线程池
     * @param method      执行方法
     * @param delayMillis 延迟执行时间 ( 毫秒 )
     * @return {@link Operation}
     */
    public static Operation threadPoolCatch(
            final ExecutorService pool,
            final Method method,
            final long delayMillis
    ) {
        return new Operation().threadPoolCatch(pool, method, delayMillis);
    }
}