package dev.utils.app.helper.flow;

import dev.utils.app.HandlerUtils;
import dev.utils.app.helper.BaseHelper;
import dev.utils.app.helper.dev.DevHelper;
import dev.utils.app.helper.quick.QuickHelper;
import dev.utils.app.helper.view.ViewHelper;

/**
 * detail: 流式 ( 链式 ) 连接 Helper 类
 * @author Ttt
 * <pre>
 *     支持连接 {@link DevHelper}、{@link QuickHelper}、{@link ViewHelper} 等类使用
 *     且不局限于上述类, 只需要调用 {@link BaseHelper#flowValue(Object, Action)} 即可使用
 * </pre>
 */
public final class FlowHelper
        extends BaseHelper<FlowHelper> {

    private FlowHelper() {
    }

    // FlowHelper
    private static final FlowHelper HELPER = new FlowHelper();

    /**
     * 获取单例 FlowHelper
     * @return {@link FlowHelper}
     */
    public static FlowHelper get() {
        return HELPER;
    }

    /**
     * detail: 操作方法
     * @author Ttt
     */
    public interface Action {

        /**
         * 操作方法
         */
        void action();
    }

    // ========
    // = Flow =
    // ========

    /**
     * 执行 Action 流方法
     * @param action Action
     * @return Helper
     */
    @Override
    public FlowHelper flow(FlowHelper.Action action) {
        if (action != null) action.action();
        return this;
    }

    // ================
    // = HandlerUtils =
    // ================

    /**
     * 在主线程 Handler 中执行任务
     * @param runnable 可执行的任务
     * @return Helper
     */
    @Override
    public FlowHelper postRunnable(Runnable runnable) {
        HandlerUtils.postRunnable(runnable);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @return Helper
     */
    @Override
    public FlowHelper postRunnable(
            Runnable runnable,
            long delayMillis
    ) {
        HandlerUtils.postRunnable(runnable, delayMillis);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @param number      轮询次数
     * @param interval    轮询时间
     * @return Helper
     */
    @Override
    public FlowHelper postRunnable(
            Runnable runnable,
            long delayMillis,
            int number,
            int interval
    ) {
        HandlerUtils.postRunnable(runnable, delayMillis, number, interval);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @param number      轮询次数
     * @param interval    轮询时间
     * @param listener    结束通知
     * @return Helper
     */
    @Override
    public FlowHelper postRunnable(
            Runnable runnable,
            long delayMillis,
            int number,
            int interval,
            HandlerUtils.OnEndListener listener
    ) {
        HandlerUtils.postRunnable(runnable, delayMillis, number, interval, listener);
        return this;
    }

    /**
     * 在主线程 Handler 中清除任务
     * @param runnable 需要清除的任务
     * @return Helper
     */
    @Override
    public FlowHelper removeRunnable(Runnable runnable) {
        HandlerUtils.removeRunnable(runnable);
        return this;
    }
}