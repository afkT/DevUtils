package dev.utils.app.assist.manager;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.utils.LogPrintUtils;

/**
 * detail: 线程管理类 - 统一使用DevThreadManager, 抛弃该类
 * Created by Ttt
 */
public final class ThreadManager {

    // 日志TAG
    private final String TAG = ThreadManager.class.getSimpleName();

    // 线程池对象
    private final ExecutorService threadPool = Executors.newFixedThreadPool(getThreads());

    // ThreadManager 实例
    private static ThreadManager INSTANCE = new ThreadManager();

    /** 禁止构造对象,保证只有一个实例 */
    private ThreadManager() {
    }

    /** 获取 ThreadManager 实例 ,单例模式 */
    public static ThreadManager getInstance() {
        return INSTANCE;
    }

    // ==

    /**
     * 获取线程数
     * @return 线程数量
     */
    private final int getThreads() {
        // 使用计算过后的
        return getCaclThreads();
        // 使用固定自定义的线程数量
        //return 10;
    }

    /**
     * 获取线程数
     * @return 线程数量
     */
    private final int getCaclThreads() {
        // return Runtime.getRuntime().availableProcessors() * 2 + 1
        // --
        // 获取CPU核心数
        int cNumber = Runtime.getRuntime().availableProcessors();
        // 如果小于等于5,则返回5
        if (cNumber <= 5) {
            return 5;
        } else { // 大于5的情况
            if (cNumber * 2 + 1 >= 10) { // 防止线程数量过大,当大于10 的时候,返回 10
                return 10;
            } else { // 不大于10的时候,默认返回 支持的数量 * 2 + 1
                return cNumber * 2 + 1;
            }
        }
    }

    // ==

    /**
     * 加入到线程池任务队列
     * @param runnable
     */
    public void addTask(Runnable runnable) {
        threadPool.execute(runnable);
    }


    /**
     * 通过反射,调用某个类的方法
     * @param method
     * @param _class
     */
    public void addTask(final Method method, final Object _class) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    method.invoke(_class);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "addTask");
                }
            }
        });
    }

    // ==

    /**
     * shutdown() 会等待所有提交的任务执行完成，不管是正在执行还是保存在任务队列中的已提交任务
     */
    public void shutdown() {
        threadPool.shutdown();
    }

    /**
     * shutdownNow 会尝试中断正在执行的任务(其主要是中断一些指定方法如sleep方法)，并且停止执行等待队列中提交的任务。
     * @return 返回等待执行的线程任务列表
     */
    public List<Runnable> shutdownNow() {
        return threadPool.shutdownNow();
    }

    /**
     * isShutDown 当调用 shutdown() 方法后返回为true。
     * @return true: 调用了 shutdown(), false: 没调用
     */
    public boolean isShutdown() {
        return threadPool.isShutdown();
    }

    /**
     * 若关闭后所有任务都已完成,则返回true.
     * 注意除非首先调用shutdown或shutdownNow, 否则isTerminated 永不为true.
     * // --
     * isTerminated当调用shutdown()方法后，并且所有提交的任务完成后返回为true
     * @return true: 是否全部已完成, false: 未完成
     */
    public boolean isTerminated() {
        return threadPool.isTerminated();
    }
}
