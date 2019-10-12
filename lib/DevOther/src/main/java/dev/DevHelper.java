package dev;

import dev.utils.app.HandlerUtils;

/**
 * detail: Dev 工具类链式调用 Helper 类
 * @author Ttt
 * <pre>
 *     通过 DevApp 工具类快捷实现
 *     <p></p>
 *     DevApp Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md"/>
 * </pre>
 */
public final class DevHelper {

    // DevHelper
    private static final DevHelper HELPER = new DevHelper();

    /**
     * 获取单例 DevHelper
     * @return {@link DevHelper}
     */
    public static DevHelper get() {
        return HELPER;
    }

    // ==========
    // = Helper =
    // ==========

    /**
     * 获取 ViewHelper
     * @return {@link ViewHelper}
     */
    public ViewHelper viewHelper(){
        return ViewHelper.get();
    }

    /**
     * 获取 DevHelper
     * @return {@link DevHelper}
     */
    public DevHelper devHelper(){
        return this;
    }

    // ===========
    // = Handler =
    // ===========

    /**
     * 在主线程 Handler 中执行任务
     * @param runnable 可执行的任务
     * @return {@link DevHelper}
     */
    public DevHelper postRunnable(final Runnable runnable) {
        HandlerUtils.postRunnable(runnable);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @return {@link DevHelper}
     */
    public DevHelper postRunnable(final Runnable runnable, final long delayMillis) {
        HandlerUtils.postRunnable(runnable, delayMillis);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @param number      轮询次数
     * @param interval    轮询时间
     * @return {@link DevHelper}
     */
    public DevHelper postRunnable(final Runnable runnable, final long delayMillis, final int number, final int interval) {
        HandlerUtils.postRunnable(runnable, delayMillis, number, interval);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable      可执行的任务
     * @param delayMillis   延迟时间
     * @param number        轮询次数
     * @param interval      轮询时间
     * @param onEndListener 结束通知
     * @return {@link DevHelper}
     */
    public DevHelper postRunnable(final Runnable runnable, final long delayMillis, final int number, final int interval, final HandlerUtils.OnEndListener onEndListener) {
        HandlerUtils.postRunnable(runnable, delayMillis, number, interval, onEndListener);
        return this;
    }

    /**
     * 在主线程 Handler 中清除任务
     * @param runnable 需要清除的任务
     * @return {@link DevHelper}
     */
    public DevHelper removeRunnable(final Runnable runnable) {
        HandlerUtils.removeRunnable(runnable);
        return this;
    }
}
