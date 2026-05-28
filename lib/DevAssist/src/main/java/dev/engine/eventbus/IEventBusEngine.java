package dev.engine.eventbus;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * detail: EventBus Engine 接口
 * @author Ttt
 */
public interface IEventBusEngine<Config extends IEventBusEngine.EngineConfig> {

    /**
     * detail: EventBus Config
     * @author Ttt
     */
    interface EngineConfig {
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 初始化方法
     * @param config EventBus Config
     * @return {@code true} success, {@code false} fail
     */
    boolean initialize(@NonNull Config config);

    /**
     * 应用事件配置
     * @param key    key
     * @param config EventBus Config
     * @return {@code true} success, {@code false} fail
     */
    boolean config(
            @NonNull String key,
            @NonNull Config config
    );

    // ==========
    // = 发送事件 =
    // ==========

    /**
     * 发送事件
     * @param key   key
     * @param value 事件数据
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean post(
            @NonNull String key,
            @NonNull T value
    );

    /**
     * 发送延迟事件
     * @param key   key
     * @param value 事件数据
     * @param delay 延迟毫秒数
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean postDelay(
            @NonNull String key,
            @NonNull T value,
            long delay
    );

    /**
     * 发送延迟事件
     * @param key       key
     * @param lifecycle 生命周期持有者
     * @param value     事件数据
     * @param delay     延迟毫秒数
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean postDelay(
            @NonNull String key,
            @NonNull LifecycleOwner lifecycle,
            @NonNull T value,
            long delay
    );

    /**
     * 顺序发送事件
     * @param key   key
     * @param value 事件数据
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean postOrderly(
            @NonNull String key,
            @NonNull T value
    );

    /**
     * 跨进程发送事件
     * @param key   key
     * @param value 事件数据
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean postAcrossProcess(
            @NonNull String key,
            @NonNull T value
    );

    /**
     * 跨 App 发送事件
     * @param key   key
     * @param value 事件数据
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean postAcrossApp(
            @NonNull String key,
            @NonNull T value
    );

    /**
     * 广播形式发送事件
     * @param key   key
     * @param value 事件数据
     * @return {@code true} success, {@code false} fail
     * @deprecated 建议使用 {@link #postAcrossProcess(String, Object)} 或 {@link #postAcrossApp(String, Object)}
     */
    <T> boolean broadcast(
            @NonNull String key,
            @NonNull T value
    );

    /**
     * 广播形式发送事件
     * @param key        key
     * @param value      事件数据
     * @param foreground {@code true} 前台广播, {@code false} 后台广播
     * @param onlyInApp  {@code true} 只在 App 内有效, {@code false} 全局有效
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean broadcast(
            @NonNull String key,
            @NonNull T value,
            boolean foreground,
            boolean onlyInApp
    );

    // ==========
    // = 观察事件 =
    // ==========

    /**
     * 注册生命周期感知观察者
     * @param key       key
     * @param type      事件类型
     * @param lifecycle 生命周期持有者
     * @param observer  事件观察者
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean observe(
            @NonNull String key,
            @NonNull Class<T> type,
            @NonNull LifecycleOwner lifecycle,
            @NonNull Observer<T> observer
    );

    /**
     * 注册生命周期感知 Sticky 观察者
     * @param key       key
     * @param type      事件类型
     * @param lifecycle 生命周期持有者
     * @param observer  事件观察者
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean observeSticky(
            @NonNull String key,
            @NonNull Class<T> type,
            @NonNull LifecycleOwner lifecycle,
            @NonNull Observer<T> observer
    );

    /**
     * 注册永久观察者
     * @param key      key
     * @param type     事件类型
     * @param observer 事件观察者
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean observeForever(
            @NonNull String key,
            @NonNull Class<T> type,
            @NonNull Observer<T> observer
    );

    /**
     * 注册永久 Sticky 观察者
     * @param key      key
     * @param type     事件类型
     * @param observer 事件观察者
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean observeStickyForever(
            @NonNull String key,
            @NonNull Class<T> type,
            @NonNull Observer<T> observer
    );

    /**
     * 移除永久观察者
     * @param key      key
     * @param type     事件类型
     * @param observer 事件观察者
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean removeObserver(
            @NonNull String key,
            @NonNull Class<T> type,
            @NonNull Observer<T> observer
    );
}