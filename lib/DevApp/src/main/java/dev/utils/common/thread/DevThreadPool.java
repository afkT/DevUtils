package dev.utils.common.thread;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * detail: 线程池 ( 构建类 )
 * @author Ttt
 * <pre>
 *     @see <a href="https://www.jianshu.com/p/4d4634c92253"/>
 *     <p></p>
 *     创建线程池 ( 参数 )
 *     1. 线程池里面管理多少个线程
 *     2. 如果排队满了, 额外的开的线程数
 *     3. 如果线程池没有要执行的任务存活多久
 *     4. 时间的单位
 *     5. 如果 线程池里管理的线程都已经用了, 剩下的任务临时存到 LinkedBlockingQueue 对象中排队
 *     public ThreadPoolExecutor(int corePoolSize,
 *                              int maximumPoolSize,
 *                              long keepAliveTime,
 *                              TimeUnit unit,
 *                              BlockingQueue<Runnable> workQueue) {
 *         this (corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
 *                Executors.defaultThreadFactory(), defaultHandler);
 *     }
 * </pre>
 */
public final class DevThreadPool {

    // 线程池对象
    private final ExecutorService          mThreadPool;
    // 定时任务线程池
    private       ScheduledExecutorService mScheduleExec;

    /**
     * 构造函数
     * @param threadNumber 线程数量
     */
    public DevThreadPool(int threadNumber) {
        // 如果小于等于 0, 则默认使用 1
        if (threadNumber <= 0) {
            threadNumber = 1;
        }
        this.mThreadPool = Executors.newFixedThreadPool(threadNumber);
        // 初始化定时器任务
        this.mScheduleExec = Executors.newScheduledThreadPool(threadNumber);
    }

    /**
     * 构造函数
     * @param threadPool {@link ExecutorService}
     */
    public DevThreadPool(final ExecutorService threadPool) {
        this.mThreadPool = threadPool;
        // 初始化定时器任务
        this.mScheduleExec = Executors.newScheduledThreadPool(getThreads());
    }

    /**
     * 构造函数
     * @param devThreadPoolType 线程初始化类型 {@link DevThreadPoolType}
     */
    public DevThreadPool(final DevThreadPoolType devThreadPoolType) {
        // 初始化定时器任务
        this.mScheduleExec = Executors.newScheduledThreadPool(getThreads());
        // =
        if (devThreadPoolType != null) {
            switch (devThreadPoolType) {
                case SINGLE:
                    mThreadPool = Executors.newSingleThreadExecutor();
                    // 初始化定时器任务
                    this.mScheduleExec = Executors.newScheduledThreadPool(1);
                    break;
//                case AUTO_CPU:
//                    mThreadPool = Executors.newWorkStealingPool();
//                    break;
                case CALC_CPU:
                    mThreadPool = Executors.newFixedThreadPool(getThreads());
                    break;
                case CACHE:
                    mThreadPool = Executors.newCachedThreadPool();
                    break;
                default:
                    mThreadPool = Executors.newFixedThreadPool(getThreads());
                    break;
            }
        } else {
            mThreadPool = Executors.newFixedThreadPool(getThreads());
        }
    }

    /**
     * detail: 线程池初始化枚举类型
     * @author Ttt
     * <pre>
     *     @see <a href="http://blog.csdn.net/a369414641/article/details/48342253"/>
     *     @see <a href="http://blog.csdn.net/vking_wang/article/details/9619137"/>
     * </pre>
     */
    public enum DevThreadPoolType {

        // 如果当前线程意外终止, 会创建一个新线程继续执行任务, 这和我们直接创建线程不同, 也和 newFixedThreadPool(1) 不同
        SINGLE, // newSingleThreadExecutor 获取的是一个单个的线程, 这个线程会保证你的任务执行完成

        AUTO_CPU, // 根据 CPU 来创建 ( 自定义创建 )

        CALC_CPU, // 手动计算 CPU 来创建

