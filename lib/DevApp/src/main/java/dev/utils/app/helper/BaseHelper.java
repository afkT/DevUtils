package dev.utils.app.helper;

import android.view.View;

import dev.utils.app.HandlerUtils;
import dev.utils.app.helper.dev.DevHelper;
import dev.utils.app.helper.flow.FlowHelper;
import dev.utils.app.helper.quick.QuickHelper;
import dev.utils.app.helper.view.ViewHelper;

/**
 * detail: 基础 Helper 通用实现类
 * @author Ttt
 */
public abstract class BaseHelper<Helper> {

    /**
     * 获取 DevHelper
     * @return {@link DevHelper}
     */
    public DevHelper devHelper() {
        return DevHelper.get();
    }

    /**
     * 获取 QuickHelper
     * @param target 目标 View
     * @return {@link QuickHelper}
     */
    public QuickHelper quickHelper(View target) {
        return QuickHelper.get(target);
    }

    /**
     * 获取 ViewHelper
     * @return {@link ViewHelper}
     */
    public ViewHelper viewHelper() {
        return ViewHelper.get();
    }

    /**
     * 获取 FlowHelper
     * @return {@link FlowHelper}
     */
    public FlowHelper flowHelper() {
        return FlowHelper.get();
    }

    // ========
    // = Flow =
    // ========

    /**
     * 执行 Action 流方法
     * @param action Action
     * @return Helper
     */
    public abstract Helper flow(FlowHelper.Action action);

    // =

    /**
     * 流式返回传入值
     * @param value 泛型值
     * @param <T>   泛型
     * @return 泛型值
     */
    public <T> T flowValue(T value) {
        return value;
    }

    /**
     * 流式返回传入值
     * @param value  泛型值
     * @param action Action
     * @param <T>    泛型
     * @return 泛型值
     */
    public <T> T flowValue(
            T value,
            FlowHelper.Action action
    ) {
        if (action != null) {
            action.action();
        }
        return value;
    }

    // ================
    // = HandlerUtils =
    // ================

    /**
     * 在主线程 Handler 中执行任务
     * @param runnable 可执行的任务
     * @return Helper
     */
    public abstract Helper postRunnable(Runnable runnable);

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @return Helper
     */
    public abstract Helper postRunnable(
            Runnable runnable,
            long delayMillis
    );

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param delayMillis 延迟时间
     * @param runnable    可执行的任务
     * @return Helper
     */
    public abstract Helper postRunnable(
            long delayMillis,
            Runnable runnable
    );

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @param number      轮询次数
     * @param interval    轮询时间
     * @return Helper
     */
    public abstract Helper postRunnable(
            Runnable runnable,
            long delayMillis,
            int number,
            int interval
    );

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @param number      轮询次数
     * @param interval    轮询时间
     * @param listener    结束通知
     * @return Helper
     */
    public abstract Helper postRunnable(
            Runnable runnable,
            long delayMillis,
            int number,
            int interval,
            HandlerUtils.OnEndListener listener
    );

    /**
     * 在主线程 Handler 中清除任务
     * @param runnable 需要清除的任务
     * @return Helper
     */
    public abstract Helper removeRunnable(Runnable runnable);
}