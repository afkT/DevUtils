package utils_use.thread;

import dev.utils.common.thread.DevThreadManager;
import dev.utils.common.thread.DevThreadPool;

/**
 * detail: 线程使用方法
 * @author Ttt
 */
public final class ThreadUse {

    private ThreadUse() {
    }

    /**
     * 线程使用方法
     */
    private void threadUse() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };

        // = 优先判断 10 个线程数, 的线程池是否存在, 不存在则创建, 存在则复用 =
        DevThreadManager.getInstance(10).execute(runnable);

        // 与上面 传入 int 是完全不同的线程池
        DevThreadManager.getInstance("10").execute(runnable);

        // 可以先增加配置
        DevThreadManager.putConfig("QPQP", new DevThreadPool(DevThreadPool.DevThreadPoolType.CALC_CPU));
        // 使用配置的信息
        DevThreadManager.getInstance("QPQP").execute(runnable);

        DevThreadManager.putConfig("QQQQQQ", 10);
        // 使用配置的信息
        DevThreadManager.getInstance("QQQQQQ").execute(runnable);
    }
}