        CACHE, // 可缓存线程池

//        1 newCachedThreadPool: 创建一个可缓存线程池, 如果线程池长度超过处理需要, 可灵活回收空闲线程, 若无可回收, 则新建线程
//        2 newFixedThreadPool: 创建一个固定数目的、可重用的线程池
//        3 newScheduledThreadPool: 创建一个定长线程池, 支持定时及周期性任务执行
//        4 newSingleThreadExecutor: 创建一个单线程化的线程池, 它只会用唯一的工作线程来执行任务, 保证所有任务按照指定顺序 (FIFO、LIFO) 优先级执行
//        5 newSingleThreadScheduledExcutor: 创建一个单例线程池, 定期或延时执行任务
//        6 newWorkStealingPool: 创建持有足够线程的线程池来支持给定的并行级别, 并通过使用多个队列, 减少竞争
//                               它需要穿一个并行级别的参数, 如果不传, 则被设定为默认的 CPU 数量
//        7 ForkJoinPool: 支持大任务分解成小任务的线程池, 这是 Java8 新增线程池, 通常配合 ForkJoinTask 接口的子类 RecursiveAction 或 RecursiveTask 使用
    }

    // =

    /**
     * 获取线程数
     * @return {@link DevThreadPool#getCalcThreads()}
     */
    public static int getThreads() {
        return getCalcThreads();
    }

    /**
     * 获取线程数
     * @return 自动计算 CPU 核心数
     */
    public static int getCalcThreads() {
        // 获取 CPU 核心数
        int cpuNumber = Runtime.getRuntime().availableProcessors();
        // 如果小于等于 5, 则返回 5
        if (cpuNumber <= 5) {
            return 5;
        } else { // 大于 5 的情况
            if (cpuNumber * 2 + 1 >= 10) { // 防止线程数量过大, 当大于 10 的时候, 返回 10
                return 10;
            } else { // 不大于 10 的时候, 默认返回 支持的数量 * 2 + 1
                return cpuNumber * 2 + 1;
            }
        }
    }

    // =

    /**
     * 加入到线程池任务队列
     * @param runnable 线程
     */
    public void execute(final Runnable runnable) {
        if (mThreadPool != null && runnable != null) {
            mThreadPool.execute(runnable);
        }
    }

    /**
     * 加入到线程池任务队列
     * @param runnables 线程集合
     */
    public void execute(final List<Runnable> runnables) {
        if (mThreadPool != null && runnables != null) {
            for (Runnable command : runnables) {
                if (command != null) {
                    mThreadPool.execute(command);
                }
            }
        }
    }

    /**
     * 通过反射, 调用某个类的方法
     * @param method 方法
     * @param object 对象
     */
    public void execute(
            final Method method,
            final Object object
    ) {
        if (mThreadPool != null && method != null && object != null) {
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        method.invoke(object);
                    } catch (Exception ignore) {
                    }
                }
            });
        }
    }

    // =

    /**
     * shutdown 会等待所有提交的任务执行完成, 不管是正在执行还是保存在任务队列中的已提交任务
     * 待以前提交的任务执行完毕后关闭线程池
     * 启动一次顺序关闭, 执行以前提交的任务, 但不接受新任务
     * 如果已经关闭, 则调用没有作用
     */
    public void shutdown() {
        if (mThreadPool != null) {
            mThreadPool.shutdown();
        }
    }

    /**
     * shutdownNow 会尝试中断正在执行的任务 ( 其主要是中断一些指定方法如 sleep 方法 ), 并且停止执行等待队列中提交的任务
     * 试图停止所有正在执行的活动任务, 暂停处理正在等待的任务, 并返回等待执行的任务列表
     * 无法保证能够停止正在处理的活动执行任务, 但是会尽力尝试
     * @return {@link List<Runnable>}
     */
    public List<Runnable> shutdownNow() {
        if (mThreadPool != null) {
            return mThreadPool.shutdownNow();
        }
        return null;
    }

    /**
     * 判断线程池是否已关闭 ( isShutDown 当调用 shutdown() 方法后返回为 true )
     * @return {@code true} yes, {@code false} no
     */
    public boolean isShutdown() {
        if (mThreadPool != null) {
            return mThreadPool.isShutdown();
        }
        return false;
    }

    /**
     * 若关闭后所有任务都已完成, 则返回 true
     * 注意除非首先调用 shutdown 或 shutdownNow, 否则 isTerminated 永不为 true
     * isTerminated 当调用 shutdown() 方法后, 并且所有提交的任务完成后返回为 true
     * @return {@code true} yes, {@code false} no
     */
    public boolean isTerminated() {
        if (mThreadPool != null) {
            return mThreadPool.isTerminated();
        }
        return false;
    }

    /**
     * 请求关闭、发生超时或者当前线程中断
     * 无论哪一个首先发生之后, 都将导致阻塞, 直到所有任务完成执行
     * @param timeout 最长等待时间
     * @param unit    时间单位
     * @return {@code true} 请求成功, {@code false} 请求超时
     * @throws InterruptedException 终端异常
     */
    public boolean awaitTermination(
            final long timeout,
            final TimeUnit unit
    )
            throws InterruptedException {
        if (mThreadPool != null && unit != null) {
            return mThreadPool.awaitTermination(timeout, unit);
        }
        return false;
    }

    /**
     * 提交一个 Callable 任务用于执行
     * 如果想立即阻塞任务的等待, 则可以使用 {@code result = threadPool.submit(aCallable).get();} 形式的构造
     * @param task 任务
     * @param <T>  泛型
     * @return 表示任务等待完成的 Future, 该 Future 的 {@code get} 方法在成功完成时将会返回该任务的结果
     */
    public <T> Future<T> submit(final Callable<T> task) {
        if (mThreadPool != null && task != null) {
            return mThreadPool.submit(task);
        }
        return null;
    }

    /**
     * 提交一个 Runnable 任务用于执行
     * @param task   任务
     * @param result 返回的结果
     * @param <T>    泛型
     * @return 表示任务等待完成的 Future, 该 Future 的 {@code get} 方法在成功完成时将会返回该任务的结果
     */
    public <T> Future<T> submit(
            final Runnable task,
            final T result
    ) {
        if (mThreadPool != null && task != null) {
            return mThreadPool.submit(task, result);
        }
        return null;
    }

    /**
     * 提交一个 Runnable 任务用于执行
     * @param task 任务
     * @param <?>  未知类型
     * @return 表示任务等待完成的 Future, 该 Future 的 {@code get} 方法在成功完成时将会返回 null 结果
     */
    public Future<?> submit(final Runnable task) {
        if (mThreadPool != null && task != null) {
            return mThreadPool.submit(task);
        }
        return null;
    }

    /**
     * 执行给定的任务
     * 当所有任务完成时, 返回保持任务状态和结果的 Future 列表
     * 返回列表的所有元素的 {@link Future#isDone} 为 {@code true}
     * 注意, 可以正常地或通过抛出异常来终止已完成任务
     * 如果正在进行此操作时修改了给定的 collection, 则此方法的结果是不确定的
     * @param tasks 任务集合
     * @param <T>   泛型
     * @return 表示任务的 Future 列表, 列表顺序与给定任务列表的迭代器所生成的顺序相同, 每个任务都已完成
     * @throws InterruptedException 如果等待时发生中断, 在这种情况下取消尚未完成的任务
     */
    public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks)
            throws InterruptedException {
        if (mThreadPool != null && tasks != null) {
            return mThreadPool.invokeAll(tasks);
        }
        return null;
    }

    /**
     * 执行给定的任务
     * 当所有任务完成或超时期满时 ( 无论哪个首先发生 ), 返回保持任务状态和结果的 Future 列表
     * 返回列表的所有元素的 {@link Future#isDone} 为 {@code true}
     * 一旦返回后, 即取消尚未完成的任务
     * 注意, 可以正常地或通过抛出异常来终止已完成任务
     * 如果此操作正在进行时修改了给定的 collection, 则此方法的结果是不确定的
     * @param tasks   任务集合
     * @param timeout 最长等待时间
     * @param unit    时间单位
     * @param <T>     泛型
     * @return 表示任务的 Future 列表, 列表顺序与给定任务列表的迭代器所生成的顺序相同
     * 如果操作未超时, 则已完成所有任务, 如果确实超时了, 则某些任务尚未完成
     * @throws InterruptedException 如果等待时发生中断, 在这种情况下取消尚未完成的任务
     */
    public <T> List<Future<T>> invokeAll(
            final Collection<? extends Callable<T>> tasks,
            final long timeout,
            final TimeUnit unit
    )
            throws InterruptedException {
        if (mThreadPool != null && tasks != null && unit != null) {
            return mThreadPool.invokeAll(tasks, timeout, unit);
        }
        return null;
    }

    /**
     * 执行给定的任务
     * 如果某个任务已成功完成 ( 也就是未抛出异常 ), 则返回其结果
     * 一旦正常或异常返回后, 则取消尚未完成的任务
     * 如果此操作正在进行时修改了给定的 collection, 则此方法的结果是不确定的
     * @param tasks 任务集合
     * @param <T>   泛型
     * @return 某个任务返回的结果
     * @throws InterruptedException 如果等待时发生中断
     * @throws ExecutionException   如果没有任务成功完成
     */
    public <T> T invokeAny(final Collection<? extends Callable<T>> tasks)
            throws InterruptedException, ExecutionException {
        if (mThreadPool != null && tasks != null) {
            return mThreadPool.invokeAny(tasks);
        }
        return null;
    }

    /**
     * 执行给定的任务
     * 如果在给定的超时期满前某个任务已成功完成 ( 也就是未抛出异常 ), 则返回其结果
     * 一旦正常或异常返回后, 则取消尚未完成的任务
     * 如果此操作正在进行时修改了给定的 collection, 则此方法的结果是不确定的
     * @param tasks   任务集合
     * @param timeout 最长等待时间
     * @param unit    时间单位
     * @param <T>     泛型
     * @return 某个任务返回的结果
     * @throws InterruptedException 如果等待时发生中断
     * @throws ExecutionException   如果没有任务成功完成
     * @throws TimeoutException     如果在所有任务成功完成之前给定的超时期满
     */
    public <T> T invokeAny(
            final Collection<? extends Callable<T>> tasks,
            final long timeout,
            final TimeUnit unit
    )
            throws InterruptedException, ExecutionException, TimeoutException {
        if (mThreadPool != null && tasks != null && unit != null) {
            return mThreadPool.invokeAny(tasks, timeout, unit);
        }
        return null;
    }

    // =

    /**
     * 延迟执行 Runnable 命令
     * @param command 命令
     * @param delay   延迟时间
     * @param unit    单位
     * @param <?>     未知类型
     * @return 表示挂起任务完成的 ScheduledFuture, 并且其 {@code get()} 方法在完成后将返回 {@code null}
     */
    public ScheduledFuture<?> schedule(
            final Runnable command,
            final long delay,
            final TimeUnit unit
    ) {
        if (mScheduleExec != null && command != null && unit != null) {
            return mScheduleExec.schedule(command, delay, unit);
        }
        return null;
    }

    /**
     * 延迟执行 Callable 命令
     * @param callable 命令
     * @param delay    延迟时间
     * @param unit     时间单位
     * @param <V>      泛型
     * @return 可用于提取结果或取消的 ScheduledFuture
     */
    public <V> ScheduledFuture<V> schedule(
            final Callable<V> callable,
            final long delay,
            final TimeUnit unit
    ) {
        if (mScheduleExec != null && callable != null && unit != null) {
            return mScheduleExec.schedule(callable, delay, unit);
        }
        return null;
    }

    /**
     * 延迟并循环执行命令
     * @param command      命令
     * @param initialDelay 首次执行的延迟时间
     * @param period       连续执行之间的周期
     * @param unit         时间单位
     * @param <?>          未知类型
     * @return 表示挂起任务完成的 ScheduledFuture, 并且其 {@code get()} 方法在取消后将抛出异常
     */
    public ScheduledFuture<?> scheduleWithFixedRate(
            final Runnable command,
            final long initialDelay,
            final long period,
            final TimeUnit unit
    ) {
        if (mScheduleExec != null && command != null && unit != null) {
            return mScheduleExec.scheduleAtFixedRate(command, initialDelay, period, unit);
        }
        return null;
    }

    /**
     * 延迟并以固定休息时间循环执行命令
     * @param command      命令
     * @param initialDelay 首次执行的延迟时间
     * @param delay        每一次执行终止和下一次执行开始之间的延迟
     * @param unit         时间单位
     * @param <?>          未知类型
     * @return 表示挂起任务完成的 ScheduledFuture, 并且其 {@code get()} 方法在取消后将抛出异常
     */
    public ScheduledFuture<?> scheduleWithFixedDelay(
            final Runnable command,
            final long initialDelay,
            final long delay,
            final TimeUnit unit
    ) {
        if (mScheduleExec != null && command != null && unit != null) {
            return mScheduleExec.scheduleWithFixedDelay(command, initialDelay, delay, unit);
        }
        return null;
    }
}